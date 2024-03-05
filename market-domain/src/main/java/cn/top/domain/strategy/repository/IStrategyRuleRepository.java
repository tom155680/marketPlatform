package cn.top.domain.strategy.repository;

import cn.top.domain.strategy.model.StrategyRuleEntity;

/**
 * @author yongjianWang
 * @description: 策略规则仓储接口
 * @date 2024年03月04日 20:37
 */

public interface IStrategyRuleRepository {

    /**
     * 查询策略规则
     * @param strategyId
     * @param ruleWeightModel
     * @return
     */
    StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleWeightModel);
}
