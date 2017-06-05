package com.nhave.tow.registry;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ModCrafting
{
	public static void init()
	{
		GameRegistry.addRecipe(ModItems.itemComp.getItem("claw", 1),
			new Object[] {" XY", "XZY", "XY ",
			'X', Items.IRON_INGOT,
			'Y', Blocks.OBSIDIAN,
			'Z', Items.REDSTONE});
		GameRegistry.addRecipe(ModItems.itemComp.getItem("handle", 1),
			new Object[] {" YX", "YZY", "XY ",
			'X', Items.IRON_INGOT,
			'Y', Items.QUARTZ,
			'Z', Items.REDSTONE});
		GameRegistry.addRecipe(ModItems.itemComp.getItem("piston", 1),
			new Object[] {"XYX", "ZAZ", "XYX",
			'X', Blocks.PISTON,
			'Y', Items.REDSTONE,
			'Z', Items.IRON_INGOT,
			'A', Items.EMERALD});
		GameRegistry.addRecipe(new ItemStack(ModItems.itemOmniWrench),
			new Object[] {" X ", " YX", "Z  ",
			'X', ModItems.itemComp.getItem("claw", 1),
			'Y', ModItems.itemComp.getItem("piston", 1),
			'Z', ModItems.itemComp.getItem("handle", 1)});
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemShaderPackBase),
			new Object[] {"XYX", "YZY", "XYX",
			'X', "nuggetGold",
			'Y', "dyeWhite",
			'Z', Items.REDSTONE}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemShaderPackBooster),
			new Object[] {"XYX", "YZY", "XYX",
			'X', "nuggetGold",
			'Y', "dyeRed",
			'Z', Items.EMERALD}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemShaderPackDestiny),
			new Object[] {"XYX", "YZY", "XYX",
			'X', "nuggetGold",
			'Y', "dyeGreen",
			'Z', Items.EMERALD}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemShaderPackOverwatch),
			new Object[] {"XYX", "YZY", "XYX",
			'X', "nuggetGold",
			'Y', "dyeOrange",
			'Z', Items.EMERALD}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ModItems.itemShaderRemover),
			new Object[] {"XYX", "YZY", "XYX",
			'X', "nuggetGold",
			'Y', "nuggetIron",
			'Z', Items.WATER_BUCKET}));
	}
}