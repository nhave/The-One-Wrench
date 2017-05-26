package com.nhave.tow.wrenchmodes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class ModeRegistry
{
	private static final List<WrenchMode> MODES = new ArrayList<WrenchMode>();

	public static WrenchMode register(String name, ItemStack icon)
	{
		WrenchMode mode = register(new WrenchMode(name, icon));
		return mode;
	}
	
	public static WrenchMode register(WrenchMode mode)
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