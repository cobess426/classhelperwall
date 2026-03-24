package com.example.classhelperwall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan("com.example.classhelperwall.mapper")
public class MyBatisConfig {
}