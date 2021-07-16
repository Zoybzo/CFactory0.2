package cc.mrbird.febs.order.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 订单表 Entity
 *
 * @author zoybzo
 * @date 2021-07-15 18:55:40
 */
@Data
@TableName("t_order")
public class Order implements Serializable, Cloneable {

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    private Date createTime;

    /**
     * 要求最晚发货时间
     */
    @TableField("DELIVERY_DATETIME")
    private Date deliveryDatetime;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
    private Date modifyTime;

    /**
     * 订单ID
     */
    @TableId(value = "ORDER_ID", type = IdType.AUTO)
    private Long orderId;

    /**
     * 订单所需产品
     */
    @TableField("PRODUCT_ID")
    private Long productId;

    /**
     * 所需数量
     */
    @TableField("QUANTITY")
    private Long quantity;

    /**
     * 中标工厂ID
     */
    @TableField("SELECTED_FACTORY_ID")
    private Long selectedFactoryId;

    /**
     * 订单状态
     */
    @TableField("STATUS")
    private String status;

    /**
     * 创建订单的用户
     */
    @TableField("USER_ID")
    private Long userId;

    @TableField(exist = false)
    private String winUserId;
    @TableField(exist = false)
    private String createTimeFrom;
    @TableField(exist = false)
    private String createTimeTo;

    @TableField(exist = false)
    private Set<String> stringPermissions;


}
