package li.cil.sp.block;

import li.cil.sp.Util;
import li.cil.sp.tileentity.TileEntitySteamWaterCollector;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSteamWaterCollector extends Block {
    public BlockSteamWaterCollector(final Material material) {
        super(material);
    }

    @Override
    public boolean hasTileEntity(final int metadata) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(final World world, final int metadata) {
        return new TileEntitySteamWaterCollector();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if (Util.isWrenchItem(player.getHeldItem())) {
            final TileEntity tileEntity = world.getTileEntity(x, y, z);
            if (tileEntity instanceof TileEntitySteamWaterCollector) {
                ((TileEntitySteamWaterCollector) tileEntity).updateFacing(side);
            }
            return true;
        } else return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
    }

    @Override
    public void onNeighborBlockChange(final World world, final int x, final int y, final int z, final Block block) {
        super.onNeighborBlockChange(world, x, y, z, block);
        final TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity != null && tileEntity instanceof TileEntitySteamWaterCollector) {
            ((TileEntitySteamWaterCollector) tileEntity).onNeighborBlockChange();
        }
    }
}
