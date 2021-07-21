package cc.mrbird.febs.order.entity;


import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * Entity
 *
 * @author zoybzo
 * @date 2021-07-20 20:37:05
 */
@Data
@TableName("t_order_equipment")
public class OrderEquipment implements Serializable, Cloneable {

    /**
     *
     */
    @TableId(value = "ORDER_EQUIPMENT_ID", type = IdType.AUTO)
    private Long orderEquipmentId;

    /**
     *
     */
    @TableField("ORDER_ID")
    private Long orderId;

    /**
     *
     */
    @TableField("EQUIPMENT_ID1")
    private Long equipmentId1;

    /**
     *
     */
    @TableField("EQUIPMENT_ID2")
    private Long equipmentId2;

    /**
     *
     */
    @TableField("EQUIPMENT_ID3")
    private Long equipmentId3;

    @TableField(exist = false)
    private Long userId;
    @TableField(exist = false)
    private Long selectedFactoryId;

    @Override
    protected OrderEquipment clone() throws CloneNotSupportedException {
        return (OrderEquipment) super.clone();
    }
}
