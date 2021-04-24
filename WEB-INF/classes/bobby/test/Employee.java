package bobby.test;
import com.thinking.machines.webRock.annotations.*;
import com.thinking.machines.webRock.pojo.*;
@Path("/employee")
public class Employee
{
/*
@AutoWired(name="item")
public Item item;
public void setItem(Item item)
{
this.item=item;
}
public Item getItem()
{
return this.item;
}
*/
@Forward("/student/add")
@SecuredAccess(checkPost="bobby.test.Security",guard="guard")
@Path("/getAllEmployees")
public String getAllEmployees()
{
return "all Employee's Data";
}
//@Forward("/index.html")
@Path("/add")
public String add(RequestScope requestScope)
{
//item=(Item)requestScope.getAttribute("item");
//return "Employee added, Item  name: "+item.getName()+" , Item code: "+item.getCode();
return "Employee added";
}
@OnStartup(priority=1)
public void method1()
{ 
System.out.println("1 of Employee");
System.out.println("1 of Employee");
System.out.println("1 of Employee");
System.out.println("1 of Employee");
System.out.println("1 of Employee");
}
@Path("/method6")
@OnStartup(priority=6)
public void method6()
{
System.out.println("6 of Employee");
System.out.println("6 of Employee");
System.out.println("6 of Employee");
System.out.println("6 of Employee");
System.out.println("6 of Employee");
}
@OnStartup(priority=3)
public int method3()
{
System.out.println("3 of Employee");
System.out.println("3 of Employee");
System.out.println("3 of Employee");
System.out.println("3 of Employee");
return 100;
}
@OnStartup(priority=88)
public void method4(int p)
{
System.out.println("4 of Employee");
System.out.println("3 of Employee");
System.out.println("3 of Employee");
System.out.println("3 of Employee");
}
}
