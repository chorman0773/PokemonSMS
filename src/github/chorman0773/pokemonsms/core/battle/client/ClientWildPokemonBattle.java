package github.chorman0773.pokemonsms.core.battle.client;

import java.util.Random;

import github.chorman0773.pokemonsms.core.battle.Battle;
import github.chorman0773.pokemonsms.core.battle.Side;
import github.chorman0773.pokemonsms.core.battle.Battle.BattleSlot;

public class ClientWildPokemonBattle extends Battle {
	protected static class SideWild extends Side{

		public SideWild(Battle owner) {
			super(owner, null);
		}
		
	}
	protected ClientWildPokemonBattle(Side[] sides, BattleSlot[] slots, Random rand) {
		super(sides, slots, rand);
		// TODO Auto-generated constructor stub
	}

}
