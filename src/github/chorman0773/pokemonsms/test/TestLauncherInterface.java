package github.chorman0773.pokemonsms.test;

import java.awt.Window;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.instrument.Instrumentation;
import java.net.URI;
import java.nio.file.Path;

import javax.swing.JFrame;

import github.chorman0773.pokemonsms.game.PokemonSMS;
import github.chorman0773.sentry.annotation.Game;
import github.chorman0773.sentry.cci.CCIVendor;
import github.chorman0773.sentry.linterface.LauncherInterface;
import github.chorman0773.sentry.linterface.ModInterface;

class TestLauncherInterface implements LauncherInterface {
	private String[] args;
	private JFrame frame;
	private Path dir;
	TestLauncherInterface(String[] args,JFrame frame,Path dir){
		this.args = args;
		this.frame = frame;
		this.dir = dir;
	}
	@Override
	public String[] getGameArguments() {
		// TODO Auto-generated method stub
		return args;
	}

	@Override
	public String getProperty(String name) {
		// TODO Auto-generated method stub
		return System.getProperty(name);
	}

	@Override
	public ModInterface[] activeMods() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("activeMods()");
	}

	@Override
	public CCIVendor getLauncherVendor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Window getGameWindow() {
		// TODO Auto-generated method stub
		return frame;
	}

	@Override
	public Path getGameDirectory() {
		// TODO Auto-generated method stub
		return dir;
	}

	@Override
	public Instrumentation getInstrumentation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() {
		frame.setVisible(false);
		frame.dispose();
	}

	@Override
	public Writer getLauncherWriter() {
		// TODO Auto-generated method stub
		return new PrintWriter(System.out);
	}

	@Override
	public SecurityManager getSandbox() {
		// TODO Auto-generated method stub
		return System.getSecurityManager();
	}

	@Override
	public void disableMods() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Game getGameAnnotation() {
		// TODO Auto-generated method stub
		return PokemonSMS.class.getAnnotation(Game.class);
	}
	@Override
	public ModInterface installMod(URI name) throws UnsupportedOperationException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
