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
public class JsFileServer extends HttpServlet
{
public void doGet(HttpServletRequest request,HttpServletResponse response)
{
try{
String fileName=request.getParameter("name");
System.out.println(fileName);
File file=new File(getServletContext().getRealPath("/WEB-INF/js/"+fileName));
if(!file.exists())
{
System.out.println("File does not exists");
return;
}
System.out.println("File exists");
response.setContentType("text/javascript");
PrintWriter pw=response.getWriter();
System.out.println("File exists11");
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
long lengthOfFile=randomAccessFile.length();
System.out.println("File exists22");
System.out.println(lengthOfFile);
randomAccessFile.seek(0);
System.out.println(randomAccessFile.getFilePointer()<lengthOfFile);
String s;
while(randomAccessFile.getFilePointer()<lengthOfFile)
{
s=randomAccessFile.readLine();
pw.println(s);
}
randomAccessFile.close();
}catch(Exception e)
{
System.out.println(e);
System.out.println(e);
System.out.println(e);
System.out.println(e);
System.out.println(e);
System.out.println(e);
System.out.println(e);
System.out.println(e);
}
}
public void doPost(HttpServletRequest request,HttpServletResponse response)
{
doGet(request,response);
}
}