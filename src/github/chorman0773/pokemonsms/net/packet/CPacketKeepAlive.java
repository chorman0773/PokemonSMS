package github.chorman0773.pokemonsms.net.packet;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

import github.chorman0773.pokemonsms.net.IPacket;
import github.chorman0773.pokemonsms.net.PacketBuffer;
import github.lightningcreations.lclib.Hash;

public class CPacketKeepAlive implements IPacket {
	private UUID id;
	private Instant instant;
	public CPacketKeepAlive(UUID id) {
		this.id = id;
		this.instant = Instant.now();
	}
	public CPacketKeepAlive() {
		
	}
	@Override
	public void read(PacketBuffer buff) throws IOException {
		id = buff.readUUID();
		instant = buff.readInstant();
	}

	@Override
	public void write(PacketBuffer buff) throws IOException {
		buff.writeUUID(id);
		buff.writeInstant(instant);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0x01;
	}

	@Override
	public int hashcode() {
		// TODO Auto-generated method stub
		return Hash.hashcode(id)*31+Hash.hashcode(instant);
	}

}
