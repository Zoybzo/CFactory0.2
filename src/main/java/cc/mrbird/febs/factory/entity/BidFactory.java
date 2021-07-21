package cc.mrbird.febs.factory.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 竞标工厂表 Entity
 *
 * @author zoybzo
 * @date 2021-07-15 18:58:07
 */
@Data
@TableName("t_bid_factory")
public class BidFactory implements Serializable, Cloneable {

    @TableId(value = "BID_FACTORY_ID", type = IdType.AUTO)
    private Long bidFactoryId;

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

    @ExcelField(value = "工厂名")
    @TableField(exist = false)
    private String factoryName;

    @Override
    protected BidFactory clone() throws CloneNotSupportedException {
        return (BidFactory) super.clone();
    }
}
