package com.thinking.machines.webRock;
import com.thinking.machines.webRock.annotations.*;
import com.thinking.machines.webRock.pojo.*;
import com.thinking.machines.webRock.model.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.nio.*;
import java.lang.reflect.*;
import java.lang.annotation.*;
public class TMWebRockStartup extends HttpServlet
{
private String pathToClasses;
private WebRockModel webRockModel;
private List<Service> startupServices;
public void init()
{
this.webRockModel=new WebRockModel();
this.startupServices=new ArrayList<Service>();
ServletConfig servletConfig=getServletConfig();
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
String servicePackagePrefix=servletConfig.getInitParameter("SERVICE_PACKAGE_PRIFIX");
String path=getServletContext().getRealPath("/WEB-INF/classes/")+servicePackagePrefix;
this.pathToClasses=getServletContext().getRealPath("/WEB-INF/classes/");
try{
File file=new File(path);
populateModel(file);
File jsFile=new File(getServletContext().getRealPath("/WEB-INF/js/"+getInitParameter("JsFile")));
File tmpFile=new File(getServletContext().getRealPath("/WEB-INF/js/tmp.tmp"));
RandomAccessFile randomAccessFile=new RandomAccessFile(jsFile,"rw");
RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");

tmpRandomAccessFile.seek(0);
long lengthOfFile=tmpRandomAccessFile.length()+randomAccessFile.length();
randomAccessFile.seek(randomAccessFile.length());
while(randomAccessFile.length()<lengthOfFile) randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\r\n");
randomAccessFile.close();
tmpRandomAccessFile.setLength(0);
tmpRandomAccessFile.close();
getServletContext().setAttribute("webRockModel",this.webRockModel);
System.out.println("StartupServices size: "+this.startupServices.size());
for(Service s: this.startupServices)
{
System.out.println("Priority: "+s.getPriority());
s.getService().invoke(s.getServiceClass().newInstance());
}
}catch(Exception e)
{
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println(e);
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
}
}

public void populateModel(File file)
{
try{
File files[]=file.listFiles();
Class pathAnnotation=Class.forName("com.thinking.machines.webRock.annotations.Path");
Class GETAnnotation=Class.forName("com.thinking.machines.webRock.annotations.GET");
Class POSTAnnotation=Class.forName("com.thinking.machines.webRock.annotations.POST");
Class forwardAnnotation=Class.forName("com.thinking.machines.webRock.annotations.Forward");
Class onStartupAnnotation=Class.forName("com.thinking.machines.webRock.annotations.OnStartup");
Class injectApplicationScopeAnnotation=Class.forName("com.thinking.machines.webRock.annotations.InjectApplicationScope");
Class injectRequestScopeAnnotation=Class.forName("com.thinking.machines.webRock.annotations.InjectRequestScope");
Class injectSessionScopeAnnotation=Class.forName("com.thinking.machines.webRock.annotations.InjectSessionScope");
Class injectApplicationDirectoryAnnotation=Class.forName("com.thinking.machines.webRock.annotations.InjectApplicationDirectory");
Class autoWiredAnnotation=Class.forName("com.thinking.machines.webRock.annotations.AutoWired");
Class injectRequestParameterAnnotation=Class.forName("com.thinking.machines.webRock.annotations.InjectRequestParameter");
Class requestParameterAnnotation=Class.forName("com.thinking.machines.webRock.annotations.RequestParameter");
Class securedAccessAnnotation=Class.forName("com.thinking.machines.webRock.annotations.SecuredAccess");

boolean injectApplicationScope,injectSessionScope,injectRequestScope,injectApplicationDirectory;
String classPathAnnotationValue;
String methodPathAnnotationValue;
Class c;
Method methods[];
String key="";
Service service;
String pathToFile;
int priority;
List<AutoWiredProperty> autoWiredProperties;
AutoWiredProperty autoWiredProperty;
Field fields[];
LinkedHashMap<String,Class> requestParameterMap=null;
int numberOfParameters;
Annotation requestParameterAnnotations[][];
Class requestParameterTypes[];
List<InjectRequestParameterPOJO> injectRequestParameters=new ArrayList<>();
int j;
int classTypeParameterCount=0;
int requestParameterCount=0;
int scopeOrDirectoryParameterCount=0;
SecuredAccess securedAccessAnnotationObject;

// js file generation
Class requestParameterType;
Field pojoFields[];
String typeName;
Set<Class> pojoSet=new LinkedHashSet<>();
File jsFile=new File(getServletContext().getRealPath("/WEB-INF/js/"+getInitParameter("JsFile")));
if(!jsFile.exists()) jsFile.createNewFile();
RandomAccessFile randomAccessFile=new RandomAccessFile(jsFile,"rw");
randomAccessFile.setLength(0);
Collection<java.lang.String> mappings=null;
String urlPattern="";
// to get url-pattern against TMWebRock starts here
ServletContext servletContext = getServletContext();
ServletRegistration servletRegistration = servletContext.getServletRegistration("TMWebRock");
mappings = servletRegistration.getMappings();
// to get url-pattern against TMWebRock ends here

File tmpFile=new File(getServletContext().getRealPath("/WEB-INF/js/tmp.tmp"));
if(tmpFile.exists()) tmpFile.delete();
tmpFile.createNewFile();
RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");


for(File f:files)
{
if(f.isDirectory()) populateModel(f);
else
{
pathToFile=f.getPath();
if(pathToFile.contains(".class"))
{
pathToFile=pathToFile.substring(pathToFile.indexOf("classes")+8,pathToFile.length()).replace("\\",".");
c=Class.forName(pathToFile.substring(0,pathToFile.length()-6));
injectApplicationScope=c.getAnnotation(injectApplicationScopeAnnotation)!=null;
injectSessionScope=c.getAnnotation(injectSessionScopeAnnotation)!=null;
injectRequestScope=c.getAnnotation(injectRequestScopeAnnotation)!=null;
injectApplicationDirectory=c.getAnnotation(injectApplicationDirectoryAnnotation)!=null;
autoWiredProperties=new ArrayList<>();

// making list of Auto wired properties

fields=c.getDeclaredFields();
System.out.println(pathToFile);
int len=0;

// Iterating over fields
for(Field field : fields)
{
len++;
if(field.getAnnotation(autoWiredAnnotation)!=null)
{
autoWiredProperty=new AutoWiredProperty();
autoWiredProperty.setName(((AutoWired)field.getAnnotation(autoWiredAnnotation)).name());
autoWiredProperty.setType(field.getType());
autoWiredProperties.add(autoWiredProperty);
}
if(field.getAnnotation(injectRequestParameterAnnotation)!=null)
{
injectRequestParameters.add(new InjectRequestParameterPOJO(field.getName(),field.getType(),((InjectRequestParameter)field.getAnnotation(injectRequestParameterAnnotation)).key()));
}
} 
// Iterating over methods
methods=c.getMethods();
if(c.getAnnotation(pathAnnotation)!=null)
{
// js file generation starts here
tmpRandomAccessFile.writeBytes("class "+c.getSimpleName()+"{\r\n");

// js file generation ends here
classPathAnnotationValue=((Path)c.getAnnotation(pathAnnotation)).value();
for(Method method: methods)
{
if(method.getAnnotation(pathAnnotation)!=null)
{
tmpRandomAccessFile.writeBytes(method.getName()+"(");
methodPathAnnotationValue=((Path)method.getAnnotation(pathAnnotation)).value();
key=classPathAnnotationValue+methodPathAnnotationValue;
service=new Service();
service.setServiceClass(c);
service.setService(method);
service.setPath(key);
service.setIsGetAllowed(method.getAnnotation(GETAnnotation)!=null  ||  c.getAnnotation(GETAnnotation)!=null);
service.setIsPostAllowed(method.getAnnotation(POSTAnnotation)!=null  ||  c.getAnnotation(POSTAnnotation)!=null);
if(method.getAnnotation(forwardAnnotation)!=null) service.setForwardTo(((Forward)method.getAnnotation(forwardAnnotation)).value());
else service.setForwardTo(null);
service.setInjectApplicationScope(injectApplicationScope);
service.setInjectSessionScope(injectSessionScope);
service.setInjectRequestScope(injectRequestScope);
service.setInjectApplicationDirectory(injectApplicationDirectory);
service.setAutoWiredProperties(autoWiredProperties);
service.setInjectRequestParameters(injectRequestParameters);

//checking for RequestParameters
numberOfParameters=method.getParameterCount();
classTypeParameterCount=0;
requestParameterCount=0;
scopeOrDirectoryParameterCount=0;
/*if(numberOfParameters==0) 
{
tmpRandomAccessFile.writeBytes(")\r\n{\r\n");
tmpRandomAccessFile.writeBytes("var prm=new Promise(function(resolve,reject){\r\n");
tmpRandomAccessFile.writeBytes("});\r\nreturn prm;\r\n}\r\n");
}*/
if(numberOfParameters>0)
{
requestParameterMap=new LinkedHashMap<String,Class>();
requestParameterAnnotations=method.getParameterAnnotations();
requestParameterTypes=method.getParameterTypes();
System.out.println("Number of parameters: "+numberOfParameters);
//int ii=1;
/*
for(Parameter p: method.getParameters())
{
tmpRandomAccessFile.writeBytes(p.getName());
if(ii!=numberOfParameters) tmpRandomAccessFile.writeBytes(",");
ii++;
}
*/

for(j=0;j<numberOfParameters;j++)
{
if(requestParameterTypes[j].getSimpleName().equals("ApplicationScope"))
{
requestParameterMap.put("__applicationScope",requestParameterTypes[j]);
scopeOrDirectoryParameterCount++;
}
else if(requestParameterTypes[j].getSimpleName().equals("SessionScope"))
{
requestParameterMap.put("__sessionScope",requestParameterTypes[j]);
scopeOrDirectoryParameterCount++;
}
else if(requestParameterTypes[j].getSimpleName().equals("RequestScope"))
{
requestParameterMap.put("__requestScope",requestParameterTypes[j]);
scopeOrDirectoryParameterCount++;
}
else if(requestParameterTypes[j].getSimpleName().equals("ApplicationDirectory"))
{
requestParameterMap.put("__applicationDirectory",requestParameterTypes[j]);
scopeOrDirectoryParameterCount++;
}
else if(requestParameterTypes[j].isPrimitive()==false && requestParameterTypes[j].getSimpleName().equals("String")==false)
{
requestParameterType=requestParameterTypes[j];
requestParameterMap.put("__json",requestParameterTypes[j]);
classTypeParameterCount++;
// for tmp.tmp
tmpRandomAccessFile.writeBytes((char)(requestParameterType.getSimpleName().charAt(0)+32)+requestParameterType.getSimpleName().substring(1));
if(j!=(numberOfParameters-1)) tmpRandomAccessFile.writeBytes(",");


// for abcd.js
if(pojoSet.contains(requestParameterType)==false)
{
pojoSet.add(requestParameterType);
randomAccessFile.writeBytes("class "+requestParameterType.getSimpleName()+"\r\n{\r\nconstructor()\r\n{\r\n");
pojoFields=requestParameterType.getDeclaredFields();
Field field;
for(int i=0;i<pojoFields.length;i++)
{
field=pojoFields[i];
randomAccessFile.writeBytes("this."+field.getName()+"=");
typeName=field.getType().getTypeName();
if(typeName=="int" || typeName=="short" || typeName=="long" || typeName=="float" || typeName=="double") randomAccessFile.writeBytes("0");
else if(typeName=="char") randomAccessFile.writeBytes("''");
else if(typeName=="String" || typeName=="java.lang.String") randomAccessFile.writeBytes("\"\"");
else randomAccessFile.writeBytes("null");
randomAccessFile.writeBytes(";\r\n");
}
randomAccessFile.writeBytes("}\r\n}\r\n");
}
// done done
}
else
{
requestParameterMap.put(((RequestParameter)requestParameterAnnotations[j][0]).key(),requestParameterTypes[j]);
requestParameterCount++;
tmpRandomAccessFile.writeBytes(((RequestParameter)requestParameterAnnotations[j][0]).key());
if(j!=(numberOfParameters-1)) tmpRandomAccessFile.writeBytes(",");
}
}

if(classTypeParameterCount>1 ||  (classTypeParameterCount==1 && requestParameterCount>0) ||  (classTypeParameterCount==1 &&  (classTypeParameterCount+scopeOrDirectoryParameterCount)!=numberOfParameters))
{
System.out.println("classTypeParameterCount: "+classTypeParameterCount);
System.out.println("scopeOrDirectoryParameterCount: "+scopeOrDirectoryParameterCount);
System.out.println("requestParameterCount"+requestParameterCount);
throw new Exception("Parameter types of method "+method.getName()+" are malformed");
}
service.setRequestParameterMap(requestParameterMap);
}
else
{
service.setRequestParameterMap(null);
}
tmpRandomAccessFile.writeBytes(")\r\n{\r\n");
tmpRandomAccessFile.writeBytes("var prm=new Promise(function(resolve,reject){\r\n");

Set<String> keySet=requestParameterMap.keySet();
Class val;
String queryString="";
int ii=0;
String keyForQueryString="DEFAULT_VALUE";
if(numberOfParameters>0)
{
for(String key2:keySet)
{
keyForQueryString=key2;
if(key2=="__applicationScope" || key2=="__sessionScope" || key2=="__requestScope" || key2=="__applicationDirectory") continue;
val=requestParameterMap.get(key2);
if(val.getSimpleName()=="int" || val.getSimpleName()=="Integer")
{
if(ii!=0) queryString+="&";
ii++;
tmpRandomAccessFile.writeBytes("if((Number.isInteger("+key2+"))==false)\r\n{\r\nreject(\"int should be passed to "+method.getName()+" method\");\r\nreturn;\r\n}\r\n");
queryString=queryString+key2+"=\"+encodeURI("+key2+")";
}
else if(val.getSimpleName()=="String" || val.getSimpleName()=="java.lang.String")
{
if(ii!=0) queryString+="&";
ii++;
tmpRandomAccessFile.writeBytes("if("+key2+" instanceof String)==false)\r\n{\r\nreject(\"String should be passed to "+method.getName()+" method\");\r\nreturn;\r\n}\r\n");
queryString=queryString+key2+"=\"+encodeURI("+key2+")";
}
else if(val.isPrimitive()==false)
{
keyForQueryString=(char)(val.getSimpleName().charAt(0)+32)+val.getSimpleName().substring(1);
tmpRandomAccessFile.writeBytes("if(("+(char)(val.getSimpleName().charAt(0)+32)+val.getSimpleName().substring(1)+" instanceof "+val.getSimpleName()+")==false)\r\n{\r\n");
tmpRandomAccessFile.writeBytes("reject(\"instance of "+val.getSimpleName()+" class should be passed to "+method.getName()+" method\");\r\nreturn;\r\n}\r\n");
}
}
}

if(urlPattern.length()==0)for(String s: mappings) urlPattern=s;
if(service.getIsGetAllowed())
{
// done
if(queryString.length()!=0)tmpRandomAccessFile.writeBytes("$.get(\""+urlPattern.substring(1,urlPattern.length()-2)+classPathAnnotationValue+methodPathAnnotationValue+"?"+queryString+",function(data,status){\r\n");
else tmpRandomAccessFile.writeBytes("$.get(\""+urlPattern.substring(1,urlPattern.length()-2)+classPathAnnotationValue+methodPathAnnotationValue+"\",function(data,status){\r\n");
}
else if(service.getIsPostAllowed())
{
if(queryString.length()==0) tmpRandomAccessFile.writeBytes("$.post(\""+urlPattern.substring(1,urlPattern.length()-2)+classPathAnnotationValue+methodPathAnnotationValue+"\",JSON.stringify("+keyForQueryString+"),function(data,status){\r\n");
else tmpRandomAccessFile.writeBytes("$.post(\""+urlPattern.substring(1,urlPattern.length()-2)+classPathAnnotationValue+methodPathAnnotationValue+"?"+queryString+",function(data,status){\r\n");
}
tmpRandomAccessFile.writeBytes("if(status!=\"success\")\r\n{\r\n");
tmpRandomAccessFile.writeBytes("reject(\"Some error occured\");\r\nreturn;\r\n}\r\n");
tmpRandomAccessFile.writeBytes("if(data==null)\r\n{\r\nreject(\"Some error occured\");\r\nreturn;\r\n}\r\n");
tmpRandomAccessFile.writeBytes("if(data.isSuccessful==false)\r\n{\r\nreject(data.exception);\r\nreturn;\r\n}\r\n");
tmpRandomAccessFile.writeBytes("resolve(data.response);\r\n");
if(service.getIsGetAllowed() || service.getIsPostAllowed())tmpRandomAccessFile.writeBytes("},\"json\");\r\n");
tmpRandomAccessFile.writeBytes("});\r\nreturn prm;\r\n}\r\n");


// checking Secured Access
securedAccessAnnotationObject=(SecuredAccess)method.getAnnotation(securedAccessAnnotation);
if(securedAccessAnnotationObject==null)
{
securedAccessAnnotationObject=(SecuredAccess) service.getServiceClass().getAnnotation(securedAccessAnnotation);
}
if(securedAccessAnnotationObject!=null)
{
service.setSecuredAccess(true);
service.setCheckPost(securedAccessAnnotationObject.checkPost());
service.setGuard(securedAccessAnnotationObject.guard());
}
this.webRockModel.addToMap(key,service);
}// if
} //for
tmpRandomAccessFile.writeBytes("}\r\n");
} // if
for(Method method: methods)
{
if(method.getAnnotation(onStartupAnnotation)!=null)
{
priority=((OnStartup)method.getAnnotation(onStartupAnnotation)).priority();
service=new Service();
service.setServiceClass(c);
service.setService(method);
service.setRunOnStart(true);
service.setPriority(priority);
addToStartupServices(service);
}
}

}
}
}

}catch(Exception e)
{
System.out.println("ERROR");
System.out.println("ERROR");
System.out.println("ERROR");
System.out.println("ERROR");
System.out.println("ERROR");
System.out.println("ERROR");
System.out.println("ERROR");
System.out.println("ERROR");
System.out.println(e);
System.out.println(e.getCause());
System.out.println("ERROR");
System.out.println("ERROR");
System.out.println("ERROR");
System.out.println("ERROR");
System.out.println("ERROR");
System.out.println("ERROR");
System.out.println("ERROR");
System.out.println("ERROR");
System.out.println("ERROR");
System.out.println("ERROR");
System.out.println("ERROR");
System.out.println("ERROR");
}
}
public void addToStartupServices(Service service)
{
System.out.println("addToStartupServices called for "+service.getPath());
try{
int priority=service.getPriority();
Method serviceMethod=service.getService();
int i;
if(serviceMethod.getReturnType()==void.class)
{
if(serviceMethod.getParameterCount()==0)
{
for(i=0;i<this.startupServices.size();i++)
{
if(this.startupServices.get(i).getPriority()>=priority) break;
}
this.startupServices.add(i,service);
System.out.println("added");
}
else
{
System.out.println("Service "+service.getServiceClass().getName()+"::"+serviceMethod.getName()+" can not be added to Startup Services as it does not have zero paramters");
}
}
else
{
System.out.println("Service "+service.getServiceClass().getName()+"::"+serviceMethod.getName()+" can not be added to Startup Services as it does not have void as return type");
}
}catch(Exception e)
{
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println(e);
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
System.out.println("");
}
}
}