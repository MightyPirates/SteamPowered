package li.cil.sp;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import li.cil.sp.block.SteamWaterCollectorBlock;
import li.cil.sp.gui.GuiHandler;
import li.cil.sp.tileentity.SteamWaterCollectorTileEntity;
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

    public static final Block steamWaterCollector = new SteamWaterCollectorBlock(Material.iron)
            .setHardness(5f)
            .setStepSound(Block.soundTypeMetal)
            .setBlockName("steamWaterCollector")
            .setCreativeTab(tab);

    public static void init() {
        GameRegistry.registerTileEntity(SteamWaterCollectorTileEntity.class, "steamWaterCollector");
        GameRegistry.registerBlock(steamWaterCollector, "steamWaterCollector");

        NetworkRegistry.INSTANCE.registerGuiHandler(SteamPowered.instance, new GuiHandler());
    }
}
