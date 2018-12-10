# Info and Copyright Notice #

## Copyright ##
PokemonSMS Public Specification Project, Copyright 2018 Connor Horman
Pokemon, the Pokemon Logo, and all Official Pokemon are Copyright Nintendo and Game Freak. This Project is in no way affiliated with Nintendo or Game Freak, and disclaims all relation with the above parties. This project is intended as a Fan Game, or as Parody of Legitimate Pokemon titles, and no Concreate Game produced using this project should be considered legitimate or affiliated to the above companies, unless they provide official consent to the connection. This project, and all games produced using this specification intend no copyright infringement or Intellectual Property theft of any kind.<br/><br/>


The PokemonSMS Pokemon Individuality Specification("This Document"), provided by the PokemonSMS Public Specification Project ("This Project") is Copyright Connor Horman("The Owner"), 2018. 
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
This document defines how Individuality is determined when generating pokemon. 

# Pokemon Individuality #

## Block Id and Slot Id ##

In PokemonSMS, Pokemon Individuality is determined by 2 unique 64-bit integers, which are generated at specific times. 
These are the Block Identifier (BlockID), and Slot Identifier (SlotID). Both Numbers are used to determine Uniqueness. 

The Slot Identifier is to be generated from the Game Random Number Generator (Random.md). 
The Block Identifier is generated in an implementation defined manner, but the implementation is required to guarantee (within reason) that no 2 pokemon generated at different times will have the same BlockID. 
This is not a hard and fast rule, as it would be impossible to completely make this guarantee. However, this rule is in place to prevent implementations from simply assigning the BlockID to a constant, possibly incrementing this value by one for each Pokemon. 
Implementations are permitted to generate the BlockID from the Game Random Number Generator, or even from a separate Pseudo-random Number generator. 

Both the BlockID and SlotID are persistant values, stored in the Pokemon Structure as Individuality.BlockID and Individuality.SlotID respectively. 

## Uniqueness Values ##

Various values are generated from the BlockID and SlotID. In general, the SlotID is used for information that can affect Gameplay, and the BlockID is used for information that does not. 

### Individual Values ###
Individual Values, or IVs are values associated with each Stat, and manipulate the stats directly. Each IV takes on an integer in [0,32). 
The 30 least significant bits of the SlotID generate the Individual Values. 
The IVs generated from in natural stat ordering (attack, defense, special attack, special defense, speed, hp), starting from the least significant bits, and use 5 bits each. 
For example, the Attack IV is the 5 least significant bits of the SlotID, the Defense IV is the next 5 bits, etc.  

### Nature ###
A Pokemon's nature specifies stats which are increased and decreased after Stat Calculation (among other things). There are 5 stats which can be affected. The 8 most significant bits of the SlotID are considered when generating the nature. 

The Selected Nature is determined by selecting the increased stat and the decreased stat separately. 

For the Increased Stat, take the 4 least significant bits chosen as n. Perform the Following Algorithm:
1. Subtract 10 from n, wrapping around (+16) if n<10.
2. If n<10, Then the stat is n%5. 
3. If n>=10, then n becomes (n/5)+(n%5). Repeat this algorithm from step 1.

For the decreased stat, perform the same algorithm using the 4 most sigficant bits. 

This algorithm is used to eliminate the non-uniformity inherent to generating bounded numbers for bounds which are not a power of 2. 
This algorithm can be used for any other prime number k, up to k=13, using 2k%16 instead of 10, and k instead of 5. 
Note that this algorithm is slightly more likely to chose the value 3 than any other value. 

If the increased and decreased stat are the same number `n`, then the nature of the pokemon is the nth neutral nature. Otherwise given that the increased stat is `n` and the decreased stat is `m`, the nature of the Pokemon is the one which increases the nth stat in natural stat order, and decreases the mth stat in natural stat order. 

### Growth Modifiers ###

In addition to IVs and nature, Pokemon also have a Growth Modifier in each Stat and for experience. 
There are 7 total Growth Modifiers, each using 8-bits. 
The 56 least significant bits of the SlotID make up the Growth Modifiers, in natural stat order. 
The 8 most significant bits used designates the Experience Growth Modifier, then the next 8 bits is the attack growth modifier, the next is the defense growht modifier, etc. 
To compute the growth modifier, take the bits associated with the range %192, and +64. This number, devided by 128 gives a value in [0.5,2) which modifies Effort Values/Experience gained by the Pokemon. 


### Pokemon Gender ###

Pokemon Gender is the last uniqueness value that directly affects gameplay. It is taken from the 8 least significant bits of the BlockID (not the SlotID). If the 8-bit integer is less than or equal to the Species Gender threshold, the pokemon is male, otherwise the pokemon is female. If the Species Gender Threshold is 256, then the pokemon is always genderless. 

### Cry Volume and Pitch Modifiers ###

2 Modifiers apply to pokemon's cry, being a volume modifier, and a pitch modifier. Given the 24 least significant bits of the BlockID, The Volume Modifier is the 8 most significant bits of this selection%192 + 64, and the Pitch Modifier is the middle 8 bits of the section (%192 + 64). 

### Shinyness ###

Shinyness is computed from both the SlotID and the BlockID, as well as the Trainer Id and Trainer Secret Id of the Original Trainer of the Pokemon (if the Pokemon does not have an original trainer, it is a wild pokemon, then it uses the Id and Secret Id of the activate trainer). If the species has the `noshiny` trait then shinyness calculations are not performed, the pokemon is never shiny.  

Take the SlotID as i, BlockID as j, OTId as k, and OTSid as l then perform the following algorithm to compute s and o:

1. Take q as i xor k, and p as j xor l.
2. Shift q left by 3, shifting in 0 bits. Shift p left by 5.
3. Rotate j right by 7 and i right by 11.
4. xor q, q rightrotated by 11, q rightrotated by 17, and q rightshifted by 5
5. xor p, p rightrotated by 13, p rightrotated by 19, and p rightshifted by 7
6. s is p xor j, o is q xor i.



 


