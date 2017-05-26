package com.nhave.tow.client.mesh;

import com.nhave.nhc.shaders.ShaderManager;
import com.nhave.tow.Reference;
import com.nhave.tow.items.ItemOmniwrench;
import com.nhave.tow.items.ItemShader;

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
		//if (ShaderManager.hasShader(stack)) name = ShaderManager.getStringTag(stack, "MODEL_LOC", null);
		
		ItemOmniwrench wrench = ((ItemOmniwrench) stack.getItem());
		if (wrench.getShader(stack) != null) return new ModelResourceLocation(new ResourceLocation(wrench.getShader(stack).getWrenchModel(stack)), "inventory");
		
		return new ModelResourceLocation(new ResourceLocation(name), "inventory");
	}
}