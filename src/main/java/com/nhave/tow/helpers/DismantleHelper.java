package com.nhave.tow.helpers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
	
	public static void dismantleBlock(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer player, boolean forceDrops)
	{
		Block block = blockState.getBlock();
    	List drops = block.getDrops(world, blockPos, blockState, 0);
    	if (block.removedByPlayer(blockState, world, blockPos, player, true)) block.breakBlock(world, blockPos, blockState);
	    world.setBlockToAir(blockPos);
        
        if (!world.isRemote && forceDrops)
        {
        	ArrayList<? extends ItemStack> items = (ArrayList<? extends ItemStack>) drops;
        	for (ItemStack stack : items)
        	{
        		dropBlockAsItem(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), stack);
            }
        }
	}
	
	public static void dismantleBlockPure(World world, BlockPos blockPos, IBlockState blockState, EntityPlayer player, boolean forceDrops)
	{
		Block block = blockState.getBlock();
		if (!world.isRemote) dropBlockAsItem(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), new ItemStack(block));
		dismantleBlock(world, blockPos, blockState, player, forceDrops);
	}
	
	/**
	 * Drops the {@link Block} as an {@link Item} in the {@link World}.
	 * 
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param stack
	 */
	public static void dropBlockAsItem(World world, int x, int y, int z, ItemStack stack)
	{
		float f = 0.3F;
    	double x2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
    	double y2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
    	double z2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
    	EntityItem theItem = new EntityItem(world, x + x2, y + y2, z + z2, stack);
    	theItem.setDefaultPickupDelay();
    	world.spawnEntity(theItem);
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
	 * Called by the OmniWrench upon Sneak-Right-Clinking a {@link Block}.
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