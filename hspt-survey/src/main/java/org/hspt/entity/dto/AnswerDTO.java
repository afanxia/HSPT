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
public class AnswerDTO {

    private Integer answerId;
    private Integer surveyId;
    private Integer patientId;
    private Integer deliveryId;
    private Integer questionId;
    private String lastModified; //最后修改人
    private Date modifiedDate;//最后修改日期
    private String textChoiceContent;
    private Integer textChoice;
    private Set<ChoiceDTO> choices;

}
