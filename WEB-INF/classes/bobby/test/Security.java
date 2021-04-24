package bobby.test;
import com.thinking.machines.webRock.annotations.*;
import com.thinking.machines.webRock.exceptions.*;
import com.thinking.machines.webRock.pojo.*;
public class Security
{
public void guard(ApplicationScope applicationScope) throws ServiceException
{
Object obj=applicationScope.getAttribute("loggedIn");
if(obj==null) throw new ServiceException("Access is not secured");
boolean b=((Boolean)obj).booleanValue();
if(b==false) throw new ServiceException("Access is not secured");
}
}