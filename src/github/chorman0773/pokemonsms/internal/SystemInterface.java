package github.chorman0773.pokemonsms.internal;

import java.nio.file.Path;

import github.chorman0773.pokemonsms.game.PokemonSMS;
import github.chorman0773.pokemonsms.server.DedicatedServer;
import github.chorman0773.pokemonsms.siding.EnumSide;

public interface SystemInterface {
	
	public Path getRootDir();
	public static SystemInterface get() {
		if(EnumSide.isClient())
			return PokemonSMS.getInstance(PokemonSMS.class);
		else
			return DedicatedServer.getInstance();
	}
}
