package cc.mrbird.febs.factory.service.impl;

import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.factory.entity.UserFactory;
import cc.mrbird.febs.factory.mapper.UserFactoryMapper;
import cc.mrbird.febs.factory.service.IUserFactoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import lombok.RequiredArgsConstructor;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * 用户工厂表 Service实现
 *
 * @author zoybzo
 * @date 2021-07-13 11:03:41
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserFactoryServiceImpl extends ServiceImpl<UserFactoryMapper, UserFactory> implements IUserFactoryService {

    private final UserFactoryMapper userFactoryMapper;

    @Override
    public String findByFactoryId(String factoryId) {
        List<UserFactory> userFactory = baseMapper.selectList(new QueryWrapper<UserFactory>().lambda()
                .eq(UserFactory::getFactoryId, factoryId));
        if (CollectionUtils.isNotEmpty(userFactory)) {
            return String.valueOf(userFactory.get(0).getUserId());
        }
        return null;
    }

    @Override
    public UserFactory findUserFactoryByFactoryId(String factoryId) {
        List<UserFactory> userFactory = baseMapper.selectList(new QueryWrapper<UserFactory>().lambda()
                .eq(UserFactory::getFactoryId, factoryId));
        if (CollectionUtils.isNotEmpty(userFactory)) {
            return userFactory.get(0);
        }
        return null;
    }

    @Override
    public IPage<UserFactory> findUserFactorys(QueryRequest request, UserFactory userFactory) {
        LambdaQueryWrapper<UserFactory> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        Page<UserFactory> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<UserFactory> findUserFactorys(UserFactory userFactory) {
        LambdaQueryWrapper<UserFactory> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createUserFactory(UserFactory userFactory) {
        this.save(userFactory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserFactory(UserFactory userFactory) {
        this.saveOrUpdate(userFactory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserFactory(UserFactory userFactory) {
        removeById(userFactory.getUserFactoryId());
    }
}
