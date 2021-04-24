package bobby.test;
import com.thinking.machines.webRock.annotations.*;
import com.thinking.machines.webRock.pojo.*;
//@InjectApplicationScope
@Path("/testStudent")
public class TestStudent
{
/*
private Item item;

@InjectRequestParameter(key="cd")
public int code;
public void setCode(int code)
{
this.code=code;
}
*/

@Path("/getAllStudents")
public String getAllStudents(ApplicationScope applicationScope)
{
//applicationScope.setAttribute("loggedIn",true);
return "allStudentData";
}
//@Forward("/employee/add")
//@POST
//@GET
@Path("/add")
public String add(RequestScope requestScope,Item item)
{
/*
item=new Item();
item.setCode(this.code);
item.setName(name);
if(requestScope!=null) requestScope.setAttribute("item",item);
else
{
System.out.println("requestScope is null");
}
*/
if(item==null) System.out.println("VALUE OF ITEM IS NULLLLLL");
return "Student added with code: "+item.getCode()+" and Name: "+item.getName();
}
@OnStartup(priority=1)
public void method1()
{
System.out.println("1");
System.out.println("1");
System.out.println("1");
System.out.println("1");
System.out.println("1");
System.out.println("1");
System.out.println("1");
}
@OnStartup(priority=3)
public void method3()
{
System.out.println("3");
System.out.println("3");
System.out.println("3");
System.out.println("3");
System.out.println("3");
System.out.println("3");
System.out.println("3");
System.out.println("3");
}
@OnStartup(priority=2)
public void method2()
{
System.out.println("2");
System.out.println("2");
System.out.println("2");
System.out.println("2");
System.out.println("2");
System.out.println("2");
System.out.println("2");
System.out.println("2");
System.out.println("2");
System.out.println("2");
}
}
