package li.cil.sp.gui;

import li.cil.sp.tileentity.TileEntitySteamFluidTransposer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandlerClient extends GuiHandlerCommon {
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        final TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof TileEntitySteamFluidTransposer) {
            return new SteamFluidTransposerGui(player.inventory, (TileEntitySteamFluidTransposer) tileEntity);
        }
        return null;
    }
}
