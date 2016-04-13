package ipsis.buildersguides.client;

import ipsis.buildersguides.init.ModBlocks;
import ipsis.buildersguides.util.BlockUtils;
import ipsis.oss.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static net.minecraft.client.renderer.RenderGlobal.drawSelectionBoundingBox;

public class DrawBlockHighlightEventHandler {

    @SubscribeEvent
    public void onDrawBlockHighlightEvent(DrawBlockHighlightEvent event) {

        ItemStack held = event.getPlayer().getHeldItemMainhand();
        if (held == null || Item.getItemFromBlock(ModBlocks.blockMarker) != held.getItem())
            return;

        EntityPlayer entityPlayer = event.getPlayer();
        World world = event.getPlayer().getEntityWorld();
        BlockPos blockpos = BlockUtils.getSelectedBlock(entityPlayer);
        if (blockpos == null)
            return;

        /**
         * Dont highlight if we are actually targetting a non-air block
         */
        IBlockState state = world.getBlockState(event.getTarget().getBlockPos());
        if (!state.getBlock().isAir(state, world, event.getTarget().getBlockPos()))
            return;

        /**
         * Vanilla block outline code
         * But works on air
         */
        GlStateManager.disableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(0.0F, 0.0F, 0.0F, 0.4F);
        GlStateManager.glLineWidth(2.0F);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);
        IBlockState iblockstate = world.getBlockState(blockpos);

        if (world.getWorldBorder().contains(blockpos))
        {
            double d0 = entityPlayer.lastTickPosX + (entityPlayer.posX - entityPlayer.lastTickPosX) * (double)event.getPartialTicks();
            double d1 = entityPlayer.lastTickPosY + (entityPlayer.posY - entityPlayer.lastTickPosY) * (double)event.getPartialTicks();
            double d2 = entityPlayer.lastTickPosZ + (entityPlayer.posZ - entityPlayer.lastTickPosZ) * (double)event.getPartialTicks();
            drawSelectionBoundingBox(iblockstate.getSelectedBoundingBox(world, blockpos).expandXyz(0.0020000000949949026D).offset(-d0, -d1, -d2));
        }

        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();

        event.setCanceled(true);
    }
}
