package li.cil.sp;

import net.minecraft.item.ItemStack;

public class Util {
    public static boolean isWrenchItem(ItemStack stack) {
        if (stack != null) {
            // TODO Check item interfaces.
            return true;
        }
        return false;
    }
}
