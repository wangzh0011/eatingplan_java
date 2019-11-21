package io.renren;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@ServletComponentScan
@SpringBootApplication
@MapperScan(basePackages = {"io.renren.modules.*.dao"})
public class AdminApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(AdminApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
