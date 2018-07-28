package com.nhave.tow.common.content;

import com.nhave.tow.client.integration.IEClientEventHandler;
import com.nhave.tow.client.integration.RFToolsClientEventHandler;
import com.nhave.tow.common.helper.DismantleHelper;
import com.nhave.tow.common.integration.IntegrationRegistry;
import com.nhave.tow.common.integration.handler.ActuallyAdditionsHandler;
import com.nhave.tow.common.integration.handler.AdvancedGeneratorsHandler;
import com.nhave.tow.common.integration.handler.CyberwareHandler;
import com.nhave.tow.common.integration.handler.EmbersHandler;
import com.nhave.tow.common.integration.handler.EngineersWorkshopHandler;
import com.nhave.tow.common.integration.handler.ExtremeReactorsHandler;
import com.nhave.tow.common.integration.handler.ForestryHandler;
import com.nhave.tow.common.integration.handler.FunkyLocomotionHandler;
import com.nhave.tow.common.integration.handler.GendustryHandler;
import com.nhave.tow.common.integration.handler.ImmersiveEngineeringHandler;
import com.nhave.tow.common.integration.handler.IndustrialCraftHandler;
import com.nhave.tow.common.integration.handler.IntegratedDynamicsHandler;
import com.nhave.tow.common.integration.handler.MekanismHandler;
import com.nhave.tow.common.integration.handler.ModularMachineryHandler;
import com.nhave.tow.common.integration.handler.OpenComputersHandler;
import com.nhave.tow.common.integration.handler.PSIHandler;
import com.nhave.tow.common.integration.handler.RFToolsHandler;
import com.nhave.tow.common.integration.handler.RefinedStorageHandler;
import com.nhave.tow.common.integration.handler.SonarCoreHandler;
import com.nhave.tow.common.integration.handler.StorageDrawersHandler;
import com.nhave.tow.common.integration.handler.TechRebornHandler;
import com.nhave.tow.common.integration.handler.TeslaCoreLibHandler;
import com.nhave.tow.common.integration.handler.XNetHandler;
import com.nhave.tow.common.integration.ntech.NHCoreIntegration;
import com.nhave.tow.common.integration.ntech.NTechCoreIntegration;
import com.nhave.tow.common.wrenchmode.WrenchMode;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

public class ModIntegration
{
	public static boolean nhcLoaded, ntechcoreLoaded, rpgtooltipsLoaded, enderioLoaded = false;
	
	public static WrenchMode modeRFTools;
	public static WrenchMode modeEmbers;
	public static WrenchMode modeMekanism;
	
	public static void postInit(FMLPostInitializationEvent event)
	{
		nhcLoaded = Loader.isModLoaded("nhc");
		ntechcoreLoaded = Loader.isModLoaded("ntechcore");
		rpgtooltipsLoaded = Loader.isModLoaded("rpgtooltips");
		enderioLoaded = Loader.isModLoaded("enderio");
		
		if (Loader.isModLoaded("nhc") && ModConfig.enableNHCore)
		{
			IntegrationRegistry.register("NHAVE's Core");
			MinecraftForge.EVENT_BUS.register(new NHCoreIntegration());
		}
		if (Loader.isModLoaded("ntechcore"))
		{
			IntegrationRegistry.register("NTech Core");
			MinecraftForge.EVENT_BUS.register(new NTechCoreIntegration());
		}
		if (Loader.isModLoaded("enderio"))
		{
			IntegrationRegistry.register("Ender IO");
		}
		if (ModConfig.customDismantle.length > 0)
		{
			for (int i = 0; i < ModConfig.customDismantle.length; ++i)
			{
				DismantleHelper.addBlock(ModConfig.customDismantle[i]);
			}
		}
		if (ModConfig.vanillaDismantle)
		{
			IntegrationRegistry.register("Vanilla Dismantling");
			DismantleHelper.addBlock("minecraft:white_shulker_box");
			DismantleHelper.addBlock("minecraft:orange_shulker_box");
			DismantleHelper.addBlock("minecraft:magenta_shulker_box");
			DismantleHelper.addBlock("minecraft:light_blue_shulker_box");
			DismantleHelper.addBlock("minecraft:yellow_shulker_box");
			DismantleHelper.addBlock("minecraft:lime_shulker_box");
			DismantleHelper.addBlock("minecraft:pink_shulker_box");
			DismantleHelper.addBlock("minecraft:gray_shulker_box");
			DismantleHelper.addBlock("minecraft:silver_shulker_box");
			DismantleHelper.addBlock("minecraft:cyan_shulker_box");
			DismantleHelper.addBlock("minecraft:purple_shulker_box");
			DismantleHelper.addBlock("minecraft:blue_shulker_box");
			DismantleHelper.addBlock("minecraft:brown_shulker_box");
			DismantleHelper.addBlock("minecraft:green_shulker_box");
			DismantleHelper.addBlock("minecraft:red_shulker_box");
			DismantleHelper.addBlock("minecraft:black_shulker_box");
			DismantleHelper.addBlock("minecraft:hopper");
			DismantleHelper.addBlock("minecraft:ender_chest/p");	    
		}
		if (Loader.isModLoaded("opencomputers") && ModConfig.enableOpenComputers)
		{
			try
			{
				IntegrationRegistry.register(new OpenComputersHandler(), "Open Computers");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("refinedstorage") && ModConfig.enableRefinedStorage)
		{
			try
			{
				IntegrationRegistry.register(new RefinedStorageHandler(), "Refined Storage");
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
				IntegrationRegistry.register(new IndustrialCraftHandler(), "Industrial Craft 2" + " (" + (ModConfig.ic2ClassicRotation ? "Classic" : "Unity") + (ModConfig.ic2CutWires ? "/Wirecutting" : "") + ")");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("bigreactors") && ModConfig.enableExtremeReactors)
		{
			try
			{
				IntegrationRegistry.register(new ExtremeReactorsHandler(), "Extreme Reactors");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("integrateddynamics") && ModConfig.enableIntegratedDynamics)
		{
			try
			{
				IntegrationRegistry.register(new IntegratedDynamicsHandler(), "Integrated Dynamics");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("actuallyadditions") && ModConfig.enableActuallyAdditions)
		{
			try
			{
				IntegrationRegistry.register(new ActuallyAdditionsHandler(), "Actually Additions");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("techreborn") && ModConfig.enableTechReborn)
		{
			try
			{
				IntegrationRegistry.register(new TechRebornHandler(), "Tech Reborn" + " (" + (ModConfig.trRotation ? "Classic" : "Unity") + (ModConfig.trCutWires ? "/Wirecutting" : "") + ")");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("storagedrawers") && ModConfig.enableStorageDrawers)
		{
			try
			{
				IntegrationRegistry.register(new StorageDrawersHandler(), "Storage Drawers");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("rftools") && ModConfig.enableRFTools)
		{
			try
			{
				IntegrationRegistry.register(new RFToolsHandler(), "RFTools");
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
				IntegrationRegistry.register(new TeslaCoreLibHandler(), "Tesla Core Lib");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("embers") && ModConfig.enableEmbers)
		{
			try
			{
				IntegrationRegistry.register(new EmbersHandler(), "Embers" + (ModConfig.allowEmbersDismantle ? " (Dismantling)" : ""));
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("immersiveengineering") && ModConfig.enableImmersiveEngineering)
		{
			try
			{
				IntegrationRegistry.register(new ImmersiveEngineeringHandler(), "Immersive Engineering" + (ModConfig.ieUnity ? " (Unity)" : ""));
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
				IntegrationRegistry.register(new ForestryHandler(), "Forestry");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("xnet") && ModConfig.enableXNet)
		{
			try
			{
				IntegrationRegistry.register(new XNetHandler(), "XNet");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("psi") && ModConfig.enablePSI)
		{
			try
			{
				IntegrationRegistry.register(new PSIHandler(), "PSI");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("modularmachinery") && ModConfig.enableModularMachinery)
		{
			try
			{
				IntegrationRegistry.register(new ModularMachineryHandler(), "Modular Machinery");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("funkylocomotion") && ModConfig.enableFunkyLocomotion)
		{
			try
			{
				IntegrationRegistry.register(new FunkyLocomotionHandler(), "Funky Locomotion");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("sonarcore") && ModConfig.enableSonarCore)
		{
			try
			{
				IntegrationRegistry.register(new SonarCoreHandler(), "SonarCore");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("engineersworkshop") && ModConfig.enableEngineersWorkshop)
		{
			try
			{
				IntegrationRegistry.register(new EngineersWorkshopHandler(), "Engineers Workshop");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("cyberware") && ModConfig.enableCyberware)
		{
			try
			{
				IntegrationRegistry.register(new CyberwareHandler(), "Cyberware");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("mekanism") && ModConfig.enableMekanism)
		{
			try
			{
				IntegrationRegistry.register(new MekanismHandler(), "Mekanism");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("advgenerators") && ModConfig.enableAdvancedGenerators)
		{
			try
			{
				IntegrationRegistry.register(new AdvancedGeneratorsHandler(), "Advanced Generators");
			}
			catch (Exception e) {}
		}
		if (Loader.isModLoaded("gendustry") && ModConfig.enableAdvancedGenerators)
		{
			try
			{
				IntegrationRegistry.register(new GendustryHandler(), "Gendustry");
			}
			catch (Exception e) {}
		}
	}
}