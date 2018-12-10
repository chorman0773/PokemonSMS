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
}
```

Given the file, magic must be exactly `[0xAD 0x4E 0x42 0x54]` ("\xADNBT"), otherwise the file is Ill-formed.
shadeVersion is the version of the ShadeNBT specification, in the Sentry Versioning Format. If the version represents one greater then 1.2, the file is Ill-formed.
flags are the Shade flags. does not exist if shadeVersion specifies any version other then 1.2.
compoundTag is the actual content of the file. Multibyte datatypes are stored in Big-Endian if the shadeVersion is not 1.2, or if flag 0x80 of the Shade Flags is clear. If The version is 1.2 and flag 0x80 in Shade Flags is set, then Multibyte datatypes are stored in Little-Endian. The `TAG_Compound` refers to the top level Compound tag in an uncompressed NBT File. The file is Ill-formed if the `TAG_Compound` cannot be parsed correctly.
<br/><br/>
Implementations of PokemonSMS can also optionally support CryptoShade. The format of a crypto shade file is as follows:

```
struct crypto_shade_file{
	u8 magic[4];
	be16 version;
	u8 flags;
	u8 salt[32];
	u8 iv[16];
	u16 blockCount;
	u8 blocks[blockCount][16];
};
```
version and flags are exactly as described for standard ShadeNBT files. magic must be exactly `[0xEC 0x4E 0x42 0x54]` or the file is Ill-formed.
salt is the random salt for Key Derivation (see below), Iv is the initialization vector for CBC Block Chaining Mode.
blockCount is the number of 16-byte Blocks that follow. 
A user supplied password and the randomly generated salt are used to derive an AES-256 key by hashing that password (without a null terminator) with the salt appended using SHA-256.
The `TAG_Compound` is encrypted or decrypted using this key in CBC mode with the IV, padded using PKCS5 Padding.
Same rules for the Decrypted `TAG_Compound` apply as do for ShadeNBT.
At least one of iv and salt must be regenerated each time the file is saved. Both must have been generated using a Secure Random Number Generator.

<h2>Save file generalization and storage</h2>
PokemonSMS supports multiple save files. An sqlite database of these save files is stored alongside the actual save files, with the name filename saves.pkmdb. The database contains a single table called Saves
Each Entry in this Table contains the following fields

```
TEXT path
TEXT nameComponent
TEXT icon
LONG saveTimeSeconds
INT saveTimeNanos
INT pokedexSeen
INT pokedexCaught
BOOL encrypted 
```
path is a relative pathname to the save file (relative to the directory that saves.pkmdb is in). 
nameComponent is a Text Component representing the name given to the player in the file. 
icon is the Image which displays alongside the save file in the save file select menu, as a base64 encoded JPEG file
saveTimeSeconds is the total seconds the save file has been open for
saveTimeNanos is the nanosecond component of the Duration the save file has been open for in total.
pokedexSeen is the number of pokemon that are registered as seen in the pokedex
pokedexCaught is the number of pokemon that are registered as caught in the pokedex
encrypted is true if the file is using the CryptoShade Extension, false otherwise.

Implementations may store other information in saves.pkmdb, such as global options, in other tables. 

The maximum number of save slots in unspecified, but must at least be 3. 


## Save File Structure ##
The NBT Structure of a Save File is broken into chunks, which describe the various portions of the game. Each Portion is detailed below:

### Pokemon Structure ###
Pokemon stored in the Save file take the following format:

* (a pokemon)(compound)
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
		

