package cn.top.infrastructure.persistent.dao;

import cn.top.infrastructure.persistent.po.Strategy;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author yongjianWang
 * @description: 抽奖策略 Dao
 * @date 2024年03月03日 13:32
 */
@Mapper
public interface IStrategyDao {

    /**
     * 查询策略集合
     * @return
     */
    List<Strategy> queryList();

    /**
     * 根据ID查询策略
     * @param strategyId
     * @return
     */
    Strategy queryById(Long strategyId);
}
