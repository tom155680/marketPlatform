package cn.top.domain.strategy.service.armory;

/**
 * @author yongjianWang
 * @description: 策略装配库,负责初始化策略计算
 * @date 2024年03月03日 18:52
 */
public interface IStrategyArmory {

    /**
     * 初始化概率查找表
     * @param StrategyId
     */
    void assembleLotteryStrategy(Long StrategyId);

    Integer getRandomAwardId(Long strategyId);
}
