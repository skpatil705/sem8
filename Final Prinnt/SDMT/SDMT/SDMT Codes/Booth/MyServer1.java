import java.lang.*;
import java.io.*;  
import java.net.*;  
public class MyServer1 
{  
    int n1,n2;
    int m[] ;
    int x[] ;
    int q[] ;
    //int temp[];
    int q_1=0;
    int a[] ;
    int b;
    int flag=0;
    DataInputStream dis; 
    DataOutputStream diso;
    ServerSocket ss;
    Socket s;
    
    
    void establishConnection()
    {
    	try
    	{
    		ss=new ServerSocket(8776);
		s=ss.accept();//establishes connection 
		dis=new DataInputStream(s.getInputStream());
         	diso=new DataOutputStream(s.getOutputStream());   
	}
	catch(Exception e)
	{
		System.out.println(e);
	}
    }
    
    //BINARY CONVERSION
    int[] binary(int n,int b)
    {
	int i[] = new int[b];
	int v[] = new int[b];
	int p=0;
	while(n>0)
	{
		i[p] = n%2;
		n = n/2;
		p++;
	}
	
	for(int x=b-1,j=0;x>=0;x--,j++)
	{
		v[j] = i[x];
	}
	return v;
    }

    int getData()
    {
    	try
	    {  
		String str=(String)dis.readUTF();  		
		n1 = Integer.parseInt(str);
		if(n1<0)
			flag+=1;
		str=(String)dis.readUTF();  
		n2 = Integer.parseInt(str);
		if(n2<0)
			flag+=1;
		str=(String)dis.readUTF();  
		b = Integer.parseInt(str);
		
		m =  new int[b];
		x =  new int[b];
		q =  new int[b];
		a =  new int[b];
		//temp=new int[b];
		if(n1<0)
		{
			int p = n1*-1;
			m = binary(p,b);
			m = comp2(m,m.length);	
		}
		else
		{
			m=binary(n1,b);
			//temp = comp2(m,temp.length);
		}
		for(int i=0;i<b;i++)
		{
			x[i] = m[i];
			
			System.out.print(m[i]);
		}
		System.out.println();
		
		
		if(n2<0)
		{
			int p = n2*-1;
			q = binary(p,b);
			q = comp2(q,q.length);		
		}
		else
		{
			q=binary(n2,b);
		}
		for(int i=0;i<b;i++)
		{
			System.out.print(q[i]);
		}
		System.out.println();
	    }
	    catch(Exception e)
	    {
	    	System.out.println(e);
	    }  
	    return b;
    }

    //2'S COMPLEMENT
	int[] comp2(int[] n,int v)
	{
		int nn[]=new int[v];
		for(int i=0;i<v;i++)
		{
			nn[i]=n[i];
		}
		int com[] = new int[v];
		com[v-1] = 1;
		int res[] = new int[v];
		int c = 0;
		//1's complementing
		for(int i=0;i<v;i++)
		{
			if(nn[i]==0)
			{
				nn[i]=1;
			}
			else if(nn[i]==1)
			{
				nn[i]=0;
			}
		}
		//adding 1 to 1's complement
		for(int i=v-1;i>=0;i--)
		{
			res[i] = com[i] + nn[i] + c;
			if(res[i] >= 2)
			{
				c=1;
			}
			else
			{
				c=0;
			}
			res[i] = res[i]%2;
		}
		/*System.out.println("Complements is:");
		for(int i=0;i<b;i++)
		{
			System.out.print(res[i]);
		}
		System.out.println("\n");*/	
		return res;
	}
	 
    //ARITHMETIC SHIFTING    
    void arithmetic_shifting()
    {
	int x,y;
	x = a[0];
	for(int i=b-1;i>0;i--)
	{
		q[i]=q[i-1];
	}
	q[0]=a[b-1];

	for(int i=b-1;i>0;i--)
	{
		a[i]=a[i-1];
	}
	a[0]=x;
    }
    
    //ADDITION
    void addition(int[] y)
    {
     	int c=0;
	for(int i=b-1;i>=0;i--)
	{
		a[i] = y[i] + a[i] + c;
		if(a[i] >= 2)
		{
			c=1;
		}
		else
		{
			c=0;
		}
		a[i] = a[i]%2;
	}		
    }
    
    void display()
    {
	for(int j=0;j<b;j++)
	{
		System.out.print(a[j]+"");
	}
	System.out.print("\t\t");
	for(int j=0;j<b;j++)
	{
		System.out.print(q[j]+"");
	}
	System.out.print("\t\t");
	System.out.print(q_1+"");
	System.out.println();
    }
	
    void booth_algo()
    {
	if(q[b-1]==0 && q_1==0)
	{
		//shifting	
		q_1 = q[b-1];
		arithmetic_shifting();
	}
	else if(q[b-1]==1 && q_1==1)
	{
		//shifting	
		q_1 = q[b-1];
		arithmetic_shifting();			
	}
	else if(q[b-1]==0 && q_1==1)
	{
		//A+=M
		addition(m);
		//shifting
		q_1 = q[b-1];
		arithmetic_shifting();			
	}
	else if(q[b-1]==1 && q_1==0)
	{
		//A-=M
		int temp[] = new int[b];
		//display x here;
		//System.out.println("X is:");
		/*for(int i=0;i<b;i++)
		{
			temp[i]=x[i];
			System.out.println(x[i]);
		}*/
		//System.out.println("\n");
		temp = comp2(x,temp.length);
		addition(temp);		
		q_1 = q[b-1];
		//shifting
		arithmetic_shifting();
	}
    }	
    
    
    
    
    void sendResult()
    {
    	try
    	{
    		int dec = 0;
    		String result="";
		if(flag==1)
		{
			int res[] = new int[2*b];
			int i=0;
			for(i=0;i<b;i++)
			{
				res[i] = a[i];
			}
			int l=i;
			for(i=0;i<b;i++,l++)
			{
				res[l] = q[i];
			}
			res = comp2(res,res.length);
			//converting into decimal number
			for(int j=0,t=2*b-1;j<2*b-1;j++,t--)
			{
				dec += (res[t] * Math.pow(2,j));
			}
			System.out.println("Result is: "+dec*-1);
			dec=dec*-1;
			result = dec+"";
		}
		if(flag==0 || flag==2)
		{
			int res[] = new int[2*b];
			int i=0;
			for(i=0;i<b;i++)
			{
				res[i] = a[i];
			}
			int l=i;
			for(i=0;i<b;i++,l++)
			{
				res[l] = q[i];
			}
			//converting into decimal number
			for(int j=0,t=2*b-1;j<2*b-1;j++,t--)
			{
				dec += (res[t] * Math.pow(2,j));
			}
			System.out.println("Result is: "+dec);
			result = dec+"";
		}
		
		diso.writeUTF(result);
		diso.flush();
		diso.close();
		ss.close();
	}
	catch(Exception e) 
	{
		System.out.println(e);
	}

    }

    
    public static void main(String[] args)
    {  
	    MyServer1 b = new MyServer1();
	    b.establishConnection();		
	    int n = b.getData();
	    System.out.println("\n\nA\t\tQ\t\tQ-1");
	    b.display();
	    System.out.println("---------------------------------------------");
	    for(int i=0;i<n;i++)
	    {
		b.booth_algo();
		b.display();			
	    }
	b.sendResult();    
    }  
}  
