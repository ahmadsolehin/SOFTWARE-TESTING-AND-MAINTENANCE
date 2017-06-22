/**
 * Title:        LoaderManager
 * Description:  An Automated Java Unit Testing Tool
 * Copyright:    Copyright (c) 2005
 * Company:      USM
 * @author:      Dr Kamal Zuhairi Zamli, Dr Nor Ashidi Mat Isa, Siti Norbaya Azizan
 * @version:     1.0
 */



import java.io.*;
import java.util.*;
import java.lang.*;

public class LoaderManager implements JTstConstant

 {

    static String param_types;
    static int param_types_count = -1; // for error checking
    static String meth_name;
    static String classString;
    static String specifier;
    static String returnType;
    static String parameter_array[] = new String [MAX];
    static String arglist_array[] = new String [MAX];
    static int param_count=0;
    static String faultFileName;
    static int instance=-1;
    
   ////////////////////////////
   // Main for Loader Manager
   ///////////////////////////
   public static void main (String args[])

    {
       

       // get fault filename as the first parameter
       faultFileName = args[0].trim(); 
     
      // get instance of the running program as the second parameter
       instance = Integer.valueOf(args[1].trim()).intValue();
   
 
       //System.out.println (packageName); 
       //System.out.println (className);
       try{
            // must clear log from previous run
            deleteFile(JTstConstant.LOGFILE+Integer.toString(instance)+JTstConstant.LOG);  
            // do clean up of files
            cleanUp(); 
            prepareHeaderParameter(faultFileName);
            createHeaderFile();
            prepareTestCaseFile (faultFileName);
            iterateStubFile ();
            cleanUp();
            regenerateKey(instance);
    	       
          } 
       catch (IOException e) 
          { 
               System.out.println (e);
               //pause();
          };
       
           
       //pause();  
       
    }


    ///////////////////////
    //Load the fault setting for display
    //////////////////////
    public static void loadSetting(String[] list, String fileName)
    {
       
       try
       {
          int listIndex=0;
          File rf = new File(fileName);
          RandomAccessFile f = new RandomAccessFile(rf,"rw");
          long length = f.length();
          long position = 0;
          String content;

          // rewind file to position 0
          f.seek(0);
          while (position < length)
          {
           content = f.readLine();
           list [listIndex++]=content+"\n";
           position = f.getFilePointer();
          }
          f.close();         
        }
      catch (IOException e)
        {
          System.err.println (e);
        }

    }
     
    ///////////////////////
    //Prepares all the generic header file parameters
    //////////////////////
    private static void prepareHeaderParameter (String fileName) throws IOException
     {
          RandomAccessFile f = new RandomAccessFile(fileName,"rw");
          long length = f.length();
          long position = 0;
          long old_position = 0;
          String content;

          // reset parameter count each time
          param_count = 0;

          // rewind file to position 0
          f.seek(0);
          while (position < length)
          {

           old_position = position;
           content = f.readLine();
        
           // find class name, number of parameters and method name
           StringTokenizer s = new StringTokenizer (content,":");
           while (s.hasMoreTokens())
           {
            String string_val = s.nextToken();

            if (string_val.trim().equals("classname".trim()))
             {
               classString=s.nextToken().trim();
             }
            else if (string_val.trim().equals("paramtypes".trim()))
             {
                param_types=s.nextToken().trim();
                param_types_count=Integer.valueOf(param_types.trim()).intValue();

             }
            else if (string_val.trim().equals("methodname".trim()))
             {
                meth_name=s.nextToken().trim();
             }
            else if (string_val.trim().equals("specifier".trim()))
             {
                specifier=s.nextToken().trim();
             }
            else if (string_val.trim().equals("returntype".trim()))
             {
                returnType=s.nextToken().trim();
             }
           
            else if (string_val.trim().equals("parameter".trim()))
             {
                 parameter_array[param_count]=s.nextToken();    //array for parameter
                 param_count++;
             }
           }
          position = f.getFilePointer();
        }
        f.close();
    }



    ///////////////////////
    //Prepare generic header file
    //////////////////////
    protected static void createHeaderFile()throws IOException
    {
      RandomAccessFile f = new RandomAccessFile(JTstConstant.HEADER_FILE+"."+Integer.toString(instance),"rw");
        
      if (classString!=null)
        f.writeBytes ("classname: "+classString+"\n");
      if (param_types_count!=-1)
        f.writeBytes ("paramtypes: "+param_types_count+"\n");
      if (meth_name!=null)
        f.writeBytes ("methodname: "+meth_name+"\n");
      if (specifier!=null)
        f.writeBytes ("specifier: "+specifier+"\n");
      if (returnType!=null)
        f.writeBytes ("returntype: "+returnType+"\n");
      if (parameter_array[0]!=null)
        {
         for (int i=0;i<param_count;i++)
           f.writeBytes ("parameter: "+parameter_array[i]+"\n");
        }
      /*if (arglist_array[0]!=null)
        { 
         for (int i=0;i<arg_count;i++)
           f.writeBytes ("parameter: "+arglist_array[i]+"\n");
        }*/  
      f.close();
     
    }


    ///////////////////////
    //Prepare test case
    //////////////////////
    protected static void prepareTestCaseFile(String fileName)throws IOException
     {
      RandomAccessFile f = new RandomAccessFile(fileName,"rw");
      long length = f.length();
      long position = 0;
      long old_position = 0;
      String content;
      int no_test = 0;
      int arg_count = 0;

      // rewind file to position 0
      f.seek(0);
      while (position < length)
        {
           old_position = position;
           content = f.readLine();

           // find class name, number of parameters and method name
           StringTokenizer s = new StringTokenizer (content,":");
           while (s.hasMoreTokens())
           {
            String string_val = s.nextToken();
            if (string_val.trim().equals("arglist".trim()))
             {
                 arglist_array[arg_count]=s.nextToken();  // array for arglist
                 arg_count++;
                 if (arg_count==param_count)
                   {
                    saveTestCaseBody(no_test,arglist_array);   
                    no_test++;    
                    arg_count=0;
                   }
             }

           }
          position = f.getFilePointer();
        }
        f.close();
      }

    ///////////////////////
    //Save individual test body
    //i.e. 1 header and multiple bodies
    //with the instance extension
    //////////////////////
    protected static void saveTestCaseBody(int no_test,String[] arglist_array)throws IOException
     {
       RandomAccessFile f = new RandomAccessFile(JTstConstant.BODY_FILE+Integer.toString(no_test)
                                                 +"."+Integer.toString(instance),"rw");

       f.writeBytes ("Test ID: "+no_test+"\n");
       for (int i=0;i<MAX;i++)
         {
          if (arglist_array[i]!=null)
            f.writeBytes ("arglist : "+arglist_array[i]+"\n");
          else
            break;
         }    
       f.close();
     }
 
 
    ///////////////////////
    //Process special escape characters..complex algorithm
    //////////////////////
   
    public static void processEscapeChar(String[] arglist_array)
    {
       int[] location = new int[500];
       int count = 1;
       int no_of_insertion = 0;
      
      for (int i=0;i<param_count;i++)
        {         
           int k = arglist_array[i].lastIndexOf("\"");
           int start =0;
            
           for (int p=0; p<arglist_array[i].length();p++)
            {
              location [p] = arglist_array[i].indexOf("\"",start);
              start = location [p]+1;
           
              if (location [p]==k || location [p]==0)
                break;
              else
                count++;
              
            }

           // handles normal Java string input
           if (count%2==0) 
            {
              for (int q=0; q<count;q++)
               {

                if (q!=count-1)
                 {
                  StringBuffer s=new StringBuffer(arglist_array[i]);
                  if (arglist_array[i].charAt(location[q])!='\\')
                    {
                      s.insert(location[q]+q,"\\");
                      no_of_insertion++;
                    }
                  arglist_array[i]=s.toString(); 
                }
               else
                {
                 // if the last item having "
                 if (k>=0)
                  {
                   StringBuffer s=new StringBuffer(arglist_array[i]);
                   s.insert(k+no_of_insertion,"\\");
                   arglist_array[i]=s.toString();
                  }
                }
              }
            }
          else
            { 
              
              int x=0,y=0;

              // handle funny string input
              for (int q=0; q<count;q++)
               {
                x = arglist_array[i].indexOf("\"");
                y = arglist_array[i].lastIndexOf("\"");
               }
              
              if (x>=0)
               {
                StringBuffer s=new StringBuffer(arglist_array[i]);
                s.insert(x,"\\");
                arglist_array[i]=s.toString();
               }
              if (y>=0)
               {
                StringBuffer s=new StringBuffer(arglist_array[i]);
                s.insert(y+1,"\\");
                arglist_array[i]=s.toString();
               }
           }
        no_of_insertion = 0;     
        count=1;
     }
   }

    ///////////////////////
    // Auto Generate Stub
    //////////////////////
    public static void generateStubFile(int test_ID) throws IOException
     { 
   
      BufferedWriter fout = new BufferedWriter(new FileWriter(JTstConstant.TEMP_DIRECTORY+
                               JTstConstant.STUB+Integer.toString(instance)+JTstConstant.JAVA_EXT));   
      
      fout.write ("import java.lang.reflect.*;\n\n\n");
      fout.write ("public class stub"+Integer.toString(instance)+"\n");
      fout.write ("{ \n");
      fout.write ("public static void main (String args[]) \n");
      fout.write ("  {\n");
      fout.write ("    System.out.println (\"=========================================\");\n");
      fout.write ("    System.out.println (\"Test ID = "+Integer.toString(test_ID)+"\");\n");
      fout.write ("     try   \n");
      fout.write ("     {\n");
      fout.write ("     Class cls = Class.forName(\""+classString+"\"); \n");
      fout.write ("     Class partypes[] = new Class ["+param_types+"]; \n");
      for (int i=0;i<param_types_count;i++)
        fout.write("    "+parameter_array[i]+";\n");
      fout.write ("     Method meth = cls.getDeclaredMethod (\""+meth_name+"\", partypes);\n");
      fout.write ("     meth.setAccessible(true);\n");
      fout.write ("     "+classString+" methobj = new "+classString+"();\n");
      fout.write ("     Object arglist[] = new Object["+param_types+"];\n");
      fout.write ("     System.out.println (\" Class = "+classString+"\");\n");
      fout.write ("     System.out.println (\" Method = "+specifier+" "+meth_name+"\");\n");
      for (int i=0;i<param_types_count;i++)
       { 
         fout.write ("   "+arglist_array[i]+";\n");
       }

      // need to process escape char
      processEscapeChar(arglist_array);
      for (int i=0;i<param_types_count;i++)
       { 
         fout.write ("     System.out.println (\" "+arglist_array[i].trim() +"\");\n");
       }
      fout.write ("     Object retobj = meth.invoke(methobj, arglist);\n");
      if (returnType.trim().equals("int") | returnType.trim().equals("Integer")|
          returnType.trim().equals("float") | returnType.trim().equals("Float")|
          returnType.trim().equals("double")| returnType.trim().equals("Double")|
          returnType.trim().equals("boolean")| returnType.trim().equals("Boolean")|
          returnType.trim().equals("char")| returnType.trim().equals("Character") |
          returnType.trim().equals("Byte")| returnType.trim().equals("Long")|
          returnType.trim().equals("Short") | returnType.trim().equals("Object"))
          fout.write ("     System.out.println (\"Result observed ==> \"+retobj);\n");          
      fout.write ("     } \n");
      fout.write ("     catch (Throwable e)\n");
      fout.write ("      {\n");
      fout.write ("         System.err.println(\"Exception raised ==>\"+e);\n");
      fout.write ("      }\n");
      fout.write ("   }\n");
      fout.write ("}\n");
      fout.close();

    }


    ///////////////////////
    //Iterate stub codes
    //////////////////////
    protected static void iterateStubFile ()throws IOException

    {

     // check and delete injection log file from previous experiment
     if (fileExist(JTstConstant.LOGFILE+Integer.toString(instance)))
       deleteFile(JTstConstant.LOGFILE+Integer.toString(instance));

    
     for (int index=0;index<MAX;index++)
      {
         if (fileExist(JTstConstant.BODY_FILE+Integer.toString(index)+"."+Integer.toString(instance)))
           {
             // update arglist_array for each iteration
             arglist_array=updateArgList(index);
             generateStubFile(index);
             executeFaults();
           }
         else 
           break;
           
      }

      // if method to be tested has no parameter
      // i.e. there is a header file but no body 
      if (fileExist(JTstConstant.HEADER_FILE+"."+Integer.toString(instance)) && (param_count==0))
       {
             // update arglist_array for each iteration
             arglist_array=updateArgList(0);
             generateStubFile(0);
             executeFaults();
       } 
    }

    ///////////////////////
    // Update test cases arguments
    //////////////////////
    public static String[] updateArgList (int id)throws IOException
    {
      RandomAccessFile f = new RandomAccessFile(JTstConstant.BODY_FILE+Integer.toString(id)
                                                +"."+Integer.toString(instance),"rw");
      long length = f.length();
      long position = 0;
      long old_position = 0;
      String content;
      int index=0;
      String local_arglist_array[] = new String [MAX];

      // rewind file to position 0
      f.seek(0);
      while (position < length)
        {
           old_position = position;
           content = f.readLine();
           StringTokenizer s = new StringTokenizer (content,":");

           while (s.hasMoreTokens())
           {
            String string_val = s.nextToken();
            if (string_val.trim().equals("arglist".trim()))
             {
                 
                 local_arglist_array[index]=s.nextToken();  // array for arglist
                 index++;
             }
           
           }
           position = f.getFilePointer();
       }
      f.close();       
      return (local_arglist_array);      
    }


    ///////////////////////
    // Check file exist
    //////////////////////
    public static boolean fileExist(String fileName)throws IOException
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
        if (!exist)
          deleteFile(fileName);

        return exist;        
    }

     ///////////////////////
    //Executes fault injection
    //////////////////////
    private static void executeFaults() throws IOException
    {

     int exitVal=0;

     // compile the stub file and save results into compile log file
     try
       {
         FileOutputStream fos = new FileOutputStream(JTstConstant.COMPILE_RESULT+Integer.toString(instance)
                                                     + JTstConstant.OUT);
 
         Runtime rt = Runtime.getRuntime();
    
         Process proc = rt.exec("javac "+JTstConstant.TEMP_DIRECTORY+JTstConstant.STUB+Integer.toString(instance)+".java");
         // any error message?
         StreamGobbler errorGobbler = new
                StreamGobbler(proc.getErrorStream(), "ERROR", fos);

         // any output?
         StreamGobbler outputGobbler = new
               StreamGobbler(proc.getInputStream(), "OUTPUT", fos);

         // kick them off
         errorGobbler.start();
         outputGobbler.start();

         // any error???
         exitVal = proc.waitFor();
         // System.out.println("Stub Compilation Exit Code: " + exitVal);
         fos.flush();
         fos.close();
         
       }
       catch (Throwable t)
       {
         t.printStackTrace();
       }

     // if compilation exitVal is successful then
     // can execute stub.class
     if (exitVal==0)
      {
    
       // running the stub.class and save results into stub log file
       try
       {
         FileOutputStream fos = new FileOutputStream(JTstConstant.LOGFILE+Integer.toString(instance)
                                                     + JTstConstant.LOG,true);
         Runtime rt = Runtime.getRuntime();
         Process proc = rt.exec("java "+STUB+Integer.toString(instance));
         // any error message?
         StreamGobbler errorGobbler = new
                StreamGobbler(proc.getErrorStream(), "ERROR");

         // any output?
         StreamGobbler outputGobbler = new
               StreamGobbler(proc.getInputStream(), "OUTPUT", fos);

         // kick them off
         errorGobbler.start();
         outputGobbler.start();

         // any error???
         exitVal = proc.waitFor();
         System.out.println("OUTPUT>Exit code: " + exitVal);
         fos.flush();
         fos.close();
        }
       catch (Throwable t)
        {
          t.printStackTrace();
        }
    
      }  

        
    }

    ///////////////////////
    //Performs clean up 
    //////////////////////
    private static void cleanUp() throws IOException
    {


      deleteFile(JTstConstant.HEADER_FILE+"."+Integer.toString(instance));
      deleteFile(JTstConstant.COMPILE_RESULT+Integer.toString(instance)+ JTstConstant.OUT);
      // TEMP directory must be included 
      deleteFile(JTstConstant.TEMP_DIRECTORY+JTstConstant.STUB+Integer.toString(instance)+JTstConstant.JAVA_EXT);
      deleteFile(JTstConstant.TEMP_DIRECTORY+JTstConstant.STUB+Integer.toString(instance)+JTstConstant.CLASS_EXT);
  
     
      for (int index=0;index<MAX;index++)
      {
         if (fileExist(JTstConstant.BODY_FILE+Integer.toString(index)
                       +"."+Integer.toString(instance)))
           {
             deleteFile(JTstConstant.BODY_FILE+Integer.toString(index)
                       +"."+Integer.toString(instance));
           }
         else 
           break;
      }
    
    }
    

    ///////////////////////
    //Deletes file 
    //////////////////////
    
    protected static void deleteFile(String fileName) throws IOException
    {
       File emptyFile  = new File(fileName);
       boolean success = emptyFile.delete();
       //System.out.println ("Deleting ==>"+ fileName);
    }

    ///////////////////////
    //Regenerate key based on instance 
    //for parallelism 
    //////////////////////
    
    public static void  regenerateKey(int instance) throws IOException
    {
        RandomAccessFile f = new RandomAccessFile(JTstConstant.KEY_FILE
                             +Integer.toString(instance),"rw");
        f.close(); 
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

 }
