package github.chorman0773.pokemonsms.game;

import java.time.Instant;
import java.util.Random;

import github.chorman0773.sentry.save.NBTSerializable;
import github.chorman0773.sentry.save.nbt.NBTTagCompound;

public class SaveGame implements NBTSerializable {
	public static final Random globalRand = new Random();
	private transient final Random rand;
	public SaveGame() {
		this(globalRand.nextLong()*31+Instant.now().getNano());
	}
	public SaveGame(long seed) {
		rand = new Random(seed);
	}

	@Override
	public void writeNBT(NBTTagCompound comp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void readNBT(NBTTagCompound comp) {
		// TODO Auto-generated method stub
		
	}

}
