package github.chorman0773.pokemonsms.net.packet;

import java.io.IOException;

import github.chorman0773.pokemonsms.net.IPacket;
import github.chorman0773.pokemonsms.net.PacketBuffer;
import github.lightningcreations.lclib.Hash;
import github.lightningcreations.lclib.Version;

public class SPacketConnectSuccess implements IPacket {
	private Version pkmComVersion;
	public SPacketConnectSuccess(Version ver) {
		this.pkmComVersion = ver;
	}
	public SPacketConnectSuccess() {
	}
	@Override
	public void read(PacketBuffer buff) throws IOException {
		pkmComVersion = buff.readVersion();
	}

	@Override
	public void write(PacketBuffer buff) throws IOException {
		buff.writeVersion(pkmComVersion);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 1;
	}
	@Override
	public int hashcode() {
		// TODO Auto-generated method stub
		return Hash.hashcode(pkmComVersion);
	}

}
