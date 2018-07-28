package com.nhave.tow.common.item;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.nhave.lib.library.helper.TooltipHelper;
import com.nhave.lib.library.util.StringUtils;
import com.nhave.tow.common.content.ModShaders;
import com.nhave.tow.common.itemshader.Shader;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemShaderPackEvent extends ItemShaderPack
{
	public ItemShaderPackEvent(String name)
	{
		super(name, null, 1);
	}
	
	@Override
	public List<Shader> getLootPool()
	{
		int day = LocalDateTime.now().getDayOfMonth();
		int month = LocalDateTime.now().getMonthValue();
		
		if ((month == 10 && day >= 24) || (month == 11 && day <= 7)) return ModShaders.HALLOWEEN_SHADERS;
		return new ArrayList<Shader>();
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if (StringUtils.isShiftKeyDown())
		{
			tooltip.add(StringUtils.format(this.getEventName(), StringUtils.ORANGE));
			TooltipHelper.addSplitString(tooltip, StringUtils.localize("tooltip.tow." + (this.getLootPool().size() > 0 ? this.getItemName() : "shaderpacknoevent")), ";", StringUtils.GRAY);
		}
		else tooltip.add(StringUtils.shiftForInfo());
	}
	
	public String getEventName()
	{
		if (this.getLootPool().size() > 0)
		{
			if (this.getLootPool() == ModShaders.HALLOWEEN_SHADERS) return StringUtils.localize("tooltip.tow.event.halloween") + " 10/24 - 11/7";
		}
		return StringUtils.localize("tooltip.tow.event.none");
	}
}