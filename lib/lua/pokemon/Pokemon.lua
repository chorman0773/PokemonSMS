
---
--This file and the enclosing library is licensed under the GNU Lesser General Public License.
--The full Copyright notice can be found in LICENSE.md


---
--

---
--@type Events
--@extends Events#Events


---
--@function[parent=#Events] newEventBus
--Creates a new event bus
--@return Events#EventBus an event bus

---
--@field Events#Events Events
local Events = require"Events"

local Constants = require"Constants"

local Registries = require"Registries";

---
--@type FormData the data of a form.

---
--@type Stats the array of base stats
--@field[parent=#Stats] #number atk Base Attack
--@field[parent=#Stats] #number def Base Defense
--@field[parent=#Stats] #number special Base Special Attack
--@field[parent=#Stats] #number spdef Base Special Defense
--@field[parent=#Stats] #number speed Base Speed
--@field[parent=#Stats] #number hp Base HP

---
--@field[parent=#FormData] #Stats baseStats

---
--@field[parent=#FormData] #list<Constants#Type> types The base types of this pokemon.


---
--@type Species a species in pokemon
--@extends #FormData

---
--@function[parent=#Species] getForm
--Obtains the form that a pokemon should be in. Returns that form's id.
--@param Game#Pokemon pokemon the pokemon that is being used.
--@return #number the id (index into the

---
--@field[parent=#FormData] #string name the Unlocalized name of the pokemon

---
--@field[parent=#Species] #string loc the resource location of the pokemon

---
--@field[parent=#Species] #string desc the Description string used in the pokedex.

---
--@field[parent=#Species] #number id the pokemon's id. (pokedex number). nil or -1 means the pokemon is not part of the national pokedex.



---
--@field[parent=#Species] #number localId The id within the Komono Pokedex. nil or -1 means the pokemon does not appear in the Komono Pokedex

---
--@field[parent=#Species] #string image the image for the pokemon to use. this is only the <name> part of pokemon.<name>.sprite and pokemon.<name>.model.


---
--@field[parent=#Species] Events#EventBus eventBus The bus to fire events to. Very few pokemon register events.

---
--@field[parent=#Species] #list<#FormData> forms Optional field, to list extra forms

---
--@field[parent=#Species] #list<#string> traits Info flags about this pokemon

---
--@field[parent=#Species] #list<Moves#Move> startMoves List of all moves that the pokemon has initialy

---
--@field[parent=#Species] #list<Moves#Move> evolveMoves List of all moves this pokemon learns after it finishes evolving.

---
--@field[parent=#Species] #map<#number,#list<Moves#Move>> levelUpMoves list of all moves this pokemon learns by level up

---
--@field[parent=#Species] #list<#number> tms all Machine Moves this pokemon learns. (number is Machine item metadata, not moveid, hms are after tms in order)

---
--@function[parent=#Species] getType
--Obtains the type of a specific member of the species. Can be better than specifying type per form
--@param Battle#Pokemon pokemon the current pokemon
--@return Constants#Type the type of the pokemon

---
--@type EvolutionMethod A Method for Evolution.
--@field[parent=#EvolutionMethod] Constants#EvolutionMethodType type the type for this evolution method
--@field[parent=#EvolutionMethod] #number target which pokemon this evolves into (pokemon id)
--@field[parent=#EvolutionMethod] #number data The data used by this evolution. This is level for Constants.Evolution.LevelUpNormal, and other data
--@field[parent=#EvolutionMethod] extra some data used by the evolution method.

---
--@field[parent=#Species] #list<#EvolutionMethod> all evolution methods this pokemon uses.

---
--@field[parent=#Species] #Stats evs the EV Output of the Pokemon

---
--@field[parent=#Species] Constants#GrowthRate growth The rate of growth of the pokemon

---
--@function[parent=#Species] getImage
--This can be used instead of image field.
--Obtains the graphic for the pokemon to use. This returns only the "name" part of pokemon.<name>.graphic and pokemon.<name>.model
--This can also return a "graphics info" to specify scaling.
--@param #number form the current form to use.
--@return #string the sole part of the name.
--@return Game#GraphicsInfo


---
--@field #list<#Species> Pokemon the pokemon to register
local Pokemon = {};


local function mkForm(form,base)

end

---
--@field #Species bulbasaur
local bulbasaur = {
  name="pokemon.bulbasaur.name",
  desc="pokemon.bulbasaur.description",
  loc="pokemon:bulbasaur",
  id=1,
  Stats={atk=49,def=49,special=65,spdef=65,speed=45,hp=45}
};


---
--@field #Species arceus
local arceus = {
 name={type="translateble",text="pokemon.arceus.name"},
 description={type="translateble",text="pokemon.arceus.description"},
 loc="pokemon:arceus",
 Stats={atk=120,def=120,special=120,spdef=120,speed=120,hp=120},
 types={Constants.Types.NORMAL},
 traits={"pokemon:notshiny","pokemon:legendary"},
 id=493,
 abilities={"pokemon:multitype"},
 startMoves={"pokemon:judgement","pokemon:sharpen_attack"},
 levelUpMoves={
  [70]={"pokemon:time_roar","pokemon:damage_space","pokemon:shadow_force2"}
 }
};
function arceus:getImage(form)
  return "arceus."..form;
end

---
--@field #Species helios
local helios = {
  name={type="translateble",text="pokemon.helios.name"},
  description={type="translateble",text="pokemon.helios.description"},
  loc="pokemon:helios",
  Stats={atk=120,def=120,special=120,spdef=120,speed=120,hp=120},
  types={Constants.Types.SOLAR},
  traits={"pokemon:notshiny","pokemon:legendary"},
  id=803,
  abilities={"pokemon:awaken"},
  startMoves={"pokemon:sun_lance","pokemon:morning_sun"},
  forms= {}

};
helios.forms[1] = {Stats={atk=140,def=140,special=140,spdef=140,speed=140,hp=120}};

function helios:getForm(pkm)
  if pkm:getHp()<0.5 then return 1 else return 0 end
end








return Pokemon;
