package cc.mrbird.febs.product.service.impl;

import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.product.entity.ProductType;
import cc.mrbird.febs.product.mapper.ProductTypeMapper;
import cc.mrbird.febs.product.service.IProductTypeService;
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
 * 产品类型表 Service实现
 *
 * @author zoybzo
 * @date 2021-07-15 18:58:21
 */
@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements IProductTypeService {

    private final ProductTypeMapper productTypeMapper;

    @Override
    public IPage<ProductType> findProductTypes(QueryRequest request, ProductType productType) {
        LambdaQueryWrapper<ProductType> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        Page<ProductType> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
    }

    @Override
    public List<ProductType> findProductTypes(ProductType productType) {
	    LambdaQueryWrapper<ProductType> queryWrapper = new LambdaQueryWrapper<>();
		// TODO 设置查询条件
		return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createProductType(ProductType productType) {
        this.save(productType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProductType(ProductType productType) {
        this.saveOrUpdate(productType);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProductType(ProductType productType) {
        LambdaQueryWrapper<ProductType> wrapper = new LambdaQueryWrapper<>();
	    // TODO 设置删除条件
	    this.remove(wrapper);
	}
}
