package com.hq.scrati.common.tcp;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

public class TCPLongClient {
	
	private static Logger logger = Logger.getLogger(TCPLongClient.class);

	private static ConcurrentHashMap<String,TCPLongClient> tcpLongClientMap = new ConcurrentHashMap<String,TCPLongClient>();
	private String serverIp;  
    private int port; 
    private Socket socket;  
    private boolean running=false;  
    private long lastSendTime;
	
	public TCPLongClient(String serverIp, int port) {  
        this.serverIp=serverIp;
        this.port=port;  
    } 

	public static TCPLongClient getClinet(String key) {
		if(tcpLongClientMap.containsKey(key)){
			return tcpLongClientMap.get(key);
		}else{
			TCPLongClient client = new TCPLongClient(key.substring(0, key.indexOf("_")),Integer.parseInt(key.substring(key.indexOf("_")+1)));  
	        try {
	        	client.start();
			} catch (UnknownHostException e) {
				logger.error("Create Socket UnknownHostException",e);
			} catch (IOException e) {
				logger.error("Create Socket IOException",e);
			}
	        tcpLongClientMap.put(key, client);
	        return client;
		}
	}
	
	 public void start() throws UnknownHostException, IOException {  
        if(running){
        	return;  
        }
        socket = new Socket(serverIp,port);
        socket.setSoTimeout(0);
		socket.setReuseAddress(true);
        lastSendTime=System.currentTimeMillis();  
        running=true;  
        new Thread(new KeepAliveWatchDog()).start();
        new Thread(new ReceiveWatchDog()).start();
    }  
	      
    public void stopThread(){  
        if(running){
        	running=false;  
        }
    }
    
    public String send(String value,int timeout,String postfix){
    	String message = null;  
		PrintWriter out = null;
        BufferedReader line = null;
        StringBuilder returnParams = new StringBuilder();
    	try {
			out = new PrintWriter(socket.getOutputStream(), true);		
	        line = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(value.toString().getBytes("UTF-8"))));
	        message = line.readLine();
	        out.println(message);
	        out.flush();
    	} catch (IOException e) {
    		logger.error("Send message IOException",e);
		}
        return returnParams.toString();
    }  
    
    class KeepAliveWatchDog implements Runnable{  
        long checkDelay = 10;  
        long keepAliveDelay = 2000;  
        public void run() {  
            while(running){  
                if(System.currentTimeMillis()-lastSendTime>keepAliveDelay){
                    send(new KeepAlive().toString(),3000,":end");  
                    lastSendTime = System.currentTimeMillis();  
                }else{  
                    try {  
                        Thread.sleep(checkDelay);  
                    } catch (InterruptedException e) {
                    	logger.error("KeepAlive Socket InterruptedException",e);
                        stopThread();  
                    }  
                }  
            }  
        }  
    }

    
    class ReceiveWatchDog implements Runnable{  
        public void run() {  
            while(running){  
                try {  
                    InputStream in = socket.getInputStream();  
                    if(in.available()>0){  
                    	BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
            	        StringBuilder returnParams = new StringBuilder();
            	        String reline;
            	        while ((reline=bufferedReader.readLine()) != null) {  
            	            if (reline.indexOf(":end") != -1) {//遇到结束符就结束接收  
            	            	returnParams.append(reline); 
            	            	break;  
            	            }  
            	            returnParams.append(reline);  
            	         }
            	        logger.info(returnParams.toString());
                    }else{  
                        Thread.sleep(10);  
                    }  
                } catch (Exception e) {  
                	logger.error("Socket Receive Message Exception",e);
                    stopThread();  
                }   
            }  
        }  
    } 
      
    /** 
     * 维持连接的消息对象。 
     */  
    public class KeepAlive implements Serializable{  
      
        private static final long serialVersionUID = -2813120366138988480L;  
      
        /* 覆盖该方法，仅用于测试使用。 
         * 
         */  
        @Override  
        public String toString() {  
            return "{begin:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"\t"+":end}";  
        }  
      
    }  
}
