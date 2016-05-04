package paner.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import paner.das.mapper.UserMapper;
import paner.service.LoginService;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016/4/21.
 */
@RestController
public class LoginController {

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Resource
    private LoginService loginService;

    @RequestMapping("/login")
    public  String login(String code,String state) {

        return loginService.login(code,state);
    }
}
