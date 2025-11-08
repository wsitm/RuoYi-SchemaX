package com.ruoyi.schemax.controller;

import cn.hutool.core.lang.Dict;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.schemax.constant.DialectEnum;
import com.ruoyi.schemax.entity.domain.ConnectInfo;
import com.ruoyi.schemax.entity.vo.ConnectInfoVO;
import com.ruoyi.schemax.entity.vo.TableVO;
import com.ruoyi.schemax.service.IConnectInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 连接配置Controller
 *
 * @author wsitm
 * @date 2025-01-11
 */
@RestController
@RequestMapping("/rdbms/connect")
public class ConnectInfoController extends BaseController {
    @Autowired
    private IConnectInfoService connectInfoService;

    /**
     * 查询连接配置列表
     */
    @PreAuthorize("@ss.hasPermi('rdbms:connect:list')")
    @GetMapping("/list")
    public TableDataInfo list(ConnectInfo connectInfo) {
        startPage();
        List<ConnectInfoVO> list = connectInfoService.selectConnectInfoList(connectInfo);
        return getDataTable(list);
    }

    /**
     * 导出连接配置列表
     */
    @PreAuthorize("@ss.hasPermi('rdbms:connect:export')")
    @Log(title = "连接配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ConnectInfo connectInfo) {
        List<ConnectInfoVO> list = connectInfoService.selectConnectInfoList(connectInfo);
        ExcelUtil<ConnectInfoVO> util = new ExcelUtil<ConnectInfoVO>(ConnectInfoVO.class);
        util.exportExcel(response, list, "连接配置数据");
    }

    /**
     * 获取连接配置详细信息
     */
//    @PreAuthorize("@ss.hasPermi('rdbms:connect:query')")
    @GetMapping(value = "/{connectId}")
    public AjaxResult getInfo(@PathVariable("connectId") Long connectId) {
        return success(connectInfoService.selectConnectInfoByConnectId(connectId));
    }

    /**
     * 新增连接配置
     */
    @PreAuthorize("@ss.hasPermi('rdbms:connect:add')")
    @Log(title = "连接配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ConnectInfo connectInfo) {
        return toAjax(connectInfoService.insertConnectInfo(connectInfo));
    }

    /**
     * 修改连接配置
     */
    @PreAuthorize("@ss.hasPermi('rdbms:connect:edit')")
    @Log(title = "连接配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ConnectInfo connectInfo) {
        return toAjax(connectInfoService.updateConnectInfo(connectInfo));
    }

    /**
     * 删除连接配置
     */
    @PreAuthorize("@ss.hasPermi('rdbms:connect:remove')")
    @Log(title = "连接配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{connectIds}")
    public AjaxResult remove(@PathVariable Long[] connectIds) {
        return toAjax(connectInfoService.deleteConnectInfoByConnectIds(connectIds));
    }

    /**
     * 查询连接配置列表
     */
//    @PreAuthorize("@ss.hasPermi('rdbms:connect:check')")
    @PostMapping("/check")
    public AjaxResult check(@RequestBody ConnectInfo connectInfo) {
        startPage();
        return toAjax(connectInfoService.checkConnectInfo(connectInfo));
    }

    /**
     * 获取连接所有表格详细信息
     */
//    @PreAuthorize("@ss.hasPermi('rdbms:connect:info')")
    @GetMapping(value = "/tables/{connectId}")
    public R<List<TableVO>> getTableInfo(@PathVariable("connectId") Long connectId) {
        return R.ok(connectInfoService.getTableInfo(connectId));
    }


    /**
     * 获取所有的方言列表
     */
    @GetMapping("/dialects")
    public R<List<Dict>> dialects() {
        return R.ok(DialectEnum.getList());
    }

    /**
     * 刷新缓存
     */
    @PostMapping("/flush/{connectId}")
    public AjaxResult flush(@PathVariable("connectId") Long connectId) {
        return toAjax(connectInfoService.flushCahce(connectId));
    }


    /**
     * 获取连接所有表DDL语句
     */
//    @PreAuthorize("@ss.hasPermi('rdbms:connect:ddl')")
    @GetMapping(value = "/ddl/{connectId}")
    public R<Map<String, String[]>> getDDLInfo(@PathVariable("connectId") Long connectId, String database) {
        return R.ok(connectInfoService.genTableDDL(connectId, database));
    }

    /**
     * 导出表结构信息
     *
     * @param connectId 连接ID
     * @param skipStrs  通配符匹配，包含，? 任何单个，* 任何多个，! 剔除
     */
//    @PreAuthorize("@ss.hasPermi('rdbms:connect:info-export')")
    @Log(title = "连接配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export/{connectId}/tableInfo")
    public void exportTableInfo(HttpServletResponse response,
                                @PathVariable("connectId") Long connectId, String[] skipStrs) throws IOException {
        connectInfoService.exportTableInfo(response, connectId, skipStrs);
    }
}
