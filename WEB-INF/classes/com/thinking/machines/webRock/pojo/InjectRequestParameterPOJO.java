package com.thinking.machines.webRock.pojo;
public class InjectRequestParameterPOJO
{
private String name;
private Class type;
private String key;
public InjectRequestParameterPOJO(String name,Class type,String key)
{
this.name=name;
this.type=type;
this.key=key;
}
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
public void setKey(String key)
{
this.key=key;
}
public String getKey()
{
return this.key;
}
}