package github.chorman0773.pokemonsms.client;

import org.luaj.vm2.LuaValue;

import github.chorman0773.pokemonsms.core.EventBus;
import github.chorman0773.pokemonsms.core.IRegistryEntry.Impl;
import github.chorman0773.pokemonsms.core.LuaEventBus;
import github.chorman0773.pokemonsms.lua.Delegable;
import github.chorman0773.pokemonsms.lua.Delegate;

public class Tile extends Impl<Tile> implements Delegable<Tile> {
	private int flags;
	private EventBus bus;
	protected Tile(LuaValue val,int flags,EventBus bus) {
		super(val);
		this.flags = flags;
		this.bus = bus;
	}
	public Tile(LuaValue val) {
		this(val,val.get("flags").checkint(),new LuaEventBus(val.get("bus")));
		// TODO Auto-generated constructor stub
	}

	@Override
	public Class<Tile> getType() {
		// TODO Auto-generated method stub
		return Tile.class;
	}

	@Override
	public Delegate<Tile> getDelegate() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean inBackground() {
		return (flags&0x01)!=0;
	}
	public boolean hasCollision() {
		return (flags&0x02)!=0;
	}
	public boolean isDrawable() {
		return (flags&0x04)!=0;
	}
	
	public EventBus getEventBus() {
		return bus;
	}

}
