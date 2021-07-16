package cc.mrbird.febs.product.service;

import cc.mrbird.febs.product.entity.ProductType;

import cc.mrbird.febs.common.entity.QueryRequest;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 产品类型表 Service接口
 *
 * @author zoybzo
 * @date 2021-07-15 18:58:21
 */
public interface IProductTypeService extends IService<ProductType> {
    /**
     * 查询（分页）
     *
     * @param request QueryRequest
     * @param productType productType
     * @return IPage<ProductType>
     */
    IPage<ProductType> findProductTypes(QueryRequest request, ProductType productType);

    /**
     * 查询（所有）
     *
     * @param productType productType
     * @return List<ProductType>
     */
    List<ProductType> findProductTypes(ProductType productType);

    /**
     * 新增
     *
     * @param productType productType
     */
    void createProductType(ProductType productType);

    /**
     * 修改
     *
     * @param productType productType
     */
    void updateProductType(ProductType productType);

    /**
     * 删除
     *
     * @param productType productType
     */
    void deleteProductType(ProductType productType);
}
