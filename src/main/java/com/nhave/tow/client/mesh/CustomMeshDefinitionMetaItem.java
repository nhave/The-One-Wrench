package com.nhave.tow.client.mesh;

import com.nhave.tow.Reference;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CustomMeshDefinitionMetaItem implements ItemMeshDefinition
{
private boolean useSingleTexture;
	
	public CustomMeshDefinitionMetaItem(boolean useSingleTexture)
	{
		this.useSingleTexture = useSingleTexture;
	}
	
	@Override
	public ModelResourceLocation getModelLocation(ItemStack stack)
	{
		if (this.useSingleTexture) return new ModelResourceLocation(new ResourceLocation(Reference.MODID + ":" + stack.getItem().getRegistryName().getResourcePath()), "inventory");
		else
		{
			String meta = "";
			if (stack.getItemDamage() > 0) meta = "_" + stack.getItemDamage();
			return new ModelResourceLocation(new ResourceLocation(Reference.MODID + ":" + stack.getItem().getRegistryName().getResourcePath() + meta), "inventory");
		}
	}
}
