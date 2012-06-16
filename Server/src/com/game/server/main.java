package com.game.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import com.google.gson.Gson;

public class main{

    // Declaration section:
    // declare a server socket and a client socket for the server
    // declare an input and an output stream
    
    static  Socket clientSocket = null;
    static  ServerSocket serverSocket = null;
    static int clientId=0;
    // This chat server can accept up to 10 clients' connections

    static  clientThread t[] = new clientThread[10];           
    
    public static void main(String args[]) {
    
	int port_number=2222;
	
	if (args.length < 1)
	    {
		System.out.println("Usage: java MultiThreadChatServer \n"+
				   "Now using port number="+port_number);
	    } else {
		port_number=Integer.valueOf(args[0]).intValue();
	    }
	
        try {
	    serverSocket = new ServerSocket(port_number);
        }
        catch (IOException e)
	    {System.out.println(e);}

	
	while(true){
	    try {
		clientSocket = serverSocket.accept();
		for(int i=0; i<=9; i++){
		    if(t[i]==null)
			{
		    	System.out.println("new client");
		    	clientId++;
			    (t[i] = new clientThread(clientSocket,t,clientId)).start();
			    break;
			}
		}
	    }
	    catch (IOException e) {
		System.out.println(e);}
	}
    }
} 

// This client thread opens the input and the output streams for a particular client,
// ask the client's name, informs all the clients currently connected to the 
// server about the fact that a new client has joined the chat room, 
// and as long as it receive data, echos that data back to all other clients.
// When the client leaves the chat room this thread informs also all the
// clients about that and terminates. 

class clientThread extends Thread{
    
    DataInputStream is = null;
    PrintStream os = null;
    Socket clientSocket = null;       
    clientThread t[]; 
    int id=0;
    Gson gson=new Gson();
    public clientThread(Socket clientSocket, clientThread[] t,int id){
	this.clientSocket=clientSocket;
        this.t=t;
        this.id=id;
    }
    
    public void run() 
    {
	String line;
        String name;
	try{
	    is = new DataInputStream(clientSocket.getInputStream());
	    os = new PrintStream(clientSocket.getOutputStream());
	    //os.println("Welcome to RoboCraft");
	    id++;
	    os.println("$"+id);
	    //os.println("Hello "+id+" to our chat room.\nTo leave enter /quit in a new line"); 
	    System.out.println(id+" entered room");
	    for(int i=0; i<=9; i++)
		if (t[i]!=null && t[i]!=this)  
		{
			//t[i].os.println("N:"+id );
		}		    
	    while (true) {
		line = is.readLine();
                if(line.startsWith("/quit")) break; 
		for(int i=0; i<=9; i++)
		{
			if (t[i]!=null)  
				{
				t[i].os.println(line); 
				}
		}
		    
	    }
	    for(int i=0; i<=9; i++)
		if (t[i]!=null && t[i]!=this)  
		    t[i].os.println("*** The user "+id+" is leaving the chat room !!! ***" );
	    
	    os.println("*** Bye "+id+" ***"); 

	    // Clean up:
	    // Set to null the current thread variable such that other client could
	    // be accepted by the server

	    for(int i=0; i<=9; i++)
		if (t[i]==this) t[i]=null;  
		
	    // close the output stream
	    // close the input stream
	    // close the socket
	    
	    is.close();
	    os.close();
	    clientSocket.close();
	}
	catch(IOException e){};
    }
}