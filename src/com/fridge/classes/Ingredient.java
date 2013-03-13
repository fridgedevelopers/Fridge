
package com.fridge.classes;

public class Ingredient
{
    private long id;

    private String name;
    
    private boolean selected;

    public Ingredient(long id,
            String name)
    {
        this.id = id;
        this.name = name;
    }

    public Ingredient(Ingredient ingredient)
    {
        this.id = ingredient.getId();
        this.name = ingredient.getName();
    }

    public long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean isSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
