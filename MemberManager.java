import java.io.*;
import java.lang.*;
class MemberManager
{
private static final String DATA_FILE="Member.data";
public static void main(String gg[])
{ 
if (gg.length==0)
{
System.out.println("Please specify an operation");
return;
}

String operation=gg[0];

if(!isOperationValid(operation))
{
System.out.println(operation + "not exists");
System.out.println("Please specify from the following operations[add,update,getAll,getByCourse,getByConatactNumber]");
}
if(operation.equalsIgnoreCase("add"))
{
add(gg);
}else
if(operation.equalsIgnoreCase("update"))
{
update(gg);
}else
if(operation.equalsIgnoreCase("remove"))
{
remove(gg);
}else
if(operation.equalsIgnoreCase("getAll"))
{
getAll(gg);
}else
if(operation.equalsIgnoreCase("getByCourse"))
{
getByCourse(gg);
}else
if(operation.equalsIgnoreCase("getByContactNumber"))
{
getByContactNumber(gg);
}
}//main ends

//operation functions

private static void add(String [] data)
{ 
if (data.length!=5) 
{
System.out.println("Not enough data to add");
return;
}
String mobileNumber=data[1];
String name=data[2];
String course=data[3];
if(!isCourseValid(course))  
{
System.out.println(course + "is not available");
System.out.println("Please select from the following courses [c,c++,java,j2ee,python]");
return;
}
int fee;
try
{
fee=Integer.parseInt(data[4]);
}catch(NumberFormatException nfe)
{
System.out.println("Fee must be an integer type value");
return;
}
try
{
File file=new File(DATA_FILE);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
String fMobileNumber;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{  fMobileNumber=randomAccessFile.readLine();
  if(fMobileNumber.equalsIgnoreCase(mobileNumber))
 {
randomAccessFile.close();
System.out.println(mobileNumber + ": exists");
return;
 }
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();
}
randomAccessFile.writeBytes(mobileNumber);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(name);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(course);
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(String.valueOf(fee));
randomAccessFile.writeBytes("\n");
randomAccessFile.close();
System.out.println("MemberAdded");
}catch(IOException ioe)
{
System.out.println(ioe.getMessage());
}
}
private static void update(String [] data)
{ 
if (data.length!=5)
{
System.out.println("Incorrect number of data elements passed");
System.out.println("Usage : java MemberManager update mobile_number name course fee");
return ;
}
String MobileNumber=data[1];
String Name=data[2];
String Course=data[3];
if(!isCourseValid(Course))
{
System.out.println("Invalid Course :"+ Course);
System.out.println("Valid Courses are : [C/C++/Java/Python/J2EE]");
return;
}
int Fee;
try
{
Fee=Integer.parseInt(data[4]);
}catch(NumberFormatException nfe)
{
System.out.println("Fee should be an int type value");
return;
}
try
{ 
File file=new File(DATA_FILE);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(file.exists()==false)
{
System.out.println("No such file exists");
return;
}
if(randomAccessFile.length()==0)
{
System.out.println("No Member added uptill now");
return;
}
String fMobileNumber=" ";
String fName=" ";
String fCourse=" ";
int fFee=0;
boolean found=false;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fMobileNumber=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fCourse=randomAccessFile.readLine();
fFee=Integer.parseInt(randomAccessFile.readLine());
if(fMobileNumber.equalsIgnoreCase(MobileNumber))
{
found=true;
break;
}

}//end of while loop
if (found==false)
{
System.out.println(MobileNumber+"does not exists in the records");
randomAccessFile.close();
return;
}
System.out.println("Updating data of Mobile Number :"+MobileNumber);
System.out.println("Name of the candidate is :"+ fName);
File tmpFile=new File("tmp.tmp");
RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
tmpRandomAccessFile.setLength(0); //in case if a file with same name exits and have some data in it
randomAccessFile.seek(0); //to move internal pointer to the first byte of the file
while(randomAccessFile.getFilePointer()<randomAccessFile.length()) 
{ 
fMobileNumber=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fCourse=randomAccessFile.readLine();
fFee=Integer.parseInt(randomAccessFile.readLine());
if(fMobileNumber.equalsIgnoreCase(MobileNumber)==false)
{
tmpRandomAccessFile.writeBytes(fMobileNumber+"\n");
tmpRandomAccessFile.writeBytes(fName+"\n");
tmpRandomAccessFile.writeBytes(fCourse+"\n");
tmpRandomAccessFile.writeBytes(fFee+"\n");
}
else
{
tmpRandomAccessFile.writeBytes(MobileNumber+"\n");
tmpRandomAccessFile.writeBytes(Name+"\n");
tmpRandomAccessFile.writeBytes(Course+"\n");
tmpRandomAccessFile.writeBytes(Fee+"\n");

}
}//end of while
randomAccessFile.seek(0); 
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}//end of while
randomAccessFile.setLength(tmpRandomAccessFile.length());
tmpRandomAccessFile.setLength(0);
randomAccessFile.close();
System.out.println("Data Updated");
}catch(IOException ioe)
{
System.out.println(ioe.getMessage());
return;
}
}// end of update function
private static void remove(String [] data)
{
if (data.length!=2)
{
System.out.println("Incorrect number of data elements passed");
System.out.println("Usage : java MemberManager remove mobile_number ");
return ;
}
String MobileNumber=data[1];
try
{ 
File file=new File(DATA_FILE);
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(file.exists()==false)
{
System.out.println("No such file exists");
return;
}
if(randomAccessFile.length()==0)
{
System.out.println("No Member added uptill now");
return;
}
String fMobileNumber=" ";
String fName=" ";
String fCourse=" ";
int fFee=0;
boolean found=false;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fMobileNumber=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fCourse=randomAccessFile.readLine();
fFee=Integer.parseInt(randomAccessFile.readLine());
if(fMobileNumber.equalsIgnoreCase(MobileNumber))
{
found=true;
break;
}

}//end of while loop
if (found==false)
{
System.out.println(MobileNumber+"does not exists in the records");
randomAccessFile.close();
return;
}
System.out.println("Deleting data of Mobile Number :"+MobileNumber);
System.out.println("Name of the candidate is :"+ fName);
File tmpFile=new File("tmp.tmp");
RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
tmpRandomAccessFile.setLength(0); //in case if a file with same name exits and have some data in it
randomAccessFile.seek(0); //to move internal pointer to the first byte of the file
while(randomAccessFile.getFilePointer()<randomAccessFile.length()) 
{ 
fMobileNumber=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fCourse=randomAccessFile.readLine();
fFee=Integer.parseInt(randomAccessFile.readLine());
if(fMobileNumber.equalsIgnoreCase(MobileNumber)==false)
{
tmpRandomAccessFile.writeBytes(fMobileNumber+"\n");
tmpRandomAccessFile.writeBytes(fName+"\n");
tmpRandomAccessFile.writeBytes(fCourse+"\n");
tmpRandomAccessFile.writeBytes(fFee+"\n");
}
}//end of while
randomAccessFile.seek(0); 
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}//end of while
randomAccessFile.setLength(tmpRandomAccessFile.length());
tmpRandomAccessFile.setLength(0);
randomAccessFile.close();
tmpRandomAccessFile.close();
System.out.println("Data Deleted");
}catch(IOException ioe)
{
System.out.println(ioe.getMessage());
return;
}
}//end of remove fuction
private static void getAll(String data[])
{
try
{
File file=new File(DATA_FILE);
if(file.exists()==false)
{
System.out.println("file does not exists ");
return;
}
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
System.out.println("No members Added");
return;
}
String name;
String MobileNumber;
String course;
int fee;
int MemberCount=0;
int totalFee=0;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
MobileNumber=randomAccessFile.readLine();
name=randomAccessFile.readLine();
course=randomAccessFile.readLine();
fee=Integer.parseInt(randomAccessFile.readLine());
System.out.printf("%s,%s,%s,%d\n",MobileNumber,name,course,fee);
totalFee+=fee;
MemberCount++;
}
randomAccessFile.close();
System.out.println("TotalRegisterations :"+ MemberCount);
System.out.println("Total Fees Colleted is :" + totalFee);
}catch(IOException ioe)
{
System.out.println(ioe.getMessage());
}

}
private static void getByCourse(String [] data)
{
if(data.length!=2) 
{
System.out.print("Invalid Number of Data Elements passed \n");
return;
}
String Course=data[1];
try
{
File file=new File(DATA_FILE);
if(file.exists()==false)
{
System.out.println("File does not exist");
return;
}
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
System.out.println("No members added");
return;
}
String fMobileNumber=" ";
String fName=" ";
String fCourse=" ";
int fee=0;
boolean found=false;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fMobileNumber=randomAccessFile.readLine();
fName=randomAccessFile.readLine();
fCourse=randomAccessFile.readLine();
fee=Integer.parseInt(randomAccessFile.readLine());
if(fCourse.equalsIgnoreCase(Course)==true)
 {
System.out.println("Contact Number :"+fMobileNumber);
System.out.println("Name :"+fName);
System.out.println("Fee :"+ fee);
found=true;
}
}//end of while
randomAccessFile.close();
if(found==false)
{
System.out.println("No registrations against course :"+Course);
return;
}

}catch(IOException ioe)
{
System.out.println(ioe.getMessage());
}

}
private static void getByContactNumber(String [] data)
{ //start of function
if(data.length!=2) 
{
System.out.print("Invalid Number of Data Elements passed \n");
return;
}
String MobileNumber=data[1];
try
{
File file=new File(DATA_FILE);
if(file.exists()==false)
{
System.out.println("File does not exist");
return;
}
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
System.out.println("No members added");
return;
}
String fMobileNumber=" ";
String fName=" ";
String fCourse=" ";
int fee=0;
boolean found=false;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fMobileNumber=randomAccessFile.readLine();
if(fMobileNumber.equalsIgnoreCase(MobileNumber)==true)
 {
  fName=randomAccessFile.readLine();
  fCourse=randomAccessFile.readLine();
  fee=Integer.parseInt(randomAccessFile.readLine());
  found=true;
  break;
 }
randomAccessFile.readLine();
randomAccessFile.readLine();
randomAccessFile.readLine();

}//end of while
randomAccessFile.close();
if(found==false)
{
System.out.println("Invalid Mobile Number");
return;
}
System.out.println("Name :"+fName);
System.out.println("Course :"+fCourse);
System.out.println("Fee :"+fee);

}catch(IOException ioe)
{
System.out.println(ioe.getMessage());
}
}
//helper functions
public static boolean isOperationValid(String operation)
{ 
String operations[]={"add","update","getAll","getByCourse","getByContactNumber","remove"};
for(int e=0; e< operations.length ;e++) 
{
if(operation.equalsIgnoreCase(operations[e])) return true;
}
return false;
}
public static boolean isCourseValid(String course)
{ 
String courses[]={"C","C++","Python","J2EE","Java"};
for(int e=0;e<courses.length;e++) 
{
if(course.equalsIgnoreCase(courses[e])) return true;
}
return false;
}
}//class ends