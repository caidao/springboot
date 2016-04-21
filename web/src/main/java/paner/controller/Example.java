package paner.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import paner.das.mapper.UserMapper;

import javax.annotation.Resource;

/**
 * Created by pan on 16/2/25.
 */

@RestController
public class Example {

     Logger logger = LoggerFactory.getLogger(Example.class);

    @Resource
    private UserMapper userMapper;

    @RequestMapping("/")
    String home() {
        logger.info("启动成功");
        return userMapper.getUser(45133);
    }
}
