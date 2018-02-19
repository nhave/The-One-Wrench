package com.nhave.tow.integration.handlers;

import com.nhave.tow.api.integration.WrenchHandler;
import com.nhave.tow.api.wrenchmodes.WrenchMode;
import com.nhave.tow.helpers.DismantleHelper;
import com.nhave.tow.registry.ModConfig;
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
import reborncore.common.blocks.BlockMachineBase;
import techreborn.blocks.BlockPlayerDetector;
import techreborn.blocks.generator.BlockCreativeSolarPanel;
import techreborn.blocks.generator.BlockWaterMill;
import techreborn.blocks.storage.BlockEnergyStorage;
import techreborn.blocks.storage.BlockLSUStorage;
import techreborn.blocks.transformers.BlockTransformer;
import techreborn.tiles.cable.TileCable;

public class TechRebornHandler extends WrenchHandler
{
	private boolean cutWires;
	private boolean classicMode;
	
	public TechRebornHandler()
	{
		this.cutWires = ModConfig.trCutWires;
		this.classicMode = ModConfig.trRotation;
	}
	
	@Override
	public EnumActionResult onWrenchUseFirst(WrenchMode mode, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
		TileEntity tile = world.getTileEntity(pos);
	    IBlockState state = world.getBlockState(pos);
	    Block block = state.getBlock();
	    
	    if (this.cutWires && mode == ModItems.modeWrench && tile != null && tile instanceof TileCable && player.isSneaking())
	    {
	    	if (!world.isRemote)
    		{
	    		DismantleHelper.dismantleBlock(world, pos, state, player, false);
		    	return EnumActionResult.SUCCESS;
    		}
    		else
    		{
				player.playSound(block.getSoundType(state, world, pos, player).getPlaceSound(), 1.0F, 0.6F);
				player.swingArm(hand);
    		}
	    }
	    
		return this.classicMode ? wrenchClassic(mode, player, world, pos, side, hitX, hitY, hitZ, hand) : wrenchUnity(mode, player, world, pos, side, hitX, hitY, hitZ, hand);
	}
	
	public EnumActionResult wrenchUnity(WrenchMode mode, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
		TileEntity tile = world.getTileEntity(pos);
	    IBlockState state = world.getBlockState(pos);
	    Block block = state.getBlock();
	    
	    if (mode == ModItems.modeWrench && (block instanceof BlockMachineBase || block instanceof BlockEnergyStorage || block instanceof BlockTransformer || block instanceof BlockWaterMill || block instanceof BlockCreativeSolarPanel || block instanceof BlockCreativeSolarPanel || block instanceof BlockLSUStorage))
		{
	    	if (!player.isSneaking())
	    	{
	    		if (block instanceof BlockPlayerDetector || block instanceof BlockLSUStorage) return EnumActionResult.PASS;
	    		else if (block instanceof BlockMachineBase)
	    		{
	    	    	EnumFacing newFacing = ((BlockMachineBase) block).getFacing(state);
	    	    	if (newFacing == EnumFacing.NORTH) newFacing = EnumFacing.EAST;
	    			else if (newFacing == EnumFacing.EAST) newFacing = EnumFacing.SOUTH;
	    			else if (newFacing == EnumFacing.SOUTH) newFacing = EnumFacing.WEST;
	    			else if (newFacing == EnumFacing.WEST) newFacing = EnumFacing.NORTH;
	    	    	
	    			if (newFacing != EnumFacing.DOWN && newFacing != EnumFacing.UP && newFacing != ((BlockMachineBase) block).getFacing(state))
	    			{
						((BlockMachineBase) block).setFacing(newFacing, world, pos);
					}
					if (!world.isRemote) return EnumActionResult.SUCCESS;
	    		}
	    		else if (block instanceof BlockEnergyStorage)
	    		{
	    	    	EnumFacing newFacing = ((BlockEnergyStorage) block).getFacing(state);
	    	    	if (newFacing == EnumFacing.NORTH) newFacing = EnumFacing.EAST;
	    			else if (newFacing == EnumFacing.EAST) newFacing = EnumFacing.SOUTH;
	    			else if (newFacing == EnumFacing.SOUTH) newFacing = EnumFacing.WEST;
	    			else if (newFacing == EnumFacing.WEST) newFacing = EnumFacing.UP;
	    			else if (newFacing == EnumFacing.UP) newFacing = EnumFacing.DOWN;
	    			else if (newFacing == EnumFacing.DOWN) newFacing = EnumFacing.NORTH;
	    	    	
	    			if(newFacing != ((BlockEnergyStorage) block).getFacing(state))
	    			{
	    				((BlockEnergyStorage) block).setFacing(newFacing, world, pos);
					}
					if (!world.isRemote) return EnumActionResult.SUCCESS;
	    		}
	    		else if (block instanceof BlockTransformer)
	    		{
	    	    	EnumFacing newFacing = ((BlockTransformer) block).getFacing(state);
	    	    	if (newFacing == EnumFacing.NORTH) newFacing = EnumFacing.EAST;
	    			else if (newFacing == EnumFacing.EAST) newFacing = EnumFacing.SOUTH;
	    			else if (newFacing == EnumFacing.SOUTH) newFacing = EnumFacing.WEST;
	    			else if (newFacing == EnumFacing.WEST) newFacing = EnumFacing.UP;
	    			else if (newFacing == EnumFacing.UP) newFacing = EnumFacing.DOWN;
	    			else if (newFacing == EnumFacing.DOWN) newFacing = EnumFacing.NORTH;
	    	    	
	    			if(newFacing != ((BlockTransformer) block).getFacing(state))
	    			{
	    				((BlockTransformer) block).setFacing(newFacing, world, pos);
					}
					if (!world.isRemote) return EnumActionResult.SUCCESS;
	    		}
	    	}
	    	else
	    	{
	    		DismantleHelper.dismantleBlockPure(world, pos, state, player, false);
	    		if (!world.isRemote) return EnumActionResult.SUCCESS;
	    	}
    		
    		if (world.isRemote)
			{
				player.playSound(block.getSoundType(state, world, pos, player).getPlaceSound(), 1.0F, 0.6F);
				player.swingArm(hand);
			}
		}
		return EnumActionResult.PASS;
	}
	
	public EnumActionResult wrenchClassic(WrenchMode mode, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
		TileEntity tile = world.getTileEntity(pos);
	    IBlockState state = world.getBlockState(pos);
	    Block block = state.getBlock();
	    
	    if (mode == ModItems.modeWrench)
		{
	    	EnumFacing newFacing = side;
			if (player.isSneaking())
			{
				newFacing = side.getOpposite();
			}
			else
			{
				newFacing = side;
			}
			if (block instanceof BlockPlayerDetector || block instanceof BlockWaterMill || block instanceof BlockCreativeSolarPanel || block instanceof BlockCreativeSolarPanel || block instanceof BlockLSUStorage)
			{
				DismantleHelper.dismantleBlockPure(world, pos, state, player, false);
				if (!world.isRemote) return EnumActionResult.SUCCESS;
			}
			else if (block instanceof BlockMachineBase)
    		{
    			if (newFacing != EnumFacing.DOWN && newFacing != EnumFacing.UP && newFacing != ((BlockMachineBase) block).getFacing(state))
    			{
					((BlockMachineBase) block).setFacing(newFacing, world, pos);
				}
    			else DismantleHelper.dismantleBlockPure(world, pos, state, player, false);
				if (!world.isRemote) return EnumActionResult.SUCCESS;
    		}
    		else if (block instanceof BlockEnergyStorage)
    		{
    			if(newFacing != ((BlockEnergyStorage) block).getFacing(state))
    			{
    				((BlockEnergyStorage) block).setFacing(newFacing, world, pos);
				}
    			else DismantleHelper.dismantleBlockPure(world, pos, state, player, false);
				if (!world.isRemote) return EnumActionResult.SUCCESS;
    		}
    		else if (block instanceof BlockTransformer)
    		{
    			if(newFacing != ((BlockTransformer) block).getFacing(state))
    			{
    				((BlockTransformer) block).setFacing(newFacing, world, pos);
				}
    			else DismantleHelper.dismantleBlockPure(world, pos, state, player, false);
				if (!world.isRemote) return EnumActionResult.SUCCESS;
    		}
    		
    		if (world.isRemote)
			{
				player.playSound(block.getSoundType(state, world, pos, player).getPlaceSound(), 1.0F, 0.6F);
				player.swingArm(hand);
			}
		}
		return EnumActionResult.PASS;
	}
	
	@Override
	public boolean preventBlockRotation(EntityPlayer player, World world, BlockPos pos)
	{
	    IBlockState state = world.getBlockState(pos);
	    Block block = state.getBlock();
		return (block instanceof BlockMachineBase || block instanceof BlockEnergyStorage);
	}
	
	@Override
	public boolean shouldDenyBlockActivate(WrenchMode mode, EntityPlayer player, World world, BlockPos pos)
	{
	    IBlockState state = world.getBlockState(pos);
	    Block block = state.getBlock();
	    
		return (mode == ModItems.modeWrench) && (block instanceof BlockMachineBase || block instanceof BlockEnergyStorage);
	}
}