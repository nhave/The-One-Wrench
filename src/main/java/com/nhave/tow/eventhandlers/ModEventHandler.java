package com.nhave.tow.eventhandlers;

import com.nhave.nhc.events.ToolStationUpdateEvent;
import com.nhave.nhc.items.ItemToken;
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
			if (stackShader != null && !stackShader.isEmpty() && stackShader.getItem() == ModItems.itemShader)
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
		else if (evt.input.getItem() instanceof ItemOmniwrench && evt.mod.getItem() == ModItems.itemShaderRemover)
		{
			ItemStack stackShader = ItemUtil.getItemFromStack(evt.input, "SHADER");
			if (stackShader == null || stackShader.isEmpty()) return;
			
			ItemStack stack = evt.input.copy();
			ItemUtil.removeAllItemFromStack(stack, "SHADER");
			evt.materialCost=0;
			evt.output=stack;
		}
		else if (evt.input.getItem() instanceof ItemOmniwrench && evt.mod.getItem() instanceof ItemToken)
		{
			ItemToken token = (ItemToken) evt.mod.getItem();
			boolean active = token.isActive(evt.mod);
			if (active)
			{
				ItemStack tokenStack = ItemUtil.getItemFromStack(evt.input, "SHADER");
				if (tokenStack != null && !tokenStack.isEmpty() && tokenStack.getItem() instanceof ItemToken) return;
				
				ItemStack output = evt.input.copy();
				ItemUtil.addItemToStack(output, evt.mod.copy(), "SHADER");
				evt.materialCost=0;
				evt.output=output;
			}
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