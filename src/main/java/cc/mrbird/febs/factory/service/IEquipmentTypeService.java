package cc.mrbird.febs.factory.service;

import cc.mrbird.febs.factory.entity.EquipmentType;

import cc.mrbird.febs.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *  Service接口
 *
 * @author zoybzo
 * @date 2021-07-19 16:42:37
 */
public interface IEquipmentTypeService extends IService<EquipmentType> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param equipmentType equipmentType
     * @return IPage
     */
    IPage<EquipmentType> findEquipmentTypes(QueryRequest request, EquipmentType equipmentType);

    /**
     * 查询（所有）
     *
     * @param equipmentType equipmentType
     * @return List
     */
    List<EquipmentType> findEquipmentTypes(EquipmentType equipmentType);

    /**
     * 新增
     *
     * @param equipmentType equipmentType
     */
    void createEquipmentType(EquipmentType equipmentType);

    /**
     * 修改
     *
     * @param equipmentType equipmentType
     */
    void updateEquipmentType(EquipmentType equipmentType);

    /**
     * 删除
     *
     * @param equipmentType equipmentType
     */
    void deleteEquipmentType(EquipmentType equipmentType);

    EquipmentType findById(String equipmentTypeId);
}
