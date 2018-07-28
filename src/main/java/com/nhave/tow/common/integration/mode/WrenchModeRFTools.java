package com.nhave.tow.common.integration.mode;

import java.util.List;

import com.nhave.lib.common.network.Key;
import com.nhave.lib.library.item.IKeyBound;
import com.nhave.lib.library.util.StringUtils;
import com.nhave.tow.common.content.ModItems;
import com.nhave.tow.common.integration.handler.RFToolsHandler;
import com.nhave.tow.common.wrenchmode.WrenchMode;

import mcjty.lib.varia.BlockPosTools;
import mcjty.lib.varia.GlobalCoordinate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WrenchModeRFTools extends WrenchMode implements IKeyBound
{
	public WrenchModeRFTools(String name)
	{
		super(name);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
	{
		ModItems.modeTune.addInformation(stack, player, list, flag);
		GlobalCoordinate b = RFToolsHandler.getCurrentBlock(stack);
        if (b != null)
        {
            list.add(StringUtils.format("Block: " + BlockPosTools.toString(b.getCoordinate()) + " at dimension " + b.getDimension(), StringUtils.GRAY));
        };
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