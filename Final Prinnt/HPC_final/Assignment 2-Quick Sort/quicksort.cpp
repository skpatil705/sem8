	#include<iostream>
	#include<stdlib.h>
	#include<omp.h>
	using namespace std;
	int swap(int *arr,int i,int j)
	{
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}
	int part(int *arr, int lo, int hi)
	{
		int i = lo;
		int j = hi;
	      	int pvt = arr[(lo+hi)/2];
	      	while(i<=j) 
		{
			while(arr[i]<pvt)
				i++;
			while(arr[j]>pvt)
				j--;
			if(i<=j) 
			{
				swap(arr,i,j);
				i++;
				j--;
		   	}
		}
		return i;
	}
	void quicksort(int *arr, int lo, int hi)
	{
		int partition = part(arr,lo,hi);
		#pragma omp parallel sections
		{
			#pragma omp section
			{
				if(lo<partition-1)
					quicksort(arr,lo,partition-1);
			}
			#pragma omp section
			{
				if(partition<hi)
					quicksort(arr,partition,hi);
			}
		}
	}
	int main()
	{
		int size = 0;
		cout<<"Enter the size of array"<<endl;
		cin>>size;
		int array[size];
		for(int i = 0; i < size; i++)
		{
			array[i]=rand();
		}
		double start=omp_get_wtime();
		quicksort(array,0,size-1);
		double finish=omp_get_wtime();
		/*cout<<"Sorted Array is:"<<endl;
		for(int i = 0; i < size; i++)
			cout<<array[i]<<endl;*/
		cout<<"Execution time is : "<<(finish-start)<<endl;
	}
