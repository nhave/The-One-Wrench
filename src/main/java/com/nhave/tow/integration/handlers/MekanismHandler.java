package com.nhave.tow.integration.handlers;

import com.nhave.nhc.helpers.ItemNBTHelper;
import com.nhave.tow.api.TOWAPI;
import com.nhave.tow.api.integration.WrenchHandler;
import com.nhave.tow.api.wrenchmodes.WrenchMode;
import com.nhave.tow.integration.modes.WrenchModeMekanism;
import com.nhave.tow.registry.ModIntegration;

import mekanism.api.Coord4D;
import mekanism.api.EnumColor;
import mekanism.api.Range4D;
import mekanism.api.transmitters.TransmissionType;
import mekanism.common.Mekanism;
import mekanism.common.SideData;
import mekanism.common.base.ISideConfiguration;
import mekanism.common.base.TileNetworkList;
import mekanism.common.item.ItemConfigurator.ConfiguratorMode;
import mekanism.common.network.PacketTileEntity.TileEntityMessage;
import mekanism.common.tile.component.TileComponentConfig;
import mekanism.common.tile.prefab.TileEntityBasicBlock;
import mekanism.common.util.LangUtils;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.SecurityUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class MekanismHandler extends WrenchHandler
{
	public MekanismHandler()
	{
		ModIntegration.modeMekanism = TOWAPI.modeRegistry.register(new WrenchModeMekanism("mekanism", null));
	}
	
	@Override
	public EnumActionResult onWrenchUseFirst(WrenchMode mode, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
		if(mode == ModIntegration.modeMekanism && !world.isRemote)
		{
			ItemStack stack = player.getHeldItem(hand);
			Block block = world.getBlockState(pos).getBlock();
			TileEntity tile = world.getTileEntity(pos);
			
			if(getState(stack).isConfigurating()) //Configurate
			{
				if(tile instanceof ISideConfiguration && ((ISideConfiguration)tile).getConfig().supports(getState(stack).getTransmission()))
				{
					ISideConfiguration config = (ISideConfiguration)tile;
					SideData initial = config.getConfig().getOutput(getState(stack).getTransmission(), side, config.getOrientation());

					if(initial != TileComponentConfig.EMPTY)
					{
						if(!player.isSneaking())
						{
							player.sendMessage(new TextComponentString(EnumColor.DARK_BLUE + "[Mekanism]" + EnumColor.GREY + " " + getViewModeText(getState(stack).getTransmission()) + ": " + initial.color + initial.localize() + " (" + initial.color.getColoredName() + ")"));
						}
						else
						{
							if(SecurityUtils.canAccess(player, tile))
							{
								MekanismUtils.incrementOutput(config, getState(stack).getTransmission(), MekanismUtils.getBaseOrientation(side, config.getOrientation()));
								SideData data = config.getConfig().getOutput(getState(stack).getTransmission(), side, config.getOrientation());
								player.sendMessage(new TextComponentString(EnumColor.DARK_BLUE + "[Mekanism]" + EnumColor.GREY + " " + getToggleModeText(getState(stack).getTransmission()) + ": " + data.color + data.localize() + " (" + data.color.getColoredName() + ")"));
	
								if(config instanceof TileEntityBasicBlock)
								{
									TileEntityBasicBlock tileEntity = (TileEntityBasicBlock)config;
									Mekanism.packetHandler.sendToReceivers(new TileEntityMessage(Coord4D.get(tileEntity), tileEntity.getNetworkedData(new TileNetworkList())), new Range4D(Coord4D.get(tileEntity)));
								}
							}
							else
							{
								SecurityUtils.displayNoAccess(player);
							}
						}
					}
					
					return EnumActionResult.SUCCESS;
				}
			}
		}
		return EnumActionResult.PASS;
	}
		
	public String getViewModeText(TransmissionType type)
	{
		String base = LangUtils.localize("tooltip.configurator.viewMode");
		return String.format(base, type.localize().toLowerCase());
	}
	
	public String getToggleModeText(TransmissionType type)
	{
		String base = LangUtils.localize("tooltip.configurator.toggleMode");
		return String.format(base, type.localize());
	}
	
	public static String getStateDisplay(ConfiguratorMode mode)
	{
		return mode.getName();
	}
	
	public static EnumColor getColor(ConfiguratorMode mode)
	{
		return mode.getColor();
	}
	
	public static ConfiguratorMode getState(ItemStack itemstack)
	{
		int state = Math.max(0, Math.min(4, ItemNBTHelper.getInteger(itemstack, "WRENCH", "MEKASTATE", 0)));
		switch (state)
		{
		case 0:
			return ConfiguratorMode.CONFIGURATE_ENERGY;
		case 1:
			return ConfiguratorMode.CONFIGURATE_FLUIDS;
		case 2:
			return ConfiguratorMode.CONFIGURATE_GASES;
		case 3:
			return ConfiguratorMode.CONFIGURATE_HEAT;
		case 4:
			return ConfiguratorMode.CONFIGURATE_ITEMS;
		default:
			return ConfiguratorMode.CONFIGURATE_ENERGY;
		}
	}
}