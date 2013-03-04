package com.fridge.classes;

public class RecipeIngredient extends Ingredient
{
    private double quantity;
    private String unit;
    private String note;
    
    public RecipeIngredient(long id, String name, double quantity, String unit, String note)
    {
        super(id, name);
        this.quantity = quantity;
        this.unit = unit;
        this.note = note;
    }
    
    public RecipeIngredient(RecipeIngredient recipeIngredient)
    {
        super(recipeIngredient.getId(), recipeIngredient.getName());
        quantity = recipeIngredient.getQuantity();
        unit = recipeIngredient.getUnit();
        note = recipeIngredient.getNote();
    }
    
    public double getQuantity()
    {
        return quantity;
    }
    
    public String getUnit()
    {
        return unit;
    }
    
    public String getNote()
    {
        return note;
    }
    
    public void setQuantity(double quantity)
    {
        this.quantity = quantity;
    }
    
    public void setUnit(String unit)
    {
        this.unit = unit;
    }
    
    public void setNote(String note)
    {
        this.note = note;
    }
    
    @Override
    public String toString()
    {
        return getName();
    }
}
