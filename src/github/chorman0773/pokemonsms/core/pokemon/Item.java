package github.chorman0773.pokemonsms.core.pokemon;

import java.util.Set;

import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.LuaValue;

import github.chorman0773.pokemonsms.core.EventBus;
import github.chorman0773.pokemonsms.core.LuaEventBus;
import github.chorman0773.pokemonsms.core.NamedRegistryEntry;
import github.chorman0773.pokemonsms.lua.Delegable;
import github.chorman0773.pokemonsms.lua.Delegate;
import github.chorman0773.sentry.text.TextComponent;

public class Item extends NamedRegistryEntry<Item> implements Delegable<Item> {
	private static class ItemDelegate extends Delegate<Item>{

		public ItemDelegate(Item obj) {
			super(obj,Item.class);
			// TODO Auto-generated constructor stub
		}
		
	}
	protected static class Info{
		final LuaValue canEquip;
		final LuaValue canUse;
		protected Info(LuaValue val){
			canEquip = val.get("canEquip");
			canUse = val.get("canUse");
		}
	}
	private final ItemDelegate delegate = new ItemDelegate(this);
	protected Info inf;
	private EventBus bus;
	private TextComponent desc;
	private Set<String> tags;
	protected Item(LuaValue val,EventBus bus,TextComponent desc,String[] tags) {
		super(val);
		this.bus = bus;
		this.desc = desc;
		this.tags = Set.of(tags);
	}
	public Item(LuaValue val) {
		this(val,new LuaEventBus(val.get("eventBus")),fromValue(val.get("description")),Move.tagListToArray(val.get("traits").checktable()));
		inf = new Info(val);
	}

	@Override
	public Class<Item> getType() {
		// TODO Auto-generated method stub
		return Item.class;
	}

	@Override
	public Delegate<Item> getDelegate() {
		// TODO Auto-generated method stub
		return delegate;
	}
	
	public EventBus getEventBus() {
		return bus;
	}
	public boolean canHold(Pokemon pkm,int meta) {
		return inf.canEquip.call(getValue(), pkm.getDelegate(), LuaInteger.valueOf(meta)).checkboolean();
	}
	public boolean canUse(Pokemon pkm,int meta) {
		return inf.canUse.call(getValue(), pkm.getDelegate(), LuaInteger.valueOf(meta)).checkboolean();
	}
	public boolean hasTrait(String tag) {
		return tags.contains(tag);
	}

}
