package com.nhave.tow.common.wrenchmode;

import java.util.ArrayList;
import java.util.List;

public class ModeRegistry
{
	private static final List<WrenchMode> MODES = new ArrayList<WrenchMode>();
	
	public static WrenchMode register(String name)
	{
		WrenchMode mode = register(new WrenchMode(name));
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