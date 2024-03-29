package com.huachu.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.huachu.api.filter.AuthInterceptor;

/**
 * @author Administrator
 * @DATE 2018/8/17
 */
@Configuration
public class AuthWebAppConfigurer extends WebMvcConfigurerAdapter {

    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authInterceptor()).addPathPatterns("/**")
				.excludePathPatterns("/login")
                .excludePathPatterns("/logout")
                .excludePathPatterns("/testMQ")
		        .excludePathPatterns("/swagger-resources/**","/v2/**","/swagger-ui.html/**","/webjars/**");
        super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("http://localhost:8080","http://back.zepone.com", "http://47.104.20.112")
			.allowedMethods("*")
			.allowedHeaders("Origin, X-Requested-With, Content-Type, Accept, x-auth-token")
			.allowCredentials(true);
	}
}
