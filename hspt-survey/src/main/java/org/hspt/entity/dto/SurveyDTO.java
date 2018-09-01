package org.hspt.entity.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * <b>  </b>
 * <p>
 * 功能描述:
 * </p>
 *
 */
@Data
@ApiModel("登陆用户机构扩展信息")
public class SurveyDTO {

    private Long surveyId;    //问卷编号
    private Long surveyTypeId;    //问卷类型
    private String surveyName;    //问卷名称
    private String author;    //作者名称
    private String department;    //科室
    private Date putdate;    //生成日期
    private Long num;    //总分发数
    private Long currentNum;    //总回收数
    private String description;    //简介
    private Long frequency;    //随访频率
    private Long times;    //随访次数
    private boolean sendOnRegister;
    private Long bday;  // overdue day
}
