package cc.mrbird.febs.order.controller;

import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.order.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Zoybzo
 */
@Controller("orderView")
@RequiredArgsConstructor
public class ViewController extends BaseController {

    private final IOrderService orderService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "order/order")
    @RequiresPermissions("order:view")
    public String orderIndex() {
        return FebsUtil.view("order/order");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "order/bid")
    @RequiresPermissions("order:view")
    public String winOrderIndex() {
        return FebsUtil.view("order/winOrder");
    }
}
