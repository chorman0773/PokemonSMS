package github.chorman0773.pokemonsms.core;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import github.chorman0773.pokemonsms.core.Registry.Registries;
import github.chorman0773.pokemonsms.core.bindings.*;
import github.chorman0773.pokemonsms.core.pokemon.Ability;
import github.chorman0773.pokemonsms.core.pokemon.Item;
import github.chorman0773.pokemonsms.core.pokemon.Move;
import github.chorman0773.pokemonsms.core.pokemon.Species;
import github.chorman0773.pokemonsms.internal.SystemInterface;
import github.chorman0773.pokemonsms.lua.CoreLibraryLocator;
import github.chorman0773.pokemonsms.lua.LuaHelpers;
import github.chorman0773.pokemonsms.siding.EnumSide;


public enum InitRegistries {
	instance;
	public final LuaValue pokemon;
	public final LuaValue abilities;
	public final LuaValue items;
	public final LuaValue moves;
	public final LuaValue trainers;
	public final LuaValue npcs;
	public final LuaValue sprites;
	public final LuaValue locations;
	public final LuaValue tiles;
	private final Globals globals;
	private LuaValue require(String name) {
		return globals.loadfile(name+".lua").call(LuaValue.valueOf(name), globals);
	}
	
	private InitRegistries() {
		globals= JsePlatform.standardGlobals();
		globals.set("luajava", LuaValue.NIL);
		globals.set("os", LuaValue.NIL);
		globals.set("coroutine", LuaValue.NIL);
		globals.set("io", LuaValue.NIL);
		LuaValue preload = globals.get("package").get("preload");
		preload.set("Siding", new Siding());
		preload.set("Constants", new Constants());
		preload.set("Utils", new Utils());
		globals.finder = new CoreLibraryLocator(SystemInterface.get().getRootDir());
		pokemon = require("Pokemon");
		abilities = require("Abilities");
		items = require("Items");
		moves = require("Moves");
		for(LuaValue v:LuaHelpers.ipairs(pokemon)) 
			Registries.species.register(new Species(v));
		for(LuaValue v:LuaHelpers.ipairs(abilities))
			Registries.abilities.register(new Ability(v));
		for(LuaValue v:LuaHelpers.ipairs(moves))
			Registries.moves.register(new Move(v));
		for(LuaValue v:LuaHelpers.ipairs(items))
			Registries.items.register(new Item(v));
		
		if(EnumSide.isClient()) {
			tiles = require("Tiles");
			trainers = require("Trainers");
			npcs = require("Npcs");
			sprites = require("Sprites");
			locations = require("Locations");
		}else {
			tiles = null;
			trainers = null;
			npcs = null;
			sprites = null;
			locations = null;
		}
		
		
	}

	public static void init() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
