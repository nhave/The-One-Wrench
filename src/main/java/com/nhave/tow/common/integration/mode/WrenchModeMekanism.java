package com.nhave.tow.common.integration.mode;

import java.util.List;

import com.nhave.lib.common.network.Key;
import com.nhave.lib.library.helper.ItemNBTHelper;
import com.nhave.lib.library.item.IHudItem;
import com.nhave.lib.library.item.IKeyBound;
import com.nhave.lib.library.util.StringUtils;
import com.nhave.tow.common.integration.handler.MekanismHandler;
import com.nhave.tow.common.wrenchmode.WrenchMode;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WrenchModeMekanism extends WrenchMode implements IKeyBound, IHudItem
{
	public WrenchModeMekanism(String name)
	{
		super(name);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced)
	{
		list.add(StringUtils.localize("tooltip.tow.mouse.use") + " " + StringUtils.format("LALT" +  "+" + StringUtils.localize("tooltip.tow.mouse.wheel"), StringUtils.ORANGE, StringUtils.ITALIC));
		//list.add(StringUtils.localize("tooltip.tow.mode.press") + " " + StringUtils.format(Minecraft.getMinecraft().gameSettings.keyBindSneak.getDisplayName() +  "+" + KeyBinds.toggleItem.getDisplayName(), StringUtils.YELLOW, StringUtils.ITALIC));
		list.add(" - " + StringUtils.localize("tooltip.tow.mekastate"));
		list.add(StringUtils.localize("gui.state") + ": " + StringUtils.format(MekanismHandler.getStateDisplay(MekanismHandler.getState(stack)), StringUtils.ORANGE, StringUtils.ITALIC));
	}
	
	@Override
	public boolean isKeyInUse(ItemStack itemStack, Key key)
	{
		return (key == Key.TOGGLE_ITEM || key == Key.SCROLLUP_ALT || key == Key.SCROLLDN_ALT);
	}
	
	@Override
	public void doKeyBindingAction(EntityPlayer player, ItemStack stack, Key key, boolean chat)
	{
		int currentState = Math.max(0, Math.min(4, ItemNBTHelper.getInteger(stack, "WRENCH", "MEKASTATE", 0)));
		int newState = (key == Key.TOGGLE_ITEM || key == Key.SCROLLUP_ALT ? (currentState >= 4 ? 0 : currentState + 1) : (currentState <= 0 ? 4 : currentState - 1));
		ItemNBTHelper.setInteger(stack, "WRENCH", "MEKASTATE", newState);
		player.sendStatusMessage(new TextComponentString(TextFormatting.GOLD + new TextComponentTranslation("gui.state").getUnformattedComponentText()).appendText(": ").appendSibling(new TextComponentString(MekanismHandler.getStateDisplay(MekanismHandler.getState(stack)))), true);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addHudInfo(ItemStack stack, EntityPlayer player, List list, boolean isArmor)
	{
		list.add(StringUtils.localize("gui.state") + ": " + StringUtils.format(MekanismHandler.getStateDisplay(MekanismHandler.getState(stack)), StringUtils.YELLOW, StringUtils.ITALIC));
	}
}