package com.nhave.tow.integration;

import com.nhave.tow.wrenchmodes.WrenchMode;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class WrenchHandler
{
	public abstract EnumActionResult onWrenchUseFirst(WrenchMode mode, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand);
	
	public boolean shouldDenyBlockActivate(WrenchMode mode, EntityPlayer player, World world, BlockPos pos)
	{
		return false;
	}
}
