package li.cil.sp.proxy;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.registry.GameRegistry;
import li.cil.sp.Registry;
import li.cil.sp.tileentity.TileEntitySteamFluidTransposer;
import li.cil.sp.tileentity.TileEntitySteamWaterCollector;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Common {
    public void onPreInit() {
        GameRegistry.registerTileEntity(TileEntitySteamWaterCollector.class, "steamWaterCollector");
        GameRegistry.registerBlock(Registry.STEAM_WATER_COLLECTOR, "steamWaterCollector");

        GameRegistry.registerTileEntity(TileEntitySteamFluidTransposer.class, "steamFluidTransposer");
        GameRegistry.registerBlock(Registry.STEAM_FLUID_TRANSPOSER, "steamFluidTransposer");
    }

    public void onInit() {
        if (Loader.isModLoaded("gregtech")) {
            GameRegistry.addShapedRecipe(new ItemStack(Registry.STEAM_WATER_COLLECTOR),
                    "pcp",
                    "chc",
                    "pcp",
                    'p', Blocks.piston,
                    'c', new ItemStack((Item) Item.itemRegistry.getObject("gregtech:gt.blockmachines"), 1, 5121),
                    'h', new ItemStack((Item) Item.itemRegistry.getObject("gregtech:gt.blockmachines"), 1, 2));
        } else {
            GameRegistry.addShapedRecipe(new ItemStack(Registry.STEAM_WATER_COLLECTOR),
                    "bib",
                    "pcp",
                    "bpb",
                    'b', Items.brick,
                    'i', Blocks.iron_bars,
                    'p', Blocks.piston,
                    'c', Items.cauldron);
        }
    }

    public void onPostInit() {
    }
}
