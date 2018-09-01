package org.hspt.entity.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;
import java.util.Set;

/**
 * <b>  </b>
 * <p>
 * 功能描述:
 * </p>
 *
 */
@Data
@ApiModel("登陆用户机构扩展信息")
public class RetrieveInfoDTO {

    private Integer deliveryId;
    private Date retrieveDate;    //答卷时间
    private Integer surveyId;    //分发问卷
    private Integer patientId;    //分发病人
    private Set<AnswerDTO> answers;

}
