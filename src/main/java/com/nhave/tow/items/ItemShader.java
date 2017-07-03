package com.nhave.tow.items;

import java.util.List;
import java.util.Map.Entry;

import com.nhave.nhc.api.items.IItemQuality;
import com.nhave.nhc.helpers.ItemNBTHelper;
import com.nhave.nhc.helpers.TooltipHelper;
import com.nhave.nhc.util.StringUtils;
import com.nhave.tow.api.shaders.Shader;
import com.nhave.tow.registry.ModItems;
import com.nhave.tow.shaders.ShaderRegistry;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemShader extends ItemBase implements IItemQuality
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
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean flag)
	{
		if (getShader(stack) == null)
		{
			list.add(StringUtils.RED + StringUtils.localize("tooltip.nhc.error.missingnbt"));
			return;
		}
		TooltipHelper.addHiddenTooltip(list, "tooltip.tow.shader." + ItemNBTHelper.getString(stack, "SHADERS", "SHADER"), ";");
		if (StringUtils.isShiftKeyDown())
		{
			list.add(StringUtils.localize("tooltip.tow.shader.artist") + ": " + StringUtils.format(getShader(stack).getArtist(), StringUtils.YELLOW, StringUtils.ITALIC));
			list.add(StringUtils.localize("tooltip.tow.shader.appliesto") + ":");
			list.add("  " + StringUtils.format(StringUtils.localize("item.tow.wrench.name"), StringUtils.YELLOW, StringUtils.ITALIC));
		}
		else list.add(StringUtils.shiftForInfo);
	}
	
	public Shader getShader(ItemStack stack)
	{
		String name = ItemNBTHelper.getString(stack, "SHADERS", "SHADER");
		if (name != null && ShaderRegistry.getShader(name) != null) return ShaderRegistry.getShader(name);
		return null;
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> list)
	{
		if (!ShaderRegistry.isEmpty())
		{
			for(Entry<String, Shader> entry : ShaderRegistry.SHADERS.entrySet())
			{
				String key = entry.getKey();
				list.add(ItemNBTHelper.setString(new ItemStack(item), "SHADERS", "SHADER", key));
			}
		}
		else list.add(new ItemStack(item));
	}
	
	@Override
	public String getQualityColor(ItemStack stack)
	{
		if (getShader(stack) != null) return getShader(stack).getQualityColor();
		return "";
	}
}