
---
--This file and the enclosing library is licensed under the GNU Lesser General Public License.
--The full Copyright notice can be found in LICENSE.md


local Events = require"Events"

local Constants = require"Constants"

local Registries = require"Registries"

local Math = require"Math";

---
--@type Move a move
--@field Events#EventBus eventBus
--@field #number power (optional), present for
--@field Constants#Type type
--@field Constants#MoveCategory category
--@field #string name
--@field #string desc
--@field #string loc
--@field #list<#string> traits
--@field #number accuracy
--@field #number targeting


---
--@function[parent=#Move] canPokemonUse
--Predicate function to check if a pokemon can use a given move. If nil it is assumed that the predicate is always true
--@param Battle#Pokemon the pokemon using the move
--@return #boolean true if the pokemon can use the move.

---
--@function[parent=#Move] getType
--Obtains the type of the move in a dynamic way. This is used when moves change there type that they display and register.
--Do not use this for moves that change their effective types, handle the Battle.Combat.
--@param Battle#Pokemon pokemon the pokemon with the move
--@return Constants#Type the type

---
--@function[parent=#Move] getPower
--Obtains the power of the move in a dynamic way.
--@param Battle#Pokemon pokemon the pokemon with the move
--@return Constants#Type the type


---
--@function[parent=#Move] getAnimation
--Gets the animation used in a pokemon dependent way.
--@param Battle#Pokemon pokemon the pokemon with the move
--@return #string animation the resource path of the animation

---
--@function[parent=#Move] getName
--Gets the name of the move in a pokemon dependent way.
--@param Battle#Pokemon pokemon the pokemon
--@return #string the unlocalized name of the move


---
--@function[parent=#Move] getDescription
--Gets the description of the move in a pokemon dependent way.
--@param Battle#Pokemon pokemon the pokemon
--@return #string the unlocalized description of the move

---
--@field[parent=#Move] #number machineId The machine code for Item 11.

---
--@field #list<#Move> moves
local moves = {}

---
--@field #Move base the null move
local base = {
  name="moves.null.name",
  desc="moves.null.description",
  loc="pokemon:null",
  category=Constants.Moves.Categories.Status,
  type=Constants.Types.Typeless
}
moves[0] = base;
---
--@field #Move selfdestruct
local selfdestruct = {
  name={type="translateble",text="moves.smallexplode.name"},
  desc={type="translateble",text="moves.smallexplode.description"},
  loc="pokemon:smallexplosion",
  animation="moves.explosion.small",
  category=Constants.Moves.Categories.Status,
  type=Constants.Types.Typeless,
  traits={"explosion"},
  targeting=Constants.Combat.Targeting.TARGETS_SELF
}
selfdestruct.eventBus = Events.newEventBus():register(Events.Battle.Move.MoveActivates,Events.nores,
function(this,target,battle,state)
local affected = battle:select(this,bit32.bor(Constants.Combat.Targeting.ALL,Constants.Combat.Targeting.ADJACENT));
battle:dealExplosionDamage(200,this,affected);
this:faint();
end):registerClient(Events.Client.GUI.MoveInfo,Scripts.AND(Constants.Pokemon.SourceSelf,function(this,source,move)
return move == selfdestruct
end),function(this,source,move,info,guielement)
info:setCategory(Constants.Moves.Categories.Physical);
info:setPower(200);
info:setTargetingMode(bit32.bor(Constants.Combat.Targeting.ALL,Constants.Combat.Targeting.ADJACENT));
end):registerClient(Events.Client.Battle.MoveGraphic,Events.nores,function(this,battle,graphics)
graphics:playRandomExplosionEffect(35,0.15,0.30,0.55);
end);
moves[1] = selfdestruct;
---
--@field #Move explosion
local explosion = {
  name={type="translateble",text="moves.largeexplode.name"},
  desc={type="translateble",text="moves.largeexplode.description"},
  loc="pokemon:largeexplosion",
  animation="moves.explosion.large",
  category=Constants.Moves.Categories.Status,
  type=Constants.Types.TypelessTypeless,
  traits={"explosion"},
  targeting=Constants.Combat.Targeting.TARGETS_SELF
}
explosion.eventBus = Events.newEventBus():register(Events.Battle.Move.MoveActivates,Events.nores,function(this,target,battle,move,state)
local affected = battle:select(this,bit32.bor(Constants.Combat.Targeting.ALL,Constants.Combat.Targeting.ADJACENT));
battle:dealExplosionDamage(250,this,affected);
this:faint();
end):registerClient(Events.Client.GUI.MoveInfo,Scripts.AND(Constants.Pokemon.SourceSelf,function(this,source,move)
return move == explosion;
end),function(this,source,move,info)
info:setCategory(Constants.Moves.Categories.Physical);
info:setPower(250);
info:setTargetingMode(bit32.bor(Constants.Combat.Targeting.ALL,Constants.Combat.Targeting.ADJACENT));
end):registerClient(Events.Client.Battle.MoveGraphic,Events.nores,function(this,battle,graphics)
graphics:playRandomExplosionEffect(40,0.20,0.35,0.45);
end);
moves[2] = explosion

---
--@field #Move curse
local curse  = {
  name="moves.curse.name",
  desc="moves.curse.description",
  loc="pokemon:curse",
  category=Constants.Moves.Categories.Status
};

curse.getType = function(pokemon)
  if pokemon:isType(Types.Ghost) then
   return Types.Ghost
  else
   return Types.Typeless
  end
end
curse.getTargeting = function(pokemon)
 if pokemon:isType(Types.Ghost) then
  return bit32.bitor(Constants.Combat.Targeting.TARGETS_ANY,Constants.Combat.Targeting.TARGETS_ADJACENT);
 else
  return Constants.Combat.Targeting.TARGET_SELF;
 end
end

curse.eventBus = Events.newEventBus():register(Events.Battle.Move.MoveActivates,Events.nores,function(this,target,battle,move,state)
 if this:isType(Types.Ghost) then
    this:reduceHP(0.5,Math.Rounding.ALL_UP);
    target:setCursed(true);
 else
  this:lowerStat("spd",1);
  this:raiseStat("atk",1);
  this:raiseStat("def",1);
 end
end);
moves[3] = curse

return moves
