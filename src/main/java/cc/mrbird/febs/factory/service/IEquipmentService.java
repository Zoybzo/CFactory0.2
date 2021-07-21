package cc.mrbird.febs.factory.service;

import cc.mrbird.febs.factory.entity.Equipment;

import cc.mrbird.febs.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 设备表 Service接口
 *
 * @author zoybzo
 * @date 2021-07-15 14:06:09
 */
public interface IEquipmentService extends IService<Equipment> {
    /**
     * 查询（分页）
     *
     * @param request   QueryRequest
     * @param equipment equipment
     * @return IPage
     */
    IPage<Equipment> findEquipments(QueryRequest request, Equipment equipment);

    /**
     * 查询（所有）
     *
     * @param equipment equipment
     * @return List
     */
    List<Equipment> findEquipments(Equipment equipment);

    /**
     * 新增
     *
     * @param equipment equipment
     */
    void createEquipment(Equipment equipment);

    /**
     * 修改
     *
     * @param equipment equipment
     */
    void updateEquipment(Equipment equipment);

    /**
     * 删除
     *
     * @param equipment equipment
     */
    void deleteEquipment(Equipment equipment);

    Equipment findById(String equipmentId);

    boolean doesEquipmentUsing(String[] equipmentIds);

    void rentEquipment(Equipment equipment);

    boolean doesEquipmentRent(String[] equipmentIds);
}
