package com.nhave.tow.common.integration.handler;

import com.nhave.tow.common.content.ModItems;
import com.nhave.tow.common.integration.WrenchHandler;
import com.nhave.tow.common.wrenchmode.WrenchMode;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.ndrei.teslacorelib.capabilities.TeslaCoreCapabilities;
import net.ndrei.teslacorelib.capabilities.wrench.ITeslaWrenchHandler;
import net.ndrei.teslacorelib.items.TeslaWrench;

public class TeslaCoreLibHandler extends WrenchHandler
{
	@Override
	public EnumActionResult onWrenchUseFirst(WrenchMode mode, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
	    EnumActionResult result = EnumActionResult.PASS;
	    if (mode == ModItems.modeWrench)
		{
			TileEntity tile = world.getTileEntity(pos);
	        IBlockState state = world.getBlockState(pos);
		    Block block = state.getBlock();
		    
	        // test if block implements the interface
	        if (block instanceof ITeslaWrenchHandler)
	        {
	            result = ((ITeslaWrenchHandler)state.getBlock()).onWrenchUse((TeslaWrench) GameRegistry.makeItemStack("teslacorelib:wrench", 0, 1, null).getItem(), player, world, pos, hand, side, hitX, hitY, hitZ);
	            
	            if (world.isRemote)
	            {
	        		player.playSound(block.getSoundType(state, world, pos, player).getPlaceSound(), 1.0F, 0.6F);
	        		player.swingArm(EnumHand.MAIN_HAND);
	            }
	        }
	        if (result == EnumActionResult.PASS)
	        {
	            if ((tile != null) && (tile.hasCapability(TeslaCoreCapabilities.CAPABILITY_WRENCH, side)))
	            {
	                result = tile.getCapability(TeslaCoreCapabilities.CAPABILITY_WRENCH, side).onWrenchUse((TeslaWrench) GameRegistry.makeItemStack("teslacorelib:wrench", 0, 1, null).getItem(), player, world, pos, hand, side, hitX, hitY, hitZ);
	                
		            if (world.isRemote)
		            {
		        		player.playSound(block.getSoundType(state, world, pos, player).getPlaceSound(), 1.0F, 0.6F);
		        		player.swingArm(EnumHand.MAIN_HAND);
		            }
	            }
	        }
		} 
	    return result;
	}
	
	@Override
	public boolean preventBlockRotation(EntityPlayer player, World world, BlockPos pos)
	{
		TileEntity tile = world.getTileEntity(pos);
        IBlockState state = world.getBlockState(pos);
	    Block block = state.getBlock();
		return (block instanceof ITeslaWrenchHandler || ((tile != null) && (tile.hasCapability(TeslaCoreCapabilities.CAPABILITY_WRENCH, null))));
	}
}