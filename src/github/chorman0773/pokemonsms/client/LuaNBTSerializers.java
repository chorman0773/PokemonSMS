package github.chorman0773.pokemonsms.client;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;

import github.chorman0773.pokemonsms.lua.LuaHelpers;
import github.chorman0773.sentry.save.nbt.NBTConstants;
import github.chorman0773.sentry.save.nbt.NBTPrimitive;
import github.chorman0773.sentry.save.nbt.NBTTagBase;
import github.chorman0773.sentry.save.nbt.NBTTagCompound;

public interface LuaNBTSerializers {
	
	static void serializeTable(LuaTable t,NBTTagCompound comp) {
		for(Varargs v:LuaHelpers.pairs(t)) {
			String name = v.checkjstring(1);
			LuaValue val = v.arg(2);
			if(val.istable())
				serializeTable(val.checktable(),comp.getTagCompound(name));
			else if(val.isboolean())
				comp.setBoolean(name, val.checkboolean());
			else if(val.isint())
				comp.setInt(name, val.checkint());
			else if(val.isnumber())
				comp.setDouble(name,val.checkdouble());
			else if(val.isstring())
				comp.setString(name, val.checkjstring());
		}
	}
	static void deserializeTable(LuaTable t,NBTTagCompound comp) {
		for(String name:comp.getKeys()) {
			NBTTagBase tag = comp.get(name);
			if(tag.getTagType()==NBTConstants.TAG_INT)
				t.set(name, ((NBTPrimitive)tag).getInt());
			else if(tag.getTagType()==NBTConstants.TAG_BYTE)
				t.set(name, ((NBTPrimitive)tag).getBoolean()?LuaValue.TRUE:LuaValue.FALSE);
			else if(tag instanceof NBTPrimitive)
				t.set(name, ((NBTPrimitive)tag).getDouble());
			else if(tag.getTagType()==NBTConstants.TAG_COMPOUND) {
				LuaTable next = LuaValue.tableOf();
				deserializeTable(next,(NBTTagCompound)tag);
				t.set(name, next);
			}
		}
	}
}
