package com.nhave.tow.items;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.nhave.nhc.helpers.ItemHelper;
import com.nhave.nhc.helpers.ItemNBTHelper;
import com.nhave.nhc.helpers.TooltipHelper;
import com.nhave.nhc.util.StringUtils;
import com.nhave.tow.api.shaders.Shader;
import com.nhave.tow.registry.ModItems;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemShaderPack extends ItemBase
{
	public static final Random rand = new Random();
	private List<Shader> pool;
	private int amount;
	
	public ItemShaderPack(String name, List<Shader> pool, int amount)
	{
		super(name);
		this.pool = pool;
		this.amount = amount;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		List<Shader> pool = getLootPool();
		if (pool.size() > 0 && !playerIn.getEntityWorld().isRemote)
		{
			if (!playerIn.capabilities.isCreativeMode) playerIn.getHeldItem(handIn).shrink(1);
			List<Shader> newPool = new ArrayList<Shader>();
			for (int i = 0; i < pool.size(); ++i)
			{
				newPool.add(pool.get(i));
			}
			for (int i = 0; i < amount; ++i)
			{
				Shader shader = newPool.get(rand.nextInt(newPool.size()));
				ItemHelper.addItemToPlayer(playerIn, ItemNBTHelper.setString(new ItemStack(ModItems.itemShader), "SHADERS", "SHADER", shader.getShaderName()));
				
				List<Shader> newPool1 = new ArrayList<Shader>();
				for (int j = 0; j < newPool.size(); ++j)
				{
					if (!newPool.get(j).getShaderName().equals(shader.getShaderName())) newPool1.add(newPool.get(j));
				}
				newPool = newPool1;
			}
			return new ActionResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
		}
		
		return new ActionResult(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
	}
	
	public List<Shader> getLootPool()
	{
		return this.pool;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if (StringUtils.isShiftKeyDown())
		{
			TooltipHelper.addSplitString(tooltip, StringUtils.localize("tooltip.tow." + this.getItemName(stack)), ";", StringUtils.GRAY);
		}
		else tooltip.add(StringUtils.shiftForInfo);
	}
}