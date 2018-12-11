package github.chorman0773.pokemonsms.core;

import java.util.Collections;
import java.util.Set;

public enum EnumType {
 NORMAL("normal"), GRASS("grass"), FIRE("fire"), WATER("water"), ELECTRIC("electric"),
 GROUND("ground"), FLYING("flying"), BUG("bug"), ROCK("rock"), FIGHTING("fightning"), 
 POISON("posion"), PSYCHIC("psychic"), GHOST("ghost"), DARK("dark"), STEEL("steel"),
 ICE("ice"), DRAGON("dragon"), FAIRY("fairy"), SOLAR("solar"), LUNAR("lunar"), 
 CELESTIAL("celestial"), TYPELESS("typeless"), CHAOTIC("chaotic"), CATASTROPHE("catastrophe");
	private final String name;
	EnumType(String name){
		this.name= name;
	}
	public String toString() {
		return "types."+name+".name";
	}
	
	@SuppressWarnings("incomplete-switch")
	public Set<EnumType> ineffectiveList() {
		switch(this) {
		case NORMAL:
			return Set.of(GHOST);
		case FIRE:
			return Set.of(ICE);
		case GROUND:
			return Set.of(ELECTRIC);
		case FLYING:
			return Set.of(GROUND);
		case GHOST:
			return Set.of(NORMAL,FIGHTING);
		case DARK:
			return Set.of(PSYCHIC);
		case FAIRY:
			return Set.of(DRAGON);
		case SOLAR:
			return Set.of(CELESTIAL,GROUND,ICE);
		case LUNAR:
			return Set.of(SOLAR,GROUND,DARK);
		case CELESTIAL:
			return Set.of(LUNAR,GROUND,FIRE);
		case CATASTROPHE:
			return Set.of(CATASTROPHE);
		}
		return Collections.emptySet();
	}
	@SuppressWarnings("incomplete-switch")
	public Set<EnumType> weeknessList(){
		switch(this) {
		case BUG:
			return Set.of(FLYING,ROCK,FIRE);
		case CELESTIAL:
			return Set.of(SOLAR,PSYCHIC,STEEL);
		case DARK:
			return Set.of(BUG,FIGHTING,FAIRY);
		case DRAGON:
			return Set.of(ICE,DRAGON,FAIRY);
		case ELECTRIC:
			return Set.of(GROUND);
		case FAIRY:
			return Set.of(POISON,STEEL);
		case FIGHTING:
			return Set.of(POISON,PSYCHIC,CELESTIAL);
		case FIRE:
			return Set.of(WATER,ROCK,GROUND);
		case FLYING:
			return Set.of(ELECTRIC,ICE,ROCK);
		case GHOST:
			return Set.of(GHOST,DARK);
		case GRASS:
			return Set.of(ICE,FIRE,BUG,FLYING);
		case GROUND:
			return Set.of(ICE,WATER,GRASS);
		case ICE:
			return Set.of(FIRE,FIGHTING,STEEL,ROCK);
		case LUNAR:
			return Set.of(FIGHTING,CELESTIAL,STEEL);
		case NORMAL:
			return Set.of(FIGHTING);
		case POISON:
			return Set.of(GROUND,PSYCHIC);
		case PSYCHIC:
			return Set.of(GHOST,DARK,BUG);
		case ROCK:
			return Set.of(GRASS,WATER,FIGHTING,GROUND,STEEL);
		case SOLAR:
			return Set.of(LUNAR,STEEL,DRAGON);
		case STEEL:
			return Set.of(FIGHTING,GROUND,FIRE,ELECTRIC);
		case WATER:
			return Set.of(ELECTRIC,GRASS);
		}
		return Collections.emptySet();
	}
	@SuppressWarnings("incomplete-switch")
	public Set<EnumType> resitanceList(){
		switch(this) {
		case BUG:
			return Set.of(FIGHTING,GROUND);
		case CELESTIAL:
			return Set.of(CELESTIAL,NORMAL,FIGHTING,ELECTRIC,FLYING);
		case DARK:
			return Set.of(GHOST,DARK);
		case DRAGON:
			return Set.of(ELECTRIC,FIRE,WATER,GRASS);
		case ELECTRIC:
			return Set.of(STEEL,ELECTRIC,FLYING);
		case FAIRY:
			return Set.of(DARK,FIGHTING,BUG);
		case FIGHTING:
			return Set.of(BUG,ROCK,DARK);
		case FIRE:
			return Set.of(FIRE,GRASS,BUG);
		case FLYING:
			return Set.of(FIGHTING,BUG,GRASS);
		case GHOST:
			return Set.of(BUG);
		case GRASS:
			return Set.of(SOLAR,GRASS,WATER,ELECTRIC,GROUND);
		case GROUND:
			return Set.of(POISON);
		case ICE:
			return Set.of(ICE);
		case LUNAR:
			return Set.of(LUNAR,ROCK,ELECTRIC);
		case POISON:
			return Set.of(POISON,GRASS,BUG);
		case PSYCHIC:
			return Set.of(PSYCHIC,CELESTIAL,FIGHTING);
		case ROCK:
			return Set.of(ROCK,ELECTRIC,FIRE);
		case SOLAR:
			return Set.of(SOLAR,FIRE,WATER);
		case STEEL:
			return Set.of(STEEL,ROCK,PSYCHIC,FAIRY,FLYING,BUG,GRASS,DRAGON,ICE,NORMAL);
		case WATER:
			return Set.of(WATER,FIRE,STEEL,ICE,SOLAR);
		
		}
		return Collections.emptySet();
	}
	
}
