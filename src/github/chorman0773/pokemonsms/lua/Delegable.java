package github.chorman0773.pokemonsms.lua;


/**
 * Base type of Concrete Non-Lua Types that can be Converted to a LuaValue.
 * @author connor
 * @param <D> Restriction Type Parameter to prevent using other type's delegates
 */
public interface Delegable<D extends Delegable<D>> {
	public Delegate<D> getDelegate();
}
