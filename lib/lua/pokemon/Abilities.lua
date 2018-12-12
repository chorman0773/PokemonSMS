
---
--This file and the enclosing library is licensed under the GNU Lesser General Public License.
--The full Copyright notice can be found in LICENSE.md


---
--@field #list<#Ability> abilities the list of all abilities in the game.
local abilities = {};

---
--@type Events
--@extends Events#Events

---
--@field[parent=#Events] #function nores
--The predicate function representing no restrictions

---
--@type Constants

---
--@type Stats

local Collect = require"Collect";
---
--@field #Events Events
--@extends Events#Events
local Events = require"Events";
---
--@field #Constants Constants
local Constants = require"Constants";



local Targeting = Constants.Battle.Targeting;
local Type = Constants.Types;
---
--@field #Stats Stats
local Stats = Constants.Stats;
---
--
local Status = Constants.Status;
local Action = Constants.Battle.Action;

---
--This file describes all abilities and the format of abilities.
--Each ability is a table which contains 2 mandatory elements and 8 optional elements



---
--@function[parent=#Events] newEventBus()
--Constructs a new Event Bus and returns it.
--@return #EventBus A new, isolated EventBus which has no events registered.




---
--@type EventBus a list of Events attached to something, like an ability
--@extends Events#EventBus

---
--@function[parent=#EventBus] register(event,restrictions,handler)
--Registers a given handler to this event bus, to invoke on a given event, when a restriction is true.
--@param event, the target event to register
--@param #function restrictions, a stateless, non-interfering predicate function, which is called to check if the handler should be called.
--@param #function handler, the handler to assign.
--@return #EventHandler the source handler, for chaining

---
--@type Ability a single ability
--@field[parent=#Ability] #string name the abilities unlocalized ability. This field is required
--@field[parent=#Ability] #string description the unlocalized description string for the ability. This field is required.
--@field[parent=#Ability] #string loc the Registry Resource Location for this ability. MUST BE UNIQUE and fully qualified.
--@field[parent=#Ability] #EventBus eventBus the Events table for this ability. Contains all methods. This field is optional, but highly recommended. If not provided the Ability is assigned no event handlers.


---
--@field[parent=#Ability] #list<#Ability> lifetimeSharedAbilities
--This field describes events which share Lifetime cycles. If 2 abilities Mutually define each other in this table array

---
--@field #list<#Ability> abilities
local abilities = {};

---
--@field #Ability default
local default = {name="abilities.null.name",description="abilities.null.description",loc="pokemon:default"};
abilities[0] = default;


---
--@field #Ability aromaveil
local aromaveil = {name="abilities.aromaveil.name",description="abilities.aromaveil.description",loc="pokemon:aromaviel"};
aromaveil.eventBus = Events.newEventBus():register(Events.Battle.Status.Precoditions,Constants.Battle.Moves.TargetsSelf,function(this,user,target,battle,move,state)
	if move:hasTrait("anger") then
		state:setState(Constants.Battle.State.Failed,{"abilities.aromaveil.fail"});
	end
end);
abilities[1] = aromaveil;

---
--@field #EventBus weatherEvents
local weatherEvents = Events.newEventBus():register(Events.Battle.Ability.Lifetime.Start,Events.nores,function(this,battle)
	battle:displayAbility(this,this.ability);
	battle:displayMessage({text="states.weather.effectclear",type="translateble"});
	battle:lockWeather();
end):register(Events.Battle.Ability.Lifetime.End,Events.nores,function(this,battle)
	battle:unlockWeather();
end);

---
--@field #Ability airlock
local airlock = {name="abilities.airlock.name",description="abilities.airlock.description",loc="pokemon:airlock"};
airlock.eventBus = weatherEvents;
abilities[2] = airlock;

---
--@field #Ability cloudnine
local cloudnine = {name="abilities.cloudnine.name",description="abilities.cloudnine.description",loc="pokemon:cloudnine"};
cloudnine.eventBus = weatherEvents;
cloudnine.lifetimeSharedAbilities = {airlock}; --Notes that Cloud Nine Shares lifetime events with Airlock

airlock.lifetimeSharedAbilities = {cloudnine}; --Notes that Airlock Shares lifetime events with Cloude Nine

---
--@field #Ability stab
local stab = {name="abilities.stab.name",description="abilities.stab.description"};
stab.eventBus = Events.newEventBus():register(Events.Battle.Combat.Damage.Calculate,Constants.Battle.Move.UserSelf,function(this,user,target,battle,move,state)
	if state:isStab() then
		state:setStabModifier(2);
	end
end);
abilities[3] = stab;


---
--@field #Ability buffFlying
local buffFlying = {name="abilites.buffflying.name",description="abilities.buffflying.description"}
buffFlying.eventBus = Events.newEventBus():registerClient(Events.Battle.Actions.CheckMoveInfo,Events.nores,function(this,battle,move,info)
	if move.type == Constants.Types.NORMAL and move:isDamaging() then
		info:set("type",Constants.Types.FLYING);
		info:modify("power",1.3);
	end
end):register(Events.Battle.Combat.TypeEffectiveness,Constants.Battle.Move.UserSelf,function(this,user,target,battle,move,state)
	if state:getType() == Constants.Type.NORMAL then
		state:setType(Constants.Type.FLYING);
		state:addMiscModifier(1.3);
		state:recaculateStabAndTypeEffectiveness();
	end
end);
abilities[4] = buffFlying;

---
--@field #Ability explode
local explode = {name="abilities.explode.name", description="abilities.explode.description"}
explode.eventBus = Events.newEventBus():register(Events.Battle.Pokemon.Faint,Constants.Battle.Pokemon.SourceSelf,function(this,source,battle,cause)
	if not cause:isExplosion() then
		battle:displayAbility(source,explode);
		battle:displayMessage({text="abilities.explode.text",type="translateble"},source.getName());
		battle:causeExplosion(source,100);
	end
end);
abilities[5] = explode;

---
--@field #Ability movecheck
local movecheck = {name="abilities.movecheck.name",description="abilities.movecheck.description"}
movecheck.eventBus = Events.newEventBus():register(Events.Battle.Pokemon.EnterBattle,Constants.Battle.Pokemon.SourceSelf,function(this,source,battle)
	if not battle:listAllValid(bit32.bor(Targeting.ADJACENT,Targeting.OPPONENT),source):stream():filter(function(p)
		return not p:getMovesStream():filter(function(m)
			return m:hasTrait("ohko") or source:getTypeEffecitveness(m:getType()) > 1;
		end):isEmpty();
	end):isEmpty() then
		battle:displayAbility(source,movecheck);
		battle:displayMessage({text="abilities.movecheck.activate",type="translateble"},source:getName());
	end
end);
abilities[6] = movecheck;

---
--@field #Ability angerpoint
local angerpoint = {name="abilities.angerpoint.name",description="abilities.angerpoint.description"}
angerpoint.eventBus = Events.newEventBus():register(Events.Battle.Combat.DamageDealt,Constants.Battle.Moves.TargetsSelf,function(this,target,source,battle,move,state)
	if state:isCriticalHit() and not this:checkFainted() then
		batle:displayAbility(source,angerpoint);
		this:raiseStat(Stats.ATTACK,12,{text="abilities.angerpoint.maxstat",type="translateble"});
	end
end);
abilities[7] = angerpoint;

---
--@field #Ability slowbuff
local slowbuff = {name="abilities.slowbuff.name",description="abilities.slowbuff.description"}
slowbuff.eventBus = Events.newEventBus():register(Events.Battle.Combat.CalculateDamage,Constants.Battle.Moves.SourceSelf,function(this,target,source,battle,move,state)
	if battle:isLast(this) then
		state:addMisModifier(1.2);
	end
end);
abilities[8] = slowbuff;

---
--@field #Ability reverseAuras
local reverseAuras = {name="abilities.reverseauras.name",description="abilities.reverseauras.description"}
stopAura.eventBus = Events.newEventBus():register(Events.Battle.Ability.Lifetime.Start,Events.nores,function(this,battle)
	battle:reverseAuras();
end):register(Events.Battle.Ability.Lifetime.End,Events.nores,function(this,battle)
  battle:normalizeAuras();
end);
abilities[9] = reverseAuras;

---
--@field #Ability nightmarePassive
local nightmarePassive = {name="abilities.nightmarepassive.name",description="abilities.nightmarepassive.description"}
nightmarePassive.eventBus = Events.newEventBus():register(Events.Battle.Turn.End.Abilities,Events.nores,function(this,battle)
	local hasDisplayed = false;
	for _,p in battle:listAllValid(bit32.bor(Targeting.ADJACENT,Targeting.OPPONENT),source):stream():filter(function(p)
		return p:hasStatus(Status.Sleep);
	end):iterator() do
		if not hasDisplayed then
			battle:displayAbility(this,nightmarePassive);
			hasDisplayed = true;
		end
		battle:displayMessage({text="abilities.nightmarepassive.torment",type="translateble"},p.getName(),this.getName());
	end
end);
abilities[10] = nightmarePassive;

---
--@field #Ability lockground
local lockground = {name="abilities.lockground.name",description="abilities.lockground.description"};
lockground.eventBus = Events.newEventBus():register(Events.Battle.Action.SelectAction,(function(this,source,battle)
return this~=source and battle:isTargetableFrom(source,Targeting.ADJACENT);
end),function(this,source,battle,action)
	if(action.type==Action.SWITCH and source.isGround() and not Types.isLockable(source.type)) then
		battle:displayAbility(this,lockground);
		action:prohibit("battle.standard.noescape");
	end
end):register(Events.Battle.Abilities.Lifetime.Reveal,Events.nores,function(this,battle)
	if battle:isEvent() and battle:isLocal() then
		battle:handleSpecialEvent("Switch",Constants.SwitchLockTypes.ArenaTrap,this); --Used whenever a local event battle needs to communicate with the Local State Battle
		--Don't try to send a packet, it will have no effect.
	end
end);

---
--@field #Ability awaken
local awaken = {name="abilities.awaken.name",description="abilities.awaken.name"};
awaken.eventBus = Events.newEventBus():register(Events.Battle.Pokemon.HPChanged,Constants.Battle.Pokemon.SourceSelf,function(this,source,battle)
if this:getHPPercent() <= 0.5 and this:isSpecies("helios") then
this:applyStatModifiers("atk, def, special, specdef, spd",1.3,"awaken");
else
this:revokeStatModifiers("*","awaken");
end
end);

---
--@field #EventBus armorEvents
local armorEvents = Events.newEventBus():register(Events.Battle.Combat.CalculateSpecialFactors,Constants.Battle.Combat.TargetsSelf,function(this, source, battle, target, state)
	state:setSpecialChance("critical",0);
	state:forceSpecialFactor("critical",false);
end);

---
--@field #Ability armor1
local armor1 = {name="abilities.armor1.name",description="abilities.armor.description",eventBus=armorEvents};
---
--@field #Ability armor2
local armor2 = {name="abilities.armor2.name",description="abilities.armor.description",eventBus=armorEvents};

---
--@field #Ability bigpecks
local bigpecks = {name="abilities.bigpecks.name",description="abilities.bigpecks.description"};
bigpecks.eventBus = Events.newEventBus():register(Events.Battle.Pokemon.StatChange, Constants.Battle.Pokemon.SourceSelf, function(this,source,battle,change)
if change:isStat("def") and change:isNegative() and change:getSource() ~=this then
change:prevent();
battle:displayAbility(this,bigpecks);
battle:displayMessage({"abilities.bigpecks.block"},this:getName());
end
end);

---
--@field #Ability blaze
local blaze = {name="abilities.blaze.name",description="abilities.blaze.description"};
blaze.eventBus = Events.newEventBus():register(Events.Battle.Combat.CalculateDamage,Constants.Battle.Pokemon.SourceSelf, function(this,source,target,battle,move,state)
 if state:isType("fire") and this:isBelowAccurateInverse(3) then
 state:applyModifier(1.3);
 end
end);

---
--@field #Ability antibalistic
local antibalistic = {name="abilities.antibalistic.name",description="abilities.antibalistic.description"};
antibalistic.eventBus = Events.newEventBus():register(Events.Battle.Combat.MoveHits,Constants.Battle.Combat.TargetSelf,function(this,source,target,battle,move,state)
if move:hasTrait("balistic") and not state:isMoldBroken() then
 battle:displayAbility(this,antibalistic);
 state:reportFail({"battle.generic.noeffect"},this:getName());
end
end);

---
--@field #Ability cheekpouch
local cheekpouch = {name="abilities.cheekpouch.name",description="abilities.cheekpouch.description"};
cheekpouch.eventBus = Events.newEventBus():register(Events.Battle.Item.ConsumeItem,Constants.Battle.Pokemon.SourceSelf,function(this,source,item,battle)
if item:hasTrait("berry") then
  this:healf(0.125);
end
end);

---
--@field #Ability sunboost
local sunboost = {name="abilities.sunboost.name",description="abilities.sunboost.description"};
sunboost.eventBus = Events.newEventBus():register(Events.Battle.Abilities.Lifetime.Start,Events.nores,function(this,battle)
if battle:hasWeather() and battle:getWeather():getBase() == Constants.Battle.Weather.Sun then
 this:applyModifiers("spd",1.3,"sunboost");
end
end):register(Events.Battle.Abilities.Lifetime.End,Events.nores,function(this,battle)
 this:removeModifiers("spd","sunboost");
end):register(Events.Battle.Weather.WeatherChanged,Events.nores,function(this,battle)
 if battle:hasWeather() and battle:getWeather():getBase() ==  Constants.Battle.Weather.Sun then
  this:applyModifiers("spd",1.3,"sunboost");
 else
  this:removeModifiers("spd","sunboost");
 end
end);

---
--@field #Ability noreduction
local noreduction = {name="abilities.noreduction.name",description="abilities.noreduction.description"};
noreduction.eventBus = Events.newEventBus():register(Events.Battle.Pokemon.StatsChanged,Constants.Battle.Pokemon.SourceSelf,function(this,source,battle,change)
if change:isNegative() and change:getSource() ~= this then
change:prevent();
end
end);

---
--@field #Ability typechange
local typechange = {name="abilities.typechange.name",description="abilities.typechange.description"};
typechange.eventBus = Events.newEventBus():register(Events.Battle.Combat.CombatEnd,Constants.Battle.Pokemon.TargetSelf,function(this,source,target,battle,move,state)
this:setTypes(state:getType());
end);

---
--@field #Ability invertchanges
local invertchanges = {name="abilities.invertchanges.name",description="abilities.invertchanges.description"};
invertchanges.eventBus = Events.newEventBus():register(Events.Battle.Pokemon.StatsChanged,Constants.Battle.Pokemon.SourceSelf,function(this,source,battle,change)
change:reverseModifiers();
end);

---
--@field #Ability specincrease
local specincrease = {name="abilities.specincrease.name",description="abilities.specincrease.description"};
specincrease.eventBus = Events.newEventBus():register(Events.Battle.Pokemon.StatsChanged,Constants.Battle.Pokemon.SourceSelf,function(this,source,battle,change)
if change:isNegative() then
this:raiseStat("special",2);
end
end);

---
--@field #Ability bugaccuracy
local bugaccuracy = {name="abilities.bugaccuracy.name",description="abilities.bugaccuracy.description"};
bugaccuracy.eventBus = Events.newEventBus():register(Events.Battle.Combat.CheckAccuracy,Constants.Battle.Pokemon.SourceSelf,function(this,source,target,battle,move,state)
state:modifyAccuracy(1.3);
end);

---
--@field #Ability disablecontact
local disablecontact = {name="abilites.disablecontact.name",description="abilities.disablecontact.description"};
disablecontact.eventBus = Events.newEventBus():register(Events.Battle.Combat.MoveMakesContact,Constants.Battle.Pokemon.TargetSelf,function(this,source,target,battle,move,state)
if battle:absoluteChance(3) and source:getDisabledTime() < 0 then
source:disable(source:moveIndexOf(move));
end
end);

---
--@field #Ability infatutatecontact
local infatutatecontact = {name="abilities.infatuatecontact.name",description="abilities.infautatecontact.description",loc="pokemon:infatutatecontact"}
infatutatecontact.eventBus = Events.newEventBus():register(Events.Battle.Combat.MoveMakesContact,Constants.Battle.Pokemon.TargetsSelf,function(this,source,target,battle,move,state)
if battle:absoluteChance(3) and not source:isInfatutated() then
source:infatuateWith(this);
end
end);


---
--@field #Ability nilexplosions
local nilexplosions = {name="abilties.nilexplosions.name",description="abilities.nilexplosions.description",loc="pokemon:nilexplosions"};
nilexplosions.eventBus = Events.newEventBus():register(Events.Battle.SpecialCombat.Explosion,Events.nores,function(this,battle,explosion,state)
	local weather = battle:getWeather();
	if not (weather and weather:getType() == Constants.Battle.Weather.Sun and weather:isExtreme()) then
		battle:displayAbility(this,nilexplosions);
		battle:displayCantExplodeMsg(explosion);
		state:moveFailed();
	end
end);

---
--@field #Ability darkaura
local darkaura = {name="abilties.darkaura.name",description="abilties.darkaura.description",loc="pokemon:darkaura"};
  darkaura.eventBus = Events.newEventBus():register(Events.Battle.Combat.CalculateDamage,Constants.Battle.Pokemon.SourceSelf,function(this,source,target,battle,move,state)
  if state:getType() == Types.DARK then
    if battle:areAurasReversed() then
      state:modifyDamage(0.67);
    else
     state:modifyDamage(1.5);
    end
  end
end);

---
--@field #Ability highspeedwinds
local highspeedwinds = {name="abilities.highspeedwinds.name",description="abilities.highspeedwinds.description",loc="pokemon:highspeedwinds"};
highspeedwinds.eventBus = Events.newEventBus():register(Events.Battle.Abilities.LocalLifetime.Start,Events.nores,function(this,battle)
if not battle:getWeather():getType() == Constants.Battle.Weather.Winds then
battle:displayAbility(this,highspeedwinds);
battle:setExtremeWeather(Constants.Battle.Weather.Winds);
end
end);
---
--@field #Ability desolateland
local desolateland = {name="abilities.desolateland.name",description="abilities.desolateland.description",loc="pokemon:desolateland"};
desolateland.eventBus = Events.newEventBus():register(Events.Battle.Abilities.LocalLifetime.Start,Events.nores,function(this,battle)
local weather = battle:getWeather();
if not (weather:getType() == Constants.Battle.Weather.Sun and weather:isExtreme()) then
battle:displayAbility(this,desolateland);
battle:setExtremeWeather(Constants.Battle.Weather.Sun);
end
end);

---
--@field #Ability primordialsea
local primordialsea = {name="abilities.primordialsea.name",description="abilities.primordialsea.description",loc="pokemon:primordialsea"};
primordialsea.eventBus = Events.newEventBus():register(Events.Battle.LocalLifetime.Start,Events.nores,function(this,battle)
local weather = battle:getWeather();
if not (weather:getType() == Constants.Battle.Weather.Rain and weather:isExtreme()) then
battle:displayAbility(this,primordialsea);
battle:setExtremeWeather(Constants.Battle.Weather.Rain);
end
end);

---
--@field #Ability awaken
local awaken = {name={type="translateble",text="abilities.awaken.name"},description={type="translateble",text="abilities.awaken.description"},loc="pokemon:awaken"};
awaken.eventBus = Events.newEventBus():register(Events.Abilities.TransferAbility,Events.nores,function(this,source,cause,state)
 state:cancel();
end):register(Events.Abilities.ReplaceAbility,Constants.Combat.SourceSelf,function(this,source,cause,state)
  state:cancel();
end);

---
--@field #Ability sturdy
local sturdy = {name={type="translateble",text="abilities.sturdy.name"},description={type="translateble",text="abilities.sturdy.description"},loc="pokemon:sturdy"};
sturdy.eventBus = Events.newEventBus():register(Events.Battle.Combat.PokemonDamagedByMove,Constants.Pokemon.SourceSelf,function(this,source,attacker,battle,move,state)
    if state:getCalculatedDamage() >= source:getHPValue() and source.getHP() == 1 and source:getHPValue() ~= 1 then
            state:setDamage(source.getHP()-1);
	    battle:displayAbility(this,sturdy);
	    battle:displayText({type="translateble",text="generic.combat.endurehit"},this:getName());
    end
end);








return abilities;
