package com.nhave.tow.integration.handlers;

import java.util.ArrayList;

import com.nhave.tow.api.integration.WrenchHandler;
import com.nhave.tow.api.wrenchmodes.WrenchMode;
import com.nhave.tow.helpers.DismantleHelper;
import com.nhave.tow.registry.ModConfig;
import com.nhave.tow.registry.ModItems;

import ic2.api.tile.IWrenchable;
import ic2.core.IC2;
import ic2.core.audio.PositionSpec;
import ic2.core.block.wiring.TileEntityCable;
import ic2.core.util.StackUtil;
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

public class IndustrialCraftHandler extends WrenchHandler
{
	private boolean cutWires;
	private boolean classicMode;
	
	public IndustrialCraftHandler()
	{
		this.cutWires = ModConfig.ic2CutWires;
		this.classicMode = ModConfig.ic2ClassicRotation;
	}
	
	@Override
	public EnumActionResult onWrenchUseFirst(WrenchMode mode, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
	    TileEntity tile = world.getTileEntity(pos);
	    IBlockState bs = world.getBlockState(pos);
	    Block block = bs.getBlock();
	    
		if (mode == ModItems.modeWrench && player.isSneaking() && tile != null && tile instanceof TileEntityCable && this.cutWires)
	    {
			DismantleHelper.dismantleBlock(world, pos, bs, player, true);
	    	if (!world.isRemote) return EnumActionResult.SUCCESS;
	    	else
			{
		    	player.swingArm(hand);
		    	IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/wrench.ogg", true, IC2.audioManager.getDefaultVolume());
			}
	    }
		return this.classicMode ? wrenchClassic(mode, player, world, pos, side, hitX, hitY, hitZ, hand) : wrenchUnity(mode, player, world, pos, side, hitX, hitY, hitZ, hand);
	}
	
	public EnumActionResult wrenchUnity(WrenchMode mode, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
	    TileEntity tile = world.getTileEntity(pos);
	    IBlockState bs = world.getBlockState(pos);
	    Block block = bs.getBlock();
	    
	    if (mode == ModItems.modeWrench || mode == ModItems.modeRotate)
		{
			if (block instanceof IWrenchable)
			{
				//Disallow Rotation mode on IC2 blocks
			    if (mode == ModItems.modeRotate)
			    {
			    	return EnumActionResult.FAIL;
			    }
			    
				IWrenchable wrenchable = (IWrenchable) block;
				
				if (!player.isSneaking())
				{
					EnumFacing currentFacing = wrenchable.getFacing(world, pos);
					EnumFacing newFacing = currentFacing;
					
					if (newFacing == EnumFacing.NORTH) newFacing = EnumFacing.EAST;
					else if (newFacing == EnumFacing.EAST) newFacing = EnumFacing.SOUTH;
					else if (newFacing == EnumFacing.SOUTH) newFacing = EnumFacing.WEST;
					else if (newFacing == EnumFacing.WEST) newFacing = EnumFacing.UP;
					else if (newFacing == EnumFacing.UP) newFacing = EnumFacing.DOWN;
					else if (newFacing == EnumFacing.DOWN) newFacing = EnumFacing.NORTH;
					
					if (wrenchable.setFacing(world, pos, newFacing, player))
					{
						world.markChunkDirty(pos, null);
						if (!world.isRemote) return EnumActionResult.SUCCESS;
					}
					else
					{
						block.rotateBlock(world, pos, EnumFacing.UP);
						world.markChunkDirty(pos, null);
						if (!world.isRemote) return EnumActionResult.SUCCESS;
					}
				}
				else
				{
					if (wrenchable.wrenchCanRemove(world, pos, player))
					{
						if (!world.isRemote)
						{
							if (block.removedByPlayer(bs, world, pos, player, true)) block.breakBlock(world, pos, bs);
							
							ArrayList<? extends ItemStack> drops = (ArrayList<? extends ItemStack>) wrenchable.getWrenchDrops(world, pos, bs, tile, player, 0);
							
							for (ItemStack stack : drops)
							{
								StackUtil.dropAsEntity(world, pos, stack);
							}
							
							return EnumActionResult.SUCCESS;
						}
					}
				}
				
				if (world.isRemote)
				{
			    	player.swingArm(hand);
			    	IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/wrench.ogg", true, IC2.audioManager.getDefaultVolume());
				}
			}
		}
	    
		return EnumActionResult.PASS;
	}
	
	public EnumActionResult wrenchClassic(WrenchMode mode, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
	    TileEntity tile = world.getTileEntity(pos);
	    IBlockState bs = world.getBlockState(pos);
	    Block block = bs.getBlock();
	    
		if (mode == ModItems.modeWrench || mode == ModItems.modeRotate)
		{
			if (block instanceof IWrenchable)
			{
				//Disallow Rotation mode on IC2 blocks
			    if (mode == ModItems.modeRotate) return EnumActionResult.FAIL;
			    
				IWrenchable wrenchable = (IWrenchable) block;
				
				EnumFacing currentFacing = wrenchable.getFacing(world, pos);
				EnumFacing newFacing = currentFacing;
				
				if (IC2.keyboard.isAltKeyDown(player))
				{
					EnumFacing.Axis axis = side.getAxis();

					if (((side.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE) && (!(player.isSneaking()))) || ((side.getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE) && (player.isSneaking())))
					{
						newFacing = newFacing.rotateAround(axis);
					}
					else
					{
						for (int i = 0; i < 3; ++i)
						{
							newFacing = newFacing.rotateAround(axis);
						}
					}
				}
				else if (player.isSneaking())
				{
					newFacing = side.getOpposite();
				}
				else
				{
					newFacing = side;
				}
				if ((newFacing != currentFacing))
				{
					if (wrenchable.setFacing(world, pos, newFacing, player))
					{
						world.markChunkDirty(pos, null);
						if (!world.isRemote) return EnumActionResult.SUCCESS;
					}
					else
					{
						for (int i = 0; i < 6; ++i)
						{
							block.rotateBlock(world, pos, EnumFacing.UP);
							if (wrenchable.getFacing(world, pos) == newFacing) break;
						}
						world.markChunkDirty(pos, null);
						if (!world.isRemote && (wrenchable.getFacing(world, pos) == newFacing)) return EnumActionResult.SUCCESS;
					}
				}
				
				if (wrenchable.wrenchCanRemove(world, pos, player))
				{
					if (!world.isRemote)
					{
						if (block.removedByPlayer(bs, world, pos, player, true)) block.breakBlock(world, pos, bs);
						
						ArrayList<? extends ItemStack> drops = (ArrayList<? extends ItemStack>) wrenchable.getWrenchDrops(world, pos, bs, tile, player, 0);
						
						for (ItemStack stack : drops)
						{
							StackUtil.dropAsEntity(world, pos, stack);
						}
						
						return EnumActionResult.SUCCESS;
					}
				}
				
				if (world.isRemote)
				{
			    	player.swingArm(hand);
			    	IC2.audioManager.playOnce(player, PositionSpec.Hand, "Tools/wrench.ogg", true, IC2.audioManager.getDefaultVolume());
				}
			}
		}
		return EnumActionResult.PASS;
	}
}
