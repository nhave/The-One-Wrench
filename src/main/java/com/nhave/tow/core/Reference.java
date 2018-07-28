package com.nhave.tow.core;

public class Reference
{
    public static final String LIBRARY_VERSION = "1.0.0";
    public static final String LOAD_BEFORE = ";before:mekanism";
    
	public static final String MODID = "tow";
    public static final String NAME = "The One Wrench";
    public static final String VERSION = "2.5.0";
    public static final String DEPENDENCIES = "required-after:nhlib@[" + LIBRARY_VERSION + ",)" + LOAD_BEFORE;
    
    public static final String GUIFACTORY = "com.nhave.tow.client.gui.ModGuiFactory";
    public static final String CLIENT_PROXY = "com.nhave.tow.core.proxy.ClientProxy";
    public static final String COMMON_PROXY = "com.nhave.tow.core.proxy.CommonProxy";
    
    public static final String MOD_DESCRIPTION = "The Wrench to rule em all.";
}