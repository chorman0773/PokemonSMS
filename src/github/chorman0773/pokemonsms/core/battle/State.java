package github.chorman0773.pokemonsms.core.battle;

import github.chorman0773.pokemonsms.core.EventBus;
import github.chorman0773.pokemonsms.lua.Delegable;
import github.chorman0773.pokemonsms.lua.Delegate;

public class State implements Delegable<State>, EventBus {

	public State() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void raise(int key, Object... parameters) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Delegate<State> getDelegate() {
		// TODO Auto-generated method stub
		return null;
	}

}
