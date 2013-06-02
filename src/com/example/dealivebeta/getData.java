package com.example.dealivebeta;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import android.os.AsyncTask;

public class getData extends AsyncTask<String, Void, String>{

	
	private int id;
	private boolean flag;
	private ArrayList<String> serverResponse;
	PrintWriter out=null;
	BufferedReader in=null;
	
	
	
	
	public getData(int id,boolean flag,PrintWriter out,BufferedReader in){
		this.id=id;
		this.flag=flag;
		this.out=out;
		this.in=in;
		
		
	}
	@Override
	protected String doInBackground(String... Params) {
		 android.os.Debug.waitForDebugger();
    	String request = "";
    	serverResponse= new ArrayList<String>(); 
    	serverResponse.clear();
    	int i=0;
    	
    	if(flag){
    		request+="Store,";
    	}else{
    		request+="Deals,";
    		
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
		return null;
    	
		
	}
	

    private int receive() {//קלללל might rethink
    	
    	boolean done = false;                
                while (!done) {
                    try {
                    	System.out.println("Reading from Server\n");
                    	serverResponse.add(in.readLine());
                    	
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
 
                    if (!serverResponse.isEmpty()) {
						if (serverResponse.get(serverResponse.size()-1).equals("endOfResponse")) {
							serverResponse.remove(serverResponse.size()-1 );
							done = true;
							return 1;
						}
					}
                }	 
                return 0;
    }
    public void response(ArrayList<String> res){/*
    	String tmp = new String();
    	ArrayList<String> ret = new ArrayList<String>();
    	for(String i: serverResponse){
    		for(int c=0;c<i.length();c++){
    			tmp+=i.charAt(c);
    		}
    		ret.add(tmp);	
    	}*/
    	String tmp = new String();
    	for(int i=0; i<serverResponse.size();i+=7){
    		tmp="";
    		tmp+="Id: "+serverResponse.get(0+i);
    		tmp+="\nName: "+serverResponse.get(1+i);
    		tmp+="\nHours: "+serverResponse.get(2+i);
    		tmp+="\nMinutes: "+serverResponse.get(3+i);
    		tmp+="\nPrice: "+serverResponse.get(4+i);
    		tmp+="\nPlace: "+serverResponse.get(5+i);
    		tmp+="\nId_s: "+serverResponse.get(6+i);
    			res.add(tmp);
    	}
    }

}
