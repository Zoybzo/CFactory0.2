package cc.mrbird.febs.product.controller;

import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.product.entity.Product;
import cc.mrbird.febs.product.entity.ProductType;
import cc.mrbird.febs.product.service.IProductService;
import cc.mrbird.febs.product.service.IProductTypeService;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Zoybzo
 */
@Controller("productView")
@RequiredArgsConstructor
public class ViewController extends BaseController {

    private final IProductService productService;
    private final IProductTypeService productTypeService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "product/product")
    @RequiresPermissions("product:view")
    public String productIndex() {
        return FebsUtil.view("product/product");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "product/add")
    @RequiresPermissions("product:view")
    public String productAddIndex() {
        return FebsUtil.view("product/productAdd");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "product/update/{productId}")
    @RequiresPermissions("product:view")
    public String productUpdateIndex(@PathVariable String productId, Model model) {
        resolveProductModel(productId, model, false);
        return FebsUtil.view("product/productUpdate");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "product/productType")
    @RequiresPermissions("productType:view")
    public String productTypeIndex() {
        return FebsUtil.view("product/productType");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "productType/add")
    @RequiresPermissions("productType:add")
    public String productTypeAddIndex() {
        return FebsUtil.view("product/productTypeAdd");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "productType/update/{productTypeId}")
    @RequiresPermissions("productType:update")
    public String productTypeUpdateIndex(@PathVariable String productTypeId, Model model) {
        resolveProductTypeModel(productTypeId, model, false);
        return FebsUtil.view("product/productTypeUpdate");
    }

    private void resolveProductTypeModel(String productTypeId, Model model, Boolean transform) {
        ProductType productType = productTypeService.findById(productTypeId);
        model.addAttribute("productType", productType);
    }

    private void resolveProductModel(String productId, Model model, Boolean transform) {
        Product product = productService.findById(productId);
        model.addAttribute("product", product);
    }

}
