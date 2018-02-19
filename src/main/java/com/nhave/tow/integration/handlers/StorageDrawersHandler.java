package com.nhave.tow.integration.handlers;

import com.jaquadro.minecraft.storagedrawers.block.BlockController;
import com.jaquadro.minecraft.storagedrawers.block.BlockDrawers;
import com.jaquadro.minecraft.storagedrawers.block.BlockFramingTable;
import com.jaquadro.minecraft.storagedrawers.block.BlockKeyButton;
import com.jaquadro.minecraft.storagedrawers.block.BlockSlave;
import com.jaquadro.minecraft.storagedrawers.block.BlockTrim;
import com.nhave.tow.api.integration.WrenchHandler;
import com.nhave.tow.api.wrenchmodes.WrenchMode;
import com.nhave.tow.helpers.DismantleHelper;
import com.nhave.tow.registry.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StorageDrawersHandler extends WrenchHandler
{
	@Override
	public EnumActionResult onWrenchUseFirst(WrenchMode mode, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
		TileEntity tile = world.getTileEntity(pos);
	    IBlockState state = world.getBlockState(pos);
	    Block block = state.getBlock();
		ItemStack heldItem = player.getHeldItem(hand).copy();
		
		if (block instanceof BlockDrawers || block instanceof BlockController || block instanceof BlockSlave || block instanceof BlockTrim || block instanceof BlockFramingTable || block instanceof BlockKeyButton)
		{
			if (mode == ModItems.modeWrench)
			{
				if (player.isSneaking())
				{
					if (!world.isRemote)
					{
						DismantleHelper.dismantleBlock(world, pos, state, player, true);
						return EnumActionResult.SUCCESS;
					}
					else
					{
						player.playSound(block.getSoundType(state, world, pos, player).getPlaceSound(), 1.0F, 0.6F);
						player.swingArm(EnumHand.MAIN_HAND);
					}
				}
				else if (block instanceof BlockDrawers || block instanceof BlockController)
				{
					if (block.rotateBlock(world, pos, side))
				    {
				    	if (!world.isRemote) return EnumActionResult.SUCCESS;
						else
						{
							player.playSound(block.getSoundType(state, world, pos, player).getPlaceSound(), 1.0F, 0.6F);
							player.swingArm(EnumHand.MAIN_HAND);
						}
				    }
				}
			}
			if (block instanceof BlockDrawers || block instanceof BlockController)
			{
				if (mode == ModItems.modeTune || mode == ModItems.modeUtil)
				{
					Item quantifyKey = com.jaquadro.minecraft.storagedrawers.core.ModItems.quantifyKey;
					Item shroudKey = com.jaquadro.minecraft.storagedrawers.core.ModItems.shroudKey;
					Item drawerKey = com.jaquadro.minecraft.storagedrawers.core.ModItems.drawerKey;
					Item personalKey = com.jaquadro.minecraft.storagedrawers.core.ModItems.personalKey;
					
					if (!world.isRemote)
					{
						player.setHeldItem(hand, new ItemStack(mode == ModItems.modeTune ? (player.isSneaking() ? shroudKey : quantifyKey) : (mode == ModItems.modeUtil && player.isSneaking() ? personalKey : drawerKey)));
						player.getHeldItem(hand).onItemUse(player, world, pos, hand, side, hitX, hitY, hitZ);
						block.onBlockActivated(world, pos, state, player, hand, side, hitX, hitY, hitZ);
						player.setHeldItem(hand, heldItem);
						return EnumActionResult.SUCCESS;
					}
					else player.swingArm(EnumHand.MAIN_HAND);
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
		return (block instanceof BlockDrawers || block instanceof BlockController || block instanceof BlockSlave || block instanceof BlockTrim || block instanceof BlockFramingTable || block instanceof BlockKeyButton);
	}
	
	@Override
	public boolean shouldDenyBlockActivate(WrenchMode mode, EntityPlayer player, World world, BlockPos pos)
	{
	    IBlockState state = world.getBlockState(pos);
	    Block block = state.getBlock();
	    
		return (block instanceof BlockDrawers || block instanceof BlockController || block instanceof BlockFramingTable || (mode == ModItems.modeWrench && block instanceof BlockKeyButton && player.isSneaking()));
	}
}
