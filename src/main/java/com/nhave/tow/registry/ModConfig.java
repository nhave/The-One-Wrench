package com.nhave.tow.registry;

import java.io.File;

import com.nhave.tow.Reference;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ModConfig
{
	public static boolean isClientConfig;
	
	public static Configuration config;
	
	//Main
    public static String[] customDismantle = Defaults.customDismantle;
    public static boolean enableAllShaders = Defaults.enableAllShaders;
    public static boolean enableDestinyShaders = Defaults.enableDestinyShaders;
    public static boolean enableOverwatchShaders = Defaults.enableOverwatchShaders;
	//Integrations
    public static boolean vanillaDismantle = Defaults.vanillaDismantle;
    public static boolean enableOpenComputers = Defaults.enableOpenComputers;
    public static boolean enableRefinedStorage = Defaults.enableRefinedStorage;
    public static boolean enableExtremeReactors = Defaults.enableExtremeReactors;
    public static boolean enableIntegratedDynamics = Defaults.enableIntegratedDynamics;
    public static boolean enableActuallyAdditions = Defaults.enableActuallyAdditions;
    public static boolean enableStorageDrawers = Defaults.enableStorageDrawers;
    public static boolean enableRFTools = Defaults.enableRFTools;
    public static boolean enableTeslaCoreLib = Defaults.enableTeslaCoreLib;
    public static boolean enableImmersiveEngineering = Defaults.enableImmersiveEngineering;
    public static boolean enableXNet = Defaults.enableXNet;
    public static boolean enablePSI = Defaults.enablePSI;
    //Integration - IC2
    public static boolean enableIC2 = Defaults.enableIC2;
    public static boolean ic2CutWires = Defaults.ic2CutWires;
    public static boolean ic2ClassicRotation = Defaults.ic2ClassicRotation;
    //Integration - Tech Reborn
    public static boolean enableTechReborn = Defaults.enableTechReborn;
    public static boolean trCutWires = Defaults.trCutWires;
    public static boolean trRotation = Defaults.trRotation;
    //Integration - Embers
    public static boolean enableEmbers = Defaults.enableEmbers;
    public static boolean allowEmbersDismantle = Defaults.allowEmbersDismantle;
    //Integration - Forestry
    public static boolean enableForestry = Defaults.enableForestry;
    public static boolean frFarmBlocks = Defaults.frFarmBlocks;
    public static boolean frGreenhouseBlocks = Defaults.frGreenhouseBlocks;
    public static boolean frAlvearyBlocks = Defaults.frAlvearyBlocks;
    public static boolean frApiaryBlocks = Defaults.frApiaryBlocks;
    public static boolean frBeeHouseBlocks = Defaults.frBeeHouseBlocks;
    public static boolean frMachineBlocks = Defaults.frMachineBlocks;
    public static boolean frEngineBlocks = Defaults.frEngineBlocks;
    public static boolean frMailBlocks = Defaults.frMailBlocks;
	
	public ModConfig(boolean isClient)
	{
		this.isClientConfig = isClient;
	}

	public static void init(File configFile)
	{
		config = new Configuration(configFile);
		loadConfig(false);
	}
	
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs)
	{
		if(eventArgs.getModID().equalsIgnoreCase(Reference.MODID))
		{
			loadConfig(false);
		}
	}
	
	public static void loadConfig(boolean load)
	{
		loadCommonConfig();
		if (isClientConfig) loadClientConfig();
		
		if (!config.hasChanged()) return;
		config.save();
	}
	
	public static void loadCommonConfig()
	{
		config.setCategoryComment("common", "Configuration for all Common configs");
		config.setCategoryComment("common.shaders", "Enable/Disable Shaders");
		customDismantle = config.get("common", "CustomDismantle", Defaults.customDismantle, "Allow Dismatling Custom Blocks").setRequiresMcRestart(true).getStringList();
		enableAllShaders = config.get("common.shaders", "EnableAllShaders", Defaults.enableAllShaders, "Enable All Shaders").setRequiresMcRestart(true).getBoolean(Defaults.enableAllShaders);
		enableDestinyShaders = config.get("common.shaders", "EnableDestinyShaders", Defaults.enableDestinyShaders, "Enable Destiny Shaders").setRequiresMcRestart(true).getBoolean(Defaults.enableDestinyShaders);
		enableOverwatchShaders = config.get("common.shaders", "EnableOverwatchShaders", Defaults.enableOverwatchShaders, "Enable Overwatch Shaders").setRequiresMcRestart(true).getBoolean(Defaults.enableOverwatchShaders);

		//Integrations
		config.setCategoryComment("integration", "Configuration for all Integration configs");
		vanillaDismantle = config.get("integration", "VanillaDismantle", Defaults.vanillaDismantle, "Enable Vanilla Dismantling").setRequiresMcRestart(true).getBoolean(Defaults.vanillaDismantle);
		enableOpenComputers = config.get("integration", "EnableOpenComputers", Defaults.enableOpenComputers, "Enable Open Computers Integration").setRequiresMcRestart(true).getBoolean(Defaults.enableOpenComputers);
		enableRefinedStorage = config.get("integration", "EnableRefinedStorage", Defaults.enableRefinedStorage, "Enable Refined Storage Integration").setRequiresMcRestart(true).getBoolean(Defaults.enableRefinedStorage);
		enableIC2 = config.get("integration", "EnableIC2", Defaults.enableIC2, "Enable Industrial Craft 2 Integration").setRequiresMcRestart(true).getBoolean(Defaults.enableIC2);
		enableExtremeReactors = config.get("integration", "EnableExtremeReactors", Defaults.enableExtremeReactors, "Enable Extreme Reactors Integration").setRequiresMcRestart(true).getBoolean(Defaults.enableExtremeReactors);
		enableIntegratedDynamics = config.get("integration", "EnableIntegratedDynamics", Defaults.enableIntegratedDynamics, "Enable Integrated Dynamics Integration").setRequiresMcRestart(true).getBoolean(Defaults.enableIntegratedDynamics);
		enableActuallyAdditions = config.get("integration", "EnableActuallyAdditions", Defaults.enableActuallyAdditions, "Enable Actually Additions Integration").setRequiresMcRestart(true).getBoolean(Defaults.enableActuallyAdditions);
		enableTechReborn = config.get("integration", "EnableTechReborn", Defaults.enableTechReborn, "Enable Tech Reborn Integration").setRequiresMcRestart(true).getBoolean(Defaults.enableTechReborn);
		enableStorageDrawers = config.get("integration", "EnableStorageDrawers", Defaults.enableStorageDrawers, "Enable Storage Drawers Integration").setRequiresMcRestart(true).getBoolean(Defaults.enableStorageDrawers);
		enableRFTools = config.get("integration", "EnableRFTools", Defaults.enableRFTools, "Enable RFTools Integration").setRequiresMcRestart(true).getBoolean(Defaults.enableRFTools);
		enableTeslaCoreLib = config.get("integration", "EnableTeslaCoreLib", Defaults.enableTeslaCoreLib, "Enable Tesla Core Lib Integration").setRequiresMcRestart(true).getBoolean(Defaults.enableTeslaCoreLib);
		enableEmbers = config.get("integration", "EnableEmbers", Defaults.enableEmbers, "Enable Embers Integration").setRequiresMcRestart(true).getBoolean(Defaults.enableEmbers);
		enableImmersiveEngineering = config.get("integration", "EnableImmersiveEngineering", Defaults.enableImmersiveEngineering, "Enable Immersive Engineering Integration").setRequiresMcRestart(true).getBoolean(Defaults.enableImmersiveEngineering);
		enableForestry = config.get("integration", "EnableForestry", Defaults.enableForestry, "Enable Forestry Integration").setRequiresMcRestart(true).getBoolean(Defaults.enableForestry);
		enableXNet = config.get("integration", "EnableXNet", Defaults.enableXNet, "Enable XNet Integration").setRequiresMcRestart(true).getBoolean(Defaults.enableXNet);
		enablePSI = config.get("integration", "EnablePSI", Defaults.enablePSI, "Enable PSI Integration").setRequiresMcRestart(true).getBoolean(Defaults.enablePSI);
	    //Integration - IC2
		config.setCategoryComment("integration.industrialcraft", "Configuration for IC2 Integrations");
		ic2CutWires = config.get("integration.industrialcraft", "CutWires", Defaults.ic2CutWires, "Allow dismantling of wires").setRequiresMcRestart(true).getBoolean(Defaults.ic2CutWires);
		ic2ClassicRotation = config.get("integration.industrialcraft", "ClassicMode", Defaults.ic2ClassicRotation, "Enable classic IC2 Wrench behavior").setRequiresMcRestart(true).getBoolean(Defaults.ic2ClassicRotation);
	    //Integration - Tech Reborn
		config.setCategoryComment("integration.techreborn", "Configuration for Tech Reborn Integrations");
		trCutWires = config.get("integration.techreborn", "CutWires", Defaults.trCutWires, "Allow dismantling of wires").setRequiresMcRestart(true).getBoolean(Defaults.trCutWires);
		trRotation = config.get("integration.techreborn", "ClassicMode", Defaults.trRotation, "Enable IC2 styled Wrench behavior").setRequiresMcRestart(true).getBoolean(Defaults.trRotation);
	    //Integration - Embers
		config.setCategoryComment("integration.embers", "Configuration for Embers Integrations");
		allowEmbersDismantle = config.get("integration.embers", "AllowEmbersDismantling", Defaults.allowEmbersDismantle, "Allow dismantling of Embers machines").setRequiresMcRestart(true).getBoolean(Defaults.allowEmbersDismantle);
	    //Integration - Forestry
		config.setCategoryComment("integration.forestry", "Configuration for Forestry Integrations");
		frFarmBlocks = config.get("integration.forestry", "DismantleFarmBlocks", Defaults.frFarmBlocks, "Allow dismantling of Forestry Farm Blocks").setRequiresMcRestart(true).getBoolean(Defaults.frFarmBlocks);
		frGreenhouseBlocks = config.get("integration.forestry", "DismantleGreenhouseBlocks", Defaults.frGreenhouseBlocks, "Allow dismantling of Forestry Greenhouse Blocks").setRequiresMcRestart(true).getBoolean(Defaults.frGreenhouseBlocks);
		frAlvearyBlocks = config.get("integration.forestry", "DismantleAlvearyBlocks", Defaults.frAlvearyBlocks, "Allow dismantling of Forestry Alveary Blocks").setRequiresMcRestart(true).getBoolean(Defaults.frAlvearyBlocks);
		frApiaryBlocks = config.get("integration.forestry", "DismantleApiaryBlocks", Defaults.frApiaryBlocks, "Allow dismantling of Forestry Apiary Blocks").setRequiresMcRestart(true).getBoolean(Defaults.frApiaryBlocks);
		frBeeHouseBlocks = config.get("integration.forestry", "DismantleBeeHouseBlocks", Defaults.frBeeHouseBlocks, "Allow dismantling of Forestry Bee House Blocks").setRequiresMcRestart(true).getBoolean(Defaults.frBeeHouseBlocks);
		frMachineBlocks = config.get("integration.forestry", "DismantleMachineBlocks", Defaults.frMachineBlocks, "Allow dismantling of Forestry Machine Blocks").setRequiresMcRestart(true).getBoolean(Defaults.frMachineBlocks);
		frEngineBlocks = config.get("integration.forestry", "DismantleEngineBlocks", Defaults.frEngineBlocks, "Allow dismantling of Forestry Engine Blocks").setRequiresMcRestart(true).getBoolean(Defaults.frEngineBlocks);
		frMailBlocks = config.get("integration.forestry", "DismantleMailBlocks", Defaults.frMailBlocks, "Allow dismantling of Forestry Mail Blocks").setRequiresMcRestart(true).getBoolean(Defaults.frMailBlocks);
	}
	
	public static void loadClientConfig() {}
}