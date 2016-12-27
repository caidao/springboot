package paner.controller;


import com.wordnik.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.*;
import paner.das.mapper.TestMapper;
import paner.das.mapper.UserMapper;
import paner.das.strong.MapperModel;
import paner.das.strong.MapperRefresh;
import paner.model.QQRespModel;

import javax.annotation.Resource;
import java.net.URLDecoder;

/**
 * Created by pan on 16/2/25.
 */

@RestController
@RequestMapping(value = "/example")
@Api(value = "/example",description = "test api")
public class Example {

     Logger logger = LoggerFactory.getLogger(Example.class);

    @Resource
    private TestMapper testMapper;
    @Resource
    private MapperRefresh mapperRefresh;

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    @ApiOperation(notes="获取用户信息",httpMethod = "GET",value = "获取用户信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of user detail"),
            @ApiResponse(code = 404, message = "User with given username does not exist"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public String home() {
        logger.info("启动成功");
        return "启动成功1"+testMapper.getUser(33031695);
    }

    @RequestMapping(value = "/test2",method = RequestMethod.POST)
    public String test2(@RequestBody MapperModel mapperModel) {
        logger.info("启动成功");

        try {
            String content = URLDecoder.decode(mapperModel.getMapperContent(),"utf-8");
            mapperRefresh.refresh(mapperModel.getClassName(),content);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return "启动成功2："+mapperModel.getClassName();
    }

    @ApiOperation(value = "post测试")
    @RequestMapping(value = "/test3",method = RequestMethod.POST)
    @ApiImplicitParams({
            @io.swagger.annotations.ApiImplicitParam(name="name",value = "用户名",required = false,
                    dataType = "string",paramType = "query",defaultValue = "yiwen.pan")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful retrieval of user detail",response = QQRespModel.class),
            @ApiResponse(code = 404, message = "User with given username does not exist"),
            @ApiResponse(code = 500, message = "Internal server error")})
    public Object test3(@RequestBody QQRespModel model,@RequestParam(value="name", defaultValue="World")String name){
        logger.info("启动成功");
        return model;
    }

}
