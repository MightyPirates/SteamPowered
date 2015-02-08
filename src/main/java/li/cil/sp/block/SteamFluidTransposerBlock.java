package li.cil.sp.block;

import li.cil.sp.SteamPowered;
import li.cil.sp.tileentity.SteamFluidTransposerTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class SteamFluidTransposerBlock extends Block {
    public SteamFluidTransposerBlock() {
        super(Material.iron);
    }

    @Override
    public boolean hasTileEntity(int metadata) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, int metadata) {
        return new SteamFluidTransposerTileEntity();
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z,
                                    EntityPlayer player, int metadata, float what, float these, float are) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (tileEntity == null || player.isSneaking()) {
            return false;
        }
        //code to open gui explained later
        player.openGui(SteamPowered.instance, 0, world, x, y, z);
        return true;
    }


}
