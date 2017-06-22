class DetTrian
{ 
public String triangle (int a, int b, int c)
{
int min,med, max; 
String str;
if (a>b)
{
max=a;
min = b;
}
else
{
max = b;
min = a;
}
if (c>max)
max = c;
else if (c<max)
min = c;
med = a+b+c-min-max;
if (max>min+med)
str= "Impossible triangle";
else if (max==min)
str= "Equilateral triangle";
else if (max==med||med==min)
str= "Isoceles triangle";
else if (max*max==min*min + med*med)
str="Rightangled triangle";
else
str="Any triangle";

return str;
}
}