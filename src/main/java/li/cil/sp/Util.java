package li.cil.sp;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class Util {
    private final static Set<String> WRENCH_CLASSES;

    static {
        final Set<String> wrenchClasses = new HashSet<String>();
        wrenchClasses.add("buildcraft.api.tools.IToolWrench");
        wrenchClasses.add("cofh.api.item.IToolHammer");
        wrenchClasses.add("crazypants.enderio.api.tool.ITool");
        wrenchClasses.add("crazypants.enderio.tool.ITool");
        wrenchClasses.add("ic2.core.item.tool.ItemToolWrench");
        wrenchClasses.add("mods.railcraft.api.core.items.IToolCrowbar");
        WRENCH_CLASSES = wrenchClasses;
    }

    public static boolean isWrenchItem(ItemStack stack) {
        if (stack != null && stack.getItem() != null) {
            final Item item = stack.getItem();
            if (WRENCH_CLASSES.contains(item.getClass().getName())) return true;
            final Class<?>[] interfaces = item.getClass().getInterfaces();
            for (Class<?> iface : interfaces) {
                if (WRENCH_CLASSES.contains(iface.getName())) return true;
            }
        }
        return false;
    }
}
