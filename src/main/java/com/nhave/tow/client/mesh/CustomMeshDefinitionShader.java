package com.nhave.tow.client.mesh;

import com.nhave.tow.Reference;
import com.nhave.tow.items.ItemShader;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CustomMeshDefinitionShader implements ItemMeshDefinition
{
	@Override
	public ModelResourceLocation getModelLocation(ItemStack stack)
	{
		String name = Reference.MODID + ":" + stack.getItem().getRegistryName().getResourcePath();
		ItemShader shader = ((ItemShader) stack.getItem());
		if (shader.getShader(stack) != null) return new ModelResourceLocation(new ResourceLocation(shader.getShader(stack).getShaderModel()), "inventory");
		return new ModelResourceLocation(new ResourceLocation(name), "inventory");
	}
}