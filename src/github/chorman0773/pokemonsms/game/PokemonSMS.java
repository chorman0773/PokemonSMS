package github.chorman0773.pokemonsms.game;

import java.awt.Graphics;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import github.chorman0773.pokemonsms.internal.SystemInterface;
import github.chorman0773.pokemonsms.siding.EnumSide;
import github.chorman0773.sentry.GameBasic;
import github.chorman0773.sentry.annotation.Game;
import github.chorman0773.sentry.generic.GenericGame;
import github.chorman0773.sentry.text.I18N;

@Game(gameId = "pokemonsms", gameName = "PokemonSMS", gameVersion = "1.0", serialId = -6093277751799286295L, uuid = "f008f340-bff6-11e8-a355-529269fb1459")
public final class PokemonSMS extends GenericGame implements SystemInterface {

	private List<Runnable> tickables = new ArrayList<>();
	private Object runLock = new Object();
	private Options opts;
	private static final long serialVersionUID = -6093277751799286295L;
	
	private I18N getLanguage() throws JsonIOException, JsonSyntaxException, IOException {
		String lang = opts.getStringOption("general.language");
		Path target = this.getDirectory().resolve("lib/l18n/"+lang+".json");
		JsonObject o = GameBasic.json.parse(Files.newBufferedReader(target)).getAsJsonObject();
		return new I18N(o);
	}
	
	public PokemonSMS() {
		super(60, 40);
		assert EnumSide.isClient();
	}
	protected void doInit() throws IOException {
		opts = new Options(this.getDirectory());
		GameBasic.lang = getLanguage();
	}
	protected void destroyGame() throws IOException{
		opts.close();
	}
	public void registerTickable(Runnable tickable) {
		synchronized(runLock) {
			tickables.add(tickable);
		}
	}

	@Override
	public void tick() {
		synchronized(runLock) {
			for(Runnable t:tickables)
				t.run();
		}
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}
	@Override
	protected void destroyOverride() {
		try {
			opts.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public Path getRootDir() {
		// TODO Auto-generated method stub
		return this.getDirectory();
	}
	@Override
	protected void paintComponent(Graphics g) {
		
	}

}
