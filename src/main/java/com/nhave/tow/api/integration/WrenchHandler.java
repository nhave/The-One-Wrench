package com.nhave.tow.api.integration;

import com.nhave.tow.api.wrenchmodes.WrenchMode;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class WrenchHandler
{
	/**
	 * Called when Right-Clicking any block with the OmniWrench.
	 * 
	 * @param mode
	 * @param player
	 * @param world
	 * @param pos
	 * @param side
	 * @param hitX
	 * @param hitY
	 * @param hitZ
	 * @param hand
	 * @return
	 */
	public abstract EnumActionResult onWrenchUseFirst(WrenchMode mode, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand);
	
	/**
	 * Prevents onBlockActivated from being called after the OmniWrench has been used.
	 * 
	 * @param mode
	 * @param player
	 * @param world
	 * @param pos
	 * @return
	 */
	public boolean shouldDenyBlockActivate(WrenchMode mode, EntityPlayer player, World world, BlockPos pos)
	{
		return false;
	}
	
	/**
	 * Prevents the OmniWrench from using its default rotation method.
	 * 
	 * @param player
	 * @param world
	 * @param pos
	 * @return
	 */
	public boolean preventBlockRotation(EntityPlayer player, World world, BlockPos pos)
	{
		return false;
	}
}
