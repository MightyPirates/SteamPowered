package li.cil.sp.proxy;

import li.cil.sp.Registry;

public class Common {
    public void onPreInit() {
    }

    public void onInit() {
        Registry.init();
    }

    public void onPostInit() {
    }
}
