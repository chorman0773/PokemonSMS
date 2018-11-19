---
--This file and the enclosing library is licensed under the GNU Lesser General Public License.
--The full Copyright notice can be found in LICENSE.md

local StrictMath = require"bridge_StrictMath"

---
--@module Math The module for Math. This also bridges with StrictMath
local Math = setmetatable({},{__index=function(t,k)
return StrictMath[k]
end});



Math.Rounding = {};


function Math.Rounding.HALF_UP(val)
  return math.ciel(val-0.5);
end

function Math.Rounding.ALL_UP(val)
  return math.ceil(val);
end

function Math.Rounding.ALL_DOWN(val)
  return math.floor(val);
end



return Math
