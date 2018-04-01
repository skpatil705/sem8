//Assignment 8 : gprof quicksort
#include<iostream>
#include<omp.h>
#include<stdlib.h>
#include<fstream>
using namespace std;
int n;								
int partition(int a[],int p, int r);				
void quicksort(int a[], int p, int r)
{
	int q;
	if(p<r){
		q=partition(a,p,r);				
		#pragma omp parallel sections
		{
			#pragma omp section
			{

				int tid = omp_get_thread_num();
			        printf("Thread %d working on index %d to %d.\n",tid,p,q-1);
				quicksort(a,p,q-1);
			}

			#pragma omp section
			{
				int tid2 = omp_get_thread_num();
		        	printf("Thread %d working on index %d to %d.\n",tid2,q+1,r);					
				quicksort(a,q+1,r);
			}

		}   }	}

int partition(int a[],int p, int r)
{
	int i=p-1;
	int x=a[r];
	int temp;
	for(int j=p; j<=r-1; j++){

		if(a[j]<=x){
			i++;
			temp=a[i];
			a[i]=a[j];
			a[j]=temp;
		}   }
	temp=a[i+1];
	a[i+1]=a[r];
	a[r]=temp;

return i+1;  }

int main()
{
	

	cout<<"\n Enter no. of elements : ";
	cin>>n;
	int a[n];
	for(int i=0; i<n; i++)
        {
	   a[i]=rand()%10000;
	}
	cout<<"\n Unsorted array : ";
	for(int i=0; i<n; i++){
		cout<<a[i]<<"\t";
	}
	cout<<"\n";
	double start = omp_get_wtime();
	quicksort(a,0,n-1);				
	double finish = omp_get_wtime();
	
	cout<<"\n Sorted array : ";
	for(int i=0; i<n; i++){
		cout<<a[i]<<"\t";

	}
	double time=finish-start;
	cout<<"\n\nInput dataset size : "<<n<<"  "<<"\n\nExecution time : "<<time<<"\n";

cout<<"\n";
return 0;
}

