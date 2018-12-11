package github.chorman0773.pokemonsms.core;

import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class LuaEventBus implements EventBus {
	private LuaValue bus;
	private LuaValue handlerFunction;
	public LuaEventBus(LuaValue bus) {
		this.bus = bus;
		this.handlerFunction = bus.get("fireEvent");
	}

	@Override
	public void raise(int key, Object... parameters) {
		LuaValue[] vals = new LuaValue[parameters.length+2];
		vals[0] = bus;
		vals[1] = LuaInteger.valueOf(key);
		for(int i =0;i<parameters.length;i++)
			vals[i+2] = CoerceJavaToLua.coerce(parameters[i]);
		handlerFunction.invoke(vals);
	}

}
