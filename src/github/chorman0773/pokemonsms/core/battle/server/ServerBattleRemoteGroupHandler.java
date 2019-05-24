package github.chorman0773.pokemonsms.core.battle.server;

import java.util.Random;

import github.chorman0773.pokemonsms.core.battle.Battle;
import github.chorman0773.pokemonsms.core.battle.Side;
import github.chorman0773.pokemonsms.net.server.NetHandlerServer;

public class ServerBattleRemoteGroupHandler extends ServerBattleRemote {

	public ServerBattleRemoteGroupHandler(Battle controller, NetHandlerServer handler, Side[] sides, BattleSlot[] slots,
			Random rand, Side side) {
		super(controller, handler, sides, slots, rand, side);
		// TODO Auto-generated constructor stub
	}

}
