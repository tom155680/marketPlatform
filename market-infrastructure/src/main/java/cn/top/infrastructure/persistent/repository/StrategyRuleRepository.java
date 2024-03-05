package cn.top.infrastructure.persistent.repository;

import cn.top.domain.strategy.model.StrategyRuleEntity;
import cn.top.domain.strategy.repository.IStrategyRuleRepository;
import cn.top.infrastructure.persistent.dao.IStrategyRuleDao;
import cn.top.infrastructure.persistent.po.StrategyRule;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author yongjianWang
 * @description: 策略规则仓储实现
 * @date 2024年03月04日 20:39
 */
@Repository
public class StrategyRuleRepository implements IStrategyRuleRepository {

    @Resource
    private IStrategyRuleDao strategyRuleDao;

    @Override
    public StrategyRuleEntity queryStrategyRule(Long strategyId, String ruleWeightModel) {
        StrategyRule strategyRule = new StrategyRule();
        strategyRule.setStrategyId(strategyId);
        strategyRule.setRuleModel(ruleWeightModel);
        StrategyRule queryStrategyRule = strategyRuleDao.queryStrategyRule(strategyRule);
        return StrategyRuleEntity.builder()
                .strategyId(queryStrategyRule.getStrategyId())
                .awardId(queryStrategyRule.getAwardId())
                .ruleType(queryStrategyRule.getRuleType())
                .ruleModel(queryStrategyRule.getRuleModel())
                .ruleValue(queryStrategyRule.getRuleValue())
                .ruleDesc(queryStrategyRule.getRuleDesc())
                .build();
    }
}
