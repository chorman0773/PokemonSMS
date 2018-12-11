package github.chorman0773.pokemonsms.net.server;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyPair;

import github.chorman0773.pokemonsms.net.INetHandlerRemote;
import github.chorman0773.pokemonsms.net.IPacket;
import github.chorman0773.pokemonsms.net.ProtocolError;
import github.chorman0773.pokemonsms.siding.EnumSide;

public class NetHandlerServer implements INetHandlerRemote {
	private ServerConnection conn;
	public NetHandlerServer(Socket sock,KeyPair keys) throws IOException {
		conn = new ServerConnection(sock,keys);
		try {
			conn.handshake();
		}catch(IOException e) {
			conn.close();
		}
	}
	private void handleProtocolError(ProtocolError perr) throws ProtocolError {
		//TODO send error message to client
		close();
	}
	
	public void send(IPacket packet) throws ProtocolError {
		try {
			conn.send(packet);
		}catch(ProtocolError perr) {
			handleProtocolError(perr);
		}catch(IOException e) {
			close();
		}
	}
	public IPacket recieve()throws ProtocolError{
		try {
			return conn.get();
		}catch(ProtocolError perr) {
			handleProtocolError(perr);
			throw perr;
		}catch(IOException e) {
			close();
			throw new ProtocolError(e);
		}
	}
	@Override
	public void close() throws ProtocolError {
		try {
		conn.close();
		}catch(IOException e) {
			throw new ProtocolError(e);
		}
	}
	@Override
	public EnumSide getActive() {
		// TODO Auto-generated method stub
		return EnumSide.SERVER;
	}
	@Override
	public Socket getConnection() {
		// TODO Auto-generated method stub
		return conn.getSocket();
	}
	@Override
	public EnumSide getRemote() {
		// TODO Auto-generated method stub
		return EnumSide.CLIENT;
	}

}
