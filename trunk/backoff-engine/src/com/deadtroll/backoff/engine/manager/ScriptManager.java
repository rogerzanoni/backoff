package com.deadtroll.backoff.engine.manager;

import java.util.Hashtable;

import com.deadtroll.backoff.engine.scripting.LuaScript;

public class ScriptManager {
	private static ScriptManager instance;
	private Hashtable<String, LuaScript> scripts;
	
	private ScriptManager() {
		this.scripts = new Hashtable<String, LuaScript>();
	}

	public static ScriptManager getInstance() {
		if (instance == null)
			instance = new ScriptManager();
		return instance;
	}
	
	public void loadScript(String scriptName, String scriptFile) {
		if (!this.scripts.contains(scriptName)) {
			this.scripts.put(scriptName, new LuaScript(scriptFile));
		}
	}
	
	public Object[] doCall(String scriptName, String functionName, Object[] params /*TODO number of return values*/) {
		LuaScript luaScript = this.scripts.get(scriptName);
		if (luaScript == null)
			return null;
		return luaScript.doCall(functionName, params);
	}
	
	public LuaScript getScript(String scriptName) {
		LuaScript luaScript = this.scripts.get(scriptName);
		return luaScript;
	}
}
