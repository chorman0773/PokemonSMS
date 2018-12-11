package github.chorman0773.pokemonsms.lua;

import org.luaj.vm2.LuaUserdata;
import org.luaj.vm2.LuaValue;

import github.lightningcreations.lclib.Hash;

public class EnumValue<E extends Enum<E>> extends LuaUserdata {
	private Class<E> cl;
	public static <E extends Enum<E>> EnumValue<E> of(E val){
		return new EnumValue<>(val);
	}

	public EnumValue(E obj) {
		super(obj);
		cl = obj.getDeclaringClass();
	}

	@SuppressWarnings("unchecked")
	public E getValue() {
		return (E)m_instance;
	}
	
	@Override
	public String typename() {
		return cl.getSimpleName();
	}

	@Override
	public LuaValue eq(LuaValue val) {
		// TODO Auto-generated method stub
		return eq_b(val)?TRUE:FALSE;
	}

	@Override
	public boolean eq_b(LuaValue val) {
		if(val.isuserdata(cl))
			return getValue().equals(val.checkuserdata(cl));
		else
			return false;
	}

	@Override
	public boolean raweq(LuaValue val) {
		// TODO Auto-generated method stub
		return eq_b(val);
	}
	
	@Override
	public int hashCode() {
		return Hash.hashcode(getValue().name());
	}
	
	@Override
	public String toString() {
		return getValue().name();
	}
	
	@Override
	public String tojstring() {
		return toString();
	}
	
	public boolean equals(Object o) {
		if(o==null)
			return false;
		else if(o==this)
			return true;
		else if(o.getClass()!=EnumValue.class)
			return false;
		else
			return eq_b((LuaValue)o);
	}

}
