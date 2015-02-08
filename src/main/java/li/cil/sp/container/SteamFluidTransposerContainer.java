package li.cil.sp.container;

import li.cil.sp.tileentity.SteamFluidTransposerTileEntity;
import li.cil.sp.tileentity.SteamWaterCollectorTileEntity;
import net.minecraft.block.BlockFurnace;
import net.minecraft.client.gui.inventory.GuiFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.IFluidContainerItem;

public class SteamFluidTransposerContainer extends Container {
    protected SteamFluidTransposerTileEntity tileEntity;
    private final static int PLAYER_SLOTS = 36;
    private final static int BLOCK_SLOTS = 2;

    public SteamFluidTransposerContainer(InventoryPlayer inventoryPlayer, SteamFluidTransposerTileEntity te) {
        tileEntity = te;

        //the Slot constructor takes the IInventory and the slot number in that it binds to
        //and the x-y coordinates it resides on-screen
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                addSlotToContainer(new Slot(tileEntity, j + i * 3, 62 + j * 18, 17 + i * 18));
//            }
//        }

        this.addSlotToContainer(new ContainerSlot(te, 0, 56, 17));
        this.addSlotToContainer(new OutputSlot(te, 1, 56, 53));
        this.addSlotToContainer(new ContainerDrainSlot(te, 2, 92, 17));
        this.addSlotToContainer(new OutputSlot(te, 3, 92, 53));
        //commonly used vanilla code that adds the player's inventory
        bindPlayerInventory(inventoryPlayer);
    }

    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
                        8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        ItemStack stack = null;
        Slot slotObject = (Slot) inventorySlots.get(slot);

        //null checks and checks if the item can be stacked (maxStackSize > 1)
        if (slotObject != null && slotObject.getHasStack()) {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();

            //merges the item into player inventory since its in the tileEntity
            if (slot < 2) {
                if (!this.mergeItemStack(stackInSlot, BLOCK_SLOTS, BLOCK_SLOTS + PLAYER_SLOTS, true)) {
                    return null;
                }
            }
            //places it into the tileEntity is possible since its in the player inventory
            else if (!this.mergeItemStack(stackInSlot, 0, BLOCK_SLOTS, false)) {
                return null;
            }

            if (stackInSlot.stackSize == 0) {
                slotObject.putStack(null);
            } else {
                slotObject.onSlotChanged();
            }

            if (stackInSlot.stackSize == stack.stackSize) {
                return null;
            }
            slotObject.onPickupFromSlot(player, stackInSlot);
        }
        return stack;
    }

    //overriden because we need check if we realy want to merge -.-
    @Override
    protected boolean mergeItemStack(ItemStack stack, int p_75135_2_, int p_75135_3_, boolean p_75135_4_) {
        boolean flag1 = false;
        int k = p_75135_2_;

        if (p_75135_4_) {
            k = p_75135_3_ - 1;
        }

        Slot slot;
        ItemStack itemstack1;

        if (stack.isStackable()) {
            while (stack.stackSize > 0 && (!p_75135_4_ && k < p_75135_3_ || p_75135_4_ && k >= p_75135_2_)) {
                slot = (Slot) this.inventorySlots.get(k);
                itemstack1 = slot.getStack();

                if (itemstack1 != null && itemstack1.getItem() == stack.getItem() && (!stack.getHasSubtypes() || stack.getItemDamage() == itemstack1.getItemDamage()) && ItemStack.areItemStackTagsEqual(stack, itemstack1)) {
                    int l = itemstack1.stackSize + stack.stackSize;

                    if (l <= stack.getMaxStackSize()) {
                        stack.stackSize = 0;
                        itemstack1.stackSize = l;
                        slot.onSlotChanged();
                        flag1 = true;
                    } else if (itemstack1.stackSize < stack.getMaxStackSize()) {
                        stack.stackSize -= stack.getMaxStackSize() - itemstack1.stackSize;
                        itemstack1.stackSize = stack.getMaxStackSize();
                        slot.onSlotChanged();
                        flag1 = true;
                    }
                }

                if (p_75135_4_) {
                    --k;
                } else {
                    ++k;
                }
            }
        }

        if (stack.stackSize > 0) {
            if (p_75135_4_) {
                k = p_75135_3_ - 1;
            } else {
                k = p_75135_2_;
            }

            while (!p_75135_4_ && k < p_75135_3_ || p_75135_4_ && k >= p_75135_2_) {
                slot = (Slot) this.inventorySlots.get(k);
                itemstack1 = slot.getStack();
                //only this is changed
                if (itemstack1 == null && slot.isItemValid(stack)) {
                    slot.putStack(stack.copy());
                    slot.onSlotChanged();
                    stack.stackSize = 0;
                    flag1 = true;
                    break;
                }

                if (p_75135_4_) {
                    --k;
                } else {
                    ++k;
                }
            }
        }

        return flag1;
    }

    public void detectAndSendChanges() {

    }

    public static class ContainerSlot extends Slot {

        public ContainerSlot(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
            super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
        }

        @Override
        public boolean isItemValid(ItemStack itemStack) {
            return FluidContainerRegistry.isBucket(itemStack) || FluidContainerRegistry.isContainer(itemStack) || itemStack.getItem() instanceof IFluidContainerItem;
        }

        @Override
        protected void onCrafting(ItemStack p_75210_1_, int p_75210_2_) {
            System.out.println("change2");
        }
    }

    public static class ContainerDrainSlot extends Slot {

        public ContainerDrainSlot(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
            super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
        }

        @Override               //TODO add full/empty check
        public boolean isItemValid(ItemStack itemStack) {
            return FluidContainerRegistry.isBucket(itemStack) || FluidContainerRegistry.isContainer(itemStack) || itemStack.getItem() instanceof IFluidContainerItem;
        }

        @Override
        protected void onCrafting(ItemStack p_75210_1_, int p_75210_2_) {
            System.out.println("change2");
        }
    }

    public static class OutputSlot extends Slot {

        public OutputSlot(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
            super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
        }

        @Override
        public boolean isItemValid(ItemStack p_75214_1_) {
            return false;
        }
    }
}
