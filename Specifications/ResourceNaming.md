<h1>Info and Copyright Notice</h1>

<h2>Copyright:</h2>
PokemonSMS Public Specification Project, Copyright 2018 Connor Horman
Pokemon, the Pokemon Logo, and all Official Pokemon are Copyright Nintendo and Game Freak. This Project is in no way affiliated with Nintendo or Game Freak, and disclaims all relation with the above parties. This project is intended as a Fan Game, or as Parody of Legitimate Pokemon titles, and no Concreate Game produced using this project should be considered legitimate or affiliated to the above companies, unless they provide official consent to the connection. This project, and all games produced using this specification intend no copyright infringement or Intellectual Property theft of any kind.<br/><br/>


The PokemonSMS Resource Naming Rules("This Document"), provided by the PokemonSMS Public Specification Project ("This Project") is Copyright Connor Horman("The Owner"), 2018. 
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
This document defines how Entry names for the Various Entries are formed, as well as limitations on naming. 
<br/><br/>
<h1>Resource Naming</h1>
<h2>Basic Overview</h2>
PokemonSMS uses a 2-part Resource Naming System to identify Entries into the Registries. The system is used to prevent entry clashes between differing versions, differing Core Libraries, and between Custom Injected Libraries (Modifications). 
Each name is made up of 2 parts, the Domain, and the path. The actual name of the resource with domain "foo" and path "bar" is foo:bar. The Domain must be a valid Java Unqualified Identifier in the Ascii Charset (That is, it just start with a latin letter or an underscore, and each other character in the name must be a latin letter, a latin underscore). The path is made up of 1 or more components, separated with a /. Each component must also be a valid Java Unqualified Identifier.
<h2>Registering Entries</h2>
Entries are stored in a registry, which organizes entries in bidirectional unique mappings from keys to values. Registering an Entry adds that Entry to the registry, and specifically to the domain its registered with.
Entries are Registered if:
<ul>
<li>They are defined within the Core Libraries of PokemonSMS (as stated in Library Loading and activating)</li>
<li>The current Implementation manually registers them (Implementation Defined Entries)</li>
<li>Custom Libraries loaded in a Implementation Defined way defines them (Known as Injected Entries)</li>
</ul>
When a Custom Library Injects an entry, it becomes the owner of that domain. The behavior is undefined if multiple loaded libraries inject entries into a single domain (addition restrictions on injected entries are also specified. See Reserved Domains)<br/>
It is illegal to register more than one entry with a given name. Implementations are required to block multiple entries being registered under the same name. It is also recommended to prevent multiple identical entries from being registered under different names, though not as strictly enforced.

<h2>Reserved Domains</h2>
There are 7 Distinct Domains which have special rules about what can be added to them. These are called reserved domains. The domains are defined either to prevent ambiguity, for implementations to place custom entries in, and for other special entries.
It is undefined behavior to add entries to any of these domains in violation of the rules for doing so.
<h3>pokemon</h3>
The pokemon domain is the basic domain. All Entries presently added by the Core Libraries are placed into the pokemon domain.
Only the Core Libraries are allowed to add entries to the pokemon domain. 

<h3>system</h3>
The system domain is an internal domain, which contains standardized names. 
These entries are standard entries for which the properties are implementation defined.
Like other internal domains, only the implementation is allowed to add entries to the system domain.
It is also illegal for Implementations to add entries to the system domain which are not listed below.<br/>
This domain is presently used for the Implementation Provided null entries, and special entries that have to interact with Internal Game Elements which are not exposed as a script binding<br/> 
There are 12 entries into the system namespace:
<ul>
<li>system:pokemon/null(Pokemon)</li>
<li>system:items/null(Item)</li>
<li>system:abilities/null(Ability)</li>
<li>system:moves/null(Move)</li>
<li>system:tiles/null(Tile)</li>
<li>system:sprites/null(Sprite)</li>
<li>system:npcs/null(NPC)</li>
<li>system:trainers/null(Trainer)</li>
<li>system:locations/null(Location)</li>
<li>system:room/arcuesRoomSprite(Sprite, Special)</li>
<li>system:room/subwayLoadSprite(Sprite, Special)</li>
<li>system:room/subwayUnloadSprite(Sprite, Special)</li>
</ul>

Null entries have no associated events, an implementation defined unlocalized names, and all required type dependent fields are set to valid but meaningless values.  
Special Entries have there 

<h3>impl and internal</h3>
The impl and internal domains are internal domains, which is reserved for implementation specific entries. 
Like other internal domains, entries can only be added to the impl domain by the Implementation. 
The distiction between impl and internal is that impl entries should be shared by Implementations which are both a client and a server implementation, whereas internal entries should be reserved to one side.
If an implementation only implements one side, there is no real distiction betwen the two domains.

<h3>mod</h3>
The mod domain is reserved to prevent generic domains from being used in modifications.  No entries are specified for the mod domain, nor may any entries be added to it. 

<h3>client and server</h3>
The client and server domains are reserved standard domains, which are reserved to place single sided standard entries in. Presently, neither specifies any entries, but they are reserved for future release.

<h3>test</h3>
The test domain is a standard domain which is used for testing an implementations  compliance to this Specification. 
Only the Core Libraries are allowed to add entries to the test domain, and only when the implementation is testing as above.
There are 1000 defined entries in the test domain, all of which are listed in the ImplementationTesting.md document.





