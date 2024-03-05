package cn.top.infrastructure.persistent.dao;

import cn.top.infrastructure.persistent.po.StrategyRule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author yongjianWang
 * @description: 抽奖规则Dao
 * @date 2024年03月03日 13:32
 */
@Mapper
public interface IStrategyRuleDao {
    /**
     * 查询策略规则集合
     * @return
     */
    List<StrategyRule> queryList();

    /**
     * 根据策略ID和策略规则模型查询对象
     * @param strategyRule
     * @return
     */
    StrategyRule queryStrategyRule(StrategyRule strategyRule);
}
