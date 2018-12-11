package github.chorman0773.pokemonsms.net;

import java.io.IOException;

/**
 * Represents a PkmCom Packet.
 * @author connor
 *
 */
public interface IPacket {
	/**
	 * Reads the Packet Payload from a buffer.
	 * @param buff the buffer to read from
	 * @throws IOException if reading the payload throws an exception or a Precondition is violated
	 */
	void read(PacketBuffer buff)throws IOException;
	/**
	 * Writes the Packet Payload to a buffer
	 * @param buff the buffer to write to
	 * @throws IOException if writing the payload throws an exception
	 */
	void write(PacketBuffer buff)throws IOException;
	/**
	 * Gets the Size of the Packets Payload.
	 */
	int size();
	/**
	 * Gets the Packet Id to encoded in the header
	 */
	int getId();
	
	/**
	 * Gets the Validation Hashcode of the Packet's Payload. 
	 * Provided with different casing to mandate implementation (as Java Object.hashCode() is unsuitable)
	 */
	int hashcode();
}
