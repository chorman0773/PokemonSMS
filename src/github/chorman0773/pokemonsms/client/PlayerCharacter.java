package github.chorman0773.pokemonsms.client;

import java.util.Random;

import github.chorman0773.sentry.save.NBTSerializable;
import github.chorman0773.sentry.save.nbt.NBTTagCompound;
import github.chorman0773.sentry.text.TextComponent;

public class PlayerCharacter implements NBTSerializable {
	private long id;
	private long sid;


	private TextComponent name;
	private String spriteKey;
	
	static {
		ClientSideClassesCheck.check();
	}

	public PlayerCharacter() {
		// TODO Auto-generated constructor stub
	}
	
	public static PlayerCharacter createNew(Random rand) {
		PlayerCharacter ret = new PlayerCharacter();
		ret.id = rand.nextLong();
		ret.sid = rand.nextLong();
		
		return ret;
	}
	
	public String getSpriteKey() {
		return spriteKey;
	}
	public TextComponent getName() {
		return name;
	}
	public long getTrainerId() {
		return id;
	}
	public long getTrainerSecretId() {
		return sid;
	}
	
	public void setSpriteKey(String key) {
		spriteKey = key;
	}
	public void setName(TextComponent comp) {
		this.name = comp;
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
