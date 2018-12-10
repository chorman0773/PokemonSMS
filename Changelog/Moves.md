#Moves
This document describes the difference between the effects of existing Pokemon Moves in Generation 7, and PokemonSMS:

##Copyright Notice and Disclaimer
Any person may view and distribute verbaitum Copies of this document, but no person may modify this document without permission. 

This document is provided by the PokemonSMS Public Specification Project (see associated notices in the Specification Folder). 

##Move List

###Anchor Shot

Effect: "The Target becomes unable to flee" -> "The Target becomes unable to flee unless it has the Ghost Type when the move resolves"

###Aqua Ring

Effect: "Restores 1/16 of the user's health per turn" -> +"If it is raining or primordial sea is in effect, Restores 1/8 or 1/4 respectively (1/6 and 1/3 with Big Root)"

###Block

Effect: * -> +"Fails unconditionally if the target is a Ghost Type when the move resolves"

###Fling

Type: Dark -> Item Dependant

Effect:

Fails if the item cannot be lost, or is an "Equipment" type item.

If the user has the Poison Touch ability, and the target would not be affected by another non-volatile status condition as a result of this move, then the target is poisoned as a secondary effect. 


Default:
Power: 50
Type: Dark
Effect: None



Iron Ball: 
Power: 120
Type: Steel
Effect: If the target is Airborne During Type Checking, then the move has an additional *2 type effectiveness and the target will be grounded when the move resolves. 








