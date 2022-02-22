package com.lwb.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author lwb
 * @date 2020/8/4 17:25
 * @Description TODO
 */
@Configuration
@EnableAutoConfiguration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 跨域支持
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT", "PATCH", "OPTIONS")
                .maxAge(3600 * 24);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**").order(2);
        registry.addInterceptor(new My2Interceptor()).addPathPatterns("/**").order(1);

    }

    /**
     * 统一输出风格
     * See {@link PropertyNamingStrategy.SnakeCaseStrategy} for details.
     * @param converters
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (int i = 0; i < converters.size(); i++) {
            if (converters.get(i) instanceof MappingJackson2HttpMessageConverter) {
                ObjectMapper objectMapper = new ObjectMapper();
                // 统一返回数据的输出风格
                //objectMapper.setPropertyNamingStrategy(new PropertyNamingStrategy.SnakeCaseStrategy());
//                objectMapper.setSerializationInclusion(JsonInclude.Include.USE_DEFAULTS);
//                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//                objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));
                //这个设置会覆盖字段注解
                //objectMapper.setDateFormat(new SimpleDateFormat(DEFAULT_DATE_FORMAT));
                MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
                converter.setObjectMapper(objectMapper);
                converters.set(i, converter);
                break;
            }
        }// end for
    }


}
