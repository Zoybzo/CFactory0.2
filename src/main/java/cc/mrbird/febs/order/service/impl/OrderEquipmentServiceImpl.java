package cc.mrbird.febs.order.service.impl;

import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.order.entity.OrderEquipment;
import cc.mrbird.febs.order.mapper.OrderEquipmentMapper;
import cc.mrbird.febs.order.service.IOrderEquipmentService;
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
 * 排产表 Service实现
 *
 * @author zoybzo
 * @date 2021-07-15 18:57:38
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class OrderEquipmentServiceImpl extends ServiceImpl<OrderEquipmentMapper, OrderEquipment> implements IOrderEquipmentService {

    private final OrderEquipmentMapper orderEquipmentMapper;

    @Override
    public IPage<OrderEquipment> findOrderEquipments(QueryRequest request, OrderEquipment orderEquipment) {
        LambdaQueryWrapper<OrderEquipment> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        Page<OrderEquipment> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<OrderEquipment> findOrderEquipments(OrderEquipment orderEquipment) {
	    LambdaQueryWrapper<OrderEquipment> queryWrapper = new LambdaQueryWrapper<>();
		// TODO 设置查询条件
		return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrderEquipment(OrderEquipment orderEquipment) {
        this.save(orderEquipment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderEquipment(OrderEquipment orderEquipment) {
        this.saveOrUpdate(orderEquipment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteOrderEquipment(OrderEquipment orderEquipment) {
        LambdaQueryWrapper<OrderEquipment> wrapper = new LambdaQueryWrapper<>();
	    // TODO 设置删除条件
	    this.remove(wrapper);
	}
}
