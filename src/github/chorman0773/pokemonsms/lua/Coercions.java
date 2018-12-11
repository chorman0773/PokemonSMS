package github.chorman0773.pokemonsms.lua;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public interface Coercions {
	public static <D extends Delegable<D>> LuaValue coerceToLua(D d) {
		return d.getDelegate();
	}
	public static <E extends Enum<E>> LuaValue coerceToLua(E e) {
		return EnumValue.of(e);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static LuaValue coerceToLua(Object o) {
		if(o.getClass().isEnum())
			return EnumValue.of((Enum)o);//Can't do much about Raw type here
		else if(Delegable.class.isAssignableFrom(o.getClass()))
			return ((Delegable<?>)o).getDelegate();
		return CoerceJavaToLua.coerce(o);
	}
}
