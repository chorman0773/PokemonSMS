package github.chorman0773.pokemonsms.core.battle;

import github.chorman0773.pokemonsms.core.pokemon.Pokemon;
import github.chorman0773.pokemonsms.lua.Delegable;
import github.chorman0773.pokemonsms.lua.Delegate;

public class InBattlePokemon implements Delegable<InBattlePokemon>, BattleEventSubscriber {

	private Pokemon pkm;
	private int sleepTime;
	private int freezeTime;
	private int toxicTime;
	private int confuseTime;
	private int infatuateTarget;
	private int leachedTarget;
	private int disableTime;
	private int disableTarget;
	private int id;
	private int[] stages = new int[9];

	public InBattlePokemon(Pokemon pkm,int id) {
		this.pkm = pkm;
		this.id = id;
	}

	@Override
	public Delegate<InBattlePokemon> getDelegate() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Pokemon getPokemon() {
		return pkm;
	}

	@Override
	public void turnEnd(Battle b) {
		// TODO Auto-generated method stub
		
	}

	public double getAccuracy() {
		int stage = stages[6];
		if(stage==0)
			return 1.0;
		else if(stage>0)
			return (3+stage)/3.0;
		else
			return 3.0/(3-stage);
	}
	public double getEvasion() {
		int stage = stages[7];
		if(stage==0)
			return 1.0;
		else if(stage>0)
			return (3+stage)/3.0;
		else
			return 3.0/(3-stage);
	}

}
