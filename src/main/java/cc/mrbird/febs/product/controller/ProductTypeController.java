package cc.mrbird.febs.product.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.product.entity.ProductType;
import cc.mrbird.febs.product.service.IProductTypeService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 产品类型表 Controller
 *
 * @author zoybzo
 * @date 2021-07-15 18:58:21
 */
@Slf4j
@Validated
@Controller
@RequiredArgsConstructor
public class ProductTypeController extends BaseController {

    private final IProductTypeService productTypeService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "productType")
    public String productTypeIndex() {
        return FebsUtil.view("productType/productType");
    }

    @GetMapping("productType")
    @ResponseBody
    @RequiresPermissions("productType:view")
    public FebsResponse getAllProductTypes(ProductType productType) {
        return new FebsResponse().success().data(productTypeService.findProductTypes(productType));
    }

    @GetMapping("productType/getAllProductType")
    @ResponseBody
    public FebsResponse getAllProductType(QueryRequest request, ProductType productType) {
        List<ProductType> productTypeList = productTypeService.findProductTypes(productType);
        return new FebsResponse().success().data(productTypeList);
    }

    @GetMapping("productType/list")
    @ResponseBody
    @RequiresPermissions("productType:view")
    public FebsResponse productTypeList(QueryRequest request, ProductType productType) {
        Map<String, Object> dataTable = getDataTable(this.productTypeService.findProductTypes(request, productType));
        return new FebsResponse().success().data(dataTable);
    }

    @ControllerEndpoint(operation = "新增ProductType", exceptionMessage = "新增ProductType失败")
    @PostMapping("productType/productTypeAdd")
    @ResponseBody
    @RequiresPermissions("productType:add")
    public FebsResponse addProductType(@Valid ProductType productType) {
        productType.setCreateTime(new Date());
        this.productTypeService.createProductType(productType);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "删除ProductType", exceptionMessage = "删除ProductType失败")
    @GetMapping("productType/delete")
    @ResponseBody
    @RequiresPermissions("productType:delete")
    public FebsResponse deleteProductType(ProductType productType) {
        this.productTypeService.deleteProductType(productType);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改ProductType", exceptionMessage = "修改ProductType失败")
    @PostMapping("productType/update")
    @ResponseBody
    @RequiresPermissions("productType:update")
    public FebsResponse updateProductType(ProductType productType) {
        this.productTypeService.updateProductType(productType);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改ProductType", exceptionMessage = "导出Excel失败")
    @PostMapping("productType/excel")
    @ResponseBody
    @RequiresPermissions("productType:export")
    public void export(QueryRequest queryRequest, ProductType productType, HttpServletResponse response) {
        List<ProductType> productTypes = this.productTypeService.findProductTypes(queryRequest, productType).getRecords();
        ExcelKit.$Export(ProductType.class, response).downXlsx(productTypes, false);
    }
}
