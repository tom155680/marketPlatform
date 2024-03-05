package cn.top.infrastructure.persistent.repository;

import cn.top.domain.strategy.model.StrategyAwardEntity;
import cn.top.domain.strategy.model.StrategyEntity;
import cn.top.domain.strategy.repository.IStrategyRepository;
import cn.top.infrastructure.persistent.dao.IStrategyAwardDao;
import cn.top.infrastructure.persistent.dao.IStrategyDao;
import cn.top.infrastructure.persistent.po.Strategy;
import cn.top.infrastructure.persistent.po.StrategyAward;
import cn.top.infrastructure.persistent.redis.IRedisService;
import cn.top.types.common.Constants;
import org.redisson.api.RMap;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yongjianWang
 * @description: 策略仓储实现
 * @date 2024年03月03日 18:57
 */
@Repository
public class StrategyRepository implements IStrategyRepository {

    @Resource
    private IStrategyDao strategyDao;

    @Resource
    private IStrategyAwardDao strategyAwardDao;

    @Resource
    private IRedisService redisService;

    @Override
    public List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId) {

        // 先从redis里查询
        String cacheKey = Constants.RedisKey.STRATEGY_AWARD_KEY + strategyId;
        List<StrategyAwardEntity> strategyAwardEntities = redisService.getValue(cacheKey);
        if (strategyAwardEntities != null && !strategyAwardEntities.isEmpty()){
            return strategyAwardEntities;
        }

        // 从库中查询数据
        List<StrategyAward> strategyAwardList = strategyAwardDao.queryListByStrategyId(strategyId);
        strategyAwardEntities = new ArrayList<>(strategyAwardList.size());
        for (StrategyAward strategyAward: strategyAwardList){
            StrategyAwardEntity strategyAwardEntity = StrategyAwardEntity.builder()
                        .strategyId(strategyAward.getStrategyId())
                        .awardId(strategyAward.getAwardId())
                        .awardCount(strategyAward.getAwardCount())
                        .awardCountSurplus(strategyAward.getAwardCountSurplus())
                        .awardRate(strategyAward.getAwardRate())
                        .build();
            strategyAwardEntities.add(strategyAwardEntity);
        }
        redisService.setValue(cacheKey, strategyAwardEntities);
        return strategyAwardEntities;
    }

    @Override
    public void saveRedis(String key, BigDecimal rateRange, HashMap<Integer, Integer> shuffleAwardRateTable) {
        // 存储抽奖策略范围值,如10000，用于生成随机数
        redisService.setValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + key,rateRange.intValue());
        // 存储概率查找表
        Map<Integer, Integer> cacheRateMap = redisService.getMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + key);
        cacheRateMap.putAll(shuffleAwardRateTable);
    }

    @Override
    public Integer getRateRange(Long strategyId) {
        return getRateRange(String.valueOf(strategyId));
    }

    @Override
    public Integer getRateRange(String strategyId) {
        return redisService.getValue(Constants.RedisKey.STRATEGY_RATE_RANGE_KEY + strategyId);
    }

    @Override
    public Integer getStrategyAwardAssemble(String strategyId, int awardId) {
        return redisService.getFromMap(Constants.RedisKey.STRATEGY_RATE_TABLE_KEY + strategyId, awardId);
    }

    @Override
    public StrategyEntity queryStrategyEntityByStrategyId(Long strategyId) {
        // 优先从缓存中获取
        String cacheKey = Constants.RedisKey.STRATEGY_KEY + strategyId;
        StrategyEntity strategyEntity = redisService.getValue(cacheKey);
        if(null != strategyEntity) return strategyEntity;

        Strategy strategy = strategyDao.queryById(strategyId);
        return StrategyEntity.builder()
                .strategyId(strategy.getStrategyId())
                .strategyDesc(strategy.getStrategyDesc())
                .ruleModels(strategy.getRuleModels())
                .build();
    }
}
