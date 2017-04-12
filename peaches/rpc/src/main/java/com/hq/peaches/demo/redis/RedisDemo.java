package com.hq.peaches.demo.redis;

import java.util.List;
import java.util.Set;

import com.hq.scrati.cache.redis.RedisCacheDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


/**
 * Created by Zale on 16/10/9.
 */
@Service
public class RedisDemo {
    @Autowired
    @Qualifier("JSONRedisCache")
    private RedisCacheDao jsonRedisCacheDao;   //以json格式存取
    @Autowired
    @Qualifier("redisCache")
    private RedisCacheDao redisCacheDao;      //以java原生序列化方式存取和json存取方式取其一,以原生序列化方式,实体类需要自动生成唯一的序列ID
    /**
     * 存取的demo
     * @Author Zale
     * @Date 16/10/9 下午3:13
     *
     */
    public void saveDemo(){
        Model model = new Model();
        model.setName("张三");
        model.setAge(13);
        jsonRedisCacheDao.save(model.getName(),model);
        redisCacheDao.save(model.getName(),model);
        //存入有效期的值
        jsonRedisCacheDao.save(model.getName(),model,60L); //60秒后过期
        redisCacheDao.save(model.getName(),model,50L);     //50秒后过期

        jsonRedisCacheDao.pushLisItem("modelList",model);  //存list
        redisCacheDao.pushLisItem("modelList",model);

        jsonRedisCacheDao.pushSetItem("modelSet",model);  //存set
        redisCacheDao.pushSetItem("modelSet",model);
    }
    /**
     *
     * 获取的demo
     * @Author Zale
     * @Date 16/10/9 下午3:13
     *
     */
    public void getDemo(){
        Model jsonModel = jsonRedisCacheDao.read("张三",Model.class);
        Model  model= redisCacheDao.read("张三",Model.class);
        //读list
        List<Model> modelList = jsonRedisCacheDao.readList("modelList", Model.class);
        modelList = redisCacheDao.readList("modelList", Model.class);

        //读set
        Set<Model> modelSet = jsonRedisCacheDao.readSet("modelSet", Model.class);
        modelSet = redisCacheDao.readSet("modelSet", Model.class);

    }
    /**
     * 删除的demo
     * @Author Zale
     * @Date 16/10/9 下午3:12
     *
     */
    public void delDemo(){
        jsonRedisCacheDao.delete("张三",Model.class);
        redisCacheDao.delete("张三",Model.class);

        //删除list中的元素
        Model model = new Model();
        model.setName("张三");
        model.setAge(13);
        jsonRedisCacheDao.removeListItem("modelList",model);
        redisCacheDao.removeListItem("modelList",model);

        //删除set中的元素
        jsonRedisCacheDao.removeSetItem("modelSet",model);
        redisCacheDao.removeSetItem("modelSet",model);
    }
    /**
     *
     * 该方法用于多线程自增,该操作为原子操作,返回自增后的值
     * @Author Zale
     * @Date 16/10/9 下午3:11
     *
     */
    public void increase(){
        redisCacheDao.incr("uid"); //调用一次步长为1
        redisCacheDao.incrBy("uid",2L); //调用一次步长为22


    }
}
