package com.nhave.tow.client.render;

import com.nhave.nhc.shaders.ShaderManager;
import com.nhave.tow.items.ItemOmniwrench;
import com.nhave.tow.items.ItemShader;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class ItemColorHandler implements IItemColor
{
	public static final ItemColorHandler INSTANCE = new ItemColorHandler();
	
	@Override
	public int colorMultiplier(ItemStack stack, int tintIndex)
	{
		if (stack.getItem() instanceof ItemOmniwrench)
		{
			ItemOmniwrench wrench = (ItemOmniwrench) stack.getItem();
			return tintIndex == 1 && wrench.supportsChroma(stack) ? ShaderManager.getChroma(stack).getColor() : 16777215;
		}
		else if (stack.getItem() instanceof ItemShader)
		{
			ItemShader shader = (ItemShader) stack.getItem();
			return (shader.getShader(stack) != null && tintIndex == 1) ? shader.getShader(stack).getShaderColor() : 16777215;
		}
		else return 16777215;
	}
}