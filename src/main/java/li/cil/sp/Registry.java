package li.cil.sp;

import li.cil.sp.block.BlockSteamFluidTransposer;
import li.cil.sp.block.BlockSteamWaterCollector;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class Registry {
    public static final CreativeTabs tab = new CreativeTabs(CreativeTabs.getNextID(), "SteamPowered") {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(STEAM_WATER_COLLECTOR);
        }
    };

    public static final Block STEAM_WATER_COLLECTOR = new BlockSteamWaterCollector(Material.iron)
            .setHardness(5f)
            .setStepSound(Block.soundTypeMetal)
            .setBlockName("steamWaterCollector")
            .setCreativeTab(tab);

    public static final Block STEAM_FLUID_TRANSPOSER = new BlockSteamFluidTransposer()
            .setHardness(5f)
            .setStepSound(Block.soundTypeMetal)
            .setBlockName("steamFluidTransposer")
            .setBlockTextureName("steampowered:generic")
            .setCreativeTab(tab);
}
