package com.fridge.classes;

import java.util.ArrayList;
import recommender.Recommendable;
import recommender.util.OrderedPair;

public class Recipe implements Recommendable
{
    private long id;
    private Category recipeCategory;
    private String name;
    private String description;
    private int servingSize;
    private int duration;
    private int views;
    private double rating;
    private double overallRating;
    private ArrayList<RecipeIngredient> recipeIngredients;
    private ArrayList<Instruction> instructions;
    private ArrayList<Tag> tags;
    
    public Recipe(long id, Category recipeCategory, String name, String description, int servingSize, int duration)
    {
        this.id = id;
        this.recipeCategory = recipeCategory;
        this.name = name;
        this.description = description;
        this.servingSize = servingSize;
        this.duration = duration;
        views = 0;
        rating = 0;
        overallRating = 0;
        recipeIngredients = new ArrayList<RecipeIngredient>();
        instructions = new ArrayList<Instruction>();
        tags = new ArrayList<Tag>();
    }
    
    public Recipe(Recipe recipe)
    {
        id = recipe.getId();
        recipeCategory = recipe.getRecipeCategory();
        name = recipe.getName();
        description = recipe.getDescription();
        servingSize = recipe.getServingSize();
        duration = recipe.getDuration();
        views = recipe.getViews();
        rating = recipe.getRatings();
        overallRating = recipe.getOverallRating();
        recipeIngredients = recipe.getIngredients();
        instructions = recipe.getInstructions();
        tags = recipe.getTags();
    }
    
    public void addIngredient(RecipeIngredient recipeIngredient)
    {
        recipeIngredients.add(recipeIngredient);
    }
    
    public void addInstruction(Instruction instruction)
    {
        instructions.add(instruction);
    }
    
    public void addTag(Tag tag)
    {
        tags.add(tag);
    }

    public long getId()
    {
        return id;
    }

    public Category getRecipeCategory()
    {
        return recipeCategory;
    }

    public String getName()
    {
        return name;
    }

    public String getDescription()
    {
        return description;
    }

    public int getServingSize()
    {
        return servingSize;
    }

    public int getDuration()
    {
        return duration;
    }

    public int getViews()
    {
        return views;
    }

    public double getRatings()
    {
        return rating;
    }

    public double getOverallRating()
    {
        return overallRating;
    }

    public ArrayList<RecipeIngredient> getIngredients()
    {
        return recipeIngredients;
    }

    public ArrayList<Instruction> getInstructions()
    {
        return instructions;
    }

    public ArrayList<Tag> getTags()
    {
        return tags;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public void setRecipeCategory(Category recipeCategory)
    {
        this.recipeCategory = recipeCategory;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setServingSize(int servingSize)
    {
        this.servingSize = servingSize;
    }

    public void setDuration(int duration)
    {
        this.duration = duration;
    }

    public void setViews(int views)
    {
        this.views = views;
    }

    public void setRatings(double ratings)
    {
        this.rating = ratings;
    }

    public void setOverallRating(double overallRating)
    {
        this.overallRating = overallRating;
    }

    public void setIngredients(ArrayList<RecipeIngredient> ingredients)
    {
        this.recipeIngredients = ingredients;
    }

    public void setInstructions(ArrayList<Instruction> instructions)
    {
        this.instructions = instructions;
    }

    public void setTags(ArrayList<Tag> tags)
    {
        this.tags = tags;
    }
    
    public void incrementViews()
    {
        views += 1;
    }
    
    @Override
    public String toString()
    {
        return getName();
    }

    @Override
    public double getNumeric()
    {
        return overallRating;
    }

    @Override
    public OrderedPair getOrderedPair()
    {
        double x = views;
        double y = overallRating;
        return new OrderedPair(x, y);
    }

    @Override
    public int compareTo(Object o)
    {
        return name.compareTo(((Recipe)o).getName());
    }

    @Override
    public ArrayList<Tag> getTagSet()
    {
        return tags;
    }
}
