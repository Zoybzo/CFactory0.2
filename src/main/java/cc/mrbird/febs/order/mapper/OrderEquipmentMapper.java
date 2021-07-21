package cc.mrbird.febs.order.mapper;

import cc.mrbird.febs.order.entity.OrderEquipment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Mapper
 *
 * @author zoybzo
 * @date 2021-07-20 20:37:05
 */
public interface OrderEquipmentMapper extends BaseMapper<OrderEquipment> {

    OrderEquipment findByOrderId(@Param("orderEquipment") OrderEquipment orderEquipment);

    List<OrderEquipment> findOrderEquipmentsWithEquipmentIds(@Param("orderEquipment") OrderEquipment orderEquipment);

    List<OrderEquipment> findOrderEquipments(@Param("orderEquipment") OrderEquipment orderEquipment);
}
