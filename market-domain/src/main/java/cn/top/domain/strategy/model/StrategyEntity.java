package cn.top.domain.strategy.model;

import cn.top.types.common.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yongjianWang
 * @description: 策略实体
 * @date 2024年03月04日 20:11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StrategyEntity {

    /** 抽奖策略ID */
    private Long strategyId;

    /** 抽奖策略描述 */
    private String strategyDesc;

    /** 策略规则模型 */
    private String ruleModels;


    public String getRuleWeight(){
        if (StringUtils.isBlank(ruleModels)) return null;
        String []ruleModels = this.ruleModels.split(Constants.SPLIT);
        for (String ruleModel: ruleModels){
            if ("rule_weight".equals(ruleModel)){
                return ruleModel;
            }
        }
        return null;
    }

}
