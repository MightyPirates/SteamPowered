package li.cil.sp.proxy;

import cpw.mods.fml.common.network.NetworkRegistry;
import li.cil.sp.SteamPowered;
import li.cil.sp.gui.GuiHandlerServer;

public class Server extends Common {
    @Override
    public void onInit() {
        super.onInit();

        NetworkRegistry.INSTANCE.registerGuiHandler(SteamPowered.instance, new GuiHandlerServer());
    }
}
