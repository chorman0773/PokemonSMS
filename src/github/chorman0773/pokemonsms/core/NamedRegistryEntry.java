package github.chorman0773.pokemonsms.core;

import org.luaj.vm2.LuaValue;

import com.google.gson.JsonObject;

import github.chorman0773.pokemonsms.core.IRegistryEntry.Impl;
import github.chorman0773.sentry.text.TextComponent;

public abstract class NamedRegistryEntry<T extends NamedRegistryEntry<T>> extends Impl<T> {
	private TextComponent name;
	public static TextComponent fromValue(LuaValue unName) {
		if(unName.isuserdata(TextComponent.class))
			return (TextComponent)unName.checkuserdata(TextComponent.class);
		else if(unName.isstring())
			return new TextComponent.TranslatebleTextComponent(unName.tojstring());
		else if(unName.istable()) {
			JsonObject o = new JsonObject();
			o.addProperty("type", unName.get("type").checkjstring());
			o.addProperty("text", unName.get("text").checkjstring());
			TextComponent comp = TextComponent.fromJson(o);
			if(!unName.get("continue").isnil())
				comp.appendComponent(fromValue(unName.get("continue")));
			return comp;
		}else
			return new TextComponent.TextComponentString(unName.tojstring());
	}
	protected NamedRegistryEntry(LuaValue val) {
		super(val);
		if(!val.get("name").isnil())
			name = fromValue(val.get("name"));
		else
			name = new TextComponent.TranslatebleTextComponent(this.getRegistryName().getPath().replaceAll("/", "."));
	}
	
	public TextComponent getName() {
		return name;
	}
	public String toString() {
		return name.getAsFormatted().toUnformattedString();
	}

}
