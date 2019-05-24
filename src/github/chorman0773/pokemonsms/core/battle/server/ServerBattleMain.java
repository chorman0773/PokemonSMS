package github.chorman0773.pokemonsms.core.battle.server;

import java.util.Random;

import github.chorman0773.pokemonsms.core.battle.Battle;
import github.chorman0773.pokemonsms.core.battle.Side;
import github.chorman0773.pokemonsms.net.server.INetHandlerServer;

public class ServerBattleMain extends Battle {
	protected static class ServerBattleSide extends Side{
		protected final BattleSlot[] slots;
		protected final ServerBattleRemote target;
		protected ServerBattleSide(Battle owner,Side[] team,BattleSlot[] ownedSlots,ServerBattleRemote rem) {
			super(owner,team);
			slots = ownedSlots;
			this.target = rem;
		}
		
	}
	private final ServerBattleRemote[] ownedRemotes;
	protected ServerBattleMain(ServerBattleSide[] sides,BattleSlot[] slots,ServerBattleRemote[] remotes,Random rand) {
		super(sides,slots, rand);
		this.ownedRemotes =remotes;
	}
	
	/**
	 * Creates the Main Pipeline of a Server-side Single Battle between 2 clients,
	 *  represented as NetHandlerServers, with a given random. <br/>
	 *  Teams are not initialized for the sides.
	 *  
	 * @param c1 The First client. Note that no specific order is enforced
	 * @param c2 The Second client.
	 * @param rand The random to use for the battle
	 * @return a new ServerBattleMain with 2 sides, 2 slots (1 per side), and 2 remotes.
	 */
	public static ServerBattleMain createSingleBattle(INetHandlerServer c1,INetHandlerServer c2,Random rand) {
		BattleSlot c1Slot = new BattleSlot(0,0,0,null);
		BattleSlot c2Slot = new BattleSlot(1,0,1,null);
		BattleSlot[] slots = new BattleSlot[] {c1Slot,c2Slot};
		
		ServerBattleSide[] c1SideTeam = new ServerBattleSide[]{null};//Will be populated later
		ServerBattleSide[] c2SideTeam = new ServerBattleSide[] {null};
		
		ServerBattleRemote[] remotes = new ServerBattleRemote[2];
		ServerBattleSide[] sides = new ServerBattleSide[2];
		
		BattleSlot[] c1Slots = new BattleSlot[] {c1Slot};
		BattleSlot[] c2Slots = new BattleSlot[] {c2Slot};
		
		ServerBattleMain main = new ServerBattleMain(sides,slots,remotes,rand);
		
		ServerBattleRemote r1 = new ServerBattleRemote(main,c1,c1SideTeam,c1Slots,rand, 0);
		c1SideTeam[0] = new ServerBattleSide(main,c1SideTeam,c1Slots,r1);
		sides[0] = c1SideTeam[0];
		remotes[0] = r1;
		
		ServerBattleRemote r2 = new ServerBattleRemote(main,c2,c2SideTeam,c2Slots,rand, 0);
		c2SideTeam[1] = new ServerBattleSide(main,c2SideTeam,c2Slots,r2);
		sides[1] = c2SideTeam[1];
		remotes[1] = r2;
		
		return main;
	}
	
	
	public boolean handlesMainEvents() {
		return true;
	}
	

}
