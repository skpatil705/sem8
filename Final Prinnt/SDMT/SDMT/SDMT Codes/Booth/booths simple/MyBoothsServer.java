import java.io.*;
import java.lang.*;
import java.net.*;
@SuppressWarnings("unused")
public class MyBoothsServer {
	ServerSocket ss;
	DataInputStream dis;
	DataOutputStream dos;
	Socket s;
	static int num1,num2,bit;
	int num1b[], num2b[];
	int res[];
	int booth_bit=0;
	int flag=0;
	void newConnection()
	{
		try 
		{
			ss = new ServerSocket(7876);
			s=ss.accept();
			dis = new DataInputStream(s.getInputStream());
			dos = new DataOutputStream(s.getOutputStream());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	void getData() 
	{
		try 
		{
			num1 = dis.readInt();
			num2 = dis.readInt();
			bit = dis.readInt();
			//System.out.println(num1+"  "+num2+"   "+bit);
			
			num1b = new int[bit]; //binary 
			num2b = new int[bit];
			res = new int[bit];
			String s1,s2;
			//check if number is negative, make it positive by multiplying with (-1)
			if(num1<0)
			{
				flag+=1;
				int n1 = num1 * (-1); //num1 made positive
				num1b = binary(n1,bit); //call binary() that converts integer num1 to binary number
				num1b = twoscomplement(num1b,num1b.length);
			}
			else
			{
				num1b = binary(num1,bit); //call binary() that converts integer num1 to binary number
			}
			
			if(num2<0)
			{
				flag+=1;
				int n2 = num2 * (-1); //num2 made positive
				num2b = binary(n2,bit); //call binary() that converts integer num2 to binary number
				num2b = twoscomplement(num2b, num2b.length);
			}
			else
			{
				num2b = binary(num2,bit);
				//call binary() that converts integer num2 to binary number
			}
			/*
			System.out.println("num1:   "+num1+":   "); //to check if numbers converted correctly
			for(int i=0;i<num1b.length;i++)
				System.out.print(num1b[i]);
			System.out.println("num2:   "+num2+":   ");
			for(int i=0;i<num2b.length;i++)
				System.out.print(num2b[i]); */
		} 
		catch (IOException e) {
			
			e.printStackTrace();
		}		
	}
	
	private int[] twoscomplement(int[] num1b2, int length) 
	{
		int[] arr = new int[length];
		int[] twocomplement = new int[length];
		int[] one = new int[length];
		
		for(int i=0;i<length;i++)
		{
			arr[i]=num1b2[i];
		}
		
		//taking one's complement of number
		for(int i=0;i<length;i++)
		{
			if(arr[i]==0)
				arr[i]=1;
			else if(arr[i]==1)
				arr[i]=0;
		}
		
		one[length-1] = 1;
		int c=0;
		
		for(int i=length-1;i>=0;i--)
		{
			twocomplement[i]=arr[i]+one[i]+c;
			if(twocomplement[i] >= 2)
				c=1;
			else
				c=0;
			
			twocomplement[i] %= 2;
		}
		return twocomplement;
	}

	int[] binary(int n1, int bit2) 
	{
		int[] i = new int[bit2];
		int[] v = new int[bit2];
		int p=0;
		while(n1>0)
		{
			i[p] = n1%2;
			n1 = n1/2;
			p++;
		}
		for(int j=0,k=bit2-1; k>=0; j++,k--)
		{
			v[j] = i[k];
		}
		return v;
	}
	
	void boothsAlgorithm()
	{
		if(num2b[bit-1]==0 && booth_bit==0)
		{
			booth_bit = num2b[bit-1];
			arithmeticshift();			
		}
		else if(num2b[bit-1]==1 && booth_bit==1)
		{
			booth_bit = num2b[bit-1];
			arithmeticshift();
		}
		else if(num2b[bit-1]==0 && booth_bit==1)
		{
			addition(num1b);
			booth_bit = num2b[bit-1];
			arithmeticshift();
		}
		else if(num2b[bit-1]==1 && booth_bit==0)
		{
			int[] temp = new int[bit];
			temp = twoscomplement(num1b, temp.length);
			addition(temp);
			booth_bit = num2b[bit-1];
			arithmeticshift();
		}
		
	}

	private void addition(int[] num1b2) 
	{
		int c=0;
		for(int i=bit-1;i>=0;i--)
		{
			res[i] = num1b2[i] + res[i] + c;
			if(res[i]>=2)
			{
				c=1;
			}
			else
			{
				c=0;
			}
			res[i]%=2;
		}
		
	}

	private void arithmeticshift() 
	{
		int x = res[0];
		for(int i=bit-1;i>0;i--)
		{
			num2b[i] = num2b[i-1];
		}
		num2b[0] = res[bit-1];
		for(int i=bit-1;i>0;i--)
		{
			res[i]=res[i-1];
		}
		res[0] = x;
		
	}
	
	private void display()
	{
		for(int i=0;i<bit;i++)
		{
			System.out.print(num1b[i]);
		}
		System.out.print("\t\t");
		for(int i=0;i<bit;i++)
		{
			System.out.print(res[i]);
		}
		System.out.print("  ");
		for(int i=0;i<bit;i++)
		{
			System.out.print(num2b[i]);
		}
		System.out.print("\t\t");
		System.out.print(booth_bit);
		System.out.println();
	}
	void converttoDecimal() 
	{
		String finalans = "";
		int dec=0;
		if(flag==1)
		{
			int[] combine = new int[bit*2];
			int n=bit*2;
			int i=0;
			for(i=0;i<bit;i++)
			{
				combine[i] = res[i];
			}
			int l=i;
			for(i=0;i<bit;i++,l++)
			{
				combine[l] = num2b[i];
			}
			
			combine = twoscomplement(combine, combine.length);
			
			for(int j=0,t=n-1;j<n-1;j++,t--)
			{
				dec+=(combine[t] * Math.pow(2, j));
			}
			dec = dec * (-1);
			System.out.println("Result is:"+dec);
			
			finalans = dec+"";
		}
		if(flag==0 || flag==2)
		{
			int[] combine = new int[bit*2];
			int n=bit*2;
			int i=0;
			for(i=0;i<bit;i++)
			{
				combine[i] = res[i];
			}
			int l=i;
			for(i=0;i<bit;i++,l++)
			{
				combine[l] = num2b[i];
			}
			
			dec=0;
			for(int j=0,t=n-1;j<n-1;j++,t--)
			{
				dec+=(combine[t] * Math.pow(2, j));
			}
			System.out.println("Result is:"+dec);
			
			finalans = dec+"";
		}
		
		try 
		{
			dos.writeUTF(finalans);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args) 
	{
		MyBoothsServer ms = new MyBoothsServer();
		System.out.println("Server started.....");
		ms.newConnection();
		System.out.println("Connection established....");
		ms.getData();
		
		System.out.println("\nMultiplicand\tMultiplier\t\tBooth's bit");
		ms.display();
		for(int k=0;k<bit;k++)
		{
			ms.boothsAlgorithm();
			ms.display();
		}
		
		ms.converttoDecimal();
	}

	
}
