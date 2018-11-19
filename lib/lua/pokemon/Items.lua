
---
--This file and the enclosing library is licensed under the GNU Lesser General Public License.
--The full Copyright notice can be found in LICENSE.md


---
--@type Events
--@extends Events#Events
--@field[parent=#Events] #function nores
--The predicate function representing no restrictions
--@return #boolean true unconditionally.

---
--@field Events#Events Events
local Events = require"Events";

---
--@field Constants#Constants Constants
local Constants = require"Constants";

---
--@field Game#Game Game
local Game = require"Game";

---
--@field Game#Utils Utils
local Utils = Game:getUtils();

---
--@function[parent=#Events] newEventBus()
--Constructs a new Event Bus and returns it.
--@return #EventBus A new, isolated EventBus which has no events registered.


---
--@type Pokemon a Pokemon

---
--@function[parent=#Pokemon] getSpecies
--Gets the species of this pokemon.
--@return Pokemon#Species

---
--@function[parent=#Pokemon] getItems
--Gets the items the pokemon has. Returns 2 item instances. First is the main item, second is the equipment.
--@return #ItemInstance the held item
--@return #ItemInstance the equipment

---
--@function[parent=#Pokemon] getMove
--Returns the move given by a specific move slot.
--@param #number index the index
--@return Move#Move the move in that index (indexed from 0 (top-left)) or nil if that move slot is not used.

---
--@function[parent=#Pokemon] getAbility
--Gets the pokemon's current Ability
--@return Ability#Ability the ability of the pokemon

---
--@function[parent=#Pokemon] getTransformed
--Returns a Pokemon Object that contains all data about the pokemon currently transformed into or nil if the pokemon is not transformed.
--@return #Pokemon the transformed target.

---
--@function[parent=#Pokemon] getOriginal
--Returns a Pokemon Object that contains all data about the original pokemon.
--@Return #Pokemon the original pokemon.

---
--@function[parent=#Pokemon] getForm
--Returns the pokemons current form
--@return #number the form metadata.

---
--@function[parent=#Pokemon] heal
--Heals this pokemon by a specified percentage of its MaxHP.
--Equivalent to this:healAmmount(value*this:getMaxHP());
--@param #number value the percentage to heal
--@return #number the ammount healed. (integer)

---
--@function[parent=#Pokemon] healAmmount
--Heals this pokemon by up the given ammount of HP.
--@param #number value the ammount to heal
--@return #number the ammount healed. (integer)

---
--@function[parent=#Pokemon] cureStatus
--Cures the Status Conditions and Confusion of this pokemon
--@return #boolean true if the pokemon had either a status condition removed and/or had confusion cleared, false otherwise.

---
--@function[parent=#Pokemon] getMaxHP
--Returns the MaxHP of the Pokemon. Convience for getStat("hp")
--@return #number the maximum hp of this pokemon

---
--@function[parent=#Pokemon] getHPf
--Returns the HP of the pokemon as a percentage of its Maximum HP.
--@return #number the hp% of this pokemon.

---
--@function[parent=#Pokemon] getHPAmmount
--Returns the HP of the pokemon.

---
--@function[parent=#Pokemon] getStat
--Gets the value of the given stat.
--This DOES NOT Factor Stat Changes, Stat Modifiers, or HP Loss
--@param #string stat the Stat Id
--@return #number the value of that stat.

---
--@function[parent=#Pokemon] getModifiedStat
--Gets the value of the given stat, after factoring Stat Changes and Stat Modifiers
--@param #string stat the stat id
--@return #number the value of that stat


---
--@function[parent=#Pokemon] hasStatus
--Checks if the Pokemon has a non-volatile status condition or confusion.
--@return #boolean true IF and ONLY IF the pokemon is affected by a non-volatile status condition or confusion.


---
--@type ItemInstance

---
--@function[parent=#ItemInstance] getItem
--Gets the regestry item of this item.
--@return #Item the orginal item.

---
--@function[parent=#ItemInstance] getMetadata
--Returns the metadata number of this instance.
--@return #number the metadata

---
--@function[parent=#ItemInstance] getExtended
--Returns the extended data (if any) the item is using. Otherwise returns nil.
--@return #table the extended data table or nil.

---
--@type EventBus a list of Events attached to something, like an ability
--@extends Events#EventBus

---
--@function[parent=#EventBus] register(event,restrictions,handler)
--Registers a given handler to this event bus, to invoke on a given event, when a restriction is true.
--@param event, the target event to register
--@param #function restrictions, a stateless, non-interfering predicate function, which is called to check if the handler should be called.
--@param #function handler, the handler to assign.
--@return #EventBus the source handler, for chaining


---
--@type SubItem an item

---
--@field[parent=#SubItem] TextComponent#TextComponent name the unlocalized name of the item.

---
--@field[parent=#SubItem] TextComponent#TextComponent description the unlocalized description of the item

---
--@field[parent=#SubItem] #boolean equipment defines whether or not the item is an equipment (uses item slot 2 when given to a pokemon)

---
--@field[parent=#SubItem] #EventBus eventBus

---
--@type Item an item which contains information about it.
--@extends #SubItem


---
--@function[parent=#Item] getName
--Gets the name of the item in a metadata dependant way. Preferable to defining subitems.
--@param #number subitem the metadata key for the sub-item.
--@return TextComponent#TextComponent the name of the given subitem.
--@return ... any arguments to translate the component.

---
--@function[parent=#Item] getDescription
--Gets the description of the item in a metadata depedant way. Preferable to defining subitems
--@param #number subitem the metadata key for the sub-item.
--@return #string the description of the given subitem.
--@return ... any arguments to translate the component.

---
--@function[parent=#Item] getGraphic
--Obtains the graphic associated with a the item in a metadata dependant way. Uses Graphics Notation (path, and object-rectangle)
--@param #number subitem the metadata key for the sub-item

---
--@function[parent=#Item] canPokemonUse
--Predicate to check if a given Pokemon can use the subitem.
--@param #Pokemon pkm
--@param #number subitem
--@return #boolean the output of the predicate

---
--@function[parent=#Item] canPokemonSteal
--Predicate to check if a pokemon can steal this subitem from another pokemon
--@param #Pokemon source
--@param #Pokemon target
--@param #number subitem
--@return #boolean the output of the predicate.


---
--@function[parent=#Item] getSubitem
--Returns the subitem for a given metadata.
--@param #number subitem
--@return #SubItem the subitem instance.

---
--@field[parent=#Item] #list<#string> traits a list of traits

---
--@field[parent=#Item] #string pocket the pocket to add this item too.


---
--@field #list<#Item> items the list of all items to registers
local items = {};

local potionAmmounts = {20,60,200};

---
--@function onUseHandler
--Handler for onUse
--@param #Pokemon pkm the pokemon being used on
--@param #ItemInstance item the item being used.
--@param handler the handler of the item
local function onUseHandler(pkm,item,handler)
  local md = item:getMetadata();
  local effect;
  if md == 4 then
  if pkm:heal(1.0) == 0 and not pkm:cureStatus() then
    effect = false;
  else
   effect = true;
  end
  elseif md == 3 then
    if pkm:heal(1.0) == 0 then
     effect = false;
    else
      effect = true;
    end
  else
    if pkm:healAmmount(potionAmmounts[md+1]) then
     effect = false;
    else
     effect = true;
    end
  end
  if effect then
    handler:reportItemSuccess({type="translateble",text="items.potion.healed"},pkm:GetName());
    handler:consume(item,1);
  end
end

---
--@field #Item potion all potions
local potion = {};
function potion:getName(subitem)
  if subitem == 0 then
    return {type="translateble",text="items.potion.normal.name"};
  elseif subitem == 1 then
    return {type="translateble",text="items.potion.super.name"};
  elseif subitem == 2 then
    return {type="translateble",text="items.potion.hyper.name"};
  elseif subitem == 3 then
    return {type="translateble",text="items.potion.max.name"};
  elseif subitem == 4 then
    return {type="translateble",text="items.potion.restore.name"};
  end
end

function potion:getDescription(subitem)
  if subitem == 0 then
    return {type="translateble",text="items.potion.normal.description"};
  elseif subitem == 1 then
    return {type="translateble",text="items.potion.super.description"};
  elseif subitem == 2 then
    return {type="translateble",text="items.potion.hyper.description"};
  elseif subitem == 3 then
    return {type="translateble",text="items.potion.max.description"};
  elseif subitem == 4 then
    return {type="translateble",text="items.potion.restore.description"};
  end
end
function potion:getGraphic(subitem)
  return {path="graphics.items.healing",border={x=subitem,y=0,length=256,width=256}};
end
potion.eventBus = Events.newEventBus():register(Events.Items.OnUse,Events.nores,onUseHandler);
items[1] = potion;


---
--@field #Item plate the item which contains all plates
local plate = {};
function plate:getName(subitem)
 return {type="translateble",text="items.plates.name"},Utils.getTypeName(subitem);
end
function plate:getDescription(subitem)
  return {type="translateble",text="items.plates.description"};
end
function plate:getGraphic(subitem)
  return {path="graphics.items.plate",pallate=Utils.getPlateColorByType(subitem)}
end
plate.traits = {"equipment"};
plate.eventBus = Events.newEventBus():register(Events.Battle.Combat.CalculateDamage,Constants.Combat.SourceSelf,function(this,source,target,battle,move,state)
 local subitem =  this:getEquipment():getSubItem();
 if state:getType() == Utils:getTypeByNumber(subitem) then
  state:addModifier("plate",1.2);
 end
end);


items[9] = plate;


---
--@field #Item keyItem all key items.
local keyItem = {};
---
--@field #list<#SubItem> keyitems key items sublist
local keyitems = {};
keyitems[0] = {name={type="translateble",text="items.keyitems."}}

items[10] = keyItem;


---
--@field #Item machine all move machines (TMs/HMs)
local machine = {};

local TM_COUNT = 127;--TODO
items.TM_COUNT = TM_COUNT;
local HM_COUNT = 9;
items.HM_COUNT = HM_COUNT;

function machine:getName(subitem)
  if subitem >TM_COUNT then
    if subitem-TM_COUNT >HM_COUNT then
      error"Can't preform this action";
     else
      return {type="translateble",text="items.machines.hidden"},subitem-TM_COUNT,Game:getMachineMove(subitem):getName();
     end
    else
      return {type="translateble",text="items.machines.generic"},subitem,Game:getItemName(Game:getMachineMove(subitem));
  end
end

function machine:getDescription(subitem)
  return Game:getMachineMove(subitem):getMoveInfoComponent();
end
function machine:getGraphic(subitem)
  return Game:getMachineGraphic(subitem);
end

function machine:canUseOnPokemon(subitem,pkm)
  return pkm:getSpecies():canLearnMachine(subitem);
end


machine.traits = {"keyitem","machineitem"};
machine.pocket = "machines";



























return items;