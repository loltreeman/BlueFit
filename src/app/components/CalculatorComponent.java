package app.components;
import java.lang.Math;
import org.springframework.stereotype.Component;

@Component
public class CalculatorComponent {

	public String add(String a, String b)
	{
		double a_num = Double.parseDouble(a);
		double b_num = Double.parseDouble(b);
		return Double.toString(a_num + b_num);
	}
	
	public String subtract(String a, String b)
	{
		
		double a_num = Double.parseDouble(a);
		double b_num = Double.parseDouble(b);
		return Double.toString(a_num - b_num);
	}
	
	public String divide(String a, String b)
	{
		double a_num = Double.parseDouble(a);
		double b_num = Double.parseDouble(b);
		return Double.toString(a_num / b_num);
	}

	public String multiply(String a, String b)
	{
		double a_num = Double.parseDouble(a);
		double b_num = Double.parseDouble(b);
		return Double.toString(a_num * b_num);
	}
	
	public String sqrt(String n_string)
	{
		double n = Double.parseDouble(n_string);
		return Double.toString(Math.sqrt(n));
	}
	

}
