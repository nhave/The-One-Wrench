package com.nhave.tow.common.integration.mode;

import java.util.List;

import com.nhave.lib.common.network.Key;
import com.nhave.lib.library.item.IKeyBound;
import com.nhave.lib.library.util.StringUtils;
import com.nhave.tow.common.content.ModItems;
import com.nhave.tow.common.integration.handler.EmbersHandler;
import com.nhave.tow.common.wrenchmode.WrenchMode;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WrenchModeEmbers extends WrenchMode implements IKeyBound
{
	public WrenchModeEmbers(String name)
	{
		super(name);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
	{
		ModItems.modeTune.addInformation(stack, player, list, flag);
		if (stack.hasTagCompound())
		{
			if (stack.getTagCompound().hasKey(EmbersHandler.NBT_TARGET_X) && stack.getTagCompound().hasKey(EmbersHandler.NBT_TARGET_Y) && stack.getTagCompound().hasKey(EmbersHandler.NBT_TARGET_Z))
			{
				list.add(StringUtils.formatLocal("embers.tooltip.targetingBlock", StringUtils.GRAY));
				list.add(StringUtils.format(" X=" + stack.getTagCompound().getInteger(EmbersHandler.NBT_TARGET_X), StringUtils.GRAY));
				list.add(StringUtils.format(" Y=" + stack.getTagCompound().getInteger(EmbersHandler.NBT_TARGET_Y), StringUtils.GRAY));
				list.add(StringUtils.format(" Z=" + stack.getTagCompound().getInteger(EmbersHandler.NBT_TARGET_Z), StringUtils.GRAY));
			}
		}
	}
	
	@Override
	public boolean isKeyInUse(ItemStack itemStack, Key key)
	{
		return ((IKeyBound) ModItems.modeTune).isKeyInUse(itemStack, key);
	}
	
	@Override
	public void doKeyBindingAction(EntityPlayer player, ItemStack stack, Key key, boolean chat)
	{
		((IKeyBound) ModItems.modeTune).doKeyBindingAction(player, stack, key, chat);
	}
}