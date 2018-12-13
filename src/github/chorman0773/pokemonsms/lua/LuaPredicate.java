package github.chorman0773.pokemonsms.lua;

import java.util.function.Predicate;

import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public class LuaPredicate extends LuaFunction {
	private Predicate<Varargs> predicate;
	private LuaPredicate(Predicate<Varargs> predicate) {
		this.predicate = predicate;
	}
	public LuaValue invoke(Varargs value) {
		return predicate.test(value)?TRUE:FALSE;
	}
	public static LuaPredicate of(Predicate<Varargs> predicate) {
		return new LuaPredicate(predicate);
	}
	public static LuaPredicate forValuePredicate(Predicate<LuaValue> predicate) {
		return new LuaPredicate(v->predicate.test(v.arg1()));
	}
	public static LuaPredicate alwaysTrue() {
		return new LuaPredicate(v->true);
	}
	public static LuaPredicate alwaysFalse() {
		return new LuaPredicate(v->false);
	}
}
