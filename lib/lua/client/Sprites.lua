
---
--This file and the enclosing library is licensed under the GNU Lesser General Public License.
--The full Copyright notice can be found in LICENSE.md


local Events = require"Events";
local Constants = require"Constants";
local BitOps = require"BitOps";
local NpcControl = require"NpcControl";
local TrainerControl = require"TrainerControl";
local Scripts = require"Scripts";
local Game = require"Game";
local EncounterControl = require"EncounterControl";

---
--@type Sprite An in game sprite
--@field[owner=#Sprite] eventBus The bus that controls the sprite.

---
--@type GameSprite an instance of a sprite.

---
--@function[owner=#GameSprite] getBase
--Obtains the base of this sprite
--@return #Sprite the Base of this sprite

---
--@function[owner=#Sprite] getGraphic Obtains the sprite graphic for the sprite in a data dependant way.
--@param #GameSprite sprite
--@return #string a path to a graphics table


---
--@field #list<#Sprite> Sprites
local Sprites = {};

---
--@field #Sprite playerc The sprite representing the player character
local playerc = {};
playerc.loc = "system:playerc";
function playerc:getGraphic(sprite)
  local trainer = Game:getTrainer();
  return trainer:getTrainerGraphicPath();
end
Sprites[0] = playerc;



local npc = {};
npc.loc = "pokemon:npc"
function npc:getGraphic(sprite)
   return NpcControl:getNpcGraphic(sprite)
end
npc.eventBus = Events.newEventBus():register(Events.Client.Sprite.InteractWith,Events.nores,function(this)
  NpcControl:fireToNpc(Events.Client.Sprite.InteractWith,this);
end):register(Events.Client.Sprite.PassiveUpdate,Events.nores,function(this)
  NpcControl:fireToNpc(Events.Client.Sprite.PassiveUpdate,this)
end):register(Events.Client.Sprite.ActiveUpdate,Events.nores,function(this)
  NpcControl:fireToNpc(Events.Client.Sprite.ActiveUpdate,this)
end);
Sprites[1] = npc;

local trainer = {};
trainer.loc = "pokemon:trainer"
function trainer:getGraphic(sprite)
  return TrainerControl:getTrainerGraphic(sprite)
end

trainer.eventBus = Events.newEventBus():register(Events.Client.Sprite.InteractWith,Events.nores,function(this)
  TrainerControl:fireInteract(this);
end):register(Events.Client.Sprite.Update,Events.nores,function(this)
  TrainerControl:fireUpdate(this);
end);
Sprites[2] = trainer;


local staticPokemon = {};
staticPokemon.loc = "pokemon:stationary_pokemon"
function staticPokemon:getGraphic(sprite)
  return Game:getOverworldSprite(Game:getPokemon(sprite:getData()))
end
staticPokemon.eventBus = Events.newEventBus():register(Events.Client.Sprite.InteractWith,Events.nores,function(this)
 if this:getExtended().kind == 2 then
  EncounterControl:dispatchRoamingEncounter(this);
 else
  EncounterControl:dispatchStaticEncounter(this);
 end
end);
Sprites[3] = staticPokemon


local warp = {};
warp.loc = "pokemon:warp";
warp.disableCollision = true;
warp.disableInteraction = true;
warp.disableRendering = true;
warp.eventBus = Events.newEventBus():register(Events.Client.Sprite.PlayerAbove,Events.nores,function(this)
  Game:playEffect(Game:getEffect("effects.warp.fade"));
  local map = Game:getMaps():getMap(this:getTarget());
  local warp = this:getExtra().warp;
  local type = this:getExtra().type;
  if type == Constants.Client.WarpTypes.Returning then
    Game:getMaps():setExitMap(Game:getMaps():getCurrent());
  elseif type == Constants.Client.WarpTypes.Exit then
    map = Game:getMaps():getExitMap();
  end
  Game:getMaps():unloadAll();
  Game:getMaps():loadPrimary(map);
  Game:getPlayer():setPosition(map:getWarp(warp));
  Game:playSound("sounds.warp.mapwarp");
  Game:refreshLocation();
end);
Sprites[4] = warp;

local groundItem = {};
groundItem.warp = "pokemon:groundItem"
function groundItem:getGraphic(sprite)
  if sprite:getExtra().type == Constants.Items.Key then
    return "sprites.item.golden"
  else
    return "sprites.item.normal"
   end
end
groundItem.eventBus = Events.newEventBus():register(Events.Client.Sprite.InteractWith,Events.nores,function(this)
  local item = Game:getItem(this:getTarget());
  local metadata = this:getExtended().meta;
  local extra = this:getExtended().extra;
  local stack = Game:newItemInstance(item,metadata,extra);
  local type = this:getExtended().type;
  if this:getExtended().regenerates then
   local timer = this:getExtended().regenerates;
   Game:setItemRegenerationTimer(this,Constants.Sprites.Items.RegenTimes[timer]);
   this:setFlag(Constants.Sprites.Flags.SpriteRemoved);
  else
    this:setFlag(Constants.Sprites.Flags.SpirteDestroyed);
  end
  if type == Constants.Items.Key then
    Game:playSound("sounds.item.obtain.key");
  else
    Game:playSound("sounds.item.obtain")
   end
   Game:obtainItem(stack);
end);
Sprites[5] = groundItem;


local arceus = {};
arceus.loc = "pokemon:arceus/overworld";
arceus.flags = Constants.SpriteFlags.NoRendering;
arceus.eventBus = Events.newEventBus():register(Events.Client.Sprite.Interact,Events.nores,function(this)
  Game:doArceusBattleSeq();
end);
Sprites[6] = arceus;

local loadMap = {};
loadMap.disableCollision = true;
loadMap.disableInteraction = true;
loadMap.disableRendering = true;
loadMap.eventBus = Events.newEventBus():register(Events.Client.Sprite.PassiveUpdate,Events.nores,function(this)
  if Game:getPlayer():getPositionX() == this:getPositionX() and Game:getPlayer():getPositionY() == this:getPositionY() then
    local map = Game:getMap(this:getTarget());
    Game:getMaps():unloadSecondary(map);
  end
end);
Sprites[7] = loadMap;

local unloadMap = {};
unloadMap.flags = BitOps.xor(Constants.SpriteFlags.NoCollision,Constants.SpriteFlags.NoInteraction,Constants.SpriteFlags.No)

unloadMap.eventBus = Events.newEventBus():register(Events.Client.Sprite.PassiveUpdate,Events.nores,function(this)
  if Game:getPlayer():getPositionX() == this:getPositionX() and Game:getPlayer():getPositionY() == this:getPositionY() then
    local map = Game:getMap(this:getTarget());
    Game:getMaps():unloadSecondary(map);
  end
end);
Sprites[8] = unloadMap;

local tree = {};
function tree:getGraphic(sprite)
   return "sprites.obstecles.shrub"
end
tree.eventBus = Events.newEventBus():register(Events.Client.Sprite.PlayerInteract,Events.nores,function(this)
  if not Game:storyProgress():eventTriggered(Constants.Client.Story.Badge.Celestial) or not Game:canPartyUseFieldMove("pokemon:hms/cut") then
    Game:displayTextBox({type="translatable",text="game.generic.shrub.pathblocked"})
   else
    Game:displayTextBox({type="translatable",text="game.generic.shrub.cuttable"});
    if Game:displayOptionBox({type="translatable",text="game.field.checkuse.cut"}) then
      Game:usePartyFieldMove("pokemon:hms/cut");
      this:setFlags(Constants.Sprites.Flags.SpriteRemoved);
    end
   end
end):register(Events.Client.Sprite.Load,Events.nores,function(this)
  this:clearFlags(Constants.Sprites.Flags.SpriteRemoved);
end);
Sprites[9] = tree;

local smallRock = {};
function smallRock:getGraphic(sprite)
  return "sprites.obstecles.rock"
end
smallRock.eventBus = Events.newEventBus():register(Events.Client.Sprite.PlayerInteract,Events.nores,function(this)
  if not Game:storyProgress():eventTriggered(Constants.Client.Story.Badge.Trensen) or not Game:canPartyUseFieldMove("pokemon:hm/breakrocks") then
   Game:displayTextBox({type="translatable",text="game.generic.rock.pathblocked"});
  else
    Game:displayTextBox({type="translatable",text="game.generic.rock.brekable"});
    if Game:displayOptionBox({type="translatable",text="game.field.checkuse.rocksmash"}) then
      Game:usePartyFieldMove("pokemon:hms/rocksmash")
      this:setFlags(Constants.Sprites.Flags.SpriteRemoved);
     end
  end
end):register(Events.Clients.Sprite.Load,Events.nores,function(this)
  this:clearFlags(Constants.Sprites.Flags.SpriteRemoved);
end);
Sprites[10] = smallRock;

local boulder = {};
function boulder:getGraphic(sprite)
  return "sprites.obstecles.boulder"
end
boulder.eventBus = Events.newEventBus():register(Events.Client.Sprite.PlayerInteract,Events.nores,function(this)
  if not Game:storyProgress():eventTriggered(Constants.Client.Story.Badge.Solem) or not Game:canPartyUseFieldMove("pokemon:hm/highpower") then
    Game:displayTextBox({type="translateable",text="game.generic.boulder.pathblocked"});
  elseif Game:isMovingRocks() then
    Game:displayTextBox({type="translateable",text="game.generic.bounder.moving"});
  else
    Game:displayTextBox({type="translateable",text="game.generic.bounder.moveable"});
    if Game:displayOptionBox({type="translateable",text="game.field.checkuse.strength"}) then
      Game:usePartyFieldMove("pokemon:hms/boulder");
      Game:setMovingRocks(true);
     end
  end
end):register(Events.Client.Sprite.PlayerCollides,Events.nores,function(this,direction)
  if Game:isMovingRocks() then
    this:moveSprite(direction:moveAway());
    Game:updateCollision();
    if Game:isOverlappingSprite(this) and Game:getOverlappingSprite(this):getBase()==Sprites[12] and Game:getOverlappingSprite(this):getData() == 0 then
      this:setFlags(Constants.Sprites.Flags.SpriteDestroyed);
      Game:getOverlappingSprite(this):setFlags(Constants.Sprites.Flags.IgnoreCollision)
      Game:getOverlappingSprite(this):setData(1);
    end
   end
end);
Sprites[11] = boulder

local boulderSink = {};
function boulderSink:getGraphic(sprite)
  if sprite:getData() == 1 then
    return "sprites.obstecles.sink.filled"
   else
    return "sprites.obstecles.sink.empty"
   end
 end
Sprites[12] = boulderSink;


return Sprites;
