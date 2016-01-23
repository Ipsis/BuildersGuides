package ipsis.buildersguides.plugins.waila;

import ipsis.buildersguides.manager.MarkerManager;
import ipsis.buildersguides.manager.MarkerType;
import ipsis.buildersguides.reference.Names;
import ipsis.buildersguides.tileentity.TileEntityMarker;
import ipsis.buildersguides.util.StringHelper;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import java.util.List;

public class WailaDataProviderBG implements IWailaDataProvider {

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

        if (accessor.getTileEntity() instanceof TileEntityMarker) {
            MarkerType t = ((TileEntityMarker)accessor.getTileEntity()).getType();
            if (t != MarkerType.BLANK) {
                currenttip.add(EnumChatFormatting.RED + String.format("%s: %s", StringHelper.localize(Names.NAME, Names.TYPE), t.getTranslatedType()));

                String mode = MarkerManager.getMode((TileEntityMarker) accessor.getTileEntity());
                if (!mode.equals(""))
                    currenttip.add(EnumChatFormatting.YELLOW + String.format("%s: %s", StringHelper.localize(Names.NAME, Names.MODE), mode));
            }

            //currenttip.add(((TileEntityMarker) accessor.getTileEntity()).getFacing().toString());
        }

        /*
        if (accessor.getPlayer().isSneaking()) {
            TileEntityMarker tem = (TileEntityMarker)accessor.getTileEntity();
            for (EnumFacing f : EnumFacing.values()) {
                if (tem.hasValidV(f))
                    currenttip.add(f.toString() + ":" + tem.getV(f));
            }
        } */

        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
        /* Already synching the relevant data via description packet */
        return tag;
    }

    public static void callbackRegister(IWailaRegistrar registrar) {

        registrar.registerBodyProvider(new WailaDataProviderBG(), TileEntityMarker.class);
    }
}
