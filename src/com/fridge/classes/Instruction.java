package com.fridge.classes;

public class Instruction
{
    private long id;
    private String instruction;
    
    public Instruction(long id, String instruction)
    {
        this.id = id;
        this.instruction = instruction;
    }
    
    public Instruction(Instruction instruction)
    {
        id = instruction.getId();
        this.instruction = instruction.getInstruction();
    }
    
    public long getId()
    {
        return id;
    }
    
    public String getInstruction()
    {
        return instruction;
    }
    
    public void setId(long id)
    {
        this.id = id;
    }
    
    public void setInstruction(String instruction)
    {
        this.instruction = instruction;
    }
    
    @Override
    public String toString()
    {
        return instruction;
    }
}