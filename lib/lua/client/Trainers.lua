
---
--This file and the enclosing library is licensed under the GNU Lesser General Public License.
--The full Copyright notice can be found in LICENSE.md


---
--@type Trainer a trainer (instance of sprite 01)
--@field[parent=#Trainer] #string class the trainer class
--@field[parent=#Trainer] #number value the Money output of the trainer
--@field[parent=#Trainer] #string name the unlocalized name of the trainer
--@field[parent=#Trainer] #list<Items#ItemInstance> itemPool the items the trainer has available for use.
--@field[parent=#Trainer] #number rotationFrequency The minimum number of ticks between each time it turns. (0 for a trainer that doesn't move)
--@field[parent=#Trainer] AI#PathFinding path The path the trainer takes (nil if it doesn't move)
--@field[parent=#Trainer] AI#Brain combatBrain the Brain used in combat.
--@field[parent=#Trainer] #list<Game#TrainerRewards> rewardPool The items given by the trainer (no money rewards)
--@field[parent=#Trainer] #number sight the maximum number of tiles the trainer can see.
--@field[parent=#Trainer] #list<#String> dialog The dialog the trainer says when encountered
--@field[parent=#Trainer] #list<#String> victoryDialog the dialog the trainer says when defeated
--@field[parent=#Trainer] #list<Game#Pokemon> team theTeam
--@field[parent=#Trainer] #function onVictory the function to call on victory. May be nil.
--@field[parent=#Trainer] #number battleType
--@field[parent=#Trainer] Sprites#SpriteInstance pairedTrainer The Trainer to pair with in double battles. Only works if battleType is Constants.Overworld.BattleTypes.Doubles or Constants.Overworld.BattleTypes.Team


local AI = require"AI"
local Game = require"Game"
local Story = require"Story"

---
--@field #list<#Trainer> trainers
local trainers = {};


trainers[0] = {
  class="rival",
  value=0,
  name="trainers.rival.name",
  combatBrain={
    tasks=AI.StandardCombatTaskList,
    intelegence=0.15
  },
  team={
    {
      species=Game:getRivalStarter(0),
      level=5
    }
  },
  value=1500,
  dialog={"messages.rival.rival1"},
  victoryDialog={"messages.rival.victory1"}
};


trainers[1] = {
  class="rival2",
  name="trainers.rival2.name",
  combatBrain={
    tasks=AI.StandardCombatTaskList,
    intelegence=0.15
  },
  team={
    {
      species=Game:getRivalStarter(1),
      level=5
    }
  },
  value=1500,
  dialog={"messages.rival.rival2"},
  victoryDialog={"messages.rival.victory2"}
};


trainers[2] = {
  class="hiker",
  name="trainers.hiker.bob",
  combatBrain={
    tasks=AI.StandardCombatTaskList,
    intelegence=0.1
  },
  team={
    {
    species=Game:getPokemon("pokemon:geodude"),
    level=7
    }
  },
  value=1000,
  dialog={"messages.hiker.bobchallenge"},
  victoryDialog={"messages.hiker.bobvictory"}
};

trainers[3] = {
  class="leader1",
  name="trainers.gyms.leader1",
  itemPool={
    {
      item=Game:getItem("items:potion"),
      data=0,
      count=1
    }
  },
  combatBrain={
    tasks=AI.PotionCombatTaskList,
    intelegence=0.2
  },
  team={
    species=Game:getPokemon("pokemon:selion"),
    level=10,
    ivs=Game:randomIvsFunction({2,31});
  }
}






return trainers;

