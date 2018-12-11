package github.chorman0773.pokemonsms.net.packet;

import java.io.IOException;

import com.google.gson.JsonObject;

import github.chorman0773.pokemonsms.net.IPacket;
import github.chorman0773.pokemonsms.net.PacketBuffer;
import github.chorman0773.pokemonsms.net.Sizes;
import github.lightningcreations.lclib.Hash;
import github.lightningcreations.lclib.Version;

public class CPacketServerConnect implements IPacket {
	private Version pkmComVersion;
	private JsonObject trainerObj;
	private JsonObject sentryAccount;
	public CPacketServerConnect() {
		// TODO Auto-generated constructor stub
	}
	public CPacketServerConnect(Version v,JsonObject obj,JsonObject acc) {
		pkmComVersion = v;
		trainerObj = obj;
		sentryAccount = acc;
	}

	@Override
	public void read(PacketBuffer buff) throws IOException {
		pkmComVersion = buff.readVersion();
		trainerObj = buff.readJson();
		sentryAccount = buff.readJson();
	}

	@Override
	public void write(PacketBuffer buff) throws IOException {
		buff.writeVersion(pkmComVersion);
		buff.writeJson(trainerObj);
		buff.writeJson(sentryAccount);
	}
	
	public JsonObject getTrainerObject() {
		return trainerObj;
	}
	public Version getRemoteVersion() {
		return pkmComVersion;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 2+Sizes.size(trainerObj)+Sizes.size(sentryAccount);
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public int hashcode() {
		return Hash.hashcode(pkmComVersion)*961+Hash.hashcode(trainerObj.toString())*31+Hash.hashcode(sentryAccount.toString());
	}

}
