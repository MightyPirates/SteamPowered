package li.cil.sp.gui;


import cpw.mods.fml.common.network.IGuiHandler;
import li.cil.sp.container.ContainerSteamFluidTransposer;
import li.cil.sp.tileentity.TileEntitySteamFluidTransposer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class GuiHandlerCommon implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        final TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof TileEntitySteamFluidTransposer) {
            return new ContainerSteamFluidTransposer(player.inventory, (TileEntitySteamFluidTransposer) tileEntity);
        }
        return null;
    }
}
