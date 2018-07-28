package com.nhave.tow.common.helper;

import java.util.List;

import com.nhave.lib.library.helper.ItemNBTHelper;
import com.nhave.lib.library.util.ColorUtils;
import com.nhave.lib.library.util.StringUtils;
import com.nhave.tow.common.content.ModConfig;
import com.nhave.tow.common.content.ModIntegration;
import com.nhave.tow.common.integration.ntech.NHCoreIntegration;
import com.nhave.tow.common.integration.ntech.NTechCoreIntegration;
import com.nhave.tow.common.item.ItemOmniwrench;

import net.minecraft.item.ItemStack;

public class ChromaHelper
{
	public static boolean isUsingChroma()
	{
		return (ModIntegration.nhcLoaded && ModConfig.nhcUseChromas) || ModIntegration.ntechcoreLoaded;
	}
	
	public static int getColor(ItemStack stack)
	{
		ItemOmniwrench item = ((ItemOmniwrench) stack.getItem());
		
		if (item.supportsChroma(stack))
		{
			if (isUsingChroma())
			{
				int color = 16777215;
				
				if (color == 16777215 && ModIntegration.nhcLoaded)
				{
					color = NHCoreIntegration.getChromaColor(stack);
				}
				if (color == 16777215 && ModIntegration.ntechcoreLoaded)
				{
					color = NTechCoreIntegration.getChromaColor(stack);
				}
				return color;
			}
			return ItemNBTHelper.getInteger(stack, "COLORS", "COLOR", 16777215);
		}
		return 16777215;
	}
	
	public static void addTooltip(ItemStack stack, List<String> tooltip)
	{
		ItemOmniwrench item = ((ItemOmniwrench) stack.getItem());
		
		if (item.supportsChroma(stack))
		{
			if (isUsingChroma())
			{
				String chroma = null;
				
				if (ModIntegration.nhcLoaded)
				{
					String newChroma = NHCoreIntegration.getChromaName(stack);
					if (newChroma != null) chroma = newChroma;
				}
				if (ModIntegration.ntechcoreLoaded)
				{
					String newChroma = NTechCoreIntegration.getChromaName(stack);
					if (newChroma != null) chroma = newChroma;
				}
				
				tooltip.add(StringUtils.localize("tooltip.tow.chroma.current") + ": " + StringUtils.format((chroma == null ? StringUtils.localize("tooltip.tow.chroma.default") : chroma), StringUtils.YELLOW, StringUtils.ITALIC));
			}
			else
			{
				tooltip.add(StringUtils.localize("tooltip.tow.color.current") + ": " + StringUtils.format("" + ColorUtils.INTtoHEX(item.getItemColor(stack, 1)), StringUtils.YELLOW, StringUtils.ITALIC));
			}
		}
	}
}