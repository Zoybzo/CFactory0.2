package cc.mrbird.febs.order.service;

import cc.mrbird.febs.order.entity.OrderReceiver;

import cc.mrbird.febs.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 订单收货人表 Service接口
 *
 * @author zoybzo
 * @date 2021-07-15 18:55:48
 */
public interface IOrderReceiverService extends IService<OrderReceiver> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param orderReceiver orderReceiver
     * @return IPage<OrderReceiver>
     */
    IPage<OrderReceiver> findOrderReceivers(QueryRequest request, OrderReceiver orderReceiver);

    /**
     * 查询（所有）
     *
     * @param orderReceiver orderReceiver
     * @return List<OrderReceiver>
     */
    List<OrderReceiver> findOrderReceivers(OrderReceiver orderReceiver);

    /**
     * 新增
     *
     * @param orderReceiver orderReceiver
     */
    void createOrderReceiver(OrderReceiver orderReceiver);

    /**
     * 修改
     *
     * @param orderReceiver orderReceiver
     */
    void updateOrderReceiver(OrderReceiver orderReceiver);

    /**
     * 删除
     *
     * @param orderReceiver orderReceiver
     */
    void deleteOrderReceiver(OrderReceiver orderReceiver);
}
