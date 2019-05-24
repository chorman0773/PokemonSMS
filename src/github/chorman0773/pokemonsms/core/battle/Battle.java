package github.chorman0773.pokemonsms.core.battle;

import java.util.Random;

import github.chorman0773.pokemonsms.core.EventBus;
import github.chorman0773.pokemonsms.lua.Delegable;
import github.chorman0773.pokemonsms.lua.Delegate;

public abstract class Battle implements Delegable<Battle> {
	public static class BattleSlot{
		int owner;
		int position;
		int side;
		InBattlePokemon pkm;
		public BattleSlot(int owner,int position,int side,InBattlePokemon pkm) {
			this.owner = owner;
			this.position = position;
			this.side = side;
			this.pkm = pkm;
		}
	}
	protected class BattleEventBus implements EventBus{
		@Override
		public void raise(int key, Object... parameters) {
			Battle.this.handle(key,parameters);
			for(Side s:sides) 
				s.raise(key, parameters);
			for(BattleSlot s:slots)
				s.pkm.getPokemon().raise(key, parameters);
			if(weather!=null)
				weather.raise(key, parameters);
			
		}
	}
	protected void handle(int key,Object[] parameters) {
		
	}
	
	protected final Side[] sides;
	protected final BattleSlot[] slots;
	private WeatherCondition weather;
	private EventBus bus;
	protected final Random rand;
	protected Battle(Side[] sides,BattleSlot[] slots,Random rand) {
		this.slots = slots;
		this.sides = sides;
		this.bus = new BattleEventBus();
		this.rand = rand;
	}

	public void setWeather(WeatherCondition weather) {
		this.weather = weather;
	}
	public void clearWeather() {
		this.weather = null;
	}
	public EventBus getEventBus() {
		return bus;
	}
	public Random getRandom() {
		return rand;
	}
	@Override
	public Delegate<Battle> getDelegate() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean handlesMainEvents() {
		return false;
	}
	
	
	public void sendFirstPokemon(BattleSlot slot,InBattlePokemon pkm) {
		slot.pkm = pkm;
	}
	
	public void switchPokemon(BattleSlot slot,InBattlePokemon pkm) {
		slot.pkm = pkm;
	}
	public InBattlePokemon getPokemon(BattleSlot slot) {
		return slot.pkm;
	}
}
