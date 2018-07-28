package com.nhave.tow.common.helper;

import java.util.ArrayList;
import java.util.List;

import com.nhave.lib.library.helper.ItemHelper;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DismantleHelper
{
	private static final List<String> BLOCKS = new ArrayList<String>();
	
	/**
	 * Allows the OmniWrench to pickup the {@link Block} when Sneak-Right-Clicked.
	 * Add <b>/n</b> to the end to prevent the block from dropping additional Items.
	 * Add <b>/p</b> to create a Silk Touch effect for the Block.
	 * 
	 * @param name The Blocks Resource Name <b>'minecraft:dirt'</b>
	 */
	public static void addBlock(String name)
	{
		BLOCKS.add(name);
	}
	
	public static void dismantleBlock(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer player, boolean noDrops)
	{
		Block block = blockState.getBlock();
		TileEntity tile = world.getTileEntity(blockPos);
		
		block.harvestBlock(world, player, blockPos, blockState, tile, player.getHeldItemMainhand());
    	if (!noDrops) block.onBlockHarvested(world, blockPos, blockState, player);
	    world.setBlockToAir(blockPos);
	}
	
	public static void dismantleBlockPure(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer player, boolean forceDrops)
	{
		Block block = blockState.getBlock();
		TileEntity tile = world.getTileEntity(blockPos);
		
		if (!world.isRemote) ItemHelper.dropItemInWorld(world, blockPos, new ItemStack(block));
    	block.onBlockHarvested(world, blockPos, blockState, player);
	    world.setBlockToAir(blockPos);
	}
	
	/**
	 * Checks to see if the block can be picked up.
	 * 
	 * @param block
	 * @param name
	 * @return
	 */
	public static boolean checkBlock(Block block, String name)
	{
		return (block.getRegistryName().getResourceDomain() + ":" + block.getRegistryName().getResourcePath()).equals(name); 
	}
	
	/**
	 * Called by the OmniWrench upon Sneak-Right-Clicking a {@link Block}.
	 * 
	 * @param world
	 * @param blockPos
	 * @param blockState
	 * @param player
	 * @return
	 */
	public static boolean customDismantle(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer player)
	{
		Block block = blockState.getBlock();
	    
	    for (int i = 0; i < BLOCKS.size(); ++i)
	    {
	    	boolean noAditionalDrops = BLOCKS.get(i).contains("/n");
	    	boolean pureDrop = BLOCKS.get(i).contains("/p");
	    	
	    	String blockName = BLOCKS.get(i).replaceAll("/n", "").replaceAll("/p", "");
	    	if (checkBlock(block, blockName))
	    	{
	    		if (pureDrop) dismantleBlockPure(world, blockPos, blockState, player, !noAditionalDrops);
	    		else dismantleBlock(world, blockPos, blockState, player, !noAditionalDrops);
	    		return true;
	    	}
	    }
		
		return false;
	}
}