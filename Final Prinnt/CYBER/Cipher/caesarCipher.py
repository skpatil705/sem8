import numpy as np
from pyDes import *

plainText = raw_input("Enter the Plain text : ")
class Encryption:
	def caesarCipher(self):
		shift = 3
		caesar=[]
		for i in plainText:
			ascii = ord(i)
			newAscii = ascii + shift
			if(i.isupper()):
				if(newAscii>90):
					newAscii = newAscii-90 + 64
			else:
				if(newAscii>122):
					newAscii = newAscii-122 + 96		
			caesar.append(chr(newAscii))
		print "Caesar Cipher : " , caesar
		
	def hillCipher(self):
		key = [[2,4,5],[9,2,1],[3,17,7]]
		Text=[]
		result =[]
		plainText1 = plainText.upper()
		msg = plainText1[:]
		NewText=[]
		encrypt=[]
		while(len(msg)%3!=0):
			msg=msg+'Z'
		for i in range(0,len(msg)):
			Text.append(ord(msg[i])-65)
		for i in range(0,len(Text),3):
			NewText.append(Text[i:i+3])				
		result = np.dot(NewText,key) %26	
		result = result + 65
		for i in range(0,len(result)):
			for j in range(0,3):
				encrypt.append(chr(result[i][j]))
		print "Hill Cipher : " ,encrypt
			
	def DES(self):
		k = des("DESCRYPT", padmode=PAD_PKCS5)
		d = k.encrypt(plainText)
		print("DES Cipher %r : " %d)
		
			
	
		

Encrypt = Encryption()

Encrypt.caesarCipher()
Encrypt.hillCipher()
Encrypt.DES()
print("\nSuccessfully Encrypt")
