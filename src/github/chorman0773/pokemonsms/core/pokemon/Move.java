package github.chorman0773.pokemonsms.core.pokemon;

import java.util.Set;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;

import github.chorman0773.pokemonsms.core.EnumAttackCategory;
import github.chorman0773.pokemonsms.core.EnumType;
import github.chorman0773.pokemonsms.core.EventBus;
import github.chorman0773.pokemonsms.core.LuaEventBus;
import github.chorman0773.pokemonsms.core.NamedRegistryEntry;
import github.chorman0773.sentry.text.TextComponent;

public class Move extends NamedRegistryEntry<Move> {
	
	private EnumType moveType;
	private boolean secondaryEffects;
	private EnumAttackCategory cat;
	private TextComponent desc;
	private int power;
	private double accuracy;
	private EventBus bus;
	private int pp;
	private Set<String> tags;
	
	static String[] tagListToArray(LuaTable t) {
		String[] ret =new String[t.length()];
		for(int i=0;i<ret.length;i++)
			ret[i] = t.get(i+1).checkjstring();
		return ret;
	}
	
	protected Move(LuaValue val,EnumType type,EnumAttackCategory cat,TextComponent desc,boolean hasSecondary,int power,double accuracy,EventBus bus,String[] tags,int pp) {
		super(val);
		this.moveType = type;
		this.cat = cat;
		this.desc = desc;
		this.power = power;
		this.secondaryEffects = hasSecondary;
		this.tags = Set.of(tags);
		this.accuracy = accuracy;
		this.bus = bus;
		this.pp = pp;
	}
	
	public Move(LuaValue val) {
		this(val,(EnumType)CoerceLuaToJava.coerce(val.get("type"), EnumType.class),(EnumAttackCategory)CoerceLuaToJava.coerce(val.get("category"), EnumAttackCategory.class),fromValue(val.get("description")),val.get("hasSecondaryEffects").checkboolean(),val.get("power").checkint(),val.get("accuracy").checkdouble(),new LuaEventBus(val.get("eventBus")),tagListToArray(val.get("traits").checktable()),val.get("pp").checkint());
		// TODO Auto-generated constructor stub
	}

	@Override
	public Class<Move> getType() {
		// TODO Auto-generated method stub
		return Move.class;
	}
	
	public EventBus getEventBus() {
		return bus;
	}
	public TextComponent getDescription() {
		return desc;
	}
	public EnumType getMoveType() {
		return moveType;
	}
	public EnumAttackCategory getAttackCategory() {
		return cat;
	}
	public boolean hasSecondaryEffects() {
		return secondaryEffects;
	}
	public int getBasePP() {
		return pp;
	}
	public int getPP(int ppUps) {
		return (int)(pp*((5+ppUps)/5.0f));
	}
	
	public boolean hasTrait(String tag) {
		return tags.contains(tag);
	}

	public int getPower() {
		// TODO Auto-generated method stub
		return power;
	}
	public double getAccuracy() {
		return accuracy;
	}
}
