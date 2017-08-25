package com.nhave.tow.registry;

import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RegistryHandler
{
	@SubscribeEvent
    public void onItemRegistry(Register<Item> event)
    {
        ModItems.init();
        ModItems.register(event);
    }
    
    @SubscribeEvent
    public void onCraftingRegistry(Register<IRecipe> event)
    {
    	ModCrafting.register(event);
    }
}