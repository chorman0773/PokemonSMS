package github.chorman0773.pokemonsms.net;

import java.net.Socket;

import github.chorman0773.pokemonsms.siding.EnumSide;

public interface INetHandlerRemote extends INetHandler {
	public EnumSide getRemote();
	public Socket getConnection();
	public IPacket recieve()throws ProtocolError;
	public void send(IPacket packet)throws ProtocolError;
}
