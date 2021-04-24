package com.thinking.machines.webRock.pojo;
import java.lang.reflect.*;
import java.util.*;
import java.lang.Object.*;
public class Service
{
private Class serviceClass;
private String path;
private Method service;
private String forwardTo;
private boolean isGetAllowed;
private boolean isPostAllowed;
private boolean runOnStart;
private int priority;
private boolean injectApplicationScope;
private boolean injectSessionScope;
private boolean injectRequestScope;
private boolean injectApplicationDirectory;
private List<AutoWiredProperty> autoWiredProperties;
private LinkedHashMap<String,Class> requestParameterMap;
private List<InjectRequestParameterPOJO> injectRequestParameters;
private boolean securedAccess;
private String checkPost;
private String guard;
public Service()
{
serviceClass=null;
path="";
service=null;
forwardTo="";
isGetAllowed=false;
isPostAllowed=false;
runOnStart=false;
priority=-1;
injectApplicationScope=false;
injectSessionScope=false;
injectRequestScope=false;
injectApplicationDirectory=false;
requestParameterMap=null;
autoWiredProperties=new ArrayList<AutoWiredProperty>();
injectRequestParameters=new ArrayList<InjectRequestParameterPOJO>();
securedAccess=false;
checkPost="";
guard="";
}
public void setServiceClass(Class serviceClass)
{
this.serviceClass=serviceClass;
}
public Class getServiceClass()
{
return this.serviceClass;
}
public void setPath(String path)
{
this.path=path;
}
public String getPath()
{
return this.path;
}
public void setService(Method service)
{
this.service=service;
}
public Method getService()
{
return this.service;
}
public void setForwardTo(String forwardTo)
{
this.forwardTo=forwardTo;
}
public String getForwardTo()
{
return this.forwardTo;
}
public void setIsGetAllowed(boolean isGetAllowed)
{
this.isGetAllowed=isGetAllowed;
}
public boolean getIsGetAllowed()
{
return this.isGetAllowed;
}
public void setIsPostAllowed(boolean isPostAllowed)
{
this.isPostAllowed=isPostAllowed;
}
public boolean getIsPostAllowed()
{
return this.isPostAllowed;
}
public void setRunOnStart(boolean runOnStart)
{
this.runOnStart=runOnStart;
}
public boolean getRunOnStart()
{
return this.runOnStart;
}
public void setPriority(int priority)
{
this.priority=priority;
}
public int getPriority()
{
return this.priority;
}
public void setInjectApplicationScope(boolean injectApplicationScope)
{
this.injectApplicationScope=injectApplicationScope;
}
public boolean getInjectApplicationScope()
{
return this.injectApplicationScope;
}
public void setInjectSessionScope(boolean injectSessionScope)
{
this.injectSessionScope=injectSessionScope;
}
public boolean getInjectSessionScope()
{
return this.injectSessionScope;
}
public void setInjectRequestScope(boolean injectRequestScope)
{
this.injectRequestScope=injectRequestScope;
}
public boolean getInjectRequestScope()
{
return this.injectRequestScope;
}
public void setInjectApplicationDirectory(boolean injectApplicationDirectory)
{
this.injectApplicationDirectory=injectApplicationDirectory;
}
public boolean getInjectApplicationDirectory()
{
return this.injectApplicationDirectory;
}
public void setAutoWiredProperties(List<AutoWiredProperty> autoWiredProperties)
{
this.autoWiredProperties=autoWiredProperties;
}
public List<AutoWiredProperty> getAutoWiredProperties()
{
return this.autoWiredProperties;
}
public void setRequestParameterMap(LinkedHashMap<String,Class> requestParameterMap)
{
this.requestParameterMap=requestParameterMap;
}
public LinkedHashMap<String,Class> getRequestParameterMap()
{
return this.requestParameterMap;
}
public void setInjectRequestParameters(List<InjectRequestParameterPOJO> injectRequestParameters)
{
this.injectRequestParameters=injectRequestParameters;
}
public List<InjectRequestParameterPOJO> getInjectRequestParameters()
{
return this.injectRequestParameters;
}
public void setSecuredAccess(boolean securedAccess)
{
this.securedAccess=securedAccess;
}
public boolean getSecuredAccess()
{
return this.securedAccess;
}
public void setCheckPost(String checkPost)
{
this.checkPost=checkPost;
}
public String getCheckPost()
{
return this.checkPost;
}
public void setGuard(String guard)
{
this.guard=guard;
}
public String getGuard()
{
return this.guard;
}
}