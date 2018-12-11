package github.chorman0773.pokemonsms.core.bindings;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;

public abstract class Binding extends TwoArgFunction {

	@Override
	public LuaValue call(LuaValue arg1, LuaValue arg2) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public LuaValue get(LuaValue key) {
		if(key.isstring())
			return get(key.tojstring());
		else
			return NIL;
	}

	@Override
	public abstract LuaValue get(String key);

}
