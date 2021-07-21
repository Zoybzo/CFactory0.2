package cc.mrbird.febs.factory.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.entity.Strings;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.factory.entity.Equipment;
import cc.mrbird.febs.factory.entity.UserFactory;
import cc.mrbird.febs.factory.service.IEquipmentService;
import cc.mrbird.febs.order.entity.OrderEquipment;
import cc.mrbird.febs.order.service.IOrderEquipmentService;
import cc.mrbird.febs.system.entity.User;
import cc.mrbird.febs.system.service.IUserRoleService;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
 * 设备表 Controller
 *
 * @author zoybzo
 * @date 2021-07-15 14:06:09
 */
@Slf4j
@Validated
@Controller
@RequiredArgsConstructor
public class EquipmentController extends BaseController {

    private final IEquipmentService equipmentService;
    private final IUserRoleService userRoleService;
    private final IOrderEquipmentService orderEquipmentService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "equipment")
    public String equipmentIndex() {
        return FebsUtil.view("equipment/equipment");
    }

    @GetMapping("equipment")
    @ResponseBody
    @RequiresPermissions("equipment:view")
    public FebsResponse getAllEquipments(Equipment equipment) {
        return new FebsResponse().success().data(equipmentService.findEquipments(equipment));
    }

    @GetMapping("equipment/list")
    @ResponseBody
    @RequiresPermissions("equipment:view")
    public FebsResponse equipmentList(QueryRequest request, Equipment equipment, HttpServletRequest httpServletRequest) {
        String userId = String.valueOf(((User) httpServletRequest.getSession().getAttribute("user")).getUserId());
        Long role = userRoleService.findRoleIdByUserId(userId);
//        if (role != 1L) {
        equipment.setUsingUserId(userId);
//        } else equipment.setUsingUserId(null);
        Map<String, Object> dataTable = getDataTable(this.equipmentService.findEquipments(request, equipment));
        return new FebsResponse().success().data(dataTable);
    }

    @GetMapping("equipment/getAllMyEquipment/{selectedFactoryId}")
    @ResponseBody
    @RequiresPermissions("equipment:view")
    public FebsResponse myEquipmentList(@PathVariable String selectedFactoryId, QueryRequest request, Equipment equipment, HttpServletRequest httpServletRequest) {
        equipment.setUsingFactory(Long.parseLong(selectedFactoryId));
        Map<String, Object> dataTable = getDataTable(this.equipmentService.findEquipments(request, equipment));
        return new FebsResponse().success().data(dataTable);
    }

    @ControllerEndpoint(operation = "新增Equipment", exceptionMessage = "新增Equipment失败")
    @PostMapping("equipment/equipmentAdd")
    @ResponseBody
    @RequiresPermissions("equipment:add")
    public FebsResponse addEquipment(@Valid Equipment equipment, HttpServletRequest request) {
        equipment.setCreateTime(new Date());
        equipment.setStatus("0");
        equipment.setUsingFactory(equipment.getOwnedFactory());
        this.equipmentService.createEquipment(equipment);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "删除Equipment", exceptionMessage = "删除Equipment失败")
    @GetMapping("equipment/delete/{equipmentIds}")
    @ResponseBody
    @RequiresPermissions("equipment:delete")
    public FebsResponse deleteEquipment(@NotBlank(message = "{required}") @PathVariable String equipmentIds, Equipment equipment) {
        if (this.equipmentService.doesEquipmentUsing(StringUtils.split(equipmentIds, Strings.COMMA)) || this.equipmentService.doesEquipmentRent(StringUtils.split(equipmentIds, Strings.COMMA))) {
            return new FebsResponse().fail();
        }
        for (String it : (StringUtils.split(equipmentIds, Strings.COMMA))) {
            equipment.setEquipmentId(Long.valueOf(it));
            this.equipmentService.deleteEquipment(equipment);
        }
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改Equipment", exceptionMessage = "修改Equipment失败")
    @PostMapping("equipment/update")
    @ResponseBody
    @RequiresPermissions("equipment:update")
    public FebsResponse updateEquipment(Equipment equipment) {
        this.equipmentService.updateEquipment(equipment);
        return new FebsResponse().success();
    }

    @GetMapping("equipment/rentList")
    @ResponseBody
    @RequiresPermissions("equipment:view")
    public FebsResponse equipmentRentList(QueryRequest request, Equipment equipment, HttpServletRequest httpServletRequest) {
        String userId = String.valueOf(((User) httpServletRequest.getSession().getAttribute("user")).getUserId());
        Long role = userRoleService.findRoleIdByUserId(userId);
        equipment.setOwnedUserId("1");
        if (role != 1L) {
            equipment.setStatus("0");
        } else {
            equipment.setStatus(null);
        }
        Map<String, Object> dataTable = getDataTable(this.equipmentService.findEquipments(request, equipment));
        return new FebsResponse().success().data(dataTable);
    }

    @ControllerEndpoint(operation = "租用Equipment", exceptionMessage = "租用Equipment失败")
    @PostMapping("equipment/rent")
    @ResponseBody
    @RequiresPermissions("equipment:update")
    public FebsResponse rentEquipment(Equipment equipment) {
        this.equipmentService.rentEquipment(equipment);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "归还Equipment", exceptionMessage = "归还Equipment失败")
    @PostMapping("equipment/return")
    @ResponseBody
    @RequiresPermissions("equipment:update")
    public FebsResponse equipmentReturnIndex(QueryRequest queryRequest, Equipment equipment) {
        equipment = this.equipmentService.findById(String.valueOf(equipment.getEquipmentId()));
        if (equipment.getOwnedFactory().equals(equipment.getUsingFactory()) || equipment.getStatus().equals("1")) {
            return new FebsResponse().fail();
        } else equipment.setUsingFactory(equipment.getOwnedFactory());

        OrderEquipment orderEquipment = new OrderEquipment();
        orderEquipment.setEquipmentId1(equipment.getEquipmentId());
        orderEquipment.setEquipmentId2(equipment.getEquipmentId());
        orderEquipment.setEquipmentId3(equipment.getEquipmentId());
        List<OrderEquipment> orderEquipmentList = orderEquipmentService.findOrderEquipmentsWithEquipmentIds(orderEquipment);
        for (OrderEquipment it : orderEquipmentList) {
            if (it.getEquipmentId1() != null)
                if (it.getEquipmentId1().equals(equipment.getEquipmentId())) it.setEquipmentId1(null);
            if (it.getEquipmentId2() != null)
                if (it.getEquipmentId2().equals(equipment.getEquipmentId())) it.setEquipmentId2(null);
            if (it.getEquipmentId3() != null)
                if (it.getEquipmentId3().equals(equipment.getEquipmentId())) it.setEquipmentId3(null);
            this.orderEquipmentService.updateOrderEquipment(it);
        }

        this.equipmentService.updateEquipment(equipment);
        return new FebsResponse().success();
    }


    @ControllerEndpoint(operation = "修改Equipment", exceptionMessage = "导出Excel失败")
    @PostMapping("equipment/excel")
    @ResponseBody
    @RequiresPermissions("equipment:export")
    public void export(QueryRequest queryRequest, Equipment equipment, HttpServletResponse response) {
        List<Equipment> equipments = this.equipmentService.findEquipments(queryRequest, equipment).getRecords();
        ExcelKit.$Export(Equipment.class, response).downXlsx(equipments, false);
    }
}
