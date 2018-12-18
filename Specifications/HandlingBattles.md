# Info and Copyright Notice #
## Copyright ##
PokemonSMS Public Specification Project, Copyright 2018 Connor Horman
Pokemon, the Pokemon Logo, and all Official Pokemon are Copyright Nintendo and Game Freak. This Project is in no way affiliated with Nintendo or Game Freak, and disclaims all relation with the above parties. This project is intended as a Fan Game, or as Parody of Legitimate Pokemon titles, and no Concreate Game produced using this project should be considered legitimate or affiliated to the above companies, unless they provide official consent to the connection. This project, and all games produced using this specification intend no copyright infringement or Intellectual Property theft of any kind.<br/><br/>


The PokemonSMS Battle Event Handling Specification("This Document"), provided by the PokemonSMS Public Specification Project ("This Project"), is Copyright Connor Horman("The Owner"), 2018. 
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

This document defines the logical sequence of events that occur during battle (this provides sequencing on event handlers). 

# Battle Event Handling #

## Handling Combat Moves ##
A combat move is defined as a move registered with category=Constants.Categories.PHYSICAL or Constants.Categories.SPECIAL. 

Combat Moves deal damage, they have secondary effects and are affected by type effectiveness. 

During Battle b, when Pokemon a, controlled by Side s, uses a Combat Move m on the list of targets ts..., Resolve the following for each target t in ts... in slot order, except that if any step causes the move to fail, then no additional steps are applied: 
1. Create a State k for the move. The state has the type Battle#CombatState as defined in the ScriptBindings specification. The Source is a, the Source Side is s, the Move is m, the base category is m:getCategory(), the base type is m:getType(), the target is t, the target side is t:getSide(), and the battle is b. 
2. Check move preconditions. If m:hasTrait("ohko") is true, then t:getLevel() <= b:getLevel() must also be true, or the move fails with `{"translate":"traits.ohko.cantaffect"},m:getName(),t:getName()`. 
3. Check move accuracy. If m:hasTrait("noaccuracy") is true, then skip this step. Otherwise resolve the following sequence in order.
    1. Set the Base Accuracy Modifier to a:getInBattleStat(Constants.Stats.Accuracy) and the base Evasion Modifier to t:getInBattleStat(Constants.Stats.Evasion)
    2. If m.getAccuracy is a function value or a table with a `__call` metatag, then the base accuracy value is set to the result of m:getAccuracy(a,t)
    3. Otherwise the base accuracy value is set to m.accuracy field. This shall be a number between 0 and 1.
    4. If the eventBus of m is subscribed to Events.Battle.Combat.CheckAccuracy with source being a, then publish an event of that type to that eventBus with the parameters a,t,b,m,k. 
    5. If the eventBus of a is subscribed to Events.Battle.Combat.CheckAccuracy with source being a, then publish an event of that type to that eventBus with the parameters a,t,b,m,k. 
    6. If the eventBus of t is subscribed to Events.Battle.Combat.CheckAccuracy with source being a, then publish an event of that type to that eventBus with the parameters a,t,b,m,k.
    7. For any pokemon p that is selected by b:select(a,Constants.Battle.Targets.ADJACENT,a,t) or by b:select(t,Constants.Battle.Targets.ADJACENT,a,t), if the eventBus of p subscribes to 
