package cn.top.test.infrastructure;

import cn.top.infrastructure.persistent.dao.IAwardDao;
import cn.top.infrastructure.persistent.po.Award;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yongjianWang
 * @description: AwardDao功能测试
 * @date 2024年03月03日 14:09
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class IAwardDaoTest {

    @Resource
    private IAwardDao IAwardDao;

    @Test
    public void testQueryList(){
        List<Award> awardList = IAwardDao.queryList();
        log.info("测试结果: {}", JSON.toJSONString(awardList));
    }
}
