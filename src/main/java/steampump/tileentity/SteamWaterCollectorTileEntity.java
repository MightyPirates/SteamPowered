package steampump.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

import java.util.Map;

public class SteamWaterCollectorTileEntity extends TileEntity implements IFluidHandler, IInventory {

    FluidTank inputTank;
    FluidTank outputTank;
    private static final int workAmount = 2;
    private static final int productionAmount = 10;

    public final int getOffsetX(byte aSide) {
        return this.xCoord + ForgeDirection.getOrientation(aSide).offsetX;
    }

    public final short getOffsetY(byte aSide) {
        return (short) (this.yCoord + ForgeDirection.getOrientation(aSide).offsetY);
    }

    public final int getOffsetZ(byte aSide) {
        return this.zCoord + ForgeDirection.getOrientation(aSide).offsetZ;
    }

    public SteamWaterCollectorTileEntity() {
        inputTank = new FluidTank(16000);
        outputTank = new FluidTank(16000);


    }


    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        inputTank.writeToNBT(nbtTagCompound);
        outputTank.writeToNBT(nbtTagCompound);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        inputTank.readFromNBT(nbtTagCompound);
        outputTank.readFromNBT(nbtTagCompound);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (outputTank.getFluidAmount() > 0) {
            TileEntity tileEntity = worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);
            if (tileEntity != null && tileEntity instanceof IFluidHandler) {
                IFluidHandler handler = (IFluidHandler) tileEntity;
                if (handler.canFill(ForgeDirection.UP, outputTank.getFluid().getFluid())) {
                    int amount = handler.fill(ForgeDirection.UP, outputTank.getFluid(), false);
                    if (amount > 0) {
                        outputTank.drain(handler.fill(ForgeDirection.UP, outputTank.getFluid(), true), true);
                    }
                }

            }
        }

        if ((outputTank.getCapacity() - outputTank.getFluidAmount()) < productionAmount) {
            return;
        }

        if (inputTank.getFluidAmount() < workAmount) {
            return;
        }
        int sourceBlocks = 0;
        for (byte side = 0; side < 7; side++) {
            Block block = worldObj.getBlock(getOffsetX(side), getOffsetY(side), getOffsetZ(side));
            if (block != null)
                if (block instanceof BlockLiquid)
                    if (worldObj.getBlockMetadata(getOffsetX(side), getOffsetY(side), getOffsetZ(side)) == 0) {
                        Fluid fluid = FluidRegistry.lookupFluidForBlock(block);
                        if (fluid != null && fluid.equals(FluidRegistry.WATER)) {
                            sourceBlocks++;
                        }
                    }
        }
        if (sourceBlocks == 0)
            return;
        inputTank.drain(workAmount, true);
        outputTank.fill(new FluidStack(FluidRegistry.WATER.getID(), productionAmount * workAmount), true);
        System.out.println(outputTank.getInfo().fluid.amount);
    }

    public int getWaterAmount() {
        return outputTank.getFluidAmount();
    }
    public int getSteamAmount() {
        return inputTank.getFluidAmount();
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (canFill(from, resource.getFluid()))
            return inputTank.fill(resource, doFill);
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return outputTank.drain(resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return outputTank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return fluid.equals(FluidRegistry.getFluid("steam"));
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return outputTank.getFluid().fluidID == fluid.getID();
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[]{outputTank.getInfo(), inputTank.getInfo()};
    }

    @Override
    public int getSizeInventory() {
        return 0;
    }

    @Override
    public ItemStack getStackInSlot(int p_70301_1_) {
        return new ItemStack(Items.diamond);
    }

    @Override
    public ItemStack decrStackSize(int p_70298_1_, int p_70298_2_) {
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int p_70304_1_) {
        return null;
    }

    @Override
    public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {

    }

    @Override
    public String getInventoryName() {
        return null;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 0;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this &&
                player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
    }


    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
        return false;
    }
}
