package github.chorman0773.pokemonsms.net.tick;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.function.Consumer;

import github.chorman0773.pokemonsms.net.INetHandlerRemote;
import github.chorman0773.pokemonsms.net.IPacket;
import github.lightningcreations.lclib.Synchronized;

public abstract class TickManagerNetwork implements Runnable, AutoCloseable {
	
	private INetHandlerRemote handler;
	private Queue<IPacket> enqueuedPackets;
	private Thread t;
	
	protected TickManagerNetwork(INetHandlerRemote handler) {
		this.handler = handler;
		enqueuedPackets = Synchronized.synchronizedQueue(new LinkedList<>());
		Thread t = new Thread(this::pollPacket);
		t.start();
	}
	
	protected abstract void onTick();
	
	protected abstract void handlePacket(IPacket packet);
	
	
	public final void close() throws InterruptedException {
		t.interrupt();
		t.join();
	}
	
	private void pollPacket() {
		try {
			while(!Thread.interrupted()) {
				IPacket packet = handler.recieve();
				enqueuedPackets.add(packet);
				Thread.sleep(1);
			}
		}catch(IOException | InterruptedException e) {
			
		}
	}
	

	@Override
	public final void run() {
		onTick();
		IPacket p;
		while((p=enqueuedPackets.poll())!=null)
			handlePacket(p);
	}

}
