package com.nhave.tow.integration.modes;

import java.util.List;

import com.nhave.nhc.api.items.IKeyBound;
import com.nhave.nhc.network.Key;
import com.nhave.nhc.network.KeyBinds;
import com.nhave.nhc.util.StringUtils;
import com.nhave.tow.api.integration.IDataWipe;
import com.nhave.tow.api.wrenchmodes.WrenchMode;
import com.nhave.tow.integration.WrenchRegistry;
import com.nhave.tow.integration.handlers.RFToolsHandler;

import mcjty.lib.varia.BlockPosTools;
import mcjty.lib.varia.GlobalCoordinate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class WrenchModeRFTools extends WrenchMode implements IKeyBound
{
	public WrenchModeRFTools(String name, ItemStack icon)
	{
		super(name, icon);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
	{
		list.add(StringUtils.localize("tooltip.tow.mode.press") + " " + StringUtils.format(StringUtils.localize("tooltip.nhc.details.shift2") +  "+" + KeyBinds.toggle.getDisplayName(), StringUtils.YELLOW, StringUtils.ITALIC));
		list.add(" - " + StringUtils.localize("tooltip.tow.wipe"));
		GlobalCoordinate b = RFToolsHandler.getCurrentBlock(stack);
        if (b != null)
        {
            list.add(TextFormatting.GREEN + "Block: " + BlockPosTools.toString(b.getCoordinate()) + " at dimension " + b.getDimension());
        };
	}
	
	@Override
	public void doKeyBindingAction(EntityPlayer player, ItemStack stack, Key key, boolean chat)
	{
		boolean result = false;
		for (int i = 0; i < WrenchRegistry.getCount(); ++i)
	    {
			if (WrenchRegistry.getHandler(i) instanceof IDataWipe)
			{
				if (((IDataWipe) WrenchRegistry.getHandler(i)).wipeData(stack)) result = true;
			}
	    }
		if (chat && result) player.sendMessage(new TextComponentTranslation("tooltip.tow.wipe.complete").appendText("!"));
	}
}