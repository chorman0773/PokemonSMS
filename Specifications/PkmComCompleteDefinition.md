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

# PkmCom Complete/Concrete Definition [pkmcom.cmpl] #

## Notes ##

This Concrete Defintion relies on the PkmCom APL defined by PkmComProtocolApl. 
This document will assume knowledge of the APL. 
Packets are structured as defined in that document. 

## Post-Handshaking [pkmcom.cmpl.posthandshake] ##
These packets are exchanged by the server after the handshake is completed, and the shared secret is established. 

If a Protocol error occurs within a Serverbound Post-Handshaking, the response is a 0x02 Connect Failure. If a protocol error occurs within a Clientbound Post-Handshaking Packet, the connection is to be closed (but may be re-established). 

### 0x00 Connect Request [pkmcom.cmpl.posthandshake.connect] ###

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
</table>

#### Sentry Account [pkmcom.cmpl.posthandshake.connect.acc] ####

If the server supports Sentry Accounts, and SentryAccount is not the NIL UUID, the server must query the sentry account server to obtain a Challenge Key, then send a 0x03 Challenge Identity Packet. The request must be made for the Sentry PokemonSMS Game (gameid: f008f340-bff6-11e8-a355-529269fb1459).
If the Challenge fails, or is not responded to in a timely manner (2 seconds), than a 0x02 Connection Failed packet must be sent in response, and the connection closed. (See Sentry Webserver API, Challenging Identity).
Additionally, if the SentryAccount is not the NIL UUID, the SentryAccount MUST NOT be already connected to the server. 


#### Version [pkmcom.cmpl.posthandshake.connect.ver] ####
The PkmCom Version MUST be at most the present version of the specification, and MUST be supported by the Server. 
Servers may implement an arbitrary version of this specification which is at most the present version, and at least that version's origin. Implementations are required to implement an arbitrary version of this specification, which is at most the present version, at at least the origin of that version. Server 
Implementations are also required to support all versions that released prior to that version of the specification and that have the same origin. 
Client Implementations are not required to support more than one version of this specification. 
The origin of a version is the version which has the same major component and a minor component of 0. 
It is implementation defined which version(s) the client supports and will attempt to connect with and the version(s) supported by the server


### 0x01 Connect Success [pkmcom.cmpl.posthandshake.connsuccess] ###

Clientbound 

Sent to indicate that the Connection to the server was established successfully, after the server issues any challenges based on the Provided account and has verified the challenge.  

<table>
	<tr>
		<th>Field</th>
		<th>Type</th>
		<th>Description</th>
	</tr>
	<tr>
		<td>Server Info</td>
		<td>Json</td>
		<td>Misc Info about the server, including Brand, Version, Specification and Core Libraries version, and any other info the server wishes to send</td>
	</tr>
	<tr>
		<td>Available Actions</td>
		<td>Unsigned Int Bitfield</td>
		<td>Defines the actions that the server permits the client to take. Bits 0-3 are defined below, and bits 4-7 are reserved. All other bits are unused.</td>
	</tr>
</table>

#### Available Actions Bitfield [pkmcom.cmpl.posthandshake.connsuccess.acts] ####

<table>
	<tr>
		<th>Bit</th>
		<th>Name</th>
		<th>Description</th>
	</tr>
	<tr>
		<td>0x01</td>
		<td>Trade</td>
		<td>Set if the server permits the client to trade pokemon</td>
	</tr>
	<tr>
		<td>0x02</td>
		<td>Battle</td>
		<td>Set if the server permits the client to battle on the server</td>
	</tr>
	<tr>
		<td>0x04</td>
		<td>IRC</td>
		<td>Set if the server supports IRC and permits the client to join IRC Channels</td>
	</tr>
	<tr>
		<td>0x08</td>
		<td>Game Share</td>
		<td>Unused on Dedicated Servers, only available on PkmCom LAN. Specifies that the LAN Client is sharing there game to LAN</td>
	</tr>
</table>


### 0x02 Connect Failure [pkmcom.cmpl.posthandshake.connfail] ###

Clientbound
		
Sent by the server when the server refuses the connection to the client for some reason. 

<table>
	<tr>
		<th>Field</th>
		<th>Type</th>
		<th>Description</th>
	</tr>
	<tr>
		<td>Reason</td>
		<td>Json</td>
		<td>The text component that indicates the reason</td>
	</tr>
	<tr>
		<td>Num. Args</td>
		<td>Byte</td>
		<td>The number of arguments for the component</td>
	</tr>
	<tr>
		<td>Args</td>
		<td>Array of Json</td>
		<td>Arguments for Formatting the Reason Component</td>
	</tr>
</table>

### 0x03 Challenge Identity [pkmcom.cmpl.posthandshake.challengereq] ###

Clientbound

Sent by servers that support sentry accounts when a client attempts to connect using a sentry account to verify the identity of the client. 

<table>
	<tr>
		<th>Field</th>
		<th>Type</th>
		<th>Description</th>
	</tr>
	<tr>
		<td>Authority Request</td>
		<td>String</td>
		<td>Base64 authority request, randomly generated by the server</td>
	</tr>
</table>

### 0x04 Challenge Response [pkmcom.cmpl.posthandshake.challengerep] ###

Serverbound

Sent in response to a 0x03 Challenge Identity request.

<table>
	<tr>
		<th>Field</th>
		<th>Type</th>
		<th>Description</th>
	</tr>
	<tr>
		<td>Session Key</td>
		<td>String</td>
		<td>Base64 encoded RSA key associated with the session key that signed the Authority Request</td>
	</tr>
	<tr>
		<td>Authorization</td>
		<td>String</td>
		<td>Base64 encoded signature of the Authority Request, which is equivalent to the Authorization field in Sentry Authentication WebAPI Requests</td>
	</tr>
</table>

#### Verification [pkmcom.cmpl.posthandshake.challengerep.verify] ####

The Session Key shall be checked against sentry to recieve the scopes associated with it. 

The Session Key must indicate general, game:f008f340-bff6-11e8-a355-529269fb1459, and server:game scopes for the associated account or the connection must be denied.
 
Otherwise, Authorization must be the hash signature of the Authority Request, signed with the Associated private key for the given session key. 


### 0x05 Re-establish Secret [pkmcom.cmpl.posthandshake.rehandshake] ###

Clientbound

Sent after identity challenges (if any), but before the connection is established. The server may send this to require the client to establish a new secret. The Handshake continues from Step 3. If the Reason code is zero, then handshaking occurs normally. Otherwise the alternative handshaking operation may be used. 

If the client refuses the request or cannot satisfy the request, then it should close the connection. 

After handshaking completes, the server then establishes the connection. 

<table>
	<tr>
		<th>Field</th>
		<th>Type</th>
		<th>Description</th>
	</tr>
	<tr>
		<td>Reason</td>
		<td>Byte Enum</td>
		<td>The reason the Secret is re-established (see below)</td>
	</tr>
</table>

#### Reason Codes [pkmcom.cmpl.posthandshake.rehandshake.reason] ####

<table>
	<tr>
		<td>Value</td>
		<td>Description</td>
	</tr>
	<tr>
		<td>0</td>
		<td>Re-establish the secret normally</td>
	</tr>
	<tr>
		<td>1</td>
		<td>The server requires a password to connect to</td>
	</tr>
	<tr>
		<td>2-8</td>
		<td>An implementation specified Side Secret is used. This may be used on proprietary servers to restrict connecting to authorized clients. These are effectively unused, and clients that don't support implementation-specific secrets should close the connection.</td>
	</tr>
</table>

It is Implementation Defined if re-establishing the secret is supported by a client and it is unspecified what reason codes are supported. It is implementation defined if a server will request an alternative handshake in this manner. 

## General Packets [pkmcom.cmpl.general] ##

### 0x06 Keep Alive (Clientbound) [pkmcom.cmpl.gener.keepalive.client] ###

Clientbound

Sent on an unspecified but regular interval by servers. The client will require that at least one of these packets are recieved every 2s or the connection is to be closed. 
This allows clients to determine server-to-client latency, as well as state inconsistency from dropped packets. 

<table>
	<tr>
		<th>Field</th>
		<th>Type</th>
		<th>Description</th>
	</tr>
	<tr>
		<td>Keep Alive Id</td>
		<td>Long</td>
		<td>A random long generated by the server. This identifies the cycle and is used to determine latency</td>
	</tr>
	<tr>
		<td>Client Packets Recieved</td>
		<td>Unsigned Short</td>
		<td>The number of client packets recieved since the last serverbound keep alive</td>
	</tr>
	<tr>
		<td>Last Packet Id</td>
		<td>Byte Enum</td>
		<td>The Packet Id of the last packet recieved by the client. 0 if no packets were recieved since the last keep alive</td>
	</tr>
	<tr>
		<td>Last Packet Hash</td>
		<td>Int</td>
		<td>The hash of the last packet recieved by the client. 0 if no packets were recieved since the last keep alive</td>
	</tr>
	<tr>
		<td>Encoding Time</td>
		<td>Instant</td>
		<td>The Instant that the packet was encoded on the server.</td>
	</tr>
</table>

### 0x07 Keep Alive (Serverbound) [pkmcom.cmpl.general.keepalive.server] ###

Sent by the client in response to Clientbound Keep Alive packets. The pair of packets form the keep alive cycle. 
This has acts effectively the same effect as the Clientbound Keep Alive packet, but on the server. 
The state of client and the server are resynced in a specific way. 


<table>
	<tr>
		<th>Field</th>
		<th>Type</th>
		<th>Description</th>
	</tr>
	<tr>
		<td>Keep Alive Id</td>
		<td>Long</td>
		<td>The Keep Alive Id of the last Clientbound Keep Alive recieved by the client.</td>
	</tr>
	<tr>
		<td>Encoding Time</td>
		<td>Instant</td>
		<td>The time that the packet was encoded by the client.</td>
	</tr>
	<tr>
		<td>Server Packets Recieved</td>
		<td>Unsigned Short</td>
		<td>The number of clientbound packets recieved before the last Clientbound Keep Alive recieved by the client, and since the previous one</td>
	</tr>
	<tr>
		<td>Last Packet Id</td>
		<td>Byte Enum</td>
		<td>The Packet Id of the last clientbound packet recieved by the client before the last clientbound keep alive</td>
	</tr>
	<tr>
		<td>Last Packet Hashcode</td>
		<td>Int</td>
		<td>The Packet Hash of the last clientbound packet recieved before the last clientbound keep alive</td>
	</tr>
</table>

## IRC Packets [pkmcom.cmpl.irc] ##


### IRC Channel Format [pkmcom.cmpl.irc.channel] ###

Various IRC Channels are available on the PkmCom Servers that support IRC. These channels take various forms depending on there type:

All Channels start with a group prefix, then end an identifier component.



### 0x10 IRC Channel List [pkmcom.cmpl.irc.channels] ###

Clientbound

Sent to set the list of IRC Channels available in general on the  
