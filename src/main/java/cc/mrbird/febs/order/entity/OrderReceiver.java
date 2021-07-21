package cc.mrbird.febs.order.entity;


import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 订单收货人表 Entity
 *
 * @author zoybzo
 * @date 2021-07-15 18:55:48
 */
@Data
@TableName("t_order_receiver")
public class OrderReceiver implements Serializable, Cloneable {

    /**
     * 订单ID
     */
    @TableField("ORDER_ID")
    private Long orderId;

    /**
     * 收货人信息ID
     */
    @TableField("RECEIVER_ID")
    private Long receiverId;

}
