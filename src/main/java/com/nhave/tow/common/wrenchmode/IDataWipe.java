package com.nhave.tow.common.wrenchmode;

import com.nhave.tow.common.integration.WrenchHandler;

import net.minecraft.item.ItemStack;

public interface IDataWipe
{
	/**
	 * When implemented by a {@link WrenchHandler}, it will be called when using the Wipe function on the OmniWrench Tuning mode.
	 * 
	 * @param stack The OmniWrench {@link ItemStack}.
	 * @return True if a Data Wipe has happened.
	 */
	public boolean wipeData(ItemStack stack);
}