package com.ruoyi.schemax.entity.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 驱动管理对象 dim_jdbc_info
 *
 * @author wsitm
 * @date 2025-01-11
 */
public class JdbcInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 驱动ID
     */
    private Long jdbcId;

    /**
     * 驱动名称
     */
    @Excel(name = "驱动名称")
    private String jdbcName;

    /**
     * 驱动文件
     */
    @Excel(name = "驱动文件")
    private String jdbcFile;

    /**
     * 驱动类名称
     */
    @Excel(name = "驱动类名称")
    private String driverClass;

    public void setJdbcId(Long jdbcId) {
        this.jdbcId = jdbcId;
    }

    public Long getJdbcId() {
        return jdbcId;
    }

    public void setJdbcName(String jdbcName) {
        this.jdbcName = jdbcName;
    }

    public String getJdbcName() {
        return jdbcName;
    }

    public void setJdbcFile(String jdbcFile) {
        this.jdbcFile = jdbcFile;
    }

    public String getJdbcFile() {
        return jdbcFile;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("jdbcId", getJdbcId())
                .append("jdbcName", getJdbcName())
                .append("jdbcFile", getJdbcFile())
                .append("driverClass", getDriverClass())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .toString();
    }
}
