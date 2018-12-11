package github.chorman0773.pokemonsms.internal;

public interface Events {
	public static interface Battle{
		public static interface Abilities{
			public static interface Lifetime{
				public static int Start = 1,
						Stop = 2,
						Refresh = 3;
			}
			public static interface LocalLifetime{
				public static int Start = 4,
						Stop = 5;
			}
			public static int Transfer = 6,
					Replace = 7;
		}
		public static interface Combat{
			public static int MoveUsed = 8,
					CheckAccuracy = 9,
					CalculateDamage = 10,
					CalculateSpecials = 11,
					CheckTypeEffectiveness = 12,
					MoveHits = 13,
					MoveMisses = 14,
					MoveFaintsPokemon = 15;
		}
		public static interface Moves{
			public static int MoveExecuted = 16,
					StatusExecuted = 17,
					ApplySecondaries = 18;
		}
		public static interface StatusCombat{
			public static int StatusUsed = 19,
					CheckStatusAccuracy = 20,
					StatusMoveApplied = 21,
					StatusMoveFails = 22;
		}
		public static interface Pokemon{
			public static int Enters = 23,
					Leaves = 24,
					TakesDamage = 25,
					HPDrops = 26,
					HPGains = 27,
					AttemptSwitch = 28,
					Statused = 29,
					Recovered = 30,
					Faints = 31,
					Transforms = 32,
					Explodes = 33;
		}
		public static interface Item{
			public static int Activates = 33,
					Used = 34;
		}
	}
	public static interface Client{
		public static interface Sprite{
			public static int PassiveUpdate = 35,
					Drawn = 36,
					Undrawn = 37,
					Interaction = 38,
					Collide = 39,
					ActiveUpdate = 40;
		}
		public static interface Location{
			public static int Load = 41,
					BackgroundLoad = 42,
					Unload = 43;
		}
		public static interface Trainer{
			public static int Prebattle = 44,
					BattleStart = 45,
					Defeated = 46,
					Blackout = 47,
					Postbattle = 48;
		}
		public static interface Wild{
			public static int BattleStart = 49,
				RoamingStart = 50,
				StationaryStart = 51,
				RoamingFlee = 52,
				TrainerFlee = 53,
				PokemonEncountered = 54,
				PokemonCaught = 55,
				DoubleEncountered = 56,
				TripleEncountered = 57,
				BattleEnd = 58,
				RoamingEnd = 59,
				StationaryEnd = 60;
		}
	}
}
