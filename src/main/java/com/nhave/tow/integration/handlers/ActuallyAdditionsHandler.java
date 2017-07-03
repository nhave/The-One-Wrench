package com.nhave.tow.integration.handlers;

import com.nhave.tow.api.integration.IDataWipe;
import com.nhave.tow.api.integration.WrenchHandler;
import com.nhave.tow.api.wrenchmodes.WrenchMode;
import com.nhave.tow.helpers.DismantleHelper;
import com.nhave.tow.registry.ModItems;

import de.ellpeck.actuallyadditions.api.ActuallyAdditionsAPI;
import de.ellpeck.actuallyadditions.mod.blocks.BlockColoredLamp;
import de.ellpeck.actuallyadditions.mod.blocks.BlockLampPowerer;
import de.ellpeck.actuallyadditions.mod.blocks.BlockSmileyCloud;
import de.ellpeck.actuallyadditions.mod.blocks.base.BlockContainerBase;
import de.ellpeck.actuallyadditions.mod.items.ItemPhantomConnector;
import de.ellpeck.actuallyadditions.mod.tile.TileEntityLaserRelay;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class ActuallyAdditionsHandler extends WrenchHandler implements IDataWipe
{
	@Override
	public EnumActionResult onWrenchUseFirst(WrenchMode mode, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
		ItemStack stack = player.getHeldItem(hand);
		TileEntity tile = world.getTileEntity(pos);
        IBlockState state = world.getBlockState(pos);
	    Block block = state.getBlock();
	    
		if (mode == ModItems.modeWrench && player.isSneaking() && block != null && (block instanceof BlockContainerBase || block instanceof BlockLampPowerer || block instanceof BlockColoredLamp))
	    {
			if (block instanceof BlockSmileyCloud) return EnumActionResult.PASS;
			boolean allDrops = block instanceof BlockContainerBase ? false : true;
	    	if (!world.isRemote)
	    	{
				DismantleHelper.dismantleBlock(world, pos, state, player, allDrops);
	    		return EnumActionResult.SUCCESS;
	    	}
	    	else player.swingArm(hand);
	    }
		else if (mode == ModItems.modeTune)
		{
			if (tile instanceof TileEntityLaserRelay)
			{
				TileEntityLaserRelay relay = (TileEntityLaserRelay) tile;
				if (!(world.isRemote))
				{
					if (ItemPhantomConnector.getStoredPosition(stack) == null)
					{
						ItemPhantomConnector.storeConnection(stack, pos.getX(), pos.getY(), pos.getZ(), world);
						player.sendMessage(new TextComponentTranslation("tooltip.actuallyadditions.laser.stored.desc", new Object[0]));
						
						return EnumActionResult.SUCCESS;
					}
					else
					{
						BlockPos savedPos = ItemPhantomConnector.getStoredPosition(stack);
						if (savedPos != null)
						{
							TileEntity savedTile = world.getTileEntity(savedPos);
							if (savedTile instanceof TileEntityLaserRelay)
							{
								int distanceSq = (int) savedPos.distanceSq(pos);
								TileEntityLaserRelay savedRelay = (TileEntityLaserRelay) savedTile;
								
								int lowestRange = Math.min(relay.getMaxRange(), savedRelay.getMaxRange());
								int range = lowestRange * lowestRange;
								if ((ItemPhantomConnector.getStoredWorld(stack) == world) && (savedRelay.type == relay.type) && (distanceSq <= range) && (ActuallyAdditionsAPI.connectionHandler.addConnection(savedPos, pos, relay.type, world)))
								{
									ItemPhantomConnector.clearStorage(stack, new String[] { "XCoordOfTileStored", "YCoordOfTileStored", "ZCoordOfTileStored", "WorldOfTileStored" });
									
									((TileEntityLaserRelay) savedTile).sendUpdate();
									relay.sendUpdate();
									
									player.sendMessage(new TextComponentTranslation("tooltip.actuallyadditions.laser.connected.desc", new Object[0]));
									
									return EnumActionResult.SUCCESS;
								}
							}
							
							player.sendMessage(new TextComponentTranslation("tooltip.actuallyadditions.laser.cantConnect.desc", new Object[0]));
							ItemPhantomConnector.clearStorage(stack, new String[] { "XCoordOfTileStored", "YCoordOfTileStored", "ZCoordOfTileStored", "WorldOfTileStored" });
							
							return EnumActionResult.SUCCESS;
						}
					}
				}
            	else
				{
					player.swingArm(EnumHand.MAIN_HAND);
				}
				return EnumActionResult.PASS;
			}
		}
		return EnumActionResult.PASS;
	}
	
	@Override
	public boolean wipeData(ItemStack stack)
	{
		if (ItemPhantomConnector.getStoredPosition(stack) != null)
		{
			ItemPhantomConnector.clearStorage(stack, new String[] { "XCoordOfTileStored", "YCoordOfTileStored", "ZCoordOfTileStored", "WorldOfTileStored" });
			return true;
		}
		return false;
	}
}
