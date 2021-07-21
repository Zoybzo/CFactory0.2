package cc.mrbird.febs.order.service.impl;

import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.factory.entity.Factory;
import cc.mrbird.febs.order.entity.Order;
import cc.mrbird.febs.order.mapper.OrderMapper;
import cc.mrbird.febs.order.service.IOrderService;
import cc.mrbird.febs.product.entity.Product;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import lombok.RequiredArgsConstructor;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * 订单表 Service实现
 *
 * @author zoybzo
 * @date 2021-07-15 18:55:40
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    private final OrderMapper orderMapper;

    @Override
    public IPage<Order> findOrders(QueryRequest request, Order order) {
        if (StringUtils.isNotBlank(order.getCreateTimeFrom()) &&
                StringUtils.equals(order.getCreateTimeFrom(), order.getCreateTimeTo())) {
            order.setCreateTimeFrom(order.getCreateTimeFrom() + FebsConstant.DAY_START_PATTERN_SUFFIX);
            order.setCreateTimeTo(order.getCreateTimeTo() + FebsConstant.DAY_END_PATTERN_SUFFIX);
        }
        Page<Factory> page = new Page<>(request.getPageNum(), request.getPageSize());
        page.setTotal(baseMapper.countOrderDetail(order));
        SortUtil.handlePageSort(request, page, "orderId", FebsConstant.ORDER_ASC, false);
        return baseMapper.findOrderDetailPage(page, order);
    }

    @Override
    public List<Order> findOrders(Order order) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrder(Order order) {
        this.save(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrder(Order order) {
        this.saveOrUpdate(order);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOrder(Order order) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        // TODO 设置删除条件
        this.remove(wrapper);
    }

    @Override
    public boolean doesFactoryHasOrder(String[] factoryIds) {
        for (String it : factoryIds) {
            Order order = new Order();
            order.setSelectedFactoryId(Long.valueOf(it));
            order.setStatus("5");
            long cnt = orderMapper.countOrderDetailWithStatus(order);
            if (cnt != 0L) return true;
        }
        return false;
    }

    @Override
    public Order findById(String orderId) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getOrderId, orderId);
        List<Order> orderList = orderMapper.selectList(wrapper);
        if (!orderList.isEmpty())
            return orderList.get(0);
        return null;
    }

    @Override
    public boolean checkOrderStatus(String[] orderIds) {
        for (String it : orderIds) {
            Order order = findById(it);
            if (Long.parseLong(order.getStatus()) != 0L) return true;
        }
        return false;
    }

    @Override
    public IPage<Order> findOtherOrders(QueryRequest request, Order order) {
        if (StringUtils.isNotBlank(order.getCreateTimeFrom()) &&
                StringUtils.equals(order.getCreateTimeFrom(), order.getCreateTimeTo())) {
            order.setCreateTimeFrom(order.getCreateTimeFrom() + FebsConstant.DAY_START_PATTERN_SUFFIX);
            order.setCreateTimeTo(order.getCreateTimeTo() + FebsConstant.DAY_END_PATTERN_SUFFIX);
        }
        Page<Factory> page = new Page<>(request.getPageNum(), request.getPageSize());
        page.setTotal(baseMapper.countOtherOrderDetail(order));
        SortUtil.handlePageSort(request, page, "orderId", FebsConstant.ORDER_ASC, false);
        return baseMapper.findOtherOrderDetailPage(page, order);
    }

    @Override
    public IPage<Order> findMyOrders(QueryRequest request, Order order) {
        Page<Factory> page = new Page<>(request.getPageNum(), request.getPageSize());
        page.setTotal(baseMapper.countMyOrderDetail(order));
        SortUtil.handlePageSort(request, page, "orderId", FebsConstant.ORDER_ASC, false);
        return baseMapper.findMyOrderDetailPage(page, order);
    }
}
