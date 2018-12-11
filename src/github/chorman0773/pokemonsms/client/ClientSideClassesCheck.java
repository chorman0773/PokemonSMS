package github.chorman0773.pokemonsms.client;

import github.chorman0773.pokemonsms.siding.EnumSide;

class ClientSideClassesCheck {
	static void check() {}
	static {
		if(!EnumSide.isClient())
			throw new InstantiationError("Use of Client Side Code on the Server Side");
	}
}
