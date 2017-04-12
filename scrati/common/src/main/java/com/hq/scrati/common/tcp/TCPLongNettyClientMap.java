package com.hq.scrati.common.tcp;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhouyang.
 */
public class TCPLongNettyClientMap {
    private static Map<String,TCPLongNettyClient> map=new ConcurrentHashMap<String, TCPLongNettyClient>();
    public static void add(String clientId,TCPLongNettyClient tcpLongNettyClient){
        map.put(clientId,tcpLongNettyClient);
    }
    public static TCPLongNettyClient get(String key){
        return map.get(key);
    }
    public static void remove(String key){
    	map.remove(key);
    }
    
    public static boolean containsKey(String key){
    	return map.containsKey(key);
    }

}
