package github.chorman0773.pokemonsms.net;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import github.chorman0773.sentry.GameBasic;
import github.lightningcreations.lclib.Version;

/**
 * Wrapper class for PkmCom Buffered IO.
 * MB IO is implemented using Big-Endian Byte Order.<br/>
 * PacketBuffer does not Fully Implement DataInput or DataOutput, 
 * it only implements the functions necessary to satisfy PkmCom.<br/>
 * By this statement, PacketBuffer does not support the readLine(), writeBytes(), or writeChars(), readChar(), or writeChar()
 * methods of DataInput or DataOutput. These methods will unconditionally throw an IOException<br/>
 * PacketBuffer is implemented in terms of an Internal Byte Array. As all position information is
 *  shared by reading and writing it is undefined behavior to both read and write to a single packet buffer.
 * The exception is that if the reset() method is called, it may be reused for any purpose.<br/>
 * PacketBuffer also supports IO on Versions, UUIDs, Instants, Durations, Unsigned Ints,
 * JsonObjects, Long Strings, and Long Json Objects, as specified by the PkmComProtocol Specification.<br/>
 * These methods are implemented as Specified in that document (TODO Link PkmComProtocol Spec).<br/>
 * The PokemonData Structure is not directly supported by PacketBuffer (a standalone class will Provide that support)<br/>
 * @author connor
 */
public class PacketBuffer implements DataInput, DataOutput {
	private byte[] buffer;
	private int capacity;
	private int position;
	private void resize(int nCap) {
		byte[] nbuff = new byte[nCap];
		System.arraycopy(buffer, 0, nbuff, 0, capacity);
		capacity = nCap;
		buffer = nbuff;
	}
	public PacketBuffer() {
		buffer = new byte[16];
		capacity = 16;
	}
	public PacketBuffer(byte[] b) {
		buffer = b;
		capacity = b.length;
	}
	public byte[] getBytes() {
		return buffer;
	}
	public int getLength() {
		return position;
	}
	@Override
	public void write(int b) throws IOException {
		if(position==capacity)
			resize(capacity+10);
		buffer[position++] = (byte)b;
	}
	private String throwStringIOError(String alternative)throws IOException{
		throw new IOException("Raw String IO is not specified by PkmCom and therefore is not implemented (Use "+alternative+" instead)");
	}
	private char throwCharIOError(String alternative)throws IOException{
		throw new IOException("Java Character IO is not specified by PkmCom and therefore is not implemented. (Use "+alternative+" instead)");
	}
	@Override
	public void write(byte[] b) throws IOException {
		write(b,0,b.length);
	}
	@Override
	public void write(byte[] b, int off, int len) throws IOException {
		if((position+len)>capacity)
			resize(capacity+len);
		System.arraycopy(b, off, buffer, position, len);
		position += len;
	}
	@Override
	public void writeBoolean(boolean v) throws IOException {
		write(v?1:0);
	}
	@Override
	public void writeByte(int v) throws IOException {
		write(v);
	}
	@Override
	public void writeShort(int v) throws IOException {
		write(v>>8);
		write(v);
	}
	@Override
	public void writeChar(int v) throws IOException {
		throwCharIOError("writeShort(int)");
	}
	@Override
	public void writeInt(int v) throws IOException {
		write(v>>24);
		write(v>>16);
		write(v>>8);
		write(v);
	}
	@Override
	public void writeLong(long v) throws IOException {
		writeInt((int)(v>>32));
		writeInt((int)v);
	}
	@Override
	public void writeFloat(float v) throws IOException {
		writeInt(Float.floatToRawIntBits(v));
	}
	@Override
	public void writeDouble(double v) throws IOException {
		writeLong(Double.doubleToRawLongBits(v));
	}
	@Override
	public void writeBytes(String s) throws IOException {
		throwStringIOError("writeUTF(String)");
	}
	@Override
	public void writeChars(String s) throws IOException {
		throwStringIOError("writeUTF(String)");
	}
	@Override
	public void writeUTF(String s) throws IOException {
		int len = s.length()&0xffff;
		writeShort(len);
		byte[] b = s.getBytes(StandardCharsets.UTF_8);
		write(b,0,len);
	}
	
	public void writeVersion(Version v)throws IOException{
		writeShort(v.getEncoded());
	}
	public void writeUUID(UUID id)throws IOException{
		writeLong(id.getMostSignificantBits());
		writeLong(id.getLeastSignificantBits());
	}
	public void writeInstant(Instant i)throws IOException{
		writeLong(i.getEpochSecond());
		writeInt(i.getNano());
	}
	public void writeDuration(Duration d)throws IOException{
		writeLong(d.getSeconds());
		writeInt(d.getNano());
	}
	public void writeJson(JsonObject o)throws IOException{
		writeUTF(o.toString());
	}
	public void writeString(String s)throws IOException{
		writeUTF(s);
	}
	public void writeLongJson(JsonObject o)throws IOException{
		writeLongString(o.toString());
	}
	public void writeLongString(String s)throws IOException{
		writeInt(s.length());
		write(s.getBytes(StandardCharsets.UTF_8));
	}
	@Override
	public void readFully(byte[] b) throws IOException {
		readFully(b,0,b.length);
	}
	@Override
	public void readFully(byte[] b, int off, int len) throws IOException {
		if((position+len)>capacity)
			throw new EOFException("Ran out of bytes");
		System.arraycopy(buffer,position,b,off,len);
		position +=len;
	}
	
	private int readSingle()throws IOException{
		if(position==capacity)
			throw new EOFException("Ran out of bytes");
		return buffer[position++]&0xff;
	}
	
	@Override
	public int skipBytes(int n){
		position += n;
		if(position>capacity) {
			n = position-capacity;
			position = capacity;
		}
		return n;
	}
	@Override
	public boolean readBoolean() throws IOException {
		return readUnsignedByte()!=0;
	}
	@Override
	public byte readByte() throws IOException {
		return (byte)readSingle();
	}
	@Override
	public int readUnsignedByte() throws IOException {
		return readSingle();
	}
	@Override
	public short readShort() throws IOException {
		// TODO Auto-generated method stub
		return ((short)(readSingle()<<8|readSingle()));
	}
	@Override
	public int readUnsignedShort() throws IOException {
		return readSingle()<<8|readSingle();
	}
	@Override
	public char readChar() throws IOException {
		return throwCharIOError("readUnsignedShort()");
	}
	@Override
	public int readInt() throws IOException {
		// TODO Auto-generated method stub
		return readSingle()<<24|readSingle()<<16|readSingle()<<8|readSingle();
	}
	@Override
	public long readLong() throws IOException {
		// TODO Auto-generated method stub
		return readInt()<<32L|readInt();
	}
	@Override
	public float readFloat() throws IOException {
		float ret = Float.intBitsToFloat(readInt());
		if(Float.isNaN(ret))
			throw new ProtocolError("Float Precondition violation: float must not be a NaN");
		return ret;
	}
	@Override
	public double readDouble() throws IOException {
		double ret = Double.longBitsToDouble(readLong());
		if(Double.isNaN(ret))
			throw new ProtocolError("Double Precondition violation: double must not be a NaN");
		return ret;
	}
	@Override
	public String readLine() throws IOException {
		return throwStringIOError("writeUTF(String)");
	}
	@Override
	public String readUTF() throws IOException {
		
		int len = readUnsignedShort();
		byte[] b = new byte[len];
		readFully(b);
		return decode(b);
	}
	
	private String decode(byte[] b) throws IOException {
		CharsetDecoder dec = StandardCharsets.UTF_8.newDecoder()
				.onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
		
		try {
			CharBuffer ret = dec.decode(ByteBuffer.wrap(b));
			return ret.toString();
		}catch(CharacterCodingException e) {
			throw new ProtocolError("UTF-8 Precondition Violation",e);
		}
	}
	
	public Version readVersion() throws IOException {
		return Version.read(this);
	}
	public UUID readUUID()throws IOException{
		return new UUID(readLong(),readLong());
	}
	public Instant readInstant()throws IOException{
		long s = readLong();
		int nanos = readInt();
		if(nanos<0||nanos>=1_000_000_000)
			throw new ProtocolError("Instant Precondition Violation, Nanos Out Of Range");
		else if(s>31556889864401399L||s<-31556889864401400L)
			throw new ProtocolError("Instant Precondition Violation, Seconds out of range");
		return Instant.ofEpochSecond(s, nanos);
	}
	public Duration readDuration()throws IOException{
		long s = readLong();
		int nanos = readInt();
		if(nanos<0||nanos>=1_000_000_000)
			throw new ProtocolError("Duration Precondition Violation, Nanos Out Of Range");
		else if(s>31556889864401399L||s<-31556889864401400L)
			throw new ProtocolError("Duration Precondition Violation, Seconds out of range");
		return Duration.ofSeconds(s, nanos);
	}
	public String readString()throws IOException{
		return readUTF();
	}
	private JsonObject parse(String str) throws IOException {
		JsonElement e = GameBasic.json.parse(str);
		if(!e.isJsonObject())
			throw new ProtocolError("Json Precondition Violation, Json Requires JsonObject");
		return e.getAsJsonObject();
	}
	public JsonObject readJson()throws IOException{
		return parse(readString());
	}
	public String readLongString()throws IOException{
		int len = readInt();
		byte[] b = new byte[len];
		readFully(b);
		return decode(b);
	}
	public JsonObject readLongJson()throws IOException{
		return parse(readLongString());
	}
	public long readUnsignedInt()throws IOException{
		return readInt()&0xffffffff;
	}

}
