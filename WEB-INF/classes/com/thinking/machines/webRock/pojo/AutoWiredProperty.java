package com.thinking.machines.webRock.pojo;
public class AutoWiredProperty
{
private String name;
private Class type;
public void setName(String name)
{
this.name=name;
}
public String getName()
{
return this.name;
}
public void setType(Class type)
{
this.type=type;
}
public Class getType()
{
return this.type;
}
}