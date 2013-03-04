package com.fridge.classes;

public class Tag
{
    private long id;
    private String tag;
    
    public Tag(long id, String tag)
    {
        this.id = id;
        this.tag = tag;
    }
    
    public Tag(Tag tag)
    {
        id = tag.getId();
        this.tag = tag.getTag();
    }
    
    public long getId()
    {
        return id;
    }
    
    public String getTag()
    {
        return tag;
    }
    
    public void setId(long id)
    {
        this.id = id;
    }
    
    public void setTag(String tag)
    {
        this.tag = tag;
    }
    
    @Override
    public String toString()
    {
        return tag;
    }
}
