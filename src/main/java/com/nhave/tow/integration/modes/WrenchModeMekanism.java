package com.nhave.tow.integration.modes;

import java.util.List;

import com.nhave.nhc.api.items.IHudItem;
import com.nhave.nhc.api.items.IKeyBound;
import com.nhave.nhc.helpers.ItemNBTHelper;
import com.nhave.nhc.network.Key;
import com.nhave.nhc.network.KeyBinds;
import com.nhave.nhc.util.StringUtils;
import com.nhave.tow.api.wrenchmodes.WrenchMode;
import com.nhave.tow.integration.handlers.MekanismHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WrenchModeMekanism extends WrenchMode implements IKeyBound, IHudItem
{
	public WrenchModeMekanism(String name, ItemStack icon)
	{
		super(name, icon);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced)
	{
		//list.add(EnumColor.PINK + LangUtils.localize("gui.state") + ": " + MekanismHandler.getColor(MekanismHandler.getState(stack)) + MekanismHandler.getStateDisplay(MekanismHandler.getState(stack)));
		list.add(StringUtils.localize("tooltip.tow.mode.press") + " " + StringUtils.format(Minecraft.getMinecraft().gameSettings.keyBindSneak.getDisplayName() +  "+" + KeyBinds.toggle.getDisplayName(), StringUtils.YELLOW, StringUtils.ITALIC));
		list.add(" - " + StringUtils.localize("tooltip.tow.mekastate"));
		list.add(StringUtils.localize("gui.state") + ": " + StringUtils.format(MekanismHandler.getStateDisplay(MekanismHandler.getState(stack)), StringUtils.YELLOW, StringUtils.ITALIC));
	}
	
	@Override
	public void doKeyBindingAction(EntityPlayer player, ItemStack stack, Key key, boolean chat)
	{
		int currentState = Math.max(0, Math.min(4, ItemNBTHelper.getInteger(stack, "WRENCH", "MEKASTATE", 0)));
		int newState = (currentState >= 4 ? 0 : currentState + 1);
		ItemNBTHelper.setInteger(stack, "WRENCH", "MEKASTATE", newState);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addHudInfo(ItemStack stack, EntityPlayer player, List list, boolean isArmor)
	{
		list.add(StringUtils.localize("gui.state") + ": " + StringUtils.format(MekanismHandler.getStateDisplay(MekanismHandler.getState(stack)), StringUtils.YELLOW, StringUtils.ITALIC));
	}
}