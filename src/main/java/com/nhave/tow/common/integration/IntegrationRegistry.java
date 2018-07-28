package com.nhave.tow.common.integration;

import java.util.ArrayList;
import java.util.List;

import com.nhave.tow.core.Reference;

import net.minecraftforge.fml.common.ModMetadata;

public class IntegrationRegistry
{
	private static final List<WrenchHandler> HANDLERS = new ArrayList<WrenchHandler>();
	private static final List<String> NAMES = new ArrayList<String>();
	
	private static ModMetadata modMeta;
	
	public static void register(WrenchHandler handler, String name)
	{
		register(handler);
		register(name);
	}
	
	public static void register(WrenchHandler handler)
	{
		HANDLERS.add(handler);
	}
	
	public static void register(String name)
	{
		NAMES.add(name);
		updateModMeta();
	}
	
	public static WrenchHandler getHandler(int index)
	{
		return HANDLERS.get(index);
	}
	
	public static int getCount()
	{
		return HANDLERS.size();
	}
	
	public static void setupModMeta(ModMetadata meta)
	{
		modMeta = meta;
		updateModMeta();
	}

	public static void updateModMeta()
	{
		modMeta.description = Reference.MOD_DESCRIPTION;
		
		if(IntegrationRegistry.NAMES.size() == 0)
        {
			modMeta.description += "\n\n\247cNo active integrations.";
        }
        else
        {
        	java.util.Collections.sort(NAMES);
        	
        	modMeta.description += "\n\n\247aActive integrations:";
        	
            for(int i = 0; i < IntegrationRegistry.NAMES.size(); i++)
            {
                modMeta.description += ("\n\247a - " + IntegrationRegistry.NAMES.get(i));
            }
        }
	}
}