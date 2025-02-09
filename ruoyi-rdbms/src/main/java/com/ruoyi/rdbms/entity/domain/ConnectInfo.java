package com.ruoyi.rdbms.entity.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 连接配置对象 dim_connect_info
 * 
 * @author wsitm
 * @date 2025-01-11
 */
public class ConnectInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 连接ID */
    private Long connectId;

    /** 连接名称 */
    @Excel(name = "连接名称")
    private String connectName;

    /** 驱动ID */
    @Excel(name = "驱动ID")
    private Long jdbcId;

    /** JDBC URL */
    @Excel(name = "JDBC URL")
    private String jdbcUrl;

    /** 用户 */
    @Excel(name = "用户")
    private String username;

    /** 密码 */
    @Excel(name = "密码")
    private String password;

    public void setConnectId(Long connectId) 
    {
        this.connectId = connectId;
    }

    public Long getConnectId() 
    {
        return connectId;
    }
    public void setConnectName(String connectName) 
    {
        this.connectName = connectName;
    }

    public String getConnectName() 
    {
        return connectName;
    }
    public void setJdbcId(Long jdbcId) 
    {
        this.jdbcId = jdbcId;
    }

    public Long getJdbcId() 
    {
        return jdbcId;
    }
    public void setJdbcUrl(String jdbcUrl) 
    {
        this.jdbcUrl = jdbcUrl;
    }

    public String getJdbcUrl() 
    {
        return jdbcUrl;
    }
    public void setUsername(String username) 
    {
        this.username = username;
    }

    public String getUsername() 
    {
        return username;
    }
    public void setPassword(String password) 
    {
        this.password = password;
    }

    public String getPassword() 
    {
        return password;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("connectId", getConnectId())
            .append("connectName", getConnectName())
            .append("jdbcId", getJdbcId())
            .append("jdbcUrl", getJdbcUrl())
            .append("username", getUsername())
            .append("password", getPassword())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
