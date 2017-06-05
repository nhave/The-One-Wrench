package com.nhave.tow.integration.modes;

import java.util.List;

import com.nhave.nhc.api.items.IKeyBound;
import com.nhave.nhc.network.Key;
import com.nhave.nhc.network.KeyBinds;
import com.nhave.nhc.util.StringUtils;
import com.nhave.tow.integration.IDataWipe;
import com.nhave.tow.integration.WrenchRegistry;
import com.nhave.tow.integration.handlers.EmbersHandler;
import com.nhave.tow.wrenchmodes.WrenchMode;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;

public class WrenchModeEmbers extends WrenchMode implements IKeyBound
{
	public WrenchModeEmbers(String name, ItemStack icon)
	{
		super(name, icon);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
	{
		list.add(StringUtils.localize("tooltip.tow.mode.press") + " " + StringUtils.format(StringUtils.localize("tooltip.nhc.details.shift2") +  "+" + KeyBinds.toggle.getDisplayName(), StringUtils.YELLOW, StringUtils.ITALIC) + " " + StringUtils.localize("tooltip.tow.wipe"));
		if (stack.hasTagCompound())
		{
			if (stack.getTagCompound().hasKey(EmbersHandler.NBT_TARGET_X) && stack.getTagCompound().hasKey(EmbersHandler.NBT_TARGET_Y) && stack.getTagCompound().hasKey(EmbersHandler.NBT_TARGET_Z))
			{
				list.add(StringUtils.localize("embers.tooltip.targetingBlock"));
				list.add(" X=" + stack.getTagCompound().getInteger(EmbersHandler.NBT_TARGET_X));
				list.add(" Y=" + stack.getTagCompound().getInteger(EmbersHandler.NBT_TARGET_Y));
				list.add(" Z=" + stack.getTagCompound().getInteger(EmbersHandler.NBT_TARGET_Z));
			}
		}
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