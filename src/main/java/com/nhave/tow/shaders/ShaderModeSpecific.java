package com.nhave.tow.shaders;

import com.nhave.tow.api.shaders.Shader;
import com.nhave.tow.items.ItemOmniwrench;
import com.nhave.tow.registry.ModItems;

import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ShaderModeSpecific extends Shader
{
	private static final String[] SUFFIXES = new String[] {"wrench", "rotate", "tune", "util", "none"};
	
	public ShaderModeSpecific(String shaderName, String wrenchModel, String shaderModel, int color, boolean chromaSupport)
	{
		super(shaderName, wrenchModel, shaderModel, color, chromaSupport);
	}
	
	@Override
	public String getWrenchModel(ItemStack stack)
	{
		String suffix = "wrench";
		ItemOmniwrench wrench = (ItemOmniwrench) stack.getItem();
		if (wrench.getWrenchMode(stack) == ModItems.modeRotate) suffix = "rotate";
		else if (wrench.getWrenchMode(stack) == ModItems.modeTune) suffix = "tune";
		else if (wrench.getWrenchMode(stack) == ModItems.modeUtil) suffix = "util";
		else if (wrench.getWrenchMode(stack) == ModItems.modeNone) suffix = "none";
		return super.getWrenchModel(stack) + "_" + suffix;
	}
	
	@Override
	public void registerModels(Item item)
	{
		for (int i = 0; i < SUFFIXES.length; ++i)
		{
			ModelBakery.registerItemVariants(item, new ResourceLocation(this.wrenchModel + "_" + SUFFIXES[i]));
		}
	}
}