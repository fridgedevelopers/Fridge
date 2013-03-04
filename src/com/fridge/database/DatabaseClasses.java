package com.fridge.database;

public class DatabaseClasses {
	
	public class Pantry_Categories{
		String name;
		String description;
		
		public Pantry_Categories(String name, String description) {
			this.name = name;
			this.description = description;
		}

	}
	
	public class Recipe_Categories{
		String name;
		String description;
		
		public Recipe_Categories(String name, String description) {
			this.name = name;
			this.description = description;
		}

	}
	
	public class Recipe_Ingredients{
		int r_id;
		int pi_id;
		String qty;
		String notes;
		
		public Recipe_Ingredients(int r_id, int pi_id,String qty, String notes) {
			this.r_id = r_id;
			this.pi_id = pi_id;
			this.qty = qty;
			this.notes = notes;
		}

	}
	
	public class Pantry_Ingredients{
		int un_id;
		int pc_id;
		String name;
		
		public Pantry_Ingredients(int un_id, int pc_id,String name) {
			this.un_id = un_id;
			this.pc_id = pc_id;
			this.name = name;
		}
	}
	
	public class Settings_BP{
		String name;
		String value;
		String desc;
		
		public Settings_BP(String name, String value, String desc) {
			this.name = name;
			this.value = value;
			this.desc = desc;
		}
	}
	
	public class Recipes_BP{
		int rc_id;
		String name;
		String desc;
		int serving_size;
		int duration;
		int views;
		int ratings;
		float overall_ratings;
		
		public Recipes_BP(int rc_id, String name, String desc, int serving_size, int duration, int views, int ratings, float overall_ratings) {
			this.rc_id = rc_id;
			this.name = name;
			this.desc = desc;
			this.serving_size = serving_size;
			this.duration = duration;
			this.views = views;
			this.ratings = ratings;
			this.overall_ratings = overall_ratings;
		}
	}
}
