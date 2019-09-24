package io.renren.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;


@Configuration
public class ResourcesConfig extends WebMvcConfigurationSupport {
    @Value("${img.location}")
    private String uploadPath;
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/statics/**").addResourceLocations("classpath:/statics/");
        registry.addResourceHandler("/upload/**").addResourceLocations("file:"+uploadPath);
        registry.addResourceHandler("/public/**").addResourceLocations("classpath:/public/");



        super.addResourceHandlers(registry);
    }
}
