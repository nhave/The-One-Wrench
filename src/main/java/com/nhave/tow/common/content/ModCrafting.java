package com.nhave.tow.common.content;

import com.nhave.tow.core.Reference;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ModCrafting
{
	private static int recipe = 0;
	
	public static void register(Register<IRecipe> event)
	{
		addRecipe(event, new ShapedOreRecipe(null, ModItems.createItemStack(ModItems.itemComp, "claw", 1),
			new Object[] {" XY", "XZY", "XY ",
			'X', Items.IRON_INGOT,
			'Y', Blocks.OBSIDIAN,
			'Z', Items.REDSTONE}));
		addRecipe(event, new ShapedOreRecipe(null, ModItems.createItemStack(ModItems.itemComp, "handle", 1),
			new Object[] {" YX", "YZY", "XY ",
			'X', Items.IRON_INGOT,
			'Y', Items.QUARTZ,
			'Z', Items.REDSTONE}));
		addRecipe(event, new ShapedOreRecipe(null, ModItems.createItemStack(ModItems.itemComp, "piston", 1),
			new Object[] {"XYX", "ZAZ", "XYX",
			'X', Blocks.PISTON,
			'Y', Items.REDSTONE,
			'Z', Items.IRON_INGOT,
			'A', Items.EMERALD}));
		addRecipe(event, new ShapedOreRecipe(null, new ItemStack(ModItems.itemOmniWrench),
			new Object[] {" X ", " YX", "Z  ",
			'X', ModItems.createItemStack(ModItems.itemComp, "claw", 1),
			'Y', ModItems.createItemStack(ModItems.itemComp, "piston", 1),
			'Z', ModItems.createItemStack(ModItems.itemComp, "handle", 1)}));
		
		if (ModConfig.enableAllShaders)
		{
			addRecipe(event, new ShapedOreRecipe(null, new ItemStack(ModItems.itemShaderPackBase),
				new Object[] {"XYX", "YZY", "XYX",
				'X', "nuggetGold",
				'Y', "dyeWhite",
				'Z', Items.REDSTONE}));
			addRecipe(event, new ShapedOreRecipe(null, new ItemStack(ModItems.itemShaderPackBooster),
				new Object[] {"XYX", "YZY", "XYX",
				'X', "nuggetGold",
				'Y', "dyeRed",
				'Z', Items.EMERALD}));
			addRecipe(event, new ShapedOreRecipe(null, new ItemStack(ModItems.itemShaderPackEvent),
				new Object[] {"XYX", "YZY", "XYX",
				'X', "nuggetGold",
				'Y', "dyePurple",
				'Z', Items.EMERALD}));
			addRecipe(event, new ShapedOreRecipe(null, new ItemStack(ModItems.itemShaderRemover),
				new Object[] {"XYX", "YZY", "XYX",
				'X', "nuggetGold",
				'Y', "nuggetIron",
				'Z', Items.WATER_BUCKET}));
			
			if (ModConfig.enableDestinyShaders)
			{
				addRecipe(event, new ShapedOreRecipe(null, new ItemStack(ModItems.itemShaderPackDestiny),
					new Object[] {"XYX", "YZY", "XYX",
					'X', "nuggetGold",
					'Y', "dyeGreen",
					'Z', Items.EMERALD}));
			}
			
			if (ModConfig.enableOverwatchShaders)
			{
				addRecipe(event, new ShapedOreRecipe(null, new ItemStack(ModItems.itemShaderPackOverwatch),
					new Object[] {"XYX", "YZY", "XYX",
					'X', "nuggetGold",
					'Y', "dyeOrange",
					'Z', Items.EMERALD}));
			}
		}
	}
	
	public static void addRecipe(Register<IRecipe> event, IRecipe rec)
	{
		event.getRegistry().register(rec.setRegistryName(new ResourceLocation(Reference.MODID, "recipe" + recipe)));
		++recipe;
	}
}