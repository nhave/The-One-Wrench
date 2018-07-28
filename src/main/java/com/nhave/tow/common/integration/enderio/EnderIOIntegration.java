package com.nhave.tow.common.integration.enderio;

import com.nhave.lib.common.network.Key;

import crazypants.enderio.base.conduit.ConduitDisplayMode;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class EnderIOIntegration
{
	public static void doKeyBindingAction(EntityPlayer player, ItemStack stack, Key key, boolean chat)
	{
		ConduitDisplayMode mode = ConduitDisplayMode.getDisplayMode(stack);
	    mode = (key == Key.SCROLLDN_ALT ? mode.next() : mode.previous());
	    ConduitDisplayMode.setDisplayMode(stack, mode);
	}
}