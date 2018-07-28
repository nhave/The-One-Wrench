package com.nhave.tow.common.integration.handler;

import com.nhave.tow.common.content.ModItems;
import com.nhave.tow.common.helper.DismantleHelper;
import com.nhave.tow.common.integration.WrenchHandler;
import com.nhave.tow.common.wrenchmode.WrenchMode;

import net.bdew.gendustry.machines.BaseMachineBlock;
import net.bdew.gendustry.machines.apiary.TileApiary;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GendustryHandler extends WrenchHandler
{
	@Override
	public EnumActionResult onWrenchUseFirst(WrenchMode mode, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
        IBlockState state = world.getBlockState(pos);
	    Block block = state.getBlock();
		TileEntity tile = world.getTileEntity(pos);
	    
	    if (mode == ModItems.modeWrench)
	    {
	    	if (block instanceof BaseMachineBlock || tile instanceof TileApiary)
	    	{
	    		if (player.isSneaking())
				{
		    		DismantleHelper.dismantleBlock(world, pos, state, player, true);
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
