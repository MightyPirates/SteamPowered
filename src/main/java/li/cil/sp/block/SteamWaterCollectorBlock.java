package li.cil.sp.block;

import li.cil.sp.SteamPowered;
import li.cil.sp.tileentity.SteamWaterCollectorTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class SteamWaterCollectorBlock extends Block {
    public SteamWaterCollectorBlock(final Material material) {
        super(material);
    }

    @Override
    public boolean hasTileEntity(final int metadata) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(final World world, final int metadata) {
        return new SteamWaterCollectorTileEntity();
    }

    @Override
    public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer player, final int side, final float hitX, final float htY, final float hitZ) {
        final TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity == null || player.isSneaking()) {
            return false;
        }
        player.openGui(SteamPowered.instance, 0, world, x, y, z);
        return true;
    }

    @Override
    public void onNeighborBlockChange(final World world, final int x, final int y, final int z, final Block block) {
        super.onNeighborBlockChange(world, x, y, z, block);
        final TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity != null && tileEntity instanceof SteamWaterCollectorTileEntity) {
            ((SteamWaterCollectorTileEntity) tileEntity).onNeighourChange();
        }
    }
}
