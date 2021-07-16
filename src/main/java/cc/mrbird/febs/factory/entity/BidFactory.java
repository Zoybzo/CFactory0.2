package cc.mrbird.febs.receiver.entity;


import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 竞标工厂表 Entity
 *
 * @author zoybzo
 * @date 2021-07-15 18:58:07
 */
@Data
@TableName("t_bid_factory")
public class BidFactory {

    /**
     * 竞标工厂ID
     */
    @TableField("FACTORY_ID")
    private Long factoryId;

    /**
     * 订单ID
     */
    @TableField("ORDER_ID")
    private Long orderId;

    /**
     * 竞标价格
     */
    @TableField("PRICE")
    private Long price;

}
