package com.deadtroll.backoff.engine.scripting;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.luaj.compiler.LuaC;
import org.luaj.lib.j2se.CoerceJavaToLua;
import org.luaj.platform.J2sePlatform;
import org.luaj.vm.LClosure;
import org.luaj.vm.LPrototype;
import org.luaj.vm.LValue;
import org.luaj.vm.LoadState;
import org.luaj.vm.LuaState;
import org.luaj.vm.Platform;

public class LuaScript {
	private LuaState luaState;
	
	public LuaScript(String script) {
		try {
			loadScript(script);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadScript(final String fileName) throws IOException {
		Platform.setInstance(new J2sePlatform());
        LuaC.install();
		this.luaState = Platform.newLuaState();
		File f = new File(fileName);
		FileInputStream fis = new FileInputStream(f);
		LPrototype p = LoadState.undump(this.luaState, fis, fileName);
		LClosure c = p.newClosure( this.luaState._G );
		this.luaState.doCall(c, new LValue[0]);
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
	
	public void shutdown() {
		this.luaState.shutdown();
	}
}
