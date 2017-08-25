package com.nhave.tow.client.integration;

import java.util.Set;

import org.lwjgl.opengl.GL11;

import com.nhave.tow.integration.handlers.RFToolsHandler;
import com.nhave.tow.items.ItemOmniwrench;
import com.nhave.tow.registry.ModIntegration;
import com.nhave.tow.registry.ModItems;

import mcjty.lib.gui.RenderGlowEffect;
import mcjty.lib.varia.GlobalCoordinate;
import mcjty.rftools.RFTools;
import mcjty.rftools.blocks.blockprotector.BlockProtectorTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RFToolsClientEventHandler
{
	@SubscribeEvent
    public void renderWorldLastEvent(RenderWorldLastEvent evt)
	{
		renderProtectedBlocks(evt);
    }
	
	private static void renderProtectedBlocks(RenderWorldLastEvent evt)
	{
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP p = mc.player;
        ItemStack heldItem = p.getHeldItem(EnumHand.MAIN_HAND);
        if (heldItem.isEmpty())
        {
            return;
        }
        if (heldItem.getItem() == ModItems.itemOmniWrench)
        {
            if (((ItemOmniwrench) heldItem.getItem()).getWrenchMode(heldItem) == ModIntegration.modeRFTools)
            {
                GlobalCoordinate current = RFToolsHandler.getCurrentBlock(heldItem);
                if (current != null)
                {
                    if (current.getDimension() == mc.world.provider.getDimension())
                    {
                        TileEntity te = mc.world.getTileEntity(current.getCoordinate());
                        if (te instanceof BlockProtectorTileEntity)
                        {
                            BlockProtectorTileEntity blockProtectorTileEntity = (BlockProtectorTileEntity) te;
                            Set<BlockPos> coordinates = blockProtectorTileEntity.getProtectedBlocks();
                            if (!coordinates.isEmpty())
                            {
                                renderHighlightedBlocks(evt, p, te.getPos(), coordinates);
                            }
                        }
                    }
                }
            }
        }
	}
	
	private static final ResourceLocation yellowglow = new ResourceLocation(RFTools.MODID, "textures/blocks/yellowglow.png");

    private static void renderHighlightedBlocks(RenderWorldLastEvent evt, EntityPlayerSP p, BlockPos base, Set<BlockPos> coordinates)
    {
        double doubleX = p.lastTickPosX + (p.posX - p.lastTickPosX) * evt.getPartialTicks();
        double doubleY = p.lastTickPosY + (p.posY - p.lastTickPosY) * evt.getPartialTicks();
        double doubleZ = p.lastTickPosZ + (p.posZ - p.lastTickPosZ) * evt.getPartialTicks();

        GlStateManager.pushMatrix();
        GlStateManager.translate(-doubleX, -doubleY, -doubleZ);

        GlStateManager.disableDepth();
        GlStateManager.enableTexture2D();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        Minecraft.getMinecraft().getTextureManager().bindTexture(yellowglow);

        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        for (BlockPos coordinate : coordinates)
        {
            float x = base.getX() + coordinate.getX();
            float y = base.getY() + coordinate.getY();
            float z = base.getZ() + coordinate.getZ();
            buffer.setTranslation(x, y, z);

            RenderGlowEffect.addSideFullTexture(buffer, EnumFacing.UP.ordinal(), 1.1f, -0.05f);
            RenderGlowEffect.addSideFullTexture(buffer, EnumFacing.DOWN.ordinal(), 1.1f, -0.05f);
            RenderGlowEffect.addSideFullTexture(buffer, EnumFacing.NORTH.ordinal(), 1.1f, -0.05f);
            RenderGlowEffect.addSideFullTexture(buffer, EnumFacing.SOUTH.ordinal(), 1.1f, -0.05f);
            RenderGlowEffect.addSideFullTexture(buffer, EnumFacing.WEST.ordinal(), 1.1f, -0.05f);
            RenderGlowEffect.addSideFullTexture(buffer, EnumFacing.EAST.ordinal(), 1.1f, -0.05f);
            buffer.setTranslation(0, 0, 0);
        }
        tessellator.draw();
        
        GlStateManager.disableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.color(.5f, .3f, 0);
        GlStateManager.glLineWidth(2);
        buffer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION_COLOR);
        
        for (BlockPos coordinate : coordinates)
        {
            mcjty.lib.gui.RenderHelper.renderHighLightedBlocksOutline(buffer, base.getX() + coordinate.getX(), base.getY() + coordinate.getY(), base.getZ() + coordinate.getZ(), .5f, .3f, 0f, 1.0f);
        }
        tessellator.draw();
        
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.popMatrix();
    }
}