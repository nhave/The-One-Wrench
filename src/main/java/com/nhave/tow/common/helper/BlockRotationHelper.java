package com.nhave.tow.common.helper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockEndRod;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.BlockTripWireHook;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRotationHelper
{
	/**
	 * Rotates Vanilla blocks and extensions using the Unity Ruleset.
	 * 
	 * @param player The player rotating the block.
	 * @param world The World holding the block.
	 * @param pos The position of the block.
	 * 
	 * @return EnumActionResult.SUCCESS if the block is rotated, EnumActionResult.FAIL if block should not be rotated
	 * and EnumActionResult.PASS if nothing has happened.
	 */
	public static EnumActionResult rotateBlock(EntityPlayer player, World world, BlockPos pos)
	{
        IBlockState state = world.getBlockState(pos);
	    Block block = state.getBlock();
	    
	    //Switches Half position if the player is sneaking
	    if (block instanceof BlockSlab && player.isSneaking())
	    {
	    	BlockSlab blockA = ((BlockSlab) block);
	    	world.setBlockState(pos, state.cycleProperty(blockA.HALF));
	    	
	    	return EnumActionResult.SUCCESS;
	    }
	    //Switches Half position if the player is sneaking
	    if (block instanceof BlockStairs)
	    {
	    	BlockStairs blockA = ((BlockStairs) block);
	    	if (player.isSneaking()) world.setBlockState(pos, state.cycleProperty(blockA.HALF));
	    	else rotateBlockHorizontal(world, pos);
	    	
	    	return EnumActionResult.SUCCESS;
	    }
	    if (block instanceof BlockHopper)
	    {
	    	BlockHopper blockA = ((BlockHopper) block);
	    	
	    	EnumFacing newFacing = state.getValue(blockA.FACING);
	    	if (newFacing == EnumFacing.NORTH) newFacing = EnumFacing.EAST;
			else if (newFacing == EnumFacing.EAST) newFacing = EnumFacing.SOUTH;
			else if (newFacing == EnumFacing.SOUTH) newFacing = EnumFacing.WEST;
			else if (newFacing == EnumFacing.WEST) newFacing = EnumFacing.DOWN;
			else if (newFacing == EnumFacing.DOWN) newFacing = EnumFacing.NORTH;
	    	
	    	state = state.withProperty(blockA.FACING, newFacing);
	    	world.setBlockState(pos, state);
	    	return EnumActionResult.SUCCESS;
	    }
	    //Rotates pistons when they are not exteded
	    if (block instanceof BlockPistonBase)
	    {
	    	BlockPistonBase blockA = ((BlockPistonBase) block);
	    	
	    	if (!state.getValue(blockA.EXTENDED))
	    	{
	    		rotateBlockDirectional(world, pos);
	    		return EnumActionResult.SUCCESS;
	    	}
	    }
	    //Unity Rotation for Shulker Boxes that doesnt use BlockDirectional.FACING
	    if (block instanceof BlockShulkerBox)
	    {
	    	BlockShulkerBox blockA = ((BlockShulkerBox) block);
	    	
	    	EnumFacing newFacing = state.getValue(blockA.FACING);
	    	if (newFacing == EnumFacing.NORTH) newFacing = EnumFacing.EAST;
			else if (newFacing == EnumFacing.EAST) newFacing = EnumFacing.SOUTH;
			else if (newFacing == EnumFacing.SOUTH) newFacing = EnumFacing.WEST;
			else if (newFacing == EnumFacing.WEST) newFacing = EnumFacing.UP;
			else if (newFacing == EnumFacing.UP) newFacing = EnumFacing.DOWN;
			else if (newFacing == EnumFacing.DOWN) newFacing = EnumFacing.NORTH;
	    	
	    	state = state.withProperty(blockA.FACING, newFacing);
	    	world.setBlockState(pos, state);
	    	return EnumActionResult.SUCCESS;
	    }
	    //Allows rotating Double Chests
	    if (block instanceof BlockChest)
	    {
	    	BlockChest blockA = ((BlockChest) block);
	    	
	    	if (blockA.canPlaceBlockAt(world, pos))
	    	{
	    		rotateBlockHorizontal(world, pos);
	    	}
	    	else
	        {
	            for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
	            {
	                if (world.getBlockState(pos.offset(enumfacing)).getBlock() == blockA)
	                {
	        	    	BlockPos posB = pos.offset(enumfacing);
	        	        IBlockState stateB = world.getBlockState(posB);
	        	        
	        	    	EnumFacing newFacing = state.getValue(blockA.FACING).getOpposite();

	        	    	world.setBlockState(pos, state.withProperty(blockA.FACING, newFacing));
	        	    	world.setBlockState(posB, stateB.withProperty(blockA.FACING, newFacing));
	                }
	            }
	        }
	    	
	    	return EnumActionResult.SUCCESS;
	    }
	    //Unity Rotation for blocks using BlockDirectional.FACING
	    if (block == Blocks.DISPENSER || block == Blocks.DROPPER || block == Blocks.OBSERVER)
	    {
	    	rotateBlockDirectional(world, pos);
	    	return EnumActionResult.SUCCESS;
	    }
	    //Prevent vanilla rotation on these blocks
	    if (block instanceof BlockTorch || block instanceof BlockLadder || block instanceof BlockEndRod || block instanceof BlockButton || block instanceof BlockTripWireHook)
	    {
	    	return EnumActionResult.FAIL;
	    }
		
		return EnumActionResult.PASS;
	}
	
	/**
	 * Rotate a block using BlockDirectional.FACING.
	 * Unity rotation [N,E,S,W,U,D]
	 * 
	 * @param world The World holding the block.
	 * @param pos The position of the block.
	 */
	public static void rotateBlockDirectional(World world, BlockPos pos)
	{
        IBlockState state = world.getBlockState(pos);
	    
		EnumFacing newFacing = state.getValue(BlockDirectional.FACING);
    	if (newFacing == EnumFacing.NORTH) newFacing = EnumFacing.EAST;
		else if (newFacing == EnumFacing.EAST) newFacing = EnumFacing.SOUTH;
		else if (newFacing == EnumFacing.SOUTH) newFacing = EnumFacing.WEST;
		else if (newFacing == EnumFacing.WEST) newFacing = EnumFacing.UP;
		else if (newFacing == EnumFacing.UP) newFacing = EnumFacing.DOWN;
		else if (newFacing == EnumFacing.DOWN) newFacing = EnumFacing.NORTH;
    	
    	state = state.withProperty(BlockDirectional.FACING, newFacing);
    	world.setBlockState(pos, state);
	}
	
	/**
	 * Rotate a block using BlockHorizontal.FACING.
	 * Unity rotation [N,E,S,W]
	 * 
	 * @param world The World holding the block.
	 * @param pos The position of the block.
	 */
	public static void rotateBlockHorizontal(World world, BlockPos pos)
	{
        IBlockState state = world.getBlockState(pos);
	    
		EnumFacing newFacing = state.getValue(BlockHorizontal.FACING);
    	if (newFacing == EnumFacing.NORTH) newFacing = EnumFacing.EAST;
		else if (newFacing == EnumFacing.EAST) newFacing = EnumFacing.SOUTH;
		else if (newFacing == EnumFacing.SOUTH) newFacing = EnumFacing.WEST;
		else if (newFacing == EnumFacing.WEST) newFacing = EnumFacing.NORTH;
    	
    	state = state.withProperty(BlockHorizontal.FACING, newFacing);
    	world.setBlockState(pos, state);
	}
}