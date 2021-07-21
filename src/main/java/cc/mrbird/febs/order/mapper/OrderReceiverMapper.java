package cc.mrbird.febs.order.mapper;

import cc.mrbird.febs.order.entity.Order;
import cc.mrbird.febs.order.entity.OrderReceiver;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * 订单收货人表 Mapper
 *
 * @author zoybzo
 * @date 2021-07-15 18:55:48
 */
public interface OrderReceiverMapper extends BaseMapper<OrderReceiver> {
    /**
     * order 的数量
     *
     * @param orderReceiver 订单对象
     * @return 数量
     */
    long countOrderReceiverDetail(@Param("orderReceiver") OrderReceiver orderReceiver);
}
