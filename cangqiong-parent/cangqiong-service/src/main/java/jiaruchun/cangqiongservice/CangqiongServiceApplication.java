package jiaruchun.cangqiongservice;

import jiaruchun.cangqiongservice.config.JwtConfiguration;
import jiaruchun.cangqiongservice.config.OssConfiguration;
import jiaruchun.cangqiongservice.config.WeChatConfiguration;
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
@EnableConfigurationProperties({OssConfiguration.class, JwtConfiguration.class, WeChatConfiguration.class})
public class CangqiongServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CangqiongServiceApplication.class, args);
    }

}
