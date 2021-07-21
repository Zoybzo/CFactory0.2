package cc.mrbird.febs.factory.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 设备表 Entity
 *
 * @author zoybzo
 * @date 2021-07-15 14:06:09
 */
@Data
@TableName("t_equipment")
public class Equipment implements Serializable, Cloneable {

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 设备描述
     */
    @TableField("DESCRIPTION")
    private String description;

    /**
     * 设备ID
     */
    @TableId(value = "EQUIPMENT_ID", type = IdType.AUTO)
    private Long equipmentId;

    /**
     * 设备名
     */
    @TableField("EQUIPMENT_NAME")
    private String equipmentName;

    /**
     * 设备类型
     */
    @TableField("EQUIPMENT_TYPE_ID")
    private Long equipmentTypeId;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
    private Date modifyTime;

    /**
     * 设备所属工厂
     */
    @TableField("OWNED_FACTORY")
    private Long ownedFactory;

    /**
     * 设备状态
     */
    @TableField("STATUS")
    private String status;

    /**
     * 正在使用设备的工厂
     */
    @TableField("USING_FACTORY")
    private Long usingFactory;

    @TableField(exist = false)
    private String createTimeFrom;
    @TableField(exist = false)
    private String createTimeTo;
    @ExcelField(value = "所属类型名")
    @TableField(exist = false)
    private String equipmentTypeName;
    @ExcelField(value = "所属工厂名")
    @TableField(exist = false)
    private String ownedFactoryName;
    @ExcelField(value = "使用工厂名")
    @TableField(exist = false)
    private String usingFactoryName;
    @ExcelField(value = "使用用户")
    @TableField(exist = false)
    private String usingUserId;
    @ExcelField(value = "所属用户")
    @TableField(exist = false)
    private String ownedUserId;

    @Override
    protected Equipment clone() throws CloneNotSupportedException {
        return (Equipment) super.clone();
    }
}
