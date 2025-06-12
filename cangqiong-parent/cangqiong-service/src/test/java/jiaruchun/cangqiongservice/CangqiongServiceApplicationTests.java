package jiaruchun.cangqiongservice;

import jiaruchun.cangqiongservice.mapper.OrderDetailMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@Slf4j
@SpringBootTest
class CangqiongServiceApplicationTests {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Test
    public void redisTest(){
        
    }
}
