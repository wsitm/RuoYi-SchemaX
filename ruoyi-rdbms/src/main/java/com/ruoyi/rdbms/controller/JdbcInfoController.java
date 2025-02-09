package com.ruoyi.rdbms.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.rdbms.constant.RdbmsConstants;
import com.ruoyi.rdbms.entity.domain.JdbcInfo;
import com.ruoyi.rdbms.entity.vo.JdbcInfoVo;
import com.ruoyi.rdbms.service.IJdbcInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.Objects;

/**
 * 驱动管理Controller
 *
 * @author wsitm
 * @date 2025-01-11
 */
@RestController
@RequestMapping("/rdbms/jdbc")
public class JdbcInfoController extends BaseController {
    @Autowired
    private IJdbcInfoService jdbcInfoService;

    /**
     * 查询驱动管理列表
     */
    @PreAuthorize("@ss.hasPermi('rdbms:jdbc:list')")
    @GetMapping("/list")
    public TableDataInfo list(JdbcInfo jdbcInfo) {
        startPage();
        List<JdbcInfoVo> list = jdbcInfoService.selectJdbcInfoList(jdbcInfo);
        return getDataTable(list);
    }

    /**
     * 导出驱动管理列表
     */
    @PreAuthorize("@ss.hasPermi('rdbms:jdbc:export')")
    @Log(title = "驱动管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, JdbcInfo jdbcInfo) {
        List<JdbcInfoVo> list = jdbcInfoService.selectJdbcInfoList(jdbcInfo);
        ExcelUtil<JdbcInfoVo> util = new ExcelUtil<JdbcInfoVo>(JdbcInfoVo.class);
        util.exportExcel(response, list, "驱动管理数据");
    }

    /**
     * 获取驱动管理详细信息
     */
//    @PreAuthorize("@ss.hasPermi('rdbms:jdbc:query')")
    @GetMapping(value = "/{jdbcId}")
    public AjaxResult getInfo(@PathVariable("jdbcId") Long jdbcId) {
        return success(jdbcInfoService.selectJdbcInfoByJdbcId(jdbcId));
    }

    /**
     * 新增驱动管理
     */
    @PreAuthorize("@ss.hasPermi('rdbms:jdbc:add')")
    @Log(title = "驱动管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody JdbcInfo jdbcInfo) {
        return toAjax(jdbcInfoService.insertJdbcInfo(jdbcInfo));
    }

    /**
     * 修改驱动管理
     */
    @PreAuthorize("@ss.hasPermi('rdbms:jdbc:edit')")
    @Log(title = "驱动管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody JdbcInfo jdbcInfo) {
        return toAjax(jdbcInfoService.updateJdbcInfo(jdbcInfo));
    }

    /**
     * 删除驱动管理
     */
    @PreAuthorize("@ss.hasPermi('rdbms:jdbc:remove')")
    @Log(title = "驱动管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{jdbcIds}")
    public AjaxResult remove(@PathVariable Long[] jdbcIds) {
        return toAjax(jdbcInfoService.deleteJdbcInfoByJdbcIds(jdbcIds));
    }


    /**
     * 安装或卸载驱动
     *
     * @param jdbcId 驱动ID
     * @param action load/unload 安装/卸载
     */
    //    @PreAuthorize("@ss.hasPermi('rdbms:jdbc:load')")
    @PostMapping("/load/{jdbcId}/{action}")
    public AjaxResult load(@PathVariable Long jdbcId, @PathVariable String action) {
        return toAjax(jdbcInfoService.load(jdbcId, action));
    }


    /**
     * 通用上传请求（单个）
     */
    @PostMapping("/upload")
    public AjaxResult uploadFile(MultipartFile file) throws Exception {
        try {
            // 上传文件路径
            String fileName = Objects.requireNonNull(file.getOriginalFilename());
            // 上传并返回新文件名称
            File newFile = new File(RdbmsConstants.LIB_PATH, fileName);
            if (newFile.exists()) {
                throw new ServiceException("驱动文件已经存在！");
            }
            file.transferTo(new File(newFile.getAbsolutePath()));

            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName", fileName);
            return ajax;
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

}
