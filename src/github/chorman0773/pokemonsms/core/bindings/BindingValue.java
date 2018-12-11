package github.chorman0773.pokemonsms.core.bindings;

import org.luaj.vm2.LuaUserdata;
import org.luaj.vm2.LuaValue;

public abstract class BindingValue extends LuaUserdata {

	public BindingValue(Object obj) {
		super(obj);
		// TODO Auto-generated constructor stub
	}
	
	public LuaValue get(LuaValue val) {
		if(val.isstring())
			return get(val.checkjstring());
		else
			return LuaValue.NIL;
	}
	public abstract LuaValue get(String val);
	
	public String typename() {
		return checkuserdata().getClass().getSimpleName();
	}

}
