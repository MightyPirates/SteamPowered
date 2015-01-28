package li.cil.sp.proxy;

import li.cil.sp.gui.SteamWaterCollectorGui;
import li.cil.sp.tileentity.SteamWaterCollectorTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class Client extends Common {
    // IGuiHandler

    @Override
    public Object getClientGuiElement(final int id, final EntityPlayer player, final World world, final int x, final int y, final int z) {
        final TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof SteamWaterCollectorTileEntity) {
            return new SteamWaterCollectorGui(player.inventory, (SteamWaterCollectorTileEntity) tileEntity);
        }
        return null;
    }
}
