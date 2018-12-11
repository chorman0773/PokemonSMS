# Info and Copyright Notice #

## Copyright ##
PokemonSMS Public Specification Project, Copyright 2018 Connor Horman
Pokemon, the Pokemon Logo, and all Official Pokemon are Copyright Nintendo and Game Freak. This Project is in no way affiliated with Nintendo or Game Freak, and disclaims all relation with the above parties. This project is intended as a Fan Game, or as Parody of Legitimate Pokemon titles, and no Concreate Game produced using this project should be considered legitimate or affiliated to the above companies, unless they provide official consent to the connection. This project, and all games produced using this specification intend no copyright infringement or Intellectual Property theft of any kind.<br/><br/>


The PokemonSMS Save File Specification("This Document"), provided by the PokemonSMS Public Specification Project ("This Project") is Copyright Connor Horman("The Owner"), 2018. 
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
### Additional Copyright Information ###

This document references the Named Binary Tag specification, created by Markus Person. The up to date specification can be located at https://wiki.vg/NBT. 
In the present version of this document, references to that specification imply Version 19133, and DO NOT use GZip or zlib compression. ShadeNBT only allows for uncompressed NBT Data. The specific Specification used defines TAG_LongArray.

## Info ##
Defines the Specification for the File Format used to store PokemonSMS Savegames, The Specific Structure of a Savegame, and Save File Generalization
# Specification #


## ShadeNBT Format ##
PokemonSMS stores games in the ShadeNBT format, which extends the NBT File Format defined by Markus Person. 
Standard Save Files take the following Format:

```
struct file{
	u8 magic[4];
	be16 shadeVersion;
	u8 flags;
	TAG_Compound compoundTag;
	u8 trailingZeros[];
}
```

Given the file, magic must be exactly `[0xAD 0x4E 0x42 0x54]` ("\xADNBT"), otherwise the file is Ill-formed.
shadeVersion is the version of the ShadeNBT specification, in the Sentry Versioning Format. If the version represents one greater then 1.2, the file is Ill-formed.
flags are the Shade flags. does not exist if shadeVersion specifies any version other then 1.2.
compoundTag is the actual content of the file. Multibyte datatypes are stored in Big-Endian if the shadeVersion is not 1.2, or if flag 0x80 of the Shade Flags is clear. If The version is 1.2 and flag 0x80 in Shade Flags is set, then Multibyte datatypes are stored in Little-Endian. The `TAG_Compound` refers to the top level Compound tag in an uncompressed NBT File. The file is Ill-formed if the `TAG_Compound` cannot be parsed correctly. 

If There are any bytes in `trailingZeros`, they must all be 0 bytes.


### CryptoShade Extension ###
Implementations of PokemonSMS can also optionally support CryptoShade. The format of a crypto shade file is as follows:

```
struct crypto_shade_file{
	u8 magic[4];
	be16 version;
	u8 flags;
	u8 salt[32];
	u8 iv[16];
	u16 blockCount;
	u8 blocks[16][blockCount];
};
```
version and flags are exactly as described for standard ShadeNBT files. magic must be exactly `[0xEC 0x4E 0x42 0x54]` or the file is Ill-formed.
salt is the random salt for Key Derivation (see below), Iv is the initialization vector for CBC Block Chaining Mode.
blockCount is the number of 16-byte Blocks that follow. 
A user supplied password and the randomly generated salt are used to derive an AES-256 key by hashing that password (without a null terminator) with the salt appended using SHA-256. 

Note: The Password is not mandated to be user supplied, it may be independently generated or derived. However the password is not stored in the file. 
Additionally, implementations are not required to support creating unencrypted save files, but are required to support loading and resaving of unencrypted save files. 
These statements are in place to allow implementations that want to keep users from modifying save files by hiding the cryptographic key source. 
It is usually not a good idea to store cryptographic details in any application (as it may lead to security issues even for proprietary applications. To quote literally every security researcher "Security through Obscurity is no Security at all"). 
However, on systems with built-in cryptographic features (such as Gaming Consoles), it may be possible to leverage these built-in features to derive the key. Due to the variety of the cryptographic salt and initialization vector for CBC Chaining using the same "password" or key-base for multiple files or multiple iterations of the same file is not a security concern, provided the security and integrety of the key remains intact. 



The `TAG_Compound` is encrypted or decrypted using this key in CBC mode with the IV, padded using PKCS5 Padding.
Same rules for the Decrypted `TAG_Compound` apply as do for ShadeNBT.
At least one of iv and salt must be regenerated each time the file is saved. Both must have been generated from a Secure Source of Random Bytes. 

### Structure of NBT Files ###

As per the specification, NBT Files take on a specific structure. This structure provides a uniform way of serializing named binary data in tag form (hense "Named Binary Tag"). 

In NBT Files, the `i8`, `i16`, `i32`, `i64`, `f32` and `f64` tags refer to the primitive types associated specifically with NBT Files. These refer to signed values. `u16` also exists (The Length Prefix of Strings). 
There length (in bits) is specified by the number after the typecode (i or f). 
Aside from `i8`, these types are constrained to the Endianess Flag set in the ShadeHeader for Files indicating a ShadeVersion of 1.2 and above. In Save files indicating a ShadeVersion of 1.1 or below, they are all encoded in Big Endian. 

The `f32` and `f64` tags are floating point numbers encoded in the IEEE 754 (IEC 559) Floating-Point Representation as binary32 or binary64 respectively. NaN Values MUST NOT be encoded in these tags (despite the Original NBT Specification permitting NaN values). 



#### Tag Types ####

<table>
	<tr>
		<th>Tag Type</th>
		<th>Tag Name</th>
		<th>Size(bytes)</th>
		<th>Payload</th>
	</tr>
	<tr>
		<td>0</td>
		<td>TAG_End</td>
		<td>0</td>
		<td>Empty</td>
	</tr>
	<tr>
		<td>1</td>
		<td>TAG_Byte</td>
		<td>1</td>
		<td>i8 value</td>
	</tr>
	<tr>
		<td>2</td>
		<td>TAG_Short</td>
		<td>2</td>
		<td>i16 value</td>
	</tr>
	<tr>
		<td>3</td>
		<td>TAG_Int</td>
		<td>4</td>
		<td>i32 value</td>
	</tr>
	<tr>
		<td>4</td>
		<td>TAG_Long</td>
		<td>8</td>
		<td>i64 value</td>
	</tr>
	<tr>
		<td>5</td>
		<td>TAG_Float</td>
		<td>4</td>
		<td>f32 value</td>
	</tr>
	<tr>
		<td>6</td>
		<td>TAG_Double</td>
		<td>8</td>
		<td>f64 value</td>
	</tr>
	<tr>
		<td>7</td>
		<td>TAG_ByteArray</td>
		<td>Varies</td>
		<td>i32 length prefix, followed by that many TAG_Bytes</td>
	</tr>
	<tr>
		<td>8</td>
		<td>TAG_String</td>
		<td>Varies</td>
		<td>u16 length prefix, followed by that many u8 values which form a valid UTF-8 String</td>
	</tr>
	<tr>
		<td>9</td>
		<td>TAG_List</td>
		<td>Varies</td>
		<td>i32 length prefix, and a u8 ListTagType, followed by length payloads for the given ListTagType.</td>
	</tr>
	<tr>
		<td>10</td>
		<td>TAG_Compound</td>
		<td>Varies</td>
		<td>See "TAG_Compound" Structure below</td>
	</tr>
	<tr>
		<td>11</td>
		<td>TAG_IntArray</td>
		<td>Varies</td>
		<td>i32 length prefix, followed by that many TAG_Ints</td>
	</tr>
	<tr>
		<td>12</td>
		<td>TAG_LongArray</td>
		<td>Varies</td>
		<td>i32 length prefix, followed by that many TAG_Longs</td>
	</tr>
</table>

Other tags may be added in future versions of this Specification. This will be updated to reflect the most current version of the Shade Specification and NBT Specification. 

The Structure of an NBTTag (stored in a compound) is as follows

```
NBTTag{
	u8 tagType;
	TAG_String name;
	union{
		TAG_Byte byte;
		TAG_Short short;
		TAG_Int int;
		TAG_Long long;
		TAG_Float float;
		TAG_Double double;
		TAG_ByteArray byteArray;
		TAG_String string;
		TAG_List list;
		TAG_Compound compound;
		TAG_IntArray intArray;
		TAG_LongArray longArray;
	}payload;
}
```

The `TAG_String` (or any other Type prefixed with `TAG_`) refers to the payload of that tag type. There is No tagType or name for these tags. 



And the Structure of a TAG_Compound payload is as follows

```
TAG_Compound{
	NBTTag tags[];
	u8 endTagType;
};
```

`endTagType` is exactly the Byte 0. 

`tags` is an array of an unknown length. It starts from the first NBTTag in the Compound, and continues until a NBTTag read specifies an End Tag, and does not include that tag. 

The Names of tags in the `TAG_Compound` follow the rules of strings defined below. It is unspecified whether or not these keys are case-sensitive. 
A Compound tag MAY NOT contain multiple tags with names that differ only in case (In the official specification, this restriction is not in place). A Compound tag MUST NOT contain multiple tags with identical names. 

The first `TAG_Compound` (the one that appears inside the ShadeFile structure's `compoundTag`, or the decrypted `TAG_Compound`) is called the base compound. The base compound does not have a type or a name. 

There may be only one tag contained within the base compound, which must be an unnamed compound tag itself (though both the name and tagType are still encoded). It is not required that the base compound be terminated with an End Tag (though may in fact exist as such). 

#### Strings ####

Strings (TAG_String), encode text in UTF-8 format. 
The following rules apply to those Strings

* There may be no Embedded Null Bytes 
* The first byte of a character must either be a single-byte character (in [0x01,0x80)), or a multi-byte character header. 
* Each byte of a multi-byte character, except the first byte, MUST be a Continuation Byte. A Continuation may not appear in any other position. 
* If the String ends after a given byte, then it MUST either be a single-byte character, or the final Continuation Byte of a Multi-byte character. If a String ends after a Multi-byte character header, or any continuation byte of a multibyte character, except the last. 
* Only 2-byte and 3-byte multibyte characters are supported. 4-byte, 5-byte, and 6-byte chracters are not. 
* If a Character is the first Character of a Surrogate Pair, then the next character must exist and must be a valid second character in the Surrogate Pair. (The String MUST NOT end in the middle of a Surrogate Pair). 
* If a Character is the second Character of a Surrogate Pair, then the previous character must be a valid character that starts a Surrogate Pair. 
* If any single character that is not part of a Surrogate Pair does not encode a valid Unicode Character, or any Surrogate Pair does not encode a valid Unicode Character

A file which contains a String tag that contains any text that does

## Save file generalization and storage ##
PokemonSMS supports multiple save files. A sqlite database of these save files is stored alongside the actual save files, with the name filename saves.pkmdb. The database shall contain a single table called "Saves" that uses the following Schema. 

```sqlite
CREATE TABLE `Saves` (
	`id`	INTEGER NOT NULL AUTOINCREMENT UNIQUE,
	`path`	TEXT NOT NULL,
	`nameComponent`	TEXT NOT NULL,
	`icon`	TEXT NOT NULL,
	`saveTimeSeconds`	INTEGER NOT NULL,
	`saveTimeNanos`	INTEGER NOT NULL,
	`encrypted`	BOOL NOT NULL,
	PRIMARY Key('id')
);
```

`id` is the save file number to uniquely identify the file, may be any number, but at most 1 Row may exist with a given `id` (hense `UNIQUE`)

`path` is a relative pathname to the save file (relative to the directory that saves.pkmdb is in). The pathname shall be case-insensitive, use `/` path separators if applicable (posix). It MUST NOT start with `/` or contain any navigation components (such as `..` or `.`). 

`nameComponent` is a Text Component representing the name given to the player in the file. 

`icon` is the Image which displays alongside the save file in the save file select menu, as a base64 encoded JPEG file

`saveTimeSeconds` is the total seconds the save file has been open for
`saveTimeNanos` is the nanosecond component of the Duration the save file has been open for in total.
`pokedexSeen` is the number of pokemon that are registered as seen in the pokedex
`pokedexCaught` is the number of pokemon that are registered as caught in the pokedex
encrypted is true if the file is using the CryptoShade Extension, false otherwise.

Implementations may store other information in saves.pkmdb, such as global options, in other tables. 

The maximum number of save slots an implementation supports is unspecified, but must at least be 3. Implementations must accept save databases which indicate a greater number of save files. 

The Directory which save files are stored in is unspecified, and may not even be a physical directory on a filesystem. Implementations are permitted to store a logical filesystem in an unspecified file that contains 



## Save File Structure ##

The NBT Structure of a Save File is broken into chunks, which describe the various portions of the game. Each Portion is detailed below:

### Serializing Lua ###
Lua Tables (and other values, but not Functions or Userdata) can be serialized. 

Tables must either represent a list (Mapping integer keys starting from 1 to some uniform type of values), or Structures (Mapping String Keys to some values), and may not contain values that cannot be serialized. Tables must also not be nested recursively. 


Tables that represent Structures are serialized as `TAG_Compound`, Lists are serialized as `TAG_List`. The elements of tables that can be serialized are serialized as such. 

Numbers are serialized as `TAG_Double` (if they do not meet the requirements for a Lua Integer defined in Lua 5.3 or do not fall in the range for a `TAG_Int`), or `TAG_Int` (if they do). The value `math.huge` serializes as `TAG_Double` which identifies the IEEE 754 Double value "Positive Infinity". The value `-math.huge` serializes as "Negative Infinity". 

Strings are serialized as `TAG_String`. Boolean values are serialized as the `TAG_Byte` containing 1b if they are true, and the `TAG_Byte` containing 0b if they are false. 

It is unspecified which userdata types can be serialized in NBT Form. 




### Pokemon Structure ###
Pokemon stored in the Save file take the following format:

* (a pokemon)(compound) (tags common to all pokemon)
    * Species (string): The resource location that names the species of the pokemon. If  the string is not a valid resource location (see ResourceNaming.md), the resource location does not name a pokemon, or the string is `system:pokemon/null` the file is ill-formed. Must Exist, or the compound may not contain any other tags. 
    * CatchInfo (compound): The Information describing how the pokemon was obtained
        * TrainerId (long): The Id of the original trainer of this pokemon
        * TrainerSid (long): The secret Id of the original trainer
        * TrainerName (String): A String representing a text component. This is the name of the original trainer. Note that this is not matched to determine if the pokemon should be disobediant.
        * CatchTimestampSeconds (Long): The Number of seconds since 1970-01-01T00:00:00Z which the pokemon was caught at.
        * CatchTimestampNanos (int): The Number of nanos since the start of the second designated by CatchTimestampSeconds, which this pokemon was caught at.
        * CatchLocation (string): A resource location that names a location. Invalid Catch Locations should be preserved and treated as `pokemon:faraway`. If the location is `system:locations/null`, then the file is ill-formed. Not set if CatchLevel is 0. 
        * CatchFlags (byte): A bit array designating the flags applicable to the capture.  See Below for valid bitflags. 
        * CatchLevel (short): The level then pokemon was at when it was caught. Must be a number in [0,100]. 0 means the Pokemon was "caught" as an egg.  
        * CatchSpecies (string): A resource location identifying the Species that the pokemon was caught as. 
    * Stats (compound): The Stats of the Pokemon
        * Curr (Int Array): An array of 7 integers, designating the Current stats of the Pokemon. Follows Natural Stat Order (0 is attack, 1 is defense, 2 is special attack, 3 is special defense, 4 is speed, 5 is maximum hp). Index 6 designates the difference between the maximum hp and the current HP. 
        * EffortValues (Byte Array): An array of 6 bytes, designating the Effort Values in each Stat. Follows Natural Stat ordering. 
        * IndividualValues (Byte Array): An array of 6 bytes, designating the Individual Values in each Stat. Follows Natural Stat Ordering. 
        * Level (Short): The level the pokemon is currently at. Must be a number in [0,100]. If Level is 0, then CatchInfo.CatchLevel must also be 0 or the file is ill-formed. 
    * Individuality (compound): The Structure which defines the Pokemon Individuality Information
        * BlockId (long): The First of the 2 Pokemon Individuality Ids (Block Identifier). 
        * SlotId (long): The Second of the 2 Pokemon Individuality Ids (Slot Identifier)
        * PokemonFlags (byte): The flags associated with the Pokemon. See below
        * CryModifier (compound): The Invididuality Cry Modifiers
            * Pitch (byte): (Unsigned) The pitch modifier that applies to the pokemon
            * Volume (byte): (Unsigned) The volume modifier that applies to the pokemon.
    * Options (compound): A Compound containing implementation specific Information about the Pokemon
    * Items (compound): The held item and equipment item. Must exist but may be empty
        * Held (compound): The compound describing the held item. See Item Structure. May not exist or be empty. 
        * Equipment (compound): The compound describing the equipment (secondary) item. See Item Structure. May not exist.
    * Extra (compound): The Serialization of the Extra Table. Species Dependent. May not Exist
    
### Item Structure ###

Items are Stored in the following structure:

* (an item) (compound)  (Tags common to all items)
    * Id (string): The resource name that identifies the item. Must be a valid resource location that names an item or the file is ill-formed.
    * Count (short): The number of items in the stack. May not Exist. If undefined, defaults to 1. If it is defined, it must be the value 1 if the compound appears in a Pokemon's Items compound. 
    * Variant (short): The Variant Id of the item. May not exist, defaults to 0. The range of valid values and the properties of item variants is depedent on the item. 
    * Extra (compound): The serialization of the Item's Extra Table. Item Dependent. May Not Exist



		

