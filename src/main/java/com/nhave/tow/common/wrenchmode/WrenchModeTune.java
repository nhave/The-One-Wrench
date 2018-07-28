package com.nhave.tow.common.wrenchmode;

import java.util.List;

import com.nhave.lib.common.network.Key;
import com.nhave.lib.common.network.KeyBinds;
import com.nhave.lib.library.item.IKeyBound;
import com.nhave.lib.library.util.StringUtils;
import com.nhave.tow.common.integration.IntegrationRegistry;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class WrenchModeTune extends WrenchMode implements IKeyBound
{
	public WrenchModeTune(String name)
	{
		super(name);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
	{
		if (KeyBinds.toggleItem.getKeyCode() != 0)
		{
			list.add(StringUtils.localize("tooltip.tow.mode.press") + " " + StringUtils.format(Minecraft.getMinecraft().gameSettings.keyBindSneak.getDisplayName() +  "+" + KeyBinds.toggleItem.getDisplayName(), StringUtils.ORANGE, StringUtils.ITALIC));
			list.add(" - " + StringUtils.localize("tooltip.tow.wipe"));
		}
	}
	
	@Override
	public boolean isKeyInUse(ItemStack itemStack, Key key)
	{
		return (key == Key.TOGGLE_ITEM);
	}
	
	@Override
	public void doKeyBindingAction(EntityPlayer player, ItemStack stack, Key key, boolean chat)
	{
		boolean result = false;
		for (int i = 0; i < IntegrationRegistry.getCount(); ++i)
	    {
			if (IntegrationRegistry.getHandler(i) instanceof IDataWipe)
			{
				if (((IDataWipe) IntegrationRegistry.getHandler(i)).wipeData(stack)) result = true;
			}
	    }
		if (result) player.sendStatusMessage(new TextComponentString(TextFormatting.GOLD + new TextComponentTranslation("tooltip.tow.wipe.complete").getUnformattedComponentText()).appendText("!"), true);
		if (chat && result) player.sendMessage(new TextComponentTranslation("tooltip.tow.wipe.complete").appendText("!"));
	}
}