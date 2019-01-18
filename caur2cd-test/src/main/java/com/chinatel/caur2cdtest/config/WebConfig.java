package com.chinatel.caur2cdtest.config;


import com.chinatel.caur2cdtest.filter.HttpTraceLogFilter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    /*
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new HttpInterceptor()).addPathPatterns("/api");
    }
    */
    /*

    @Bean
    @ConditionalOnMissingBean(HttpTraceRepository.class)
    public RemoteHttpTraceRepository traceRepository() {
        return new RemoteHttpTraceRepository();
    }
     */

    @Bean
    public HttpTraceLogFilter httpTraceLogFilter(MeterRegistry registry) {
        return new HttpTraceLogFilter(registry);
    }

}
