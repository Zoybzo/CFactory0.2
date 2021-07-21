package cc.mrbird.febs.factory.service.impl;

import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.factory.entity.BidFactory;
import cc.mrbird.febs.factory.entity.Factory;
import cc.mrbird.febs.factory.mapper.BidFactoryMapper;
import cc.mrbird.febs.factory.service.IBidFactoryService;
import cc.mrbird.febs.order.entity.Order;
import org.apache.commons.lang3.StringUtils;
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
 * 竞标工厂表 Service实现
 *
 * @author zoybzo
 * @date 2021-07-15 18:58:07
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class BidFactoryServiceImpl extends ServiceImpl<BidFactoryMapper, BidFactory> implements IBidFactoryService {

    private final BidFactoryMapper bidFactoryMapper;

    @Override
    public IPage<BidFactory> findBidFactorys(QueryRequest request, BidFactory bidFactory) {
        Page<Factory> page = new Page<>(request.getPageNum(), request.getPageSize());
        page.setTotal(baseMapper.countBidFactoryDetail(bidFactory));
        SortUtil.handlePageSort(request, page, "bidFactoryId", FebsConstant.ORDER_ASC, false);
        return baseMapper.findBidFactoryDetailPage(page, bidFactory);
    }

    @Override
    public List<BidFactory> findBidFactorys(BidFactory bidFactory) {
        return baseMapper.findBidFactoryDetail(bidFactory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createBidFactory(BidFactory bidFactory) {
        this.save(bidFactory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBidFactory(BidFactory bidFactory) {
        updateById(bidFactory);
        this.saveOrUpdate(bidFactory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBidFactory(BidFactory bidFactory) {
        LambdaQueryWrapper<BidFactory> wrapper = new LambdaQueryWrapper<>();
        // TODO 设置删除条件
        this.remove(wrapper);
    }

    @Override
    public BidFactory findById(String bidFactoryId) {
        LambdaQueryWrapper<BidFactory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BidFactory::getBidFactoryId, bidFactoryId);
        List<BidFactory> bidFactoryList = bidFactoryMapper.selectList(wrapper);
        if (!bidFactoryList.isEmpty())
            return bidFactoryList.get(0);
        return null;
    }
}
