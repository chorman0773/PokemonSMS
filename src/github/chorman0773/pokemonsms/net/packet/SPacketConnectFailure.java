package github.chorman0773.pokemonsms.net.packet;


import java.io.IOException;

import github.chorman0773.pokemonsms.net.IPacket;
import github.chorman0773.pokemonsms.net.PacketBuffer;
import github.chorman0773.pokemonsms.net.Sizes;
import github.chorman0773.sentry.text.TextComponent;
import github.lightningcreations.lclib.Hash;

public class SPacketConnectFailure implements IPacket {
	private TextComponent failReason;
	
	public SPacketConnectFailure() {
		// TODO Auto-generated constructor stub
	}
	public SPacketConnectFailure(TextComponent failReason) {
		this.failReason = failReason;
	}

	@Override
	public void read(PacketBuffer buff) throws IOException {
		failReason = TextComponent.fromJson(buff.readJson());
	}

	@Override
	public void write(PacketBuffer buff) throws IOException {
		buff.writeJson(failReason.toJson());
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return Sizes.size(failReason.toJson());
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 2;
	}
	
	@Override
	public int hashcode() {
		return Hash.hashcode(failReason.toString());
	}

}
