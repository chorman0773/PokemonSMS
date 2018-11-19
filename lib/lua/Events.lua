
---
--This file and the enclosing library is licensed under the GNU Lesser General Public License.
--The full Copyright notice can be found in LICENSE.md


---
--Contains data for the event system
--@module Events
local Events = {};


---
--The Event Table for Battle events.
--All battle events are passed a battle instance, representing the current battle.
--@field[parent=#Events]
Events.Battle = {};

---
--Event Table for Ability events.
--The this instance passed is an instance of the current pokemon
Events.Battle.Ability = {};

---
--Event table for Ability Lifetime events.
--These are fired when certain events occur to cause an ability to start existing or stop existing.
Events.Battle.Ability.Lifetime = {};

---
--Event listing for whenever an ability starts to exist, IE. the ability is given to a pokemon,
--A pokemon with the ability enters,
Events.Battle.Ability.Lifetime.Start = 1

---
--Event listing for whenever an ability stops existing, IE. the last pokemon with the ability lost its ability,
--or it was replaced
Events.Battle.Ability.Lifetime.Stop = 2

---
--Event listing for when the ability is refreshed.
--Some abilities that share event code with others may use this to handle when one ability ceases to exist,
--but another identical one does.
--This is only fired manually, by Events.Battle:refershAbilities()
--This event is deprecated. It has very few uses, outside of cases where Ability#lifetimeSharedAbilities could be used instead.
Events.Battle.Ability.Lifetime.Refresh = 3


---
--Event table for Ability Local Lifetime events.
--Similar to Lifetime events, but is fired when a specific pokemon has the ability Start or Stop existing
Events.Battle.Ability.LocalLifetime = {};

---
--Event listing for whenever an ability starts existing in a local context.
Events.Battle.Ability.LocalLifetime.Start = 4

---
--Event Listing for whenever an ability stops existing in a local context.
Events.Battle.Ability.LocalLifetime.Stop = 5

---
--Event Listing for whenever an ability is to be gained by another pokemon, such as via skill swap
Events.Battle.Ability.Transfer = 6

---
--Event Listing for whenever an ability is to be replaced on this pokemon, such as via Worry Seed
Events.Battle.Ability.Replace = 7

---
--Event Table for Abilities that occur during Combat (during the use and execution of damage-dealing moves)
--All Combat events are passed the attacker, the target, the battle, the move in use, and the combat state
Events.Battle.Combat = {};
---
--Event Listing for when a move is used
Events.Battle.Combat.MoveUsed = 8

---
--Event Listing for when the combat state checks accuracy.
--Relevent Methods: state:applyAccuracyModifier(#number mod) state:moveFailed(#string cause,[params ...])
Events.Battle.Combat.CheckAccuracy = 9

---
--Event Listing for when Base damage is calculated (including Attack and Defense, but not including type, specials, etc.)
--Relevent Methods: state:applyStatModifier(#string stat,#number mod) state:moveFailed(#string cause,[params ...])
Events.Battle.Combat.CalculateDamage = 10

---
--Event Listing for when Special Modifiers are calculated into damage formula. The Specials as of the current version (1.0.0) only include critical hits and STAB
--Relevent Methods: state:overrideSpecialState(#string special,#boolean state), state:forceSpecialState(#string special,#boolean state), state:recalcuateSpecials(),
--state:moveFailed(#string cause,[params ...]), state:modifySpecial(#string special,#number modifier)
Events.Battle.Combat.CalculateSpecials = 11

---
--Event Listing for when Type Effectiveness is Calculated
--Relevent Methods: state:setIgnoresImmunity(#boolean ignoresImmunity), state:setType(#Type type), state:recalculateTypeEffectiveness(),
--state:moveFailed(#string cause,[params ...])
Events.Battle.Combat.CalculateTypeEffectiveness = 12


---
--Event Listing for when a Move hits.
--This actually occurs after damage is calculated
--No battle state methods
Events.Battle.Combat.MoveHits = 13


---
--Event Listing for when a move misses (for any reason)
--This event is slightly missnamed, it is fired whenever the move FAILS, rather than just misses (missing, having no effect, etc. all cause the move to fail)
Events.Battle.Combat.MoveMisses = 14

---
--Event listing for when a move faints a pokemon
--The pokemon's previous hp is passed in the state object.
--This event can prevent the pokemon from fainting, by setting the pokemon's hp to anything > 0.
Events.Battle.Combat.MoveFaintsPokemon = 15

---
--Event table for events fired to moves specifically.
Events.Battle.Moves = {}

---
--Event listing for when a move is executed. This is fired after the co-responding CombatEvent (MoveUsed)
Events.Battle.Moves.MoveExecuted = 16

---
--Event listing for when a status move is executed. This is fired after its co-responding StatusCombat Event (StatusMoveUsed)
Events.Battle.Moves.StatusExecuted = 17

---
--Event listing for when a move's Secondary Effects are applied
Events.Battle.Moves.ApplySecondaries =  18

---
--Event table for Events dealing with Status Moves.
Events.Battle.StatusCombat = {}

---
--Event listing for when a Status move is used
Events.Battle.StatusCombat.StatusMoveUsed = 19

---
--Event listing for when a Status move checks accuracy
Events.Battle.StatusCombat.CheckStatusAccuracy = 20

---
--Event listing for when a Status move applies its effect
Events.Battle.StatusCombat.StatusMoveEffect = 21

---
--Event listing for when a Status move fails for any reason
Events.Battle.StatusCombat.StatusMoveFailed = 22

---
--Event table for Events concerning pokemon
Events.Battle.Pokemon  = {}



---
--All this code is independent of any Constants, and can be used reguardless of intent
--


---
--@field[parent=#Events] #function nores a predicate representing no restrictions
Events.nores = function()return true;end;

---
--@type EventBus a part of the event system used by pokemon SMS
--

---
--@function[parent=#EventBus] register
--Registers a given event with a given handler
--@param #number event the Event Key used to map the handler
--@param #function filter the predicate which to check if the event occurs
--@param #function handler the event handler to assign this to
--@return #EventBus The current event Bus, for chaining


---
--@function[parent=#EventBus] registerServer
--Registers a given event with the given handler, if the current side is the server. (Has no effect on the client side)
--@param #number event the Event Key used to map the handler
--@param #function filter the predicate which to check if the event occurs
--@param #function handler the event handler to assign this to
--@return #EventBus The current event Bus, for chaining

---
--@function[parent=#EventBus] registerClient
--Registers a given event to a given handler, if the current side is the client (Has no effect on the server side)
--This is implied by Any event key outside of the Battle namespace.
--@param #number event the Event Key used to map the handler
--@param #function filter the predicate which to check if the event occurs
--@param #function handler the event handler to assign this to
--@return #EventBus The current event Bus, for chaining


---
--@function[parent=#EventBus] registerChained
--Registers a given event handler as a chained event Bus for this.
--@param #EventBus chained the base of the event bus
--@return #EventBus this for chaining


---
--@function[parent=#EventBus] fireEvent
--Fires an event based on its key, and the given arguments
--@param #number key
--@param ... the arguments
--@return #EventBus this for chaining

local Siding = require"Siding";

---
--Constructs a new Event Bus.
--@function[parent=#Events] newEventBus
--@return #EventBus a new instance of an event Bus.
function Events.newEventBus()
local ret = {};
local chained = {};
local handlers = {};
local function mkHandler(handler,predicate)
return function(...)
if predicate(...) then handler(...) end
end
end
local function registerNoEffect(self,event,filter,handler)
return self;
end
function ret:register(event, filter,handler)
if not handlers[event] then
handlers[event] = {};
end
local t = handlers[event];
t[#t + 1] = mkHandler(handler,filter);
return self;
end
if Siding:isSideServer() then
ret.registerServer = ret.register
ret.registerClient = registerNoEffect
else
ret.registerClient = ret.register
ret.registerServer = registerNoEffect
end
function ret:registerChained(bus)
chained[#chained + 1] = bus;
end
function ret:fireEvent(key,...)
for _,v in ipairs(chained) do
v:fireEvent(key,...);
end
for _,f in ipairs(handlers[key]) do
f(...);
end
return self;
end
return ret;
end

return Events;



