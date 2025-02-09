package com.ruoyi.rdbms.service;

import com.ruoyi.rdbms.entity.domain.ConnectInfo;
import com.ruoyi.rdbms.entity.vo.ConnectInfoVO;
import com.ruoyi.rdbms.entity.vo.TableVO;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 连接配置Service接口
 *
 * @author wsitm
 * @date 2025-01-11
 */
public interface IConnectInfoService {
    /**
     * 查询连接配置
     *
     * @param connectId 连接配置主键
     * @return 连接配置
     */
    public ConnectInfoVO selectConnectInfoByConnectId(Long connectId);

    /**
     * 查询连接配置列表
     *
     * @param connectInfo 连接配置
     * @return 连接配置集合
     */
    public List<ConnectInfoVO> selectConnectInfoList(ConnectInfo connectInfo);

    /**
     * 新增连接配置
     *
     * @param connectInfo 连接配置
     * @return 结果
     */
    public int insertConnectInfo(ConnectInfo connectInfo);

    /**
     * 修改连接配置
     *
     * @param connectInfo 连接配置
     * @return 结果
     */
    public int updateConnectInfo(ConnectInfo connectInfo);

    /**
     * 批量删除连接配置
     *
     * @param connectIds 需要删除的连接配置主键集合
     * @return 结果
     */
    public int deleteConnectInfoByConnectIds(Long[] connectIds);

    /**
     * 删除连接配置信息
     *
     * @param connectId 连接配置主键
     * @return 结果
     */
    public int deleteConnectInfoByConnectId(Long connectId);

    /**
     * @param connectInfo 连接信息
     * @return 布尔
     */
    boolean checkConnectInfo(ConnectInfo connectInfo);

    /**
     * 获取连接所有表格详细信息
     *
     * @param connectId 连接ID
     * @return 表格信息
     */
    List<TableVO> getTableInfo(Long connectId);

    /**
     * 刷新缓存
     *
     * @param connectId 连接ID
     * @return 布尔
     */
    boolean flushCahce(Long connectId);

    /**
     * 生成表格DDL
     *
     * @param connectId 连接ID
     * @param database  数据库类型
     * @return DDL
     */
    Map<String, String[]> genTableDDL(Long connectId, String database);

    void exportTableInfo(HttpServletResponse response, Long connectId, String[] skipStrArr) throws IOException;
}
