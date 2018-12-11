package github.chorman0773.pokemonsms.core.pokemon;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;

import github.chorman0773.pokemonsms.core.EventBus;
import github.chorman0773.pokemonsms.core.Registry.Registries;
import github.chorman0773.sentry.text.TextComponent;

public final class NullAbility extends Ability {
	private static final LuaTable tag = LuaTable.tableOf();
	static {
		tag.set("loc", "system:abilities/null");
		tag.set("name", "abilities.null.name");
		Registries.abilities.register(new NullAbility());
	}
	private NullAbility() {
		super(tag, TextComponent.empty(), new NullEventBus());
		// TODO Auto-generated constructor stub
	}
}
