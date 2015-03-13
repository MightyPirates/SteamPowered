package li.cil.sp;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import li.cil.sp.proxy.Common;

@Mod(modid = SteamPowered.MOD_ID, version = SteamPowered.VERSION, name = SteamPowered.MOD_NAME)
public class SteamPowered {
    public static final String MOD_ID = "steampowered";
    public static final String MOD_NAME = "SteamPowered";
    public static final String VERSION = "@VERSION@";

    @Mod.Instance
    public static SteamPowered instance;

    @SidedProxy(clientSide = "li.cil.sp.proxy.Client", serverSide = "li.cil.sp.proxy.Server")
    public static Common proxy;

    @Mod.EventHandler
    public void onPreInit(final FMLPreInitializationEvent event) {
        proxy.onPreInit();
    }

    @Mod.EventHandler
    public void onInit(final FMLInitializationEvent event) {
        proxy.onInit();
    }

    @Mod.EventHandler
    public void onPostInit(final FMLPostInitializationEvent event) {
        proxy.onPostInit();
    }
}
