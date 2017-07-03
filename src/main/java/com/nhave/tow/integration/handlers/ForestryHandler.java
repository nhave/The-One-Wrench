package com.nhave.tow.integration.handlers;

import com.nhave.tow.api.integration.WrenchHandler;
import com.nhave.tow.api.wrenchmodes.WrenchMode;
import com.nhave.tow.helpers.DismantleHelper;
import com.nhave.tow.registry.ModItems;

import forestry.apiculture.PluginApiculture;
import forestry.apiculture.blocks.BlockAlveary;
import forestry.arboriculture.PluginArboriculture;
import forestry.core.blocks.BlockBase;
import forestry.core.tiles.TileUtil;
import forestry.energy.EnergyHelper;
import forestry.energy.blocks.BlockEngine;
import forestry.factory.PluginFactory;
import forestry.factory.blocks.BlockFactoryPlain;
import forestry.factory.blocks.BlockFactoryTESR;
import forestry.farming.blocks.BlockFarm;
import forestry.greenhouse.blocks.BlockGreenhouse;
import forestry.lepidopterology.PluginLepidopterology;
import forestry.mail.blocks.BlockMail;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ForestryHandler extends WrenchHandler
{
	@Override
	public EnumActionResult onWrenchUseFirst(WrenchMode mode, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
		TileEntity tile = world.getTileEntity(pos);
        IBlockState state = world.getBlockState(pos);
	    Block block = state.getBlock();
	    
	    boolean factory = PluginFactory.getBlocks() != null;
	    boolean apiculture = PluginApiculture.getBlocks() != null;
	    boolean arboriculture = PluginArboriculture.getBlocks() != null;
	    boolean lepidopterology = PluginLepidopterology.getBlocks() != null;
	    
	    if (mode == ModItems.modeRotate)
	    {
	    	if ((apiculture && (block == PluginApiculture.getBlocks().beeHouse || block == PluginApiculture.getBlocks().apiary || block == PluginApiculture.getBlocks().beeChest)) || (arboriculture && block == PluginArboriculture.getBlocks().treeChest) || (lepidopterology && block == PluginLepidopterology.getBlocks().butterflyChest))
	    	{
	    		EnumFacing newFacing = state.getValue(((BlockBase) block).FACING);
    	    	if (newFacing == EnumFacing.NORTH) newFacing = EnumFacing.EAST;
    			else if (newFacing == EnumFacing.EAST) newFacing = EnumFacing.SOUTH;
    			else if (newFacing == EnumFacing.SOUTH) newFacing = EnumFacing.WEST;
    			else newFacing = EnumFacing.NORTH;
    	    	
    			IBlockState blockState = world.getBlockState(pos);
    	    	blockState = blockState.withProperty(((BlockBase) block).FACING, newFacing);
    			world.setBlockState(pos, blockState);
    			
    			if (!world.isRemote) return EnumActionResult.SUCCESS;
				else
				{
					player.playSound(block.getSoundType(state, world, pos, player).getPlaceSound(), 1.0F, 0.6F);
					player.swingArm(EnumHand.MAIN_HAND);
				}
	    	}
	    }
	    else if (mode == ModItems.modeWrench)
    	{
    		if (player.isSneaking() && (block instanceof BlockFarm || block instanceof BlockGreenhouse || block instanceof BlockAlveary || (apiculture && block == PluginApiculture.getBlocks().apiary)))
    		{
    			DismantleHelper.dismantleBlock(world, pos, state, player, true);
    			if (!world.isRemote) return EnumActionResult.SUCCESS;
				else
				{
					player.playSound(block.getSoundType(state, world, pos, player).getPlaceSound(), 1.0F, 0.6F);
					player.swingArm(EnumHand.MAIN_HAND);
				}
    		}
    		else if (block instanceof BlockEngine || block instanceof BlockFactoryPlain || block instanceof BlockFactoryTESR || block instanceof BlockMail)
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
	    		else if (block instanceof BlockEngine)
	    		{
		    		if (!rotateEngine(world, pos))
		    		{
		    			EnumFacing newFacing = state.getValue(((BlockBase) block).FACING);
		    	    	if (newFacing == EnumFacing.NORTH) newFacing = EnumFacing.EAST;
		    			else if (newFacing == EnumFacing.EAST) newFacing = EnumFacing.SOUTH;
		    			else if (newFacing == EnumFacing.SOUTH) newFacing = EnumFacing.WEST;
		    			else if (newFacing == EnumFacing.WEST) newFacing = EnumFacing.UP;
		    			else if (newFacing == EnumFacing.UP) newFacing = EnumFacing.DOWN;
		    			else if (newFacing == EnumFacing.DOWN) newFacing = EnumFacing.NORTH;
		    	    	
		    			IBlockState blockState = world.getBlockState(pos);
		    	    	blockState = blockState.withProperty(((BlockBase) block).FACING, newFacing);
		    			world.setBlockState(pos, blockState);
		    		}
	    			if (!world.isRemote) return EnumActionResult.SUCCESS;
					else
					{
						player.playSound(block.getSoundType(state, world, pos, player).getPlaceSound(), 1.0F, 0.6F);
						player.swingArm(EnumHand.MAIN_HAND);
					}
	    		}
	    		else
	    		{
	    			if (factory && block == PluginFactory.getBlocks().rainmaker) return EnumActionResult.PASS;
	    			
	    			EnumFacing newFacing = state.getValue(((BlockBase) block).FACING);
	    	    	if (newFacing == EnumFacing.NORTH) newFacing = EnumFacing.EAST;
	    			else if (newFacing == EnumFacing.EAST) newFacing = EnumFacing.SOUTH;
	    			else if (newFacing == EnumFacing.SOUTH) newFacing = EnumFacing.WEST;
	    			else newFacing = EnumFacing.NORTH;
	    	    	
	    			IBlockState blockState = world.getBlockState(pos);
	    	    	blockState = blockState.withProperty(((BlockBase) block).FACING, newFacing);
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
	
	public boolean preventBlockRotation(WrenchMode mode, EntityPlayer player, World world, BlockPos pos)
	{
        IBlockState state = world.getBlockState(pos);
	    Block block = state.getBlock();
		return block instanceof BlockBase;
	}
	
	/**
	 * Recratinon of Forestrys BlockEngine isOrientedAtEnergyReciever
	 * 
	 * @param world
	 * @param pos
	 * @param orientation
	 * @return
	 */
	private static boolean isEngineOrientedAtEnergyReciever(World world, BlockPos pos, EnumFacing orientation)
	{
		BlockPos offsetPos = pos.offset(orientation);
		TileEntity tile = TileUtil.getTile(world, offsetPos);
		return EnergyHelper.isEnergyReceiverOrEngine(orientation.getOpposite(), tile);
	}
	
	/**
	 * Recratinon of Forestrys BlockEngine rotate
	 * 
	 * @param world
	 * @param pos
	 * @return
	 */
	private static boolean rotateEngine(World world, BlockPos pos)
	{
		IBlockState blockState = world.getBlockState(pos);
	    Block block = blockState.getBlock();
		EnumFacing blockFacing = blockState.getValue(((BlockBase) block).FACING);
		for (int i = blockFacing.ordinal() + 1; i <= blockFacing.ordinal() + 6; ++i)
		{
			EnumFacing orientation = EnumFacing.values()[i % 6];
			if (isEngineOrientedAtEnergyReciever(world, pos, orientation))
			{
				blockState = blockState.withProperty(((BlockBase) block).FACING, orientation);
				world.setBlockState(pos, blockState);
				return true;
			}
		}
		return false;
	}
}