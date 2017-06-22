
import java.util.*;
import java.lang.Math;


public class Complex
{
  private String real;
  private String imaginary;

  public Complex ()
   {

     this.real = "0";
     this.imaginary = "0";
   }

  public Complex (String real, String imaginary)
   {

     this.real = real;
     this.imaginary = imaginary;
   }

  public String getReal()
   {
     return real;
   }
 

  public String getImaginary()
   {
     return imaginary;
   }
}
