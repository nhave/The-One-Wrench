package com.nhave.tow.registry;

import com.nhave.tow.client.integration.IEClientEventHandler;
import com.nhave.tow.client.integration.RFToolsClientEventHandler;
import com.nhave.tow.helpers.DismantleHelper;
import com.nhave.tow.integration.WrenchRegistry;
import com.nhave.tow.integration.handlers.ActuallyAdditionsHandler;
import com.nhave.tow.integration.handlers.EmbersHandler;
import com.nhave.tow.integration.handlers.ExtremeReactorsHandler;
import com.nhave.tow.integration.handlers.ImmersiveEngineeringHandler;
import com.nhave.tow.integration.handlers.IndustrialCraftHandler;
import com.nhave.tow.integration.handlers.IntegratedDynamicsHandler;
import com.nhave.tow.integration.handlers.OpenComputersHandler;
import com.nhave.tow.integration.handlers.RFToolsHandler;
import com.nhave.tow.integration.handlers.RefinedStorageHandler;
import com.nhave.tow.integration.handlers.StorageDrawersHandler;
import com.nhave.tow.integration.handlers.TechRebornHandler;
import com.nhave.tow.integration.handlers.TeslaCoreLibHandler;
import com.nhave.tow.wrenchmodes.WrenchMode;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

public class ModIntegration
{
	public static WrenchMode modeRFTools;
	public static WrenchMode modeEmbers;
	
	public static void postInit(FMLPostInitializationEvent event)
	{
		if (ModConfig.customDismantle.length > 0)
		{
			for (int i = 0; i < ModConfig.customDismantle.length; ++i)
			{
				DismantleHelper.addBlock(ModConfig.customDismantle[i]);
			}
		}
		if (ModConfig.vanillaDismantle)
		{
			WrenchRegistry.register("Vanilla Dismantling");
			DismantleHelper.addBlock("minecraft:white_shulker_box/n");
			DismantleHelper.addBlock("minecraft:orange_shulker_box/n");
			DismantleHelper.addBlock("minecraft:magenta_shulker_box/n");
			DismantleHelper.addBlock("minecraft:light_blue_shulker_box/n");
			DismantleHelper.addBlock("minecraft:yellow_shulker_box/n");
			DismantleHelper.addBlock("minecraft:lime_shulker_box/n");
			DismantleHelper.addBlock("minecraft:pink_shulker_box/n");
			DismantleHelper.addBlock("minecraft:gray_shulker_box/n");
			DismantleHelper.addBlock("minecraft:silver_shulker_box/n");
			DismantleHelper.addBlock("minecraft:cyan_shulker_box/n");
			DismantleHelper.addBlock("minecraft:purple_shulker_box/n");
			DismantleHelper.addBlock("minecraft:blue_shulker_box/n");
			DismantleHelper.addBlock("minecraft:brown_shulker_box/n");
			DismantleHelper.addBlock("minecraft:green_shulker_box/n");
			DismantleHelper.addBlock("minecraft:red_shulker_box/n");
			DismantleHelper.addBlock("minecraft:black_shulker_box/n");
			DismantleHelper.addBlock("minecraft:hopper");
			DismantleHelper.addBlock("minecraft:ender_chest/p/n");
		    
		}
		if (Loader.isModLoaded("opencomputers") && ModConfig.enableOpenComputers)
		{
			try
			{
				WrenchRegistry.register(new OpenComputersHandler(), "Open Computers");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("refinedstorage") && ModConfig.enableRefinedStorage)
		{
			try
			{
				WrenchRegistry.register(new RefinedStorageHandler(), "Refined Storage");
				DismantleHelper.addBlock("refinedstorage:machine_casing");
				DismantleHelper.addBlock("refinedstorage:controller");
				DismantleHelper.addBlock("refinedstorage:processing_pattern_encoder");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("ic2") && ModConfig.enableIC2)
		{
			try
			{
				WrenchRegistry.register(new IndustrialCraftHandler(ModConfig.ic2CutWires, ModConfig.ic2ClassicRotation), "Industrial Craft 2");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("bigreactors") && ModConfig.enableExtremeReactors)
		{
			try
			{
				WrenchRegistry.register(new ExtremeReactorsHandler(), "Extreme Reactors");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("integrateddynamics") && ModConfig.enableIntegratedDynamics)
		{
			try
			{
				WrenchRegistry.register(new IntegratedDynamicsHandler(), "Integrated Dynamics");
				DismantleHelper.addBlock("integrateddynamics:variablestore");
				DismantleHelper.addBlock("integrateddynamics:logic_programmer");
				DismantleHelper.addBlock("integrateddynamics:energy_battery");
				DismantleHelper.addBlock("integrateddynamics:creative_energy_battery");
				DismantleHelper.addBlock("integrateddynamics:coal_generator");
				DismantleHelper.addBlock("integrateddynamics:proxy");
				DismantleHelper.addBlock("integrateddynamics:materializer");
				DismantleHelper.addBlock("integrateddynamics:squeezer");
				DismantleHelper.addBlock("integrateddynamics:drying_basin");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("actuallyadditions") && ModConfig.enableActuallyAdditions)
		{
			try
			{
				WrenchRegistry.register(new ActuallyAdditionsHandler(), "Actually Additions");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("techreborn") && ModConfig.enableTechReborn)
		{
			try
			{
				WrenchRegistry.register(new TechRebornHandler(ModConfig.trCutWires, ModConfig.trRotation), "Tech Reborn");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("storagedrawers") && ModConfig.enableStorageDrawers)
		{
			try
			{
				WrenchRegistry.register(new StorageDrawersHandler(), "Storage Drawers");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("rftools") && ModConfig.enableRFTools)
		{
			try
			{
				WrenchRegistry.register(new RFToolsHandler(), "RFTools");
			}
			catch (Exception e) {}
			if (event.getSide() == Side.CLIENT)
			{
				try
				{
			        MinecraftForge.EVENT_BUS.register(new RFToolsClientEventHandler());
				}
				catch (Exception e) {}
			}
		}
		if (Loader.isModLoaded("teslacorelib") && ModConfig.enableTeslaCoreLib)
		{
			try
			{
				WrenchRegistry.register(new TeslaCoreLibHandler(), "Tesla Core Lib");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("embers") && ModConfig.enableEmbers)
		{
			try
			{
				WrenchRegistry.register(new EmbersHandler(), "Embers");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("immersiveengineering") && ModConfig.enableImmersiveEngineering)
		{
			try
			{
				WrenchRegistry.register(new ImmersiveEngineeringHandler(), "Immersive Engineering");
			}
			catch (Exception e) {}
			if (event.getSide() == Side.CLIENT)
			{
				try
				{
			        MinecraftForge.EVENT_BUS.register(new IEClientEventHandler());
				}
				catch (Exception e) {}
			}
		}
	}
}