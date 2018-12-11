package github.chorman0773.pokemonsms.test;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.JFrame;

import github.chorman0773.pokemonsms.game.PokemonSMS;
import github.chorman0773.sentry.annotation.Game;

public class TestRuntime {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				Path dir = Paths.get(System.getenv("HOME"),"pokemonsms/test/");
				if(!Files.exists(dir))
					try {
						Files.createDirectories(dir);
					} catch (IOException e1) {
						throw new RuntimeException(e1);
					}
				
				final PokemonSMS game = new PokemonSMS();
				JFrame frame = new JFrame();
				Game g = PokemonSMS.class.getAnnotation(Game.class);
				frame.setTitle(g.gameName()+" "+g.gameVersion()+" Test");
				TestLauncherInterface linterface =  new TestLauncherInterface(args, frame, dir);
				game.instantiate(args,linterface);
				
				game.setPreferredSize(new Dimension(700,550));
				game.setSize(game.getPreferredSize());
				frame.setContentPane(game);
				frame.setSize(game.getSize());
				frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				frame.addWindowListener(new WindowAdapter() {

					@Override
					public void windowClosing(WindowEvent e) {
						game.exit(0);
						System.exit(0);
					}
					
				});
				game.init();
				game.start();
				frame.setVisible(true);
				game.repaint();
			}
		});
	}

}
