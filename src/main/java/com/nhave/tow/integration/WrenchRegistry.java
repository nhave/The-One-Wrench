package com.nhave.tow.integration;

import java.util.ArrayList;
import java.util.List;

import com.nhave.tow.Reference;
import com.nhave.tow.api.IIntegrationRegistry;
import com.nhave.tow.api.integration.WrenchHandler;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;

public class WrenchRegistry implements IIntegrationRegistry
{
	private static final List<WrenchHandler> HANDLERS = new ArrayList<WrenchHandler>();

	private static final List<String> NAMES = new ArrayList<String>();
	
	@Mod.Metadata(Reference.MODID)
	private static ModMetadata modMeta;
	
	public void register(WrenchHandler handler, String name)
	{
		register(handler);
		register(name);
	}
	
	public void register(WrenchHandler handler)
	{
		HANDLERS.add(handler);
	}
	
	public void register(String name)
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

	public static void updateModMeta()
	{
		modMeta.description = Reference.MOD_DESCRIPTION;
		
		if(WrenchRegistry.NAMES.size() == 0)
        {
			modMeta.description += "\n\n\247cNo active integrations.";
        }
        else
        {
        	java.util.Collections.sort(NAMES);
        	
        	modMeta.description += "\n\n\247aActive integrations:";
        	
            for(int i = 0; i < WrenchRegistry.NAMES.size(); i++)
            {
                modMeta.description += ("\n\247a - " + WrenchRegistry.NAMES.get(i));
            }
        }
	}
}