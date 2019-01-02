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

This document defines the Constants Library of PokemonSMS. 

# Constants Library [bind.const] #

The constants library is a library containing a set of immutable tables that define constant values that can be used in specific circumstances. The values and the actual defined type of the values are unspecified, given a few requirements (listed below). 

As with all other public libraries, Recursive Immutability applies to the Constants Library. Attempting to assign any string or positive integer index of the table obtained from require"Constants" or any member of that result is an error. Attempting to assign any other index for that table results in undefined behavior. 

Additionally, as with all other public libraries, all string and positive integer indecies defined within Constants (or any subconstants) shall be nil if and only if that index is not defined in this file. It is unspecified if any other types of keys have definitions. 

## Symbolic Types [bind.const.symbolic] ##

The Field values defined in `Constants` do not have a real type enforced by this specification. Instead they have a Symbolic Type. The following guarantees are required to be made for these symbolic types: 
* The values are defined and are not false. Formally, they must be considered `true` by any boolean context
* Any valid value of that type compares equal with any other valid value of the same type, if and only if they are the same constant as defined here. 
* Each constant defined to be of the type is a valid value of that type. It is implementation-defined if additional constants are defined to be of the type. Any other values of the symbolic type are invalid. 
* Any valid value of that type compares inequal with any other valid value of the same type, unless the values would compare equal. 

The following operations are not defined for values of a Symbolic type. Formally, attempting any of these operations results in undefined behavior:
* Equality/Inequality Comparison between invalid values of the same type, or between an invalid value and a valid value, even if such a comparison would otherwise be well defined. 
* Equality/Inequality Comparison between values of any symbolic type and any value of a different type, even if such a comparison would otherwise be well defined. 
* Relational Comparison between a value of a symbolic type and any value, even if such a comparison would otherwise be well defined. 
* All type sensitive operations, including type(). 
* Conversion to/Use as any other type in lua, except boolean. 
* Indexing/Assigning an index of the value, including via pairs/ipairs
* Any arithmetic, bitwise (if supported), or concatination operator






