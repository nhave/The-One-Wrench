package com.nhave.tow.shaders;

import java.util.List;
import java.util.TreeMap;

import com.nhave.tow.registry.ModShaders;

public class ShaderRegistry
{
	public static final TreeMap<String, Shader> SHADERS = new TreeMap<String, Shader>();
    
    /**
     * Registers a new Shader to the registry.
     * 
     * @param id
     *            The String ID of the Shader. 
     * @param shader
     *            The Shader to register.
     */
    public static Shader registerShader(List<Shader> pool, int weight, boolean addAll, Shader shader) 
    {
    	SHADERS.put(shader.getShaderName(), shader);
		pool.add(shader);
    	for (int i = 0; i < weight; ++i)
    	{
        	if (addAll) ModShaders.ALL_SHADERS.add(shader);
    	}
    	return shader;
    }
    
    /**
     * Returns a Shader from its name.
     * 
     * @param name
     *            The name of the Shader.
     */
    public static Shader getShader(String name)
    {
    	Shader result = SHADERS.get(name);
        return result;
    }
    
    /**
     * Returns the amount of registered Shaders.
     */
    public static int getSize()
    {
    	return SHADERS.size();
    }
    
    /**
     * Returns true if no Shaders has been registered.
     */
    public static boolean isEmpty()
    {
    	return SHADERS.isEmpty();
    }
}