package cc.mrbird.febs.factory.controller;

import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.factory.entity.Factory;
import cc.mrbird.febs.factory.service.IFactoryService;
import cc.mrbird.febs.factory.service.IUserFactoryService;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author MrBird
 */
@Controller("factoryView")
@RequiredArgsConstructor
public class ViewController extends BaseController {

    private final IFactoryService factoryService;
    private final IUserFactoryService userFactoryService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "factory/factory")
    @RequiresPermissions("factory:view")
    public String factoryIndex() {
        return FebsUtil.view("factory/factory");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "factory/equipment")
    @RequiresPermissions("equipment:view")
    public String equipmentIndex() {
        return FebsUtil.view("factory/equipment");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "factory/detail/{factoryName}")
    @RequiresPermissions("factory:view")
    public String FactoryDetail(@PathVariable String factoryName, Model model) {
        resolveFactoryModel(factoryName, model, false);
        return FebsUtil.view("factory/factoryDetail");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "factory/update/{factoryName}")
    @RequiresPermissions("factory:update")
    public String FactoryUpdate(@PathVariable String factoryName, Model model) {
        resolveFactoryModel(factoryName, model, true);
        return FebsUtil.view("factory/factoryUpdate");
    }

    private void resolveFactoryModel(String factoryName, Model model, Boolean transform) {
        Factory factory = factoryService.findByName(factoryName);
        String userId = userFactoryService.findByFactoryId(String.valueOf(factory.getFactoryId()));
        factory.setUserId(userId);
        model.addAttribute("factory", factory);
    }
}
