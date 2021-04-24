package com.thinking.machines.webRock.pojo;
public class ServiceResponse
{
private Object response;
private boolean isSuccessful;
private String exception;
public void setResponse(Object response)
{
this.response=response;
}
public Object getResponse()
{
return this.response;
}
public void setIsSuccessful(boolean isSuccessful)
{
this.isSuccessful=isSuccessful;
}
public boolean getIsSuccessful()
{
return this.isSuccessful;
}
public void setException(String exception)
{
this.exception=exception;
}
public String getException()
{
return this.exception;
}
}