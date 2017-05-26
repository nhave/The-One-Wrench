package com.nhave.tow.proxy;

import java.io.File;

import com.nhave.tow.registry.ModConfig;
import com.nhave.tow.registry.ModItems;

import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy
{
	@Override
	public void setupConfig(File configFile)
	{
		MinecraftForge.EVENT_BUS.register(new ModConfig(true));
		ModConfig.init(configFile);
	}
	
	@Override
	public void registerRenders()
	{
		ModItems.registerRenders();
	}
	
	@Override
	public void registerEventHandlers()
	{
		super.registerEventHandlers();
	}
}