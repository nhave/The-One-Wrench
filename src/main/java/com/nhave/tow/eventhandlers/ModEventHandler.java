package com.nhave.tow.eventhandlers;

import com.nhave.nhc.events.ToolStationUpdateEvent;
import com.nhave.nhc.util.ItemUtil;
import com.nhave.tow.integration.WrenchRegistry;
import com.nhave.tow.items.ItemOmniwrench;
import com.nhave.tow.items.ItemShader;
import com.nhave.tow.registry.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModEventHandler
{
	@SubscribeEvent
	public void handleToolStationEvent(ToolStationUpdateEvent evt)
	{
		if (evt.input == null || evt.mod == null)
		{
			return;
		}
		if (evt.input.getItem() instanceof ItemOmniwrench && evt.mod.getItem() == ModItems.itemShader)
		{
			ItemStack stackShader = ItemUtil.getItemFromStack(evt.input, "SHADER");
			if (stackShader != null)
			{
				ItemShader itemShader = (ItemShader) stackShader.getItem();
				if (itemShader.getShader(stackShader) == itemShader.getShader(evt.mod)) return;
			}
			
			ItemStack stack = evt.input.copy();
			ItemStack shader = evt.mod.copy();
			shader.setCount(1);
			ItemUtil.addItemToStack(stack, shader, "SHADER");
			evt.materialCost=0;
			evt.output=stack;
		}
		if (evt.input.getItem() instanceof ItemOmniwrench && evt.mod.getItem() == ModItems.itemShaderRemover)
		{
			ItemStack stackShader = ItemUtil.getItemFromStack(evt.input, "SHADER");
			if (stackShader == null) return;
			
			ItemStack stack = evt.input.copy();
			ItemUtil.removeAllItemFromStack(stack, "SHADER");
			evt.materialCost=0;
			evt.output=stack;
		}
	}
	
	/**
	 * Prevents Block from calling its GUI
	 * 
	 * @param evt PlayerInteractEvent.RightClickBlock
	 */
	@SubscribeEvent()
    public void onPlayerInteract(PlayerInteractEvent.RightClickBlock evt)
	{
		ItemStack stack = evt.getEntityPlayer().getHeldItemMainhand();
	    if (stack != null && stack.getItem() instanceof ItemOmniwrench)
	    {
	    	ItemOmniwrench wrench = (ItemOmniwrench) stack.getItem();
	    	//if (wrench.getWrenchMode(stack) == ModItems.modeRotate) evt.setUseBlock(Result.DENY);
	    	for (int i = 0; i < WrenchRegistry.getCount(); ++i)
		    {
		    	if (WrenchRegistry.getHandler(i).shouldDenyBlockActivate(wrench.getWrenchMode(stack), evt.getEntityPlayer(), evt.getWorld(), evt.getPos()))
		    	{
		    		evt.setUseBlock(Result.DENY);
		    	}
		    }
	    }
	}
}