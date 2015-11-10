Author: Larissa Hahn
Last Modification Date: October 28, 2015
Program Description: Chat Client and Chat Server -- TCP connection

---------------------------------------------------------------------------
README.txt
---------------------------------------------------------------------------

COMPILE instructions:
     After uploading all files to a directory on the OSU server, navigate
     to that directory and then enter this command:
          makefile

EXECUTE instructions:
     On Host A (for example, "flip1" server) enter:
          java chatserve <Port Number>
     On Host B (for example, "flip2" server) enter:
          python chatclient.py <Host A> <Host A's Port Number>

EXAMPLE execution:
     HOST A (flip1):
          makefile
          java chatserve 30333
     HOST B (flip2):
          python chatclient.py flip1 30333

CONTROL instructions:
     HOST A (chatserve):
          Enter a handle, then wait for a connection from the chatclient.
     HOST B (chatclient):
          Enter a handle, then send a message to the chatserve.
     HOST A (chatserve):
          Read message from chatclient, then send a reply.
     ** Continue to reply on HOST A and HOST B for the duration of the
     conversation or until either the chatclient or chatserve closes the
     connection by typing in the '\quit' command.
     *** After the connection has been closed:
     HOST A (chatserve):
          Continues to wait for a new connection from the chatclient until
          either a connection is accepted or a SIGINT is received. (Ctrl-C)


---------------------------------------------------------------------------
EXAMPLE TEST RUN: HOST A (chatserve) Console
---------------------------------------------------------------------------
java chatserve 30333

Welcome to chatserve.
Port 30333: chatserve now listening for connection.

Enter a chatserve handle: Bob

Connection with chatclient established.
Use \quit command to end connection.

Sally> Hey
Bob> How are you?
Sally> I'm great, thanks! So glad to talk to you again!
Bob> Me too.
Sally> How are you, Bob?
Bob> Good, good. :)
Sally> Awesome. Well, what do you want to do Sunday?
Bob> idk. watch football?
Sally> oh, okay...
Bob> you don't like football?
Connection ended by chatclient.
Port 30333: chatserve now listening for new connection.


---------------------------------------------------------------------------
EXAMPLE TEST RUN: HOST B (chatclient) Console
---------------------------------------------------------------------------
python chatclient.py flip1 30333

Welcome to chatclient.
Connection with chatserve established.
Use \quit command to end connection.

Enter a chatclient handle: Sally

Sally> Hey
Bob> How are you?
Sally> I'm great, thanks! So glad to talk to you again!
Bob> Me too.
Sally> How are you, Bob?
Bob> Good, good. :)
Sally> Awesome. Well, what do you want to do Sunday?
Bob> idk. watch football?
Sally> oh, okay...
Bob> you don't like football?
Sally> \quit
Connection ended by chatclient.
