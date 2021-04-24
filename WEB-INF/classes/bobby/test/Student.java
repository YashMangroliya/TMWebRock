package bobby.test;
public class Student
{
private int rollNumber;
private String name;
private char gender;
public Student(int rollNumber,String name,char gender)
{
this.rollNumber=rollNumber;
this.name=name;
this.gender=gender;
}
public void setRollNumber(int rollNumber)
{
this.rollNumber=rollNumber;
}
public int getRollNumber()
{
return this.rollNumber;
}
public void setName(String name)
{
this.name=name;
}
public String getName()
{
return this.name;
}
public void setGender(char gender)
{
this.gender=gender;
}
public char getGender()
{
return this.gender;
}
}