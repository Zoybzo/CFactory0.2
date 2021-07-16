package cc.mrbird.febs.factory.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import cc.mrbird.febs.common.converter.TimeConverter;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.Size;

/**
 * 工厂表 Entity
 *
 * @author zoybzo
 * @date 2021-07-13 10:43:05
 */
@Data
@Excel("工厂信息表")
@TableName("t_factory")
public class Factory implements Serializable, Cloneable {
    /**
     * 工厂ID
     */
    @TableId(value = "FACTORY_ID", type = IdType.AUTO)
    private Long factoryId;

    /**
     * 工厂名
     */
    @TableField("FACTORY_NAME")
    @Size(min = 4, max = 10, message = "{range}")
    @ExcelField(value = "工厂名")
    private String factoryName;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    @ExcelField(value = "创建时间", writeConverter = TimeConverter.class)
    private Date createTime;

    /**
     * 工厂简介
     */
    @TableField("DESCRIPTION")
    @Size(max = 100, message = "{noMoreThan}")
    @ExcelField(value = "工厂描述")
    private String description;


    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
    @ExcelField(value = "修改时间", writeConverter = TimeConverter.class)
    private Date modifyTime;

    /**
     * 工厂状态 0关停 1正常 2申请中
     */
    @TableField("STATUS")
    private String status;

    @ExcelField(value = "所属用户ID")
    @TableField(exist = false)
    private String userId;

    @ExcelField(value = "所属用户姓名")
    @TableField(exist = false)
    private String username;

    @TableField(exist = false)
    private String createTimeFrom;
    @TableField(exist = false)
    private String createTimeTo;

    @TableField(exist = false)
    private Set<String> stringPermissions;

    @Override
    public Factory clone() throws CloneNotSupportedException {
        return (Factory) super.clone();
    }
}
