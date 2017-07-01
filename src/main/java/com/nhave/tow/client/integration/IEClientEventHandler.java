package com.nhave.tow.client.integration;

import com.nhave.tow.items.ItemOmniwrench;
import com.nhave.tow.registry.ModItems;

import blusunrize.immersiveengineering.api.Lib;
import blusunrize.immersiveengineering.client.ClientEventHandler;
import blusunrize.immersiveengineering.client.ClientProxy;
import blusunrize.immersiveengineering.client.ClientUtils;
import blusunrize.immersiveengineering.common.Config.IEConfig;
import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces.IBlockOverlayText;
import blusunrize.immersiveengineering.common.blocks.wooden.TileEntityTurntable;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class IEClientEventHandler
{
	@SubscribeEvent()
	public void onRenderOverlayPost(RenderGameOverlayEvent.Post event)
	{
		if(ClientUtils.mc().player!=null && event.getType() == RenderGameOverlayEvent.ElementType.TEXT)
		{
			EntityPlayer player = ClientUtils.mc().player;
			ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);
			
			if(ClientUtils.mc().objectMouseOver!=null)
			{
				boolean hammer = !stack.isEmpty() && stack.getItem() instanceof ItemOmniwrench && ((ItemOmniwrench) stack.getItem()).getWrenchMode(stack) == ModItems.modeWrench;
				RayTraceResult mop = ClientUtils.mc().objectMouseOver;
				if(mop!=null && mop.getBlockPos()!=null)
				{
					TileEntity tileEntity = player.world.getTileEntity(mop.getBlockPos());
					if(tileEntity instanceof IBlockOverlayText && hammer)
					{
						IBlockOverlayText overlayBlock = (IBlockOverlayText) tileEntity;
						String[] text = overlayBlock.getOverlayText(ClientUtils.mc().player, mop, hammer);
						boolean useNixie = overlayBlock.useNixieFont(ClientUtils.mc().player, mop);
						if(text!=null && text.length>0)
						{
							FontRenderer font = useNixie?ClientProxy.nixieFontOptional:ClientUtils.font();
							int col = (useNixie && IEConfig.nixietubeFont)?Lib.colour_nixieTubeText:0xffffff;
							int i = 0;
							for(String s : text)
								if(s!=null)
									font.drawString(s, event.getResolution().getScaledWidth()/2+8, event.getResolution().getScaledHeight()/2+8+(i++)*font.FONT_HEIGHT, col, true);
						}
					}
				}
			}
		}
	}
	
	@SubscribeEvent()
	public void renderAdditionalBlockBounds(DrawBlockHighlightEvent event)
	{
		if (event.getSubID() == 0 && event.getTarget().typeOfHit == Type.BLOCK)
		{
			float f1 = 0.002F;
			double px = -TileEntityRendererDispatcher.staticPlayerX;
			double py = -TileEntityRendererDispatcher.staticPlayerY;
			double pz = -TileEntityRendererDispatcher.staticPlayerZ;
			TileEntity tile = event.getPlayer().world.getTileEntity(event.getTarget().getBlockPos());
			ItemStack stack = event.getPlayer().getHeldItem(EnumHand.MAIN_HAND);
			boolean hammer = !stack.isEmpty() && stack.getItem() instanceof ItemOmniwrench && ((ItemOmniwrench) stack.getItem()).getWrenchMode(stack) == ModItems.modeWrench;
			
			if(hammer && tile instanceof TileEntityTurntable)
			{
				BlockPos pos = event.getTarget().getBlockPos();

				GlStateManager.enableBlend();
				GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
				GlStateManager.glLineWidth(2.0F);
				GlStateManager.disableTexture2D();
				GlStateManager.depthMask(false);

				Tessellator tessellator = Tessellator.getInstance();
				VertexBuffer vertexbuffer = tessellator.getBuffer();

				EnumFacing f = ((TileEntityTurntable)tile).getFacing();
				double tx = pos.getX()+.5;
				double ty = pos.getY()+.5;
				double tz = pos.getZ()+.5;
				if(!event.getPlayer().world.isAirBlock(pos.offset(f)))
				{
					tx += f.getFrontOffsetX();
					ty += f.getFrontOffsetY();
					tz += f.getFrontOffsetZ();
				}
				vertexbuffer.setTranslation(tx+px,ty+py,tz+pz);

				double angle = -event.getPlayer().ticksExisted%80/40d*Math.PI;
				ClientEventHandler.drawRotationArrows(tessellator, vertexbuffer, f, angle, ((TileEntityTurntable)tile).invert);

				vertexbuffer.setTranslation(0, 0, 0);

				GlStateManager.depthMask(true);
				GlStateManager.enableTexture2D();
				GlStateManager.disableBlend();
			}
		}
	}
}