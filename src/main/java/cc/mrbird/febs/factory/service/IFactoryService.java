package cc.mrbird.febs.factory.service;

import cc.mrbird.febs.factory.entity.Factory;

import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.system.entity.User;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 工厂表 Service接口
 *
 * @author zoybzo
 * @date 2021-07-13 10:43:05
 */
public interface IFactoryService extends IService<Factory> {

    /**
     * @param factoryName 工厂名
     * @return 工厂
     */
    Factory findFactoryDetailList(String factoryName);

    /**
     * 通过工厂名查找工厂
     *
     * @param factoryName 工厂名
     * @return 工厂
     */
    Factory findByName(String factoryName);

    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param factory factory
     * @return IPage<Factory>
     */
    IPage<Factory> findFactories(QueryRequest request, Factory factory);

    /**
     * 查询（所有）
     *
     * @param factory factory
     * @return List<Factory>
     */
    List<Factory> findFactories(Factory factory);

    /**
     * 新增
     *
     * @param factory factory
     */
    void createFactory(Factory factory);

    /**
     * 修改
     *
     * @param factory factory
     */
    void updateFactory(Factory factory);

    /**
     * 删除
     *
     * @param factory factory
     */
    void deleteFactory(Factory factory);

}
