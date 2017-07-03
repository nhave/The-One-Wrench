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
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import reborncore.common.blocks.BlockMachineBase;
import reborncore.mcmultipart.block.TileMultipartContainer;
import reborncore.mcmultipart.multipart.IMultipart;
import reborncore.mcmultipart.raytrace.PartMOP;
import techreborn.blocks.BlockPlayerDetector;
import techreborn.blocks.generator.BlockCreativePanel;
import techreborn.blocks.generator.BlockSolarPanel;
import techreborn.blocks.generator.BlockWaterMill;
import techreborn.blocks.storage.BlockEnergyStorage;
import techreborn.blocks.transformers.BlockTransformer;
import techreborn.parts.powerCables.CableMultipart;

public class TechRebornHandler extends WrenchHandler
{
	private boolean cutWires;
	private boolean classicMode;
	
	public TechRebornHandler(boolean cutWires, boolean classicMode)
	{
		this.cutWires = cutWires;
		this.classicMode = classicMode;
	}
	
	@Override
	public EnumActionResult onWrenchUseFirst(WrenchMode mode, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
		TileEntity tile = world.getTileEntity(pos);
	    IBlockState state = world.getBlockState(pos);
	    Block block = state.getBlock();
	    
	    if (this.cutWires && mode == ModItems.modeWrench && tile != null && tile instanceof TileMultipartContainer && player.isSneaking())
	    {
		    RayTraceResult rayTrace = player.rayTrace(5D, 1.0F);
		    TileMultipartContainer multiPartTile = (TileMultipartContainer) tile;
		    
		    if (rayTrace instanceof PartMOP)
		    {
		    	IMultipart part = ((PartMOP) rayTrace).partHit;
		    	if (part instanceof CableMultipart)
		    	{
		    		if (!world.isRemote)
		    		{
				    	multiPartTile.removePart(part);
				    	for (ItemStack stack : part.getDrops())
			        	{
			        		DismantleHelper.dropBlockAsItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
			            }
				    	return EnumActionResult.SUCCESS;
		    		}
		    		else
		    		{
						player.playSound(block.getSoundType(state, world, pos, player).getPlaceSound(), 1.0F, 0.6F);
						player.swingArm(hand);
		    		}
		    	}
		    }
	    }
	    
		return this.classicMode ? wrenchClassic(mode, player, world, pos, side, hitX, hitY, hitZ, hand) : wrenchUnity(mode, player, world, pos, side, hitX, hitY, hitZ, hand);
	}
	
	public EnumActionResult wrenchUnity(WrenchMode mode, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
		TileEntity tile = world.getTileEntity(pos);
	    IBlockState state = world.getBlockState(pos);
	    Block block = state.getBlock();
	    
	    if (mode == ModItems.modeRotate && (block instanceof BlockMachineBase || block instanceof BlockEnergyStorage))
		{
	    	return EnumActionResult.FAIL;
		}
	    else if (mode == ModItems.modeWrench && (block instanceof BlockMachineBase || block instanceof BlockEnergyStorage || block instanceof BlockTransformer || block instanceof BlockWaterMill || block instanceof BlockSolarPanel || block instanceof BlockCreativePanel))
		{
	    	if (!player.isSneaking())
	    	{
	    		if (block instanceof BlockPlayerDetector) return EnumActionResult.PASS;
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
	    	else DismantleHelper.dismantleBlockPure(world, pos, state, player, false);
    		
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
	    
	    if (mode == ModItems.modeRotate && (block instanceof BlockMachineBase || block instanceof BlockEnergyStorage))
		{
	    	return EnumActionResult.FAIL;
		}
	    else if (mode == ModItems.modeWrench)
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
			if (block instanceof BlockPlayerDetector || block instanceof BlockWaterMill || block instanceof BlockSolarPanel || block instanceof BlockCreativePanel)
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
	public boolean shouldDenyBlockActivate(WrenchMode mode, EntityPlayer player, World world, BlockPos pos)
	{
	    IBlockState state = world.getBlockState(pos);
	    Block block = state.getBlock();
	    
		return (mode == ModItems.modeWrench) && (block instanceof BlockMachineBase || block instanceof BlockEnergyStorage);
	}
}