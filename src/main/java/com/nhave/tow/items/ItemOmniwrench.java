package com.nhave.tow.items;

import java.util.List;
import java.util.Set;

import com.nhave.nhc.api.items.IChromaAcceptor;
import com.nhave.nhc.api.items.IHudItem;
import com.nhave.nhc.api.items.IInventoryItem;
import com.nhave.nhc.api.items.IItemQuality;
import com.nhave.nhc.api.items.IKeyBound;
import com.nhave.nhc.api.items.IMouseWheel;
import com.nhave.nhc.api.items.INHWrench;
import com.nhave.nhc.api.items.IToolStationHud;
import com.nhave.nhc.api.items.IWidgetControl;
import com.nhave.nhc.client.widget.WidgetBase;
import com.nhave.nhc.helpers.ItemNBTHelper;
import com.nhave.nhc.items.ItemChroma;
import com.nhave.nhc.network.Key;
import com.nhave.nhc.util.ItemUtil;
import com.nhave.nhc.util.StringUtils;
import com.nhave.tow.client.widget.WidgetWrench;
import com.nhave.tow.helpers.DismantleHelper;
import com.nhave.tow.integration.WrenchRegistry;
import com.nhave.tow.registry.ModConfig;
import com.nhave.tow.registry.ModItems;
import com.nhave.tow.shaders.Shader;
import com.nhave.tow.wrenchmodes.ModeRegistry;
import com.nhave.tow.wrenchmodes.WrenchMode;

import appeng.api.implementations.items.IAEWrench;
import blusunrize.immersiveengineering.api.tool.ITool;
import cofh.api.item.IToolHammer;
import li.cil.oc.api.internal.Wrench;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemOmniwrench extends ItemBase implements IWidgetControl, IHudItem, IToolStationHud, IMouseWheel, IKeyBound, IItemQuality, IChromaAcceptor, IInventoryItem, INHWrench, IToolHammer, Wrench, IAEWrench, ITool
{
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
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean flag)
	{
		if (StringUtils.isShiftKeyDown())
		{
			if (ModConfig.enableAllShaders)
			{
				String shaderName = StringUtils.localize("tooltip.tow.shader.none");
				if (hasShader(stack)) shaderName = ItemUtil.getItemFromStack(stack, "SHADER").getDisplayName();
				
				list.add(StringUtils.localize("tooltip.tow.shader.current") + ": " + StringUtils.format(shaderName, StringUtils.YELLOW, StringUtils.ITALIC));
			}
			
			if (!hasShader(stack) || (hasShader(stack) && getShader(stack).getSupportsChroma())) list.add(StringUtils.localize("tooltip.tow.chroma.current") + ": " + StringUtils.format(getStackInSlot(stack, 0).getDisplayName(), StringUtils.YELLOW, StringUtils.ITALIC));
			
			list.add(StringUtils.localize("tooltip.tow.mouse.use") + " " + StringUtils.format(StringUtils.localize("tooltip.nhc.details.shift2") +  "+" + StringUtils.localize("tooltip.tow.mouse.wheel"), StringUtils.YELLOW, StringUtils.ITALIC) + " " + StringUtils.localize("tooltip.tow.mode.change"));
			list.add(StringUtils.localize("tooltip.tow.mode") + ": " + StringUtils.format(StringUtils.localize("tooltip.tow.mode." + getWrenchMode(stack).getName()), StringUtils.YELLOW, StringUtils.ITALIC));
			
			getWrenchMode(stack).addInformation(stack, player, list, flag);
		}
		else list.add(StringUtils.shiftForInfo);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addHudInfo(ItemStack stack, EntityPlayer player, List list, boolean isArmor)
	{
		list.add(StringUtils.format(this.getItemStackDisplayName(stack), StringUtils.YELLOW, StringUtils.ITALIC));
		list.add(StringUtils.localize("tooltip.tow.mode") + ": " + StringUtils.format(StringUtils.localize("tooltip.tow.mode." + getWrenchMode(stack).getName()), StringUtils.YELLOW, StringUtils.ITALIC));
	}

	@Override
	public void addToolStationInfo(ItemStack stack, List list)
	{
		list.add(StringUtils.localize("tooltip.nhc.toolstation.chroma") + ": " + StringUtils.format(getStackInSlot(stack, 0).getDisplayName(), StringUtils.YELLOW, StringUtils.ITALIC));
		String shader = (getStackInSlot(stack, 1) != null ? getStackInSlot(stack, 1).getDisplayName() : StringUtils.localize("tooltip.tow.shader.none"));
		list.add(StringUtils.localize("tooltip.tow.toolstation.shader") + ": " + StringUtils.format(shader, StringUtils.YELLOW, StringUtils.ITALIC));
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
	
	@Override
	public String getQualityColor(ItemStack stack)
	{
		return StringUtils.ORANGE;
	}

	@Override
	public void addWidgets(ItemStack stack, List<WidgetBase> list)
	{
		list.add(new WidgetWrench());
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
	
	/* =========================================================== Usage Code ===============================================================*/
	
	@Override
	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
	{
		if (hand == EnumHand.OFF_HAND) return EnumActionResult.PASS;
	    IBlockState bs = world.getBlockState(pos);
	    Block block = bs.getBlock();
	    ItemStack stack = player.getHeldItem(hand);
	    
	    for (int i = 0; i < WrenchRegistry.getCount(); ++i)
	    {
	    	EnumActionResult result = WrenchRegistry.getHandler(i).onWrenchUseFirst(this.getWrenchMode(stack), player, world, pos, side, hitX, hitY, hitZ, hand);
	    	if (result != EnumActionResult.PASS) return result;
	    }
	    
	    if (getWrenchMode(stack) == ModItems.modeWrench)
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
	    else if (getWrenchMode(stack) == ModItems.modeRotate)
	    {
			if (block.rotateBlock(world, pos, side))
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
	
	/* =========================================================== Interface: IMouseWheel, IKeyBound ===============================================================*/
	
	@Override
	public void doKeyBindingAction(EntityPlayer entityPlayer, ItemStack itemStack, Key key, boolean chat)
	{
		if (key == Key.SCROLLUP) setMode(itemStack, (getModeAsNumber(itemStack) > 0 ? getModeAsNumber(itemStack) - 1 : ModeRegistry.getSize() - 1));
		else if (key == Key.SCROLLDN) setMode(itemStack, (getModeAsNumber(itemStack) < ModeRegistry.getSize() -1 ? getModeAsNumber(itemStack) + 1 : 0));
		else if (key == Key.TOGGLE && entityPlayer.isSneaking() && getWrenchMode(itemStack) instanceof IKeyBound) ((IKeyBound) getWrenchMode(itemStack)).doKeyBindingAction(entityPlayer, itemStack, key, chat);
		
		if (chat && (key == Key.SCROLLUP || key == Key.SCROLLDN)) entityPlayer.sendMessage(new TextComponentTranslation("tooltip.tow.mode").appendText(": ").appendSibling(new TextComponentTranslation("tooltip.tow.mode." + getWrenchMode(itemStack).getName())));
	}
	
	/* =========================================================== Interface: IInventoryItem ===============================================================*/
		
	@Override
	public int getInventoryX(ItemStack stack)
	{
		return 2;
	}

	@Override
	public int getInventoryY(ItemStack stack)
	{
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(ItemStack stack, int slot)
	{
		if (slot == 0)
		{
			ItemStack slotItem = ItemUtil.getItemFromStack(stack, "CHROMA");
			if (slotItem == null || (slotItem != null && !(slotItem.getItem() instanceof ItemChroma))) slotItem = ItemNBTHelper.setString(new ItemStack(com.nhave.nhc.registry.ModItems.itemChroma), "CHROMAS", "CHROMA", "white");
			return slotItem;
		}
		else if (slot == 1)
		{
			ItemStack slotItem = ItemUtil.getItemFromStack(stack, "SHADER");
			if (slotItem == null || (slotItem != null && !(slotItem.getItem() instanceof ItemShader))) slotItem = null;
			return slotItem;
		}
		return null;
	}
	
	/* =========================================================== Interface: INHWrench ===============================================================*/
	
	@Override
	public boolean canItemWrench(EntityPlayer player, int x, int y, int z)
	{
	    return getWrenchMode(player.getHeldItemMainhand()) == ModItems.modeWrench;
	}
	
	@Override
	public void onWrenchUsed(EntityPlayer player, int x, int y, int z)
	{
		player.swingArm(EnumHand.MAIN_HAND);
	}
	
	/* =========================================================== Interface: IToolHammer ===============================================================*/
	
	@Override
	public boolean isUsable(ItemStack item, EntityLivingBase user, BlockPos pos)
	{
		return getWrenchMode(item) == ModItems.modeWrench;
	}
	
	@Override
	public boolean isUsable(ItemStack item, EntityLivingBase user, Entity entity)
	{
		return getWrenchMode(item) == ModItems.modeWrench;
	}
	
	@Override
	public void toolUsed(ItemStack item, EntityLivingBase user, BlockPos pos)
	{
		user.swingArm(EnumHand.MAIN_HAND);
	}
	
	@Override
	public void toolUsed(ItemStack item, EntityLivingBase user, Entity entity)
	{
		user.swingArm(EnumHand.MAIN_HAND);
	}
	
	/* =========================================================== Interface: Wrench ===============================================================*/
	
	@Override
	public boolean useWrenchOnBlock(EntityPlayer player, World world, BlockPos pos, boolean simulate)
	{
		return getWrenchMode(player.getHeldItemMainhand()) == ModItems.modeWrench;
	}
	
	/* =========================================================== Interface: IAEWrench ===============================================================*/
	
	@Override
	public boolean canWrench(ItemStack wrench, EntityPlayer player, BlockPos pos)
	{
		return getWrenchMode(wrench) == ModItems.modeWrench;
	}
	
	/* =========================================================== Interface: ITool ===============================================================*/
	
	@Override
	public boolean isTool(ItemStack item)
	{
		return true;
	}
}