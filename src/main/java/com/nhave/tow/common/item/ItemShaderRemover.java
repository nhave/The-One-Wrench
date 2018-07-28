package com.nhave.tow.common.item;

import java.util.List;

import com.nhave.lib.library.helper.TooltipHelper;
import com.nhave.lib.library.util.StringUtils;
import com.nhave.tow.common.content.ModItems;

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
			TooltipHelper.addHiddenTooltip(tooltip, "tooltip.tow." + this.getItemName(), ";", StringUtils.GRAY);
			tooltip.add(StringUtils.localize("tooltip.tow.shader.appliesto") + ":");
			tooltip.add("  " + StringUtils.format(StringUtils.localize("item.tow.wrench.name"), StringUtils.YELLOW, StringUtils.ITALIC));
		}
		else tooltip.add(StringUtils.shiftForInfo());
	}
}