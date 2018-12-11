package github.chorman0773.pokemonsms.core.pokemon;

import org.luaj.vm2.LuaTable;


import github.chorman0773.pokemonsms.core.EnumAttackCategory;
import github.chorman0773.pokemonsms.core.EnumType;
import github.chorman0773.pokemonsms.core.Registry.Registries;
import github.chorman0773.sentry.text.TextComponent;


public class NullMove extends Move {
	private static final LuaTable tag = LuaTable.tableOf();
	public static final NullMove instance;
	static {
		tag.set("loc", "system:moves/null");
		tag.set("name", "moves.null.name");
		Registries.moves.register(instance = new NullMove());
	}
	private NullMove() {
		super(tag,EnumType.TYPELESS,EnumAttackCategory.STATUS,TextComponent.empty(),false,0,1,new NullEventBus(),new String[0],0);
		// TODO Auto-generated constructor stub
	}

}
