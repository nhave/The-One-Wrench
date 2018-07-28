package com.nhave.tow.common.item;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.nhave.lib.common.network.Key;
import com.nhave.lib.library.client.render.IModelRegister;
import com.nhave.lib.library.helper.ItemNBTHelper;
import com.nhave.lib.library.item.IColoredItem;
import com.nhave.lib.library.item.IHudItem;
import com.nhave.lib.library.item.IKeyBound;
import com.nhave.lib.library.item.INTechWrench;
import com.nhave.lib.library.util.ItemUtil;
import com.nhave.lib.library.util.StringUtils;
import com.nhave.nhc.api.items.INHWrench;
import com.nhave.ntechcore.api.item.IChromaAcceptor;
import com.nhave.tow.client.mesh.CustomMeshDefinitionWrench;
import com.nhave.tow.common.content.ModConfig;
import com.nhave.tow.common.content.ModIntegration;
import com.nhave.tow.common.content.ModItems;
import com.nhave.tow.common.helper.BlockRotationHelper;
import com.nhave.tow.common.helper.ChromaHelper;
import com.nhave.tow.common.helper.DismantleHelper;
import com.nhave.tow.common.integration.IntegrationRegistry;
import com.nhave.tow.common.integration.enderio.EnderIOIntegration;
import com.nhave.tow.common.integration.ntech.RPGTooltipsIntegration;
import com.nhave.tow.common.itemshader.Shader;
import com.nhave.tow.common.itemshader.ShaderRegistry;
import com.nhave.tow.common.wrenchmode.ModeRegistry;
import com.nhave.tow.common.wrenchmode.WrenchMode;
import com.nhave.tow.core.Reference;

import appeng.api.implementations.items.IAEWrench;
import blusunrize.immersiveengineering.api.tool.ITool;
import buildcraft.api.tools.IToolWrench;
import cofh.api.item.IToolHammer;
import crazypants.enderio.api.tool.IConduitControl;
import li.cil.oc.api.internal.Wrench;
import mrtjp.projectred.api.IScrewdriver;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Optional.InterfaceList({
    @Optional.Interface(iface = "blusunrize.immersiveengineering.api.tool.ITool", modid = "immersiveengineering"),
    @Optional.Interface(iface = "crazypants.enderio.api.tool.IConduitControl", modid = "enderio"),
    @Optional.Interface(iface = "com.nhave.nhc.api.items.INHWrench", modid = "nhc"),
    @Optional.Interface(iface = "mrtjp.projectred.api.IScrewdriver", modid = "projectred-core"),
    @Optional.Interface(iface = "appeng.api.implementations.items.IAEWrench", modid = "appliedenergistics2"),
    @Optional.Interface(iface = "li.cil.oc.api.internal.Wrench", modid = "OpenComputers"),
    @Optional.Interface(iface = "buildcraft.api.tools.IToolWrench", modid = "buildcraftcore"),
    @Optional.Interface(iface = "cofh.api.item.IToolHammer", modid = "cofhcore"),
    @Optional.Interface(iface = "com.nhave.ntechcore.api.item.IChromaAcceptor", modid = "ntechcore")
})
public class ItemOmniwrench extends ItemBase implements IHudItem, IModelRegister, IColoredItem, IKeyBound, INTechWrench, IToolHammer, Wrench, IAEWrench, ITool, IToolWrench, IConduitControl, INHWrench, IScrewdriver, IChromaAcceptor
{
	private String[] rarityColors = new String[] {StringUtils.WHITE, StringUtils.LIGHT_BLUE, StringUtils.PURPLE, StringUtils.ORANGE};
	
	public ItemOmniwrench(String name)
	{
		super(name);
		this.setMaxStackSize(1);
		this.setNoRepair();
		this.setHasSubtypes(true);
	}
	
	/* =========================================================== Client Code ===============================================================*/
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if (StringUtils.isShiftKeyDown())
		{
			if (ModConfig.enableAllShaders)
			{
				String shaderName = StringUtils.localize("tooltip.tow.shader.none");
				String color = StringUtils.YELLOW;
				
				if (hasShader(stack))
				{
					ItemStack shaderStack = ItemUtil.getItemFromStack(stack, "SHADER");
					shaderName = shaderStack.getDisplayName();
					color = rarityColors[getShader(stack).getQuality()];
					if (color.length() <= 0) color = StringUtils.WHITE;
					
					//Display the correct color if rpgtooltips is loaded
					if (ModIntegration.rpgtooltipsLoaded) color = RPGTooltipsIntegration.getDisplayColor(shaderStack);
				}
				
				tooltip.add(StringUtils.localize("tooltip.tow.shader.current") + ": " + StringUtils.format(shaderName, color, StringUtils.ITALIC));
			}
			
			ChromaHelper.addTooltip(stack, tooltip);
			
			tooltip.add(StringUtils.localize("tooltip.tow.mouse.use") + " " + StringUtils.format(Minecraft.getMinecraft().gameSettings.keyBindSneak.getDisplayName() +  "+" + StringUtils.localize("tooltip.tow.mouse.wheel"), StringUtils.YELLOW, StringUtils.ITALIC));
			tooltip.add(" - " + StringUtils.localize("tooltip.tow.mode.change"));
			tooltip.add(StringUtils.localize("tooltip.tow.mode") + ": " + StringUtils.format(StringUtils.localize("tooltip.tow.mode." + getWrenchMode(stack).getName()), StringUtils.YELLOW, StringUtils.ITALIC));
			
			getWrenchMode(stack).addInformation(stack, Minecraft.getMinecraft().player, tooltip, flagIn.isAdvanced());
		}
		else tooltip.add(StringUtils.shiftForInfo());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addHudInfo(ItemStack stack, EntityPlayer player, List list, boolean isArmor)
	{
		list.add(StringUtils.format(this.getItemStackDisplayName(stack), StringUtils.YELLOW, StringUtils.ITALIC));
		list.add(StringUtils.localize("tooltip.tow.mode") + ": " + StringUtils.format(StringUtils.localize("tooltip.tow.mode." + getWrenchMode(stack).getName()), StringUtils.YELLOW, StringUtils.ITALIC));
		
		if (getWrenchMode(stack) instanceof IHudItem) ((IHudItem) getWrenchMode(stack)).addHudInfo(stack, player, list, isArmor);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels()
	{
		ModelLoader.registerItemVariants(this, new ResourceLocation(Reference.MODID + ":" + this.getItemName()));
		
		if (!ShaderRegistry.isEmpty())
		{
			for(Entry<String, Shader> entry : ShaderRegistry.SHADERS.entrySet())
			{
				Shader shader = entry.getValue();
				shader.registerModels(this);
				ModelLoader.registerItemVariants(ModItems.itemShader, new ResourceLocation(shader.getShaderModel()));
			}
		}
		
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		ModelLoader.setCustomMeshDefinition(this, new CustomMeshDefinitionWrench());
	}
	
	@Override
	public int getItemColor(ItemStack stack, int index)
	{
		return (index == 1 ? ChromaHelper.getColor(stack) : 16777215);
	}
	
	/* =========================================================== Setup Code ===============================================================*/
	
	@Override
	public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player)
	{
		return true;
	}
	
	public void setMode(ItemStack stack, int mode)
	{
		ItemNBTHelper.setInteger(stack, "WRENCH", "MODE", mode);
	}
	
	public int getModeAsNumber(ItemStack stack)
	{
		int mode = ItemNBTHelper.getInteger(stack, "WRENCH", "MODE", 0);
		return (mode >= 0 && mode < ModeRegistry.getSize()) ? mode : 0;
	}
	
	public WrenchMode getWrenchMode(ItemStack stack)
	{
		return ModeRegistry.getMode(getModeAsNumber(stack));
	}
	
	public static boolean hasShader(ItemStack stack)
	{
		ItemStack stackShader = ItemUtil.getItemFromStack(stack, "SHADER");
		return stackShader != null && stackShader.getItem() instanceof ItemShader && ((ItemShader) stackShader.getItem()).getShader(stackShader) != null;
	}
	
	public static Shader getShader(ItemStack stack)
	{
		if (hasShader(stack))
		{
			ItemStack stackShader = ItemUtil.getItemFromStack(stack, "SHADER");
			return ((ItemShader) stackShader.getItem()).getShader(stackShader);
		}
		else return null;
	}
	
	/* =========================================================== Usage Code ===============================================================*/
	
	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
		if (hand == EnumHand.OFF_HAND) return EnumActionResult.PASS;
	    IBlockState bs = world.getBlockState(pos);
	    Block block = bs.getBlock();
	    ItemStack stack = player.getHeldItem(hand);
	    
	    for (int i = 0; i < IntegrationRegistry.getCount(); ++i)
	    {
	    	EnumActionResult result = IntegrationRegistry.getHandler(i).onWrenchUseFirst(this.getWrenchMode(stack), player, world, pos, side, hitX, hitY, hitZ, hand);
	    	if (result != null && result != EnumActionResult.PASS) return result;
	    }
	    	
	    if (this.getWrenchMode(stack) == ModItems.modeWrench)
	    {
	    	if (player.isSneaking() && DismantleHelper.customDismantle(world, pos, bs, player))
	    	{
		    	if (!world.isRemote) return EnumActionResult.SUCCESS;
				else
				{
					player.playSound(block.getSoundType(bs, world, pos, player).getPlaceSound(), 1.0F, 0.6F);
					player.swingArm(EnumHand.MAIN_HAND);
				}
	    	}
	    }
	    else if (this.getWrenchMode(stack) == ModItems.modeRotate)
	    {
	    	boolean doRotation = true;
	    	
	    	for (int i = 0; i < IntegrationRegistry.getCount(); ++i)
		    {
		    	if (IntegrationRegistry.getHandler(i).preventBlockRotation(player, world, pos))
		    	{
		    		doRotation = false;
		    	}
		    }
			if (doRotation)
		    {
				EnumActionResult rotated = BlockRotationHelper.rotateBlock(player, world, pos);
				if (rotated == EnumActionResult.SUCCESS)
				{
					world.markChunkDirty(pos, null);
			    	if (!world.isRemote) return EnumActionResult.SUCCESS;
					else
					{
						player.playSound(block.getSoundType(bs, world, pos, player).getPlaceSound(), 1.0F, 0.6F);
						player.swingArm(EnumHand.MAIN_HAND);
					}
				}
				else if (rotated == EnumActionResult.FAIL)
				{
					return EnumActionResult.PASS;
				}
				else if (block.rotateBlock(world, pos, side))
				{
					world.markChunkDirty(pos, null);
			    	if (!world.isRemote) return EnumActionResult.SUCCESS;
					else
					{
						player.playSound(block.getSoundType(bs, world, pos, player).getPlaceSound(), 1.0F, 0.6F);
						player.swingArm(EnumHand.MAIN_HAND);
					}
				}
		    }
	    }
	    
		return EnumActionResult.PASS;
	}
	
	@Override
	public int getHarvestLevel(ItemStack stack, String toolClass, EntityPlayer player, IBlockState blockState)
	{
		return super.getHarvestLevel(stack, toolClass, player, blockState);
	}
	
	@Override
	public Set<String> getToolClasses(ItemStack stack)
	{
		return super.getToolClasses(stack);
	}
	
	/* =========================================================== Interface: IKeyBound ===============================================================*/
	
	@Override
	public boolean isKeyInUse(ItemStack itemStack, Key key)
	{
		if (ModIntegration.enderioLoaded && (key == Key.SCROLLUP_ALT || key == Key.SCROLLDN_ALT)) return true;
		else if (getWrenchMode(itemStack) instanceof IKeyBound && ((IKeyBound) getWrenchMode(itemStack)).isKeyInUse(itemStack, key)) return true;
		return (key == Key.TOGGLE_ITEM || key == Key.SCROLLUP || key == Key.SCROLLDN);
	}
	
	@Override
	public void doKeyBindingAction(EntityPlayer player, ItemStack itemStack, Key key, boolean chat)
	{
		if ((key == Key.TOGGLE_ITEM && !player.isSneaking()) || key == Key.SCROLLDN) setMode(itemStack, (getModeAsNumber(itemStack) < ModeRegistry.getSize() -1 ? getModeAsNumber(itemStack) + 1 : 0));
		else if (key == Key.SCROLLUP) setMode(itemStack, (getModeAsNumber(itemStack) > 0 ? getModeAsNumber(itemStack) - 1 : ModeRegistry.getSize() - 1));
		else if (getWrenchMode(itemStack) instanceof IKeyBound && ((IKeyBound) getWrenchMode(itemStack)).isKeyInUse(itemStack, key)) ((IKeyBound) getWrenchMode(itemStack)).doKeyBindingAction(player, itemStack, key, chat);
		else if (ModIntegration.enderioLoaded && (key == Key.SCROLLUP_ALT || key == Key.SCROLLDN_ALT)) EnderIOIntegration.doKeyBindingAction(player, itemStack, key, chat);
		
		if ((key == Key.TOGGLE_ITEM && !player.isSneaking()) || key == Key.SCROLLDN || key == Key.SCROLLUP) player.sendStatusMessage(new TextComponentString(TextFormatting.GOLD + new TextComponentTranslation("tooltip.tow.mode").getUnformattedComponentText()).appendText(": ").appendSibling(new TextComponentTranslation("tooltip.tow.mode." + getWrenchMode(itemStack).getName())), true);
	}
	
	/* =========================================================== Interface: INTechWrench ===============================================================*/
	
	@Override
	public boolean canUseWrench(ItemStack stack, EntityPlayer player, EnumHand hand, BlockPos pos)
	{
		return this.getWrenchMode(stack) == ModItems.modeWrench;
	}

	@Override
	public void onWrenchUsed(ItemStack stack, EntityPlayer player, EnumHand hand, BlockPos pos)
	{
		player.swingArm(hand);
	}
	
	/* =========================================================== Interface: IToolHammer ===============================================================*/
	
	@Optional.Method(modid = "cofhcore")
	@Override
	public boolean isUsable(ItemStack item, EntityLivingBase user, BlockPos pos)
	{
		return this.getWrenchMode(item) == ModItems.modeWrench;
	}
	
	@Optional.Method(modid = "cofhcore")
	@Override
	public boolean isUsable(ItemStack item, EntityLivingBase user, Entity entity)
	{
		return this.getWrenchMode(item) == ModItems.modeWrench;
	}
	
	@Optional.Method(modid = "cofhcore")
	@Override
	public void toolUsed(ItemStack item, EntityLivingBase user, BlockPos pos)
	{
		user.swingArm(EnumHand.MAIN_HAND);
	}
	
	@Optional.Method(modid = "cofhcore")
	@Override
	public void toolUsed(ItemStack item, EntityLivingBase user, Entity entity)
	{
		user.swingArm(EnumHand.MAIN_HAND);
	}
	
	/* =========================================================== Interface: Wrench ===============================================================*/
	
	@Optional.Method(modid = "OpenComputers")
	@Override
	public boolean useWrenchOnBlock(EntityPlayer player, World world, BlockPos pos, boolean simulate)
	{
		return this.getWrenchMode(player.getHeldItemMainhand()) == ModItems.modeWrench;
	}
	
	/* =========================================================== Interface: IAEWrench ===============================================================*/
	
	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public boolean canWrench(ItemStack wrench, EntityPlayer player, BlockPos pos)
	{
		return this.getWrenchMode(wrench) == ModItems.modeWrench;
	}
	
	/* =========================================================== Interface: ITool ===============================================================*/
	
	@Optional.Method(modid = "immersiveengineering")
	@Override
	public boolean isTool(ItemStack item)
	{
		return true;
	}
	
	/* =========================================================== Interface: IToolWrench ===============================================================*/
	
	@Optional.Method(modid = "buildcraftcore")
	@Override
	public boolean canWrench(EntityPlayer player, EnumHand hand, ItemStack wrench, RayTraceResult rayTrace)
	{
		return this.getWrenchMode(wrench) == ModItems.modeWrench;
	}
	
	@Optional.Method(modid = "buildcraftcore")
	@Override
	public void wrenchUsed(EntityPlayer player, EnumHand hand, ItemStack wrench, RayTraceResult rayTrace)
	{
		player.swingArm(hand);
	}
	
	/* =========================================================== Interface: IConduitControl ===============================================================*/
	
	@Optional.Method(modid = "enderio")
	@Override
	public boolean shouldHideFacades(ItemStack stack, EntityPlayer player)
	{
		return true;
	}
	
	@Optional.Method(modid = "enderio")
	@Override
	public boolean showOverlay(ItemStack stack, EntityPlayer player)
	{
		return true;
	}
	
	/* =========================================================== Interface: INHWrench ===============================================================*/
	
	@Optional.Method(modid = "nhc")
	@Override
	public boolean canItemWrench(EntityPlayer player, int x, int y, int z)
	{
		ItemStack stack = player.getHeldItemMainhand();
		return this.getWrenchMode(stack) == ModItems.modeWrench;
	}
	
	@Optional.Method(modid = "nhc")
	@Override
	public void onWrenchUsed(EntityPlayer player, int x, int y, int z)
	{
		player.swingArm(EnumHand.MAIN_HAND);
	}
	
	/* =========================================================== Interface: IScrewdriver ===============================================================*/
	
	@Optional.Method(modid = "projectred-core")
	@Override
	public boolean canUse(EntityPlayer player, ItemStack stack)
	{
		return this.getWrenchMode(stack) == ModItems.modeTune;
	}
	
	@Optional.Method(modid = "projectred-core")
	@Override
	public void damageScrewdriver(EntityPlayer player, ItemStack stack)
	{
		player.swingArm(EnumHand.MAIN_HAND);
	}
	
	/* =========================================================== Interface: IChromaAcceptor ===============================================================*/
	
	@Optional.Method(modid = "ntechcore")
	@Override
	public boolean supportsChroma(ItemStack stack)
	{
		return (!hasShader(stack)) || (hasShader(stack) && getShader(stack).getSupportsChroma());
	}
}