package github.chorman0773.pokemonsms.net;

import com.google.gson.JsonObject;

public interface Sizes {
	public static int size(String s) {
		int len = s.length();
		if(len>=65536)
			return 65538;
		else
			return len+2;
	}
	public static int size(JsonObject o) {
		return size(o.toString());
	}
	public static int longSize(String s) {
		return 4+s.length();
	}
	public static int longSize(JsonObject o) {
		return longSize(o.toString());
	}
}
