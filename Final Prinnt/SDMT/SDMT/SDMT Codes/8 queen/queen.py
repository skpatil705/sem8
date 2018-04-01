import json
x=[20,21,22,23,24,25,26,27,28]
n=8
global flag
def Queen(k):
	global flag
	for i in range(1,n+1):
		if (Place(k,i)==True):
			x[k]=i;
			if(k==n):
				if(flag==0):
					print("\nSolutions that satisfy the given condition are:\n")
				print x[1:n+1]
				flag=1
			else:
				Queen(k+1)

def Place(k,i):
	for j in range (1,k):
		if((x[j]==i) or (abs(x[j]-i)==abs(j-k))):
			return False
	return True
	
data=[]
flag=0
with open('input.json') as f:
	data=json.load(f)
    
if(data["start"]<1 or data["start"]>8):
	print "\nInvalid JSON input.\n"
        exit()

print "\nGiven condition: First queen is placed in column ",data["start"],".\n"
x[1]=data["start"]      
Queen(2)
if(flag==0):
	print("\nNo solution satisfying the given conditon is found.\n")

print("\n")

