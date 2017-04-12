package com.hq.scrati.common.tcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TCPLongNettyClient{
	
	private static Logger logger = LoggerFactory.getLogger(TCPLongNettyClient.class);
	
    public SocketChannel socketChannel;
    
    private Bootstrap bootstrap;
    
    private EventLoopGroup eventLoopGroup;
    
    private String host;
    
    private int port;
    
	public void createClient( final String host,final int port) throws InterruptedException {
		eventLoopGroup=new NioEventLoopGroup();
        bootstrap=new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE,true);
        bootstrap.option(ChannelOption.SO_REUSEADDR,true);
        bootstrap.group(eventLoopGroup);
        bootstrap.remoteAddress(host,port);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
            	socketChannel.pipeline().addFirst(new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                        super.channelInactive(ctx);
                        ctx.channel().eventLoop().schedule(new Runnable() {
							@Override
	                        public void run() {
	                        	
	    						try {
	    							eventLoopGroup.shutdownGracefully();
									Thread.sleep(30L);
									TCPLongNettyClientMap.remove(host+"_"+port);
		    						TCPLongNettyClient tcpLongNettyClient = new TCPLongNettyClient();
		    						tcpLongNettyClient.getClinet(host+"_"+port);
								} catch (InterruptedException e) {
									logger.error("",e);
								}
	    						
	                        }
	                    }, 1, TimeUnit.SECONDS);
                    }
                });
                socketChannel.pipeline().addLast(new IdleStateHandler(20,10,0));
                socketChannel.pipeline().addLast(new ByteArrayEncoder());
                socketChannel.pipeline().addLast(new TCPLongNettyHandler());
            }
        });
        
        
        ChannelFuture future =bootstrap.connect(host,port).sync();
        if (future.isSuccess()) {
            socketChannel = (SocketChannel)future.channel();
            logger.info("客户端创建成功,IP为:"+host+" 端口为:"+port);
        }
	}
	
	/**
	 * 根据Key获取Netty的长链接的客户端：TCPLongNettyClient
	 * @param key
	 * @return
	 */
	public TCPLongNettyClient getClinet(String key) {
		if(TCPLongNettyClientMap.containsKey(key)){
			return TCPLongNettyClientMap.get(key);
		}else{
			host = key.substring(0, key.indexOf("_"));
			port = Integer.parseInt(key.substring(key.indexOf("_")+1));
			TCPLongNettyClient client = new TCPLongNettyClient();
			try {
				client.createClient(host,port);
			} catch (InterruptedException e) {
				logger.info("create netty long client is InterruptedException,ip:" + host+" port:"+port);
			};  
	        TCPLongNettyClientMap.add(key, client);
	        return client;
		}
	}
}
