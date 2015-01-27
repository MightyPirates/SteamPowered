package li.cil.sp;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod(modid = SteamWaterCollector.MODID, version = SteamWaterCollector.VERSION)
public class SteamWaterCollector {
    public static final String MODID = "steamwatercollector";
    public static final String VERSION = "1.0";

    @Mod.Instance( MODID )
    public static SteamWaterCollector instance;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        Registry.init();

    }
}