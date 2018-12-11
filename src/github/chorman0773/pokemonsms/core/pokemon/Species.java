package github.chorman0773.pokemonsms.core.pokemon;

import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.LuaValue;

import github.chorman0773.pokemonsms.core.EnumStat;
import github.chorman0773.pokemonsms.core.EnumType;
import github.chorman0773.pokemonsms.core.EventBus;
import github.chorman0773.pokemonsms.core.LuaEventBus;
import github.chorman0773.pokemonsms.core.NamedRegistryEntry;
import github.chorman0773.pokemonsms.lua.Delegable;
import github.chorman0773.pokemonsms.lua.Delegate;
import github.chorman0773.pokemonsms.lua.EnumValue;

public class Species extends NamedRegistryEntry<Species> implements Delegable<Species> {
	public static class PokemonDelegate extends Delegate<Species>{

		public PokemonDelegate(Species obj) {
			super(obj,Species.class);
			// TODO Auto-generated constructor stub
		}
		
	}
	public final PokemonDelegate delegate = new PokemonDelegate(this);
	
	private EventBus bus;
	private int localNum;
	private int nationalNum;
	protected Info inf;
	private int genderThreshold;
	protected static class Info{
		final LuaValue getFormCount;
		final LuaValue getTypes;
		final LuaValue getAbilities;
		final LuaValue getMovesAtLevel;
		final LuaValue getStartMoves;
		final LuaValue getEvolutionMoves;
		final LuaValue getGraphicsPath;
		final LuaValue getUnlocalizedName;
		final LuaValue getForm;
		final LuaValue getBaseStat;
		
		Info(LuaValue val){
			getFormCount = val.get("getFormCount");
			getTypes = val.get("getTypes");
			getAbilities = val.get("getAbilities");
			getMovesAtLevel = val.get("getMovesAtLevel");
			getStartMoves = val.get("getStartMoves");
			getEvolutionMoves = val.get("getEvolutionMoves");
			getGraphicsPath = val.get("getGraphicsPath");
			getUnlocalizedName = val.get("getUnlocalizedName");
			getForm = val.get("getForm");
			getBaseStat = val.get("getBaseStat");
		}
	}
	
	protected Species(LuaValue val,EventBus bus,int localDexNum,int nationalDexNum,int genderThreshold) {
		super(val);
		this.bus = bus;
		this.localNum = localDexNum;
		this.nationalNum = nationalDexNum;
		this.genderThreshold = genderThreshold;
	}
	public Species(LuaValue val) {
		this(val,new LuaEventBus(val.get("eventBus")),val.get("localNum").optint(-1),val.get("nationalNum").optint(-1),val.get("genderThreshold").checkint());
		inf = new Info(val);
	}

	@Override
	public Class<Species> getType() {
		// TODO Auto-generated method stub
		return Species.class;
	}

	@Override
	public Delegate<Species> getDelegate() {
		// TODO Auto-generated method stub
		return delegate;
	}
	
	public int getFormCount() {
		return inf.getFormCount.call(this.getValue()).checkint();
	}
	public EnumType[] getTypes(int form) {
		EnumType[] ret = new EnumType[2];
		LuaValue val = inf.getTypes.call(this.getValue(),LuaInteger.valueOf(form));
		ret[0] = (EnumType)val.get(1).checkuserdata(EnumType.class);
		ret[1] = (EnumType)val.get(2).optuserdata(EnumType.class, EnumType.TYPELESS);
		return ret;
	}
	public int getForm(Pokemon p) {
		if(inf.getForm.isnil())
			return 0;
		else
			return inf.getForm.call(this.getValue(),p.getDelegate()).checkint();
	}
	public int getBaseStat(int form,EnumStat stat) {
		return inf.getBaseStat.call(this.getValue(),LuaInteger.valueOf(form),EnumValue.of(stat)).checkint();
	}
	public EventBus getEventBus() {
		// TODO Auto-generated method stub
		return bus;
	}

}
