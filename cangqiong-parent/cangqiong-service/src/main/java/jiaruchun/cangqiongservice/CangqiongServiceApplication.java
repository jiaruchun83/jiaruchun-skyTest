package jiaruchun.cangqiongservice;

import jiaruchun.common.properties.JwtProperties;
import jiaruchun.common.properties.OssProperties;
import jiaruchun.common.properties.WeChatProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@ServletComponentScan
@SpringBootApplication
@EnableCaching
@MapperScan
@EnableWebSocket
@EnableScheduling
@EnableTransactionManagement
@EnableConfigurationProperties({OssProperties.class, JwtProperties.class, WeChatProperties.class})
public class CangqiongServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CangqiongServiceApplication.class, args);
    }

}
