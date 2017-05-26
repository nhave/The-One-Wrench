package com.nhave.tow.shaders;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class Shader
{
	protected String shaderName;
	protected String wrenchModel;
	protected String shaderModel;
	protected String itemQulity = "";
	private String shaderArtist = "nhave";
	protected int color;
	protected boolean chromaSupport;
	
	public Shader(String shaderName, String wrenchModel, String shaderModel, int color, boolean chromaSupport)
	{
		this.shaderName = shaderName;
		this.wrenchModel = wrenchModel;
		this.shaderModel = shaderModel;
		this.color = color;
		this.chromaSupport = chromaSupport;
	}
	
	public Shader(String shaderName, String wrenchModel, String shaderModel)
	{
		this(shaderName, wrenchModel, shaderModel, 16777215, false);
	}
	
	public Shader(String shaderName, String wrenchModel, String shaderModel, int color)
	{
		this(shaderName, wrenchModel, shaderModel, color, false);
	}
	
	public Shader(String shaderName, String wrenchModel, String shaderModel, boolean chromaSupport)
	{
		this(shaderName, wrenchModel, shaderModel, 16777215, chromaSupport);
	}
	
	public String getShaderName()
	{
		return this.shaderName;
	}
	
	public String getWrenchModel(ItemStack stack)
	{
		return this.wrenchModel;
	}
	
	public String getShaderModel()
	{
		return this.shaderModel;
	}
	
	public boolean getSupportsChroma()
	{
		return this.chromaSupport;
	}
	
	public int getShaderColor()
	{
		return this.color;
	}
	
	public String getQualityColor()
	{
		return this.itemQulity;
	}
	
	public Shader setQualityColor(String color)
	{
		this.itemQulity = color;
		return this;
	}
	
	public String getArtist()
	{
		return this.shaderArtist;
	}
	
	public Shader setArtist(String artist)
	{
		this.shaderArtist = artist;
		return this;
	}
	
	public void registerModels(Item item)
	{
		ModelBakery.registerItemVariants(item, new ResourceLocation(this.wrenchModel));
	}
}