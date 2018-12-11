package github.chorman0773.pokemonsms.lua;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

public interface LuaHelpers {
	public static Iterable<LuaValue> overVarargs(final Varargs args){
		return ()->new Iterator<LuaValue>() {
			private int idx = 1;
			public boolean hasNext() {
				return !args.isnoneornil(idx);
			}
			public LuaValue next() {
				if(!hasNext())
					throw new NoSuchElementException();
				return args.arg(idx++);
			}
		};
	}
	public static Iterable<LuaValue> ipairs(final LuaValue val){
		return()->new Iterator<LuaValue>() {
			private int idx = 1;
			public boolean hasNext() {
				return val.get(idx)!=LuaValue.NIL;
			}
			public LuaValue next() {
				if(!hasNext())
					throw new NoSuchElementException();
				return val.get(idx++);
			}
		};
	}
	public static Iterable<Varargs> pairs(final LuaValue val){
		return()->new Iterator<Varargs>() {
			private Varargs pairs = val.next(LuaValue.NIL);

			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return pairs.isnil(0);
			}

			@Override
			public Varargs next() {
				if(pairs.isnil(0))
					throw new NoSuchElementException();
				Varargs ret = pairs;
				pairs = val.next(ret.arg1());
				return pairs;
			}
		};
	}
	
	public static Stream<LuaValue> streamVarargs(Varargs args){
		return StreamSupport.stream(overVarargs(args).spliterator(), false);
	}
}
