# Info and Copyright Notice #

## Copyright ##
PokemonSMS Public Specification Project, Copyright 2018 Connor Horman
Pokemon, the Pokemon Logo, and all Official Pokemon are Copyright Nintendo and Game Freak. This Project is in no way affiliated with Nintendo or Game Freak, and disclaims all relation with the above parties. This project is intended as a Fan Game, or as Parody of Legitimate Pokemon titles, and no Concreate Game produced using this project should be considered legitimate or affiliated to the above companies, unless they provide official consent to the connection. This project, and all games produced using this specification intend no copyright infringement or Intellectual Property theft of any kind.<br/><br/>


The PkmCom Protocol Abstract Protocol Layout Definition("This Document"), provided by the PokemonSMS Public Specification Project ("This Project"), is Copyright Connor Horman("The Owner"), 2018. 
Using the license specified by the project, you may, with only the restrictions detailed below,
(a)Use this document to produce a complete or partial implementation of PokemonSMS, 
(b)Use this document as reference material to create other related projects or derivative works,
(c)Transmit and/or Distribute Verbatim Copies of this document,
(d)Transmit and/or Distribute Partial copies of this document, which link to this document and include this copyright notice,
(e)Use this document as a guideline to design other specifications for projects, related or not,
(f)Quote parts of this document for works of any kind, that link to this document,
(g)Use part or the whole of this document for any purpose which does not constitute copyright infringement under the intellectual property laws of your jurisdiction.
<br/><br/>
The following restrictions (and only the following restrictions) apply to the above:
You may not, under any circumstances, 
(a)Claim that this document, or any part of it, belongs to you, 
(b)Use this document in any way that would be unlawful in your jurisdiction, or in Canada, 
(c)Use this document in a way that would infringe upon the copyright of any person, organization, or corporation, without prior written consent from that entity or an entity which has been designated by that company as a legal authority on their behalf, including the Owner, or Sentry Game Engine.
<br/><br/>
  This Document, and this project are distributed with the intention that they will be useful and complete. However this document and this project are provided on an AS-IS basis, without any warranties of Any Kind, including the implied warranties of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. As such, any person using this document for any reason does so at their own risk.  By using this document, you explicitly agree to release The Owner, and any person who might have distributed a copy of this document to you from all liability connected with your use of this document
<br/><br/>

## Information ##
The PkmCom Abstract Protocol Layout defines how PkmCom structures Packets, how errors in general are checked and handled, as well as the relationship between the Server and the Client, and the Overview of the Initial Connection Handshake. The APL does NOT define the Concrete Packet Definitions, or Specifics about the Concrete protocol definition. (See /Specifications/PkmComCompleteDefinition.md for actual structure detail).




# PkmCom Apl [pkmcom] #

## Types [pkmcom.types] ###


PkmCom Packets are divided into Types. These types define how many bytes a field takes up (size), how that field is verified (hashcode), and what Preconditions are implied by the field.<br/>
All Multibyte Data types are read and written in Big-Endian (network) Byte order. (So a short s is written as (s>>8)0xff followed by s&0xff.<br/><br/>

### Type List [pkmcom.types.basic] ###


The PkmCom APL defines 21 Types by default. The types are as Such:
<table>
	<tr>
		<th>Type name</th>
		<th>Size (bytes)</th>
		<th>Overview</th>
		<th>Notes</th>
	</tr>
	<tr>
		<td>Byte</td>
		<td>1</td>
		<td>An Unsigned 1-byte Integer in [0,256)
  		<td></td>
  	</tr>
  	<tr>
  		<td>Signed byte</td>
  		<td>1</td>
  		<td>A signed (2s Compliment) 1-byte integer in [-128,128)</td>
  		<td></td>
  	</tr>
  	<tr>
  		<td>Short</td>
  		<td>2</td>
  		<td>A Signed (2s Compliment) 2-byte Integer in [-32768,32768)</td>
  		<td></td>
	</tr>
	<tr>
		<td>Unsigned Short</td>
		<td>2</td>
		<td>An Unsigned 2-byte Integer in [0,65536)</td>
		<td></td>
	</tr>
	<tr>
		<td>Int</td>
		<td>4</td>
		<td>A Signed (2s Compliment) 4-byte Integer in [-2147483648,2147483648)</td>
		<td></td>
	</tr>
	<tr>
		<td>Unsigned Int</td>
		<td>4</td>
		<td>An Unsigned 4-byte Integer in [0,4294967296)</td>
		<td>The Base Implementation only uses Unsigned Int Bitflags, and as the Hashcode field of Packet</td>
	</tr>
	<tr>
		<td>Long</td>
		<td>8</td>
		<td>A Signed (2s Compliment) 8-byte Integer in [-9223372036854775808,9223372036854775808)</td>
		<td></td>
	</tr>
	<tr>
		<td>T Enum</td>
		<td>sizeof T</td>
		<td>Same as T, but it can only represent a defined set of values. By default, its a Protocol Error to send any unspecified values in an Enum Field</td>
		<td>T Enum is only specified for Byte, Signed Byte, Short, Unsigned Short, Int, Unsigned Int, and Long. The values which a particular Enum field are defined for is specified where its used</td>
	</tr>
	<tr>
		<td>T Bitflag</td>
		<td>sizeof T</td>
		<td>Same as T, but it's used in the context to represent a set of Boolean Values (which is more Efficient then Boolean Array). Specifically a Bitflag is considered to store sizeof T*8 separate boolean values.</td>
		<td>T Bitfield is only specified for Byte, Signed Byte, Short, Unsigned Short, Int, Unsigned Int, Long, and UUID. The Base Implementation only uses Byte, and Unsigned Int Bitflags</td>
	</tr>
	<tr>
		<td>T Array</td>
		<td>n*sizeof T</td>
		<td>Stores n values of T. n is the length of the array, which is implied by the context</td>
		<td>Static Length Arrays are primarily used to abrieviate otherwise long packet defintions. Dynamic Length Arrays may have the Length given by annother field. Otherwise, the length prefix has to be stored separately. A T Array with a non-zero size should be considered to hold the same preconditions as its element</td>
	</tr>
	<tr>
		<td>String</td>
		<td>2+n</td>
		<td>Stores an Unsigned Short Length Prefix, followed by that many bytes of a String.</td>
		<td>The String encoded in Java's Modified UTF-8 Format (see https://docs.oracle.com/javase/10/docs/api/java/io/DataInput.html).</td>
	</tr>
	<tr>
		<td>Json</td>
		<td>2+n</td>
		<td>Same as Equivalent String. Must represent a Valid Json Object.</td>
		<td></td>
	</tr>
	<tr>
		<td>Long String</td>
		<td>4+n</td>
		<td>Same as String, except the Length prefix is a positive Int value.</td>
		<td>Rarely Used. Same Encoding as String</td>
	</tr>
	<tr>
		<td>Long Json</td>
		<td>4+n</td>
		<td>Same as Json, except the Length prefix is a positive Int value.</td>
		<td>Rarely Used. Same Encoding as String</td>
	</tr>
	<tr>
		<td>Float</td>
		<td>4</td>
		<td>An 4-byte Single-precision (binary32) IEEE754 floating point number.</td>
		<td></td>
	</tr>
	<tr>
		<td>Double</td>
		<td>8</td>
		<td>An 8-byte Double-precision (binary64) IEEE754 floating point number.</td>
		<td></td>
	</tr>
	<tr>
		<td>Boolean</td>
		<td>1</td>
		<td>Stores a single Boolean value as if by a Byte Enum, given that 0 is false, and 1 is true.</td>
		<td></td>
	</tr>
	<tr>
		<td>Version</td>
		<td>2</td>
		<td>Stores a Version as if by (major-1)<<8|minor. (1 byte per component)</td>
		<td>The Format the Version is stored as is the Sentry Version Encoding</td>
	</tr>
	<tr>
		<td>UUID</td>
		<td>16</td>
		<td>Stores a UUID as 2 longs, storing the Most Significant 64-bits, followed by the Least Significant 64-bits</td>
		<td></td>
	</tr>
	<tr>
		<td>Instant</td>
		<td>12</td>
		<td>Stores an instant in time as Long Seconds since Epoch (January 1st, 1970 at 12:00:00 AM GMT) and Int Nanos of Second.</td>
		<td>The definition (and formatting) of Instant used is Specified by the Java Time API (A Full Specification of this definition is located at <https://docs.oracle.com/javase/10/docs/api/java/time/package-summary.html>). Note that Despite Nanosecond Precision being defined, it is not necessary that Instants are computed to Nanosecond Precision, though at least Milisecond Precision is required.</td>
	</tr>
	<tr>
		<td>Duration</td>
		<td>12</td>
		<td>Stores a Duration of Time as Long Seconds and Int Nanos of Second</td>
		<td>The definition of Duration used is specified by the Java Time API</td>
	</tr>
</table>

### Structure Types [pkmcom.types.struct] ###

PkmCom also specifies Structure Types, (though none such types are defined in The PkmCom APL). 
Structure Types are defined to contain fields of any other type defined in PkmCom

The Structure type defines its own size, hashcode algorithm, content, and rules. 
Structure types can contain other Structure types<br/><br/>
Any example of a Structure type is as follows:
<table>
	<tr>
		<th>Field Name</th>
		<th>Type</th>
		<th>Notes</th>
	</tr>
	<tr>
		<td>Example Byte</td>
		<td>Byte</td>
		<td>An Example Byte Value</td>
	</tr>
	<tr>
		<td>Example Unsigned Int Bitflag</td>
		<td>Unsigned Int Bitflag</td>
		<td>An Example Bitflag</td>
	</tr>
	<tr>
		<td>Example Structure Field</td>
		<td>Another Structure Type</td>
		<td>An example of using another structure within a Structure type</td>
	</tr>
</table>
<br/>
In general: The size of a structure type is the total size of its fields, and the hashcode of a structure type is computed from the hashcode of each field. Finally the Preconditions of the structure type include the preconditions of each field, as well as potentially individually defined preconditions.<br/><br/>

## Packet Format [pkmcom.packet] ##


PkmCom uses an Headed Packet Format which stores several fields (listing enough information to identify and validate the packet contents. 
Packet is itself considered a Structured Type in the PkmCom protocol, though may never be used as such in a packet. 
The Actual Content of a Packet is also structure type, which is defined by the Id.<br/><br/>

The structure of a Packet is as follows:
<table>
	<tr>
		<th>Field Name</th>
		<th>Type</th>
		<th>Notes</th>
	</tr>
	<tr>
		<td>Id</td>
		<td>Byte Enum</td>
		<td>The Id of the Packet. No specific values are defined by the APL (1 exception below). The Concrete Protocol Definition defines the valid values and the content structure of those packets</td>
	</tr>
	<tr>
		<td>Hashcode</td>
		<td>Unsigned Int</td>
		<td>The Hashcode representation of the packet (See below)</td>
	</tr>
	<tr>
		<td>Size</td>
		<td>Int</td>
		<td>The Size of the Encoded Content Structure</td>
	</tr>
	<tr>
		<td>Content</td>
		<td>Packet Content</td>
		<td>The Actual Content of the packet. The structure is given by the Concrete Protocol and the Id</td>
	</tr>
</table>

## Packet Verification (Hashcodes)  [pkmcom.hash]##



PkmCom uses a 4-byte Unsigned Hashcode to verify and validate that the content of the packet was received correctly.
Each type defines it own rules for how this hashcode is computed.<br/>

<h3>hashsum function</h3>
The hashsum function is the commonly referenced function for computing hashcodes. 
The hashsum function is defined for n inputs as follows:<br/>
<ol type="1">
<li>If n is 0, the hashsum is 0</li>
<li>If n is 1, the hashsum is the hashcode of the value</li>
<li>If n is 2, given by a and b, the hashsum is hashcode(a)*31+hashcode(b)</li>
<li>Otherwise, if the first 2 values are a and b, and the remaining values are given by c..., then the hashsum is hashsum(hashsum(a,b),c...).</li>
</ol>
This function is defined to provide a consistent method of chaining value hashcodes together in a order-sensitive manner.

### Hashcode Definitions [pkmcom.hash.def] ###



The hashcode of each type is given as such:
<ul>
<li>Byte,Unsigned Short: The hashcode of Byte and Unsigned Short is the value itself, zero-extended to an Unsigned Int</li>
<li>Signed Byte, Short: The hashcode of a Signed Byte is the value itself, sign-extended to an Int, then the representation interpreted as an Unsigned Int</li>
<li>Int: The hashcode of an int is the representation of that int interpreted as an Unsigned Int</li>
<li>Unsigned Int: The hashcode of an unsigned int is the value itself</li>
<li>Long: The hashcode of a Long is the high 32-bits xored with the low 32-bits and the result treated as an Unsigned Int</li>
<li>Boolean: The hashcode of a Boolean is 1331 if that Boolean is false, and 1337 is true.</li>
<li>String, Json, Long String, Long Json: The hashcode of String/Long String is the hashsum of each byte in the string. The hashcode of Json/Long Json is the hashcode of the String representation</li>
<li>T Array: The hashcode of an array is the hashsum of its elements</li> 
<li>T Enum/T Bitflag: The hashcode of an Enum or Bitflag is the hashcode of its value</li>
<li>Float, Double: The hashcode of a Float or Double is the hashcode of its representation, interpreted as an Unsigned Int or Long Respectively</li>
<li>UUID: The hashcode of a UUID is the hashsum of its most significant 64-bits and its least significant 64-bits</li>
<li>Instant, Duration: The hashcode of an Instant or Duration is the hashsum of its seconds field and its nanos field.</li>
<li>Version: The hashcode of a Version is the hashsum of the major component (which is the first byte in the encoding+1), and its minor component (which is the second byte in the encoding)</li>
<li>Packet Content: In general, the hashcode of the content of a Packet should be the hashsum of each field</li>
<li>Packet: The hashcode of a packet is the hashsum of its id and content. Note that neither the size nor hashcode fields are included (otherwise we would have an issue with hashcode field).</li>
</ul>

## Rules/Preconditions [pkmcom.rules] ##


Each type in PkmCom may additionally define a set of preconditions, that is, rules about the stored value. If a packet recieved contains any fields with values in violation of these rules, the recieving side MUST generate a Protocol Error<br/>
The Rules of each type in PkmCom that defines any are as follows: 
* Boolean: The value must either be true (1) or false (0) and may be no other bitpattern. 
* T Enum: The value must be one of the values specified in its definition, or an unused value. (No other values of T may be sent). 
* T Bitflag: Any bits which are defined as "Reserved" may not be clear. Note that any other bit may be set (even unspecified ones). 
* Float, Double: Float and Double values may not be a NaN value. This restriction is put in place due to varying handling of NaNs, and the fact that whether or not the bit representation of a NaN is preserved. 
* Instant, Duration: The Nanos field must be in the range (0,1000000000]. The Seconds field must be in the range (-31556889864401400,31556889864401400].
* String, Long String: The String must end on either a valid single-byte character, or a complete multi-byte character. If a header byte is read it must be followed by the correct number of continuation bytes, before the next header byte or single-byte character. If a continuation byte is read, it must have been preceeded by a header byte or at most 1 other continuation byte, which itself is preceeded by a header byte specifying a 3 byte character. Embedded Nul(0) Bytes are Not Permitted
* Json, Long Json: The Raw representation must meet the Preconditions for String/Long String Respectively. In addition, the text must be a valid Json Object which is enclosed in `{}`. 


## Connection Handshaking [pkmcom.handshake] ##


The PkmCom protocol is built on TCP. When the connection is opened, and after the TCP Handshake, The Server and Client preform a Secret Key exchange to send data over a channel secured by AES-256, using Cipher Block Chaining, and Padded with PKCS5 Padding.<br/>
The steps of the key exchange are preformed as follows:
<ol type="1">
<li>The Server has a prepared RSA Key Pair (may be RSA-1024 or RSA-2048). The Public Key of this Key Pair is sent to the Client</li>
<li>The Client generates an RSA Key pair (Either RSA-1024 or RSA-2048), and sends the public key to the server. This key pair is to be disposed of by the client by the time connection is closed. It may be disposed of earlier, but only after all points which the Complete Protocol Definition applying PkmCom permits the server to re-establish the shared secret</li>
<li>The server and the client each generate (in a secure fashion), a 2048-bit message. This message encrypted using the public key of the target side (using PKCS1 Padding). They both also generate and send a 128-byte iv</li>
<li>The 2 messages are Combined by XORing each byte in sequence, and also combine the IVs by the same method. The message is then used to Derive a 256-bit AES Key, using SHA-256. Past this point AES Encryption is used, with the XORed IVs used for Cipher Block Chaining</li>
<li>The Client should then send a Handshaking Packet (0xFF, See below). If its read correctly, then the server should respond with the same packet. If either packet is read incorrectly, the connection is closed (though may be reopened).</li>
</ol>
### Handshaking Packet [pkmcom.handshake.packet] ###



This Packet is sent and verified at the end of the handshake sequence. It contains a single Unsigned Int Enum Field, which should be exactly 0x504B4D00. The Id of the Packet is 0xFF.

### Alternative Handshaking [pkmcom.handshake.alt] ###

In certain situations, an alternative method is used to derive the Session Shared Secret, such as a password. 
After the messages are exchanged, if indicated by the server, the client and server should append some sort of alternatively exchanged secret to the combined messages (usually a password exchanged physically, such as in person). The AES Key should then be derived from that, and handshaking should be completed from that point. 

This is primarily used in LAN (indicated by the 0x80 bit set in the LAN Game type bitfield), but Servers can use this by requesting the secret be re-established with the reason code being set to 1 (password required), and as such, implement non-exclusive whitelists (whitelists that are not associated with one save file, or one sentry account, but are protected by a password). 

It is implementation-defined if a client supports alternative handshaking. 

