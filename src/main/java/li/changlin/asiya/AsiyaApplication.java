package li.changlin.asiya;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class AsiyaApplication extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/uploadFiles/*").addResourceLocations("file:E:/迅雷下载/project/x1hn1k/asiya/uploadFiles/");
            registry.addResourceHandler("/uploadIcon/*").addResourceLocations("file:E:/迅雷下载/project/x1hn1k/asiya/uploadIcon/");
    }
    public static void main(String[] args) {
        SpringApplication.run(AsiyaApplication.class, args);
    }

}
