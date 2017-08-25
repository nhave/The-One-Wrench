package com.nhave.tow.integration.handlers;

import org.cyclops.cyclopscore.config.configurable.ConfigurableBlockContainer;
import org.cyclops.integrateddynamics.block.BlockCable;
import org.cyclops.integrateddynamics.item.ItemWrench;

import com.nhave.tow.api.integration.WrenchHandler;
import com.nhave.tow.api.wrenchmodes.WrenchMode;
import com.nhave.tow.registry.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class IntegratedDynamicsHandler extends WrenchHandler
{
	@Override
	public EnumActionResult onWrenchUseFirst(WrenchMode mode, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
	    TileEntity tile = world.getTileEntity(pos);
	    IBlockState bs = world.getBlockState(pos);
	    Block block = bs.getBlock();
		ItemStack heldItem = player.getHeldItem(hand).copy();
		
		if (mode == ModItems.modeWrench)
		{
			if (player.isSneaking() && block instanceof BlockCable)
			{
				if (!world.isRemote)
				{
					player.setHeldItem(hand, new ItemStack(ItemWrench.getInstance()));
					block.onBlockActivated(world, pos, bs, player, hand, side, hitX, hitY, hitZ);
					player.setHeldItem(hand, heldItem);
					return EnumActionResult.SUCCESS;
				}
				else
				{
					SoundEvent soundName = block.getSoundType(bs, world, pos, player).getPlaceSound();
					player.playSound(soundName, 1.0F, 0.6F);
					player.swingArm(EnumHand.MAIN_HAND);
				}
			}
			else if (block.getRegistryName().getResourceDomain().equals("integrateddynamics") && block instanceof ConfigurableBlockContainer)
			{
				if (player.isSneaking())
				{
			    	if (block.removedByPlayer(bs, world, pos, player, true)) block.breakBlock(world, pos, bs);
				    //block.onBlockHarvested(world, pos, bs, player);
					block.harvestBlock(world, player, pos, bs, tile, player.getHeldItemMainhand());
			    	if (!world.isRemote) return EnumActionResult.SUCCESS;
					else
					{
						player.playSound(block.getSoundType(bs, world, pos, player).getPlaceSound(), 1.0F, 0.6F);
						player.swingArm(EnumHand.MAIN_HAND);
					}
				}
				else if (block.rotateBlock(world, pos, side))
			    {
					world.markChunkDirty(pos, null);
			    	if (!world.isRemote) return EnumActionResult.SUCCESS;
					else
					{
						player.playSound(block.getSoundType(bs, world, pos, player).getPlaceSound(), 1.0F, 0.6F);
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
	    IBlockState bs = world.getBlockState(pos);
	    Block block = bs.getBlock();
		return (block.getRegistryName().getResourceDomain().equals("integrateddynamics") && block instanceof ConfigurableBlockContainer);
	}
}
