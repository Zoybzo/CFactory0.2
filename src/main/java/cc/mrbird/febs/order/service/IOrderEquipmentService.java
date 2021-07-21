package cc.mrbird.febs.order.service;

import cc.mrbird.febs.order.entity.OrderEquipment;

import cc.mrbird.febs.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *  Service接口
 *
 * @author zoybzo
 * @date 2021-07-20 20:37:05
 */
public interface IOrderEquipmentService extends IService<OrderEquipment> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param orderEquipment orderEquipment
     * @return IPage
     */
    IPage<OrderEquipment> findOrderEquipments(QueryRequest request, OrderEquipment orderEquipment);

    /**
     * 查询（所有）
     *
     * @param orderEquipment orderEquipment
     * @return List
     */
    List<OrderEquipment> findOrderEquipments(OrderEquipment orderEquipment);

    /**
     * 新增
     *
     * @param orderEquipment orderEquipment
     */
    void createOrderEquipment(OrderEquipment orderEquipment);

    /**
     * 修改
     *
     * @param orderEquipment orderEquipment
     */
    void updateOrderEquipment(OrderEquipment orderEquipment);

    /**
     * 删除
     *
     * @param orderEquipment orderEquipment
     */
    void deleteOrderEquipment(OrderEquipment orderEquipment);

    OrderEquipment findByOrderId(String orderId);

    List<OrderEquipment> findOrderEquipmentsWithEquipmentIds(OrderEquipment orderEquipment);
}
