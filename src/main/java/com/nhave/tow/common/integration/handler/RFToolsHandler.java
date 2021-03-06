package com.nhave.tow.common.integration.handler;

import com.nhave.tow.common.content.ModIntegration;
import com.nhave.tow.common.integration.WrenchHandler;
import com.nhave.tow.common.integration.mode.WrenchModeRFTools;
import com.nhave.tow.common.item.ItemOmniwrench;
import com.nhave.tow.common.wrenchmode.IDataWipe;
import com.nhave.tow.common.wrenchmode.ModeRegistry;
import com.nhave.tow.common.wrenchmode.WrenchMode;

import mcjty.lib.api.smartwrench.SmartWrenchSelector;
import mcjty.lib.varia.GlobalCoordinate;
import mcjty.lib.varia.Logging;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RFToolsHandler extends WrenchHandler implements IDataWipe
{
	public RFToolsHandler()
	{
		ModIntegration.modeRFTools = ModeRegistry.register(new WrenchModeRFTools("smartwrench"));
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@Override
	public EnumActionResult onWrenchUseFirst(WrenchMode mode, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
		if (mode == ModIntegration.modeRFTools)
		{
			ItemStack stack = player.getHeldItem(hand);
			TileEntity tile = world.getTileEntity(pos);
			GlobalCoordinate b = getCurrentBlock(stack);
			
            if (!player.isSneaking() && b != null)
	        {
				if (!world.isRemote)
				{
		            if (b.getDimension() != world.provider.getDimension())
		            {
		                Logging.message(player, TextFormatting.RED + "The selected block is in another dimension!");
		                return EnumActionResult.FAIL;
		            }
		            TileEntity te = world.getTileEntity(b.getCoordinate());
		            if (te instanceof SmartWrenchSelector)
		            {
		                SmartWrenchSelector smartWrenchSelector = (SmartWrenchSelector) te;
		                smartWrenchSelector.selectBlock(player, pos);
		            }
		    		return EnumActionResult.SUCCESS;
				}
				else player.swingArm(hand);
	        }
		}
		return EnumActionResult.PASS;
	}
	
	public static void setCurrentBlock(ItemStack itemStack, GlobalCoordinate c)
	{
        NBTTagCompound tagCompound = itemStack.getTagCompound();
        if (tagCompound == null)
        {
            tagCompound = new NBTTagCompound();
            itemStack.setTagCompound(tagCompound);
        }
        if (c == null)
        {
            tagCompound.removeTag("selectedX");
            tagCompound.removeTag("selectedY");
            tagCompound.removeTag("selectedZ");
            tagCompound.removeTag("selectedDim");
        }
        else
        {
            tagCompound.setInteger("selectedX", c.getCoordinate().getX());
            tagCompound.setInteger("selectedY", c.getCoordinate().getY());
            tagCompound.setInteger("selectedZ", c.getCoordinate().getZ());
            tagCompound.setInteger("selectedDim", c.getDimension());
        }
    }
	
    public static GlobalCoordinate getCurrentBlock(ItemStack itemStack)
    {
        NBTTagCompound tagCompound = itemStack.getTagCompound();
        if (tagCompound != null && tagCompound.hasKey("selectedX"))
        {
            int x = tagCompound.getInteger("selectedX");
            int y = tagCompound.getInteger("selectedY");
            int z = tagCompound.getInteger("selectedZ");
            int dim = tagCompound.getInteger("selectedDim");
            return new GlobalCoordinate(new BlockPos(x, y, z), dim);
        }
        return null;
    }
    
    @Override
    public boolean shouldDenyBlockActivate(WrenchMode mode, EntityPlayer player, World world, BlockPos pos)
    {
    	return mode == ModIntegration.modeRFTools;
    }
    
    /**
     * Hijacking RFTools Block Protector
     * using EventPriority.HIGHEST
     * 
     * @param event
     */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerInteractEvent(PlayerInteractEvent.RightClickBlock event)
    {
    	if (event.getHand() == EnumHand.OFF_HAND) return;
    	
    	EntityPlayer player = event.getEntityPlayer();
    	ItemStack stack = event.getItemStack();
		TileEntity tile = event.getWorld().getTileEntity(event.getPos());
		
		if (stack != null && stack.getItem() instanceof ItemOmniwrench && ((ItemOmniwrench) stack.getItem()).getWrenchMode(stack) == ModIntegration.modeRFTools)
		{
			if (player.isSneaking() && tile != null && tile instanceof SmartWrenchSelector)
		    {
				//player.sendMessage(new TextComponentString("test"));
				GlobalCoordinate b = getCurrentBlock(stack);
				
				if (!event.getWorld().isRemote)
				{
					if (b == null)
					{
						setCurrentBlock(stack, new GlobalCoordinate(event.getPos(), event.getWorld().provider.getDimension()));
		                Logging.message(player, TextFormatting.YELLOW + "Selected block");
					}
					else
					{
						setCurrentBlock(stack, null);
		                Logging.message(player, TextFormatting.YELLOW + "Cleared selected block");
					}
				}
				else player.swingArm(event.getHand());
				event.setCanceled(true);
		    }
    	}
    }
	
	@Override
	public boolean wipeData(ItemStack stack)
	{
		boolean result = false;
		GlobalCoordinate b = RFToolsHandler.getCurrentBlock(stack);
        if (b != null)
        {
        	setCurrentBlock(stack, null);
        	result = true;
        }
		return result;
	}
}