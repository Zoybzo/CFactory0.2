package cc.mrbird.febs.factory.service.impl;

import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.factory.entity.Factory;
import cc.mrbird.febs.system.entity.User;
import cc.mrbird.febs.factory.mapper.FactoryMapper;
import cc.mrbird.febs.factory.service.IFactoryService;
import cc.mrbird.febs.factory.service.IUserFactoryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 工厂表 Service实现
 *
 * @author zoybzo
 * @date 2021-07-13 10:43:05
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class FactoryServiceImpl extends ServiceImpl<FactoryMapper, Factory> implements IFactoryService {

    private final FactoryMapper factoryMapper;
    private final IUserFactoryService userFactoryService;

    @Override
    public Factory findFactoryDetailList(String factoryName) {
        Factory param = new Factory();
        param.setFactoryName(factoryName);
        List<Factory> factories = baseMapper.findFactoryDetail(param);
        return CollectionUtils.isNotEmpty(factories) ? factories.get(0) : null;
    }

    @Override
    public Factory findByName(String factoryName) {
        return this.baseMapper.findByName(factoryName);
    }

    @Override
    public IPage<Factory> findFactories(QueryRequest request, Factory factory) {
        if (StringUtils.isNotBlank(factory.getCreateTimeFrom()) &&
                StringUtils.equals(factory.getCreateTimeFrom(), factory.getCreateTimeTo())) {
            factory.setCreateTimeFrom(factory.getCreateTimeFrom() + FebsConstant.DAY_START_PATTERN_SUFFIX);
            factory.setCreateTimeTo(factory.getCreateTimeTo() + FebsConstant.DAY_END_PATTERN_SUFFIX);
        }
        Page<Factory> page = new Page<>(request.getPageNum(), request.getPageSize());
        page.setTotal(baseMapper.countFactoryDetail(factory));
        SortUtil.handlePageSort(request, page, "factoryId", FebsConstant.ORDER_ASC, false);
        return baseMapper.findFactoryDetailPage(page, factory);
    }

    @Override
    public List<Factory> findFactories(Factory factory) {
        if (StringUtils.isNotBlank(factory.getCreateTimeFrom()) &&
                StringUtils.equals(factory.getCreateTimeFrom(), factory.getCreateTimeTo())) {
            factory.setCreateTimeFrom(factory.getCreateTimeFrom() + FebsConstant.DAY_START_PATTERN_SUFFIX);
            factory.setCreateTimeTo(factory.getCreateTimeTo() + FebsConstant.DAY_END_PATTERN_SUFFIX);
        }
        return baseMapper.findFactoryDetail(factory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createFactory(Factory factory) {
        this.save(factory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFactory(Factory factory) {
        factory.setModifyTime(new Date());
        updateById(factory);
        this.saveOrUpdate(factory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFactory(Factory factory) {
//        LambdaQueryWrapper<Factory> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(Factory::getFactoryId, factory.getFactoryId());
        removeById(factory.getFactoryId());
    }
}
