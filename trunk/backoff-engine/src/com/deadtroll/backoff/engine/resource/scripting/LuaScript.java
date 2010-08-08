package com.deadtroll.backoff.engine.resource.scripting;

import java.io.File;
import java.io.FileInputStream;

import org.luaj.compiler.LuaC;
import org.luaj.lib.j2se.CoerceJavaToLua;
import org.luaj.platform.J2sePlatform;
import org.luaj.vm.LClosure;
import org.luaj.vm.LPrototype;
import org.luaj.vm.LValue;
import org.luaj.vm.LoadState;
import org.luaj.vm.LuaState;
import org.luaj.vm.Platform;

import com.deadtroll.backoff.engine.resource.AbstractResource;

public class LuaScript extends AbstractResource {
	private LuaState luaState;
	
	public LuaScript(String name, String file) {
		super(name, file);
	}

	public Object[] doCall(String functionName, Object... params /*TODO number of return values*/) {
		Object[] ret = null;
		this.luaState.getglobal(functionName);
		
		int numParams=0;		
		if (params !=  null) {
			for (Object param : params) {
				this.luaState.pushlvalue(CoerceJavaToLua.coerce(param));
				numParams++;
			}
		}
		this.luaState.call(numParams,0);
		
		//TODO get return values
		return ret;
	}

	public void load() throws Exception {
		Platform.setInstance(new J2sePlatform());
        LuaC.install();
		this.luaState = Platform.newLuaState();
		File f = new File(this.file);
		FileInputStream fis = new FileInputStream(f);
		LPrototype p = LoadState.undump(this.luaState, fis, this.file);
		LClosure c = p.newClosure( this.luaState._G );
		this.luaState.doCall(c, new LValue[0]);
	}

	public void unload() {
		this.luaState.shutdown();		
	}
}
