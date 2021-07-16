package cc.mrbird.febs.factory.mapper;

import cc.mrbird.febs.factory.entity.Equipment;
import cc.mrbird.febs.factory.entity.Factory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * 设备表 Mapper
 *
 * @author zoybzo
 * @date 2021-07-15 14:06:09
 */
public interface EquipmentMapper extends BaseMapper<Equipment> {
    /**
     * equipment 的数量
     *
     * @param equipment 设备对象
     * @return 数量
     */
    long countEquipmentDetail(@Param("equipment") Equipment equipment);


    /**
     * 查找设备详细信息
     *
     * @param page      分页对象
     * @param equipment 设备对象，用于传递查询条件
     * @return IPage
     */
    <T> IPage<Equipment> findEquipmentDetailPage(Page<T> page, @Param("equipment") Equipment equipment);
}
