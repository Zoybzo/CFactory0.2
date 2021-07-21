package cc.mrbird.febs.factory.service;

import cc.mrbird.febs.factory.entity.Factory;
import cc.mrbird.febs.factory.entity.UserFactory;

import cc.mrbird.febs.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 用户工厂表 Service接口
 *
 * @author zoybzo
 * @date 2021-07-13 11:03:41
 */
public interface IUserFactoryService extends IService<UserFactory> {

    /**
     * 查询工厂所属用户
     *
     * @param factoryId 工厂ID
     * @return 用户ID
     */
    String findByFactoryId(String factoryId);

    UserFactory findUserFactoryByFactoryId(String factoryId);

    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param userFactory userFactory
     * @return IPage
     */
    IPage<UserFactory> findUserFactorys(QueryRequest request, UserFactory userFactory);

    /**
     * 查询（所有）
     *
     * @param userFactory userFactory
     * @return List
     */
    List<UserFactory> findUserFactorys(UserFactory userFactory);

    /**
     * 新增
     *
     * @param userFactory userFactory
     */
    void createUserFactory(UserFactory userFactory);

    /**
     * 修改
     *
     * @param userFactory userFactory
     */
    void updateUserFactory(UserFactory userFactory);

    /**
     * 删除
     *
     * @param userFactory userFactory
     */
    void deleteUserFactory(UserFactory userFactory);
}
