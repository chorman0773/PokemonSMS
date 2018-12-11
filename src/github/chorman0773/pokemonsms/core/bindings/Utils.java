package github.chorman0773.pokemonsms.core.bindings;

import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;

import github.chorman0773.pokemonsms.core.EnumType;

public class Utils extends Binding {
	private static class GetTypeName extends OneArgFunction{
		@Override
		public LuaValue call(LuaValue arg) {
			if(arg.isuserdata(EnumType.class))
				return LuaString.valueOf(((EnumType)arg.checkuserdata(EnumType.class)).toString());
			return LuaString.valueOf(EnumType.values()[arg.checkint()].toString());
		}
	}
	private final GetTypeName getTypeName = new GetTypeName();
	public Utils() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public LuaValue get(String key) {
		switch(key) {
		case "getTypeName":return getTypeName;
		}
		return NIL;
	}

}
