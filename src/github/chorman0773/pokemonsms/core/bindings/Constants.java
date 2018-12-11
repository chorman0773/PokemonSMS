package github.chorman0773.pokemonsms.core.bindings;

import org.luaj.vm2.LuaValue;

import github.chorman0773.pokemonsms.client.EnumDirection;
import github.chorman0773.pokemonsms.core.EnumAttackCategory;
import github.chorman0773.pokemonsms.core.EnumStat;
import github.chorman0773.pokemonsms.core.EnumStatus;
import github.chorman0773.pokemonsms.core.EnumType;
import github.chorman0773.pokemonsms.lua.EnumValue;

public class Constants extends Binding {
	public static class Stats extends BindingValue{
		
		public Stats(Object obj) {
			super(obj);
			// TODO Auto-generated constructor stub
		}

		@Override
		public LuaValue get(String key) {
			switch(key) {
			case "Attack": return EnumValue.of(EnumStat.ATTACK);
			case "Defense": return EnumValue.of(EnumStat.DEFENSE);
			case "Special": return EnumValue.of(EnumStat.SPECIAL);
			case "SpecDef": return EnumValue.of(EnumStat.SPEC_DEF);
			case "Speed": return EnumValue.of(EnumStat.SPEED);
			case "HP": return EnumValue.of(EnumStat.HP);
			case "Accuracy": return EnumValue.of(EnumStat.ACCURACY);
			case "Evasion": return EnumValue.of(EnumStat.EVASION);
			case "CritRatio": return EnumValue.of(EnumStat.CRITRATIO);
			}
			return NIL;
		}
		
	}
	public static class Categories extends BindingValue{

		public Categories(Object obj) {
			super(obj);
			// TODO Auto-generated constructor stub
		}
		@Override
		public LuaValue get(String key) {
			switch(key) {
			case "Physical": return EnumValue.of(EnumAttackCategory.PHYSICAL);
			case "Special": return EnumValue.of(EnumAttackCategory.SPECIAL);
			case "Status": return EnumValue.of(EnumAttackCategory.STATUS);
			}
			return NIL;
		}
		
	}
	public static class Types extends BindingValue{

		public Types(Object obj) {
			super(obj);
			// TODO Auto-generated constructor stub
		}
		@Override
		public LuaValue get(String key) {
			switch(key) {
			case "Normal": return EnumValue.of(EnumType.NORMAL);
			case "Grass": return EnumValue.of(EnumType.GRASS);
			case "Fire": return EnumValue.of(EnumType.FIRE);
			case "Water": return EnumValue.of(EnumType.WATER);
			case "Electric": return EnumValue.of(EnumType.ELECTRIC);
			case "Flying": return EnumValue.of(EnumType.FLYING);
			case "Fighting": return EnumValue.of(EnumType.FIGHTING);
			case "Rock": return EnumValue.of(EnumType.ROCK);
			case "Ground": return EnumValue.of(EnumType.GROUND);
			case "Ice": return EnumValue.of(EnumType.ICE);
			case "Psychic": return EnumValue.of(EnumType.PSYCHIC);
			case "Poison": return EnumValue.of(EnumType.POISON);
			case "Ghost": return EnumValue.of(EnumType.GHOST);
			case "Dark": return EnumValue.of(EnumType.DARK);
			case "Dragon": return EnumValue.of(EnumType.DRAGON);
			case "Steel": return EnumValue.of(EnumType.STEEL);
			case "Fairy": return EnumValue.of(EnumType.FAIRY);
			case "Solar": return EnumValue.of(EnumType.SOLAR);
			case "Lunar": return EnumValue.of(EnumType.LUNAR);
			case "Celestial": return EnumValue.of(EnumType.CELESTIAL);
			case "Typeless": return EnumValue.of(EnumType.TYPELESS);
			case "Chaotic": return EnumValue.of(EnumType.CHAOTIC);
			case "Catastrophe": return EnumValue.of(EnumType.CATASTROPHE);
			case "Bug": return EnumValue.of(EnumType.BUG);
			}
			return NIL;
		}
		
		public String typename() {
			return "Types";
		}
		
	}
	public static class Status extends BindingValue{

		public Status(Object obj) {
			super(obj);
			// TODO Auto-generated constructor stub
		}
		@Override
		public LuaValue get(String key) {
			switch(key) {
			case "Poison": return EnumValue.of(EnumStatus.POISON);
			case "Burn": return EnumValue.of(EnumStatus.BURN);
			case "Paralysis": return EnumValue.of(EnumStatus.PARALYZE);
			case "Paralyze": return EnumValue.of(EnumStatus.PARALYZE);
			case "Sleep": return EnumValue.of(EnumStatus.SLEEP);
			case "Freeze": return EnumValue.of(EnumStatus.FREEZE);
			}
			return NIL;
		}
		
	}
	public static class Direction extends BindingValue{
		public Direction(Object o) {
			super(o);
		}
		public LuaValue get(String val) {
			switch(val) {
			case "North": return EnumValue.of(EnumDirection.NORTH);
			case "East": return EnumValue.of(EnumDirection.EAST);
			case "South": return EnumValue.of(EnumDirection.SOUTH);
			case "West": return EnumValue.of(EnumDirection.WEST);
			}
			return NIL;
		}
	}
	private final Stats stats = new Stats(this);
	private final Categories categories = new Categories(this);
	private final Types types = new Types(this);
	private final Status status = new Status(this);
	private final Direction dir = new Direction(this);
	public Constants() {
		// TODO Auto-generated constructor stub
	}

	public LuaValue get(String s) {
		switch(s){
			case "Stats":return stats;
			case "Types":return types;
			case "Categories": return categories;
			case "Status":return status;
			case "Direction": return dir;
		}
		return NIL;
	}
	
	public String typename() {
		return "Constants";
	}
}
