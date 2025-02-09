package com.ruoyi.rdbms.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.rdbms.entity.vo.ConvertVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * DDL转换Service接口
 *
 * @author wsitm
 * @date 2025-01-27
 */
public interface IConvertService {

    /**
     * excel文件上传
     *
     * @param file 文件
     * @return univer数据
     */
    AjaxResult upload(MultipartFile file);

    /**
     * 转换DDL语句，可指定{database}类型
     *
     * @param convertVO DDL语句信息
     * @return 结果
     */
    AjaxResult convertDDL(ConvertVO convertVO);

}
