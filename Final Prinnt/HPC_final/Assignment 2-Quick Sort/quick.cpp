	#include<iostream>
	#include<omp.h>
	#include<stdlib.h>
	using namespace std;


	void swap(int* a, int* b)
	{
	    int t = *a;
	    *a = *b;
	    *b = t;
	}

	int partition (int arr[], int low, int high)
	{
	    int pivot = arr[high];    
	    int i = (low - 1);  
	 
	    for (int j = low; j <= high- 1; j++)
	    {
		if (arr[j] <= pivot)
		{
		    i++;    
		    swap(&arr[i], &arr[j]);
		}
	    }
	    swap(&arr[i + 1], &arr[high]);
	    return (i + 1);
	}
	 
	void quickSort(int arr[], int low, int high)
	{
	    if (low < high)
	    {
		int pi = partition(arr, low, high);
		quickSort(arr, low, pi - 1);
		quickSort(arr, pi + 1, high);
	    }
	}
	 

	/*void printArray(int arr[], int size)
	{
	    int i;
	    for (i=0; i < size; i++)
		cout<<arr[i]<<"\n";
	    		
	}*/
	 
	int main()
	{
	    int ch,n;
	    cout<<"Enter size of the array : ";
	    cin>>n;
	    int arr[n];
	    for(int i=0;i<n;i++)
	    {
	    	arr[i]=rand();
	    }
	    double start=omp_get_wtime();
	    quickSort(arr, 0, n-1);
	    double finish=omp_get_wtime();
	    double req_time=finish-start;
	    //cout<<"\nSorted array:\n";
	   // printArray(arr, n);
	    cout<<"\nRequired Time : "<<req_time;
	    cout<<"\n";
	    return 0;
	}
