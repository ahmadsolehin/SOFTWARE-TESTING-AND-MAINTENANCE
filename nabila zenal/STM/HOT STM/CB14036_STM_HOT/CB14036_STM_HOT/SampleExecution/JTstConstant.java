/**
 * Title:        JTstConstant
 * Description:  An Automated Java Unit Testing Tool
 * Copyright:    Copyright (c) 2005
 * Company:      USM
 * @author:      Dr Kamal Zuhairi Zamli, Dr Nor Ashidi Mat Isa, Siti Norbaya Azizan
 * @version:     1.0
 */


public interface JTstConstant
{
    
    // stub filename for code generation
    public static String TEMP_DIRECTORY = "D:\\CB14036_STM_HOT\\SampleExecution\\"; 
    public static String LOG_DIRECTORY = "D:\\CB14036_STM_HOT\\SampleExecution\\";
    public static String FAULT_DIRECTORY ="D:\\CB14036_STM_HOT\\SampleExecution\\";
    public static String CLASS_DIRECTORY ="D:\\CB14036_STM_HOT\\SampleExecution\\";
    public static String INSPECTOR_FILE = TEMP_DIRECTORY+"inspect.dat";
    public static String JAVA_EXT =".java";
    public static String CLASS_EXT =".class";
    public static String LOG =".log";
    public static String OUT =".out";
 
    // .class file cannot have directory info
    public static String STUB ="stub";
    public static String LOGFILE = LOG_DIRECTORY+"output";
    public static String COMPILE_RESULT = TEMP_DIRECTORY+"compile";
    public static String HEADER_FILE = TEMP_DIRECTORY+"header";
    public static String BODY_FILE = TEMP_DIRECTORY+"body";
    public static String KEY_FILE = TEMP_DIRECTORY+"key";

      
    // maximum allowable array length
    public static final int MAX = 1500;
}
