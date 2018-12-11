package github.chorman0773.pokemonsms.siding;

import github.chorman0773.sentry.GameBasic;

public enum EnumSide {
	CLIENT, SERVER, COMMON;
	private static EnumSide currentSide =GameBasic.getInstance()!=null?CLIENT:SERVER;
	public static EnumSide current() {
		return currentSide;
	}
	public static boolean isClient() {
		return current()==CLIENT;
	}
	public static boolean isServer() {
		return current()==SERVER;
	}
}
