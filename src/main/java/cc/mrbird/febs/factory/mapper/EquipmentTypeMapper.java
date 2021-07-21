package cc.mrbird.febs.factory.mapper;

import cc.mrbird.febs.factory.entity.EquipmentType;
import cc.mrbird.febs.factory.entity.Factory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * Mapper
 *
 * @author zoybzo
 * @date 2021-07-19 16:42:37
 */
public interface EquipmentTypeMapper extends BaseMapper<EquipmentType> {

    long countEquipmentTypeDetail(@Param("equipmentType") EquipmentType equipmentType);

    <T> IPage<EquipmentType> findEquipmentTypeDetailPage(Page<T> page,@Param("equipmentType") EquipmentType equipmentType);
}
