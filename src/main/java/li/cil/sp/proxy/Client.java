package li.cil.sp.proxy;

import cpw.mods.fml.common.network.NetworkRegistry;
import li.cil.sp.SteamPowered;
import li.cil.sp.gui.GuiHandlerClient;

public class Client extends Common {
    @Override
    public void onInit() {
        super.onInit();

        NetworkRegistry.INSTANCE.registerGuiHandler(SteamPowered.instance, new GuiHandlerClient());
    }
}
