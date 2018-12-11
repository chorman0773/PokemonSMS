package github.chorman0773.pokemonsms.core;

import java.util.Arrays;
import java.util.List;

public enum EnumStat {
	ATTACK, DEFENSE, SPECIAL, SPEC_DEF, SPEED,
	HP, ACCURACY, EVASION, CRITRATIO;
	
	public final static List<EnumStat> numericOrder = Arrays.asList(ATTACK,DEFENSE,SPECIAL,SPEC_DEF,SPEED);
}
