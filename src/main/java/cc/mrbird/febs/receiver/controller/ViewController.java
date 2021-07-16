package cc.mrbird.febs.receiver.controller;

import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.receiver.service.IReceiverService;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Zoybzo
 */
@Controller("receiverView")
@RequiredArgsConstructor
public class ViewController extends BaseController {

    private final IReceiverService receiverService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "receiver/receiver")
    @RequiresPermissions("receiver:view")
    public String receiverIndex() {
        return FebsUtil.view("receiver/receiver");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "receiver/add")
    @RequiresPermissions("receiver:view")
    public String receiverAddIndex() {
        return FebsUtil.view("receiver/add");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "receiver/update/{receiverName}");

    public String receiverUpdate() {
        return FebsUtil.view("receiver/update");
    }
}
