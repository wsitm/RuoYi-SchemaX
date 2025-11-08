package com.ruoyi.schemax.mapper;

import java.util.List;
import com.ruoyi.schemax.entity.domain.JdbcInfo;
import com.ruoyi.schemax.entity.vo.JdbcInfoVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 驱动管理Mapper接口
 *
 * @author wsitm
 * @date 2025-01-11
 */
@Mapper
public interface JdbcInfoMapper
{
    /**
     * 查询驱动管理
     *
     * @param jdbcId 驱动管理主键
     * @return 驱动管理
     */
    public JdbcInfoVo selectJdbcInfoByJdbcId(Long jdbcId);

    /**
     * 查询驱动管理列表
     *
     * @param jdbcInfo 驱动管理
     * @return 驱动管理集合
     */
    public List<JdbcInfoVo> selectJdbcInfoList(JdbcInfo jdbcInfo);

    /**
     * 新增驱动管理
     *
     * @param jdbcInfo 驱动管理
     * @return 结果
     */
    public int insertJdbcInfo(JdbcInfo jdbcInfo);

    /**
     * 修改驱动管理
     *
     * @param jdbcInfo 驱动管理
     * @return 结果
     */
    public int updateJdbcInfo(JdbcInfo jdbcInfo);

    /**
     * 删除驱动管理
     *
     * @param jdbcId 驱动管理主键
     * @return 结果
     */
    public int deleteJdbcInfoByJdbcId(Long jdbcId);

    /**
     * 批量删除驱动管理
     *
     * @param jdbcIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteJdbcInfoByJdbcIds(Long[] jdbcIds);
}
