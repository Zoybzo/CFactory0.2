package cc.mrbird.febs.product.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.product.entity.Product;
import cc.mrbird.febs.product.service.IProductService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
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

    @GetMapping(FebsConstant.VIEW_PREFIX + "product")
    public String productIndex(){
        return FebsUtil.view("product/product");
    }

    @GetMapping("product")
    @ResponseBody
    @RequiresPermissions("product:list")
    public FebsResponse getAllProducts(Product product) {
        return new FebsResponse().success().data(productService.findProducts(product));
    }

    @GetMapping("product/list")
    @ResponseBody
    @RequiresPermissions("product:list")
    public FebsResponse productList(QueryRequest request, Product product) {
        Map<String, Object> dataTable = getDataTable(this.productService.findProducts(request, product));
        return new FebsResponse().success().data(dataTable);
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

    @ControllerEndpoint(operation = "修改Product", exceptionMessage = "导出Excel失败")
    @PostMapping("product/excel")
    @ResponseBody
    @RequiresPermissions("product:export")
    public void export(QueryRequest queryRequest, Product product, HttpServletResponse response) {
        List<Product> products = this.productService.findProducts(queryRequest, product).getRecords();
        ExcelKit.$Export(Product.class, response).downXlsx(products, false);
    }
}
