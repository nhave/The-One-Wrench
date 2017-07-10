package com.nhave.tow;

public class Reference
{
    public static final String FORGE_VERSION = "13.20.0.2253";
    public static final String NHCORE_VERSION = "3.1.0";
    
	public static final String MODID = "tow";
    public static final String NAME = "The One Wrench";
    public static final String VERSION = "1.1.1";
    public static final String MCVERSIONS = "[1.11.2,)";
    public static final String DEPENDENCIES = 
    		"required-after:forge@[" + FORGE_VERSION + ",);"
			+ "required-after:nhc@[" + NHCORE_VERSION + ",)";
    
    public static final String GUIFACTORY = "com.nhave.tow.client.gui.ModGuiFactory";
    public static final String CLIENT_PROXY = "com.nhave.tow.proxy.ClientProxy";
    public static final String COMMON_PROXY = "com.nhave.tow.proxy.CommonProxy";
    
    public static final String MOD_DESCRIPTION = "The Wrench to rule em all.";
}