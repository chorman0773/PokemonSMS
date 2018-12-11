package github.chorman0773.pokemonsms.net;

import java.io.Closeable;

import github.chorman0773.pokemonsms.siding.EnumSide;

/**
 * The Base of PkmCom's Network Model.
 * @author connor
 *
 */
public interface INetHandler extends Closeable {
	public EnumSide getActive();
}
