# Java-based-WebService-Framework
Framework makes it easy to create applications and services with absolute minimum fuss.Small over view of the frame work and user can easily use the framework to produce backends of the web applications/projects.

You can use this framework to create backend/serverside services for web requests, Now user not need to know about servlets or edit Web.xml,user can simply use the framework and all its need can be solved.

# Benifits of using this FrameWork:-
1.Absolutely no requirement for XML configuration.

2.No need to write servlets Classes for every new web Request.

3.User dont have to worry about get/post request and how to Handle them.

4.User dont have to worry about how to handle multipart requests and how to parse them and process them.

5.User can use ServicesDoc tool in framework to find list of services inside application.

# Getting Started.(steps to use the Framework)
1.Getting Started.(steps to use the Framework)

2.Extract the zip File.

3Copy/cut web.xml to tomcat9/Webapps/"Project Name"/WEB-INF/.

User just need to change/write a single word inside web.xml and that was the param-value against param-name 'SERVICE_PACKAGE_PREFIX' i.e. by default there was "bobby", user have to change it.

User have to write the folder-name/folder-name-starts-with there in which services which are using this Framework exists.

Note : the folder-name mention here should exists inside tomcat9/Webapps/"project name"/WEB-INF/classes/.
```
<init-param>
<param-name>SERVICE_PACKAGE_PREFIX</param-name>
<param-value>bobby</param-value>
</init-param>
```

the above example show how to write folder-name in which services classes are located (services classes are classes using the framework to create web service for requests).

User also have to change a single word inside web.xml, instead of 'service' user have to write his/her application entity name there.
```
<servlet>
<servlet-name>TMWebRock</servlet-name>
<servlet-class>com.thinking.machines.webrock.TMWebRock</servlet-class>
</servlet>
<servlet-mapping>
<servlet-name>TMWebRock</servlet-name>
<url-pattern>/service/*</url-pattern>
</servlet-mapping>
```
In above piece of code user have to make change only in line number 7. i.e. replace 'service' with other word.

Now you can forget about web.xml as you dont have to change or configure it again.

4.Now copy webServiceFramework.jar to tomcat9/Webapps/"Project Name"/WEB-INF/lib/. Tomcat search for servlet classes in classes folder or lib folder.

5.Now copy all files inside Dependencies folder and paste them inside tomcat9/lib/. these are all the files you will ever need to create a web service. Our framework is Dependent on some of the these files. some of the jar file may already be present there you can skip those files.

6.You are done setting up the environment,now you can use the frameWork easily.

# Tutorials and reference documentation:-
user can create Web service by using these annotations on class and Methods. User dont have to worry about how these webservices will run when request arrives. User can request Data, HttpServletRequest and HttpServletResponse from framework.
# Annotations User can use are:
1.@Path("/employee")

Path annotation can be applied to class and method.value of path should starts with front Slash followed by path. Example:-
```
import com.thinking.machines.webrock.annotations.*;
@Path("/employee")
public class Employee
{
@Path("/view")
public void view()
{
	System.out.println("View Service");
}
}
```
user can access this service by sending request to "User's entity name"/employee/view.

2.@RequestParameter("username").

RequestParameter annotation can only be applied on Parameter. User can use the following annotation to request data from framework which arrives as web request. framework finds the value of the annotation and search for data with given name in request Bag and if found provide this requested data to user without user having to worry about conversions. Example:-
```
import com.thinking.machines.webrock.annotations.*;
@Path("/employee")
public class Employee
{
@Path("/add")
public String add(@RequestParameter("username") String name,@RequestParameter("gender") String gender,@RequestParameter("indian") boolean indian)
{
System.out.println(name+"----"+gender+"-----"+indian);
return "Add model service Used";
}
}
```
Example url to access add service http://localhost:8080/"user's-application-context-name"/"user's-entity-name"/employee/add?username=Rahul+Choudhary&gender=male&indian=true To access Boolean data client user must send data as True or TRUE or true and same goes for it counterpart.

3.@SecuredAccess(checkPost="com.thinking.machines.secured.Security",guard="securityGuardOne")

By using this annotation user dont have to write verification code for every service that need to be secured,user can just apply this annotation to all the services that are needed to be secured from unidentified access. SecuredAccess annotation can only be applied on Method.

=> checkPost = full classname(with package) to your verification class.

=> guard = method name within user's verification class.

Example:-
```
import com.thinking.machines.webrock.annotations.*;
@Path("/employee")
public class Employee
{
@Path("/get")
@SecuredAccess(checkPost="com.thinking.machines.secured.Security",guard="securityGuardOne")
public String get()
{
System.out.println("Get method got invoked and returned a String");
return "Employee Getted";
}
}
```
value of checkPost should be a full name to the actual class that verify user based on the users details and value of guard should be method name inside that verification class.

4.@Forward("/employee/view")

Using this annotation user can forward request to another web service or to some jsp file below example show how to use forward annotaton to forward request to other service "/employee/view",you can also forward to some JSP also.by giving JSP file name as value of forward annotation. Forward annotation can only be applied on Method.

5.@Get

Using this annotation user can declare that only GET type request allowed for this service. Get annotation can be applied to both class and method. If applied on class then for all services inside that class GET type request is allowed. and we can apply for particular method also.

Similarly Post annotation can be used for allowing POST type request. and it can also applied on both class and method.

If neither Get and Post annotation applied on method then both GET and POST type requests allowed. and user can apply both Get and Post annotation on method to allow both type request.

6.@Post

Refer above point.

Example :-
```
import com.thinking.machines.webrock.annotations.*;
@Path("/employee")
@Get
public class Employee
{
@Path("/add")
@Get
@Forward("/employee/view")
public String add(@RequestParameter("username") String name,@RequestParameter("gender") String gender,@RequestParameter("indian") boolean indian)
{
System.out.println(name+"----"+gender+"-----"+indian);
return "Add model service Used";
}

@Path("/view")
@Post
public void view()
{
System.out.println("View Service");
}
}
```
7.@InjectApplicationScope

Framework provides four classes, names are -

RequestScope, SessionScope, ApplicationScope, ApplicationDirectory inside package com.thinking.machines.webrock.scopes, i.e. if user want to use these classes user have to import/write - "import com.thinking.machines.webrock.scopes.*;" on top.

Basically user uses these classes whenever user require any kind of scopes/application-directory inside any service or class.

These 3 classes which represent scopes have only 2 functionalities, they are - setAttribute and getAttribute.

public void setAttribute(String,Object); - for setting an attribute against name inside any scope.

public Object getAttribute(String); - for getting attribute against name from any scope.

and class with name ApplicationDirectory has only one functionality and it was - getDirectory.

public File getDirectory(); - for getting application directory.

User just need to write/take parameter of these above 4 classes inside any service and our framework will provide that scope/directory whatever is demanded by user.

Example :-
```
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.scopes.*;
@Path("/employee")
public class Employee
{
@Path("/view")
public void view(RequestScope rs, SessionScope ss, ApplicationScope as, ApplicationDirectory ad)
{
System.out.println("View Service");
rs.setAttribute("age",23);
ss.setAttribute("name","Shivam Maheshwari");
as.setAttribute("gender","Male");
System.out.println(rs.getAttribute("age"));
System.out.println(ss.getAttribute("name"));
System.out.println(as.getAttribute("gender"));
System.out.println(ad.getDirectory().getAbsolutePath());
}
}
```
This annotation(InjectApplicationScope) and next 3 are only applied on class. User applied those annotations if user require any scope or directory.

If user uses any of these 4 annotations than user must have to write its respective setter for accessing scope/directory.
Example :-
```
import com.thinking.machines.webrock.annotations.*;
import com.thinking.machines.webrock.scopes.*;
@Path("/employee")
@InjectSessionScope
@InjectApplicationDirectory
public class Employee
{
private SessionScope sessionScope;
private ApplicationDirectory applicationDirectory;
public void setSessionScope(SessionScope sessionScope)
{
this.sessionScope=sessionScope;
}
private void setApplicationDirectory(ApplicationDirectory applicationDirectory)
{
this.applicationDirectory=applicationDirectory;
}
@Path("/view")
public void view(RequestScope rs, SessionScope ss, ApplicationScope as, ApplicationDirectory ad)
{
System.out.println("View Service");
rs.setAttribute("age",23);
ss.setAttribute("name","Rahul Choudhary");
as.setAttribute("gender","Male");
System.out.println(rs.getAttribute("age"));
System.out.println(ss.getAttribute("name"));
System.out.println(as.getAttribute("gender"));
System.out.println(ad.getDirectory().getAbsolutePath());

System.out.println(this.applicationDirectory.getDirectory().getAbsolutePath());
System.out.println(this.sessionScope.getAttribute("name"));
}
}
```
Above example shows how to get session scope and application directory. Similarly user can get application scope and request scope.
8.@InjectSessionScope

Refer above point number (7).

9.@InjectRequestScope

Refer above point number (7).

10.@InjectApplicationDirectory

Refer above point number (7).

11.@InjectRequestParameter("gender")

This annotation is same as RequestParameter annotation but unlike it applied on class properties. It simply work similar as RequestParameter but benefit of using this annotation was that if something is coming inside query string and more than one service required that then instead of using RequestParameter annotation on parameters of both services user can use InjectRequestParameter on that place.

Example:-
```
import com.thinking.machines.webrock.annotations.*;
@Path("/employee")
public class Employee
{
@InjectRequestParameter("gender")
private String gender;
@Path("/add")
public String add(@RequestParameter("username") String name,@RequestParameter("indian") boolean indian)
{
System.out.println(name+"----"+this.gender+"-----"+indian);
return "Add model service Used";
}
@Path("/view")
public void view()
{
System.out.println("View Service and Gender is - "+this.gender);
}
}
```
12.@OnStartup(priority=1)

This annotation can only be applied on method/service. User uses this annotation if user wants that one or more service got called when server get started then he/she must apply this annotation over those services and user have to mention priority of calling this services, lesser priority number will called first.

Example :-
```
import com.thinking.machines.webrock.annotations.*;
@Path("/employee")
public class Employee
{
@Path("/get")
@OnStartup(priority=1)
public String get()
{
System.out.println("Get method got invoked and returned a String");
return "Employee Getted";
}
}
```

13.@AutoWired(name="username")

AutoWired annotation can only be applied on properties of service class. User apply these annotation when he/she wants that the value against 'name' property of AutoWired annotation should setted to that property on which annotation applied and value will be extracted from scopes (request scope > session scope > application scope). Setter method should be present for that property so that value can be setted.

i.e. order of finding the value against value of name field of annotation was - Request scope -> Session scope -> Application scope.

Example :-
```
import com.thinking.machines.webrock.annotations.*;
@Path("/employee")
public class Employee
{
@AutoWired(name="username")
private String name;
public void setName(String name)
{
this.name=name;
}
@Path("/get")
@OnStartup(priority=1)
public String get()
{
System.out.println("Get method got invoked and returned a String");
System.out.println(this.name);
return "Employee Getted";
}
}
```
value against 'username' will be founded and setted to name property of employee class.

14.If user send raw data in post Request user can just use it simply as:-
```
@Path("/add")
public Student add(Student s)
{
return s;
}
```
Framework will automaically find the type of patameter and parse the raw data into Student object and pass it as argument when invoking this service method.

15.A tool is also provided which can be used to check errors in files written by user. User should go to WEB-INF\classes and type the follwing command, java -classpath ..\lib\*;c:\tomcat9\lib\*;. com.thinking.machines.webrock.tools.ServiceDoc path(where user wants to save the pdf) When user enters this line one of the following pdf will be generated,

 Error.pdf : If classes written by user contains errors this pdf will be generated.
 
 Service.pdf : If there are no error this pdf will be generated with a description of Annotations used, Classes and Methods.
