package cc.mrbird.febs.factory.service.impl;

import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.factory.entity.EquipmentType;
import cc.mrbird.febs.factory.entity.Factory;
import cc.mrbird.febs.factory.mapper.EquipmentTypeMapper;
import cc.mrbird.febs.factory.service.IEquipmentTypeService;
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
 * Service实现
 *
 * @author zoybzo
 * @date 2021-07-19 16:42:37
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class EquipmentTypeServiceImpl extends ServiceImpl<EquipmentTypeMapper, EquipmentType> implements IEquipmentTypeService {

    private final EquipmentTypeMapper equipmentTypeMapper;

    @Override
    public IPage<EquipmentType> findEquipmentTypes(QueryRequest request, EquipmentType equipmentType) {
        if (StringUtils.isNotBlank(equipmentType.getCreateTimeFrom()) &&
                StringUtils.equals(equipmentType.getCreateTimeFrom(), equipmentType.getCreateTimeTo())) {
            equipmentType.setCreateTimeFrom(equipmentType.getCreateTimeFrom() + FebsConstant.DAY_START_PATTERN_SUFFIX);
            equipmentType.setCreateTimeTo(equipmentType.getCreateTimeTo() + FebsConstant.DAY_END_PATTERN_SUFFIX);
        }
        Page<Factory> page = new Page<>(request.getPageNum(), request.getPageSize());
        page.setTotal(baseMapper.countEquipmentTypeDetail(equipmentType));
        SortUtil.handlePageSort(request, page, "equipmentTypeId", FebsConstant.ORDER_ASC, false);
        return baseMapper.findEquipmentTypeDetailPage(page, equipmentType);
    }

    @Override
    public List<EquipmentType> findEquipmentTypes(EquipmentType equipmentType) {
        LambdaQueryWrapper<EquipmentType> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createEquipmentType(EquipmentType equipmentType) {
        this.save(equipmentType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateEquipmentType(EquipmentType equipmentType) {
        equipmentType.setModifyTime(new Date());
        updateById(equipmentType);
        this.saveOrUpdate(equipmentType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteEquipmentType(EquipmentType equipmentType) {
        LambdaQueryWrapper<EquipmentType> wrapper = new LambdaQueryWrapper<>();
        // TODO 设置删除条件
        this.remove(wrapper);
    }

    @Override
    public EquipmentType findById(String equipmentTypeId) {
        LambdaQueryWrapper<EquipmentType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EquipmentType::getEquipmentTypeId, equipmentTypeId);
        List<EquipmentType> equipmentTypeList = equipmentTypeMapper.selectList(wrapper);
        if (!equipmentTypeList.isEmpty())
            return equipmentTypeList.get(0);
        return null;
    }
}
