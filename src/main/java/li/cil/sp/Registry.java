package li.cil.sp;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import li.cil.sp.block.BlockSteamWaterCollector;
import li.cil.sp.block.BlockSteamFluidTransposer;
import li.cil.sp.gui.GuiHandler;
import li.cil.sp.tileentity.TileEntitySteamFluidTransposer;
import li.cil.sp.tileentity.TileEntitySteamWaterCollector;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class Registry {
    public static final CreativeTabs tab = new CreativeTabs(CreativeTabs.getNextID(), "Steam Water Collector") {
        @Override
        public Item getTabIconItem() {
            return Items.brick;
        }
    };

    public static final Block STEAM_WATER_COLLECTOR = new BlockSteamWaterCollector(Material.iron)
            .setHardness(5f)
            .setStepSound(Block.soundTypeMetal)
            .setBlockName("steamWaterCollector")
            .setBlockTextureName("steampowered:generic")
            .setCreativeTab(tab);

    public static final Block STEAM_FLUID_TRANSPOSER = new BlockSteamFluidTransposer()
            .setHardness(5f)
            .setStepSound(Block.soundTypeMetal)
            .setBlockName("steamFluidTransposer")
            .setBlockTextureName("steampowered:generic")
            .setCreativeTab(tab);

    public static void init() {
        GameRegistry.registerTileEntity(TileEntitySteamWaterCollector.class, "steamWaterCollector");
        GameRegistry.registerBlock(STEAM_WATER_COLLECTOR, "steamWaterCollector");

        GameRegistry.registerTileEntity(TileEntitySteamFluidTransposer.class, "steamFluidTransposer");
        GameRegistry.registerBlock(STEAM_FLUID_TRANSPOSER, "steamFluidTransposer");

        NetworkRegistry.INSTANCE.registerGuiHandler(SteamPowered.instance, new GuiHandler());
    }
}
