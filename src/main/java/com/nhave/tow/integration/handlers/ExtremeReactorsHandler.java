package com.nhave.tow.integration.handlers;

import com.nhave.tow.api.integration.WrenchHandler;
import com.nhave.tow.api.wrenchmodes.WrenchMode;
import com.nhave.tow.helpers.DismantleHelper;
import com.nhave.tow.registry.ModItems;

import erogenousbeef.bigreactors.common.multiblock.tileentity.TileEntityMachinePart;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ExtremeReactorsHandler extends WrenchHandler
{
	@Override
	public EnumActionResult onWrenchUseFirst(WrenchMode mode, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
	    TileEntity tile = world.getTileEntity(pos);
	    IBlockState bs = world.getBlockState(pos);
	    Block block = bs.getBlock();
	    
		if (tile != null && tile instanceof TileEntityMachinePart)
		{
			if (mode != ModItems.modeWrench) return EnumActionResult.SUCCESS;
			else if (player.isSneaking())
			{
				DismantleHelper.dismantleBlock(world, pos, bs, player, true);
				if (!world.isRemote) return EnumActionResult.SUCCESS;
				else
				{
					SoundEvent soundName = block.getSoundType(bs, world, pos, player).getPlaceSound();
					player.playSound(soundName, 1.0F, 0.6F);
					player.swingArm(EnumHand.MAIN_HAND);
				}
			}
		}
		return EnumActionResult.PASS;
	}
}
