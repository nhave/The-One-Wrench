package com.nhave.tow.integration.handlers;

import com.nhave.tow.api.integration.WrenchHandler;
import com.nhave.tow.api.wrenchmodes.WrenchMode;
import com.nhave.tow.helpers.DismantleHelper;
import com.nhave.tow.registry.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vazkii.arl.block.tile.TileSimpleInventory;

public class PSIHandler extends WrenchHandler
{
	@Override
	public EnumActionResult onWrenchUseFirst(WrenchMode mode, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
		TileEntity tile = world.getTileEntity(pos);
        IBlockState state = world.getBlockState(pos);
	    Block block = state.getBlock();
	    
		if (mode == ModItems.modeWrench && (DismantleHelper.checkBlock(block, "psi:cad_assembler") || DismantleHelper.checkBlock(block, "psi:programmer")))
		{
			if (player.isSneaking())
			{
				if (!world.isRemote && DismantleHelper.checkBlock(block, "psi:cad_assembler") && tile instanceof TileSimpleInventory)
				{
					for (int i = 1; i < 7; ++i)
					{
						ItemStack slot = ((TileSimpleInventory) tile).getStackInSlot(i);
						if (slot != null && !slot.isEmpty()) DismantleHelper.dropBlockAsItem(world, pos.getX(), pos.getY(), pos.getZ(), slot.copy());
					}
				}
				DismantleHelper.dismantleBlock(world, pos, state, player, true);
    			if (!world.isRemote) return EnumActionResult.SUCCESS;
				else
				{
					player.playSound(block.getSoundType(state, world, pos, player).getPlaceSound(), 1.0F, 0.6F);
					player.swingArm(EnumHand.MAIN_HAND);
				}
			}
			else
			{
				if (block.rotateBlock(world, pos, side))
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
		return EnumActionResult.PASS;
	}
	
	@Override
	public boolean preventBlockRotation(EntityPlayer player, World world, BlockPos pos)
	{
        IBlockState state = world.getBlockState(pos);
	    Block block = state.getBlock();
		return (DismantleHelper.checkBlock(block, "psi:cad_assembler") || DismantleHelper.checkBlock(block, "psi:programmer"));
	}
	
	@Override
	public boolean shouldDenyBlockActivate(WrenchMode mode, EntityPlayer player, World world, BlockPos pos)
	{
        IBlockState state = world.getBlockState(pos);
	    Block block = state.getBlock();
		return (mode == ModItems.modeWrench && (DismantleHelper.checkBlock(block, "psi:cad_assembler") || DismantleHelper.checkBlock(block, "psi:programmer")));
	}
}