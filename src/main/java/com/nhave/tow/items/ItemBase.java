package com.nhave.tow.items;

import com.nhave.nhc.api.items.IItemQuality;
import com.nhave.tow.Reference;
import com.nhave.tow.registry.ModItems;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBase extends Item implements IItemQuality
{
	private String quality = "";
	
	public ItemBase(String name)
	{
		this.setRegistryName(name);
		this.setCreativeTab(ModItems.TAB_ITEMS);
		this.setUnlocalizedName(Reference.MODID + "." + name);
	}
	
	public String getItemName(ItemStack stack)
	{
		return stack.getItem().getRegistryName().getResourcePath();
	}
	
	public ItemBase setQuality(String quality)
	{
		this.quality = quality;
		return this;
	}

	@Override
	public String getQualityColor(ItemStack stack)
	{
		return this.quality;
	}
}