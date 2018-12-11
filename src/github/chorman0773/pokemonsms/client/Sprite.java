package github.chorman0773.pokemonsms.client;

import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

import github.chorman0773.pokemonsms.core.EventBus;
import github.chorman0773.pokemonsms.core.IRegistryEntry.Impl;
import github.chorman0773.pokemonsms.core.LuaEventBus;
import github.chorman0773.pokemonsms.lua.Coercions;
import github.chorman0773.pokemonsms.lua.Delegable;
import github.chorman0773.pokemonsms.lua.Delegate;

public class Sprite extends Impl<Sprite> implements Delegable<Sprite> {
	static {
		ClientSideClassesCheck.check();
	}
	public static interface Flags{
		public static int Removed = 1, Destroyed = 2,
				NoRendering = 4, NoInteraction = 8,
				NoCollision = 16, NoUpdates = 32,
				RequiresActiveUpdates = 64;
	}
	private static class SpriteDelegateFunction extends LuaFunction{
		private int callNo;
		SpriteDelegateFunction(int callNo){
			this.callNo = callNo;
		}
		private Object call(Sprite s,Varargs rest) {
			switch(callNo) {
			case 0: return s.getRegistryName().toString();
			case 1: return s.isName(rest.checkjstring(1));
			case 2: return s.isUpdatable();
			case 3: return s.hasCollision();
			case 4: return s.hasInteraction();
			case 5: return s.hasRendering();
			}
			return null;
		}
		public Varargs invoke(Varargs args) {
			Object o = call(((Sprite)args.arg1().checkuserdata(Sprite.class)),args.subargs(2));
			if(o==null)
				return LuaValue.NONE;
			else
				return Coercions.coerceToLua(o);
		}
	}
	protected static class SpriteDelegate extends Delegate<Sprite>{
		private final SpriteDelegateFunction F_getResourceLocation = new SpriteDelegateFunction(0);
		private final SpriteDelegateFunction F_isSprite = new SpriteDelegateFunction(1);
		private final SpriteDelegateFunction F_isUpdatable = new SpriteDelegateFunction(2);
		private final SpriteDelegateFunction F_hasCollision = new SpriteDelegateFunction(3);
		private final SpriteDelegateFunction F_hasInteraction = new SpriteDelegateFunction(4);
		private final SpriteDelegateFunction F_hasRendering = new SpriteDelegateFunction(5);
		public SpriteDelegate(Sprite obj) {
			super(obj,Sprite.class);
		}
		public LuaValue get(String key) {
			switch(key) {
			case "getResourceLocation":return F_getResourceLocation;
			case "isSprite": return F_isSprite;
			case "isUpdatable": return F_isUpdatable;
			case "hasCollision": return F_hasCollision;
			case "hasInteraction": return F_hasInteraction;
			case "hasRendering": return F_hasRendering;
			}
			return LuaValue.NIL;
		}
	}
	private final SpriteDelegate delegate = new SpriteDelegate(this);
	private EventBus bus;
	private int flags;
	protected Sprite(LuaValue val,EventBus bus,int flags) {
		super(val);
		this.bus = bus;
		this.flags = flags;
	}
	public Sprite(LuaValue val) {
		this(val,new LuaEventBus(val.get("eventBus")),val.get("flags").checkint());
	}
	@Override
	public final Class<Sprite> getType() {
		// TODO Auto-generated method stub
		return Sprite.class;
	}
	
	public static boolean hasCollision(int flags) {
		return (flags&(Flags.NoCollision|Flags.Removed|Flags.Destroyed))==0;
	}
	public static boolean hasInteraction(int flags) {
		return (flags&(Flags.NoInteraction|Flags.Removed|Flags.Destroyed))==0;
	}
	public static boolean hasRendering(int flags) {
		return (flags&(Flags.NoRendering|Flags.Removed|Flags.Destroyed))==0;
	}
	public static boolean isUpdatable(int flags) {
		return (flags&(Flags.NoUpdates|Flags.Destroyed))==0;
	}
	public static boolean shouldActivelyUpdate(int flags,boolean randTick) {
		return isUpdatable(flags)&&(randTick||((flags&Flags.RequiresActiveUpdates)==0));
	}
	
	public EventBus getEventBus() {
		return bus;
	}
	public boolean hasCollision() {
		return (flags&(Flags.NoCollision|Flags.Removed|Flags.Destroyed))==0;
	}
	public boolean hasInteraction() {
		return (flags&(Flags.NoInteraction|Flags.Removed|Flags.Destroyed))==0;
	}
	public boolean hasRendering() {
		return (flags&(Flags.NoRendering|Flags.Removed|Flags.Destroyed))==0;
	}
	public boolean isUpdatable() {
		return (flags&(Flags.NoUpdates|Flags.Destroyed))==0;
	}
	
	
	/**
	 * Checks if a given tick should trigger an active update. 
	 * A sprite which has updates disabled or is destroyed will never be updated. (removed sprites still recieve updates)
	 * Otherwise if its a random tick the sprite should be updated.
	 * If the sprite requires active updates, then it should.  
	 * @param randTick true if this is a random tick update, rather than a consistent tick update.
	 */
	public boolean shouldActivelyUpdate(boolean randTick) {
		return isUpdatable()&&(randTick||((flags&Flags.RequiresActiveUpdates)==0));
	}
	@Override
	public Delegate<Sprite> getDelegate() {
		// TODO Auto-generated method stub
		return delegate;
	}

}
