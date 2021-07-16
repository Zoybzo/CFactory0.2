package cc.mrbird.febs.receiver.service.impl;

import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.receiver.entity.BidFactory;
import cc.mrbird.febs.receiver.mapper.BidFactoryMapper;
import cc.mrbird.febs.receiver.service.IBidFactoryService;
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
        LambdaQueryWrapper<BidFactory> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        Page<BidFactory> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<BidFactory> findBidFactorys(BidFactory bidFactory) {
	    LambdaQueryWrapper<BidFactory> queryWrapper = new LambdaQueryWrapper<>();
		// TODO 设置查询条件
		return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createBidFactory(BidFactory bidFactory) {
        this.save(bidFactory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBidFactory(BidFactory bidFactory) {
        this.saveOrUpdate(bidFactory);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBidFactory(BidFactory bidFactory) {
        LambdaQueryWrapper<BidFactory> wrapper = new LambdaQueryWrapper<>();
	    // TODO 设置删除条件
	    this.remove(wrapper);
	}
}
