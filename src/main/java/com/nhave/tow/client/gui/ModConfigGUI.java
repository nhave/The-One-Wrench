package com.nhave.tow.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.nhave.tow.Reference;
import com.nhave.tow.registry.ModConfig;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.DummyConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.IConfigElement;

public class ModConfigGUI extends GuiConfig
{
    public ModConfigGUI(GuiScreen parent)
    {
        super(parent, getConfigElements(), Reference.MODID, false, false, I18n.translateToLocal("cfg.tow.title.main"));
    }
    
    private static List<IConfigElement> getConfigElements()
    {
		List list = new ArrayList();
		list.add(new DummyConfigElement.DummyCategoryElement("cfg.tow.entry.common", "cfg.tow.entry.common", CommonEntry.class));
		list.add(new DummyConfigElement.DummyCategoryElement("cfg.tow.entry.integration", "cfg.tow.entry.integration", IntegrationEntry.class));
		return list;
    }
    
    public static class CommonEntry extends GuiConfigEntries.CategoryEntry
    {
		public CommonEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop)
		{
			super(owningScreen, owningEntryList, prop);
		}

		protected GuiScreen buildChildScreen()
		{
			return new GuiConfig(this.owningScreen,
					new ConfigElement(ModConfig.config.getCategory("common")).getChildElements(),
					this.owningScreen.modID, "common",
					(this.configElement.requiresWorldRestart())
							|| (this.owningScreen.allRequireWorldRestart),
					(this.configElement.requiresMcRestart())
							|| (this.owningScreen.allRequireMcRestart),
					GuiConfig.getAbridgedConfigPath(I18n.translateToLocal("cfg.tow.title.common")));
		}
	}
    
    public static class IntegrationEntry extends GuiConfigEntries.CategoryEntry
    {
		public IntegrationEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList, IConfigElement prop)
		{
			super(owningScreen, owningEntryList, prop);
		}

		protected GuiScreen buildChildScreen()
		{
			return new GuiConfig(this.owningScreen,
					new ConfigElement(ModConfig.config.getCategory("integration")).getChildElements(),
					this.owningScreen.modID, "integration",
					(this.configElement.requiresWorldRestart())
							|| (this.owningScreen.allRequireWorldRestart),
					(this.configElement.requiresMcRestart())
							|| (this.owningScreen.allRequireMcRestart),
					GuiConfig.getAbridgedConfigPath(I18n.translateToLocal("cfg.tow.title.integration")));
		}
	}
}