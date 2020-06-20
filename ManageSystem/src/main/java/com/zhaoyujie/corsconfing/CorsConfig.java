package com.zhaoyujie.corsconfing;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 全局跨域配置
 * 
 * @author zhaoyujie
 *
 */
@Service
public class CorsConfig {

	private CorsConfiguration buildConfig() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("*");  // 允许所有域名进行跨域调用。若是需要限制域名，则可以自己设置允许的 域名。  
		configuration.addAllowedHeader("*");  // 放行全部原始头信息  
		configuration.addAllowedMethod("*");  // 允许所有请求方法跨域调用  
		return configuration;
	}

	@Bean
	public CorsFilter corsFilter() {
	    // 注册 CORS 过滤器 
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", buildConfig());
		return new CorsFilter(source);
	}

}