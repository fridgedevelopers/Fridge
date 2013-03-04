package com.fridge.classes;

public class Category
{
    private long id;
    private String name;
    private String description;
    
    public Category(long id, String name, String description)
    {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    
    public Category(Category recipeCategory)
    {
        id = recipeCategory.getId();
        name = recipeCategory.getName();
        description = recipeCategory.getDescription();
    }
    
    public long getId()
    {
        return id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setId(long id)
    {
        this.id = id;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    @Override
    public String toString()
    {
        return name;
    }
}
