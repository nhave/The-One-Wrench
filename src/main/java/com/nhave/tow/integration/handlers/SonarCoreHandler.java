package com.nhave.tow.integration.handlers;

import com.nhave.tow.api.integration.WrenchHandler;
import com.nhave.tow.api.wrenchmodes.WrenchMode;
import com.nhave.tow.registry.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import sonar.core.api.blocks.IWrenchable;
import sonar.core.common.block.SonarBlock;
import sonar.core.utils.IMachineSides;

public class SonarCoreHandler extends WrenchHandler
{
	@Override
	public EnumActionResult onWrenchUseFirst(WrenchMode mode, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
		TileEntity tile = world.getTileEntity(pos);
        IBlockState state = world.getBlockState(pos);
	    Block block = state.getBlock();
	    
		if (block instanceof SonarBlock)
		{
			SonarBlock sBLock = (SonarBlock) block;
			
			if (mode == ModItems.modeWrench)
			{
				if (block instanceof IWrenchable && ((IWrenchable) block).canWrench(player, world, pos))
				{
					if (player.isSneaking())
					{
						((IWrenchable) block).wrenchBlock(player, world, pos, true);
						if (!world.isRemote) return EnumActionResult.SUCCESS;
						else
						{
							player.playSound(block.getSoundType(state, world, pos, player).getPlaceSound(), 1.0F, 0.6F);
							player.swingArm(EnumHand.MAIN_HAND);
						}
					}
					else if (block.rotateBlock(world, pos, side) && sBLock.orientation)
				    {
						world.markChunkDirty(pos, null);
				    	if (!world.isRemote) return EnumActionResult.SUCCESS;
						else
						{
							player.playSound(block.getSoundType(state, world, pos, player).getPlaceSound(), 1.0F, 0.6F);
							player.swingArm(EnumHand.MAIN_HAND);
						}
				    }
				}
			}
			else if (mode == ModItems.modeTune)
			{
				if (tile != null && tile instanceof IMachineSides)
				{
					((IMachineSides) tile).getSideConfigs().increaseSide(side);
					if (!world.isRemote) return EnumActionResult.SUCCESS;
					else
					{
						player.playSound(block.getSoundType(state, world, pos, player).getPlaceSound(), 1.0F, 0.6F);
						player.swingArm(EnumHand.MAIN_HAND);
					}
				}
			}
		}
		return EnumActionResult.PASS;
	}
	
	@Override
	public boolean preventBlockRotation(EntityPlayer player, World world, BlockPos pos)
	{
        IBlockState state = world.getBlockState(pos);
	    Block block = state.getBlock();
		return block instanceof SonarBlock;
	}
	
	@Override
	public boolean shouldDenyBlockActivate(WrenchMode mode, EntityPlayer player, World world, BlockPos pos)
	{
        IBlockState state = world.getBlockState(pos);
	    Block block = state.getBlock();
		return (mode == ModItems.modeWrench && block instanceof SonarBlock && ((SonarBlock) block).orientation);
	}
}