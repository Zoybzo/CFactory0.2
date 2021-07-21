package cc.mrbird.febs.order.service.impl;

import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.order.entity.Order;
import cc.mrbird.febs.order.entity.OrderReceiver;
import cc.mrbird.febs.order.mapper.OrderReceiverMapper;
import cc.mrbird.febs.order.service.IOrderReceiverService;
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
 * 订单收货人表 Service实现
 *
 * @author zoybzo
 * @date 2021-07-15 18:55:48
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class OrderReceiverServiceImpl extends ServiceImpl<OrderReceiverMapper, OrderReceiver> implements IOrderReceiverService {

    private final OrderReceiverMapper orderReceiverMapper;

    @Override
    public IPage<OrderReceiver> findOrderReceivers(QueryRequest request, OrderReceiver orderReceiver) {
        LambdaQueryWrapper<OrderReceiver> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        Page<OrderReceiver> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<OrderReceiver> findOrderReceivers(OrderReceiver orderReceiver) {
        LambdaQueryWrapper<OrderReceiver> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrderReceiver(OrderReceiver orderReceiver) {
        this.save(orderReceiver);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderReceiver(OrderReceiver orderReceiver) {
        this.saveOrUpdate(orderReceiver);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOrderReceiver(OrderReceiver orderReceiver) {
        LambdaQueryWrapper<OrderReceiver> wrapper = new LambdaQueryWrapper<>();
        // TODO 设置删除条件
        this.remove(wrapper);
    }

    @Override
    public boolean isReceiversExist(String[] receiverIds) {
        for (String it : receiverIds) {
            OrderReceiver orderReceiver = new OrderReceiver();
            orderReceiver.setReceiverId(Long.valueOf(it));
            long cnt = orderReceiverMapper.countOrderReceiverDetail(orderReceiver);
            if (cnt != 0L) return true;
        }
        return false;
    }
}
