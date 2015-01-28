package li.cil.sp.proxy;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import li.cil.sp.Registry;
import li.cil.sp.SteamPowered;
import li.cil.sp.container.SteamWaterCollectorContainer;
import li.cil.sp.tileentity.SteamWaterCollectorTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class Common implements IGuiHandler {
    public void onPreInit() {
    }

    public void onInit() {
        Registry.init();

        NetworkRegistry.INSTANCE.registerGuiHandler(SteamPowered.instance, this);
    }

    public void onPostInit() {
    }

    // IGuiHandler

    @Override
    public Object getServerGuiElement(final int id, final EntityPlayer player, final World world, final int x, final int y, final int z) {
        final TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof SteamWaterCollectorTileEntity) {
            return new SteamWaterCollectorContainer(player.inventory, (SteamWaterCollectorTileEntity) tileEntity);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(final int id, final EntityPlayer player, final World world, final int x, final int y, final int z) {
        return null;
    }
}
