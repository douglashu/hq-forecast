package com.hq.ellie.web.controller;
import com.hq.ellie.dao.generate.DemoMapper;
import com.hq.ellie.entity.generate.DemoExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Zale on 16/10/9.
 */
@RestController
@RequestMapping("/demo")
public class DemoController extends BaseController {
//    @Autowired
//    private DemoMapper demoMapper;
//
//    @RequestMapping(path="test",method = RequestMethod.GET)
//    public ResponseEntity<String> test(){
//        DemoExample example =new DemoExample();
//        demoMapper.selectByExample(example);
//        return this.getJSONResp("Hello", HttpStatus.OK);
//    }

}
