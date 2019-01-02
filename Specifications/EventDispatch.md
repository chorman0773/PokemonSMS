# Info and Copyright Notice #
## Copyright ##
PokemonSMS Public Specification Project, Copyright 2018 Connor Horman
Pokemon, the Pokemon Logo, and all Official Pokemon are Copyright Nintendo and Game Freak. This Project is in no way affiliated with Nintendo or Game Freak, and disclaims all relation with the above parties. This project is intended as a Fan Game, or as Parody of Legitimate Pokemon titles, and no Concreate Game produced using this project should be considered legitimate or affiliated to the above companies, unless they provide official consent to the connection. This project, and all games produced using this specification intend no copyright infringement or Intellectual Property theft of any kind.<br/><br/>


The PokemonSMS Event Dispatch Definition("This Document"), provided by the PokemonSMS Public Specification Project ("This Project"), is Copyright Connor Horman("The Owner"), 2018. 
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

This document defines how events are dispatched and handled by PokemonSMS.

# Event Dispatching [event] #

Event's are used to connect the execution sequence dealing with various parts of PokemonSMS to parts of the PokemonSMS Core Libraries and Extensions. 

## Event Library [event.lib] ##

The Event Library contains a set of constants, which act the same as constants in the Constants Library ([bind.constants]). They all possess the symbolic type `Event` ([bind.constants.symtype]), and act as such. 

There also exists a function call newEventBus()

### Event Library Synopsis [event.lib.def] ###

```lua
local Events = library;
Events.Battle = immutable {};
Events.Battle.Ability = immutable {};
Events.Battle.Ability.Lifetime = immutable {};
Events.Battle.Ability.Lifetime.Start = unspecified;
Events.Battle.Ability.Lifetime.End = unspecified;
Events.Battle.Ability.Lifetime.Refresh = unspecified;
Events.Battle.Ability.LocalLifetime = immutable {};
Events.Battle.Ability.LocalLifetime.Start = unspecified;
Events.Battle.Ability.LocalLifetime.End = unspecified;
Events.Battle.Ability.LocalLifetime.Refresh = unspecified;
Events.Battle.Combat = immutable {};
Events.Battle.Combat.MoveUsed = unspecified;
Events.Battle.Combat.CheckAccuracy = unspecified;
Events.Battle.Combat.CheckTypeEffectiveness = unspecified;
Events.Battle.Combat.CheckStats = unspecified;
Events.Battle.Combat.
```

## Subscribing to Events [event.subscribe] ##

A Component 

