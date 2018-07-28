package com.nhave.tow.common.integration.ntech;

import com.nhave.rpgtooltips.common.content.ModConfig;
import com.nhave.rpgtooltips.common.item.EnumItemQuality;
import com.nhave.rpgtooltips.common.item.IItemQuality;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

public class RPGTooltipsIntegration
{
	public static String getDisplayColor(ItemStack stack)
	{
		EnumItemQuality quality = EnumItemQuality.COMMON;
		if(stack.getItem() instanceof IItemQuality) quality = ((IItemQuality) stack.getItem()).getItemQuality(stack);
		else if (stack.getItem().getRarity(stack) == EnumRarity.UNCOMMON) quality = EnumItemQuality.RARE;
		else if (stack.getItem().getRarity(stack) == EnumRarity.RARE) quality = EnumItemQuality.EPIC;
		else if (stack.getItem().getRarity(stack) == EnumRarity.EPIC) quality = EnumItemQuality.CREATIVE;
		
		if (ModConfig.itemQualityType.equals("RPG") && (ModConfig.alterVanillaRarity || stack.getItem() instanceof IItemQuality))
		{
			return quality.getColor();
		}
		return quality.getVanillaColor();
	}
}