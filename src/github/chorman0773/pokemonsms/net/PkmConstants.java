package github.chorman0773.pokemonsms.net;

import github.lightningcreations.lclib.Version;
import github.lightningcreations.lclib.VersionRange;

public interface PkmConstants {
	public static Version current = new Version("1.0");
	public static VersionRange supported = current.prior();
}
