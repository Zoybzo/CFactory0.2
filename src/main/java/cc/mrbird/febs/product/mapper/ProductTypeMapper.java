package cc.mrbird.febs.product.mapper;

import cc.mrbird.febs.factory.entity.Factory;
import cc.mrbird.febs.product.entity.ProductType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * 产品类型表 Mapper
 *
 * @author zoybzo
 * @date 2021-07-15 18:58:21
 */
public interface ProductTypeMapper extends BaseMapper<ProductType> {

    long countProductTypeDetail(@Param("productType") ProductType productType);

    <T> IPage<ProductType> findProductTypeDetailPage(Page<T> page, @Param("productType") ProductType productType);

}
