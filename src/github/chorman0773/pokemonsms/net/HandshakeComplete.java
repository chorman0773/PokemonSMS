package github.chorman0773.pokemonsms.net;

import java.io.IOException;

public class HandshakeComplete implements IPacket {

	@Override
	public void read(PacketBuffer buff)throws IOException {
		if(buff.readInt()!=0x504B4D00)
			throw new ProtocolError("Handshaking Error, Magic Number Mismatch");
	}

	@Override
	public void write(PacketBuffer buff)throws IOException{
		buff.writeInt(0x504B4D00);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0xff;
	}
	
	public int hashcode() {
		return 0x504B4D00;
	}

}
