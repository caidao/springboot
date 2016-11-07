package paner.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2016/4/26.
 */
@ApiModel(value = "QQRespModel",discriminator = "what",subTypes = {QQRespModel.class})
public class QQRespModel {

    @ApiModelProperty(value = "客户端id",allowableValues = "12456,7893432",dataType = "string")
    private String client_id;
    @ApiModelProperty(value = "open id",allowableValues = "232ded42,7893sdccf432",dataType = "string")
    private String openid;

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
