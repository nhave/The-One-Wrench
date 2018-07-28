package com.nhave.tow.common.item;

import com.nhave.rpgtooltips.common.item.EnumItemQuality;
import com.nhave.rpgtooltips.common.item.IItemQuality;
import com.nhave.tow.common.content.ModItems;
import com.nhave.tow.core.Reference;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Optional;

@Optional.InterfaceList({
    @Optional.Interface(iface = "com.nhave.rpgtooltips.common.item.IItemQuality", modid = "rpgtooltips")
})
public class ItemBase extends Item implements IItemQuality
{
	private int quality = 0;
	
	public ItemBase(String name)
	{
		this.setRegistryName(name);
		this.setCreativeTab(ModItems.TAB_ITEMS);
		this.setUnlocalizedName(Reference.MODID + "." + name);
	}
	
	public String getItemName()
	{
		return this.getRegistryName().getResourcePath();
	}
	
	public ItemBase setQuality(int quality)
	{
		this.quality = quality;
		return this;
	}
	
	public int getQuality(ItemStack stack)
	{
		return this.quality;
	}
	
	@Optional.Method(modid = "rpgtooltips")
	@Override
	public EnumItemQuality getItemQuality(ItemStack stack)
	{
		switch (getQuality(stack))
		{
		case 0:
			return EnumItemQuality.COMMON;
		case 1:
			return EnumItemQuality.RARE;
		case 2:
			return EnumItemQuality.EPIC;
		case 3:
			return EnumItemQuality.LEGENDARY;
		default:
			return EnumItemQuality.COMMON;
		}
	}
}