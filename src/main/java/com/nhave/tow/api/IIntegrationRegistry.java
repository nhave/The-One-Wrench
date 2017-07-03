package com.nhave.tow.api;

import com.nhave.tow.api.integration.WrenchHandler;

public interface IIntegrationRegistry
{
	/**
	 * Registers a {@link WrenchHandler} and adds the name to the list of Integrations.
	 * 
	 * @param handler
	 * @param name
	 */
	public void register(WrenchHandler handler, String name);
	
	/**
	 * Registers a {@link WrenchHandler} without adding the name to the list of Integrations.
	 * 
	 * @param handler
	 */
	public void register(WrenchHandler handler);
	
	/**
	 * Just adds the name to the list of Integrations.
	 * 
	 * @param name
	 */
	public void register(String name);
}