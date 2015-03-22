package li.cil.sp.block;

import li.cil.sp.tileentity.TileEntitySteamPowered;
import li.cil.sp.tileentity.TileEntitySteamWaterCollector;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.List;

public class BlockDataProvider implements IWailaDataProvider {

    public static final String STEAMPUMP_SHOWBODY = "steampump.showbody";

    public static void callbackRegister(IWailaRegistrar registrar) {
        registrar.addConfig("Steam Pump", STEAMPUMP_SHOWBODY, "Show Body");
        registrar.registerHeadProvider(new BlockDataProvider(), BlockSteamWaterCollector.class);
        registrar.registerBodyProvider(new BlockDataProvider(), BlockSteamWaterCollector.class);
        registrar.registerNBTProvider(new BlockDataProvider(),BlockSteamWaterCollector.class);
    }

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {

        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        if (!config.getConfig(STEAMPUMP_SHOWBODY)) {
            return currenttip;
        }
        TileEntity te = accessor.getTileEntity();
        if(te instanceof TileEntitySteamPowered) {

            currenttip.add("Steam: " + accessor.getNBTData().getInteger("steam"));
        }
        if(te instanceof TileEntitySteamWaterCollector) {

            currenttip.add("Water: " + accessor.getNBTData().getInteger("water"));
            currenttip.add("Possible water production: " + accessor.getNBTData().getInteger("watertick")+"l/tick");
        }
        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x, int y, int z) {
        if(te instanceof TileEntitySteamPowered) {
            TileEntitySteamPowered steamPowered = ((TileEntitySteamPowered) te);
            tag.setInteger("steam",steamPowered.getSteamAmount());
        }
        if(te instanceof TileEntitySteamWaterCollector) {
            TileEntitySteamWaterCollector waterCollector = ((TileEntitySteamWaterCollector) te);
            tag.setInteger("water",waterCollector.getWaterAmount());
            tag.setInteger("watertick",waterCollector.getWaterPerTick());

        }
        return tag;
    }
}
