package github.chorman0773.pokemonsms.core.battle.server;

import java.util.Random;

import github.chorman0773.pokemonsms.core.battle.Battle;
import github.chorman0773.pokemonsms.core.battle.Side;
import github.chorman0773.pokemonsms.net.server.INetHandlerServer;

public class ServerBattleRemote extends Battle {
	protected final Battle controller;
	protected final INetHandlerServer handler;
	protected final int side;
	protected ServerBattleRemote(Battle controller,INetHandlerServer handler,Side[] sides,BattleSlot[] slots, Random rand,int side) {
		super(sides,slots,rand);
		this.controller = controller;
		this.handler = handler;
		this.side = side;
	}

}
