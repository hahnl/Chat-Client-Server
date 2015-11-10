# Author: Larissa Hahn
# File name: chatclient.py
# Last Modification Date: October 28, 2015
# References: Socket Programming, Section 2.7
#             from Computer Networking, A Top-Down Approach (6th Edition)
# Description: Client side of chat client which creates a client TCP socket,
# performs 3-way handshake with server chat client, collects chatclient username,
# collects message from user, sends message from Client to Server, receives
# message from Server to Client, and the program ends by entering the "\quit"
# command from either end of the TCP connection.

#Socket module import allows for creation of sockets within program
from socket import socket, AF_INET, SOCK_STREAM
import sys

#Check for the correct number of arguments (3)
if len(sys.argv) != 3:
    #Correct usage displayed... Try again
    print " usage: { python chatclient.py <server_hostname> <port_number> }"
    sys.exit(1)

#Get address of Server connection from command line arguments
serverName = sys.argv[1]
serverPort = int(sys.argv[2])

#Set up chatclient variables
quit = "\quit"
message = " "

#Create the Client Socket
clientSocket = socket(AF_INET, SOCK_STREAM) #IPv4, TCP Socket

#Initiate the TCP connection between the Client and Server
#Performs the 3-Way TCP Handshake
#If connection fails, throws "Connection refused." and ends program
try:
    clientSocket.connect((serverName,serverPort)) #address of Server connection
except Exception as error:
    print error.strerror + "."
    sys.exit(1)

#Inform user of chatclient that connection is established
print '\nWelcome to chatclient.'
print 'Connection with chatserve established.'
print 'Use \quit command to end connection.'

#Prompt to enter clientName
#clientName collects characters until user inputs a carriage return
clientName = raw_input('\nEnter a chatclient handle: ')

#10 character maximum for chatclient handle
while((len(clientName) > 10) or (" " in clientName)):
    clientName = raw_input('\nNotice: 10 character maximum and no whitespace allowed. Try again.\nEnter a chatclient handle: ')

#Add formatting to chatclient handle output
clientName = clientName+"> "
print ' '

#Infinite Loop which waits until the \quit command is received or sent to do a system exit
while(1):
    #message continues to collect characters until user inputs a carriage return
    message = raw_input(clientName)

    #If message is greater than 500 characters, report an error
    while (len(message) > 500):
        print '\nNotice: Messages must be 500 characters or less. Try again.\n'
        message = raw_input(clientName)

    #If \quit command entered in by chatclient user, end program.
    if (message == quit):
        #Send command for the Server side to end also
        clientSocket.send("\quit")
        print 'Connection ended by chatclient.'
        #Close Socket and Closes TCP Connection between the Client and Server
        #TCP in Client sends TCP message to TCP in Server
        clientSocket.close()
        sys.exit(0)

    #Send Message from Client to Server
    message = clientName+message+"\n"
    clientSocket.send(message)

    #Get Response Message from Server to Client
    responseMessage = clientSocket.recv(1024)

    #Display Response Message from Server to Client
    print(responseMessage),

    #If \quit command sent from Server, end program
    if quit in responseMessage:
        print 'Connection ended by chatserve.'
        #Close Socket and Closes TCP Connection between the Client and Server
        #TCP in Client sends TCP message to TCP in Server
        clientSocket.close()
        sys.exit(0)
