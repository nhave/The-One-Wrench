package com.nhave.tow.common.eventhandler;

import com.nhave.lib.library.helper.ItemNBTHelper;
import com.nhave.lib.library.util.ColorUtils;
import com.nhave.lib.library.util.ItemUtil;
import com.nhave.tow.common.content.ModIntegration;
import com.nhave.tow.common.content.ModItems;
import com.nhave.tow.common.integration.IntegrationRegistry;
import com.nhave.tow.common.item.ItemOmniwrench;
import com.nhave.tow.common.item.ItemShader;

import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

public class ModEventHandler
{
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
	    	for (int i = 0; i < IntegrationRegistry.getCount(); ++i)
		    {
		    	if (IntegrationRegistry.getHandler(i).shouldDenyBlockActivate(wrench.getWrenchMode(stack), evt.getEntityPlayer(), evt.getWorld(), evt.getPos()))
		    	{
		    		evt.setUseBlock(Result.DENY);
		    	}
		    }
	    }
	}
	
	/**
	 * Allows customizing the wrench when
	 * NTech-Core is not loaded
	 * 
	 * @param evt AnvilUpdateEvent
	 */
	@SubscribeEvent()
	public void onAnvilUpdate(AnvilUpdateEvent evt)
    {
		if (ModIntegration.nhcLoaded) return;
		
		if (evt.getLeft().isEmpty() || evt.getRight().isEmpty())
		{
			return;
		}
		if (evt.getLeft().getItem() instanceof ItemOmniwrench && evt.getRight().getItem() == ModItems.itemShader)
		{
			ItemStack stackShader = ItemUtil.getItemFromStack(evt.getLeft(), "SHADER");
			if (stackShader != null && !stackShader.isEmpty() && stackShader.getItem() == ModItems.itemShader)
			{
				ItemShader itemShader = (ItemShader) stackShader.getItem();
				if (itemShader.getShader(stackShader) == itemShader.getShader(evt.getRight())) return;
			}
			
			ItemStack stack = evt.getLeft().copy();
			ItemStack shader = evt.getRight().copy();
			shader.setCount(1);
			ItemUtil.addItemToStack(stack, shader, "SHADER");
			
			evt.setOutput(stack);
			evt.setMaterialCost(1);
			evt.setCost(1);
			return;
		}
		if (evt.getLeft().getItem() instanceof ItemOmniwrench && evt.getRight().getItem() == ModItems.itemShaderRemover)
		{
			ItemStack stackShader = ItemUtil.getItemFromStack(evt.getLeft(), "SHADER");
			if (stackShader == null || stackShader.isEmpty()) return;
			
			ItemStack stack = evt.getLeft().copy();
			ItemUtil.removeAllItemFromStack(stack, "SHADER");
			
			evt.setOutput(stack);
			evt.setMaterialCost(1);
			evt.setCost(1);
			return;
		}
		if (evt.getLeft().getItem() instanceof ItemOmniwrench && ((ItemOmniwrench) evt.getLeft().getItem()).supportsChroma(evt.getLeft()))
		{
			for (int i = 0; i < ColorUtils.colorCodes.length; ++i)
			{
				if (OreDictionary.containsMatch(true, OreDictionary.getOres(ColorUtils.oreDict[i]), evt.getRight()))
				{
					if (ItemNBTHelper.getInteger(evt.getLeft().copy(), "COLORS", "COLOR", 16777215) == ColorUtils.colorCodes[i])
					{
						return;
					}
					ItemStack stack = evt.getLeft().copy();
					ItemNBTHelper.setInteger(stack, "COLORS", "COLOR", ColorUtils.colorCodes[i]);
					
					evt.setOutput(stack);
					evt.setMaterialCost(1);
					evt.setCost(1);
					return;
				}
			}
		}
    }
}