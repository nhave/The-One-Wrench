package com.nhave.tow.integration.handlers;

import com.nhave.tow.api.TOWAPI;
import com.nhave.tow.api.integration.IDataWipe;
import com.nhave.tow.api.integration.WrenchHandler;
import com.nhave.tow.api.wrenchmodes.WrenchMode;
import com.nhave.tow.helpers.DismantleHelper;
import com.nhave.tow.integration.modes.WrenchModeEmbers;
import com.nhave.tow.registry.ModConfig;
import com.nhave.tow.registry.ModIntegration;
import com.nhave.tow.registry.ModItems;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import teamroots.embers.block.BlockAdvancedEdge;
import teamroots.embers.block.BlockInfernoForgeEdge;
import teamroots.embers.block.BlockItemPipe;
import teamroots.embers.block.BlockItemPump;
import teamroots.embers.block.BlockMechEdge;
import teamroots.embers.block.BlockPipe;
import teamroots.embers.block.BlockPump;
import teamroots.embers.block.BlockStoneEdge;
import teamroots.embers.block.BlockTEBase;
import teamroots.embers.block.IDial;
import teamroots.embers.power.IEmberPacketProducer;
import teamroots.embers.power.IEmberPacketReceiver;
import teamroots.embers.tileentity.ITargetable;
import teamroots.embers.util.Misc;

public class EmbersHandler extends WrenchHandler implements IDataWipe
{
	public static final String NBT_TARGET_X = "EmberTargetX";
	public static final String NBT_TARGET_Y = "EmberTargetY";
	public static final String NBT_TARGET_Z = "EmberTargetZ";
	
	public EmbersHandler()
	{
		ModIntegration.modeEmbers = TOWAPI.modeRegistry.register(new WrenchModeEmbers("embers", GameRegistry.makeItemStack("embers:tinker_hammer", 0, 1, null)));
	}
	
	@Override
	public EnumActionResult onWrenchUseFirst(WrenchMode mode, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);
		TileEntity tile = world.getTileEntity(pos);
        IBlockState state = world.getBlockState(pos);
	    Block block = state.getBlock();
		
		if ((mode == ModItems.modeWrench || mode == ModItems.modeRotate) && (block instanceof BlockTEBase || block instanceof IDial || block instanceof BlockMechEdge || block instanceof BlockStoneEdge || block instanceof BlockAdvancedEdge || block instanceof BlockInfernoForgeEdge))
		{
			if (mode == ModItems.modeRotate)
		    {
		    	return EnumActionResult.FAIL;
		    }
			
			boolean isEdge = (block instanceof BlockMechEdge || block instanceof BlockAdvancedEdge || block instanceof BlockInfernoForgeEdge);
	    	
	    	if (player.isSneaking())
			{
	    		if (ModConfig.allowEmbersDismantle)
	    		{
		    		DismantleHelper.dismantleBlock(world, pos, state, player, (isEdge ? false : true));
					player.swingArm(EnumHand.MAIN_HAND);
					if (!world.isRemote) return EnumActionResult.SUCCESS;
					else
					{
						player.playSound(block.getSoundType(state, world, pos, player).getPlaceSound(), 1.0F, 0.6F);
						player.swingArm(EnumHand.MAIN_HAND);
					}
	    		}
			}
	    	else if (block instanceof BlockItemPipe || block instanceof BlockItemPump || block instanceof BlockPipe || block instanceof BlockPump)
	    	{
	    		if (!world.isRemote)
				{
					ItemStack heldItem = player.getHeldItem(hand).copy();
		    		ItemStack hammer = GameRegistry.makeItemStack("embers:tinker_hammer", 0, 1, null);
					
					player.setHeldItem(hand, hammer);
					block.onBlockActivated(world, pos, state, player, hand, side, hitX, hitY, hitZ);
					world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 3);
					player.setHeldItem(hand, heldItem);
					return EnumActionResult.SUCCESS;
				}
				else
				{
					player.playSound(block.getSoundType(state, world, pos, player).getPlaceSound(), 1.0F, 0.6F);
					player.swingArm(EnumHand.MAIN_HAND);
				}
	    	}
		}
		else if (mode == ModIntegration.modeEmbers)
		{
			if (player.isSneaking())
			{
				stack.getTagCompound().setInteger(NBT_TARGET_X, pos.getX());
				stack.getTagCompound().setInteger(NBT_TARGET_Y, pos.getY());
				stack.getTagCompound().setInteger(NBT_TARGET_Z, pos.getZ());
				if (!world.isRemote) return EnumActionResult.SUCCESS;
				else 
				{
					world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.BLOCKS, 1.0f, 1.9f+Misc.random.nextFloat()*0.2f, false);
					player.swingArm(EnumHand.MAIN_HAND);
				}
			}
			else if (tile instanceof IEmberPacketProducer && stack.getTagCompound().hasKey(NBT_TARGET_X) && stack.getTagCompound().hasKey(NBT_TARGET_Y) && stack.getTagCompound().hasKey(NBT_TARGET_Z))
			{
				if (world.getTileEntity(new BlockPos(stack.getTagCompound().getInteger(NBT_TARGET_X), stack.getTagCompound().getInteger(NBT_TARGET_Y), stack.getTagCompound().getInteger(NBT_TARGET_Z))) instanceof IEmberPacketReceiver)
				{
					((IEmberPacketProducer)tile).setTargetPosition(new BlockPos(stack.getTagCompound().getInteger(NBT_TARGET_X), stack.getTagCompound().getInteger(NBT_TARGET_Y), stack.getTagCompound().getInteger(NBT_TARGET_Z)), side);
					if (!world.isRemote) return EnumActionResult.SUCCESS;
					else
					{
						world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.BLOCKS, 1.0f, 0.95f+Misc.random.nextFloat()*0.1f, false);	
    					player.swingArm(EnumHand.MAIN_HAND);
					}
				}
			}
			else if (tile instanceof ITargetable && stack.getTagCompound().hasKey(NBT_TARGET_X))
			{
				((ITargetable)tile).setTarget(new BlockPos(stack.getTagCompound().getInteger(NBT_TARGET_X), stack.getTagCompound().getInteger(NBT_TARGET_Y), stack.getTagCompound().getInteger(NBT_TARGET_Z)));
				if (!world.isRemote) return EnumActionResult.SUCCESS;
				else
				{
					world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.BLOCKS, 1.0f, 0.95f+Misc.random.nextFloat()*0.1f, false);	
					player.swingArm(EnumHand.MAIN_HAND);
				}
			}
		}
		return EnumActionResult.PASS;
	}
	
	@Override
	public boolean wipeData(ItemStack stack)
	{
		boolean result = false;
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey(NBT_TARGET_X))
		{
			stack.getTagCompound().removeTag(NBT_TARGET_X);
			result = true;
		}
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey(NBT_TARGET_Y))
		{
			stack.getTagCompound().removeTag(NBT_TARGET_Y);
			result = true;
		}
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey(NBT_TARGET_Z))
		{
			stack.getTagCompound().removeTag(NBT_TARGET_Z);
			result = true;
		}
		return result;
	}
}
