
/**
 * Title:        InspectorManager.java
 * Description:  An Automated Java Unit Testing Tool
 * Copyright:    Copyright (c) 2005
 * Company:      USM
 * @author:      Dr Kamal Zuhairi Zamli
 * @version:     1.0
 **/


import java.lang.reflect.*;
import java.io.*;
import java.util.*;
import java.lang.*;


public class InspectorManager implements JTstConstant
{
   static String list[] = new String [MAX];
   static String array_var[] = new String [MAX]; 
   static String var1,var2,var3,var4,var5,var6,var7,var8,var9,
           var10,var11,var12,var13,var14,var15,var16;


   ////////////////////////////
   // Main for Inspector Manager
   ///////////////////////////
   public static void main (String args[])

    {
       String packageName, className;

       packageName = getPackage (args[0].trim());
       className = getClassName (args[0].trim());
      
       //System.out.println (packageName); 
       //System.out.println (className);
       if (fileExist(className+".class"))
         {
            if (!packageName.equals(""))
             {
               interrogate(packageName+"."+className,list); 
             }
            else
             { 
               interrogate(className,list);
             }              
            
            printList(list);
            try
              { 
                printToFile (INSPECTOR_FILE);
              } catch (IOException e) {};
         }
       else
         {
           System.out.println ("No such file exist");
         }    
       //pause();  
       
    }


   ////////////////////////////
   //Interrogate a given class
   ///////////////////////////
   public static void interrogate(String className, String[] list)
    {
      int listIndex=0;

      try
       {
         Class cls = Class.forName(className);
      	 Method methlist[] = cls.getDeclaredMethods();
         list[listIndex++]="------------------------------------------------------------------------------\n";

	 for (int i = 0; i< methlist.length; i++ )
          {
               
             Method m = methlist[i];
             // Display name of Class and Method
             list [listIndex++]="Method declaration = "+m.toString()+"\n";
             list [listIndex++]="Class name = " + m.getDeclaringClass()+"\n";
             list [listIndex++]="Method name = " + m.getName()+"\n";

             Class pvec[] = m.getParameterTypes();

             for (int j=0; j<pvec.length; j++)
               {
                array_var[j]=pvec[j].toString();
                var1 = array_var[j];
               // Variable Declaration
                var2 = "class [Ljava.lang.String;";
                var3 = "class [Ljava.lang.Integer;";
                var4 = "class [Ljava.lang.Character;";
                var5 = "class java.lang.String";
                var6 = "class java.lang.Integer";
                var7 = "class java.lang.Character";
                var8 = "class java.lang.Double";
                var9 =  "class [I";
                var10 = "class [C";
                var11 = "class [J";
                var12 = "class [D";
                //clasification Array of Class/ Object
                if (var2.trim().equals(var1.trim()))       //array class String
                   list [listIndex++]="parameter #" + j + " = array of class String\n";
                else if (var3.trim().equals(var1.trim())) // array of class Integer
                   list [listIndex++]="parameter #" + j + " = array of class Integer\n";
                else if (var4.trim().equals(var1.trim())) // array of class Character
                   list [listIndex++]="parameter #" + j + " = array of class Character\n";
                //clasification of Single Class/Object
                else if (var5.trim().equals(var1.trim())) //single class String
                   list [listIndex++]="parameter #" + j + " = class String\n";
                else if (var6.trim().equals(var1.trim())) // single class Integer
                   list [listIndex++]="parameter #" + j + " = class Integer\n";
                else if (var7.trim().equals(var1.trim())) // single class Character
                   list [listIndex++]="parameter #" + j + " = class Character\n";
                else if (var8.trim().equals(var1.trim())) // single class Double
                   list [listIndex++]="parameter #" + j + " = class Double\n";
                //clasification Array of Basic Data Types
                else if (var9.trim().equals(var1.trim())) //Array of basic data Integer
                   list [listIndex++]="parameter #" + j + " = array of int\n";
                else if (var10.trim().equals(var1.trim())) //Array of basic data Character
                   list [listIndex++]="parameter #" + j + " = array of char\n";
                else if (var11.trim().equals(var1.trim())) //Array of basic data Long
                   list [listIndex++]="parameter #" + j + " = array of long\n";
                else if (var12.trim().equals(var1.trim())) //Array of basic data Double
                   list [listIndex++]="parameter #" + j + " = array of double\n";
                else
                   list [listIndex++]="parameter #" + j + " =>> " + array_var[j]+"\n";
              }

                Class evec[] = m.getExceptionTypes();
                for (int j = 0; j < evec.length; j++)
                  list [listIndex++]="exception #" + j + " = " +evec[j]+"\n";
		list [listIndex++]="return type = " + m.getReturnType()+"\n";
		list [listIndex++]="------------------------------------------------------------------------------\n";
             }
           }
	   catch (Throwable e)
            {
              System.err.println(e);
	    }
    }

    ///////////////////////
    // Check file exist
    //////////////////////
    public static boolean fileExist(String fileName)
    {
          boolean exist = true;
          File rf = new File(fileName);
          try
           {
            FileReader f = new FileReader(rf);
            f.close();
           }
          catch (IOException e)
           {
             
             exist=false;
           }
          return exist;
          
     }

    ///////////////////////
    // Tokenize filename (i.e. removes file extension)
    ///////////////////////
    public static String tokenize(String fileName)
     { 

         StringTokenizer s = new StringTokenizer (fileName,".");
         String string_val = s.nextToken();  
         return (string_val);
     }

    ///////////////////////
    // Tokenize and get package name 
    // from user text field. Returns empty
    // string if no package found
    ///////////////////////
    public static String getPackage(String nameField)
     { 

         StringTokenizer s = new StringTokenizer (nameField,".");
         
         String string_val = s.nextToken();  
         String val = new String ("");
         int count = 0;
         while (s.hasMoreTokens())
           {
             val  = s.nextToken();
             count++;
           }

         s = new StringTokenizer (nameField,".");
         val = new String ("");
         String packageName = new String("");
         packageName = s.nextToken();
         
         int i=0;
         while (s.hasMoreTokens())
           {
             val = s.nextToken();
             if (i<count-1)
               packageName = packageName+"."+val;
             
             i++;               
           }

        if (count!=0)
         return (packageName);   
        else
         return "";

     }


    ///////////////////////
    // Tokenize and get classname 
    // Returns empty string if no package found
    ///////////////////////
    public static String getClassName(String nameField)
     { 

         StringTokenizer s = new StringTokenizer (nameField,".");
         
         String string_val = s.nextToken();  
         String val = new String ("");
         int count = 0;
         while (s.hasMoreTokens())
           {
             val  = s.nextToken();
             count++;
           }

         s = new StringTokenizer (nameField,".");
         val = new String ("");
         String fileName = new String("");
         fileName = s.nextToken();
         
         int i=0;
         while (s.hasMoreTokens())
           {
             val = s.nextToken();
             if (i==count-1)
               fileName=val;
             
             i++;               
           }

         return fileName;

     }


    ///////////////////////
    // Check .class file
    //////////////////////
    public static boolean isClassFile(String fileName)
     { 

         StringTokenizer s = new StringTokenizer (fileName,".");
         String string_val = s.nextToken();
         String extension = new String("");

         while (s.hasMoreTokens())
           {
             extension  = s.nextToken();
           }
         if (extension.trim().equals("class"))
           return true;
         else
           return false;

     }

    ///////////////////////
    // Pause
    //////////////////////
    public static void pause()
     { 

       BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
       String input = "";
       try
        {
         input = in.readLine();
        }catch (IOException e) {};

     }

    ///////////////////////
    // Print array to screen
    //////////////////////
    public static void printList(String[] list)
     { 

       for (int i=0;i<list.length;i++)
       {
         if (list[i]!=null)
           System.out.println (list[i]);
         else
          break;
       } 
       
     }


    ///////////////////////
    // Save list array to file
    //////////////////////
    public static void printToFile(String pathFileName) throws IOException
     { 
   
      BufferedWriter fout = new BufferedWriter(new FileWriter(pathFileName));   
      for (int i=0;i<list.length;i++)
       {
         if (list[i]!=null)
          { 
           fout.write (list[i]);
          }
         else
          break;
       } 
      fout.close();

    }


 }


