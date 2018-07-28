package com.nhave.tow.common.item;

import java.util.List;
import java.util.Map.Entry;

import com.nhave.lib.library.client.render.IModelRegister;
import com.nhave.lib.library.helper.ItemNBTHelper;
import com.nhave.lib.library.helper.TooltipHelper;
import com.nhave.lib.library.item.IColoredItem;
import com.nhave.lib.library.util.StringUtils;
import com.nhave.tow.client.mesh.CustomMeshDefinitionShader;
import com.nhave.tow.common.content.ModIntegration;
import com.nhave.tow.common.content.ModItems;
import com.nhave.tow.common.itemshader.Shader;
import com.nhave.tow.common.itemshader.ShaderRegistry;
import com.nhave.tow.core.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemShader extends ItemBase implements IModelRegister, IColoredItem
{
	private String[] rarityNames = new String[] {"common", "rare", "epic", "legendary"};
	private String[] rarityColors = new String[] {StringUtils.WHITE, StringUtils.LIGHT_BLUE, StringUtils.PURPLE, StringUtils.ORANGE};
	
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
			tooltip.add(StringUtils.RED + StringUtils.localize("tooltip.tow.error.missingnbt"));
			return;
		}
		
		if (StringUtils.isShiftKeyDown())
		{
			if (!ModIntegration.rpgtooltipsLoaded) tooltip.add(StringUtils.format(StringUtils.localize("tooltip.tow.quality." + rarityNames[getShader(stack).getQuality()]), rarityColors[getShader(stack).getQuality()], StringUtils.BOLD));
			tooltip.add(StringUtils.format(StringUtils.localize("tooltip.tow.shader"), StringUtils.GREEN, StringUtils.ITALIC));
			
			TooltipHelper.addHiddenTooltip(tooltip, "tooltip.tow.shader." + ItemNBTHelper.getString(stack, "SHADERS", "SHADER"), ";", StringUtils.GRAY);
			
			if (getShader(stack).getSupportsChroma()) tooltip.add(StringUtils.format(StringUtils.localize("tooltip.tow.chroma.enabled"), StringUtils.YELLOW, StringUtils.ITALIC));
			tooltip.add(StringUtils.localize("tooltip.tow.shader.artist") + ": " + StringUtils.format(getShader(stack).getArtist(), StringUtils.YELLOW, StringUtils.ITALIC));
			tooltip.add(StringUtils.localize("tooltip.tow.shader.appliesto") + ":");
			tooltip.add("  " + StringUtils.format(StringUtils.localize("item.tow.wrench.name"), StringUtils.YELLOW, StringUtils.ITALIC));
		}
		else tooltip.add(StringUtils.shiftForInfo());
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
		if (this.isInCreativeTab(tab))
		{
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
	}
	
	@Override
	public int getQuality(ItemStack stack)
	{
		if (getShader(stack) != null) return getShader(stack).getQuality();
		return 0;
	}
	
	@Override
	public int getItemColor(ItemStack stack, int index)
	{
		return (this.getShader(stack) != null && index == 1) ? this.getShader(stack).getShaderColor() : 16777215;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels()
	{
		ModelLoader.registerItemVariants(this, new ResourceLocation(Reference.MODID + ":" + this.getItemName()));
		
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		ModelLoader.setCustomMeshDefinition(this, new CustomMeshDefinitionShader());
	}
}