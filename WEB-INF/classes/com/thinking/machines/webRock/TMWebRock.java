package com.thinking.machines.webRock;
import javax.servlet.http.*;
import javax.servlet.*;
import java.util.*;
import java.io.*;
import java.lang.reflect.*;
import com.google.gson.*;
import com.thinking.machines.webRock.pojo.*;
import com.thinking.machines.webRock.model.*;
import com.thinking.machines.webRock.annotations.*;
import com.thinking.machines.webRock.exceptions.*;
public class TMWebRock extends HttpServlet
{
private boolean forwardedFromPost=false;

// implementing all logics on doGet for now, there are no difference between doGet and doPost except checking for METHOD TYPE of request
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
System.out.println("doGet of TMWebRock got called");
System.out.println("doGet of TMWebRock got called");
System.out.println("doGet of TMWebRock got called");
System.out.println("doGet of TMWebRock got called");
System.out.println("doGet of TMWebRock got called");
System.out.println("doGet of TMWebRock got called");
System.out.println("doGet of TMWebRock got called");
System.out.println("doGet of TMWebRock got called");
System.out.println("doGet of TMWebRock got called");
System.out.println("doGet of TMWebRock got called"+request.getRequestURI());
PrintWriter pw=null;
Gson gson=null;
try{
pw=response.getWriter();
System.out.println("1");
ServletContext servletContext=getServletContext();
WebRockModel webRockModel;
String key,methodType;
int index;
Service service;
Class serviceClass;
Method serviceMethod;
System.out.println("2");
response.setContentType("text/html");
webRockModel=(WebRockModel)servletContext.getAttribute("webRockModel");
key=request.getRequestURI().substring(request.getContextPath().length(),request.getRequestURI().length());
System.out.println("3");
index=key.indexOf("/",1);
key=key.substring(index,key.length());
HashMap<String,Service> map=webRockModel.getMap();
service=map.get(key);
serviceClass=service.getServiceClass();
serviceMethod=service.getService();
gson=new Gson();

// checking method type of request (GET/POST)
System.out.println("4");
if(this.forwardedFromPost==false)
{
if(!service.getIsGetAllowed() && service.getIsPostAllowed())
{
response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
return;
}
}
else
{
this.forwardedFromPost=false;
if(service.getIsGetAllowed() && !service.getIsPostAllowed())
{
response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
return;
}
}
System.out.println("5");

Object serviceClassObject=serviceClass.newInstance();

ApplicationScope applicationScope=null;
SessionScope sessionScope=null;
RequestScope requestScope=null;
ApplicationDirectory applicationDirectory=null;

// checking for Secured Access
if(service.getSecuredAccess())
{
Class checkPostClass=Class.forName(service.getCheckPost());
Method guardMethod=null;
String guard=service.getGuard();
Object guardParameters[]=null;
int numberOfParametersInGuard;
Class parameterTypesInGuard[];
String parameterTypeString;
for(Method method : checkPostClass.getMethods())
{
if(method.getName().equals(guard))
{
guardMethod=method;
numberOfParametersInGuard=method.getParameterCount();
guardParameters=new Object[numberOfParametersInGuard];
parameterTypesInGuard=method.getParameterTypes();
for(int i=0;i<numberOfParametersInGuard;i++)
{
parameterTypeString=parameterTypesInGuard[i].getSimpleName();
if(parameterTypeString.equals("ApplicationScope"))
{
if(applicationScope==null) applicationScope=new ApplicationScope(servletContext);
guardParameters[i]=applicationScope;
}
else if(parameterTypeString.equals("SessionScope"))
{
if(sessionScope==null) sessionScope=new SessionScope(request.getSession());
guardParameters[i]=sessionScope;
}
else if(parameterTypeString.equals("RequestScope"))
{
if(requestScope==null) requestScope=new RequestScope(request);
guardParameters[i]=requestScope;
}
else if(parameterTypeString.equals("ApplicationDirectory"))
{
if(applicationDirectory==null) applicationDirectory=new ApplicationDirectory(new File(servletContext.getRealPath("")));
guardParameters[i]=applicationDirectory;
}
else throw new Exception("Parameters in guard method are malformed");
} // for
break;
}  //if
} //for
if(guardMethod!=null)
{
System.out.println("Control reached here");
try{
guardMethod.invoke(checkPostClass.newInstance(),guardParameters);
}catch(InvocationTargetException invocationTargetException)
{
System.out.println("guard sent exception : "+invocationTargetException);
System.out.println("with cause : "+invocationTargetException.getCause());
response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
return;
}
System.out.println("Control reached here2");
}
} // if



// checking for RequestParameters
String primitiveTypeString;
String keyString;
Object requestParameters[];
System.out.println("5.1");
LinkedHashMap<String,Class> requestParameterMap=service.getRequestParameterMap();
requestParameters=null;
System.out.println("5.2");
if(requestParameterMap!=null)
{
System.out.println("5.3");
Set<String> keySet=requestParameterMap.keySet();
requestParameters=new Object[keySet.size()];
System.out.println("5.4");
Iterator<String> itr=keySet.iterator();
System.out.println("5.5");
int integerValue;
Object obj;
System.out.println("Size of set: "+keySet.size());
for(int i=0;itr.hasNext();i++)
{
keyString=itr.next();
if(keyString.equals("__json"))
{
System.out.println("JSON Found");
// declaring here because we know that this if condition will get executed only once
BufferedReader bufferedReader=request.getReader();
StringBuffer stringBuffer=new StringBuffer();
String b;
String rawString;
while(true)
{
b=bufferedReader.readLine();
if(b==null) break;
stringBuffer.append(b);
}
rawString=stringBuffer.toString();
System.out.println(rawString);
requestParameters[i]=gson.fromJson(rawString,requestParameterMap.get(keyString));
continue;
}
// checking if the parameter is ApplicationScope/SessionScope/RequestScope/ApplicationDirectory
if(keyString.equals("__applicationScope"))
{
if(applicationScope==null)  applicationScope=new ApplicationScope(servletContext);
requestParameters[i]=applicationScope;
continue;
}
if(keyString.equals("__sessionScope"))
{
if(sessionScope==null) sessionScope=new SessionScope(request.getSession());
requestParameters[i]=sessionScope;
continue;
}
if(keyString.equals("__requestScope"))
{
if(requestScope==null) requestScope=new RequestScope(request);
requestParameters[i]=requestScope;
continue;
}
if(keyString.equals("__applicationDirectory"))
{
if(applicationDirectory==null) applicationDirectory=new ApplicationDirectory(new File(servletContext.getRealPath("")));
requestParameters[i]=applicationDirectory;
continue;
}

// checking if the parameter is of primitive type
if(requestParameterMap.get(keyString).isPrimitive())
{
primitiveTypeString=requestParameterMap.get(keyString).toString();
System.out.println(primitiveTypeString);
System.out.println("5.7.1");
if(primitiveTypeString.equalsIgnoreCase("int") || primitiveTypeString.equalsIgnoreCase("Integer"))
{
System.out.println("5.7.2");
System.out.println(keyString);
System.out.println(request.getParameter(keyString));
requestParameters[i]=Integer.parseInt(request.getParameter(keyString));
}
else if(primitiveTypeString.equalsIgnoreCase("long"))
{
System.out.println("5.7.3");
System.out.println(request.getParameter(keyString));
requestParameters[i]=Long.parseLong(request.getParameter(keyString));
}
else if(primitiveTypeString.equalsIgnoreCase("short"))
{
requestParameters[i]=Short.parseShort(request.getParameter(keyString));
}
else if(primitiveTypeString.equalsIgnoreCase("double"))
{
requestParameters[i]=Double.parseDouble(request.getParameter(keyString));
}
else if(primitiveTypeString.equalsIgnoreCase("float"))
{
requestParameters[i]=Float.parseFloat(request.getParameter(keyString));
}
else if(primitiveTypeString.equalsIgnoreCase("boolean"))
{
requestParameters[i]=Boolean.parseBoolean(request.getParameter(keyString));
}
else if(primitiveTypeString.equalsIgnoreCase("byte"))
{
requestParameters[i]=Byte.parseByte(request.getParameter(keyString));
}
else if(primitiveTypeString.equalsIgnoreCase("char"))
{
requestParameters[i]=request.getParameter(keyString).charAt(0);
}
}
else
{
System.out.println("5.9");
requestParameters[i]=request.getParameter(keyString);
}
}
}

System.out.println("5.10");
Class classes[]=new Class[1];

// checking for IOC (INVERSE OF CONTROL)
classes[0]=Class.forName("com.thinking.machines.webRock.pojo.ApplicationScope");
if(service.getInjectApplicationScope() && serviceClass.getMethod("setApplicationScope",classes)!=null)
{
serviceClass.getMethod("setApplicationScope",classes).invoke(serviceClassObject,new ApplicationScope(servletContext));
}
classes[0]=Class.forName("com.thinking.machines.webRock.pojo.SessionScope");
if(service.getInjectSessionScope() && serviceClass.getMethod("setSessionScope",classes)!=null)
{
serviceClass.getMethod("setSessionScope",classes).invoke(serviceClassObject,new SessionScope(request.getSession()));
}
classes[0]=Class.forName("com.thinking.machines.webRock.pojo.RequestScope");
if(service.getInjectRequestScope() && serviceClass.getMethod("setRequestScope",classes)!=null)
{
serviceClass.getMethod("setRequestScope",classes).invoke(serviceClassObject,new RequestScope(request));
}
classes[0]=Class.forName("com.thinking.machines.webRock.pojo.ApplicationDirectory");
if(service.getInjectApplicationDirectory() && serviceClass.getMethod("setApplicationDirectory",classes)!=null)
{
serviceClass.getMethod("setApplicationDirectory",classes).invoke(serviceClassObject,new ApplicationDirectory(new File(servletContext.getRealPath(""))));
}
System.out.println("6");

// checking for IOC (INVERSE OF CONTROL) Next Level

List<AutoWiredProperty> autoWiredProperties;
autoWiredProperties=service.getAutoWiredProperties();
String autoWiredPropertyName;
Object autoWiredPropertyTypeObject;
Class autoWiredPropertyType;
Method autoWiredPropertySetter;
for(AutoWiredProperty autoWiredProperty: autoWiredProperties)
{
autoWiredPropertyName=autoWiredProperty.getName();
autoWiredPropertyType=autoWiredProperty.getType();
autoWiredPropertyTypeObject=request.getAttribute(autoWiredPropertyName);
System.out.println("6.1.2");
autoWiredPropertySetter=service.getServiceClass().getMethod("set"+autoWiredPropertyType.getSimpleName(),autoWiredPropertyType);
System.out.println("6.2");
if(autoWiredPropertyType!=null && autoWiredPropertyType.isInstance(autoWiredPropertyTypeObject))
{
System.out.println("6.3");
autoWiredPropertySetter.invoke(serviceClassObject,autoWiredPropertyTypeObject);
}
else
{
autoWiredPropertyTypeObject=request.getSession().getAttribute(autoWiredPropertyName);
if(autoWiredPropertyType!=null && autoWiredPropertyType.isInstance(autoWiredPropertyTypeObject))
{
autoWiredPropertySetter.invoke(serviceClassObject,autoWiredPropertyTypeObject);
}
else
{
autoWiredPropertyTypeObject=servletContext.getAttribute(autoWiredPropertyName);
if(autoWiredPropertyType!=null && autoWiredPropertyType.isInstance(autoWiredPropertyTypeObject))
{
autoWiredPropertySetter.invoke(serviceClassObject,autoWiredPropertyTypeObject);
}
else
{
autoWiredPropertySetter.invoke(serviceClassObject);
}
}
}
}

System.out.println("7");

// checking for InsertRequestParameters

List<InjectRequestParameterPOJO> injectRequestParameters=service.getInjectRequestParameters();
Method injectRequestParameterSetter;
Class requestParameterType;
System.out.println("7.1");
System.out.println(injectRequestParameters==null);
System.out.println(injectRequestParameters.size());
for(InjectRequestParameterPOJO requestParameter: injectRequestParameters)
{
System.out.println("7.2");
injectRequestParameterSetter=service.getServiceClass().getMethod("set"+(char)(requestParameter.getName().charAt(0)-32)+requestParameter.getName().substring(1),requestParameter.getType());
requestParameterType=requestParameter.getType();
keyString=requestParameter.getKey();
if(requestParameterType.isPrimitive())
{
primitiveTypeString=requestParameterType.toString();
System.out.println(primitiveTypeString);
System.out.println("5.7.1.1");
if(primitiveTypeString.equalsIgnoreCase("int") || primitiveTypeString.equalsIgnoreCase("Integer"))
{
System.out.println("5.7.2");
System.out.println(request.getParameter(keyString));
injectRequestParameterSetter.invoke(serviceClassObject,Integer.parseInt(request.getParameter(keyString)));
}
else if(primitiveTypeString.equalsIgnoreCase("long"))
{
System.out.println("5.7.3.3");
System.out.println(request.getParameter(keyString));
injectRequestParameterSetter.invoke(serviceClassObject,Long.parseLong(request.getParameter(keyString)));
}
else if(primitiveTypeString.equalsIgnoreCase("short"))
{
injectRequestParameterSetter.invoke(serviceClassObject,Short.parseShort(request.getParameter(keyString)));
}
else if(primitiveTypeString.equalsIgnoreCase("double"))
{
injectRequestParameterSetter.invoke(serviceClassObject,Double.parseDouble(request.getParameter(keyString)));
}
else if(primitiveTypeString.equalsIgnoreCase("float"))
{
injectRequestParameterSetter.invoke(serviceClassObject,Float.parseFloat(request.getParameter(keyString)));
}
else if(primitiveTypeString.equalsIgnoreCase("boolean"))
{
injectRequestParameterSetter.invoke(serviceClassObject,Boolean.parseBoolean(request.getParameter(keyString)));
}
else if(primitiveTypeString.equalsIgnoreCase("byte"))
{
injectRequestParameterSetter.invoke(serviceClassObject,Byte.parseByte(request.getParameter(keyString)));
}
else if(primitiveTypeString.equalsIgnoreCase("char"))
{
injectRequestParameterSetter.invoke(serviceClassObject,request.getParameter(keyString).charAt(0));
}
}
else
{
System.out.println("5.9.9");
injectRequestParameterSetter.invoke(serviceClassObject,request.getParameter(keyString));
}
}



System.out.println("6");
System.out.println("6.1");

// checking for request forwarding
String forwardTo=service.getForwardTo();
if(forwardTo!=null)
{
System.out.println("6.2");
if(map.containsKey(forwardTo))
{
System.out.println("6.3");
if(requestParameters!=null) service.getService().invoke(serviceClassObject,requestParameters);
else service.getService().invoke(serviceClassObject);
System.out.println("6.4");
service=map.get(forwardTo);
String contextURI=request.getRequestURI();
int index1=contextURI.indexOf("/",1);
int index2=contextURI.indexOf("/",index1+1);
String str=contextURI.substring(index1,index2);

RequestDispatcher requestDispatcher=request.getRequestDispatcher(str+forwardTo);
requestDispatcher.forward(request,response);

}
else
{
RequestDispatcher requestDispatcher=request.getRequestDispatcher(forwardTo);
requestDispatcher.forward(request,response);
}
}
else
{
System.out.println("6.5");
ServiceResponse serviceResponse=new ServiceResponse();
serviceResponse.setIsSuccessful(true);
if(requestParameters!=null)
{
serviceResponse.setResponse(service.getService().invoke(serviceClassObject,requestParameters));
}
else
{
serviceResponse.setResponse(service.getService().invoke(serviceClassObject));
}
pw.println(gson.toJson(serviceResponse));
pw.flush();
}
System.out.println("7");
}catch(Exception e)
{
System.out.println(e+" "+request.getRequestURI());
System.out.println(e.getCause());
ServiceResponse serviceResponse=new ServiceResponse();
serviceResponse.setIsSuccessful(false);
serviceResponse.setException(e.getCause().toString());
pw.println(gson.toJson(serviceResponse));
pw.flush();
}
}
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
System.out.println("doPost of TMWebRock got called");
System.out.println("doPost of TMWebRock got called");
System.out.println("doPost of TMWebRock got called");
System.out.println("doPost of TMWebRock got called");
System.out.println("doPost of TMWebRock got called");
System.out.println("doPost of TMWebRock got called");
System.out.println("doPost of TMWebRock got called");
System.out.println("doPost of TMWebRock got called");
System.out.println("doPost of TMWebRock got called");
this.forwardedFromPost=true;
doGet(request,response);
/*
try{
PrintWriter pw=response.getWriter();
WebRockModel webRockModel;
String key,methodType;
int index;
Service service;
Class serviceClass;
Method serviceMethod;
response.setContentType("text/html");
webRockModel=(WebRockModel)getServletContext().getAttribute("webRockModel");
key=request.getRequestURI().substring(request.getContextPath().length(),request.getRequestURI().length());
index=key.indexOf("/",1);
key=key.substring(index,key.length());
HashMap<String,Service> map=webRockModel.getMap();
service=map.get(key);
serviceClass=service.getServiceClass();
serviceMethod=service.getService();
Class get=Class.forName("com.thinking.machines.webRock.annotations.GET");
if(!service.getIsPostAllowed() && service.getIsGetAllowed())
{
response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
return;
}
ServiceResponse serviceResponse=new ServiceResponse();
serviceResponse.setIsSuccessful(true);
serviceResponse.setResponse(service.getService().invoke(service.getServiceClass().newInstance()));
pw.println(gson.toJson(serviceResponse));
pw.flush();
}catch(Exception e)
{
System.out.println(e);
ServiceResponse serviceResponse=new ServiceResponse();
serviceResponse.setIsSuccessful(false);
serviceResponse.setException(e.getMessage());
pw.println(gson.toJson(serviceResponse));
pw.flush();
}
*/
}
}