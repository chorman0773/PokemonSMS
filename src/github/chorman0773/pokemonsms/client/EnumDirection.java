package github.chorman0773.pokemonsms.client;

/**
 * 
 * @author connor
 */
public enum EnumDirection {
	NORTH, SOUTH, EAST, WEST;
	public static enum Axis{
		X,Y;
		public static Axis fromDirection(EnumDirection dir) {
			switch(dir) {
			case NORTH: return Y;
			case SOUTH: return Y;
			case EAST: return X;
			case WEST: return X;
			}
			throw new Error("Universe is invalid for some reason");
		}
	}
	public static EnumDirection fromPositiveAxis(Axis a) {
		switch(a) {
		case X: return EAST;
		case Y: return NORTH;
		}
		throw new Error("Universe is invalid for some reason");
	}
	public static EnumDirection fromNegativeAxis(Axis a) {
		switch(a) {
		case X: return WEST;
		case Y: return SOUTH;
		}
		throw new Error("Universe is invalid for some reason");
	}
	static {
		ClientSideClassesCheck.check();
	}
	public EnumDirection inverse() {
		switch(this) {
		case EAST: return WEST;
		case NORTH: return SOUTH;
		case SOUTH: return NORTH;
		case WEST: return EAST;
		}
		throw new Error("Universe is invalid for some reason");
	}
}
