package com.nhave.tow.integration.handlers;

import com.nhave.tow.api.integration.WrenchHandler;
import com.nhave.tow.api.wrenchmodes.WrenchMode;
import com.nhave.tow.helpers.DismantleHelper;
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

public class CyberwareHandler extends WrenchHandler
{
	@Override
	public EnumActionResult onWrenchUseFirst(WrenchMode mode, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
		TileEntity tile = world.getTileEntity(pos);
        IBlockState state = world.getBlockState(pos);
	    Block block = state.getBlock();
	    
	    //Checking current block with need of the Mod itself.
	    String modName = block.getRegistryName().getResourceDomain();
	    String blockName = block.getRegistryName().getResourcePath();
	    
	    //The Blocks "component_box" and "radio_post" is not machines and should be ignored.
	    if (mode == ModItems.modeWrench && modName.equals("cyberware") && !blockName.equals("component_box") && !blockName.equals("radio_post"))
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
	    	else if (canRotateBlock(player, world, pos))
			{
				if (block.rotateBlock(world, pos, side))
			    {
					//Rotating Multiblocks using Bit Comparison. (n & 1)
					if (blockName.equals("surgery_chamber") || blockName.equals("engineering_table"))
					{
						//Finds the other part of this Multiblock and calls "block.rotateBlock(...)" on that as well.
						BlockPos newPos = ((block.getMetaFromState(state) & 1) > 0 ? pos.down() : pos.up());
						block.rotateBlock(world, newPos, side);
					}
					
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
	public boolean shouldDenyBlockActivate(WrenchMode mode, EntityPlayer player, World world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos);
	    Block block = state.getBlock();
	    
		return (block.getRegistryName().getResourceDomain().equals("cyberware") && ((mode == ModItems.modeWrench && canRotateBlock(player, world, pos)) || (mode == ModItems.modeRotate && block.getRegistryName().getResourcePath().equals("component_box"))));
	}
	
	@Override
	public boolean preventBlockRotation(EntityPlayer player, World world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos);
	    Block block = state.getBlock();
	    
		return (block.getRegistryName().getResourceDomain().equals("cyberware") && canRotateBlock(player, world, pos));
	}
	
	public boolean canRotateBlock(EntityPlayer player, World world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos);
	    Block block = state.getBlock();
	    String blockName = block.getRegistryName().getResourcePath();
	    
	    //These Blocks will be treated with the Unity Ruleset "Machine" and is therefore rotated in "Wrench Mode".
		return (blockName.equals("beacon") || blockName.equals("beacon_large") || blockName.equals("engineering_table") || blockName.equals("surgery_chamber") || blockName.equals("blueprint_archive"));
	}
}