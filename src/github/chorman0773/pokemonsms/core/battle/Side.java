package github.chorman0773.pokemonsms.core.battle;

import github.chorman0773.pokemonsms.core.EventBus;
import github.chorman0773.pokemonsms.lua.Delegable;
import github.chorman0773.pokemonsms.lua.Delegate;

public abstract class Side implements EventBus, Delegable<Side> {
	private final Battle owner;
	private final Side[] team;
	
	public Side(Battle owner,Side[] team) {
		this.owner = owner;
		this.team = team;
	}

	@Override
	public void raise(int key, Object... parameters) {
		
	}

	@Override
	public Delegate<Side> getDelegate() {
		// TODO Auto-generated method stub
		return null;
	}
}
