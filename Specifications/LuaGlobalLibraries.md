# Info and Copyright Notice #
## Copyright ##
PokemonSMS Public Specification Project, Copyright 2018 Connor Horman
Pokemon, the Pokemon Logo, and all Official Pokemon are Copyright Nintendo and Game Freak. This Project is in no way affiliated with Nintendo or Game Freak, and disclaims all relation with the above parties. This project is intended as a Fan Game, or as Parody of Legitimate Pokemon titles, and no Concreate Game produced using this project should be considered legitimate or affiliated to the above companies, unless they provide official consent to the connection. This project, and all games produced using this specification intend no copyright infringement or Intellectual Property theft of any kind.<br/><br/>


The PokemonSMS Lua Global Library Listing("This Document"), provided by the PokemonSMS Public Specification Project ("This Project"), is Copyright Connor Horman("The Owner"), 2018. 
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

Defines the library functions that apply to handling lua sources. 

# Lua Global Libraries [lua.lib] #

The libraries exposed to Lua Sources are a subset of the Lua 5.2 Libraries. 

## Synopsis ##

```lua
(in global environment)
_G = unspecified;
_VERSION = implementation defined;
math = math library;
bit32 = bit32 library;
package = package library;
function require(name)
function pairs(table)
function next(table,key)
function ipairs(table)
function inext(table,idx)
function type(value)
```

```
(in the environment of any lua source, unless override)
_ENV = unspecified
_G = unspecified
-- All values defined in the global environment
function setfenv(table)
```

All fields may not be assigned, except that any field not defined here may be assigned in the local environment. 

### Implementation Specific Libraries [lua.lib.impl] ###

The implementation may choose to add any lua standard library to the global environment, or any additional library with the following restrictions:

* None of the `os`, `io`, `debug`, or `coroutine` libraries may be added to the global enviornment this way
* The library MUST NOT allow lua source files to break the sandbox in any way.

It is unspecified which libraries are added to the global environment this way. Other lua global functions may not be added this way.  

### Recursive Immutability Rule [lua.lib.immutability] ###

All tables and libraries defined here apply the recursive immutability rule. That is, no index of the table may be assigned, and all table members of that table apply the same rule. 
The exception is that fields of _ENV may be assigned if they are not defined by this document, and such table fields are except from the recursive immutability rule. 
This rule also applies to all tables loaded via require. 

### Table Access Rule [lua.lib.tabacc] ###

Additionally, table access is only well defined by this specification for any table or library if the key is either a string or a positive integer. Table access with any other key type results in undefined behavior. 

## Environment Variables [lua.lib.env] ##

```lua
_G = unspecified; //(1)
_ENV = unspecified; //(2)
function setfenv(env); //(3)
```

1. Obtains a reference to the global environment. If this is used in a lua source file, returns a table that contains All members of the local environment, and any fields defined in the file, except ones defined with local. Specifically `_G._G` is an identical expression to `_G`. 
2. Obtains a reference to the current environment. If setfenv has not been used in the present environment, then results in a table that contains all members of the the local environment, including any fields defined by the file, except ones defined with local. Additionally, contains all fields defined with local by the directly enclosing scope, and all external enclosing scopes, except Upvalue scopes. Otherwise, results in the table passed to setfenv. If it has not be changed, it is unspecified if defining a field by assigning its index in `_ENV` results in defining that field at file scope or in the current scope. If it has been changed via setfenv, then defining a field by assigning its index in `_ENV` only affects the current block scope, except that the changes are reflected in the table passed to setfenv. 
3. Sets the table that is bound to _ENV. The local environment of the current block scope becomes the table, until the scope exists. 

## Lua Version [lua.lib.ver] ##

```lua
_VERSION = implementation defined;
```

As specified in Lua Source Handling ([lua.ver]), the Implementation uses either Lua 5.2 or Lua 5.3. If the implementation uses Lua 5.3 then _VERSION must be the string "Lua 5.3", otherwise it must be the string "Lua 5.2". 

All Lua Source files must be handled using this version. If Lua Binary Chunk files are parsed by the implementation, it is unspecified if the Lua Version reflected in _VERSION is the one used by the global environment or the one indicated by the Version Byte of the Binary Chunk. 

## Require Function [lua.lib.require] ##

```lua
function require(mod)
```

Attempts to load mod. If mod can be loaded, the result is cached in packages.loaded[mod] and returned later. Otherwise an error must be generated. 

`mod` may be any module defined by this specification as a standalone Module. It is implementation-defined if lua source files in the Core Libraries can be found via require. It is unspecified if any other lua files can be found via require. 

## Math Library [lua.lib.math] ##

```lua
math = math library
```

Contains a subset of the standard math functions defined by lua. 

### Math library Synopsis ###

```lua
pi = acos(1);
e = exp(1);
huge = --[[Infinity]];
function sin(a)
function cos(a)
function tan(a)
function asin(a)
function acos(a)
function atan(a)
function atan2(x,y)
function sinh(a)
function cosh(a)
function tanh(a)
function exp(a)
function pow(b,e)
function sqrt(a)
function log(p,[b=e])
function log2(p)
function hypot(a,b)
function fma(m,x,b)
function max(a,...)
function min(a,...)
```

### Math Library Requirements [lua.lib.math.req] ###

All functions computed here shall be computed in FP-Strict Mode, as though the floating point representation is IEC-559 binary64 floating point number. The following requirements apply to these functions in general:

* All floating-point exceptions must be supressed during the execution of the function
* Floating-point exceptions for Underflow, Overflow, and inexact values must not result from these functions
* The value may not be converted to any other floating-point representation during the calculation
* Inexact values are to be handled with Round-To-Nearest rounding mode. 

### Constants [lua.lib.math.const] ###

```lua
huge = see below; //(1)
pi = see below; //(2)
e = see below; //(3)
```

1. The largest possible value. Defined as Infinity. 
2. The constant pi. The value shall be the result of the function `acos(1)`. 
3. The constant e. The value shall be the result of the function `exp(1)`.

### Trigonometric Functions [lua.lib.math.trig] ###

```lua
function sin(a) //(1)
function cos(a) //(2)
function tan(a) //(3)
function asin(r) //(4)
function acos(r) //(5)
function atan(r) //(6)
function atan2(x,y) //(7)
function sinh(a) //(8)
function cosh(a) //(9)
function tanh(a) //(10)
```

1. Computes the sine of angle a, in radians. 




