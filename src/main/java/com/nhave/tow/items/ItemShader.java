package com.nhave.tow.items;

import java.util.List;
import java.util.Map.Entry;

import com.nhave.nhc.helpers.ItemNBTHelper;
import com.nhave.nhc.helpers.TooltipHelper;
import com.nhave.nhc.util.StringUtils;
import com.nhave.tow.api.shaders.Shader;
import com.nhave.tow.registry.ModItems;
import com.nhave.tow.shaders.ShaderRegistry;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ItemShader extends ItemBase
{
	public ItemShader(String name)
	{
		super(name);
		this.setHasSubtypes(true);
		this.setCreativeTab(ModItems.TAB_SHADERS);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		if (getShader(stack) != null) return StringUtils.localize("shader.tow." + ItemNBTHelper.getString(stack, "SHADERS", "SHADER"));
		else return super.getItemStackDisplayName(stack);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if (getShader(stack) == null)
		{
			tooltip.add(StringUtils.RED + StringUtils.localize("tooltip.nhc.error.missingnbt"));
			return;
		}
		if (StringUtils.isShiftKeyDown())
		{
			tooltip.add(StringUtils.format(StringUtils.localize("tooltip.tow.shader"), StringUtils.GREEN, StringUtils.ITALIC));
			
			TooltipHelper.addHiddenTooltip(tooltip, "tooltip.tow.shader." + ItemNBTHelper.getString(stack, "SHADERS", "SHADER"), ";", StringUtils.GRAY);
			
			if (getShader(stack).getSupportsChroma()) tooltip.add(StringUtils.format(StringUtils.localize("tooltip.tow.chroma.enabled"), StringUtils.YELLOW, StringUtils.ITALIC));
			tooltip.add(StringUtils.localize("tooltip.tow.shader.artist") + ": " + StringUtils.format(getShader(stack).getArtist(), StringUtils.YELLOW, StringUtils.ITALIC));
			tooltip.add(StringUtils.localize("tooltip.tow.shader.appliesto") + ":");
			tooltip.add("  " + StringUtils.format(StringUtils.localize("item.tow.wrench.name"), StringUtils.YELLOW, StringUtils.ITALIC));
		}
		else tooltip.add(StringUtils.shiftForInfo);
	}
	
	public Shader getShader(ItemStack stack)
	{
		String name = ItemNBTHelper.getString(stack, "SHADERS", "SHADER");
		if (name != null && ShaderRegistry.getShader(name) != null) return ShaderRegistry.getShader(name);
		return null;
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if (tab != ModItems.TAB_SHADERS) return;
		if (!ShaderRegistry.isEmpty())
		{
			for(Entry<String, Shader> entry : ShaderRegistry.SHADERS.entrySet())
			{
				if (!entry.getValue().isHidden())
				{
					String key = entry.getKey();
					items.add(ItemNBTHelper.setString(new ItemStack(this), "SHADERS", "SHADER", key));
				}
			}
		}
		else items.add(new ItemStack(this));
	}
	
	@Override
	public String getQualityColor(ItemStack stack)
	{
		if (getShader(stack) != null) return getShader(stack).getQualityColor();
		return "";
	}
}