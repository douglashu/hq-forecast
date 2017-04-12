package com.hq.buck.rpc.dubbo.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hq.buck.rpc.dubbo.InAppDubboService;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.framework.service.DubboBaseService;
import com.hq.scrati.model.HqRequest;
import org.apache.zookeeper.*;
import java.util.List;

/**
 * Created by zhaoyang on 03/01/2017.
 */
@Service(interfaceName = "com.hq.buck.rpc.dubbo.InAppDubboService", version = "1.0")
public class InAppDubboServiceImpl extends DubboBaseService implements InAppDubboService {

    @Override
    public RespEntity getMyInApps(HqRequest request) {
        return null;
    }

    @Override
    public RespEntity getMyInAppCats(HqRequest request) {
        return null;
    }

    @Override
    public RespEntity updateMyInApps(HqRequest request) {
        return null;
    }

    public static void main(String[] args) {
        try {
            ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 1000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    System.out.println(watchedEvent.getType().name());
                }
            });
            List<String> rootNodes = zooKeeper.getChildren("/", true);

            zooKeeper.create("/hello", "hello world".getBytes()
                    , ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
            System.out.println(rootNodes);

            int i=0;
            while (true) {
                Thread.sleep(1000);
                i++;
                System.out.println(i);
                if (i > 8) break;
            }
            System.out.println("end");
            // zooKeeper.create("/hello", "hello world".getBytes(), null, CreateMode.EPHEMERAL);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

}
