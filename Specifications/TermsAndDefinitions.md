# Info and Copyright Notice #

## Copyright ##
PokemonSMS Public Specification Project, Copyright 2018 Connor Horman
Pokemon, the Pokemon Logo, and all Official Pokemon are Copyright Nintendo and Game Freak. This Project is in no way affiliated with Nintendo or Game Freak, and disclaims all relation with the above parties. This project is intended as a Fan Game, or as Parody of Legitimate Pokemon titles, and no Concreate Game produced using this project should be considered legitimate or affiliated to the above companies, unless they provide official consent to the connection. This project, and all games produced using this specification intend no copyright infringement or Intellectual Property theft of any kind.<br/><br/>


The PokemonSMS Specification Terms and Definitions("This Document"), provided by the PokemonSMS Public Specification Project ("This Project") is Copyright Connor Horman("The Owner"), 2018. 
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
## Info ##
This file lists many of the terms used in the PokemonSMS Specification, and there meaning. It also distinguishes between similar terms.

# Term list [terms] #



## PokemonSMS Public Specification [terms.spec] ##



The PokemonSMS Public Specification is this set of documents that define the behavior of Implementations of PokemonSMS. May also appear as PokemonSMS Specification or This Specification. 

## PokemonSMS [terms.game] ##



Refers to the complete work consisting of the PokemonSMS Public Specification and PokemonSMS Core. 

## PokemonSMS Core [terms.core] ##



The PokemonSMS Core (Commonly just "Core") is the /lib folder and its complete contents. That is, the core code, resources, and language files that make up the main structure of PokemonSMS.

### PokemonSMS Core Libraries [terms.core.lib] ###



 The PokemonSMS Core Libraries refers to the /lib/lua folder, which specifically is Code portion of the PokemonSMS Core. 


## Implementation [terms.impl] ##



An Implementation is some Encapsulating Runtime, which loads, parses, and executes the PokemonSMS Core in accordance with some portion of this specification which is at least the Minimum requirements. 
A non-conforming Implementation is an Implementation which violates some part of the rules of the portion of the specification which it provides. 
A Complete Implementation is an Implementation which provides all components of a particular side. In general, server side implementations should be Complete. 


## Extension [terms.extension] ##



An Extension is an additional code, either in the Implementation Language, a Lua Script, or any other Implementation Defined Languages, that is part of neither the Core Libraries or the Implementation. It is Implementation Defined how extensions are loaded, or even if they are supported. Extensions have restrictions on what actions they are allowed to perform.


## Resource [terms.resource] ##



A Resource is either, some part of the Core which is not part of the Core Libraries, a non-code portion of an Extension, or a binding from the Core Libraries to the Implementation.


## MUST/MUST NOT [terms.mandate] ##



MUST and MUST NOT are the strongest qualifiers used in the PokemonSMS Specification. 
If part of the specification says that an Implementation MUST perform some action, or expose some resource, then it is required that the Implementation perform that action or expose that resource, or the Implementation is Non-conforming. 
MUST NOT is the opposite qualifier, that is, if part of the specification says that an Implementation MUST NOT perform some action, then the Implementation is not allowed to perform that action. In general, there are no resources that an Implementation MUST NOT Expose. If an implementation performs an action it MUST NOT perform, then it is Non-conforming. 
In general, the Core Libraries will not load correctly, or at all, on an Implementation that does not expose a resource or perform an action that an Implementation MUST expose or perform, or one that performs an action it MUST NOT perform. 
If an extension MUST NOT perform an action, then the Implementation MUST respond to an extension which does, either by discontinuing execution, or disabling the extension. The Implementation MUST NOT silently ignore the violation, or block the instance of the violation only.


## MAY NOT [terms.maynot] ##


MAY NOT is a step down from MUST/MUST NOT. If an implementation MAY NOT perform an action, or expose a resource, then the Core Libraries will ignore the case that that action is performed or resource exposed. It is not a violation of the specification to violate a MAY NOT clause, however, the execution may not run as intended. Exposing a resource that MAY NOT be exposed in general has no effect. Performing an action which MAY NOT be performed is Undefined Behavior. For example, Implementations MAY NOT add custom entries to the pokemon domain. 
If an extension MAY NOT perform an action, or expose a resource, it is unspecified how the Implementation acts. In general, an implementation which performs an action or exposes a resource that it MAY NOT has undefined behavior. 

## SHOULD NOT [terms.shouldnot] ##



SHOULD NOT not is similar to MAY NOT, except in terms of Implementations, it describes actions which would not affect the observable behavior of the Implementation, nor any behavior of other implementations which may rely on the effects of the implementation. 
It may also describe an action which may have negative effects on the user, but have no direct effect on gameplay (for example, using a Weak Source of Random numbers for the CryptoShade extensions in SaveFiles). 
Actions which SHOULD NOT be performed by extensions follow the same pattern. 
A resource which SHOULD NOT be exposed is treated as one that MAY NOT be exposed.

## SHOULD/MAY [terms.suggestion] ##

SHOULD and MAY refer to actions which are optional for the Implementation to perform, or resources which are option for the Implementation to expose. It may also refer to such an action or resource optionally performed or exposed by an extension. There is no distiction between SHOULD or MAY except that SHOULD will be used when it is recommended to perform that action or expose that resource. 

## Illegal Action [terms.illegal] ##


An Illegal Action is some action which is forbidden by the specification, and the specification mandates that an error be generated. Implementations that encounter any code in an extension or in the core libraries which take an Illegal Action must cease execution of said code.  
Implementations are permitted to ignore the error outside of the violating code, or cease operation entirely (Crash). 

## Undefined Behavior [terms.undef] ##


Undefined behavior is the behavior of code contained within the Core Libraries or in Extensions which is in violation of this specification, when the specification violation has no requirements on handling it. 
Implementations are allowed to handle a violation which results in Undefined behavior in any way they choose. This includes, but is not limited to
* Generating an Error
* Ceasing operation entirely
* Entering some sort of unspecified recovery mode
* Taking the action, despite the violation, which may result in a meaningful or meaningless change in state
* Taking any other meaningful or meaningless action (defined below)
* Performing No operation
* or Performing some action in response to the violating action which may or may not be correct (this permits implementations to "allow" comparing constants of unrelated types as well as to define the constants from ScriptHooks.md as Numbers)

Actions taken in response to undefined behavior may not affect the persistent state of any game directly. 
Implementations are allowed to ignore cases in which undefined behavior occurs, and make optimizations to the internal code 

## Effective No-op [terms.nop] ##

At any point in the game, implementations are allowed to take any side operation they wish outside of the specification, provided that the operation is a no-op as far as this specification is concerned.  
Actions which are an Effective No-op are ones that do not affect the visible state of the game. These include, but are not limited to:
* Writing to some file, except a game save file or `saves.pkmdb`
* Writing to a table in the `saves.pkmdb`, except the Global Save Table
* Writing to the console
* Writing to some unspecified output area, except any Graphics Layer
* Reading some variable used by this specification.
* Reading or Writing an internal implementation variable that does not affect gameplay
* Prefetching and Caching any Resource in the Core
* Discarding a Cached Resource in the Core which is not actively in use
* While Connected to a Server, sending a 0x06 Keep Alive (serverbound) to the server is an effective no-op
* While running or acting as a server, sending a 0x05 Keep Alive (clientbound) to any connected client is an effective no-op
* Redrawing any Graphics Layer
* Refreshing sprites without updating them
* While Connected to a Server, and running in battle mode, sending a 0x21 Refresh State (Battle) is an effective no-op.
* While Connected to a Server, and running in trade mode, sending a 0x61 Refresh State (Trade) is an effective no-op 
* Prefetching any Lua library into the packages.loaded table for the use of require, including libraries defined by this specification, libraries defined by outside lua script files, or defined by the implementation. 


## Meaningful Action [terms.action.correct] ##

A meaningful action is an action permitted by this specification to be taken in the active context. It may either be an action specific to the context defined by this specification as Required, Optional, Implementation-Defined, or Unspecified, or an effective no-op (as above). 

In particular, a meaningful action may not be any of the following, unless the current context permits or requires that action to be taken (such an action taken in a context that does not permit or require the action is known as a meaningless action): 
* Changing the value of some variable used by this specification
* Undefining any Binding Function or variable defined by the specification
* Connecting to a Server or sending a packet to a connected server or client, except a 0x05 Keep Alive (Clientbound), 0x06 Keep Alive (Serverbound), 0x21 Refresh State (Battle), 0x61 Refresh State (Trade) 
* Drawing to the Graphics Layer (but redrawing is fine)
* Starting any battle mode
* Ending any battle mode
* Adding an entry to any reserved domain, except the internal and impl domains, or if the entry name is a reserved name. 
* Removing an entry registered in any domain, except the internal and impl domains, or if the entry name is a reserved name. 

## Meaningless Action [terms.action.incorrect] ##


A Meaningless action is one that is not permitted by this specification in the active context. It is any action taken that is neither considered an effective no-op nor defined by this specification in the context as Required, Optional, Implementation-Defined, or Unspecified. 

Such an action MUST NOT be taken by implementations, except in situtations where the core libraries or an extension takes an action that results in undefined behavior. 

## Implementation-Defined Behavior [terms.impl] ##

Behavior which is not constrained (or are loosely constrained) by the specification, but may not be a meaningless action. Implementations are required to act deterministically and consistently, and clearly document the results of implementation-defined behavior. 

## Unspecified Behavior [terms.unspecified] ##

Similar to Implementation-Defined Behavior however implementations are not required to document the results of unspecified behavior. Under most circumstances unspecified behavior is required to be handled deterministically and consistently.  

## Implementation Specific Behavior [terms.implspec] ##

The Superset of Implementation-Defined Behavior and Unspecified Behavior. 

## This Specification [terms.this] ##

The PokemonSMS Public Specification or some part thereof. 
