package com.nhave.tow.common.integration.ntech;

import com.nhave.lib.library.util.ItemUtil;
import com.nhave.ntechcore.common.event.ItemUpgradeEvent;
import com.nhave.ntechcore.common.item.ItemChroma;
import com.nhave.tow.common.content.ModItems;
import com.nhave.tow.common.item.ItemOmniwrench;
import com.nhave.tow.common.item.ItemShader;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * This class is used to integrate with NTechCore and should not be called if NTechCore is not present.
 * 
 * @author NHAVE
 */
public class NTechCoreIntegration
{
	/**
	 * Used to get the name of the Chroma applied to an Item.
	 * 
	 * @param stack The ItemStack containing the Chroma.
	 * @return The localized display name of the Chroma.
	 */
	public static String getChromaName(ItemStack stack)
	{
		ItemStack item = ItemUtil.getItemFromStack(stack, "CHROMA");
		
		if (item != null && item.getItem() instanceof ItemChroma)
		{
			if (((ItemChroma) item.getItem()).getChroma(item) != null) return item.getDisplayName();
		}
		return null;
	}
	
	/**
	 * Used to get the color of the Chroma applied to an Item.
	 * 
	 * @param stack The ItemStack containing the Chroma.
	 * @return The color of the Chroma.
	 */
	public static int getChromaColor(ItemStack stack)
	{
		ItemStack item = ItemUtil.getItemFromStack(stack, "CHROMA");
		if (item != null && item.getItem() instanceof ItemChroma)
		{
			if (((ItemChroma) item.getItem()).getChroma(item) != null) return ((ItemChroma) item.getItem()).getChroma(item).getColor();
		}
		return 16777215;
	}
	
	/**
	 * Allows the wrench to be modded using the Tool Station from NHAVE's Core.
	 * 
	 * @param evt ToolStationUpdateEvent
	 */
	@SubscribeEvent
	public void handleItemUpgradeEvent(ItemUpgradeEvent evt)
	{
		if (evt.getInput() == null || evt.getUpgrade() == null)
		{
			return;
		}
		if (evt.getInput().getItem() instanceof ItemOmniwrench && evt.getUpgrade().getItem() == ModItems.itemShader)
		{
			ItemStack stackShader = ItemUtil.getItemFromStack(evt.getInput(), "SHADER");
			if (stackShader != null && !stackShader.isEmpty() && stackShader.getItem() == ModItems.itemShader)
			{
				ItemShader itemShader = (ItemShader) stackShader.getItem();
				if (itemShader.getShader(stackShader) == itemShader.getShader(evt.getUpgrade())) return;
			}
			
			ItemStack stack = evt.getInput().copy();
			ItemStack shader = evt.getUpgrade().copy();
			shader.setCount(1);
			ItemUtil.addItemToStack(stack, shader, "SHADER");
			evt.setMaterialCost(0);
			evt.setOutput(stack);
			return;
		}
		if (evt.getInput().getItem() instanceof ItemOmniwrench && evt.getUpgrade().getItem() == ModItems.itemShaderRemover)
		{
			ItemStack stackShader = ItemUtil.getItemFromStack(evt.getInput(), "SHADER");
			if (stackShader == null || stackShader.isEmpty()) return;
			
			ItemStack stack = evt.getInput().copy();
			ItemUtil.removeAllItemFromStack(stack, "SHADER");
			evt.setMaterialCost(0);
			evt.setOutput(stack);
			return;
		}
	}
}