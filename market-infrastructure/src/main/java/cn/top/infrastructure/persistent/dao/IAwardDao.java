package cn.top.infrastructure.persistent.dao;

import cn.top.infrastructure.persistent.po.Award;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author yongjianWang
 * @description: 奖品 Dao
 * @date 2024年03月03日 13:29
 */
@Mapper
public interface IAwardDao {

    List<Award> queryList();
}
