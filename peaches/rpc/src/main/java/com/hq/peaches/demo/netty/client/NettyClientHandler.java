package com.hq.peaches.demo.netty.client;

import org.apache.log4j.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

@SuppressWarnings("deprecation")
public class NettyClientHandler extends ChannelInboundHandlerAdapter{
	private static final Logger logger = Logger.getLogger(NettyClientHandler.class);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		if(msg instanceof ByteBuf){
			ByteBuf buf = (ByteBuf)msg;
			byte[] dst = new byte[buf.capacity()];
			buf.readBytes(dst);
			logger.info("client接收到服务器返回的消息:"+new String(dst));
			ReferenceCountUtil.release(msg);
		}else{
			logger.warn("error object");
		}
		
	}

}
