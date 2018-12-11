package github.chorman0773.pokemonsms.core.pokemon;


import org.luaj.vm2.LuaValue;

import github.chorman0773.pokemonsms.core.EventBus;
import github.chorman0773.pokemonsms.core.LuaEventBus;
import github.chorman0773.pokemonsms.core.NamedRegistryEntry;
import github.chorman0773.sentry.text.TextComponent;

public class Ability extends NamedRegistryEntry<Ability> {
	private TextComponent description;
	private EventBus bus;
	protected Ability(LuaValue val,TextComponent desc,EventBus bus) {
		super(val);
		this.description = desc;
		this.bus = bus;
	}
	public Ability(LuaValue val) {
		this(val,fromValue(val.get("description")),new LuaEventBus(val.get("eventBus")));
	}

	@Override
	public Class<Ability> getType() {
		// TODO Auto-generated method stub
		return Ability.class;
	}
	public EventBus getEventBus() {
		return bus;
	}
	public TextComponent getDescription() {
		return description;
	}
}
