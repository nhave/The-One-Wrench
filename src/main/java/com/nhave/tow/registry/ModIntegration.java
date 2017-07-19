package com.nhave.tow.registry;

import com.nhave.tow.api.TOWAPI;
import com.nhave.tow.api.wrenchmodes.WrenchMode;
import com.nhave.tow.client.integration.IEClientEventHandler;
import com.nhave.tow.client.integration.RFToolsClientEventHandler;
import com.nhave.tow.helpers.DismantleHelper;
import com.nhave.tow.integration.handlers.ActuallyAdditionsHandler;
import com.nhave.tow.integration.handlers.EmbersHandler;
import com.nhave.tow.integration.handlers.ExtremeReactorsHandler;
import com.nhave.tow.integration.handlers.ForestryHandler;
import com.nhave.tow.integration.handlers.ImmersiveEngineeringHandler;
import com.nhave.tow.integration.handlers.IndustrialCraftHandler;
import com.nhave.tow.integration.handlers.IntegratedDynamicsHandler;
import com.nhave.tow.integration.handlers.OpenComputersHandler;
import com.nhave.tow.integration.handlers.PSIHandler;
import com.nhave.tow.integration.handlers.RFToolsHandler;
import com.nhave.tow.integration.handlers.RefinedStorageHandler;
import com.nhave.tow.integration.handlers.SonarCoreHandler;
import com.nhave.tow.integration.handlers.StorageDrawersHandler;
import com.nhave.tow.integration.handlers.TechRebornHandler;
import com.nhave.tow.integration.handlers.TeslaCoreLibHandler;
import com.nhave.tow.integration.handlers.XNetHandler;

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
			TOWAPI.integrationRegistry.register("Vanilla Dismantling");
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
				TOWAPI.integrationRegistry.register(new OpenComputersHandler(), "Open Computers");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("refinedstorage") && ModConfig.enableRefinedStorage)
		{
			try
			{
				TOWAPI.integrationRegistry.register(new RefinedStorageHandler(), "Refined Storage");
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
				TOWAPI.integrationRegistry.register(new IndustrialCraftHandler(ModConfig.ic2CutWires, ModConfig.ic2ClassicRotation), "Industrial Craft 2");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("bigreactors") && ModConfig.enableExtremeReactors)
		{
			try
			{
				TOWAPI.integrationRegistry.register(new ExtremeReactorsHandler(), "Extreme Reactors");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("integrateddynamics") && ModConfig.enableIntegratedDynamics)
		{
			try
			{
				TOWAPI.integrationRegistry.register(new IntegratedDynamicsHandler(), "Integrated Dynamics");
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
				TOWAPI.integrationRegistry.register(new ActuallyAdditionsHandler(), "Actually Additions");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("techreborn") && ModConfig.enableTechReborn)
		{
			try
			{
				TOWAPI.integrationRegistry.register(new TechRebornHandler(ModConfig.trCutWires, ModConfig.trRotation), "Tech Reborn");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("storagedrawers") && ModConfig.enableStorageDrawers)
		{
			try
			{
				TOWAPI.integrationRegistry.register(new StorageDrawersHandler(), "Storage Drawers");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("rftools") && ModConfig.enableRFTools)
		{
			try
			{
				TOWAPI.integrationRegistry.register(new RFToolsHandler(), "RFTools");
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
				TOWAPI.integrationRegistry.register(new TeslaCoreLibHandler(), "Tesla Core Lib");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("embers") && ModConfig.enableEmbers)
		{
			try
			{
				TOWAPI.integrationRegistry.register(new EmbersHandler(), "Embers");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("immersiveengineering") && ModConfig.enableImmersiveEngineering)
		{
			try
			{
				TOWAPI.integrationRegistry.register(new ImmersiveEngineeringHandler(), "Immersive Engineering");
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
		if (Loader.isModLoaded("forestry") && ModConfig.enableForestry)
		{
			try
			{
				TOWAPI.integrationRegistry.register(new ForestryHandler(), "Forestry");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("xnet") && ModConfig.enableXNet)
		{
			try
			{
				TOWAPI.integrationRegistry.register(new XNetHandler(), "XNet");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("psi") && ModConfig.enablePSI)
		{
			try
			{
				TOWAPI.integrationRegistry.register(new PSIHandler(), "PSI");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("sonarcore") && ModConfig.enableSonarCore)
		{
			try
			{
				TOWAPI.integrationRegistry.register(new SonarCoreHandler(), "SonarCore");
			}
			catch (Exception e) {}
		}
	}
}