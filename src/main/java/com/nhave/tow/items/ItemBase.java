package com.nhave.tow.items;

import com.nhave.tow.Reference;
import com.nhave.tow.registry.ModItems;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBase extends Item
{
	private EnumRarity rarity = EnumRarity.COMMON;
	
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
}