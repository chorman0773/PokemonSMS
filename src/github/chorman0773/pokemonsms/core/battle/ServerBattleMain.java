package github.chorman0773.pokemonsms.core.battle;

import java.util.Random;

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
	protected ServerBattleMain(int nplayers,int npokemon, Random rand) {
		super(new ServerBattleSide[nplayers],new BattleSlot[nplayers*npokemon], rand);
		this.ownedRemotes = new ServerBattleRemote[nplayers];
	}
	
	
	public boolean handlesMainEvents() {
		return true;
	}
	

}
