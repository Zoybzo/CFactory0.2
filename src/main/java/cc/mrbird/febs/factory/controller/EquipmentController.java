package cc.mrbird.febs.factory.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.factory.entity.Equipment;
import cc.mrbird.febs.factory.service.IEquipmentService;
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

    @GetMapping(FebsConstant.VIEW_PREFIX + "equipment")
    public String equipmentIndex(){
        return FebsUtil.view("equipment/equipment");
    }

    @GetMapping("equipment")
    @ResponseBody
    @RequiresPermissions("equipment:list")
    public FebsResponse getAllEquipments(Equipment equipment) {
        return new FebsResponse().success().data(equipmentService.findEquipments(equipment));
    }

    @GetMapping("equipment/list")
    @ResponseBody
    @RequiresPermissions("equipment:list")
    public FebsResponse equipmentList(QueryRequest request, Equipment equipment) {
        Map<String, Object> dataTable = getDataTable(this.equipmentService.findEquipments(request, equipment));
        return new FebsResponse().success().data(dataTable);
    }

    @ControllerEndpoint(operation = "新增Equipment", exceptionMessage = "新增Equipment失败")
    @PostMapping("equipment")
    @ResponseBody
    @RequiresPermissions("equipment:add")
    public FebsResponse addEquipment(@Valid Equipment equipment) {
        this.equipmentService.createEquipment(equipment);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "删除Equipment", exceptionMessage = "删除Equipment失败")
    @GetMapping("equipment/delete")
    @ResponseBody
    @RequiresPermissions("equipment:delete")
    public FebsResponse deleteEquipment(Equipment equipment) {
        this.equipmentService.deleteEquipment(equipment);
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

    @ControllerEndpoint(operation = "修改Equipment", exceptionMessage = "导出Excel失败")
    @PostMapping("equipment/excel")
    @ResponseBody
    @RequiresPermissions("equipment:export")
    public void export(QueryRequest queryRequest, Equipment equipment, HttpServletResponse response) {
        List<Equipment> equipments = this.equipmentService.findEquipments(queryRequest, equipment).getRecords();
        ExcelKit.$Export(Equipment.class, response).downXlsx(equipments, false);
    }
}
