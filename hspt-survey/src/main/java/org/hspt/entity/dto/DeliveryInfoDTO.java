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
public class DeliveryInfoDTO {

    private Integer deliveryId;    //分发编号
    private Integer surveyId;    //分发问卷
    private Integer patientId;    //分发病人
    private Date deliveryDate;    //分发日期
    private Date endDate;    //截止日期
    private Integer overday;    //逾期天数
    private Integer state; //状态 (已简化为： 未答卷=0,重发未答卷>0[次数])

}
