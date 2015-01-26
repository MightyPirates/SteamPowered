package steampump.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import steampump.SteamWaterCollector;
import steampump.tileentity.SteamWaterCollectorTileEntity;

public class SteamWaterCollectorBlock extends Block {
    public SteamWaterCollectorBlock(Material material) {
        super(material);
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new SteamWaterCollectorTileEntity();
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z,
                                    EntityPlayer player, int metadata, float what, float these, float are) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity == null || player.isSneaking()) {
            return false;
        }
        //code to open gui explained later
        player.openGui(SteamWaterCollector.instance, 0, world, x, y, z);
        return true;
    }

}
