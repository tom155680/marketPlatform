package cn.top.domain.strategy.service.armory;

import cn.top.domain.strategy.model.StrategyAwardEntity;
import cn.top.domain.strategy.repository.IStrategyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author yongjianWang
 * @description: 抽奖策略装配实现类
 * @date 2024年03月03日 18:55
 */
@Slf4j
@Service
public class StrategyArmory implements IStrategyArmory{

    @Resource
    private IStrategyRepository strategyRepository;

    @Override
    public void assembleLotteryStrategy(Long strategyId) {
        // 查询策略配置信息
        List<StrategyAwardEntity> strategyAwardEntities = strategyRepository.queryStrategyAwardList(strategyId);

        // 获取最小概率值
        BigDecimal minAwardRate = strategyAwardEntities.stream()
                .map(StrategyAwardEntity::getAwardRate)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);

        // 获取概率总和
        BigDecimal totalAwardSum = strategyAwardEntities.stream()
                .map(StrategyAwardEntity::getAwardRate)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 用 1 % 0.0001 获取概率范围, 百分位还是千分位等等

        BigDecimal rateRange = totalAwardSum.divide(minAwardRate, 0, RoundingMode.CEILING);

        // 循环填充奖品数据
        ArrayList<Integer> strategyAwardSearchTable = new ArrayList<>(rateRange.intValue());
        for (StrategyAwardEntity strategyAwardEntity: strategyAwardEntities){
            Integer awardId = strategyAwardEntity.getAwardId();
            BigDecimal awardRate = strategyAwardEntity.getAwardRate();
            // 计算这个概率占用多少个空间位置并填充
            for (int i = 0; i<rateRange.multiply(awardRate).setScale(0, RoundingMode.CEILING).intValue();i++){
                strategyAwardSearchTable.add(awardId);
            }
        }
        Collections.shuffle(strategyAwardSearchTable);


        HashMap<Integer, Integer> shuffleAwardRateTable = new HashMap<>();
        for (int i = 0;i<strategyAwardSearchTable.size();i++){
            shuffleAwardRateTable.put(i,strategyAwardSearchTable.get(i));
        }

        //存储到redis中
        strategyRepository.saveRedis(strategyId,rateRange,shuffleAwardRateTable);
    }

    @Override
    public Integer getRandomAwardId(Long strategyId) {
        Integer rateRange = strategyRepository.getRateRange(strategyId);
        return strategyRepository.getStrategyAwardAssemble(strategyId, new SecureRandom().nextInt(rateRange));
    }
}
