package com.example.collectiontableadministration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 杨子涵 springBoot启动类
 */
@SpringBootApplication
@MapperScan("com.example.collectiontableadministration.mapper")
public class CollectionTableAdministrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(CollectionTableAdministrationApplication.class, args);
    }

}
