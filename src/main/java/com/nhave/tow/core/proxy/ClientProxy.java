package com.nhave.tow.core.proxy;

import java.io.File;

import com.nhave.tow.common.content.ModConfig;
import com.nhave.tow.core.registry.ClientRegistryHandler;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{
	@Override
	public void setupConfig(File configFile)
	{
		MinecraftForge.EVENT_BUS.register(new ModConfig(true));
		ModConfig.init(configFile);
	}
	
	@Override
    public void preInit(FMLPreInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new ClientRegistryHandler());
    }
	
	@Override
	public void registerRenders() {}
	
	@Override
	public void registerEventHandlers()
	{
		super.registerEventHandlers();
	}
}