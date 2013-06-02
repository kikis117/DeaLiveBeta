package com.example.dealivebeta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import android.os.AsyncTask;


	 
public  class connectServer extends AsyncTask<String, Void, String>  {

	 
	    public static String SERVERIP;
	    public static final int SERVERPORT = 20000;
	    private Socket socket;
	   
	    PrintWriter out;
	    BufferedReader in;
	    boolean error=false;
	    
	    
	    
	   

	    public connectServer(String serverIp) {
	    	SERVERIP=serverIp;        
	    }
	    
	   
		@Override
		protected String doInBackground(String... params) {
			android.os.Debug.waitForDebugger();
	        try {
				connectToServer();
			} catch (IOException e) {
				System.out.printf("Socket error");
				e.printStackTrace();
				error=true; //προσοχη!!! μετά το construct να καλείται η stopClient αν true τοτε destruct του αντικειμένου και ξανά προσπάθησε ή γράφουμε μνμ λάθους με την επικοινωνία με τον σερβερ 
			 }
			return null;
		}

 

	 
	    public boolean stopClient(){
	    	if(out== null || in== null)
	    		return true;
	        return error;
	    }
	    
	    private void connectToServer() throws IOException{
	    	android.os.Debug.waitForDebugger();
	    	InetAddress serverAddr;

				try {
					serverAddr = InetAddress.getByName(SERVERIP);
					socket = new Socket(serverAddr, SERVERPORT);
					//out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
					out=new PrintWriter(socket.getOutputStream(), true);
					in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					error= true;
					e.printStackTrace();		
					if(socket!= null)
							socket.close();					

				}
				if(out == null || in==null)
					error=true;
	    	
			
	    }
	    
	  /*  public  ArrayList<String> sendRequest (int id,boolean flag) {
	    	android.os.Debug.waitForDebugger();
	    /*	String request = null;
	    	serverResponse= new ArrayList<String>(); 
	    	serverResponse.clear();
	    	int i=0;
	    	if(flag){
	    		request+="Deals,";
	    	}else{
	    		request+="Store,";
	    	}
    		request+=Integer.toString(id);
    		
    		if (out != null && !out.checkError()) {
	            out.println(request);
	            out.flush();
	            i=receive();	            
	           }
    		
	    	if(i!=1){
	        	serverResponse.add("error");
	        }
	    	return serverResponse;*/
	    	
	    	/*getData data = new getData(id, flag,out,in);
	    	data.execute();
	    	return data.response();
	            
	    }
	    */



    }





	 

