import github.chorman0773.sentry.GameBasic;

/**
 * 
 */
/**
 * @author connor
 *
 */
module github.chorman0773.pokemonsms {
	requires transitive java.desktop;
	requires transitive github.chorman0773.sentry;
	requires transitive luaj;
	requires transitive github.lightningcreations.lclib;
	requires transitive github.chorman0773.pkmcom;
	requires sqlite;
	requires java.base;
	requires java.sql;
	exports github.chorman0773.pokemonsms;
	exports github.chorman0773.pokemonsms.client;
	exports github.chorman0773.pokemonsms.core;
	exports github.chorman0773.pokemonsms.game;
	exports github.chorman0773.pokemonsms.core.pokemon;
	exports github.chorman0773.pokemonsms.core.bindings;
	exports github.chorman0773.pokemonsms.core.battle;
	exports github.chorman0773.pokemonsms.lua;
	exports github.chorman0773.pokemonsms.siding;
	provides GameBasic with github.chorman0773.pokemonsms.game.PokemonSMS;
}