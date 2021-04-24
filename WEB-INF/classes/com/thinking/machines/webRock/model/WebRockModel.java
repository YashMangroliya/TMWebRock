package com.thinking.machines.webRock.model;
import com.thinking.machines.webRock.pojo.*;
import java.util.*;
public class WebRockModel
{
private HashMap<String,Service> pathClassMap;
public WebRockModel()
{
pathClassMap=new HashMap<>();
}
public void addToMap(String key,Service value)
{
this.pathClassMap.put(key,value);
}
public HashMap<String,Service> getMap()
{
return this.pathClassMap;
}
}