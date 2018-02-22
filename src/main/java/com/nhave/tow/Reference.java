package com.nhave.tow;

public class Reference
{
    public static final String NHCORE_VERSION = "4.0.3";
    public static final String LOAD_BEFORE = ";before:mekanism";
    
	public static final String MODID = "tow";
    public static final String NAME = "The One Wrench";
    public static final String VERSION = "2.1.1";
    public static final String DEPENDENCIES = 
    		"required-after:nhc@[" + NHCORE_VERSION + ",)" + LOAD_BEFORE;
    
    public static final String GUIFACTORY = "com.nhave.tow.client.gui.ModGuiFactory";
    public static final String CLIENT_PROXY = "com.nhave.tow.proxy.ClientProxy";
    public static final String COMMON_PROXY = "com.nhave.tow.proxy.CommonProxy";
    
    public static final String MOD_DESCRIPTION = "The Wrench to rule em all.";
}