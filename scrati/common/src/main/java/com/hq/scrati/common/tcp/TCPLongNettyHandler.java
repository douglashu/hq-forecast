package com.hq.scrati.common.tcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author zhouyang
 *
 */
public class TCPLongNettyHandler extends SimpleChannelInboundHandler<String> {
	
	private static Logger logger = LoggerFactory.getLogger(TCPLongNettyHandler.class);
	
	public static boolean isHeard = false;
	
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case WRITER_IDLE:
                	ctx.writeAndFlush(new byte[]{(byte) 48,(byte) 48,(byte) 48,(byte) 48});
                    break;
                default:
                    break;
            }
        }
    }

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, String msg)
			throws Exception {
				
	}


}
