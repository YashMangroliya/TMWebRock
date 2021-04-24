package bobby.test;
import java.sql.*;
import java.util.*;
import com.thinking.machines.webRock.annotations.*;
@Path("/student")
public class StudentService
{
@POST
@Path("/add")
public void add(Student student) throws Exception
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select rollNumber from Student where rollNumber=?");
preparedStatement.setInt(1,student.getRollNumber());
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new Exception("Roll Number "+student.getRollNumber()+" already exists.");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("insert into Student values(?,?,?)");
preparedStatement.setInt(1,student.getRollNumber());
preparedStatement.setString(2,student.getName());
preparedStatement.setString(3,String.valueOf(student.getGender()));
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}
@GET
@Path("/getAll")
public List<Student> getAll() throws Exception
{
Connection connection=DAOConnection.getConnection();
List<Student> list=new LinkedList<>();
PreparedStatement preparedStatement=connection.prepareStatement("select * from Student");
ResultSet resultSet=preparedStatement.executeQuery();
Student student;
while(resultSet.next())
{
student=new Student(resultSet.getInt("rollNumber"),resultSet.getString("name").trim(),resultSet.getString("gender").trim().charAt(0));
list.add(student);
}
resultSet.close();
preparedStatement.close();
connection.close();
return list;
}
@POST
@Path("/update")
public void update(Student student) throws Exception
{
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select rollNumber from Student where rollNumber=?");
preparedStatement.setInt(1,student.getRollNumber());
ResultSet resultSet=preparedStatement.executeQuery();
if(!resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new Exception("Student with roll number "+student.getRollNumber()+" does not exists.");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("update Student set name=? , gender=? where rollNumber=?");
preparedStatement.setString(1,student.getName());
preparedStatement.setString(2,String.valueOf(student.getGender()));
preparedStatement.setInt(3,student.getRollNumber());
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}
@POST
@Path("/delete")
public void delete(@RequestParameter(key="rollNumber") int rollNumber) throws Exception
{
System.out.println("delete function called with roll number: "+rollNumber);
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select rollNumber from Student where rollNumber=?");
preparedStatement.setInt(1,rollNumber);
ResultSet resultSet=preparedStatement.executeQuery();
if(!resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new Exception("Student with roll number "+rollNumber+" does not exists.");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("delete from Student where rollNumber=?");
preparedStatement.setInt(1,rollNumber);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}
@GET
@Path("/getByRollNumber")
public Student getByRollNumber(@RequestParameter(key="rollNumber") int rollNumber) throws Exception
{
Student student;
Connection connection=DAOConnection.getConnection();
PreparedStatement preparedStatement=connection.prepareStatement("select * from Student where rollNumber=?");
preparedStatement.setInt(1,rollNumber);
ResultSet resultSet=preparedStatement.executeQuery();
if(!resultSet.next())
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new Exception("Roll number "+rollNumber+"  does not exists.");
}
student=new Student(rollNumber,resultSet.getString("name"),resultSet.getString("gender").charAt(0));
return student;
}
}