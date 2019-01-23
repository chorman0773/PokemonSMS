# PokemonSMS Specification Implementation Defined Behavior #

Copyright (c) 2018, Connor Horman. 
Any person obtaining a copy of this document may view, copy, transmit, and distribute verbatim copies of this document, without restriction, to any other person. 

This document is provided on an AS IS basis without any warranties of any kind including the implied warranties of MERCHENTABILITY and FITNESS FOR A PARTICULAR PURPOSE. For no reason shall any person who transmitted this document to you, directly or indirectly, be held liable for any damage caused by use or misuse of this document. 

This Document defines the implementation-defined behavior and conditionally supported behavior of the PokemonSMS Java Runtime created by Connor Horman, which is defined in the PokemonSMS Public Specification. 
It also documents a limited amount of Unspecified Behavior. 

Note that this document is not intended as a guide, or walkthrough for PokemonSMS. This is a technical document to provided to conform with the PokemonSMS Public Specification. 

## General [general] ##

### Extensions [general.extensions] ###

Extensions written in lua are supported, and are provided by subfolders of the `/mods` folder relative to the runtime root folder. 
Extensions interact with the implementation in the same manner that the Core Libraries do. 
The restrictions imposed upon the Core Libraries also apply to these extensions, with additional restrictions. 

Extensions may also be added by using the Sentry Modding API via the Sentry Launcher. These extensions can be provided in any language that compiles to JVM Bytecode, and interacts directly with the implementation. Special privilleges are provided to such extensions. 

### Error Handling [general.error] ###

If an action taken by the Core Libraries or an extension would result in an error, the error results in the game crashing (and therefore ceasing execution). 

## Resource Naming [res] ##

### Exposed Reserved Entries [res.reserved.entries] ###

The following Entries are exposed by this implementation in `reserved` domains. 

```cpp
impl:moves/explosion/activate -> Move
impl:moves/future/activate -> Move
```

## Save File [save] ##

### Maximum Save files [save.generic.maxfiles] ###

This implementation does not explicitly impose any restrictions on the number of save files a player is allowed to create. 
However various additional limits outside the scope of this implementation, such as the maximum number of files a directory can contain, the maximum size of a single file, etc. may artificially impose a limit. 

### CryptoShade Extensions [save.shade.crypto] ###

This implementation does support the CryptoShade save file extensions. Both the IV and the Salt are regenerated each time files are saved. 
The password for saving new CryptoShade files are user-provided. 

## Lua Source Handling [lua] ##

### Lua Version [lua.version] ###

Lua Source files in the Core and in extension libraries are parsed using the Lua 5.2 Specification, modified as stated in [lua.modifications]. 

### Exposed Lua Libraries [lua.lib.exposed] ###

Only the libraries guaranteed to be exposed by [lua.lib.exposed] are provided by the implementation. 

## PkmCom [pkmcom] ##

### PkmCom LAN Features [pkmcom.lan.features] ###

All features of pkmcom lan are supported. 


## Script Bindings [bind] ##

### Game [bind.client.game] ###

#### Game:genProlougePokemon [bind.client.game.genProlougePokemon] ####

Calling Game:genProlougePokemon outside of the prolouge sequence is supported. 



