package org.lgp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * https://blog.csdn.net/H_Sino/article/details/138891129?spm=1001.2101.3001.6650.2&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EYuanLiJiHua%7ECtr-2-138891129-blog-109406093.235%5Ev43%5Epc_blog_bottom_relevance_base3&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EYuanLiJiHua%7ECtr-2-138891129-blog-109406093.235%5Ev43%5Epc_blog_bottom_relevance_base3&utm_relevant_index=5
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    public TraceInterceptor initTraceInterceptor() {
        return new TraceInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(initTraceInterceptor()).addPathPatterns("/**");
    }
}
