package github.chorman0773.pokemonsms.core.battle;

import github.chorman0773.pokemonsms.core.EventBus;
import github.chorman0773.pokemonsms.lua.Delegable;
import github.chorman0773.pokemonsms.lua.Delegate;

public class WeatherCondition implements BattleEventSubscriber, Delegable<WeatherCondition>, EventBus {
	private int time;
	private boolean extreme;
	private WeatherType type;
	public static enum WeatherType{
		SUN, RAIN, SNOW, SAND, WIND;
	}
	public WeatherCondition(WeatherType weather,int time) {
		if(weather==WeatherType.WIND||time<0)
			extreme = true;
		this.type = weather;
		this.time = time;
	}
	@Override
	public Delegate<WeatherCondition> getDelegate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void turnEnd(Battle b) {
		if(!extreme) {
			time--;
			if(time<=0)
				;//TODO Send Info to battle
		}
		
	}
	@Override
	public void raise(int key, Object... parameters) {
		// TODO Auto-generated method stub
		
	}

}
