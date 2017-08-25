package com.nhave.tow.registry;

import com.nhave.nhc.registry.ModBlocks;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientRegistryHandler
{
	@SubscribeEvent
    public void onModelRegistry(ModelRegistryEvent event)
	{
		ModBlocks.registerRenders();
		ModItems.registerRenders();
	}
}