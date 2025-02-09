package com.ruoyi.rdbms.mapper;

import com.ruoyi.rdbms.entity.domain.ConnectInfo;
import com.ruoyi.rdbms.entity.vo.ConnectInfoVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 连接配置Mapper接口
 *
 * @author wsitm
 * @date 2025-01-11
 */
@Mapper
public interface ConnectInfoMapper {
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
     * 删除连接配置
     *
     * @param connectId 连接配置主键
     * @return 结果
     */
    public int deleteConnectInfoByConnectId(Long connectId);

    /**
     * 批量删除连接配置
     *
     * @param connectIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteConnectInfoByConnectIds(Long[] connectIds);
}
