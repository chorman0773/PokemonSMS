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

This document defines the list of item entries added by Core Libraries to the `pokemon` reserved domain. 

# Item Entries for Pokemon Domain # 

## Potion ##

ResourceId: `pokemon:potion`

Name Translation Key: `items.pokemon.potion.{subitem}.name`

Description Translation Key: `items.pokemon.potion.{subitem}.name`

Type: Consumable Item (non-held)

Pocket: `heal` 

### Generic Description ###

Heals the pokemon it is used on by a specific ammount. Cannot be used on a pokemon that is fainted or has its maximum hp, except that subitem 4 can be used on a pokemon that has its maximum hp if it is affected by an NVStatus or is Confused (in-battle only). 

### Restrictions ###

Can apply to all pokemon, except under the conditions described above. Can be used as an in-battle consumable in a battle `b` when `b:canGainExperience()` is true. 


### Subitems ###

1. regular, heals 20 HP 
2. super, heals 60 HP
3. hyper, heals 120 HP
4. max, heals the target to `p:getMaximumHP()`
5. full, heals the target to `p:getMaximumHP()` and applies `p:cure()`. In battle, applies `p:setConfusionTimer(0)`. 


