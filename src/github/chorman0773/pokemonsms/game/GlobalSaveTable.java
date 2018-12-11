package github.chorman0773.pokemonsms.game;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.sqlite.JDBC;

import github.chorman0773.sentry.GameBasic;
import github.chorman0773.sentry.text.TextComponent;

public enum GlobalSaveTable {
	INSTANCE;
	private final Path savePath;
	private final List<GSTEntry> entries;
	private Connection conn;

	GlobalSaveTable(){
		savePath = GameBasic.getInstance(PokemonSMS.class).getDirectory().resolve("saves");
		entries = new ArrayList<>();
		try {
			loadEntries();
		} catch (SQLException e) {
			throw new Error(e);
		}
	}
	private static class GSTEntry{
		private int slot;
		private String relativePath;
		private TextComponent nameComponent;
		private Duration saveTime;
		private int dexSeenCount;
		private int dexCaughtCount;
		private String b64Icon;
		private boolean encr;
	}
	private void loadEntries() throws SQLException {
		String sqliteUri = "sqlite:"+savePath.resolve("saves.pkmdb").toString();
		conn = JDBC.createConnection(sqliteUri, new Properties());
		
	}
}
