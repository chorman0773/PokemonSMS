package github.chorman0773.pokemonsms.client;

import org.luaj.vm2.LuaValue;

import github.chorman0773.pokemonsms.core.EventBus;
import github.chorman0773.pokemonsms.core.NamedRegistryEntry;
import github.chorman0773.pokemonsms.core.pokemon.Pokemon;

public class Trainer extends NamedRegistryEntry<Trainer> {
	private int rewardV;
	private EventBus bus;
	private Pokemon[] pkmInf;
	
	public Trainer(LuaValue val) {
		super(val);
		// TODO Auto-generated constructor stub
	}
	


	@Override
	public Class<Trainer> getType() {
		// TODO Auto-generated method stub
		return Trainer.class;
	}
}
