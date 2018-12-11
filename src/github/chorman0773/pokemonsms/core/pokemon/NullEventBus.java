package github.chorman0773.pokemonsms.core.pokemon;

import github.chorman0773.pokemonsms.core.EventBus;

public class NullEventBus implements EventBus {

	public NullEventBus() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void raise(int key, Object... parameters) {

	}

}
