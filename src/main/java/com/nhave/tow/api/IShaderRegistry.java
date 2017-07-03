package com.nhave.tow.api;

import java.util.List;

import com.nhave.tow.api.shaders.Shader;

public interface IShaderRegistry
{
	/**
	 * Registers a {@link Shader}.
	 * 
	 * @param pool The Lootpool to add the {@link Shader} to, can be <b>null</b>.
	 * @param weight How likely for the {@link Shader} to drop.
	 * @param addAll Add the {@link Shader} to the Global Lootpool.
	 * @param shader The {@link Shader} to be registered.
	 * @return The {@link Shader} registered.
	 */
	public Shader registerShader(List<Shader> pool, int weight, boolean addAll, Shader shader);
}