package cc.mrbird.febs.product.mapper;

import cc.mrbird.febs.factory.entity.Factory;
import cc.mrbird.febs.product.entity.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
 * 产品表 Mapper
 *
 * @author zoybzo
 * @date 2021-07-15 18:58:25
 */
public interface ProductMapper extends BaseMapper<Product> {

    long countProductDetail(@Param("product") Product product);

    <T> IPage<Product> findProductDetailPage(Page<T> page, @Param("product") Product product);
}
