package li.cil.sp;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod(modid = SteamPowered.MOD_ID, version = SteamPowered.VERSION, name = "Steam Powered")
public class SteamPowered {
    public static final String MOD_ID = "steampowered";
    public static final String VERSION = "1.0.0";

    @Mod.Instance(MOD_ID)
    public static SteamPowered instance;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        Registry.init();
    }
}
