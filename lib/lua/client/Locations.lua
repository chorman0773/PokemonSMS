
---
--This file and the enclosing library is licensed under the GNU Lesser General Public License.
--The full Copyright notice can be found in LICENSE.md



---
--@type Location a location
--@field[parent=#Location] #list<#list<Tiles#Tile>> tiles
--@field[parent=#Location] #Postion startingPos
--@field[parent=#Location] #list<Sprites#SpriteInstance> sprites
--@field[parent=#Location] Events#EventBus eventBus
--@field[parent=#Location] #string tilemap
--@field[parent=#Location] #string name
--@field[parent=#Location] #Size mapSize

---
--@field Events#Events Events
local Events = require"Events";

---
--@type Position
--@field[parent=#Postion] #number x
--@field[parent=#Position] #number y

---
--@type Size
--@field[parent=#Size] #number length
--@field[parent=#Size] #number width

---
--@field #list<#Location> locations
local locations = {}

locations[0] = {
  name="locations.buildings.pchouse.2",
  tilemap="tilemaps.buildings2",
  sprites={
      {
        pos={x=35,y=40},
        id=10,
        data={
          target=1,
          warp=0
        }
      }
    },
   tiles = {
    {
      {
        id=0
      }
    }
   },
   startingPos={x=0,y=0},
   size={length=35,width=35}
}

locations[1] = {
  name="locations.buildings.pchouse.1",
  tilemap="tilemaps.buildings2",
  sprites={
    {

    }
  },
  startingPos={x=0,y=60},
  size={length=35,width=35}
}


return locations;