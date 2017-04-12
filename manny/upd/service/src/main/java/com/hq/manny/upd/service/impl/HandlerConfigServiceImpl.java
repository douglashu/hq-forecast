package com.hq.manny.upd.service.impl;

import com.hq.manny.upd.dao.mongodb.HandlerConfigMongoRepo;
import com.hq.manny.upd.entity.mongodb.HandlerConfig;
import com.hq.manny.upd.service.HandlerConfigService;
import com.hq.manny.upd.service.model.RegisterRequest;
import com.hq.manny.upd.service.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Zale on 2016/11/23.
 */
@Service
public class HandlerConfigServiceImpl implements HandlerConfigService{
    @Autowired
    private HandlerConfigMongoRepo handlerConfigMongoRepo;
    @Override
    public Result register(RegisterRequest registerRequest,String ip) {
        Result rst = new Result();
        try {
            HandlerConfig hc = new HandlerConfig();
            hc.setType(registerRequest.getType());
            Example<HandlerConfig> example = Example.of(hc);
            HandlerConfig dbHC = handlerConfigMongoRepo.findOne(example);
            if (dbHC == null) {
                hc.setCode(registerRequest.getCode());
                hc.setVersion(registerRequest.getVersion());

                Set<String> hosts = new HashSet<>();
                hosts.add(ip + ":" + registerRequest.getPort());
                hc.setHandlers(hosts);
                handlerConfigMongoRepo.insert(hc);

            }else{
                if(!dbHC.getCode().equals(registerRequest.getCode())||!dbHC.getVersion().equals(registerRequest.getVersion())){
                    throw new RuntimeException("注册冲突,同一个type下不能注册不同的服务");
                }
                if(!dbHC.getHandlers().contains(ip + ":" + registerRequest.getPort())) {
                    dbHC.getHandlers().add(ip + ":" + registerRequest.getPort());
                    handlerConfigMongoRepo.save(dbHC);
                }
            }
            rst.setCode("0000");
            rst.setMessage("注册成功");
        }catch (Exception e){
            rst.setCode("9999");
            rst.setMessage(e.getMessage());
        }
        return rst;
    }

    @Override
    public HandlerConfig getHandlerByType(String type) {
        HandlerConfig hc = new HandlerConfig();
        hc.setType(type);
        Example<HandlerConfig> example = Example.of(hc);
        HandlerConfig dbHC = handlerConfigMongoRepo.findOne(example);
        return dbHC;
    }

}
