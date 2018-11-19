<h1>Info and Copyright Notice</h1>

<h2>Copyright:</h2>
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
<h2>Info</h2>
This file lists many of the terms used in the PokemonSMS Specification, and there meaning. It also distinguishes between similar terms.

<h1>Term list</h1>
<h2>PokemonSMS Core</h2>
The PokemonSMS Core (Commonly just "Core") is the /lib folder and its complete contents. That is, the core code, resources, and language files that make up the main structure of PokemonSMS. The PokemonSMS Core Libraries refers to the /lib/lua folder, which specifically is Code portion of the PokemonSMS Core. 


<h2>Implementation</h2>
An Implementation is some Encapsulating Runtime, which loads, parses, and executes the PokemonSMS Core in accordance with some portion of this specification which is at least the Minimum requirements. 
A non-conforming Implementation is an Implementation which violates some part of the rules of the portion of the specification which it provides. 
A Complete Implementation is an Implementation which provides all components of a particular side. In general, server side implementations should be Complete. 


<h2>Extension</h2>
An Extension is an additional code, either in the Implementation Language, a Lua Script, or any other Implementation Defined Languages, that is part of neither the Core Libraries or the Implementation. It is Implementation Defined how extensions are loaded, or even if they are supported. Extensions have restrictions on what actions they are allowed to perform.


<h2>Resource</h2>
A Resource is either, some part of the Core which is not part of the Core Libraries, a non-code portion of an Extension, or a binding from the Core Libraries to the Implementation.


<h2>MUST/MUST NOT</h2>
MUST and MUST NOT are the strongest qualifiers used in the PokemonSMS Specification. 
If part of the specification says that an Implementation MUST perform some action, or expose some resource, then it is required that the Implementation perform that action or expose that resource, or the Implementation is Non-conforming. 
MUST NOT is the opposite qualifier, that is, if part of the specification says that an Implementation MUST NOT perform some action, then the Implementation is not allowed to perform that action. In general, there are no resources that an Implementation MUST NOT Expose. If an implementation performs an action it MUST NOT perform, then it is Non-conforming. 
In general, the Core Libraries will not load correctly, or at all, on an Implementation that does not expose a resource or perform an action that an Implementation MUST expose or perform, or one that performs an action it MUST NOT perform. 
If an extension MUST NOT perform an action, then the Implementation MUST respond to an extension which does, either by discontinuing execution, or disabling the extension. The Implementation MUST NOT silently ignore the violation, or block the instance of the violation only.


<h2>MAY NOT</h2> 
MAY NOT is a step down from MUST/MUST NOT. If an implementation MAY NOT perform an action, or expose a resource, then the Core Libraries will ignore the case that that action is performed or resource exposed. It is not a violation of the specification to violate a MAY NOT clause, however, the execution may not run as intended. Exposing a resource that MAY NOT be exposed in general has no effect. Performing an action which MAY NOT be performed is Undefined Behavior. For example, Implementations MAY NOT add custom entries to the pokemon domain. 
If an extension MAY NOT perform an action, or expose a resource, it is unspecified how the Implementation acts. In general, an implementation which performs an action or exposes a resource that it MAY NOT has undefined behavior. 

<h2>SHOULD NOT</h2>
SHOULD NOT not is similar to MAY NOT, except in terms of Implementations, it describes actions which would not affect the observable behavior of the Implementation, nor any behavior of other implementations which may rely on the effects of the implementation. 
It may also describe an action which may have negative effects on the user, but have no direct effect on gameplay (for example, using a Weak Source of Random numbers for the CryptoShade extensions in SaveFiles). 
Actions which SHOULD NOT be performed by extensions follow the same pattern. 
A resource which SHOULD NOT be exposed is treated as one that MAY NOT be exposed.

<h2>SHOULD/MAY</h2>
SHOULD and MAY refer to actions which are optional for the Implementation to perform, or resources which are option for the Implementation to expose. It may also refer to such an action or resource optionally performed or exposed by an extension. There is no distiction between SHOULD or MAY except that SHOULD will be used when it is recommended to perform that action or expose that resource. 

<h2>Physical Side</h2>
The Physical Side is the roll of the Implementation, the Server or the Client. An Implementation which acts as a Controller hub for PokemonSMS Clients to connect to is a Server. An Implementation which provides a user interface, and allows users to connect to servers is a client. See Siding.md for a specific definition of Server/Client.



