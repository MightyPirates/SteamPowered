package li.cil.sp.block;

import li.cil.sp.SteamPowered;
import li.cil.sp.Util;
import li.cil.sp.tileentity.TileEntitySteamWaterCollector;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockSteamWaterCollector extends Block {
    private IIcon iconTop = null;
    private IIcon icontTopOutput = null;
    private IIcon iconSide = null;
    private IIcon iconSideOutput = null;

    public BlockSteamWaterCollector(final Material material) {
        super(material);
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        final TileEntity tileEntity = world.getTileEntity(x, y, z);
        final boolean isOutputSide = tileEntity instanceof TileEntitySteamWaterCollector && ((TileEntitySteamWaterCollector) tileEntity).getOutputSide().ordinal() == side;
        final ForgeDirection direction = ForgeDirection.getOrientation(side);
        switch (direction) {
            case DOWN:
            case UP:
                if (isOutputSide) return icontTopOutput;
                else return iconTop;
            default:
                if (isOutputSide) return iconSideOutput;
                else return iconSide;
        }
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        final ForgeDirection direction = ForgeDirection.getOrientation(side);
        switch (direction) {
            case DOWN:
            case UP:
                return iconTop;
            default:
                if (side == ForgeDirection.SOUTH.ordinal()) return iconSideOutput;
                else return iconSide;
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister registry) {
        iconTop = registry.registerIcon(SteamPowered.MOD_ID.toLowerCase() + ":collector_top");
        icontTopOutput = registry.registerIcon(SteamPowered.MOD_ID.toLowerCase() + ":collector_top_output");
        iconSide = registry.registerIcon(SteamPowered.MOD_ID.toLowerCase() + ":collector_side");
        iconSideOutput = registry.registerIcon(SteamPowered.MOD_ID.toLowerCase() + ":collector_side_output");
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
