package github.chorman0773.pokemonsms.core.battle.server;

import java.util.Random;

import github.chorman0773.pokemonsms.core.battle.Battle;
import github.chorman0773.pokemonsms.core.battle.Side;
import github.chorman0773.pokemonsms.core.battle.Battle.BattleSlot;
import github.chorman0773.pokemonsms.net.server.NetHandlerServer;

public class ServerBattleRemote extends Battle {
	protected final Battle controller;
	protected final NetHandlerServer handler;
	protected final Side side;
	protected ServerBattleRemote(Battle controller,NetHandlerServer handler,Side[] sides,BattleSlot[] slots, Random rand,Side side) {
		super(sides,slots,rand);
		this.controller = controller;
		this.handler = handler;
		this.side = side;
	}

}
