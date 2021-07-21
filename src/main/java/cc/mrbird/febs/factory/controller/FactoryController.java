package cc.mrbird.febs.factory.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.common.entity.Strings;
import cc.mrbird.febs.factory.entity.Equipment;
import cc.mrbird.febs.factory.entity.Factory;
import cc.mrbird.febs.factory.entity.UserFactory;
import cc.mrbird.febs.factory.service.IEquipmentService;
import cc.mrbird.febs.factory.service.IFactoryService;
import cc.mrbird.febs.factory.service.IUserFactoryService;
import cc.mrbird.febs.order.service.IOrderService;
import cc.mrbird.febs.system.entity.User;
import cc.mrbird.febs.system.service.IUserRoleService;
import com.wuwenze.poi.ExcelKit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 工厂表 Controller
 *
 * @author zoybzo
 * @date 2021-07-13 10:43:05
 */
@Slf4j
@Validated
@Controller
@RequiredArgsConstructor
public class FactoryController extends BaseController {

    private final IFactoryService factoryService;
    private final IUserRoleService userRoleService;
    private final IEquipmentService equipmentService;
    private final IUserFactoryService userFactoryService;
    private final IOrderService orderService;

    @GetMapping("{factoryName}")
    public Factory getFactory(@NotBlank(message = "{required}") @PathVariable String factoryName) {
        return factoryService.findFactoryDetailList(factoryName);
    }

    @GetMapping("factory/list/{userId}")
    @ResponseBody
    @RequiresPermissions("factory:view")
    public FebsResponse factoryList(@PathVariable String userId, QueryRequest request, Factory factory) {
        Long role = userRoleService.findRoleIdByUserId(userId);
        if (role != 1L) {
            factory.setUserId(userId);
        } else factory.setUserId(null);

        Map<String, Object> dataTable = getDataTable(this.factoryService.findFactories(request, factory));
        return new FebsResponse().success().data(dataTable);
    }

    @GetMapping("factory/equipment/list")
    @ResponseBody
    @RequiresPermissions("equipment:view")
    public FebsResponse equipmentList(QueryRequest request, Equipment equipment) {
        Map<String, Object> dataTable = getDataTable(this.equipmentService.findEquipments(request, equipment));
        return new FebsResponse().success().data(dataTable);
    }

    @GetMapping("factory/getAllMyFactory")
    @ResponseBody
    public FebsResponse getAllMyFactory(HttpServletRequest request) {
        Factory factory = new Factory();
        factory.setUserId(String.valueOf(((User) request.getSession().getAttribute("user")).getUserId()));
        List<Factory> factoryList = this.factoryService.findFactories(factory);
        return new FebsResponse().success().data(factoryList);
    }

    @GetMapping("factory/getAllFactory")
    @ResponseBody
    public FebsResponse getAllFactory() {
        List<Factory> factoryList = this.factoryService.findFactories(new Factory());
        return new FebsResponse().success().data(factoryList);
    }

    @ControllerEndpoint(operation = "新增Factory", exceptionMessage = "新增Factory失败")
    @PostMapping("factory/factoryAdd")
    @ResponseBody
    @RequiresPermissions("factory:add")
    public FebsResponse addFactory(@Valid Factory factory, HttpServletRequest request) {
        factory.setCreateTime(new Date());
        this.factoryService.createFactory(factory);
        UserFactory userFactory = new UserFactory();
        userFactory.setFactoryId(factory.getFactoryId());
        userFactory.setUserId(((User) request.getSession().getAttribute("user")).getUserId());
        this.userFactoryService.createUserFactory(userFactory);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "删除Factory", exceptionMessage = "删除Factory失败")
    @GetMapping("factory/delete/{factoryIds}")
    @ResponseBody
    @RequiresPermissions("factory:delete")
    public FebsResponse deleteFactory(@NotBlank(message = "{required}") @PathVariable String factoryIds, Factory factory, HttpServletRequest request) {
        if (this.orderService.doesFactoryHasOrder(StringUtils.split(factoryIds, Strings.COMMA))) {
            return new FebsResponse().fail();
        }
        for (String it : StringUtils.split(factoryIds, Strings.COMMA)) {
            factory.setFactoryId(Long.valueOf(it));
            this.factoryService.deleteFactory(factory);
            UserFactory userFactory = userFactoryService.findUserFactoryByFactoryId(it);
            this.userFactoryService.deleteUserFactory(userFactory);
        }
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改Factory", exceptionMessage = "修改Factory失败")
    @PostMapping("factory/update")
    @ResponseBody
    @RequiresPermissions("factory:update")
    public FebsResponse updateFactory(Factory factory) {
        this.factoryService.updateFactory(factory);
        return new FebsResponse().success();
    }

    @GetMapping("factory/excel")
    @ResponseBody
    @RequiresPermissions("factory:export")
    @ControllerEndpoint(operation = "修改Factory", exceptionMessage = "导出Excel失败")
    public void export(QueryRequest queryRequest, Factory factory, HttpServletResponse response) {
        ExcelKit.$Export(Factory.class, response)
                .downXlsx(factoryService.findFactories(queryRequest, factory).getRecords(), false);
    }
}
