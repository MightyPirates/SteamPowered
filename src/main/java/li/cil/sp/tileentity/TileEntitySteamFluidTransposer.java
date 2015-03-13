package li.cil.sp.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public class TileEntitySteamFluidTransposer extends TileEntitySteamPowered implements ISidedInventory {


    //Two slots for input two for output. One input slot for empty/one for filled canisters. Same for output.  (0 and 2 being input/ 1 and 3 the respective output slots)
    private ItemStack[] stacks = new ItemStack[4];

    private FluidTank fluidTank = new FluidTank(16000);

    private ForgeDirection steamInput = ForgeDirection.UP;

    private int workTimeFill = -1;
    private int workTimeDrain = -1;
    private int STEAM_PER_OPERATION = 2;
    private int AMOUNT_PER_OPERATION = 25;

    @Override
    public void updateEntity() {
        super.updateEntity();
        //fill container
        //not working
        if (steamTank.getFluidAmount() < STEAM_PER_OPERATION) {
            return;
        }
        //check if input is valid and can be used to fill tank
        ItemStack resource = stacks[0];
        if (resource != null) {

            if (resource.getItem() instanceof IFluidContainerItem) {
                if (fluidTank.getFluidAmount() > AMOUNT_PER_OPERATION) {
                    IFluidContainerItem fluidContainerItem = ((IFluidContainerItem) resource.getItem());
                    int drainAmount;
                    if ((drainAmount = fluidContainerItem.fill(resource, fluidTank.drain(AMOUNT_PER_OPERATION, false), false)) > 0) {
                        //
                        steamTank.drain(AMOUNT_PER_OPERATION,true);
                        fluidContainerItem.fill(resource,fluidTank.drain(drainAmount,true),true);

                    }

                }
            } else if (FluidContainerRegistry.isContainer(resource)) {

                if (FluidContainerRegistry.isEmptyContainer(resource)) {

                    if (fluidTank.getFluid() != null) {

                        //TODO start working, remember what fluid to save and only work with this fluid
                    } else {
                        return;
                    }

                } else {
                    //shouldn't happen
                }
            }
        }


        //drain container
        resource = stacks[2];
        if (resource != null) {

        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("workTimeFill", workTimeFill);
        if (stacks[0] != null) {
            final NBTTagCompound inputNbt = new NBTTagCompound();
            stacks[0].writeToNBT(inputNbt);
            nbt.setTag("inputStack", inputNbt);
        }
        if (stacks[1] != null) {
            final NBTTagCompound outputNBT = new NBTTagCompound();
            stacks[1].writeToNBT(outputNBT);
            nbt.setTag("outputStack", outputNBT);
        }
        fluidTank.writeToNBT(nbt);
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        workTimeFill = tagCompound.getInteger("workTimeFill");
        if (tagCompound.hasKey("inputStack"))
            stacks[0] = ItemStack.loadItemStackFromNBT(tagCompound.getCompoundTag("inputStack"));
        else
            //not sure if this is necessary...
            stacks[0] = null;
        if (tagCompound.hasKey("inputStack"))
            stacks[1] = ItemStack.loadItemStackFromNBT(tagCompound.getCompoundTag("outputStack"));
        else
            //not sure if this is necessary...
            stacks[1] = null;
        fluidTank.readFromNBT(tagCompound);
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[]{0, 1,2,3};
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack item, int side) {
        return isItemValidForSlot(slot, item);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack item, int side) {
        return slot == 1||slot ==3;//TODO maybe more checks?
    }

    @Override
    public int getSizeInventory() {
        return 2;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return stacks[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        ItemStack stack = stacks[slot];
        if (stack == null)
            return null;
        if (stack.stackSize <= amount) {
            stacks[slot] = null;
            return stack;
        }
        return stack.splitStack(amount);

    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack item) {
        stacks[slot] = item;
    }

    @Override
    public String getInventoryName() {
        return "Transposing";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {

        if (stacks[slot] != null)
            return stacks[slot].isItemEqual(item);

        return slot != 1 && (FluidContainerRegistry.isBucket(item) || FluidContainerRegistry.isContainer(item) || item.getItem() instanceof IFluidContainerItem);


    }

    ////////////////////////////////////////////////
    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        //TODO allow steam only from top? maybe make this dynamic?
        if (from == steamInput) {
            return super.fill(from, resource, doFill);
        }
        return fluidTank.fill(resource, doFill);
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return fluidTank.drain(resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return fluidTank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {

        //TODO allow steam only from top? maybe make this dynamic?
        if (from == steamInput)
            return super.canFill(from, fluid);
        return fluidTank.getFluid() == null || fluidTank.getFluid().getFluid() == fluid;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return fluidTank.getFluid() != null && fluidTank.getFluid().getFluid() == fluid;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[]{fluidTank.getInfo(), steamTank.getInfo()};
    }
}
