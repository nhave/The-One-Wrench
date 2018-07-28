package com.nhave.tow.common.itemshader;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class Shader
{
	protected String shaderName;
	protected String wrenchModel;
	protected String shaderModel;
	private String shaderArtist = "nhave";
	protected int itemQuality = 0;
	protected int color;
	protected boolean chromaSupport;
	protected boolean hidden = false;
	
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
	
	/**
	 * @return Returns the unlocalized name for the Shader.
	 */
	public String getShaderName()
	{
		return this.shaderName;
	}
	
	/**
	 * Gets the Item Model for the OmniWrench.
	 * 
	 * @param stack The OmniWrench {@link ItemStack} for advanced Shaders to use.
	 * @return Return the model location for the OmniWrench.
	 */
	public String getWrenchModel(ItemStack stack)
	{
		return this.wrenchModel;
	}
	
	/**
	 * @return Returns the model location for the Shader itself.
	 */
	public String getShaderModel()
	{
		return this.shaderModel;
	}
	
	/**
	 * @return Returns true if the Shader supports Chroma.
	 */
	public boolean getSupportsChroma()
	{
		return this.chromaSupport;
	}
	
	/**
	 * @return Returns the color for the actual Shader.
	 */
	public int getShaderColor()
	{
		return this.color;
	}
	
	/**
	 * @return Returns the Quality ID for the Shader.
	 */
	public int getQuality()
	{
		return this.itemQuality;
	}
	
	/**
	 * Sets a Quality ID for the Shader.
	 * 
	 * @param id
	 */
	public Shader setQuality(int id)
	{
		this.itemQuality = id;
		return this;
	}
	
	/**
	 * @return Returns the Artist of the Shader.
	 */
	public String getArtist()
	{
		return this.shaderArtist;
	}
	
	/**
	 * Sets the Shader to be hidden.
	 * 
	 * @param artist
	 */
	public Shader setHidden()
	{
		this.hidden = true;
		return this;
	}
	
	/**
	 * @return Returns true if the Shader should be hidden.
	 */
	public boolean isHidden()
	{
		return this.hidden;
	}
	
	/**
	 * Sets the artist of the Shader.
	 * 
	 * @param artist
	 */
	public Shader setArtist(String artist)
	{
		this.shaderArtist = artist;
		return this;
	}
	
	/**
	 * Registers the models for the Shader.
	 * 
	 * @param item
	 */
	public void registerModels(Item item)
	{
		ModelBakery.registerItemVariants(item, new ResourceLocation(this.wrenchModel));
	}
}