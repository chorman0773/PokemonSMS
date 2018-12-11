package github.chorman0773.pokemonsms.net.packet.battle;

import java.io.IOException;

import github.chorman0773.pokemonsms.core.PokemonData;
import github.chorman0773.pokemonsms.net.IPacket;
import github.chorman0773.pokemonsms.net.PacketBuffer;

public class SPacketTurnBegin implements IPacket {
	public static interface TeamEValues{
		public static byte unused = 0, normal = 1,
				statused = 2, fainted = 3;
	}
	private PokemonData[] data;
	private byte[] team;
	private byte[] oteam;
	public SPacketTurnBegin(PokemonData[] data,byte[] team,byte[] oteam) {
		// TODO Auto-generated constructor stub
	}
	public SPacketTurnBegin() {}
	@Override
	public void read(PacketBuffer buff) throws IOException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void write(PacketBuffer buff) throws IOException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int hashcode() {
		// TODO Auto-generated method stub
		return 0;
	}
	

}
