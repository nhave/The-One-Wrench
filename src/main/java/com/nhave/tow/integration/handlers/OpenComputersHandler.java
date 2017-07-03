package com.nhave.tow.integration.handlers;

import com.nhave.tow.api.integration.WrenchHandler;
import com.nhave.tow.api.wrenchmodes.WrenchMode;
import com.nhave.tow.helpers.DismantleHelper;
import com.nhave.tow.registry.ModItems;

import li.cil.oc.common.block.Screen;
import li.cil.oc.common.block.SimpleBlock;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OpenComputersHandler extends WrenchHandler
{
	@Override
	public EnumActionResult onWrenchUseFirst(WrenchMode mode, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
	    IBlockState bs = world.getBlockState(pos);
	    Block block = bs.getBlock();
	    
	    if ((mode == ModItems.modeWrench || mode == ModItems.modeRotate) && block instanceof SimpleBlock && !isBlockIgnored(block))
	    {
	    	if (mode == ModItems.modeRotate)
		    {
		    	return EnumActionResult.FAIL;
		    }
	    	
	    	if (player.isSneaking())
			{
	    		DismantleHelper.dismantleBlock(world, pos, bs, player, true);
				player.swingArm(EnumHand.MAIN_HAND);
				if (!world.isRemote) return EnumActionResult.SUCCESS;
				else
				{
					player.playSound(block.getSoundType(bs, world, pos, player).getPlaceSound(), 1.0F, 0.6F);
					player.swingArm(EnumHand.MAIN_HAND);
				}
			}
	    	else
	    	{
	    		if (block.rotateBlock(world, pos, side))
			    {
			    	if (!world.isRemote) return EnumActionResult.SUCCESS;
					else
					{
						player.playSound(block.getSoundType(bs, world, pos, player).getPlaceSound(), 1.0F, 0.6F);
						player.swingArm(EnumHand.MAIN_HAND);
					}
			    }
	    		else if (block.rotateBlock(world, pos, EnumFacing.UP))
			    {
			    	if (!world.isRemote) return EnumActionResult.SUCCESS;
					else
					{
						player.playSound(block.getSoundType(bs, world, pos, player).getPlaceSound(), 1.0F, 0.6F);
						player.swingArm(EnumHand.MAIN_HAND);
					}
			    }
	    	}
		}
		return EnumActionResult.PASS;
	}
	
	public static boolean isBlockIgnored(Block block)
	{
		return DismantleHelper.checkBlock(block, "opencomputers:endstone") || DismantleHelper.checkBlock(block, "opencomputers:chameliumblock");
	}
	
	@Override
	public boolean shouldDenyBlockActivate(WrenchMode mode, EntityPlayer player, World world, BlockPos pos)
	{
	    IBlockState state = world.getBlockState(pos);
	    Block block = state.getBlock();
	    
		return (mode == ModItems.modeWrench || mode == ModItems.modeRotate) && (block instanceof Screen);
	}
}
