package com.nhave.tow.integration.handlers;

import java.util.Random;

import com.nhave.tow.api.integration.WrenchHandler;
import com.nhave.tow.api.wrenchmodes.WrenchMode;
import com.nhave.tow.helpers.DismantleHelper;
import com.nhave.tow.registry.ModItems;

import engineers.workshop.client.container.slot.SlotBase;
import engineers.workshop.common.table.BlockTable;
import engineers.workshop.common.table.TileTable;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EngineersWorkshopHandler extends WrenchHandler
{
	@Override
	public EnumActionResult onWrenchUseFirst(WrenchMode mode, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
	    TileEntity tile = world.getTileEntity(pos);
		IBlockState bs = world.getBlockState(pos);
	    Block block = bs.getBlock();
	    
	    if (mode == ModItems.modeWrench)
		{
	    	if (player.isSneaking() && block instanceof BlockTable)
			{
	    		dropInventory(world, pos);
	    		DismantleHelper.dismantleBlock(world, pos, bs, player, true);
	    		if (!world.isRemote) return EnumActionResult.SUCCESS;
				else
				{
					player.playSound(block.getSoundType(bs, world, pos, player).getPlaceSound(), 1.0F, 0.6F);
					player.swingArm(EnumHand.MAIN_HAND);
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
		return block instanceof BlockTable;
	}
	
	protected void dropInventory(World world, BlockPos pos)
	{
		if (!world.isRemote)
		{
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			
			TileEntity tileEntity = world.getTileEntity(pos);
			
			if (tileEntity instanceof TileTable)
			{
				TileTable table = (TileTable) tileEntity;
				for (SlotBase slot : table.getSlots())
				{
					if (slot.shouldDropOnClosing())
					{
						ItemStack itemStack = slot.getStack();
						if (!itemStack.isEmpty())
						{
							Random random = new Random();
							
							float dX = random.nextFloat() * 0.8F + 0.1F;
							float dY = random.nextFloat() * 0.8F + 0.1F;
							float dZ = random.nextFloat() * 0.8F + 0.1F;
							
							EntityItem entityItem = new EntityItem(world, (double) ((float) x + dX), (double) ((float) y + dY), (double) ((float) z + dZ), itemStack.copy());
							if (itemStack.hasTagCompound())
							{
								entityItem.getItem().setTagCompound(itemStack.getTagCompound().copy());
							}
							float factor = 0.05F;
							
							entityItem.motionX = random.nextGaussian() * (double) factor;
							entityItem.motionX = random.nextGaussian() * (double) factor + 0.2D;
							entityItem.motionX = random.nextGaussian() * (double) factor;
							
							world.spawnEntity(entityItem);
							itemStack.setCount(0);
						}
					}
				}
			}
		}
	}
}