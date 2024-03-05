package cn.top.domain.strategy.service.armory;

/**
 * @author yongjianWang
 * @description: 策略抽奖调度
 * @date 2024年03月04日 20:01
 */
public interface IStrategyDispatch {
    /**
     * 获取抽奖策略装配的随机结果
     * @param strategyId
     * @return
     */
    Integer getRandomAwardId(Long strategyId);

    /**
     * 带权重的随机抽奖
     * @param strategyId
     * @param ruleWeightValue
     * @return
     */
    Integer getRandomAwardId(Long strategyId, String ruleWeightValue);

}
