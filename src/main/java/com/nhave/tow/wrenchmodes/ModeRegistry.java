package com.nhave.tow.wrenchmodes;

import java.util.ArrayList;
import java.util.List;

import com.nhave.tow.api.IModeRegistry;
import com.nhave.tow.api.wrenchmodes.WrenchMode;

import net.minecraft.item.ItemStack;

public class ModeRegistry implements IModeRegistry
{
	private static final List<WrenchMode> MODES = new ArrayList<WrenchMode>();

	public WrenchMode register(String name, ItemStack icon)
	{
		WrenchMode mode = register(new WrenchMode(name, icon));
		return mode;
	}
	
	public WrenchMode register(WrenchMode mode)
	{
		MODES.add(mode);
		return mode;
	}
	
	public static WrenchMode getMode(int index)
	{
		return MODES.get(index);
	}
	
	public static int getSize()
	{
		return MODES.size();
	}
}