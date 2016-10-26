package paner.swagger;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * Created by Administrator on 2016/10/25.
 */
public class ApplicationInitializer implements WebApplicationInitializer{

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
//        ServletRegistration.Dynamic registration = servletContext.addServlet("dispatcher",new DispatcherServlet());
//        registration.setInitParameter("contextConfigLocation","classpath:spring-web.xml");
//        registration.setLoadOnStartup(1);
//        registration.addMapping("/*");
    }
}
