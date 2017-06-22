import java.io.*;
import java.util.*;
import java.lang.Math;

public class adder
{


  private int add_basictypes_integer ( int first, int second)
   {
      int sum = first + second;
      System.out.println ("The sum = " + sum);
      return sum;
   }

  protected  void add_java_Integer ( Integer first, Integer second)
   {
      int sum = first.intValue() + second.intValue();
      System.out.println ("The sum = " + sum);
   }

  public  void add_user_defined_Complex_Double (Complex first, Complex second)
   {
      double real_result, imaginary_result;
      
      real_result = Double.parseDouble(first.getReal()) + Double.parseDouble(second.getReal());


      imaginary_result = Double.parseDouble(first.getImaginary()) + Double.parseDouble(second.getImaginary());

       System.out.println ("The sum = " + real_result + "+ j"+imaginary_result);


   }
}



