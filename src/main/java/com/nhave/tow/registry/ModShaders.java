package com.nhave.tow.registry;

import java.util.ArrayList;
import java.util.List;

import com.nhave.nhc.util.StringUtils;
import com.nhave.tow.Reference;
import com.nhave.tow.api.TOWAPI;
import com.nhave.tow.api.shaders.Shader;
import com.nhave.tow.shaders.ShaderModeSpecific;
import com.nhave.tow.utils.ColorUtils;

public class ModShaders
{
	public static final List<Shader> ALL_SHADERS = new ArrayList<Shader>();
	public static final List<Shader> BASE_SHADERS = new ArrayList<Shader>();
	public static final List<Shader> RARE_SHADERS = new ArrayList<Shader>();
	public static final List<Shader> EPIC_SHADERS = new ArrayList<Shader>();
	public static final List<Shader> LEGENDARY_SHADERS = new ArrayList<Shader>();
	public static final List<Shader> DESTINY_SHADERS = new ArrayList<Shader>();
	public static final List<Shader> OVERWATCH_SHADERS = new ArrayList<Shader>();
	
	public static void init()
	{
		if (ModConfig.enableAllShaders)
		{
			//Shaders: main
			TOWAPI.shaderRegistry.registerShader(BASE_SHADERS, 1, false, new Shader("main.white", Reference.MODID + ":shaders/main/white", Reference.MODID + ":shader", ColorUtils.white, true));
			TOWAPI.shaderRegistry.registerShader(BASE_SHADERS, 1, false, new Shader("main.black", Reference.MODID + ":shaders/main/black", Reference.MODID + ":shader", ColorUtils.black, true));
			TOWAPI.shaderRegistry.registerShader(LEGENDARY_SHADERS, 1, true, new Shader("main.dalek", Reference.MODID + ":shaders/main/dalek", Reference.MODID + ":shader", ColorUtils.orange, true).setQualityColor(StringUtils.ORANGE));
			TOWAPI.shaderRegistry.registerShader(LEGENDARY_SHADERS, 1, true, new Shader("main.cofh", Reference.MODID + ":shaders/main/cofh", Reference.MODID + ":shader", ColorUtils.lightBlue, true).setQualityColor(StringUtils.ORANGE));
			TOWAPI.shaderRegistry.registerShader(EPIC_SHADERS, 5, true, new Shader("main.nyan", Reference.MODID + ":shaders/main/nyan", Reference.MODID + ":shader", ColorUtils.pink, false).setQualityColor(StringUtils.PURPLE));
			TOWAPI.shaderRegistry.registerShader(RARE_SHADERS, 15, true, new Shader("main.danish", Reference.MODID + ":shaders/main/danish", Reference.MODID + ":shader", ColorUtils.red, false).setQualityColor(StringUtils.LIGHT_BLUE));
			TOWAPI.shaderRegistry.registerShader(LEGENDARY_SHADERS, 1, true, new Shader("main.botany", Reference.MODID + ":shaders/main/botany", Reference.MODID + ":shader", ColorUtils.green, false).setQualityColor(StringUtils.ORANGE));
			TOWAPI.shaderRegistry.registerShader(LEGENDARY_SHADERS, 1, true, new Shader("main.taint", Reference.MODID + ":shaders/main/taint", Reference.MODID + ":shader", ColorUtils.purple, false).setQualityColor(StringUtils.ORANGE));
			TOWAPI.shaderRegistry.registerShader(EPIC_SHADERS, 5, true, new Shader("main.aeonic", Reference.MODID + ":shaders/main/aeonic", Reference.MODID + ":shader", ColorUtils.lightBlue, false).setQualityColor(StringUtils.PURPLE));
			TOWAPI.shaderRegistry.registerShader(EPIC_SHADERS, 5, true, new ShaderModeSpecific("main.prototype", Reference.MODID + ":shaders/main/prototype", Reference.MODID + ":shader", ColorUtils.lime, false).setQualityColor(StringUtils.PURPLE));
			TOWAPI.shaderRegistry.registerShader(LEGENDARY_SHADERS, 1, true, new Shader("main.millenium", Reference.MODID + ":shaders/main/millenium", Reference.MODID + ":shader", ColorUtils.lightGray, false).setQualityColor(StringUtils.ORANGE));
			TOWAPI.shaderRegistry.registerShader(EPIC_SHADERS, 5, true,  new Shader("main.gold",Reference.MODID + ":shaders/main/gold", Reference.MODID + ":shader", ColorUtils.orange, false).setQualityColor(StringUtils.PURPLE));
			TOWAPI.shaderRegistry.registerShader(BASE_SHADERS, 1, false, new Shader("main.vibrantred", Reference.MODID + ":shaders/main/vibrantred", Reference.MODID + ":shader", ColorUtils.red, true));
			TOWAPI.shaderRegistry.registerShader(BASE_SHADERS, 1, false, new Shader("main.vibrantgreen", Reference.MODID + ":shaders/main/vibrantgreen", Reference.MODID + ":shader", ColorUtils.green, true));
			TOWAPI.shaderRegistry.registerShader(BASE_SHADERS, 1, false, new Shader("main.vibrantblue", Reference.MODID + ":shaders/main/vibrantblue", Reference.MODID + ":shader", ColorUtils.blue, true));
			TOWAPI.shaderRegistry.registerShader(LEGENDARY_SHADERS, 1, true, new Shader("main.bound", Reference.MODID + ":shaders/main/bound", Reference.MODID + ":shader", ColorUtils.yellow, false).setArtist("Voxel_Friend").setQualityColor(StringUtils.ORANGE));
			TOWAPI.shaderRegistry.registerShader(EPIC_SHADERS, 5, true, new ShaderModeSpecific("main.nhave", Reference.MODID + ":shaders/main/nhave", Reference.MODID + ":shader", ColorUtils.red, false).setQualityColor(StringUtils.PURPLE));
			TOWAPI.shaderRegistry.registerShader(EPIC_SHADERS, 5, true, new Shader("main.future", Reference.MODID + ":shaders/main/future", Reference.MODID + ":shader", ColorUtils.lightBlue, false).setQualityColor(StringUtils.PURPLE));
			TOWAPI.shaderRegistry.registerShader(RARE_SHADERS, 15, true, new Shader("main.nvidia", Reference.MODID + ":shaders/main/nvidia", Reference.MODID + ":shader", ColorUtils.lime, false).setQualityColor(StringUtils.LIGHT_BLUE));
			TOWAPI.shaderRegistry.registerShader(RARE_SHADERS, 15, true, new Shader("main.temple", Reference.MODID + ":shaders/main/temple", Reference.MODID + ":shader", 4418111, false).setQualityColor(StringUtils.LIGHT_BLUE));
			TOWAPI.shaderRegistry.registerShader(EPIC_SHADERS, 5, true, new Shader("main.steam", Reference.MODID + ":shaders/main/steam", Reference.MODID + ":shader", ColorUtils.yellow, false).setQualityColor(StringUtils.PURPLE));
			TOWAPI.shaderRegistry.registerShader(RARE_SHADERS, 15, true, new Shader("main.team", Reference.MODID + ":shaders/main/team", Reference.MODID + ":shader", ColorUtils.white, true).setQualityColor(StringUtils.LIGHT_BLUE));
			TOWAPI.shaderRegistry.registerShader(RARE_SHADERS, 15, true, new Shader("main.bugfix", Reference.MODID + ":shaders/main/bugfix", Reference.MODID + ":shader", ColorUtils.yellow, false).setQualityColor(StringUtils.LIGHT_BLUE));
			TOWAPI.shaderRegistry.registerShader(EPIC_SHADERS, 5, true, new Shader("main.scorpion", Reference.MODID + ":shaders/main/scorpion", Reference.MODID + ":shader", ColorUtils.black, false).setQualityColor(StringUtils.PURPLE));
			TOWAPI.shaderRegistry.registerShader(RARE_SHADERS, 15, true, new Shader("main.highend", Reference.MODID + ":shaders/main/highend", Reference.MODID + ":shader", ColorUtils.gray, false).setQualityColor(StringUtils.LIGHT_BLUE));
			TOWAPI.shaderRegistry.registerShader(LEGENDARY_SHADERS, 1, true, new Shader("main.mario", Reference.MODID + ":shaders/main/mario", Reference.MODID + ":shader", ColorUtils.red, false).setQualityColor(StringUtils.ORANGE));
			TOWAPI.shaderRegistry.registerShader(LEGENDARY_SHADERS, 1, true, new Shader("main.luigi", Reference.MODID + ":shaders/main/luigi", Reference.MODID + ":shader", ColorUtils.green, false).setQualityColor(StringUtils.ORANGE));
			TOWAPI.shaderRegistry.registerShader(LEGENDARY_SHADERS, 1, true, new Shader("main.underwater", Reference.MODID + ":shaders/main/underwater", Reference.MODID + ":shader", ColorUtils.orange, false).setQualityColor(StringUtils.ORANGE));
			TOWAPI.shaderRegistry.registerShader(LEGENDARY_SHADERS, 1, true, new Shader("main.fluxed", Reference.MODID + ":shaders/main/fluxed", Reference.MODID + ":shader", 12955724, false).setArtist("Voxel_Friend").setQualityColor(StringUtils.ORANGE));
			TOWAPI.shaderRegistry.registerShader(LEGENDARY_SHADERS, 1, true, new Shader("main.cryostatic", Reference.MODID + ":shaders/main/cryostatic", Reference.MODID + ":shader", 603960, false).setArtist("Voxel_Friend").setQualityColor(StringUtils.ORANGE));
			TOWAPI.shaderRegistry.registerShader(RARE_SHADERS, 15, true, new Shader("main.racer", Reference.MODID + ":shaders/main/racer", Reference.MODID + ":shader", 4980736, false).setQualityColor(StringUtils.LIGHT_BLUE));
			TOWAPI.shaderRegistry.registerShader(RARE_SHADERS, 15, true, new Shader("main.crystals", Reference.MODID + ":shaders/main/crystals", Reference.MODID + ":shader", 10149341, false).setQualityColor(StringUtils.LIGHT_BLUE));
			TOWAPI.shaderRegistry.registerShader(LEGENDARY_SHADERS, 1, true, new ShaderModeSpecific("main.gen4", Reference.MODID + ":shaders/main/gen4", Reference.MODID + ":shader", ColorUtils.white, true).setQualityColor(StringUtils.ORANGE));
			TOWAPI.shaderRegistry.registerShader(LEGENDARY_SHADERS, 1, true, new Shader("main.zerodawn", Reference.MODID + ":shaders/main/zerodawn", Reference.MODID + ":shader", 6367010, false).setQualityColor(StringUtils.ORANGE));
			TOWAPI.shaderRegistry.registerShader(LEGENDARY_SHADERS, 1, true, new Shader("main.immersive", Reference.MODID + ":shaders/main/immersive", Reference.MODID + ":shader", 5450521, false).setArtist("Voxel_Friend").setQualityColor(StringUtils.ORANGE));
			TOWAPI.shaderRegistry.registerShader(LEGENDARY_SHADERS, 1, true, new ShaderModeSpecific("main.modularium", Reference.MODID + ":shaders/main/modularium", Reference.MODID + ":shader", 16731648, false).setQualityColor(StringUtils.ORANGE));
			
			//Shaders: destiny
			if (ModConfig.enableDestinyShaders)
			{
				TOWAPI.shaderRegistry.registerShader(DESTINY_SHADERS, 1, false, new Shader("destiny.newmonarchy", Reference.MODID + ":shaders/destiny/newmonarchy", Reference.MODID + ":shaders/destiny/shader", ColorUtils.red, false).setQualityColor(StringUtils.PURPLE));
				TOWAPI.shaderRegistry.registerShader(DESTINY_SHADERS, 1, false, new Shader("destiny.deadorbit", Reference.MODID + ":shaders/destiny/deadorbit", Reference.MODID + ":shaders/destiny/shader", ColorUtils.black, false).setQualityColor(StringUtils.PURPLE));
				TOWAPI.shaderRegistry.registerShader(DESTINY_SHADERS, 1, false, new Shader("destiny.warcult", Reference.MODID + ":shaders/destiny/warcult", Reference.MODID + ":shaders/destiny/shader", ColorUtils.orange, false).setQualityColor(StringUtils.PURPLE));
				TOWAPI.shaderRegistry.registerShader(DESTINY_SHADERS, 1, false, new Shader("destiny.taken", Reference.MODID + ":shaders/destiny/taken", Reference.MODID + ":shaders/destiny/shader", ColorUtils.black, false).setQualityColor(StringUtils.PURPLE));
				TOWAPI.shaderRegistry.registerShader(DESTINY_SHADERS, 1, false, new Shader("destiny.harrowed", Reference.MODID + ":shaders/destiny/harrowed", Reference.MODID + ":shaders/destiny/shader", ColorUtils.black, false).setQualityColor(StringUtils.PURPLE));
				TOWAPI.shaderRegistry.registerShader(DESTINY_SHADERS, 1, false, new Shader("destiny.vanguard", Reference.MODID + ":shaders/destiny/vanguard", Reference.MODID + ":shaders/destiny/shader", ColorUtils.orange, false).setQualityColor(StringUtils.PURPLE));
				TOWAPI.shaderRegistry.registerShader(DESTINY_SHADERS, 1, false, new Shader("destiny.crucible", Reference.MODID + ":shaders/destiny/crucible", Reference.MODID + ":shaders/destiny/shader", ColorUtils.lime, false).setQualityColor(StringUtils.PURPLE));
				TOWAPI.shaderRegistry.registerShader(DESTINY_SHADERS, 1, false, new Shader("destiny.suroswhite", Reference.MODID + ":shaders/destiny/suroswhite", Reference.MODID + ":shaders/destiny/shader", ColorUtils.white, false).setQualityColor(StringUtils.PURPLE));
				TOWAPI.shaderRegistry.registerShader(DESTINY_SHADERS, 1, false, new Shader("destiny.surosblack", Reference.MODID + ":shaders/destiny/surosblack", Reference.MODID + ":shaders/destiny/shader", ColorUtils.black, false).setQualityColor(StringUtils.PURPLE));
				TOWAPI.shaderRegistry.registerShader(DESTINY_SHADERS, 1, false, new Shader("destiny.queensguard", Reference.MODID + ":shaders/destiny/queensguard", Reference.MODID + ":shaders/destiny/shader", ColorUtils.purple, true).setQualityColor(StringUtils.PURPLE));
				TOWAPI.shaderRegistry.registerShader(DESTINY_SHADERS, 1, false, new Shader("destiny.wolfclaw", Reference.MODID + ":shaders/destiny/wolfclaw", Reference.MODID + ":shaders/destiny/shader", ColorUtils.brown, true).setQualityColor(StringUtils.PURPLE));
				TOWAPI.shaderRegistry.registerShader(DESTINY_SHADERS, 1, false, new Shader("destiny.vex", Reference.MODID + ":shaders/destiny/vex", Reference.MODID + ":shaders/destiny/shader",13942416, false).setQualityColor(StringUtils.PURPLE));
				TOWAPI.shaderRegistry.registerShader(DESTINY_SHADERS, 1, false, new Shader("destiny.hive", Reference.MODID + ":shaders/destiny/hive", Reference.MODID + ":shaders/destiny/shader", ColorUtils.lime, false).setQualityColor(StringUtils.PURPLE));
				TOWAPI.shaderRegistry.registerShader(DESTINY_SHADERS, 1, false, new Shader("destiny.veist", Reference.MODID + ":shaders/destiny/veist", Reference.MODID + ":shaders/destiny/shader", 3816244, false).setQualityColor(StringUtils.PURPLE));
				TOWAPI.shaderRegistry.registerShader(DESTINY_SHADERS, 1, false, new Shader("destiny.omolon", Reference.MODID + ":shaders/destiny/omolon", Reference.MODID + ":shaders/destiny/shader", 8496314, false).setQualityColor(StringUtils.PURPLE));
				TOWAPI.shaderRegistry.registerShader(DESTINY_SHADERS, 1, false, new Shader("destiny.hakke", Reference.MODID + ":shaders/destiny/hakke", Reference.MODID + ":shaders/destiny/shader", 11038807, false).setQualityColor(StringUtils.PURPLE));
				TOWAPI.shaderRegistry.registerShader(DESTINY_SHADERS, 1, false, new Shader("destiny.siva", Reference.MODID + ":shaders/destiny/siva", Reference.MODID + ":shaders/destiny/shader", ColorUtils.red, false).setQualityColor(StringUtils.PURPLE));
				TOWAPI.shaderRegistry.registerShader(DESTINY_SHADERS, 1, false, new Shader("destiny.surosv2", Reference.MODID + ":shaders/destiny/surosv2", Reference.MODID + ":shaders/destiny/shader", ColorUtils.white, false).setQualityColor(StringUtils.PURPLE));
			}

			//Shaders: overwatch
			if (ModConfig.enableOverwatchShaders)
			{
				TOWAPI.shaderRegistry.registerShader(OVERWATCH_SHADERS, 1, false, new Shader("overwatch.dva", Reference.MODID + ":shaders/overwatch/dva", Reference.MODID + ":shaders/overwatch/shader", 16720043, false).setQualityColor(StringUtils.PURPLE));
				TOWAPI.shaderRegistry.registerShader(OVERWATCH_SHADERS, 1, false, new Shader("overwatch.bastion", Reference.MODID + ":shaders/overwatch/bastion", Reference.MODID + ":shaders/overwatch/shader", 14465924, false).setQualityColor(StringUtils.PURPLE));
				TOWAPI.shaderRegistry.registerShader(OVERWATCH_SHADERS, 1, false, new Shader("overwatch.tracer", Reference.MODID + ":shaders/overwatch/tracer", Reference.MODID + ":shaders/overwatch/shader", 11075583, false).setQualityColor(StringUtils.PURPLE));
				TOWAPI.shaderRegistry.registerShader(OVERWATCH_SHADERS, 1, false, new Shader("overwatch.widowmaker", Reference.MODID + ":shaders/overwatch/widowmaker", Reference.MODID + ":shaders/overwatch/shader", 4205895, false).setQualityColor(StringUtils.PURPLE));
				TOWAPI.shaderRegistry.registerShader(OVERWATCH_SHADERS, 1, false, new Shader("overwatch.pharah", Reference.MODID + ":shaders/overwatch/pharah", Reference.MODID + ":shaders/overwatch/shader", 16758274, false).setQualityColor(StringUtils.PURPLE));
				TOWAPI.shaderRegistry.registerShader(OVERWATCH_SHADERS, 1, false, new Shader("overwatch.reinhardt", Reference.MODID + ":shaders/overwatch/reinhardt", Reference.MODID + ":shaders/overwatch/shader", 16769024, false).setQualityColor(StringUtils.PURPLE));
				TOWAPI.shaderRegistry.registerShader(OVERWATCH_SHADERS, 1, false, new Shader("overwatch.reaper", Reference.MODID + ":shaders/overwatch/reaper", Reference.MODID + ":shaders/overwatch/shader", 14269568, false).setQualityColor(StringUtils.PURPLE));
				TOWAPI.shaderRegistry.registerShader(OVERWATCH_SHADERS, 1, false, new Shader("overwatch.symmetra", Reference.MODID + ":shaders/overwatch/symmetra", Reference.MODID + ":shaders/overwatch/shader", 8577272, false).setQualityColor(StringUtils.PURPLE));
				TOWAPI.shaderRegistry.registerShader(OVERWATCH_SHADERS, 1, false, new Shader("overwatch.lucio", Reference.MODID + ":shaders/overwatch/lucio", Reference.MODID + ":shaders/overwatch/shader", 4980600, false).setQualityColor(StringUtils.PURPLE));
				TOWAPI.shaderRegistry.registerShader(OVERWATCH_SHADERS, 1, false, new Shader("overwatch.sombra", Reference.MODID + ":shaders/overwatch/sombra", Reference.MODID + ":shaders/overwatch/shader", 2829662, false).setQualityColor(StringUtils.PURPLE));
				TOWAPI.shaderRegistry.registerShader(OVERWATCH_SHADERS, 1, false, new Shader("overwatch.doomfist", Reference.MODID + ":shaders/overwatch/doomfist", Reference.MODID + ":shaders/overwatch/shader", 16774585, false).setQualityColor(StringUtils.PURPLE));
				TOWAPI.shaderRegistry.registerShader(OVERWATCH_SHADERS, 1, false, new Shader("overwatch.mercy", Reference.MODID + ":shaders/overwatch/mercy", Reference.MODID + ":shaders/overwatch/shader", 16760410, false).setQualityColor(StringUtils.PURPLE));
				TOWAPI.shaderRegistry.registerShader(OVERWATCH_SHADERS, 1, false, new Shader("overwatch.dvav2", Reference.MODID + ":shaders/overwatch/dvav2", Reference.MODID + ":shaders/overwatch/shader", 16720043, false).setQualityColor(StringUtils.PURPLE));
			}
		}
	}
}