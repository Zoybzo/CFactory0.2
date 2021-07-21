package cc.mrbird.febs.factory.service.impl;

import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.factory.entity.Equipment;
import cc.mrbird.febs.factory.entity.Factory;
import cc.mrbird.febs.factory.mapper.EquipmentMapper;
import cc.mrbird.febs.factory.service.IEquipmentService;
import cc.mrbird.febs.order.entity.Order;
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

import java.util.Date;
import java.util.List;

/**
 * 设备表 Service实现
 *
 * @author zoybzo
 * @date 2021-07-15 14:06:09
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class EquipmentServiceImpl extends ServiceImpl<EquipmentMapper, Equipment> implements IEquipmentService {

    private final EquipmentMapper equipmentMapper;

    @Override
    public IPage<Equipment> findEquipments(QueryRequest request, Equipment equipment) {
        if (StringUtils.isNotBlank(equipment.getCreateTimeFrom()) &&
                StringUtils.equals(equipment.getCreateTimeFrom(), equipment.getCreateTimeTo())) {
            equipment.setCreateTimeFrom(equipment.getCreateTimeFrom() + FebsConstant.DAY_START_PATTERN_SUFFIX);
            equipment.setCreateTimeTo(equipment.getCreateTimeTo() + FebsConstant.DAY_END_PATTERN_SUFFIX);
        }
        Page<Factory> page = new Page<>(request.getPageNum(), request.getPageSize());
        page.setTotal(baseMapper.countEquipmentDetail(equipment));
        SortUtil.handlePageSort(request, page, "equipmentId", FebsConstant.ORDER_ASC, false);
        return baseMapper.findEquipmentDetailPage(page, equipment);
    }

    @Override
    public List<Equipment> findEquipments(Equipment equipment) {
        LambdaQueryWrapper<Equipment> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createEquipment(Equipment equipment) {
        this.save(equipment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateEquipment(Equipment equipment) {
        equipment.setModifyTime(new Date());
        updateById(equipment);
        this.saveOrUpdate(equipment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteEquipment(Equipment equipment) {
        removeById(equipment.getEquipmentId());
    }

    @Override
    public Equipment findById(String equipmentId) {
        LambdaQueryWrapper<Equipment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Equipment::getEquipmentId, equipmentId);
        List<Equipment> equipmentList = equipmentMapper.selectList(wrapper);
        if (!equipmentList.isEmpty())
            return equipmentList.get(0);
        return null;
    }

    @Override
    public boolean doesEquipmentUsing(String[] equipmentIds) {
        for (String it : equipmentIds) {
            Equipment equipment = new Equipment();
            equipment.setEquipmentId(Long.valueOf(it));
            equipment.setStatus("1");
            long cnt = equipmentMapper.countEquipmentDetail(equipment);
            if (cnt != 0L) return true;
        }
        return false;
    }

    @Override
    public void rentEquipment(Equipment equipment) {
        updateById(equipment);
        this.saveOrUpdate(equipment);
    }

    @Override
    public boolean doesEquipmentRent(String[] equipmentIds) {
        for (String it : equipmentIds) {
            Equipment equipment = findById(it);
            if (!equipment.getOwnedFactory().equals(equipment.getUsingFactory())) return true;
        }
        return false;
    }
}
