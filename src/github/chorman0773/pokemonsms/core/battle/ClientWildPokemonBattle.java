package github.chorman0773.pokemonsms.core.battle;

import java.util.Random;

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
