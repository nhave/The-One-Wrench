package com.nhave.tow.integration.handlers;

import com.nhave.tow.api.integration.WrenchHandler;
import com.nhave.tow.api.wrenchmodes.WrenchMode;
import com.nhave.tow.registry.ModItems;

import blusunrize.immersiveengineering.api.MultiblockHandler;
import blusunrize.immersiveengineering.api.TargetingInfo;
import blusunrize.immersiveengineering.api.energy.wires.IImmersiveConnectable;
import blusunrize.immersiveengineering.api.energy.wires.ImmersiveNetHandler;
import blusunrize.immersiveengineering.common.IESaveData;
import blusunrize.immersiveengineering.common.blocks.BlockIEMultiblock;
import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces.IConfigurableSides;
import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces.IDirectionalTile;
import blusunrize.immersiveengineering.common.blocks.IEBlockInterfaces.IHammerInteraction;
import blusunrize.immersiveengineering.common.util.ItemNBTHelper;
import blusunrize.immersiveengineering.common.util.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ImmersiveEngineeringHandler extends WrenchHandler
{
	@Override
	public EnumActionResult onWrenchUseFirst(WrenchMode mode, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);
		TileEntity tileEntity = world.getTileEntity(pos);
        IBlockState state = world.getBlockState(pos);
	    Block block = state.getBlock();
		
		if (mode == ModItems.modeWrench)
		{
			EnumActionResult ret = tryFormMB(player, world, pos, side, hand);
			if (ret != EnumActionResult.PASS)
			{
				if (world.isRemote && ret != EnumActionResult.PASS) Minecraft.getMinecraft().getConnection().sendPacket(new CPacketPlayerTryUseItemOnBlock(pos, side, hand, hitX, hitY, hitZ));
				return ret;
			}
			else if (!(block instanceof BlockIEMultiblock) && (tileEntity instanceof IConfigurableSides || (tileEntity instanceof IDirectionalTile && ((IDirectionalTile)tileEntity).canHammerRotate(side, hitX, hitY, hitZ, player)) || tileEntity instanceof IHammerInteraction))
			{
				if (!world.isRemote)
				{
					ItemStack heldItem = player.getHeldItem(hand).copy();
					ItemStack hammer = GameRegistry.makeItemStack("immersiveengineering:tool", 0, 1, null);
					
					player.setHeldItem(hand, hammer);
					block.onBlockActivated(world, pos, state, player, hand, side, hitX, hitY, hitZ);
					player.setHeldItem(hand, heldItem);
					return EnumActionResult.SUCCESS;
				}
				else
				{
					SoundEvent soundName = block.getSoundType(state, world, pos, player).getPlaceSound();
					player.playSound(soundName, 1.0F, 0.6F);
					player.swingArm(EnumHand.MAIN_HAND);
				}
			}
		}
		else if (mode == ModItems.modeTune && tileEntity instanceof IImmersiveConnectable)
		{
			TargetingInfo target = new TargetingInfo(side, hitX, hitY, hitZ);
			BlockPos masterPos = ((IImmersiveConnectable)tileEntity).getConnectionMaster(null, target);
			tileEntity = world.getTileEntity(masterPos);
			if(!(tileEntity instanceof IImmersiveConnectable)) return EnumActionResult.PASS;
			if (!world.isRemote)
			{
				IImmersiveConnectable nodeHere = (IImmersiveConnectable) tileEntity;
				boolean cut = ImmersiveNetHandler.INSTANCE.clearAllConnectionsFor(Utils.toCC(nodeHere), world, target);
				IESaveData.setDirty(world.provider.getDimension());
			}
			else Minecraft.getMinecraft().getConnection().sendPacket(new CPacketPlayerTryUseItemOnBlock(pos, side, hand, hitX, hitY, hitZ));
			return EnumActionResult.SUCCESS;
		}
		
		return EnumActionResult.PASS;
	}
	
	private EnumActionResult tryFormMB(EntityPlayer player, World world, BlockPos pos, EnumFacing side, EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);
		if (stack.getMetadata() == 0)
		{
			String[] permittedMultiblocks = null;
			String[] interdictedMultiblocks = null;
			if (ItemNBTHelper.hasKey(stack, "multiblockPermission"))
			{
				NBTTagList list = stack.getTagCompound().getTagList("multiblockPermission", 8);
				permittedMultiblocks = new String[list.tagCount()];
				for (int i = 0; i < permittedMultiblocks.length; i++)
					permittedMultiblocks[i] = list.getStringTagAt(i);
			}
			if (ItemNBTHelper.hasKey(stack, "multiblockInterdiction"))
			{
				NBTTagList list = stack.getTagCompound().getTagList("multiblockInterdiction", 8);
				interdictedMultiblocks = new String[list.tagCount()];
				for (int i = 0; i < interdictedMultiblocks.length; i++)
					interdictedMultiblocks[i] = list.getStringTagAt(i);
			}
			for (MultiblockHandler.IMultiblock mb : MultiblockHandler.getMultiblocks())
				if (mb.isBlockTrigger(world.getBlockState(pos)))
				{
					boolean b = permittedMultiblocks == null;
					if (permittedMultiblocks != null)
						for (String s : permittedMultiblocks)
							if (mb.getUniqueName().equalsIgnoreCase(s))
							{
								b = true;
								continue;
							}
					if (!b)
						break;
					if (interdictedMultiblocks != null)
						for (String s : interdictedMultiblocks)
							if (mb.getUniqueName().equalsIgnoreCase(s))
							{
								b = false;
								continue;
							}
					if (!b)
						break;
					if (MultiblockHandler.postMultiblockFormationEvent(player, mb, pos, stack).isCanceled())
						continue;
					if (mb.createStructure(world, pos, side, player))
						return EnumActionResult.SUCCESS;
				}
		}
		return EnumActionResult.PASS;
	}
}