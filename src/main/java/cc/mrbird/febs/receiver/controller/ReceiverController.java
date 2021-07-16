package cc.mrbird.febs.receiver.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.receiver.entity.Receiver;
import cc.mrbird.febs.receiver.service.IReceiverService;
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
import java.util.List;
import java.util.Map;

/**
 * 收货人信息表 Controller
 *
 * @author zoybzo
 * @date 2021-07-15 18:57:54
 */
@Slf4j
@Validated
@Controller
@RequiredArgsConstructor
public class ReceiverController extends BaseController {

    private final IReceiverService receiverService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "receiver")
    public String receiverIndex() {
        return FebsUtil.view("receiver/receiver");
    }

    @GetMapping("receiver")
    @ResponseBody
    @RequiresPermissions("receiver:view")
    public FebsResponse getAllReceivers(Receiver receiver) {
        return new FebsResponse().success().data(receiverService.findReceivers(receiver));
    }

    @GetMapping("receiver/list")
    @ResponseBody
    @RequiresPermissions("receiver:view")
    public FebsResponse receiverList(QueryRequest request, Receiver receiver) {
        Map<String, Object> dataTable = getDataTable(this.receiverService.findReceivers(request, receiver));
        return new FebsResponse().success().data(dataTable);
    }

    @ControllerEndpoint(operation = "新增Receiver", exceptionMessage = "新增Receiver失败")
    @PostMapping("receiver/add")
    @ResponseBody
    @RequiresPermissions("receiver:add")
    public FebsResponse addReceiver(@Valid Receiver receiver, HttpServletRequest request) {
        receiver.setUserId(((User) (request.getSession().getAttribute("user"))).getUserId());
        this.receiverService.createReceiver(receiver);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "删除Receiver", exceptionMessage = "删除Receiver失败")
    @GetMapping("receiver/delete")
    @ResponseBody
    @RequiresPermissions("receiver:delete")
    public FebsResponse deleteReceiver(Receiver receiver) {
        this.receiverService.deleteReceiver(receiver);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改Receiver", exceptionMessage = "修改Receiver失败")
    @PostMapping("receiver/update")
    @ResponseBody
    @RequiresPermissions("receiver:update")
    public FebsResponse updateReceiver(Receiver receiver) {
        this.receiverService.updateReceiver(receiver);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改Receiver", exceptionMessage = "导出Excel失败")
    @PostMapping("receiver/excel")
    @ResponseBody
    @RequiresPermissions("receiver:export")
    public void export(QueryRequest queryRequest, Receiver receiver, HttpServletResponse response) {
        List<Receiver> receivers = this.receiverService.findReceivers(queryRequest, receiver).getRecords();
        ExcelKit.$Export(Receiver.class, response).downXlsx(receivers, false);
    }
}
