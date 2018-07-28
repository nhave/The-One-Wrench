package com.nhave.tow.common.content;

import java.util.ArrayList;
import java.util.List;

import com.nhave.lib.library.client.render.IModelRegister;
import com.nhave.lib.library.helper.ItemNBTHelper;
import com.nhave.lib.library.item.IOreRegister;
import com.nhave.tow.common.item.ItemMeta;
import com.nhave.tow.common.item.ItemOmniwrench;
import com.nhave.tow.common.item.ItemShader;
import com.nhave.tow.common.item.ItemShaderPack;
import com.nhave.tow.common.item.ItemShaderPackEvent;
import com.nhave.tow.common.item.ItemShaderRemover;
import com.nhave.tow.common.wrenchmode.ModeRegistry;
import com.nhave.tow.common.wrenchmode.WrenchMode;
import com.nhave.tow.common.wrenchmode.WrenchModeTune;
import com.nhave.tow.core.Reference;
import com.nhave.tow.core.TheOneWrench;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems
{
	public static final ModItems INSTANCE = new ModItems();
	
	private static final List<Item> ITEMS = new ArrayList<Item>();
	
	public static WrenchMode modeWrench;
	public static WrenchMode modeRotate;
	public static WrenchMode modeTune;
	public static WrenchMode modeUtil;
	public static WrenchMode modeNone;
	
	public static CreativeTabs TAB_ITEMS;
    public static CreativeTabs TAB_SHADERS;

	public static Item itemOmniWrench;
	public static Item itemComp;
	public static Item itemShader;
	public static Item itemShaderRemover;
	public static Item itemShaderPackBase;
	public static Item itemShaderPackBooster;
	public static Item itemShaderPackDestiny;
	public static Item itemShaderPackOverwatch;
	public static Item itemShaderPackEvent;
	
	public static String[][] craftingComponents = new String[][] {
		{"claw", "2"}, {"handle", "2"}, {"piston", "2"}};
	
	public static void init()
	{
		addCreativeTabs();
		
		itemOmniWrench = addItem(new ItemOmniwrench("wrench").setQuality(3));
		itemComp = addItem(new ItemMeta("comp", craftingComponents));
		itemShaderRemover = addItem(new ItemShaderRemover("shaderremover"));
		itemShader = addItem(new ItemShader("shader"));
		itemShaderPackBase = addItem(new ItemShaderPack("shaderpackbase", ModShaders.BASE_SHADERS, ModShaders.BASE_SHADERS.size()).setCreativeTab(ModConfig.enableAllShaders ? TAB_ITEMS : null));
		itemShaderPackBooster = addItem(new ItemShaderPack("shaderpackbooster", ModShaders.ALL_SHADERS, 3).setQuality(1).setCreativeTab(ModConfig.enableAllShaders ? TAB_ITEMS : null));
		itemShaderPackDestiny = addItem(new ItemShaderPack("shaderpackdestiny", ModShaders.DESTINY_SHADERS, 3).setQuality(2).setCreativeTab(ModConfig.enableAllShaders && ModConfig.enableDestinyShaders ? TAB_ITEMS : null));
		itemShaderPackOverwatch = addItem(new ItemShaderPack("shaderpackoverwatch", ModShaders.OVERWATCH_SHADERS, 3).setQuality(2).setCreativeTab(ModConfig.enableAllShaders && ModConfig.enableOverwatchShaders ? TAB_ITEMS : null));
		itemShaderPackEvent = addItem(new ItemShaderPackEvent("shaderpackevent").setQuality(2).setCreativeTab(ModConfig.enableAllShaders ? TAB_ITEMS : null));
		
		modeWrench = ModeRegistry.register("wrench");
		modeRotate = ModeRegistry.register("rotate");
		modeTune = ModeRegistry.register(new WrenchModeTune("tune"));
		modeUtil = ModeRegistry.register("util");
		modeNone = ModeRegistry.register("none");
	}
	
	public static void addCreativeTabs()
	{
	    TAB_ITEMS = new CreativeTabs("tow.items")
		{
			@Override
			public ItemStack getTabIconItem()
			{
				return new ItemStack(itemOmniWrench);
			}
		};
		if (ModConfig.enableAllShaders)
		{
		    TAB_SHADERS = new CreativeTabs("tow.shaders")
			{
				@Override
				public ItemStack getTabIconItem()
				{
					return new ItemStack(itemShader);
				}
				
				@Override
				public boolean hasSearchBar()
				{
					return true;
				}
			}.setBackgroundImageName("item_search.png");
		}
	}
	
	private static Item addItem(Item item)
	{
		ITEMS.add(item);
		return item;
	}
	
	public static List<Item> getItems()
	{
		return ITEMS;
	}
	
	public static void register(Register<Item> event)
	{
		for (Item item : getItems())
		{
			TheOneWrench.logger.info("Registering Item: " + item.getRegistryName());
			event.getRegistry().register(item);
			if (item instanceof IOreRegister) ((IOreRegister) item).registerOres();
		}
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerRenders()
	{
		for (Item item : getItems())
		{
			TheOneWrench.logger.info("Registering Renderers for Item: " + item.getRegistryName());
			if (item instanceof IModelRegister) ((IModelRegister) item).registerModels();
			else registerRender(item);
		}
	}
	
	@SideOnly(Side.CLIENT)
	private static void registerRender(Item item)
	{
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(Reference.MODID + ":" + item.getRegistryName().getResourcePath(), "inventory"));
	}
	
	public static ItemStack createItemStack(Item item, String name, int amount)
	{
		if (item instanceof ItemMeta)
		{
			ItemMeta metaItem = (ItemMeta) item;
			for (int meta = 0; meta < metaItem.names.length; ++meta)
			{
				if (metaItem.names[meta].toLowerCase().equals(name.toLowerCase())) return new ItemStack(item, amount, meta);
			}
		}
		else if (item instanceof ItemShader)
		{
			ItemStack shader = new ItemStack(item, amount);
			ItemNBTHelper.setString(shader, "SHADERS", "SHADER", name.toLowerCase());
			return shader;
		}
		return new ItemStack(item, amount);
	}
}