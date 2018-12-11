package github.chorman0773.pokemonsms.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.function.Supplier;

import github.chorman0773.pokemonsms.net.packet.CPacketServerConnect;
import github.chorman0773.pokemonsms.net.packet.SPacketConnectSuccess;

public class PacketDecoder {
	@SuppressWarnings("unchecked")
	private static final Supplier<? extends IPacket>[] suppliers =(Supplier<? extends IPacket>[]) new Supplier[]{
			CPacketServerConnect::new,
			SPacketConnectSuccess::new
	};
	public PacketDecoder() {
		
	}
	public IPacket read(DataInputStream strm) throws IOException {
		int id= strm.readUnsignedByte();
		int hashcode = strm.readInt();
		int size = strm.readInt();
		byte[] b = new byte[size];
		strm.readFully(b);
		PacketBuffer buff = new PacketBuffer(b);
		if(id==255) {
			HandshakeComplete ret = new HandshakeComplete();
			ret.read(buff);
			if(ret.size()!=size)
				throw new ProtocolError("Packet Size Mismatch ("+ret.size()+"!="+size+")");
			else if((ret.hashcode()*31+ret.getId())!=hashcode)
				throw new ProtocolError("Packet Hash Mismatch ("+ret.hashCode()+"!="+hashcode+")");
			return ret;
		}else if(id>=suppliers.length) {
			throw new ProtocolError("Invalid Packet Id "+Integer.toHexString(id));
		}else {
			IPacket ret = suppliers[id].get();
			ret.read(buff);
			if(ret.size()!=size)
				throw new ProtocolError("Packet Size Mismatch ("+ret.size()+"!="+size+")");
			else if((ret.hashcode()*31+ret.getId())!=hashcode)
				throw new ProtocolError("Packet Hash Mismatch ("+ret.hashCode()+"!="+hashcode+")");
			return ret;
		}
	}
	public void write(DataOutputStream out,IPacket packet)throws IOException{
		PacketBuffer buff = new PacketBuffer();
		out.writeByte(packet.getId());
		out.writeInt(packet.hashcode()*31+packet.getId());
		out.writeInt(packet.size());
		packet.write(buff);
		out.write(buff.getBytes());
	}
}
