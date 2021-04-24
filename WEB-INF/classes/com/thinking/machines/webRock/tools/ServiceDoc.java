package com.thinking.machines.webRock.tools;

import com.thinking.machines.webRock.annotations.*;
//import com.thinking.machines.webRock.servlets.*;
import com.thinking.machines.webRock.pojo.*;
import com.thinking.machines.webRock.exceptions.*;
import com.itextpdf.text.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;


import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


public class ServiceDoc
{
private static Document document = new Document();
private static HashMap<String,Service> servicesMap=new HashMap<>();


public static void main(String gg[])
{

String pathToClassesFolder=gg[0];
String pathToPdfFile=gg[1];
File classesFolder=new File(pathToClassesFolder);
if(!classesFolder.exists() || !classesFolder.isDirectory())
{
System.out.println("Path to classes folder does not exists or is not directory.");
return;
}

try{
createPDF(classesFolder);
Class GETAnnotation=Class.forName("com.thinking.machines.webRock.annotations.GET");
Class POSTAnnotation=Class.forName("com.thinking.machines.webRock.annotations.POST");
Class forwardAnnotation=Class.forName("com.thinking.machines.webRock.annotations.Forward");
PdfWriter.getInstance(document, new FileOutputStream(pathToPdfFile.replace("\\","/")));
document.open();
ArrayList<String> keySet=new ArrayList<>(servicesMap.keySet());
Paragraph p1;
Font f=new Font(Font.FontFamily.TIMES_ROMAN,10);
Parameter parameters[];
p1=new Paragraph("Starting from here",f);
document.add(p1);
for(String key: keySet)
{
Service s=servicesMap.get(key);
p1=new Paragraph("Path:                   "+key,f);
document.add(p1);
p1=new Paragraph("Class:                  "+s.getServiceClass().getSimpleName(),f);
document.add(p1);
p1=new Paragraph("Method:              "+s.getService().getName(),f);
document.add(p1);
parameters=s.getService().getParameters();
if(parameters.length==0)
{
p1=new Paragraph("Parameters:          0 Parameters",f);
document.add(p1);
}
else
{
p1=new Paragraph("Parameters:          "+parameters.length+" Parameters",f);
document.add(p1);
for(int i=0;i<parameters.length;i++)
{
p1=new Paragraph("                             "+(i+1)+"."+parameters[i].getType().getName(),f);
document.add(p1);
}
}
p1=new Paragraph("Return Type:         "+s.getService().getReturnType().getName(),f);
document.add(p1);
java.lang.annotation.Annotation annotations[]=s.getServiceClass().getAnnotations();
p1=new Paragraph("Other Annotations: ",f);
document.add(p1);
for(int i=0;i<annotations.length;i++)
{
p1=new Paragraph(annotations[i].toString(),f);
document.add(p1);
}
if(s.getIsGetAllowed()) 
{
p1=new Paragraph(s.getService().getAnnotation(GETAnnotation).toString(),f);
document.add(p1);
}
if(s.getIsPostAllowed()) 
{
p1=new Paragraph(s.getService().getAnnotation(POSTAnnotation).toString(),f);
document.add(p1);
}
if(s.getService().getAnnotation(forwardAnnotation)!=null) 
{
p1=new Paragraph(s.getService().getAnnotation(forwardAnnotation).toString(),f);
document.add(p1);
}
p1=new Paragraph("-------------------------------------------------------",f);
document.add(p1);
}
document.close();
System.out.println(servicesMap.size());
}catch(Exception e)
{
System.out.println(e);
System.out.println(e);
System.out.println(e);
System.out.println(e);
}

System.out.println("Service Doc");
}



public static void createPDF(File classesFolder)
{
try{
Class c=null;
Method methods[];
Class pathAnnotation=Class.forName("com.thinking.machines.webRock.annotations.Path");
String classPathAnnotationValue;
String methodPathAnnotationValue;
String key;
Service service;
Class GETAnnotation=Class.forName("com.thinking.machines.webRock.annotations.GET");
Class POSTAnnotation=Class.forName("com.thinking.machines.webRock.annotations.POST");
Class forwardAnnotation=Class.forName("com.thinking.machines.webRock.annotations.Forward");
Class onStartupAnnotation=Class.forName("com.thinking.machines.webRock.annotations.OnStartup");
String pathToFile;

//done

for(File file: classesFolder.listFiles())
{
if(file.getPath().contains("com")) continue;
if(file.isDirectory()) createPDF(file);
else
{
if(file.getPath().contains(".class"))
{
//c=Class.forName(file.getPath().replace("\\","."));

pathToFile=file.getPath();
pathToFile=pathToFile.substring(pathToFile.indexOf("classes")+8,pathToFile.length()).replace("\\",".");
c=Class.forName(pathToFile.substring(0,pathToFile.length()-6));



methods=c.getMethods();
if(c.getAnnotation(pathAnnotation)!=null)
{
classPathAnnotationValue=((Path)c.getAnnotation(pathAnnotation)).value();
for(Method method: methods)
{
if(method.getAnnotation(pathAnnotation)!=null)
{
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
service.setRunOnStart(method.getAnnotation(onStartupAnnotation)!=null);
servicesMap.put(key,service);
//System.out.println(key);
}// if
}// for



}




}
}
}
}catch(Exception e)
{
System.out.println(e);
System.out.println(e);
System.out.println(e);
System.out.println(e);
System.out.println(e);
}
}

}