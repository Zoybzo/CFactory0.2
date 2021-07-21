package cc.mrbird.febs.factory.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.factory.entity.EquipmentType;
import cc.mrbird.febs.factory.service.IEquipmentTypeService;
import cc.mrbird.febs.product.entity.ProductType;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author zoybzo
 * @date 2021-07-19 16:42:37
 */
@Slf4j
@Validated
@Controller
@RequiredArgsConstructor
public class EquipmentTypeController extends BaseController {

    private final IEquipmentTypeService equipmentTypeService;

    @GetMapping("equipmentType")
    @ResponseBody
    @RequiresPermissions("equipmentType:view")
    public FebsResponse getAllEquipmentTypes(EquipmentType equipmentType) {
        return new FebsResponse().success().data(equipmentTypeService.findEquipmentTypes(equipmentType));
    }

    @GetMapping("equipmentType/list")
    @ResponseBody
    @RequiresPermissions("equipmentType:view")
    public FebsResponse equipmentTypeList(QueryRequest request, EquipmentType equipmentType) {
        Map<String, Object> dataTable = getDataTable(this.equipmentTypeService.findEquipmentTypes(request, equipmentType));
        return new FebsResponse().success().data(dataTable);
    }

    @GetMapping("equipmentType/getAllEquipmentType")
    @ResponseBody
    public FebsResponse getAllEquipmentType(QueryRequest request, EquipmentType equipmentType) {
        List<EquipmentType> equipmentTypeList = equipmentTypeService.findEquipmentTypes(equipmentType);
        return new FebsResponse().success().data(equipmentTypeList);
    }

    @ControllerEndpoint(operation = "新增EquipmentType", exceptionMessage = "新增EquipmentType失败")
    @PostMapping("equipmentType/equipmentTypeAdd")
    @ResponseBody
    @RequiresPermissions("equipmentType:add")
    public FebsResponse addEquipmentType(@Valid EquipmentType equipmentType) {
        equipmentType.setCreateTime(new Date());
        this.equipmentTypeService.createEquipmentType(equipmentType);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "删除EquipmentType", exceptionMessage = "删除EquipmentType失败")
    @GetMapping("equipmentType/delete")
    @ResponseBody
    @RequiresPermissions("equipmentType:delete")
    public FebsResponse deleteEquipmentType(EquipmentType equipmentType) {
        this.equipmentTypeService.deleteEquipmentType(equipmentType);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改EquipmentType", exceptionMessage = "修改EquipmentType失败")
    @PostMapping("equipmentType/update")
    @ResponseBody
    @RequiresPermissions("equipmentType:update")
    public FebsResponse updateEquipmentType(EquipmentType equipmentType) {
        this.equipmentTypeService.updateEquipmentType(equipmentType);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改EquipmentType", exceptionMessage = "导出Excel失败")
    @PostMapping("equipmentType/excel")
    @ResponseBody
    @RequiresPermissions("equipmentType:export")
    public void export(QueryRequest queryRequest, EquipmentType equipmentType, HttpServletResponse response) {
        List<EquipmentType> equipmentTypes = this.equipmentTypeService.findEquipmentTypes(queryRequest, equipmentType).getRecords();
        ExcelKit.$Export(EquipmentType.class, response).downXlsx(equipmentTypes, false);
    }
}
