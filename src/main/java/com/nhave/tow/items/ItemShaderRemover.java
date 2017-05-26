package com.nhave.tow.items;

import java.util.List;

import com.nhave.nhc.api.items.IItemQuality;
import com.nhave.nhc.util.StringUtils;
import com.nhave.tow.registry.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemShaderRemover extends ItemBase implements IItemQuality
{
	public ItemShaderRemover(String name)
	{
		super(name);
		this.setCreativeTab(ModItems.TAB_SHADERS);
		this.setMaxStackSize(1);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean flag)
	{
		if (StringUtils.isShiftKeyDown())
		{
			list.add(StringUtils.localize("tooltip.tow.shader.appliesto") + ":");
			list.add("  " + StringUtils.format(StringUtils.localize("item.tow.wrench.name"), StringUtils.YELLOW, StringUtils.ITALIC));
		}
		else list.add(StringUtils.shiftForInfo);
	}
	
	@Override
	public String getQualityColor(ItemStack arg0)
	{
		return StringUtils.LIGHT_BLUE;
	}
}