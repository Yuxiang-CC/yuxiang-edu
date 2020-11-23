package com.yuxiang.edu.service.vod.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yuxiang.edu.service.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 直播信息表
 * </p>
 *
 * @author yuxiang
 * @since 2020-11-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("vod_live_info")
@ApiModel(value="LiveInfo对象", description="直播信息表")
public class LiveInfo extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "讲师id")
    private String tid;

    @ApiModelProperty(value = "直播标题")
    private String title;

    @ApiModelProperty(value = "直播封面")
    private String cover;

    @ApiModelProperty(value = "直播分类id")
    private Integer categoryId;

    @ApiModelProperty(value = "直播分类名称")
    private String categoryName;

    @ApiModelProperty(value = "个人简介")
    private String introduction;

    @ApiModelProperty(value = "是否禁用 1（true）已禁用，  0（false）未禁用")
    @TableField("is_disabled")
    private Boolean disabled;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;


}
