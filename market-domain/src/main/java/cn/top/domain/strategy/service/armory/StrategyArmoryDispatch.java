package cn.top.domain.strategy.service.armory;

import cn.top.domain.strategy.model.StrategyAwardEntity;
import cn.top.domain.strategy.model.StrategyEntity;
import cn.top.domain.strategy.model.StrategyRuleEntity;
import cn.top.domain.strategy.repository.IStrategyRepository;
import cn.top.domain.strategy.repository.IStrategyRuleRepository;
import cn.top.types.common.Constants;
import cn.top.types.enums.ResponseCode;
import cn.top.types.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.*;

/**
 * @author yongjianWang
 * @description: 抽奖策略装配实现类
 * @date 2024年03月03日 18:55
 */
@Slf4j
@Service
public class StrategyArmoryDispatch implements IStrategyArmory, IStrategyDispatch{

    @Resource
    private IStrategyRepository strategyRepository;

    @Resource
    private IStrategyRuleRepository strategyRuleRepository;

    @Override
    public boolean assembleLotteryStrategy(Long strategyId) {
        // 查询策略配置信息
        List<StrategyAwardEntity> strategyAwardEntities = strategyRepository.queryStrategyAwardList(strategyId);

        assembleLotteryStrategy(String.valueOf(strategyId), strategyAwardEntities);

        // 权重策略配置, 适用于rule_weight权重规则配置
        StrategyEntity strategyEntity = strategyRepository.queryStrategyEntityByStrategyId(strategyId);
        if (null == strategyEntity.getRuleModels()) return true;

        String ruleWeightModel = strategyEntity.getRuleWeight();

        StrategyRuleEntity strategyRuleEntity = strategyRuleRepository.queryStrategyRule(strategyId, ruleWeightModel);

        if (null == strategyRuleEntity){
            throw new AppException(ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL.getCode(),ResponseCode.STRATEGY_RULE_WEIGHT_IS_NULL.getInfo());
        }

        Map<String, List<Integer>> ruleWeightValues = strategyRuleEntity.getRuleWeightValues();
        Set<String> keys = ruleWeightValues.keySet();
        // 装配带权重的策略规则
        for (String key:keys){
            List<Integer> ruleWeightValueList = ruleWeightValues.get(key);
            ArrayList<StrategyAwardEntity> strategyAwardEntitiesClone = new ArrayList<>(strategyAwardEntities);
            strategyAwardEntitiesClone.removeIf(entity -> !ruleWeightValueList.contains(entity.getAwardId()));
            assembleLotteryStrategy(String.valueOf(strategyId).concat("_").concat(key), strategyAwardEntitiesClone);
        }

        return true;
    }


    private void assembleLotteryStrategy(String key, List<StrategyAwardEntity> strategyAwardEntities){
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
        strategyRepository.saveRedis(key,rateRange,shuffleAwardRateTable);
    }

    @Override
    public Integer getRandomAwardId(Long strategyId) {
        Integer rateRange = strategyRepository.getRateRange(strategyId);
        return strategyRepository.getStrategyAwardAssemble(String.valueOf(strategyId), new SecureRandom().nextInt(rateRange));
    }

    @Override
    public Integer getRandomAwardId(Long strategyId, String ruleWeightValue) {
        String key = String.valueOf(strategyId).concat("_").concat(ruleWeightValue);
        Integer rateRange = strategyRepository.getRateRange(key);
        return strategyRepository.getStrategyAwardAssemble(key, new SecureRandom().nextInt(rateRange));
    }
}
