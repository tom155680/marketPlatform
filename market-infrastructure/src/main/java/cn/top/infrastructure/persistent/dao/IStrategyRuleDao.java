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
    List<StrategyRule> queryList();
}
