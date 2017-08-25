package com.nhave.tow;

import java.io.File;

import org.apache.logging.log4j.Logger;

import com.nhave.tow.api.TOWAPI;
import com.nhave.tow.integration.WrenchRegistry;
import com.nhave.tow.proxy.CommonProxy;
import com.nhave.tow.registry.ModIntegration;
import com.nhave.tow.registry.ModShaders;
import com.nhave.tow.registry.RegistryHandler;
import com.nhave.tow.shaders.ShaderRegistry;
import com.nhave.tow.wrenchmodes.ModeRegistry;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, dependencies = Reference.DEPENDENCIES, guiFactory = Reference.GUIFACTORY)
public class TheOneWrench
{
    public static Logger logger;
    
	@SidedProxy(clientSide = Reference.CLIENT_PROXY, serverSide = Reference.COMMON_PROXY)
	public static CommonProxy proxy;
    
    @Mod.Instance(Reference.MODID)
	public static TheOneWrench instance = new TheOneWrench();
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	TOWAPI.integrationRegistry = new WrenchRegistry();
    	TOWAPI.modeRegistry = new ModeRegistry();
    	TOWAPI.shaderRegistry = new ShaderRegistry();
    	
    	logger = event.getModLog();
		proxy.setupConfig(new File(event.getModConfigurationDirectory(), "theonewrench.cfg"));
		
		ModShaders.init();
    	//ModItems.init();
    	//ModItems.register();
		
		MinecraftForge.EVENT_BUS.register(new RegistryHandler());
    	
    	WrenchRegistry.updateModMeta();
    	
    	proxy.preInit(event);
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	proxy.registerRenders();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	proxy.registerEventHandlers();
    	ModIntegration.postInit(event);
    }
}