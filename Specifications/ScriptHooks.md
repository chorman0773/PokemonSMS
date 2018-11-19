<h1>Info and Copyright Notice</h1>

<h2>Copyright:</h2>
PokemonSMS Public Specification Project, Copyright 2018 Connor Horman
Pokemon, the Pokemon Logo, and all Official Pokemon are Copyright Nintendo and Game Freak. This Project is in no way affiliated with Nintendo or Game Freak, and disclaims all relation with the above parties. This project is intended as a Fan Game, or as Parody of Legitimate Pokemon titles, and no Concreate Game produced using this project should be considered legitimate or affiliated to the above companies, unless they provide official consent to the connection. This project, and all games produced using this specification intend no copyright infringement or Intellectual Property theft of any kind.<br/><br/>


The PokemonSMS Script Bindings Listing("This Document"), provided by the PokemonSMS Public Specification Project ("This Project") is Copyright Connor Horman("The Owner"), 2018. 
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
<h2>Info:</h2>
This document details all the Lua types and functions which must be exposed by an implementation to bind the Scripts and Resources
<br/><br/>


<h1>Bindings List</h1>
<h2>Global Libraries</h2>
<h3>Constants</h3>
<h4>Overview</h4>
Constants is a library which defines the Primary Constants used by the entire game.
<h4>Type List</h4>
<h5>Notes</h5>
Each Type defined in Constants is Symbolic Only (Except for Mid-Level Table-like types). 
There are no requirements for the actual type of the values, except that they are equality comparable with other values of the same declared type. 
For example, and underlying implementation may define all Constants as being a Number. 
Type sensitive operations (such as type(), setmetatable/getmetatable()) should not be used or relied upon. 
Comparing values of different types is undefined behavior, and may result in an error (despite the fact that Lua defines equality/in-equality comparisons between unrelated types), except that comparison to nil is well defined (and always false for actual values).
Values in these types should be considered unordered. Using Ordering Comparisons results in undefined behavior and may be an error.
Finally, a value of each type is well formed if and only if it was a defined value in the particular block. Using other values of each type in any way is undefined behavior. 


<h5>PokemonType</h5>
The Symbolic Type of constants in the Constants.Types. 

<h5>Stat</h5>
The Symbolic type of the constants in Constants.Stats, except for the 3 In Battle Stats (Accuracy, Evasion, CritRatio) 

<h5>InBattleStat</h5>
The Symbolic type of the 3 In Battle Stats defined in Constants.Stats (Accuracy, Evasion, CritRatio). 

<h5>AttackCategory</h5>
The Symbolic type of the constants in Constants.AttackCategories. 

<h4>Defined Fields</h4>
<h5>Notes</h5>
All defined fields are constant (may not be reassigned), and are of an immutable table-like type which define index and pairs. 
 pairs operates over the members of the Value. index can index any of the defined members, others return nil. 
 The typename of a field of Constants is unspecified. 
 All Fields in these tables are required to be valid values of there associated type (the actual value is unspecified however), and are distinct from any other valid value obtained from other fields of the same table.<br/>
<h5>Stats</h5>
Constants for Pokemon Stats and Battle Stats. 
The members of Stats are as follows:
<ul>
<li>Stats.Attack -> An unspecified, but valid value of Stat, which represents the Attack Stat</li>
<li>Stats.Defense -> An unspecified, but valid value of Stat, which represents the Defense Stat</li> 
<li>Stats.Special -> An unspecified, but valid value of Stat, which represents the Special Attack Stat</li>
<li>Stats.SpecDef -> An unspecified, but valid value of Stat, which represents the Special Defense Stat</li>
<li>Stats.Speed -> An unspecified, but valid value of Stat, which represents the Speed Stat</li>
<li>Stats.HP -> An unspecified, but valid value of Stat, which represents the Hit Points Stat</li>
<li>Stats.Accuracy -> An unspecified, but valid value of InBattleStat, which represents the Accuracy Battle Stat</li>
<li>Stats.Evasion -> An unspecified, but valid value of InBattleStat, which represents the Evasion Battle Stat</li>
<li>Stats.CritRate -> An unspecified, but valid value of InBattleStat, which represents the Critical Hit Ratio Battle Stat</li>
</ul>

<h5>Types</h5>
Constants for Pokemon Types. 
The members of Types are as follows:
<ul>
<li>Types.NORMAL -> An unspecified, but valid value of Type, which represents the Normal Type</li>
<li>Types.GRASS -> An unspecified, but valid value of Type, whcih represents the Grass Type</li>
</ul>



<h2>Type List</h2>
<h3>Combat State (State)</h3>
<h4>Overview</h4>
A State refers to the state of a combat (damaging) move being used by a pokemon.
Each state knows several things about the active move: base damage, type, accuracy, 
<h4>Functions</h4>
<h5>setAccuracy(#number accuracy)</h5>
Sets the accuracy of the move. If the accuracy check has passed or the move ignores accuracy checks then this function has no effect
<h5>#number getAccuracy()</h5>
Gets the currently calculated accuracy of the move. Returns math.huge (+Infinity) if the move does not check accuracy