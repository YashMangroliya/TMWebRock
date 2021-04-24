class Student
{
constructor()
{
this.rollNumber=0;
this.name="";
this.gender='';
}
}
class Item
{
constructor()
{
this.code=0;
this.name="";
}
}
class Employee{
add()
{
var prm=new Promise(function(resolve,reject){
if(status!="success")
{
reject("Some error occured");
return;
}
if(data==null)
{
reject("Some error occured");
return;
}
if(data.isSuccessful==false)
{
reject(data.exception);
return;
}
resolve(data.response);
});
return prm;
}
getAllEmployees()
{
var prm=new Promise(function(resolve,reject){
if(status!="success")
{
reject("Some error occured");
return;
}
if(data==null)
{
reject("Some error occured");
return;
}
if(data.isSuccessful==false)
{
reject(data.exception);
return;
}
resolve(data.response);
});
return prm;
}
method6()
{
var prm=new Promise(function(resolve,reject){
if(status!="success")
{
reject("Some error occured");
return;
}
if(data==null)
{
reject("Some error occured");
return;
}
if(data.isSuccessful==false)
{
reject(data.exception);
return;
}
resolve(data.response);
});
return prm;
}
}
class StudentService{
add(student)
{
var prm=new Promise(function(resolve,reject){
if((student instanceof Student)==false)
{
reject("instance of Student class should be passed to add method");
return;
}
$.post("schoolService/student/add",JSON.stringify(student),function(data,status){
if(status!="success")
{
reject("Some error occured");
return;
}
if(data==null)
{
reject("Some error occured");
return;
}
if(data.isSuccessful==false)
{
reject(data.exception);
return;
}
resolve(data.response);
},"json");
});
return prm;
}
update(student)
{
var prm=new Promise(function(resolve,reject){
if((student instanceof Student)==false)
{
reject("instance of Student class should be passed to update method");
return;
}
$.post("schoolService/student/update",JSON.stringify(student),function(data,status){
if(status!="success")
{
reject("Some error occured");
return;
}
if(data==null)
{
reject("Some error occured");
return;
}
if(data.isSuccessful==false)
{
reject(data.exception);
return;
}
resolve(data.response);
},"json");
});
return prm;
}
delete(rollNumber)
{
var prm=new Promise(function(resolve,reject){
if((Number.isInteger(rollNumber))==false)
{
reject("int should be passed to delete method");
return;
}
$.post("schoolService/student/delete?rollNumber="+encodeURI(rollNumber),function(data,status){
if(status!="success")
{
reject("Some error occured");
return;
}
if(data==null)
{
reject("Some error occured");
return;
}
if(data.isSuccessful==false)
{
reject(data.exception);
return;
}
resolve(data.response);
},"json");
});
return prm;
}
getAll()
{
var prm=new Promise(function(resolve,reject){
$.get("schoolService/student/getAll",function(data,status){
if(status!="success")
{
reject("Some error occured");
return;
}
if(data==null)
{
reject("Some error occured");
return;
}
if(data.isSuccessful==false)
{
reject(data.exception);
return;
}
resolve(data.response);
},"json");
});
return prm;
}
getByRollNumber(rollNumber)
{
var prm=new Promise(function(resolve,reject){
if((Number.isInteger(rollNumber))==false)
{
reject("int should be passed to getByRollNumber method");
return;
}
$.get("schoolService/student/getByRollNumber?rollNumber="+encodeURI(rollNumber),function(data,status){
if(status!="success")
{
reject("Some error occured");
return;
}
if(data==null)
{
reject("Some error occured");
return;
}
if(data.isSuccessful==false)
{
reject(data.exception);
return;
}
resolve(data.response);
},"json");
});
return prm;
}
}
class TestStudent{
add(item)
{
var prm=new Promise(function(resolve,reject){
if((item instanceof Item)==false)
{
reject("instance of Item class should be passed to add method");
return;
}
if(status!="success")
{
reject("Some error occured");
return;
}
if(data==null)
{
reject("Some error occured");
return;
}
if(data.isSuccessful==false)
{
reject(data.exception);
return;
}
resolve(data.response);
});
return prm;
}
getAllStudents()
{
var prm=new Promise(function(resolve,reject){
if(status!="success")
{
reject("Some error occured");
return;
}
if(data==null)
{
reject("Some error occured");
return;
}
if(data.isSuccessful==false)
{
reject(data.exception);
return;
}
resolve(data.response);
});
return prm;
}
}
