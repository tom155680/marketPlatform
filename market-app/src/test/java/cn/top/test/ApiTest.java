package cn.top.test;

import cn.top.infrastructure.persistent.redis.IRedisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RMap;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {

    @Resource
    private IRedisService redisService;

    @Test
    public void test() {
        RMap<Object, Object> strategyMap = redisService.getMap("strategy001");
        strategyMap.put(1,101);
        strategyMap.put(2,102);
        log.info("测试结果: {}", redisService.getFromMap("strategy001", 2).toString());
    }

}
