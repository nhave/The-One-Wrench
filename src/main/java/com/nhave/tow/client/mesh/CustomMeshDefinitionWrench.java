package com.nhave.tow.client.mesh;

import com.nhave.tow.Reference;
import com.nhave.tow.items.ItemOmniwrench;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CustomMeshDefinitionWrench implements ItemMeshDefinition
{
	@Override
	public ModelResourceLocation getModelLocation(ItemStack stack)
	{
		String name = Reference.MODID + ":" + "wrench";
		ItemOmniwrench wrench = ((ItemOmniwrench) stack.getItem());
		if (wrench.hasToken(stack)) return new ModelResourceLocation(new ResourceLocation(Reference.MODID + ":" + "shaders/main/support"), "inventory");
		else if (wrench.hasShader(stack)) return new ModelResourceLocation(new ResourceLocation(wrench.getShader(stack).getWrenchModel(stack)), "inventory");
		
		return new ModelResourceLocation(new ResourceLocation(name), "inventory");
	}
}