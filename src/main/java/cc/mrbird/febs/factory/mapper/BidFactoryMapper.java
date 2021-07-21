package cc.mrbird.febs.factory.mapper;

import cc.mrbird.febs.factory.entity.BidFactory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 竞标工厂表 Mapper
 *
 * @author zoybzo
 * @date 2021-07-15 18:58:07
 */
public interface BidFactoryMapper extends BaseMapper<BidFactory> {

    long countBidFactoryDetail(@Param("bidFactory") BidFactory bidFactory);

    <T> IPage<BidFactory> findBidFactoryDetailPage(Page<T> page, @Param("bidFactory") BidFactory bidFactory);

    List<BidFactory> findBidFactoryDetail(@Param("bidFactory") BidFactory bidFactory);
}
