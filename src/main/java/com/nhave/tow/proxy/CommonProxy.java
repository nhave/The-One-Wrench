package com.nhave.tow.proxy;

import java.io.File;

import com.nhave.tow.eventhandlers.ModEventHandler;
import com.nhave.tow.registry.ModConfig;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy
{
	public void setupConfig(File configFile)
	{
		MinecraftForge.EVENT_BUS.register(new ModConfig(false));
		ModConfig.init(configFile);
	}
	
	public void preInit(FMLPreInitializationEvent event) {}
	
	public void registerRenders() {}
	
	public void registerEventHandlers()
	{
    	MinecraftForge.EVENT_BUS.register(new ModEventHandler());
	}
}