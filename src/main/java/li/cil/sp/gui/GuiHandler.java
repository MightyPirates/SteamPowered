package li.cil.sp.gui;


import cpw.mods.fml.common.network.IGuiHandler;
import li.cil.sp.container.SteamWaterCollectorContainer;
import li.cil.sp.tileentity.SteamWaterCollectorTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof SteamWaterCollectorTileEntity) {
            return new SteamWaterCollectorContainer(player.inventory, (SteamWaterCollectorTileEntity) tileEntity);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity instanceof SteamWaterCollectorTileEntity) {
            return new SteamWaterCollectorGui(player.inventory, (SteamWaterCollectorTileEntity) tileEntity);
        }
        return null;
    }
}
