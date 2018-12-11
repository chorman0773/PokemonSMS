package github.chorman0773.pokemonsms.core.pokemon;

import org.luaj.vm2.LuaTable;
import github.chorman0773.pokemonsms.core.Registry.Registries;
import github.chorman0773.sentry.text.TextComponent;

public class NullItem extends Item {
	private static final LuaTable tag = LuaTable.tableOf();
	public static final NullItem instance;
	static {
		tag.set("loc", "system:itemss/null");
		tag.set("name", "items.null.name");
		Registries.items.register(instance = new NullItem());
	}
	private NullItem() {
		super(tag, new NullEventBus(), TextComponent.empty(), new String[0]);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean canHold(Pokemon pkm, int meta) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean canUse(Pokemon pkm, int meta) {
		// TODO Auto-generated method stub
		return false;
	}



}
