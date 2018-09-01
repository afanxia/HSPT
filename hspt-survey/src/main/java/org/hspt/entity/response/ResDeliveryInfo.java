package org.hspt.entity.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <b> 问卷分发基础信息 </b>
 * <p>
 * 功能描述:
 * </p>
 *
 */
@Data
@ApiModel("问卷分发响应实体信息")
public class ResDeliveryInfo implements Serializable {

    @ApiModelProperty("问卷分发编号")
    @NotNull
    private Integer deliveryId;    //问卷分发编号

    @ApiModelProperty("问卷编号")
    @NotNull
    private Integer surveyId;    //问卷编号

    @ApiModelProperty("病人编号")
    @NotNull
    private Integer patientId;    //病人编号

    @ApiModelProperty("逾期天数")
    @NotNull
    private Integer overday;    //逾期天数

    @ApiModelProperty("逾期天数")
    @NotNull
    private Integer state;    //状态 (已简化为： 未答卷=0,重发未答卷>0[次数])

    @ApiModelProperty("问卷分发日期")
    @NotNull
    private Date deliveryDate;    //分发日期

    @ApiModelProperty("问卷回答截止日期")
    @NotNull
    private Date endDate;    //截止日期

}
