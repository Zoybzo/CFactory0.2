package cc.mrbird.febs.factory.entity;


import cc.mrbird.febs.common.converter.TimeConverter;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * Entity
 *
 * @author zoybzo
 * @date 2021-07-19 16:42:37
 */
@Data
@TableName("t_equipment_type")
public class EquipmentType implements Serializable, Cloneable {

    /**
     * 设备类型描述
     */
    @TableField("DESCRIPTION")
    private String description;

    /**
     * 设备类型ID
     */
    @TableId(value = "EQUIPMENT_TYPE_ID", type = IdType.AUTO)
    private Long equipmentTypeId;

    /**
     * 设备类型名
     */
    @TableField("EQUIPMENT_TYPE_NAME")
    private String equipmentTypeName;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    @ExcelField(value = "创建时间", writeConverter = TimeConverter.class)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
    @ExcelField(value = "修改时间", writeConverter = TimeConverter.class)
    private Date modifyTime;


    @TableField(exist = false)
    private String createTimeFrom;
    @TableField(exist = false)
    private String createTimeTo;


    @Override
    protected EquipmentType clone() throws CloneNotSupportedException {
        return (EquipmentType) super.clone();
    }
}
