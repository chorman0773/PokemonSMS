package github.chorman0773.pokemonsms.core.battle;

import github.chorman0773.pokemonsms.core.EnumAttackCategory;
import github.chorman0773.pokemonsms.core.EnumStat;
import github.chorman0773.pokemonsms.core.EnumType;
import github.chorman0773.pokemonsms.core.pokemon.Move;
import github.chorman0773.pokemonsms.internal.Events;
import github.chorman0773.pokemonsms.lua.Delegable;
import github.chorman0773.pokemonsms.lua.Delegate;
import github.chorman0773.sentry.text.TextComponent;

public class CombatStatus implements Delegable<CombatStatus> {
	private Battle owner;
	private InBattlePokemon source;
	private InBattlePokemon target;
	private Move mv;
	private EnumType type;
	private boolean failed;
	private TextComponent failComponent;
	private double mod;
	private double stab;
	private double critical;
	private double typeMod;
	private int power;
	private double accuracy;
	private EnumStat atk;
	private EnumStat def;
	private EnumAttackCategory cat;
	private boolean noImmunities;
	@Override
	public Delegate<CombatStatus> getDelegate() {
		// TODO Auto-generated method stub
		return null;
	}
	public CombatStatus(Battle owner,InBattlePokemon src,InBattlePokemon target,Move mv) {
		this.owner = owner;
		this.source = src;
		this.target = target;
		this.mv = mv;
		this.type = mv.getMoveType();
		this.cat = mv.getAttackCategory();
		this.atk = cat.attack;
		this.def = cat.defense;
		this.power = mv.getPower();
		this.accuracy = mv.getAccuracy();
	}
	
	private void raise(int event) {
		owner.getEventBus().raise(event, source,target,owner,mv,this);
	}
	
	public void checkAccuracy() {
		raise(Events.Battle.Combat.CheckAccuracy);
		
		if(hasFailed())
			return;
		if(accuracy==Double.POSITIVE_INFINITY||mv.hasTrait("bypass_accuracy"))
			return;
		else {
			accuracy *= source.getAccuracy();
			accuracy /= target.getEvasion();
			if(accuracy<owner.getRandom().nextDouble())
				reportFail(TextComponent.translate("battle.generic.miss"));
		}
	}

	public void reportFail(TextComponent comp) {
		this.failed = true;
		this.failComponent = comp;
	}
	public boolean hasFailed() {
		return failed;
	}
	public TextComponent getFailCause() {
		if(failed)
			return failComponent;
		else
			return TextComponent.empty();
	}
	
}
