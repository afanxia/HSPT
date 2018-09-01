package org.hspt.entity.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <b>  </b>
 * <p>
 * 功能描述:
 * </p>
 *
 */
@Data
@ApiModel("登陆用户机构扩展信息")
public class ChoiceDTO {

    private Integer choiceId;
    private Integer questionId;
    private BigDecimal score;
    private String choiceContent;
    private String choiceImgPath;

}
