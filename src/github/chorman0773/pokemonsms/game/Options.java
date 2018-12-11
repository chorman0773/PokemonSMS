package github.chorman0773.pokemonsms.game;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonWriter;

import github.chorman0773.sentry.GameBasic;

public final class Options implements Closeable {
	private Path optionsPath;
	private JsonObject optionsTable;
	public Options(Path topLevel) throws IOException {
		optionsPath = topLevel.resolve("options.json");
		if(!Files.exists(optionsPath))
			Files.createFile(optionsPath);
		else
		try(InputStream read = Files.newInputStream(optionsPath)){
			optionsTable = GameBasic.json.parse(new InputStreamReader(read,StandardCharsets.UTF_8)).getAsJsonObject();
		}
	}
	
	@Override
	public synchronized void close() throws IOException {
		try(OutputStream out = Files.newOutputStream(optionsPath)){
			out.write(optionsTable.toString().getBytes(StandardCharsets.UTF_8));
		}
	}

	public synchronized <E extends Enum<E>> void setOption(String key,E value) {
		String[] path = key.split("\\.");
		JsonObject target = optionsTable;
		for(int i = 0;i<path.length-1;i++)
			target = target.getAsJsonObject(path[i]);
		target.addProperty(path[path.length-1], value.name());
	}
	public synchronized void setOption(String key,int value) {
		String[] path = key.split("\\.");
		JsonObject target = optionsTable;
		for(int i = 0;i<path.length-1;i++)
			target = target.getAsJsonObject(path[i]);
		target.addProperty(path[path.length-1], value);
	}
	public synchronized void setOption(String key,double value) {
		String[] path = key.split("\\.");
		JsonObject target = optionsTable;
		for(int i = 0;i<path.length-1;i++)
			target = target.getAsJsonObject(path[i]);
		target.addProperty(path[path.length-1], value);
	}
	public synchronized void setOption(String key,String value) {
		String[] path = key.split("\\.");
		JsonObject target = optionsTable;
		for(int i = 0;i<path.length-1;i++)
			target = target.getAsJsonObject(path[i]);
		target.addProperty(path[path.length-1], value);
	}
	public synchronized <E extends Enum<E>> E getEnumOption(String key,Class<E> clazz) {
		String[] path = key.split("\\.");
		JsonObject target = optionsTable;
		for(int i = 0;i<path.length-1;i++)
			target = target.getAsJsonObject(path[i]);
		return Enum.valueOf(clazz, target.get(key).getAsString());
	}
	public synchronized int getIntOption(String key) {
		String[] path = key.split("\\.");
		JsonObject target = optionsTable;
		for(int i = 0;i<path.length-1;i++)
			target = target.getAsJsonObject(path[i]);
		return target.get(key).getAsInt();
	}
	public synchronized double getDoubleOption(String key) {
		String[] path = key.split("\\.");
		JsonObject target = optionsTable;
		for(int i = 0;i<path.length-1;i++)
			target = target.getAsJsonObject(path[i]);
		return target.get(key).getAsDouble();
	}
	public synchronized String getStringOption(String key) {
		String[] path = key.split("\\.");
		JsonObject target = optionsTable;
		for(int i = 0;i<path.length-1;i++)
			target = target.getAsJsonObject(path[i]);
		return target.get(key).getAsString();
	}


}
