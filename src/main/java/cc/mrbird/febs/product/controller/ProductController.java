package cc.mrbird.febs.product.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.factory.entity.EquipmentType;
import cc.mrbird.febs.factory.entity.Factory;
import cc.mrbird.febs.factory.entity.UserFactory;
import cc.mrbird.febs.product.entity.Product;
import cc.mrbird.febs.product.service.IProductService;
import cc.mrbird.febs.product.service.IProductTypeService;
import cc.mrbird.febs.system.entity.User;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 产品表 Controller
 *
 * @author zoybzo
 * @date 2021-07-15 18:58:25
 */
@Slf4j
@Validated
@Controller
@RequiredArgsConstructor
public class ProductController extends BaseController {

    private final IProductService productService;
    private final IProductTypeService productTypeService;

    @GetMapping("product")
    @ResponseBody
    @RequiresPermissions("product:view")
    public FebsResponse getAllProducts(Product product) {
        return new FebsResponse().success().data(productService.findProducts(product));
    }

    @GetMapping("product/list")
    @ResponseBody
    @RequiresPermissions("product:view")
    public FebsResponse productList(QueryRequest request, Product product) {
        Map<String, Object> dataTable = getDataTable(this.productService.findProducts(request, product));
        return new FebsResponse().success().data(dataTable);
    }

    @ControllerEndpoint(operation = "新增Product", exceptionMessage = "新增Product失败")
    @PostMapping("product/productAdd")
    @ResponseBody
    @RequiresPermissions("product:add")
    public FebsResponse addFactory(@Valid Product product, HttpServletRequest request) {
        product.setCreateTime(new Date());
        product.setProductTypeName((this.productTypeService.findById(String.valueOf(product.getProductTypeId()))).getProductTypeName());
        this.productService.createProduct(product);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "新增Product", exceptionMessage = "新增Product失败")
    @PostMapping("product")
    @ResponseBody
    @RequiresPermissions("product:add")
    public FebsResponse addProduct(@Valid Product product) {
        this.productService.createProduct(product);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "删除Product", exceptionMessage = "删除Product失败")
    @GetMapping("product/delete")
    @ResponseBody
    @RequiresPermissions("product:delete")
    public FebsResponse deleteProduct(Product product) {
        this.productService.deleteProduct(product);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改Product", exceptionMessage = "修改Product失败")
    @PostMapping("product/update")
    @ResponseBody
    @RequiresPermissions("product:update")
    public FebsResponse updateProduct(Product product) {
        this.productService.updateProduct(product);
        return new FebsResponse().success();
    }

    @GetMapping("product/getAllProduct")
    @ResponseBody
    public FebsResponse getAllEquipmentType(QueryRequest request, Product product) {
        List<Product> productList = productService.findProducts(product);
        return new FebsResponse().success().data(productList);
    }

    @ControllerEndpoint(operation = "导出Product", exceptionMessage = "导出Excel失败")
    @PostMapping("product/excel")
    @ResponseBody
    @RequiresPermissions("product:export")
    public void export(QueryRequest queryRequest, Product product, HttpServletResponse response) {
        List<Product> products = this.productService.findProducts(queryRequest, product).getRecords();
        ExcelKit.$Export(Product.class, response).downXlsx(products, false);
    }
}
