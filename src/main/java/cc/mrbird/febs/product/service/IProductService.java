package cc.mrbird.febs.product.service;

import cc.mrbird.febs.product.entity.Product;

import cc.mrbird.febs.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 产品表 Service接口
 *
 * @author zoybzo
 * @date 2021-07-15 18:58:25
 */
public interface IProductService extends IService<Product> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param product product
     * @return IPage<Product>
     */
    IPage<Product> findProducts(QueryRequest request, Product product);

    /**
     * 查询（所有）
     *
     * @param product product
     * @return List<Product>
     */
    List<Product> findProducts(Product product);

    /**
     * 新增
     *
     * @param product product
     */
    void createProduct(Product product);

    /**
     * 修改
     *
     * @param product product
     */
    void updateProduct(Product product);

    /**
     * 删除
     *
     * @param product product
     */
    void deleteProduct(Product product);
}
