package github.chorman0773.pokemonsms.net;

import java.net.ServerSocket;
import java.util.List;
import java.util.stream.Stream;

public interface INetController extends INetHandler {
	public Stream<INetHandlerRemote> getRemotes();
	public List<INetHandlerRemote> listRemotes();
	public ServerSocket getServer();
}
