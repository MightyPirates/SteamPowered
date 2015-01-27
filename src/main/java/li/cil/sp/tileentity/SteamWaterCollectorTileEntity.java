package li.cil.sp.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class SteamWaterCollectorTileEntity extends TileEntity implements IFluidHandler {
    private static final int STEAM_PER_OPERATION = 2;
    private static final int WATER_PER_SOURCE = 10;
    private static final int UPDATE_DELAY = 5;

    private final FluidTank inputTank = new FluidTank(16000);
    private final FluidTank outputTank = new FluidTank(16000);

    private int ticksUntilUpdate = UPDATE_DELAY;
    private int sourceBlockCount = 0;

    public SteamWaterCollectorTileEntity() {
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        final NBTTagCompound inputNbt = new NBTTagCompound();
        inputTank.writeToNBT(inputNbt);
        nbt.setTag("inputTank", inputNbt);

        final NBTTagCompound outputNbt = new NBTTagCompound();
        outputTank.writeToNBT(outputNbt);
        nbt.setTag("outputTank", outputNbt);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        inputTank.readFromNBT(nbt.getCompoundTag("inputTank"));
        outputTank.readFromNBT(nbt.getCompoundTag("outputTank"));
    }

    public void onNeighourChange() {
        ticksUntilUpdate = UPDATE_DELAY;
    }

    private void recalculateNeighbours() {
        sourceBlockCount = 0;
        for (ForgeDirection side : ForgeDirection.VALID_DIRECTIONS) {
            final int x = xCoord + side.offsetX;
            final int y = yCoord + side.offsetY;
            final int z = zCoord + side.offsetZ;
            final Block block = worldObj.getBlock(x, y, z);
            if (block instanceof BlockLiquid && getWorldObj().getBlockMetadata(x, y, z) == 0) {
                final Fluid fluid = FluidRegistry.lookupFluidForBlock(block);
                if (fluid != null && fluid == FluidRegistry.WATER)
                    sourceBlockCount++;
            }
        }
        // TODO REMOVE THIS
        System.out.println(sourceBlockCount);
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

        if (ticksUntilUpdate > 0) {
            --ticksUntilUpdate;
            if (ticksUntilUpdate == 0) {
                recalculateNeighbours();
            }
        }

        if (sourceBlockCount > 0 && // Anything to pull from?
                inputTank.getFluidAmount() >= STEAM_PER_OPERATION && // Any power to use?
                outputTank.getFluidAmount() < outputTank.getCapacity()) // Any room to put it?
        {
            inputTank.drain(STEAM_PER_OPERATION, true);
            outputTank.fill(new FluidStack(FluidRegistry.WATER, WATER_PER_SOURCE * sourceBlockCount), true);

            // TODO REMOVE THIS
            System.out.println(outputTank.getFluidAmount());
        }
    }

    public int getWaterAmount() {
        return outputTank.getFluidAmount();
    }

    public int getSteamAmount() {
        return inputTank.getFluidAmount();
    }

    @Override
    public int fill(ForgeDirection from, FluidStack stack, boolean doFill) {
        if (canFill(from, stack.getFluid()))
            return inputTank.fill(stack, doFill);
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack stack, boolean doDrain) {
        return outputTank.drain(stack.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return outputTank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return fluid == FluidRegistry.getFluid("steam");
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return outputTank.getFluid().getFluid() == fluid;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[]{outputTank.getInfo(), inputTank.getInfo()};
    }
}
