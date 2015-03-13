package li.cil.sp.block;

import li.cil.sp.SteamPowered;
import li.cil.sp.tileentity.TileEntitySteamFluidTransposer;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSteamFluidTransposer extends Block {
    public BlockSteamFluidTransposer() {
        super(Material.iron);
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new TileEntitySteamFluidTransposer();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z,
                                    EntityPlayer player, int metadata, float what, float these, float are) {
        final TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity == null || player.isSneaking()) {
            return false;
        }
        player.openGui(SteamPowered.instance, 0, world, x, y, z);
        return true;
    }
}
