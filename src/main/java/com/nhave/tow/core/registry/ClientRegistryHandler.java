package com.nhave.tow.core.registry;

import com.nhave.tow.common.content.ModItems;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientRegistryHandler
{
	@SubscribeEvent
    public void onModelRegistry(ModelRegistryEvent event)
	{
		ModItems.registerRenders();
	}
}