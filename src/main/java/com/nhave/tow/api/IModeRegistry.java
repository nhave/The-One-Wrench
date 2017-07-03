package com.nhave.tow.api;

import com.nhave.tow.api.wrenchmodes.WrenchMode;

import net.minecraft.item.ItemStack;

public interface IModeRegistry
{
	/**
	 * Registers a new basic {@link WrenchMode} using a name and an icon.
	 * 
	 * @param name A unique name for the {@link WrenchMode}.
	 * @param icon An {@link ItemStack} to be used as an icon on the widget, can be <b>null</b>
	 * @return The {@link WrenchMode} registered.
	 */
	public WrenchMode register(String name, ItemStack icon);
	
	/**
	 * Registers a {@link WrenchMode}
	 * 
	 * @param mode The {@link WrenchMode} to be registered.
	 * @return The {@link WrenchMode} registered.
	 */
	public WrenchMode register(WrenchMode mode);
}