package com.nhave.tow.registry;

import java.util.Map.Entry;

import com.nhave.nhc.util.StringUtils;
import com.nhave.tow.Reference;
import com.nhave.tow.api.TOWAPI;
import com.nhave.tow.api.shaders.Shader;
import com.nhave.tow.api.wrenchmodes.WrenchMode;
import com.nhave.tow.client.mesh.CustomMeshDefinitionMetaItem;
import com.nhave.tow.client.mesh.CustomMeshDefinitionShader;
import com.nhave.tow.client.mesh.CustomMeshDefinitionWrench;
import com.nhave.tow.client.render.ItemColorHandler;
import com.nhave.tow.items.ItemBase;
import com.nhave.tow.items.ItemMeta;
import com.nhave.tow.items.ItemOmniwrench;
import com.nhave.tow.items.ItemShader;
import com.nhave.tow.items.ItemShaderPack;
import com.nhave.tow.items.ItemShaderRemover;
import com.nhave.tow.shaders.ShaderRegistry;
import com.nhave.tow.wrenchmodes.WrenchModeTune;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems
{
	public static CreativeTabs TAB_ITEMS;
    public static CreativeTabs TAB_SHADERS;
	
	public static WrenchMode modeWrench;
	public static WrenchMode modeRotate;
	public static WrenchMode modeTune;
	public static WrenchMode modeUtil;
	public static WrenchMode modeNone;
	
	public static Item itemOmniWrench;
	public static ItemMeta itemComp;
	public static Item itemShader;
	public static Item itemShaderRemover;
	public static Item itemIcons;
	public static Item itemShaderPackBase;
	public static Item itemShaderPackBooster;
	public static Item itemShaderPackDestiny;
	public static Item itemShaderPackOverwatch;
	
	public static String[][] craftingComponents = new String[][]
	{
		{"claw", "2"}, {"handle", "2"}, {"piston", "2"}
	};
	
	public static void init()
	{
		ModShaders.init();
		addCreativeTabs();
		
		itemOmniWrench = new ItemOmniwrench("wrench");
		itemComp = new ItemMeta("comp", craftingComponents);
		itemShaderRemover = new ItemShaderRemover("shaderremover");
		itemShader = new ItemShader("shader");
		itemIcons = new ItemBase("icons").setCreativeTab(null).setHasSubtypes(true);
		itemShaderPackBase = new ItemShaderPack("shaderpackbase", ModShaders.BASE_SHADERS, ModShaders.BASE_SHADERS.size()).setCreativeTab(ModConfig.enableAllShaders ? TAB_ITEMS : null);
		itemShaderPackBooster = new ItemShaderPack("shaderpackbooster", ModShaders.ALL_SHADERS, 3).setQuality(StringUtils.LIGHT_BLUE).setCreativeTab(ModConfig.enableAllShaders ? TAB_ITEMS : null);
		
		itemShaderPackDestiny = new ItemShaderPack("shaderpackdestiny", ModShaders.DESTINY_SHADERS, 3).setQuality(StringUtils.PURPLE).setCreativeTab(ModConfig.enableAllShaders && ModConfig.enableDestinyShaders ? TAB_ITEMS : null);
		itemShaderPackOverwatch = new ItemShaderPack("shaderpackoverwatch", ModShaders.OVERWATCH_SHADERS, 3).setQuality(StringUtils.PURPLE).setCreativeTab(ModConfig.enableAllShaders && ModConfig.enableOverwatchShaders ? TAB_ITEMS : null);
		
		modeWrench = TOWAPI.modeRegistry.register("wrench", new ItemStack(itemIcons, 1, 2));
		modeRotate = TOWAPI.modeRegistry.register("rotate", new ItemStack(itemIcons, 1, 3));
		modeTune = TOWAPI.modeRegistry.register(new WrenchModeTune("tune", new ItemStack(itemIcons, 1, 4)));
		modeUtil = TOWAPI.modeRegistry.register("util", new ItemStack(itemIcons, 1, 5));
		modeNone = TOWAPI.modeRegistry.register("none", new ItemStack(itemIcons, 1, 6));
	}
	
	public static void addCreativeTabs()
	{
	    TAB_ITEMS = new CreativeTabs("tow.items")
		{
			@Override
			public ItemStack getTabIconItem()
			{
				return new ItemStack(itemIcons, 1, 0);
			}
		};
		if (ModConfig.enableAllShaders)
		{
		    TAB_SHADERS = new CreativeTabs("tow.shaders")
			{
				@Override
				public ItemStack getTabIconItem()
				{
					return new ItemStack(itemIcons, 1, 1);
				}
				
				@Override
				public boolean hasSearchBar()
				{
					return true;
				}
			}.setBackgroundImageName("item_search.png");
		}
	}
	
	public static void register()
	{
		GameRegistry.register(itemOmniWrench);
		GameRegistry.register(itemComp);
		GameRegistry.register(itemShaderRemover);
		GameRegistry.register(itemShader);
		GameRegistry.register(itemIcons);
		GameRegistry.register(itemShaderPackBase);
		GameRegistry.register(itemShaderPackBooster);
		GameRegistry.register(itemShaderPackDestiny);
		GameRegistry.register(itemShaderPackOverwatch);
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerRenders()
	{
		ModelBakery.registerItemVariants(itemOmniWrench, new ResourceLocation(Reference.MODID + ":" + itemOmniWrench.getRegistryName().getResourcePath()));
		
		if (!ShaderRegistry.isEmpty())
		{
			for(Entry<String, Shader> entry : ShaderRegistry.SHADERS.entrySet())
			{
				Shader shader = entry.getValue();
				shader.registerModels(itemOmniWrench);
				ModelBakery.registerItemVariants(itemShader, new ResourceLocation(shader.getShaderModel()));
			}
		}
		
		registerRenderMesh(itemOmniWrench, new CustomMeshDefinitionWrench());
		registerMetaRender(itemComp, craftingComponents.length, false);
		registerRender(itemShaderRemover);
		registerRenderMesh(itemShader, new CustomMeshDefinitionShader());
		registerMetaRender(itemIcons, 7, false);
		registerRender(itemShaderPackBase);
		registerRender(itemShaderPackBooster);
		registerRender(itemShaderPackDestiny);
		registerRender(itemShaderPackOverwatch);
		
		FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler(ItemColorHandler.INSTANCE, itemOmniWrench);
		FMLClientHandler.instance().getClient().getItemColors().registerItemColorHandler(ItemColorHandler.INSTANCE, itemShader);
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerRender(Item item)
	{
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		renderItem.getItemModelMesher().register(item, 0, new ModelResourceLocation(Reference.MODID + ":" + item.getRegistryName().getResourcePath(), "inventory"));
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerMetaRender(Item item, int loop, boolean single)
	{
		for (int i = 0; i < loop; ++i)
		{
			String meta = "";
			if (i > 0 && !single) meta = "_" + i;
			ModelBakery.registerItemVariants(item, new ResourceLocation(Reference.MODID + ":" + item.getRegistryName().getResourcePath() + meta));
		}
		registerRenderMesh(item, new CustomMeshDefinitionMetaItem(single));
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerRenderMesh(Item item, ItemMeshDefinition mesh)
	{
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		
		renderItem.getItemModelMesher().register(item, mesh);
	}
}