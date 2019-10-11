package com.yb.base.config;

import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringBootConfiguration;

/**
 * Created by mayn on 2019/7/24.
 */
@SpringBootConfiguration
@MapperScan(basePackages = "com.yb.mapper")
public class SpringDataconfig {
    //配置mybatis-plus分页拦截器
    public PaginationInterceptor getPaginationInterceptor(){
        PaginationInterceptor interceptor = new PaginationInterceptor();
        return interceptor;
    }
}
