package github.chorman0773.pokemonsms.lua;

import org.luaj.vm2.LuaUserdata;
import org.luaj.vm2.LuaValue;


public abstract class Delegate<D extends Delegable<D>> extends LuaUserdata {
	private Class<? extends D> cl;
	public Delegate(D obj,Class<? extends D> cl) {
		super(obj);
		this.cl = cl;
	}
	public  D checkuserdata() {
		return (D)checkuserdata();
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public D checkuserdata(Class cl) {
		return (D)super.checkuserdata(cl);
	}
	
	public String typename() {
		return cl.getSimpleName();
	}
	
	public LuaValue get(String name) {
		return LuaValue.NIL;
	}
	public LuaValue get(LuaValue val) {
		if(val.isstring())
			return get(val.checkjstring());
		return LuaValue.NIL;
	}
	
}
