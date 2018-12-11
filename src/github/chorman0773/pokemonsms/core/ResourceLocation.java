package github.chorman0773.pokemonsms.core;

import java.util.Set;

import github.lightningcreations.lclib.Hash;
import github.lightningcreations.lclib.annotation.Literal;
import github.lightningcreations.lclib.annotation.Pattern;
import github.lightningcreations.lclib.annotation.Constant;

@Literal
@Pattern("[_A-Za-z][_\\w]*:[_A-Za-z][_\\w]*(\\\\[_A-Za-z][_\\w]*)*")
public final class ResourceLocation implements Comparable<ResourceLocation> {
	private final String domain, path;
	
	@Constant
	public static ResourceLocation valueOf(String name) {
		return new ResourceLocation(name);
	}
	
	@Constant
	private ResourceLocation(String[] str) {
		this(str[0],str[1]);
	}
	@Constant
	public ResourceLocation(String name) {
		this(name.split(":"));
	}
	@Constant
	public ResourceLocation(String domain,String path) {
		this.domain = domain;
		this.path = path;
	}
	
	@Constant
	@Override
	public int compareTo(ResourceLocation o) {
		int comp;
		if((comp =domain.compareToIgnoreCase(o.domain))!=0)
			return comp;
		return path.compareToIgnoreCase(o.path);
	}
	
	@Constant
	@Override
	public int hashCode() {
		return Hash.hashcode(domain)*31+Hash.hashcode(path);
	}
	
	public boolean isReserved() {
		return Registry.Reserved.domains.contains(domain)||domain.startsWith("__")||path.startsWith("__")
				||path.contains("/__");
	}
	
	@Constant
	@Override
	public boolean equals(Object o) {
		if(o==null)
			return false;
		else if(o==this)
			return true;
		else if(o.getClass()!=ResourceLocation.class)
			return false;
		else
			return compareTo((ResourceLocation)o)==0;
	}
	
	@Constant
	public String getDomain() {
		return domain;
	}
	@Constant
	public String getPath() {
		return path;
	}
	@Constant
	public String toString() {
		return domain+":"+path;
	}
}
