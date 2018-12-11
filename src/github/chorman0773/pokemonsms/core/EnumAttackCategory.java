package github.chorman0773.pokemonsms.core;

public enum EnumAttackCategory {
	PHYSICAL(EnumStat.ATTACK,EnumStat.DEFENSE), SPECIAL(EnumStat.SPECIAL,EnumStat.SPEC_DEF)
	, STATUS(EnumStat.ACCURACY,EnumStat.EVASION);
	public final EnumStat attack;
	public final EnumStat defense;
	EnumAttackCategory(EnumStat atk,EnumStat def){
		this.attack = atk;
		this.defense = def;
	}
}
