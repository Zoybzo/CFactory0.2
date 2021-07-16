package cc.mrbird.febs.order.entity;

import java.util.Date;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 排产表 Entity
 *
 * @author zoybzo
 * @date 2021-07-15 18:57:38
 */
@Data
@TableName("t_order_equipment")
public class OrderEquipment {

    /**
     * 生产开始时间
     */
    @TableField("BEGIN_DATETIME")
    private Date beginDatetime;

    /**
     * 生产结束时间
     */
    @TableField("END_DATETIME")
    private Date endDatetime;

    /**
     * 设备ID
     */
    @TableField("EQUIPMENT_ID")
    private Long equipmentId;

    /**
     * 订单ID
     */
    @TableField("ORDER_ID")
    private Long orderId;

}
