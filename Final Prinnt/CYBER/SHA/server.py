import os 
import socket
import hashlib

s = socket.socket()
s.bind(('172.16.133.98',8004))

s.listen(5)


while True:
	clientsocket,addr = s.accept()

	print("connection established with %s" %str(addr))
	msg = raw_input("Enter the string to be sent:")
	sha = hashlib.sha1(msg).hexdigest()
	
	clientsocket.send(msg)
	reply = clientsocket.recv(1024)
	print(""+reply)
	clientsocket.send(sha)
	
	clientsocket.close()
	
