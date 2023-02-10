package com.example.collectiontableadministration.config;

import com.example.collectiontableadministration.interceptor.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Web配置类
 * @author 杨子涵
 */
@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {
	/**
	 * 添加Web项目的拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		log.info("拦截器加载......");
		// 对所有访问路径，都通过MyInterceptor类型的拦截器进行拦截
		registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**")
		.excludePathPatterns("/login/login").excludePathPatterns("/userService/user/downloadUserExcel")
		.excludePathPatterns("/collectionTableService/collectionTable/userApplyForUpdateCollectionTableAdmin");
	}
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		log.info("扩展消息转换器...");
		//创建消息转换器对象
		MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
		//设置对象转换器，底层使用Jackson将Java对象转为json
		messageConverter.setObjectMapper(new JacksonObjectMapper());
		//将上面的消息转换器对象追加到mvc框架的转换器集合中
		converters.add(0,messageConverter);
	}
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				// 是否发送Cookie
				.allowCredentials(true)
				// 放行哪些原始域
				.allowedOriginPatterns("*")
				// 放行哪些请求方式
				.allowedMethods("GET", "POST", "PUT", "DELETE")
				// 放行哪些原始请求头部信息
				.allowedHeaders("*")
				// 暴露哪些头部信息
				.exposedHeaders("*");
	}
}
