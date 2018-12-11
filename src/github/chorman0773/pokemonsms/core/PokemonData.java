package github.chorman0773.pokemonsms.core;

import java.io.IOException;

import github.chorman0773.pokemonsms.net.PacketBuffer;

public class PokemonData {
	private byte id;
	private ResourceLocation pkm;
	private byte vFlags;
	private int status;
	
	public PokemonData() {
		// TODO Auto-generated constructor stub
	}
	
	public void read(PacketBuffer buff)throws IOException{
		
	}
	public void write(PacketBuffer buff)throws IOException{
		
	}
	
	public int hashCode() {
		return 0;
	}
	
	public int size() {
		return 0;
	}

}
