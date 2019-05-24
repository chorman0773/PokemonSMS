package github.chorman0773.pokemonsms.core.battle.net;

import github.chorman0773.pokemonsms.core.ResourceLocation;
import github.chorman0773.sentry.text.TextComponent;

public class PokemonData {
	private int slot;
	private ResourceLocation name;
	private int level;
	private TextComponent displayName;
	private int status;
	private int nvStatus;
	private int displayFlags;
	
	public PokemonData() {
		// TODO Auto-generated constructor stub
	}

	public int getDisplayFlags() {
		return displayFlags;
	}

	public void setDisplayFlags(int displayFlags) {
		this.displayFlags = displayFlags;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	public ResourceLocation getName() {
		return name;
	}

	public void setName(ResourceLocation name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public TextComponent getDisplayName() {
		return displayName;
	}

	public void setDisplayName(TextComponent displayName) {
		this.displayName = displayName;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getNvStatus() {
		return nvStatus;
	}

	public void setNvStatus(int nvStatus) {
		this.nvStatus = nvStatus;
	}

}
