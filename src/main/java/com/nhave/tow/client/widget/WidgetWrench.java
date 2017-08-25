package com.nhave.tow.client.widget;

import com.nhave.nhc.api.items.IInventoryItem;
import com.nhave.nhc.client.widget.WidgetBase;
import com.nhave.tow.items.ItemOmniwrench;
import com.nhave.tow.wrenchmodes.ModeRegistry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

public class WidgetWrench extends WidgetBase
{
	public static ResourceLocation WIDGET_RESOURCE = new ResourceLocation("tow", "textures/misc/widget.png");
	
	@Override
	public int getSizeX(ItemStack stack)
	{
		return  (18 * 7) + 10;
	}
	
	@Override
	public int getSizeY(ItemStack stack)
	{
		return (int) ((18 * (Math.max((Math.ceil((double)getSize() / 5D)), 1) + 1)) + 10);
	}
	
	@Override
	public void drawWidget(ItemStack stack, int x, int y)
	{
		IInventoryItem item = (IInventoryItem) stack.getItem();
		ItemOmniwrench wrench = (ItemOmniwrench) stack.getItem();
		
		Minecraft mc = Minecraft.getMinecraft();
		mc.getTextureManager().bindTexture(WIDGET_RESOURCE);
		
		int maxX = 7;
		int maxY = ((int) Math.max((Math.ceil((double)getSize() / 5D)), 1)) + 1;
		
		for (int coordY = 0; coordY < maxY; ++coordY)
		{
			for (int coordX = 0; coordX < maxX; ++coordX)
			{
				if (coordX == 0)
				{
					if (coordY < 2) Gui.drawModalRectWithCustomSizedTexture(x + (18 * coordX) + 5, y + (18 * coordY) + 5, 0, 0, 18, 18, 256, 256);
					else Gui.drawModalRectWithCustomSizedTexture(x + (18 * coordX) + 5, y + (18 * coordY) + 5, 19, 0, 18, 18, 256, 256);
				}
				else Gui.drawModalRectWithCustomSizedTexture(x + (18 * coordX) + 5, y + (18 * coordY) + 5, 19, 0, 18, 18, 256, 256);
				
				if (coordX == 0) Gui.drawModalRectWithCustomSizedTexture(x, y + (18 * coordY) + 5, 0, 31, 5, 18, 256, 256);
				if (coordX == maxX - 1) Gui.drawModalRectWithCustomSizedTexture(x + (18 * coordX) + 23, y + (18 * coordY) + 5, 6, 31, 5, 18, 256, 256);
				if (coordY == 0) Gui.drawModalRectWithCustomSizedTexture(x + (18 * coordX) + 5, y + (18 * coordY), 12, 19, 18, 5, 256, 256);
				if (coordY == maxY - 1) Gui.drawModalRectWithCustomSizedTexture(x + (18 * coordX) + 5, y + (18 * coordY) + 23, 12, 25, 18, 5, 256, 256);
				if (coordY == 0 && coordX == 0) Gui.drawModalRectWithCustomSizedTexture(x, y, 0, 19, 5, 5, 256, 256);
				if (coordY == 0 && coordX == maxX - 1) Gui.drawModalRectWithCustomSizedTexture(x + (18 * coordX) + 23, y, 6, 19, 5, 5, 256, 256);
				if (coordY == maxY - 1 && coordX == 0) Gui.drawModalRectWithCustomSizedTexture(x, y + (18 * coordY) + 23, 0, 25, 5, 5, 256, 256);
				if (coordY == maxY - 1 && coordX == maxX - 1) Gui.drawModalRectWithCustomSizedTexture(x + (18 * coordX) + 23, y + (18 * coordY) + 23, 6, 25, 5, 5, 256, 256);
			}
		}
		
		for (int coordY = 0; coordY < (maxY - 1); ++coordY)
		{
			for (int coordX = 0; coordX < 5; ++coordX)
			{
				int current = ((coordY * 5) + (coordX + 1)) -1;
				if (current < ModeRegistry.getSize()) Gui.drawModalRectWithCustomSizedTexture(x + (18 * coordX) + 33, y + (18 * coordY) + 5, 0, 0, 18, 18, 256, 256);
				if ((current + 1) == (wrench.getModeAsNumber(stack) + 1)) Gui.drawModalRectWithCustomSizedTexture(x + (18 * coordX) + 32, y + (18 * coordY) + 4, 38, 0, 20, 20, 256, 256);
			}
		}
		
		RenderItem render = mc.getRenderItem();
		
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.enableDepth();
		int slot = 0;
		for (int coordY = 0; coordY < 2; ++coordY)
		{
			ItemStack invItem = item.getStackInSlot(stack, slot);
			if (invItem != null)
			{
				render.renderItemAndEffectIntoGUI(invItem, x + 6, y + (18 * coordY) + 6);
			}
			++slot;
		}
		for (int coordY = 0; coordY < (maxY - 1); ++coordY)
		{
			for (int coordX = 0; coordX < 5; ++coordX)
			{
				int current = ((coordY * 5) + (coordX + 1)) -1;
				if (current < ModeRegistry.getSize())
				{
					ItemStack icon = ModeRegistry.getMode(current).getIcon();
					if (icon != null)
					{
						render.renderItemAndEffectIntoGUI(icon, x + (18 * coordX) + 34, y + (18 * coordY) + 6);
					}
				}
			}
		}
		GlStateManager.disableDepth();
		
		GlStateManager.pushMatrix();
		GlStateManager.disableLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableDepth();
		
        String text = I18n.translateToLocal("tooltip.tow.mode") + ": " + "§e" + "§o" + I18n.translateToLocal("tooltip.tow.mode." + wrench.getWrenchMode(stack).getName()) + "§r";
        mc.fontRenderer.drawStringWithShadow(text, x + 34,  y + (18 * (maxY - 1)) + 7, 16777215);
        
		GlStateManager.enableDepth();
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
		GlStateManager.popMatrix();
	}
	
	@Override
	public boolean shouldDraw(ItemStack stack)
	{
		return stack.getItem() instanceof ItemOmniwrench && stack.getItem() instanceof IInventoryItem;
	}
	
	private int getSize()
	{
		return ModeRegistry.getSize();
	}
}
