package cc.mrbird.febs.factory.service;

import cc.mrbird.febs.factory.entity.BidFactory;

import cc.mrbird.febs.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 竞标工厂表 Service接口
 *
 * @author zoybzo
 * @date 2021-07-15 18:58:07
 */
public interface IBidFactoryService extends IService<BidFactory> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param bidFactory bidFactory
     * @return IPage
     */
    IPage<BidFactory> findBidFactorys(QueryRequest request, BidFactory bidFactory);

    /**
     * 查询（所有）
     *
     * @param bidFactory bidFactory
     * @return List
     */
    List<BidFactory> findBidFactorys(BidFactory bidFactory);

    /**
     * 新增
     *
     * @param bidFactory bidFactory
     */
    void createBidFactory(BidFactory bidFactory);

    /**
     * 修改
     *
     * @param bidFactory bidFactory
     */
    void updateBidFactory(BidFactory bidFactory);

    /**
     * 删除
     *
     * @param bidFactory bidFactory
     */
    void deleteBidFactory(BidFactory bidFactory);

    BidFactory findById(String bidFactoryId);
}
