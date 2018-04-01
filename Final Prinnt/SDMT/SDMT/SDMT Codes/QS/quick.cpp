#include<iostream>
//#include<omp.h>
using namespace std;
class Quick
{
	private:
	int *array;
	int size;
	public:
	Quick()
	{
		cout<<"Enter n:";	
		cin>>size;
		array = new int[size];
	
		cout<<"Enter elements:";
		for(int i=0;i<size;i++)
			cin>>array[i];	
	}
	void display();	
	void quicksort();
	void quicksortn(int l, int h);
	int partition(int l,int h);
};

void Quick::display()
{
	for(int i=0;i<size;i++)
	cout<<array[i]<<"   ";
}

void Quick::quicksort()
{
	quicksortn(0, size-1);
}

void Quick::quicksortn(int l,int h)
{
	if(l<h)
	{
		int p = partition(l, h);
		#pragma omp parallel sections
		{
		#pragma omp section
		{
			quicksortn(l, p-1);
		}
		
		#pragma omp section
		{
			quicksortn(p+1,h);	
		}
		
		}
	}
}

int Quick::partition(int l,int h)
{
	int x = array[h];
	int i = (l-1);
	for(int j=l;j<=h-1;j++)
	{
		if(array[j] <= x)
		{
			i++;
			int temp1 = array[i];
			array[i] = array[j];
			array[j] = temp1;
		}
	}
	int temp2 = array[i+1];
	array[i+1] = array[h];
	array[h] = temp2;
	return i+1;
}

int main()
{
	Quick q;
	//q.display();
	q.quicksort();
	
	q.display();
		

}
