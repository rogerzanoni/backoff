package com.deadtroll.backoff.engine.model;

import com.deadtroll.backoff.engine.manager.ScriptManager;
import com.deadtroll.backoff.engine.scripting.LuaScript;

public abstract class ScriptedGameObject extends AbstractGameObject {
	protected LuaScript luaScript;
	protected String scriptName;
	
	public ScriptedGameObject(String scriptName) {
		this.scriptName = scriptName;
		luaScript = ScriptManager.getInstance().getScript(this.scriptName);
	}
	
	public Object[] call(String functionName /*TODO return values*/) {
		return luaScript.doCall(functionName, this); // always pass self reference
	}
	
	public Object[] call(String functionName, Object[] params /*TODO number of return values*/) {		
		return luaScript.doCall(functionName, this, params); // always pass self reference
	}

	public String getScriptName() {
		return scriptName;
	}

	public LuaScript getLuaScript() {
		return luaScript;
	}
}
