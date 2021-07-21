package cc.mrbird.febs.factory.mapper;

import cc.mrbird.febs.factory.entity.Factory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 工厂表 Mapper
 *
 * @author zoybzo
 * @date 2021-07-13 10:43:05
 */
public interface FactoryMapper extends BaseMapper<Factory> {


    /**
     * 通过用户名查找工厂
     *
     * @param userId 用户名
     * @return 工厂
     */
    Factory findByUserId(String userId);

    /**
     * 通过工厂名查找工厂
     *
     * @param factoryName 工厂名
     * @return 工厂
     */
    Factory findByName(String factoryName);

    /**
     * @param factory 工厂对象，用于传递查询条件
     * @return List
     */
    List<Factory> findFactoryDetail(@Param("factory") Factory factory);

    /**
     * factory 的数量
     *
     * @param factory 工厂对象
     * @return 数量
     */
    long countFactoryDetail(@Param("factory") Factory factory);

    /**
     * 查找工厂详细信息
     *
     * @param page    分页对象
     * @param factory 工厂对象，用于传递查询条件
     * @return IPage
     */
    <T> IPage<Factory> findFactoryDetailPage(Page<T> page, @Param("factory") Factory factory);

}
