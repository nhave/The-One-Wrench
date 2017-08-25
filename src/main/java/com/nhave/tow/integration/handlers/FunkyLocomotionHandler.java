package com.nhave.tow.integration.handlers;

import com.nhave.tow.api.integration.WrenchHandler;
import com.nhave.tow.api.wrenchmodes.WrenchMode;
import com.nhave.tow.helpers.DismantleHelper;
import com.nhave.tow.registry.ModItems;
import com.rwtema.funkylocomotion.blocks.BlockBooster;
import com.rwtema.funkylocomotion.blocks.BlockFrame;
import com.rwtema.funkylocomotion.blocks.BlockPusher;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FunkyLocomotionHandler extends WrenchHandler
{
	@Override
	public EnumActionResult onWrenchUseFirst(WrenchMode mode, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
		TileEntity tile = world.getTileEntity(pos);
        IBlockState state = world.getBlockState(pos);
	    Block block = state.getBlock();
	    
	    if (mode == ModItems.modeWrench)
		{
	    	if (block instanceof BlockBooster || block instanceof BlockPusher || block instanceof BlockFrame)
	    	{
				if (player.isSneaking())
				{
					DismantleHelper.dismantleBlock(world, pos, state, player, false);
	    			if (!world.isRemote) return EnumActionResult.SUCCESS;
					else
					{
						player.playSound(block.getSoundType(state, world, pos, player).getPlaceSound(), 1.0F, 0.6F);
						player.swingArm(EnumHand.MAIN_HAND);
					}
				}
				else if (block instanceof BlockBooster || block instanceof BlockPusher)
				{
					EnumFacing newFacing = state.getValue(BlockDirectional.FACING);
	    	    	if (newFacing == EnumFacing.NORTH) newFacing = EnumFacing.EAST;
	    			else if (newFacing == EnumFacing.EAST) newFacing = EnumFacing.SOUTH;
	    			else if (newFacing == EnumFacing.SOUTH) newFacing = EnumFacing.WEST;
	    			else if (newFacing == EnumFacing.WEST) newFacing = EnumFacing.UP;
	    			else if (newFacing == EnumFacing.UP) newFacing = EnumFacing.DOWN;
	    			else if (newFacing == EnumFacing.DOWN) newFacing = EnumFacing.NORTH;
	    	    	
	    			IBlockState blockState = world.getBlockState(pos);
	    	    	blockState = blockState.withProperty(BlockDirectional.FACING, newFacing);
	    			world.setBlockState(pos, blockState);
	    			
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
}