package cc.mrbird.febs.order.mapper;

import cc.mrbird.febs.factory.entity.Factory;
import cc.mrbird.febs.order.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * 订单表 Mapper
 *
 * @author zoybzo
 * @date 2021-07-15 18:55:40
 */
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * order 的数量
     *
     * @param order 订单对象
     * @return 数量
     */
    long countOrderDetail(@Param("order") Order order);

    /**
     * 查找 order 详细信息
     *
     * @param page  分页对象
     * @param order 订单对象，用于传递查询条件
     * @return IPage
     */
    <T> IPage<Order> findOrderDetailPage(Page<T> page, @Param("order") Order order);

}
