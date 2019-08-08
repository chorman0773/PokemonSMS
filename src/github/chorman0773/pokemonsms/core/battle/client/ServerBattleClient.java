package github.chorman0773.pokemonsms.core.battle.client;

import java.util.Random;

import github.chorman0773.pokemonsms.core.battle.Battle;
import github.chorman0773.pokemonsms.core.battle.Side;
import github.chorman0773.pokemonsms.net.INetHandlerRemote;

public class ServerBattleClient extends Battle {
	private INetHandlerRemote server;

	public ServerBattleClient(Side[] sides, BattleSlot[] slots, Random rand, INetHandlerRemote server) {
		super(sides, slots, rand);
		this.server = server;
		
	}
	

}
