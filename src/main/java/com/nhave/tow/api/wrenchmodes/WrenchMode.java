package com.nhave.tow.api.wrenchmodes;

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
	
	/**
	 * @return Returns the unlocalized name for the Mode.
	 */
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * @return Returns the Icon used on the widget, can be <b>null</b>.
	 */
	public ItemStack getIcon()
	{
		return this.icon;
	}
	
	/**
	 * Same as the Item addInformation, Allows the mode to add info to the OmniWrench Tooltip.
	 * 
	 * @param stack The OmniWrench {@link ItemStack}.
	 * @param player The player viewing the Item.
	 * @param list The actual tooltip.
	 * @param advanced True if F3+H is enabled.
	 */
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced) {}
}