package cn.top.infrastructure.persistent.dao;

import cn.top.infrastructure.persistent.po.StrategyAward;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author yongjianWang
 * @description: 抽奖奖品明细配置 Dao
 * @date 2024年03月03日 13:32
 */
@Mapper
public interface IStrategyAwardDao {

    List<StrategyAward> queryListByStrategyId(Long strategyId);

}
