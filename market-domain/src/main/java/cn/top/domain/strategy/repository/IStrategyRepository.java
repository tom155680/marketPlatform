package cn.top.domain.strategy.repository;

import cn.top.domain.strategy.model.StrategyAwardEntity;
import cn.top.domain.strategy.model.StrategyEntity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * @author yongjianWang
 * @description: 策略仓储接口
 * @date 2024年03月03日 18:56
 */
public interface IStrategyRepository {

    /**
     * 根据策略ID查询策略奖品明细
     * @param strategyId
     * @return
     */
    List<StrategyAwardEntity> queryStrategyAwardList(Long strategyId);

    /**
     * 保存装配好的抽奖数据到redis中
     * @param key
     * @param rateRange
     * @param shuffleAwardRateTable
     */
    void saveRedis(String key, BigDecimal rateRange, HashMap<Integer, Integer> shuffleAwardRateTable);

    /**
     * 从redis中查询抽奖概率范围
     * @param strategyId
     * @return
     */
    Integer getRateRange(Long strategyId);

    /**
     * 从redis中查询抽奖概率范围
     * @param strategyId
     * @return
     */
    Integer getRateRange(String strategyId);

    /**
     * 获取抽奖奖品信息
     * @param strategyId
     * @param nextInt
     * @return
     */
    Integer getStrategyAwardAssemble(String strategyId, int nextInt);

    /**
     * 根据策略ID查询策略实体对象
     * @param strategyId
     * @return
     */
    StrategyEntity queryStrategyEntityByStrategyId(Long strategyId);
}
