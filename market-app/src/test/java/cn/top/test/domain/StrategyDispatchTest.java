package cn.top.test.domain;

import cn.top.domain.strategy.service.armory.IStrategyDispatch;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author yongjianWang
 * @description: 策略调度测试
 * @date 2024年03月04日 21:19
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyDispatchTest {

    @Resource
    private IStrategyDispatch strategyDispatch;

    @Test
    public void testDispatch(){
        log.info("随机抽奖结果: {}", strategyDispatch.getRandomAwardId(100001L));
        log.info("随机抽奖结果: {}", strategyDispatch.getRandomAwardId(100001L));
        log.info("随机抽奖结果: {}", strategyDispatch.getRandomAwardId(100001L));
    }

    @Test
    public void testDispatchWithWeight(){
        log.info("随机抽奖结果: {} - 权重4000", strategyDispatch.getRandomAwardId(100001L,"4000"));
        log.info("随机抽奖结果: {} - 权重4000", strategyDispatch.getRandomAwardId(100001L,"4000"));
        log.info("随机抽奖结果: {} - 权重4000", strategyDispatch.getRandomAwardId(100001L,"4000"));
        log.info("随机抽奖结果: {} - 权重4000", strategyDispatch.getRandomAwardId(100001L,"4000"));
        log.info("随机抽奖结果: {} - 权重5000", strategyDispatch.getRandomAwardId(100001L,"5000"));
        log.info("随机抽奖结果: {} - 权重5000", strategyDispatch.getRandomAwardId(100001L,"5000"));
        log.info("随机抽奖结果: {} - 权重5000", strategyDispatch.getRandomAwardId(100001L,"5000"));
        log.info("随机抽奖结果: {} - 权重5000", strategyDispatch.getRandomAwardId(100001L,"5000"));
        log.info("随机抽奖结果: {} - 权重5000", strategyDispatch.getRandomAwardId(100001L,"5000"));
        log.info("随机抽奖结果: {} - 权重5000", strategyDispatch.getRandomAwardId(100001L,"5000"));
        log.info("随机抽奖结果: {} - 权重5000", strategyDispatch.getRandomAwardId(100001L,"5000"));
//        log.info("随机抽奖结果: {} - 权重6000", strategyDispatch.getRandomAwardId(100001L,"6000:102,103,104,105,106,107,108,109"));
    }
}
