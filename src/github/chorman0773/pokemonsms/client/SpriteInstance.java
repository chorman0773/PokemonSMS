package github.chorman0773.pokemonsms.client;

import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

import github.chorman0773.pokemonsms.core.EventBus;
import github.chorman0773.pokemonsms.core.LuaEventBus;
import github.chorman0773.pokemonsms.core.Registry;
import github.chorman0773.pokemonsms.core.ResourceLocation;
import github.chorman0773.pokemonsms.core.pokemon.NullEventBus;
import github.chorman0773.pokemonsms.internal.Events;
import github.chorman0773.pokemonsms.lua.Coercions;
import github.chorman0773.pokemonsms.lua.Delegable;
import github.chorman0773.pokemonsms.lua.Delegate;
import github.chorman0773.sentry.save.NBTSerializable;
import github.chorman0773.sentry.save.nbt.NBTTagCompound;


public final class SpriteInstance implements Delegable<SpriteInstance>, EventBus {
	private Sprite base;
	private transient ResourceLocation target;
	private transient EventBus distinctBus;
	private int flags;
	private LuaValue extra;
	private transient int prevFlags;
	private transient int x;
	private transient int y;
	private transient boolean drawn;
	private transient boolean drawable;
	private transient EnumDirection facing;
	static {
		ClientSideClassesCheck.check();
	}
	
	private static class SpriteDelegateFunction extends LuaFunction{
		private int callNo;
		SpriteDelegateFunction(int callNo){
			this.callNo = callNo;
		}
		private Object call(SpriteInstance s,Varargs rest) {
			switch(callNo) {
			case 0: return s.getSprite();
			case 1: return s.getTarget();
			case 2: return s.getDirection();
			case 3: return s.getFlags();
			case 4: return s.isDrawable();
			case 5: return s.isUpdatable();
			case 6: return s.hasRendering();
			case 7: return s.hasCollision();
			case 8: return s.hasInteraction();
			case 9: s.setFlags(rest.checkint(1)); break;
			case 10: s.clearFlags(rest.checkint(1)); break;
			case 11: return s.getPositionX(); 
			case 12: return s.getPositionY();
			case 13: s.move(rest.checkint(1), rest.checkint(2)); break;
			case 14: return s.getExtra();
			}
			return null;
		}
		public Varargs invoke(Varargs args) {
			Object o = call(((SpriteInstance)args.arg1().checkuserdata(Sprite.class)),args.subargs(2));
			if(o==null)
				return LuaValue.NONE;
			else
				return Coercions.coerceToLua(o);
		}
	}
	private static class SpriteDelegate extends Delegate<SpriteInstance>{
		private static final SpriteDelegateFunction F_getSprite = new SpriteDelegateFunction(0);
		private static final SpriteDelegateFunction F_getTarget = new SpriteDelegateFunction(1);
		private static final SpriteDelegateFunction F_getDirection = new SpriteDelegateFunction(2);
		private static final SpriteDelegateFunction F_getFlags = new SpriteDelegateFunction(3);
		private static final SpriteDelegateFunction F_isDrawable = new SpriteDelegateFunction(4);
		private static final SpriteDelegateFunction F_isUpdatable = new SpriteDelegateFunction(5);
		private static final SpriteDelegateFunction F_hasRendering = new SpriteDelegateFunction(6);
		private static final SpriteDelegateFunction F_hasCollision = new SpriteDelegateFunction(7);
		private static final SpriteDelegateFunction F_hasInteraction = new SpriteDelegateFunction(8);
		private static final SpriteDelegateFunction F_setFlags = new SpriteDelegateFunction(9);
		private static final SpriteDelegateFunction F_clearFlags = new SpriteDelegateFunction(10);
		private static final SpriteDelegateFunction F_getPositionX = new SpriteDelegateFunction(11);
		private static final SpriteDelegateFunction F_getPositionY = new SpriteDelegateFunction(12);
		private static final SpriteDelegateFunction F_move = new SpriteDelegateFunction(13);
		private static final SpriteDelegateFunction F_getExtra = new SpriteDelegateFunction(14);
		public SpriteDelegate(SpriteInstance obj) {
			super(obj, SpriteInstance.class);
			// TODO Auto-generated constructor stub
		}
		public LuaValue get(String key) {
			switch(key) {
			case "getSprite": return F_getSprite;
			case "getTarget": return F_getTarget;
			case "getDirection": return F_getDirection;
			case "getFlags": return F_getFlags;
			case "isDrawable": return F_isDrawable;
			case "isUpdateble": return F_isUpdatable;
			case "hasRendering": return F_hasRendering;
			case "hasCollision": return F_hasCollision;
			case "hasInteraction": return F_hasInteraction;
			case "setFlags": return F_setFlags;
			case "clearFlags": return F_clearFlags;
			case "getPositionX": return F_getPositionX;
			case "getPositionY": return F_getPositionY;
			case "move": return F_move;
			case "getExtra": return F_getExtra;
			}
			return LuaValue.NIL;
		}
	}
	private static ResourceLocation getOptionalLocation(LuaValue val) {
		return val.isstring()?new ResourceLocation(val.checkjstring()):null;
	}
	private static EventBus getOptionalEventBus(LuaValue val) {
		return !val.isnil()?new LuaEventBus(val):new NullEventBus();
	}
	@SuppressWarnings("unchecked")
	private static <E extends Enum<E>> E optEnum(LuaValue val,Class<E> cl,E def) {
		return (E)val.optuserdata(cl, def);
	}
	private final SpriteDelegate delegate = new SpriteDelegate(this);
	public SpriteInstance(Sprite base,ResourceLocation target,EventBus bus,int flags,LuaValue extra,int x,int y,EnumDirection facing) {
		this.base = base;
		this.target = target;
		this.distinctBus = bus;
		this.flags = flags;
		this.extra = extra;
		this.facing = facing;
		this.x = x;
		this.y = y;
		this.prevFlags = flags;
	}
	public SpriteInstance(Sprite base,int flags,int x,int y,EnumDirection facing) {
		this(base,null,new NullEventBus(),flags,LuaValue.NIL,x,y,facing);
	}
	public SpriteInstance(LuaValue table,int locBaseX,int locBaseY) {
		this(Registry.ClientRegistries.sprites.get(table.get("loc").checkjstring()),getOptionalLocation(table.get("target")),getOptionalEventBus(table.get("eventBus")),table.get("flags").optint(0),table.get("extra"),table.get("x").checkint()+locBaseX,table.get("y").checkint()+locBaseY,optEnum(table.get("direction"),EnumDirection.class,EnumDirection.SOUTH));
	}
	@Override
	public Delegate<SpriteInstance> getDelegate() {
		// TODO Auto-generated method stub
		return delegate;
	}
	
	@Override
	public void raise(int key, Object... parameters) {
		base.getEventBus().raise(key, parameters);
		distinctBus.raise(key, parameters);
	}
	
	public boolean hasCollision() {
		return Sprite.hasCollision(flags)&&base.hasCollision();
	}
	public boolean hasInteraction() {
		return Sprite.hasInteraction(flags)&&base.hasInteraction();
	}
	public boolean hasRendering() {
		return Sprite.hasRendering(flags)&&base.hasRendering();
	}
	public boolean isUpdatable() {
		return Sprite.isUpdatable(flags)&&base.isUpdatable();
	}
	public boolean isDrawable() {
		return drawable&&hasRendering();
	}
	public boolean shouldActivelyUpdate(boolean randTick) {
		return Sprite.shouldActivelyUpdate(flags, randTick)&&base.shouldActivelyUpdate(randTick);
	}
	
	public void onInteract() {
		if(hasInteraction())
			raise(Events.Client.Sprite.Interaction,this);
	}
	public void onCollide() {
		if(hasCollision())
			raise(Events.Client.Sprite.Collide,this);
	}
	public void draw() {
		if(hasRendering()&&drawable) {
			raise(Events.Client.Sprite.Drawn,this);
			drawn = true;
		}
	}
	public void undraw() {
		if(drawn)
			raise(Events.Client.Sprite.Undrawn,this);
		drawn = false;
	}
	public void refresh() {
		if(prevFlags!=flags) {
			if(drawn&&!Sprite.hasRendering(flags))
				undraw();
			else if(isDrawable()&&!drawn)
				draw();
		}
		prevFlags = flags;
	}
	public void setFlags(int flags) {
		this.flags |= flags;
	}
	public void clearFlags(int flags) {
		this.flags &= ~flags;
	}
	public int getFlags() {
		return flags;
	}
	public void randomTick() {
		if(shouldActivelyUpdate(true))
			raise(Events.Client.Sprite.ActiveUpdate,this);
	}
	public void tick() {
		if(shouldActivelyUpdate(false))
			raise(Events.Client.Sprite.ActiveUpdate,this);
	}
	public void update() {
		if(isUpdatable())
			raise(Events.Client.Sprite.PassiveUpdate,this);
	}
	public LuaValue getExtra() {
		return extra;
	}
	public ResourceLocation getTarget() {
		return target;
	}
	public int getPositionX() {
		return x;
	}
	public int getPositionY() {
		return y;
	}
	public void move(int dx,int dy) {
		x += dx;
		y += dy;
	}
	public void setPositionX(int x) {
		this.x = x;
	}
	public void setPositionY(int y) {
		this.y = y;
	}
	public void setDirection(EnumDirection dir) {
		facing = dir;
	}
	public EnumDirection getDirection() {
		return facing;
	}
	public Sprite getSprite() {
		return base;
	}
}
