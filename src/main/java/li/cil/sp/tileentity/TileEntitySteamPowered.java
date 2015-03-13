package li.cil.sp.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public abstract class TileEntitySteamPowered extends TileEntity implements IFluidHandler {
    protected final FluidTank steamTank = new FluidTank(16000);

    public int getSteamAmount() {
        return steamTank.getFluidAmount();
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        final NBTTagCompound inputNbt = new NBTTagCompound();
        steamTank.writeToNBT(inputNbt);
        nbt.setTag("steamTank", inputNbt);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        steamTank.readFromNBT(nbt.getCompoundTag("steamTank"));
    }

    @Override
    public int fill(ForgeDirection from, FluidStack stack, boolean doFill) {
        if (canFill(from, stack.getFluid()))
            return steamTank.fill(stack, doFill);
        return 0;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return fluid == FluidRegistry.getFluid("steam");
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[]{steamTank.getInfo()};
    }
}
