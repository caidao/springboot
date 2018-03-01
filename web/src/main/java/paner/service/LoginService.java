package paner.service;

import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import paner.das.entity.QQUserinfoModel;
import paner.das.mapper.UserMapper;
import paner.util.ConqqUtil;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2016/4/26.
 */
@Service
public class LoginService {

    @Resource
    private UserMapper userMapper;

    public String login(String code,String state){

        String userinfo =  ConqqUtil.getQqInfo(code,state);
        String ret =null;
        try {
            QQUserinfoModel model = new Gson().fromJson(userinfo,QQUserinfoModel.class);
            //将用户信息插入数据库
            userMapper.addQQUserinfo(model);
            ret = model.getNickname();
        }catch (Exception e){
            ret= e.getMessage();
        }
        return ret;
    }
}
