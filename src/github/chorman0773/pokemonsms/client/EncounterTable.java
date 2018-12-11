package github.chorman0773.pokemonsms.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.luaj.vm2.LuaValue;

import github.chorman0773.pokemonsms.core.pokemon.Pokemon;
import github.chorman0773.pokemonsms.core.pokemon.Species;

public class EncounterTable {
	public static enum EncounterType{
		VERY_COMMON, COMMON, UNCOMMON, RARE, VERY_RARE, SPECIAL;
		public double getChance() {
			switch(this) {
			case VERY_COMMON:
				return 1.0;
			case COMMON:
				return 0.75;
			case UNCOMMON:
				return 0.5;
			case RARE:
				return 0.25;
			case VERY_RARE:
				return 0.125;
			case SPECIAL:
				return 1.0/65536;
			}
			throw new Error("Wut");
		}
	}
	private static final EmptyEncounter EMPTY = new EmptyEncounter();
	public static abstract class BaseEncounter{
		public abstract Pokemon get(Random rand);
		public boolean isRoamingEncounter() {
			return false;
		}
		public boolean checkAdd(Random rand) {
			return true;
		}
	}
	
	private static final class EmptyEncounter extends BaseEncounter{
		public Pokemon get(Random rand) {
			return null;
		}
	}
	
	public static final class Encounter extends BaseEncounter{
		final EncounterType type;
		final Species species;
		final int minLevel;
		final int maxLevel;
		final double chance;
		public Encounter(EncounterType type,Species species,int minLevel,int maxLevel) {
			this(type,species,minLevel,maxLevel,type.getChance());
		}
		private Encounter(EncounterType type,Species species,int minLevel,int maxLevel,double chance) {
			this.type = type;
			this.species = species;
			this.minLevel = minLevel;
			this.maxLevel = maxLevel;
			this.chance = chance;
		}
		
		BaseEncounter withRepel(int repelLevel) {
			if(maxLevel<repelLevel)
				return EMPTY;
			else if(minLevel>repelLevel)
				return this;
			else
				return new Encounter(type,species,minLevel,maxLevel,chance);
		}
		Encounter withModifier(double modifier) {
			return new Encounter(type,species,minLevel,maxLevel,chance*modifier);
		}
		public Pokemon get(Random rand) {
			return null;
		}
		public boolean checkAdd(Random rand) {
			if(chance>=1.0)
				return true;
			else
				return rand.nextDouble()<chance;
		}
	}
	public static final class RoamingEncounter extends BaseEncounter{
		final Species target;
		final LuaValue extra;
		final int level;
		final int minPerfectIvs;
		final int shinyRolls;
		final boolean hiddenAbility;
		private Pokemon species;
		public RoamingEncounter(Species target,LuaValue extra,int level,int minPerfectIvs,int shinyRolls,boolean hiddenAbility){
			this.target = target;
			this.level = level;
			this.minPerfectIvs = minPerfectIvs;
			this.shinyRolls = shinyRolls;
			this.hiddenAbility = hiddenAbility;
			this.extra = extra;
		}
		public Pokemon get(Random rand) {
			if(species!=null)
				return species;
			else {
				//Generate Later
				return species;
			}
		}
		public boolean isRoamingEncounter() {
			return true;
		}
	}
	public static enum EncounterRegion{
		GRASS, TALLGRASS, OLD_ROD, GOOD_ROD, VERY_GOOD_ROD, CAVE, DUNGEON,
		SURF;
		
		public int getRepeats() {
			switch(this) {
			case CAVE:
			case DUNGEON:
			case SURF:
				return 3;
			default:
				return 2;
			}
		}
	}
	private final Encounter[] encounters;
	public EncounterTable(Encounter[] encounters) {
		this.encounters = encounters;
	}
	
	public BaseEncounter select(RoamingEncounter[] roamingEncounters,Random rand,EncounterRegion region,int repelLevel,int intimidateLevel,double[] manipulators) {
		List<BaseEncounter> selector = new ArrayList<>();
		selector.add(EMPTY);
		for(RoamingEncounter e:roamingEncounters)
			selector.add(e);
		for(int i = 0;i<region.getRepeats();i++)
			for(Encounter e:encounters)
			{
				if(!rand.nextBoolean())
					selector.add(EMPTY);
				if(repelLevel!=0) {
					BaseEncounter be = e.withRepel(repelLevel);
					if(be instanceof EmptyEncounter) {
						selector.add(be);
						continue;
					}
					
					if(e.minLevel<repelLevel) {
						int low = (repelLevel-e.minLevel);
						int high = (e.maxLevel-repelLevel);
						double chance = (high*0.5/low);
						if(chance<rand.nextDouble()) {
							selector.add(EMPTY);
							continue;
						}
					}
					e = (Encounter)be;
				}
				if(e.type!=EncounterType.VERY_COMMON&&e.type!=EncounterType.SPECIAL)
					e.withModifier(manipulators[e.type.ordinal()-1]);
				if(e.checkAdd(rand))
					selector.add(e);
			}
		return selector.get(rand.nextInt(selector.size()));
	}

}
