package com.nhave.tow.integration.modes;

import java.util.List;

import com.nhave.tow.integration.handlers.RFToolsHandler;
import com.nhave.tow.wrenchmodes.WrenchMode;

import mcjty.lib.varia.BlockPosTools;
import mcjty.lib.varia.GlobalCoordinate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public class WrenchModeRFTools extends WrenchMode
{
	public WrenchModeRFTools(String name, ItemStack icon)
	{
		super(name, icon);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
	{
		GlobalCoordinate b = RFToolsHandler.getCurrentBlock(stack);
        if (b != null)
        {
            list.add(TextFormatting.GREEN + "Block: " + BlockPosTools.toString(b.getCoordinate()) + " at dimension " + b.getDimension());
        };
	}
}