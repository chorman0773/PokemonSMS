package github.chorman0773.pokemonsms.lua;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.luaj.vm2.lib.ResourceFinder;

public class CoreLibraryLocator implements ResourceFinder {
	private Path baseDir;
	public CoreLibraryLocator(Path baseDir) {
		this.baseDir = baseDir;
	}

	@Override
	public InputStream findResource(String filename) {
		try {
			Path libDir = baseDir.resolve("lib/lua/");
			{
				Path target= libDir.resolve(filename);
				System.out.println(target.toString());
				if(Files.exists(target))
					return Files.newInputStream(target);
			}
			Path modsDir = baseDir.resolve("mods/");
			for(Path p:Files.newDirectoryStream(libDir)) {
				if(!Files.isDirectory(p))
					continue;
				Path target= p.resolve(filename);
				System.out.println(target.toString());
				if(Files.exists(target))
					return Files.newInputStream(target);
			}
			if(Files.exists(modsDir))
				for(Path mod:Files.newDirectoryStream(modsDir)) {
					Path target = mod.resolve(filename);
					if(Files.exists(target))
						return Files.newInputStream(target);
				}
			return null;
		}catch(IOException e) {
			return null;
		}	
	}

}
