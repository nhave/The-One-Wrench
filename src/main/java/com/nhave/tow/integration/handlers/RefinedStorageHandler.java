package com.nhave.tow.integration.handlers;

import com.nhave.tow.integration.IDataWipe;
import com.nhave.tow.integration.WrenchHandler;
import com.nhave.tow.registry.ModItems;
import com.nhave.tow.wrenchmodes.WrenchMode;
import com.raoulvdberge.refinedstorage.api.util.IWrenchable;
import com.raoulvdberge.refinedstorage.block.BlockNode;
import com.raoulvdberge.refinedstorage.tile.TileNode;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class RefinedStorageHandler extends WrenchHandler implements IDataWipe
{
	public static final String NBT_WRENCHED_DATA = "WrenchedData";
	public static final String NBT_WRENCHED_TILE = "WrenchedTile";
	
	@Override
	public EnumActionResult onWrenchUseFirst(WrenchMode mode, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
		TileEntity tile = world.getTileEntity(pos);
        IBlockState state = world.getBlockState(pos);
	    Block block = state.getBlock();
        ItemStack stack = player.getHeldItem(hand);

        if (mode == ModItems.modeWrench && player.isSneaking())
		{
            if (tile instanceof TileNode)
            {
            	if (!world.isRemote)
            	{
	                NBTTagCompound data = new NBTTagCompound();
	
	                ((TileNode) tile).writeConfiguration(data);
	
	                ItemStack tileStack = state.getBlock().getDrops(world, pos, state, 0).get(0);
	
	                if (!tileStack.hasTagCompound())
	                {
	                    tileStack.setTagCompound(new NBTTagCompound());
	                }
	
	                tileStack.getTagCompound().setTag(BlockNode.NBT_REFINED_STORAGE_DATA, data);
	
	                world.setBlockToAir(pos);
	
	                InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), tileStack);
	                
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
        else if (mode == ModItems.modeTune)
		{
        	if (!world.isRemote)
        	{
        		if (!player.isSneaking())
        		{
        			if (tile instanceof IWrenchable)
        			{
                        IWrenchable wrenchable = (IWrenchable) tile;
                        
                        stack.getTagCompound().setString(NBT_WRENCHED_TILE, wrenchable.getClass().getName());
                        stack.getTagCompound().setTag(NBT_WRENCHED_DATA, wrenchable.writeConfiguration(new NBTTagCompound()));
                        
                        player.sendMessage(new TextComponentTranslation("item.refinedstorage:wrench.saved"));
                        return EnumActionResult.SUCCESS;
        			}
        		}
        		else 
        		{
        			if (tile instanceof IWrenchable)
        			{
                        IWrenchable wrenchable = (IWrenchable) tile;
        			    
                        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(NBT_WRENCHED_DATA) && stack.getTagCompound().hasKey(NBT_WRENCHED_TILE))
                        {
                            NBTTagCompound wrenchedData = stack.getTagCompound().getCompoundTag(NBT_WRENCHED_DATA);
                            String wrenchedTile = stack.getTagCompound().getString(NBT_WRENCHED_TILE);
                            
                            if (wrenchable.getClass().getName().equals(wrenchedTile))
                            {
                                wrenchable.readConfiguration(wrenchedData);
                                
                                tile.markDirty();
                                
                                player.sendMessage(new TextComponentTranslation("item.refinedstorage:wrench.read"));
                            }
                        }
                        
                        return EnumActionResult.SUCCESS;
                    }
        		}
        	}
		}
		
		return EnumActionResult.PASS;
	}
	
	@Override
	public boolean wipeData(ItemStack stack)
	{
		boolean result = false;
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey(NBT_WRENCHED_DATA))
		{
			stack.getTagCompound().removeTag(NBT_WRENCHED_DATA);
			result = true;
		}
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey(NBT_WRENCHED_TILE))
		{
			stack.getTagCompound().removeTag(NBT_WRENCHED_TILE);
			result = true;
		}
		return result;
	}
}
