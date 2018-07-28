package com.nhave.tow.common.integration.ntech;

import com.nhave.lib.library.helper.ItemNBTHelper;
import com.nhave.lib.library.util.ColorUtils;
import com.nhave.lib.library.util.ItemUtil;
import com.nhave.nhc.events.ToolStationUpdateEvent;
import com.nhave.nhc.items.ItemChroma;
import com.nhave.tow.common.content.ModItems;
import com.nhave.tow.common.helper.ChromaHelper;
import com.nhave.tow.common.item.ItemOmniwrench;
import com.nhave.tow.common.item.ItemShader;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

/**
 * This class is used to integrate with NHCore and should not be called if NHCore is not present.
 * 
 * @author NHAVE
 */
public class NHCoreIntegration
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
	public void handleToolStationEvent(ToolStationUpdateEvent evt)
	{
		if (evt.input == null || evt.mod == null)
		{
			return;
		}
		if (ChromaHelper.isUsingChroma() && evt.input.getItem() instanceof ItemOmniwrench && evt.mod.getItem() instanceof ItemChroma)
		{
			ItemOmniwrench wrench = ((ItemOmniwrench) evt.input.getItem());
			
			if (!wrench.supportsChroma(evt.input)) return;
			ItemStack stackChroma = ItemUtil.getItemFromStack(evt.input, "CHROMA");
			
			if (stackChroma == null) stackChroma = ItemNBTHelper.setString(GameRegistry.makeItemStack("nhc:chroma", 0, 1, null), "CHROMAS", "CHROMA", "white");
			//if (stackChroma == null) stackChroma = ItemNBTHelper.setString(new ItemStack(com.nhave.nhc.registry.ModItems.itemChroma), "CHROMAS", "CHROMA", "white");
			
			if (stackChroma.getItem() instanceof ItemChroma)
			{
				ItemChroma itemChroma = (ItemChroma) stackChroma.getItem();
				if (itemChroma.getChroma(stackChroma) == itemChroma.getChroma(evt.mod)) return;
			}
			
			ItemStack stack = evt.input.copy();
			ItemStack chroma = evt.mod.copy();
			chroma.setCount(1);
			ItemUtil.addItemToStack(stack, chroma, "CHROMA");
			evt.materialCost=1;
			evt.output=stack;
			return;
		}
		if (!ChromaHelper.isUsingChroma() && evt.input.getItem() instanceof ItemOmniwrench && ((ItemOmniwrench) evt.input.getItem()).supportsChroma(evt.input))
		{
			for (int i = 0; i < ColorUtils.colorCodes.length; ++i)
			{
				if (OreDictionary.containsMatch(true, OreDictionary.getOres(ColorUtils.oreDict[i]), evt.mod))
				{
					if (ItemNBTHelper.getInteger(evt.input.copy(), "COLORS", "COLOR", 16777215) == ColorUtils.colorCodes[i])
					{
						return;
					}
					ItemStack stack = evt.input.copy();
					ItemNBTHelper.setInteger(stack, "COLORS", "COLOR", ColorUtils.colorCodes[i]);
					
					evt.materialCost=1;
					evt.output=stack;
					return;
				}
			}
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
			return;
		}
		if (evt.input.getItem() instanceof ItemOmniwrench && evt.mod.getItem() == ModItems.itemShaderRemover)
		{
			ItemStack stackShader = ItemUtil.getItemFromStack(evt.input, "SHADER");
			if (stackShader == null || stackShader.isEmpty()) return;
			
			ItemStack stack = evt.input.copy();
			ItemUtil.removeAllItemFromStack(stack, "SHADER");
			evt.materialCost=0;
			evt.output=stack;
			return;
		}
	}
}