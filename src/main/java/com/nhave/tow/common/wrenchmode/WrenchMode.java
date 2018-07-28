package com.nhave.tow.common.wrenchmode;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WrenchMode
{
	private String name;
	
	public WrenchMode(String name)
	{
		this.name = name;
	}
	
	/**
	 * @return Returns the unlocalized name for the Mode.
	 */
	public String getName()
	{
		return this.name;
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