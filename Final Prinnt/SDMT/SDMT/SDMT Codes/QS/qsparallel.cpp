#include<iostream>
#include<omp.h>
#include<stdlib.h>
#include<vector>
using namespace std;

class Quick
{
	vector<int> a;
	int n;
	public:
		int get_ele_number(); 
		Quick();
		int partition(int,int);
		void quicksort(int,int);
		void quicksort_p(int,int);
		void print_array();
};

void Quick :: print_array()
{
	cout<<"Sorted array is: \n";
	for(int i=0;i<n;i++)
	{
		cout<<a[i]<<"   ";
	}
	cout<<endl;
}

Quick :: Quick()
{
	a.clear();
	n=0;
}

int Quick :: get_ele_number()
{
	cout<<"\nEnter the number of elements: ";
	cin>>n;
	for(int i=0;i<n;i++)
	{
		a.push_back(rand()%1000+6);
	}
	return n;
}

int Quick :: partition(int low,int high)
{
	int p=a[high];
	int i=low-1;
	for(int j=low;j<high;j++)
	{
		if(a[j]<p)
		{	
			i++;
			int t=a[j];
			a[j]=a[i];
			a[i]=t;
		}
		
	}
	a[high]=a[i+1];
	a[i+1]=p;
	
	return i+1;
}
void Quick :: quicksort(int low,int high)
{
	if(low<high)
	{
		int i = partition(low,high);		
		
		quicksort(low,i-1);
		quicksort(i+1,high);
	}
}
void Quick :: quicksort_p(int low,int high)
{
	if(low<high)
	{
		int i=partition(low,high);
		if((high-low)<1000)
		{
			quicksort(low,i-1);
			quicksort(i+1,high);
		}
		else
		{
			#pragma omp task
			quicksort_p(low,i-1);
			#pragma omp task
			quicksort_p(i+1,high);
			#pragma omp taskwait	
		}
	}
}

int main()
{
	omp_set_nested(1);
	int k=0;
	double start,end;
	Quick q;
	k = q.get_ele_number();
	#pragma omp parallel
	{
		#pragma omp single
		{
			start=omp_get_wtime();
			q.quicksort_p(0,k-1);	
			end=omp_get_wtime();
			q.print_array();
			cout<<"Time required: "<<(end-start)<<" seconds.\n";
		}
	}
}




/*
True, they both are constant time. However, a vector is an object and there is a penalty for redirecting function calls. Consider this your first experience with C++ operator overloading. The vector class overloads the [] operator to achieve similar semantics with a real array.*/
