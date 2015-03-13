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

public class TileEntitySteamWaterCollector extends TileEntity implements IFluidHandler {
    private static final int STEAM_PER_OPERATION = 2;
    private static final int WATER_PER_SOURCE = 10;
    private static final int UPDATE_DELAY = 10;

    private final FluidTank inputTank = new FluidTank(16000);
    private final FluidTank outputTank = new FluidTank(16000);

    private ForgeDirection outputSide = ForgeDirection.DOWN;
    private int ticksUntilUpdate = UPDATE_DELAY;
    private int sourceBlockCount = 0;

    public void updateFacing(int side) {
        final ForgeDirection direction = ForgeDirection.getOrientation(side);
        if (direction == outputSide) {
            outputSide = outputSide.getOpposite();
        } else {
            outputSide = direction;
        }
    }

    @Override
    public void writeToNBT(final NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        final NBTTagCompound inputNbt = new NBTTagCompound();
        inputTank.writeToNBT(inputNbt);
        nbt.setTag("inputTank", inputNbt);

        final NBTTagCompound outputNbt = new NBTTagCompound();
        outputTank.writeToNBT(outputNbt);
        nbt.setTag("outputTank", outputNbt);

        nbt.setByte("outputSide", (byte) outputSide.ordinal());
    }

    @Override
    public void readFromNBT(final NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        inputTank.readFromNBT(nbt.getCompoundTag("inputTank"));
        outputTank.readFromNBT(nbt.getCompoundTag("outputTank"));
        outputSide = ForgeDirection.getOrientation(nbt.getByte("outputSide"));
    }

    public void onNeighborBlockChange() {
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
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (outputTank.getFluidAmount() > 0) {
            final TileEntity tileEntity = worldObj.getTileEntity(xCoord + outputSide.offsetX, yCoord + outputSide.offsetY, zCoord + outputSide.offsetZ);
            if (tileEntity != null && tileEntity instanceof IFluidHandler) {
                final IFluidHandler handler = (IFluidHandler) tileEntity;
                if (handler.canFill(outputSide.getOpposite(), outputTank.getFluid().getFluid())) {
                    int amount = handler.fill(outputSide.getOpposite(), outputTank.getFluid(), false);
                    if (amount > 0) {
                        outputTank.drain(handler.fill(outputSide.getOpposite(), outputTank.getFluid(), true), true);
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
                outputTank.getFluidAmount() + WATER_PER_SOURCE < outputTank.getCapacity()) // Any room to put it?
        {
            inputTank.drain(STEAM_PER_OPERATION, true);
            outputTank.fill(new FluidStack(FluidRegistry.WATER, WATER_PER_SOURCE * sourceBlockCount), true);
        }
    }

    @Override
    public int fill(final ForgeDirection side, final FluidStack stack, final boolean doFill) {
        if (canFill(side, stack.getFluid()))
            return inputTank.fill(stack, doFill);
        return 0;
    }

    @Override
    public FluidStack drain(final ForgeDirection side, final FluidStack stack, final boolean doDrain) {
        return outputTank.drain(stack.amount, doDrain);
    }

    @Override
    public FluidStack drain(final ForgeDirection side, final int maxAmount, final boolean doDrain) {
        return outputTank.drain(maxAmount, doDrain);
    }

    @Override
    public boolean canFill(final ForgeDirection side, final Fluid fluid) {
        return fluid == FluidRegistry.getFluid("steam");
    }

    @Override
    public boolean canDrain(final ForgeDirection side, final Fluid fluid) {
        return outputTank.getFluid().getFluid() == fluid;
    }

    @Override
    public FluidTankInfo[] getTankInfo(final ForgeDirection side) {
        return new FluidTankInfo[]{outputTank.getInfo(), inputTank.getInfo()};
    }
}
