
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

local npc = {};
function npc.getGraphic(sprite)
  return NpcControl:getGraphic(sprite);
end
npc.loc = "pokemon:npc";
npc.eventBus = NpcControl:getNpcEventBus();
Sprites[1] = npc;

local trainer = {};
function trainer.getGraphic(sprite)
  return TrainerControl:getGraphic(sprite);
end
trainer.loc = "pokemon:trainer";
trainer.eventBus = TrainerControl:getTrainerEventBus();
Sprites[2] = trainer;

local pokemon = {};
function pokemon.getGraphic(sprite)
  return Game:getOverworldGraphicFor(sprite:getData());
end
pokemon.loc = "pokemon:overworld"
pokemon.eventBus = EncounterControl:getStantionaryEventBus();

return Sprites;
