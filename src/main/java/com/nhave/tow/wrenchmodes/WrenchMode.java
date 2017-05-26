package com.nhave.tow.wrenchmodes;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WrenchMode
{
	private String name;
	private ItemStack icon;
	
	public WrenchMode(String name, ItemStack icon)
	{
		this.name = name;
		this.icon = icon;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public ItemStack getIcon()
	{
		return this.icon;
	}
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
	{
		
	}
}