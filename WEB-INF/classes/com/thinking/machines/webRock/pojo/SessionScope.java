package com.thinking.machines.webRock.pojo;
import javax.servlet.http.*;
public class SessionScope
{
private HttpSession httpSession;
public SessionScope(HttpSession httpSession)
{
this.httpSession=httpSession;
}
public void setAttribute(String key,Object value)
{
this.httpSession.setAttribute(key,value);
}
public Object getAttribute(String key)
{
return this.httpSession.getAttribute(key);
}
}