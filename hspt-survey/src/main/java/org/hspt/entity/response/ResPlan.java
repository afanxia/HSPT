package org.hspt.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hspt.entity.dto.SurveyDTO;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

/**
 * <b> 计划基础信息 </b>
 * <p>
 * 功能描述:
 * </p>
 *
 */
@Data
@ApiModel("计划响应实体信息")
public class ResPlan implements Serializable {

    @ApiModelProperty("计划编号")
    @NotNull
    private Integer planId;    //计划编号

    @ApiModelProperty("年龄区间下限")
    @NotNull
    private Integer beginAge;    //年龄区间下限

    @ApiModelProperty("年龄区间上限")
    @NotNull
    private Integer endAge;    //年龄区间上限

    @ApiModelProperty("激活状态")
    @NotNull
    private Integer active;    //激活状态

    @ApiModelProperty("性别要求")
    @NotNull
    private Integer sex;    //性别: 1 male, 2 female, 3 either

    @ApiModelProperty("病例类型")
    @NotNull
    private Integer oldPatient; //1 仅限新病例, 2 仅限既往病例, 3 仅限哮喘无忧用户, 4 不限

    @ApiModelProperty("病人类型")
    @NotNull
    private Integer patientTypeId;

    @ApiModelProperty("操作医生")
    @NotNull
    private Long aid;    //操作医生

    @ApiModelProperty("随访问卷")
    private Set<SurveyDTO> surveys;
}
