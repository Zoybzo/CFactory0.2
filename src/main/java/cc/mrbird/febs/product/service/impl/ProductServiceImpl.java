package cc.mrbird.febs.product.service.impl;

import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.utils.SortUtil;
import cc.mrbird.febs.factory.entity.Factory;
import cc.mrbird.febs.product.entity.Product;
import cc.mrbird.febs.product.entity.ProductType;
import cc.mrbird.febs.product.mapper.ProductMapper;
import cc.mrbird.febs.product.service.IProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import lombok.RequiredArgsConstructor;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Date;
import java.util.List;

/**
 * 产品表 Service实现
 *
 * @author zoybzo
 * @date 2021-07-15 18:58:25
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    private final ProductMapper productMapper;

    @Override
    public IPage<Product> findProducts(QueryRequest request, Product product) {
        if (StringUtils.isNotBlank(product.getCreateTimeFrom()) &&
                StringUtils.equals(product.getCreateTimeFrom(), product.getCreateTimeTo())) {
            product.setCreateTimeFrom(product.getCreateTimeFrom() + FebsConstant.DAY_START_PATTERN_SUFFIX);
            product.setCreateTimeTo(product.getCreateTimeTo() + FebsConstant.DAY_END_PATTERN_SUFFIX);
        }
        Page<Factory> page = new Page<>(request.getPageNum(), request.getPageSize());
        page.setTotal(baseMapper.countProductDetail(product));
        SortUtil.handlePageSort(request, page, "productId", FebsConstant.ORDER_ASC, false);
        return baseMapper.findProductDetailPage(page, product);
    }

    @Override
    public List<Product> findProducts(Product product) {
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createProduct(Product product) {
        this.save(product);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProduct(Product product) {
        Product product2 = findByName(product.getProductName());
        product2.setDescription(product.getDescription());
        product2.setProductName(product.getProductName());
        product2.setModifyTime(new Date());
        updateById(product2);
        this.saveOrUpdate(product2);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProduct(Product product) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        // TODO 设置删除条件
        this.remove(wrapper);
    }

    @Override
    public Product findById(String productId) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Product::getProductId, productId);
        List<Product> productList = productMapper.selectList(wrapper);
        if (!productList.isEmpty())
            return productList.get(0);
        return null;
    }


    public Product findByName(String productName) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Product::getProductName, productName);
        List<Product> productList = productMapper.selectList(wrapper);
        if (!productList.isEmpty())
            return productList.get(0);
        return null;
    }
}
