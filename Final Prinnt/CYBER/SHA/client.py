
import socket
import hashlib
s = socket.socket()
s.connect(('172.16.133.98',8004))

msg = s.recv(1024)
s.send("Message Recieve")
hash0 = s.recv(1024)
hash1 = hashlib.sha1(msg).hexdigest()
print("Message recieved is: "+ msg)
print(hash0)
print(hash1)
if (hash0==hash1):
	print("Hash correct")
else:
	print("Incorrect hash")
