package com.nhave.tow.items;

import com.nhave.nhc.util.StringUtils;
import com.nhave.tow.registry.ModItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemMeta extends ItemBase
{
	private String[] rarityNames = new String[] {"", StringUtils.LIGHT_BLUE, StringUtils.PURPLE, StringUtils.ORANGE};
	private int rarity = 0;
	public String[] names;
	private int[] rarities;
	
	public ItemMeta(String name, String[] names, int rarity)
	{
		super(name);
		this.names = names;
		this.rarity = rarity;
		this.setHasSubtypes(true);
	}
	
	public ItemMeta(String name, String[] names, String type, int[] rarities)
	{
		this(name, names, 0);
		if (rarities.length == names.length) this.rarities = rarities;
	}
	
	public ItemMeta(String name, String[][] names)
	{
		super(name);
		this.rarities = new int[names.length];
		this.names = new String[names.length];
		for (int i = 0; i < names.length; ++i)
		{
			this.names[i] = names[i][0];
			this.rarities[i] = Integer.parseInt(names[i][1]);
		}
		this.rarity = 0;
		this.setHasSubtypes(true);
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if (tab != ModItems.TAB_ITEMS) return;
		for (int i = 0; i < names.length; ++i)
		{
			items.add(new ItemStack(this, 1, i));
		}
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		int meta = Math.min(stack.getItemDamage(), names.length-1);
		return StringUtils.localize("item.tow." + names[meta] + ".name");
	}
	
	public  ItemStack getItem(String name, int amount)
	{
		for (int meta = 0; meta < this.names.length; ++meta)
		{
			if (this.names[meta].equals(name)) return new ItemStack(this, amount, meta);
		}
		return null;
	}

	@Override
	public String getQualityColor(ItemStack stack)
	{
		int meta = Math.min(stack.getItemDamage(), names.length-1);
		return this.rarities != null ? this.rarityNames[rarities[meta]] : this.rarityNames[0];
	}
}