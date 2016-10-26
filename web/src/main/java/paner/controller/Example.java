package paner.controller;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import paner.das.mapper.UserMapper;

import javax.annotation.Resource;

/**
 * Created by pan on 16/2/25.
 */

@RestController
@RequestMapping(value = "/example")
@Api(value = "测试api",description = "测试api")
public class Example {

     Logger logger = LoggerFactory.getLogger(Example.class);

    @Resource
    private UserMapper userMapper;

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    @ApiOperation(notes="获取用户信息",httpMethod = "GET",value = "获取用户信息")
    public String home() {
        logger.info("启动成功");
        return userMapper.getUser(45133);
    }

    @RequestMapping(value = "/test2",method = RequestMethod.GET)
    public String test2() {
        logger.info("启动成功");
        return userMapper.getUser(45133);
    }
}
