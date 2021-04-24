package com.thinking.machines.webRock.pojo;
import javax.servlet.http.*;
public class RequestScope
{
private HttpServletRequest httpServletRequest;
public RequestScope(HttpServletRequest httpServletRequest)
{
this.httpServletRequest=httpServletRequest;
}
public void setAttribute(String key,Object value)
{
this.httpServletRequest.setAttribute(key,value);
}
public Object getAttribute(String key)
{
return this.httpServletRequest.getAttribute(key);
}
}