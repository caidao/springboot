package paner.swagger;

import com.mangofactory.swagger.plugin.EnableSwagger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created by Administrator on 2016/10/25.
 */

@EnableWebMvc
@EnableSwagger2
@ComponentScan(basePackages = {"paner.controller"})
@Configuration
public class ApplicationSwaggerConfig {

    @Bean
    public Docket addUserDocket(){
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        ApiInfo apiInfo = new ApiInfo(
                "restfule API",
                "API Document管理",
                "V3.8.0",
                "www.baidu.com",
                "panyiwen2009@gmail.com",
                "测试连接",
                "这又是啥"
        );
        docket.apiInfo(apiInfo).select().
                apis(RequestHandlerSelectors.basePackage("paner.controller"))
                .paths(PathSelectors.any()).build();
        return docket;
    }
}
