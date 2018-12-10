# Info and Copyright Notice #

## Copyright ##
PokemonSMS Public Specification Project, Copyright 2018 Connor Horman
Pokemon, the Pokemon Logo, and all Official Pokemon are Copyright Nintendo and Game Freak. This Project is in no way affiliated with Nintendo or Game Freak, and disclaims all relation with the above parties. This project is intended as a Fan Game, or as Parody of Legitimate Pokemon titles, and no Concreate Game produced using this project should be considered legitimate or affiliated to the above companies, unless they provide official consent to the connection. This project, and all games produced using this specification intend no copyright infringement or Intellectual Property theft of any kind.<br/><br/>


The PkmCom Protocol Concrete Protocol Definition("This Document"), provided by the PokemonSMS Public Specification Project ("This Project"), is Copyright Connor Horman("The Owner"), 2018. 
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
The PkmCom Concrete Protocol Definition defines how the established Client<->Server connection is used to send information and requests, and how that data is verified. 

# Notes #

This Concrete Defintion relies on the PkmCom APL defined by PkmComProtocolApl. 
This document will assume knowledge of the APL. 
Packets are structured as defined in that document. 

## Post-Handshaking ##
These packets are exchanged by the server after the handshake is completed, and the shared secret is established. 

### 0x00 Connect Request ###

Serverbound

Sends Trainer Data, optional Sentry Account Data, and optional implementation cci info. 

<h4>Payload</h4>
<table>
	<tr>
		<th>Field</th>
		<th>Type</th>
		<th>Description</th>
	</tr>
	<tr>
		<td>PkmVersion</td>
		<td>Version</td>
		<td>The Version of the PkmCom protocol provided by the implementation. As of this version of the Spec, this version should be 1.0</td>
	</tr>
	<tr>
		<td>TrainerInfo</td>
		<td>Json</td>
		<td>A Trainer info structure containing the name component, image, hours spent on the game, etc.</td>
	</tr>
	<tr>
		<td>SentryAccount</td>
		<td>UUID</td>
		<td>The Id of the Logged in Sentry Account, or the NIL UUID if not logged in</td>
	</tr>
	<tr>
		<td>Provider</td>
		<td>Json</td>
		<td>An empty JsonObject ({}) or a CCI Provider Structure which represents the provider. May be challenged by the Server</td>
	</tr>
</table>

#### Rules ####
If the server supports Sentry Accounts, and SentryAccount is not the NIL UUID, the server must query the sentry account server to obtain a Challenge Key, then send a 0x04 Challenge Identity Packet. 
If the Challenge fails, or is not responded to in a timely manner (2 seconds), than a 0x02 Connection Failed packet must be sent in response, and the connection closed. (See Sentry Webserver API, Challenging Identity). 
Additionally, if the SentryAccount is not the NIL UUID, the SentryAccount MUST NOT be already connected to the server. 

The PkmCom Version MUST be at most the present version of the specification, and MUST be supported by the Server. 
Servers may implement an arbitrary version of this specification which is at most the present version, and at least that version's origin. Implementations are required to implement an arbitrary version of this specification, which is at most the present version, at at least the origin of that version. Server Implementations are also required to support all versions that released prior to that version of the specification and that have the same origin. Client Implementations are not required to support more than one version of this specification. The origin of a version is the version which has the same major component and a minor component of 0. 



### 0x01 Connect Success ###

Clientbound 

Sent to indicate that the Connection to the server was established succesfully, after the server issues any challenges based on the Provided account and has verified the challenge.  



