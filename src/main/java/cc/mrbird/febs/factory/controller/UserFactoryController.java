package cc.mrbird.febs.factory.controller;

import cc.mrbird.febs.common.annotation.ControllerEndpoint;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsResponse;
import cc.mrbird.febs.common.entity.QueryRequest;
import cc.mrbird.febs.factory.entity.UserFactory;
import cc.mrbird.febs.factory.service.IUserFactoryService;
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
 * 用户工厂表 Controller
 *
 * @author zoybzo
 * @date 2021-07-15 14:05:51
 */
@Slf4j
@Validated
@Controller
@RequiredArgsConstructor
public class UserFactoryController extends BaseController {

    private final IUserFactoryService userFactoryService;

    @GetMapping(FebsConstant.VIEW_PREFIX + "userFactory")
    public String userFactoryIndex(){
        return FebsUtil.view("userFactory/userFactory");
    }

    @GetMapping("userFactory")
    @ResponseBody
    @RequiresPermissions("userFactory:list")
    public FebsResponse getAllUserFactorys(UserFactory userFactory) {
        return new FebsResponse().success().data(userFactoryService.findUserFactorys(userFactory));
    }

    @GetMapping("userFactory/list")
    @ResponseBody
    @RequiresPermissions("userFactory:list")
    public FebsResponse userFactoryList(QueryRequest request, UserFactory userFactory) {
        Map<String, Object> dataTable = getDataTable(this.userFactoryService.findUserFactorys(request, userFactory));
        return new FebsResponse().success().data(dataTable);
    }

    @ControllerEndpoint(operation = "新增UserFactory", exceptionMessage = "新增UserFactory失败")
    @PostMapping("userFactory")
    @ResponseBody
    @RequiresPermissions("userFactory:add")
    public FebsResponse addUserFactory(@Valid UserFactory userFactory) {
        this.userFactoryService.createUserFactory(userFactory);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "删除UserFactory", exceptionMessage = "删除UserFactory失败")
    @GetMapping("userFactory/delete")
    @ResponseBody
    @RequiresPermissions("userFactory:delete")
    public FebsResponse deleteUserFactory(UserFactory userFactory) {
        this.userFactoryService.deleteUserFactory(userFactory);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改UserFactory", exceptionMessage = "修改UserFactory失败")
    @PostMapping("userFactory/update")
    @ResponseBody
    @RequiresPermissions("userFactory:update")
    public FebsResponse updateUserFactory(UserFactory userFactory) {
        this.userFactoryService.updateUserFactory(userFactory);
        return new FebsResponse().success();
    }

    @ControllerEndpoint(operation = "修改UserFactory", exceptionMessage = "导出Excel失败")
    @PostMapping("userFactory/excel")
    @ResponseBody
    @RequiresPermissions("userFactory:export")
    public void export(QueryRequest queryRequest, UserFactory userFactory, HttpServletResponse response) {
        List<UserFactory> userFactorys = this.userFactoryService.findUserFactorys(queryRequest, userFactory).getRecords();
        ExcelKit.$Export(UserFactory.class, response).downXlsx(userFactorys, false);
    }
}
