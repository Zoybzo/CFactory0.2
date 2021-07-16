package cc.mrbird.febs.product.service.impl;

import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.product.entity.Product;
import cc.mrbird.febs.product.mapper.ProductMapper;
import cc.mrbird.febs.product.service.IProductService;
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
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        // TODO 设置查询条件
        Page<Product> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.page(page, queryWrapper);
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
        this.saveOrUpdate(product);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProduct(Product product) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
	    // TODO 设置删除条件
	    this.remove(wrapper);
	}
}
