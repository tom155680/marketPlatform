package cn.top.test.domain;

import cn.top.domain.strategy.service.armory.IStrategyArmory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author yongjianWang
 * @description: 策略奖品装配测试类
 * @date 2024年03月03日 20:03
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyArmoryTest {

    @Resource
    private IStrategyArmory strategyArmory;

    @Test
    public void testAssemble(){
        strategyArmory.assembleLotteryStrategy(100001L);
    }

    @Test
    public void testAssembleRandomAwardVal(){
        log.info("随机抽奖结果: {}", strategyArmory.getRandomAwardId(100001L));
        log.info("随机抽奖结果: {}", strategyArmory.getRandomAwardId(100001L));
        log.info("随机抽奖结果: {}", strategyArmory.getRandomAwardId(100001L));

    }
}
