# Info and Copyright Notice #

## Copyright ##
PokemonSMS Public Specification Project, Copyright 2018 Connor Horman
Pokemon, the Pokemon Logo, and all Official Pokemon are Copyright Nintendo and Game Freak. This Project is in no way affiliated with Nintendo or Game Freak, and disclaims all relation with the above parties. This project is intended as a Fan Game, or as Parody of Legitimate Pokemon titles, and no Concreate Game produced using this project should be considered legitimate or affiliated to the above companies, unless they provide official consent to the connection. This project, and all games produced using this specification intend no copyright infringement or Intellectual Property theft of any kind.<br/><br/>


The PokemonSMS Lua Engine Specification("This Document"), provided by the PokemonSMS Public Specification Project ("This Project"), is Copyright Connor Horman("The Owner"), 2018. 
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
This document defines how implementations are to handle lua standard libraries and lua standard globals, as well as how lua script files are handled and interpreted.

## Note ##
This file references the lua files defined by the PokemonSMS Core Libraries, and by extension libraries as "Lua Source Files" or "source files". Restrictions defined by this part of the specification ONLY apply to these such files and are not required to be honored for any implementation provided lua files (this makes it possible for PokemonSMS to be implemented in lua, as such an implementation would not be able to execute within the sandbox).  

# Lua File Interpretation [lua] #

## Lua Interpret Version [lua.ver] ##
Lua Source files are to be interpreted as per the [Lua 5.2 language](http://www.lua.org/manual/5.2/) with some modifications. 

Alternatively, implementations may choose to interpret the file per the [Lua 5.3 Language](http://www.lua.org/manual/5.3/) with the same modifications (however the bit32 library MUST still be exposed as below). It is implementation defined if this is the case. 

The modifications are made to ensure that each source file exists within its own sandbox, independant of other source files.

## Execution Environment [lua.exec] ##
In PokemonSMS, every lua source file executed by the implemention must execute independently of every other file. That is, all side effects of executing any given source file MUST NOT affect the execution of any other file. This is achieved by isolating the global environment per file, as well as restricting the libraries accessible to these files. 

All members of the global libraries exposed to these files may be shared or per file. However, attempts to assign these members, or members of these members must result in an error. 

If by some bug or obsecure feature of lua (which is not documented here), a lua source file is able to bypass this restriction in an implementation which conforms to these rules, the behavior of a file which does so is undefined. Implementations are not required to diagnose this issue. 

Despite Caching the result of require in package.loaded having visible side effects, it is not considered a violation of this specification (as it simply an optimization with both the members of package.loaded and their members (recursively) being unassignable). 



## Numeric Calcuations [lua.num] ##

There are 2 distinct numeric types specified in the modified lua that apply to this specification, integer and float. These types have similar, but distinct handling. Integer values shall be treated as a 32-bit signed integer using 2s compliment representation. Float values shall be treated as a 64-bit IEC 559 double-precision floating point value, except that Signaling NaN values are not distinct from Quiet NaN values. 

An integer value is result of one of the following expressions:
* An integer literal, which is a number literal that does not contain a decimal point, an exponent, and is representable as an integer value
* The "index" value obtained with the ipairs iterator or similar iterators. 
* The result of the addition operator, subtraction operator, multiplication operator, and division operator when both operands are integer values
* The result of the unary minus operator, when the operand is an integer value
* The result of any function in the bit32 library.

All addition/subtraction/multiplication involving integer values (and only integer values), shall be carried out in the domain of 32-bit signed integers. It is unspecified if division is carried out in this domain, but the result must be truncated (round-towards-zero) if it is not representable in an integer. If an integer value is divided by 0 this way, the implementation must raise an error. If overflow would occur, the result shall be the result of performing this operation with float values instead, after which the result is truncated to an infinite precision integer, of which the low order 32-bits are preserved, and the result is the value (overflows result in wraparround to negative values). 
