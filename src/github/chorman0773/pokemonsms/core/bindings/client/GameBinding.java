package github.chorman0773.pokemonsms.core.bindings.client;

import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

import github.chorman0773.pokemonsms.core.bindings.Binding;
import github.chorman0773.pokemonsms.siding.EnumSide;

public class GameBinding extends Binding {
	
	static {
		if(!EnumSide.isClient())
			throw new IllegalStateException("The Game Binding is unavaible on the lua client");
	}
	
	private static class GameBindingFunction extends LuaFunction{
		private int fn;
		private static final int IS_POKEMON = 0,
				IS_ABILITY = 1,
				IS_ITEM = 2,
				IS_MOVE = 3,
				IS_TILE = 4,
				IS_NPC = 5,
				IS_TRAINER = 6,
				IS_SPRITE = 7,
				IS_LOCATION = 8,
				GET_POKEMON = 9,
				GET_ABILITY = 10,
				GET_ITEM = 11,
				GET_MOVE = 12,
				GET_TILE = 13,
				GET_NPC = 14,
				GET_TRAINER = 15,
				GET_SPRITE = 16,
				GET_LOCATION = 17,
				GET_IMPLEMENTATION_NAME = 18,
				GET_IMPLEMENETATION_VERSION = 19,
				GET_SPECIFICATION_VERSION = 20,
				GET_OVERWORLDSPRITE_FOR = 21,
				WARP_TO = 22,
				GET_PROVIDER_NAME = 23,
				GET_PROVIDER_REALNAME = 24,
				IS_PROVIDER_VERIFIED = 25,
				IS_REGISTERED = 26,
				GENERATE_PROLOUGE_SPRITE = 27,
				SET_GRAPHICS_MODE = 28,
				DRAW = 29;
		private GameBindingFunction(int fn) {
			this.fn = fn;
		}
		@Override
		public LuaValue invoke(LuaValue[] args) {
			switch(fn) {
			
			}
			throw new IllegalStateException("Someone is messing with the universe");
		}
		
	}
	public GameBinding() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public LuaValue get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

}
