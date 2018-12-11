package github.chorman0773.pokemonsms.core;

public interface EventBus {
	public void raise(int key,Object... parameters);
}
