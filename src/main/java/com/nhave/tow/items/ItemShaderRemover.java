package com.nhave.tow.items;

import java.util.List;

import com.nhave.nhc.helpers.TooltipHelper;
import com.nhave.nhc.util.StringUtils;
import com.nhave.tow.registry.ModItems;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemShaderRemover extends ItemBase
{
	public ItemShaderRemover(String name)
	{
		super(name);
		this.setCreativeTab(ModItems.TAB_SHADERS);
		this.setMaxStackSize(1);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if (StringUtils.isShiftKeyDown())
		{
			tooltip.add(StringUtils.format(StringUtils.localize("tooltip.tow.shader"), StringUtils.GREEN, StringUtils.ITALIC));
			TooltipHelper.addHiddenTooltip(tooltip, "tooltip.tow." + this.getItemName(stack), ";", StringUtils.GRAY);
			tooltip.add(StringUtils.localize("tooltip.tow.shader.appliesto") + ":");
			tooltip.add("  " + StringUtils.format(StringUtils.localize("item.tow.wrench.name"), StringUtils.YELLOW, StringUtils.ITALIC));
		}
		else tooltip.add(StringUtils.shiftForInfo);
	}
	
	@Override
	public String getQualityColor(ItemStack arg0)
	{
		return StringUtils.LIGHT_BLUE;
	}
}