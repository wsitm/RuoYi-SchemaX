package com.ruoyi.schemax.service.impl;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.schemax.entity.domain.JdbcInfo;
import com.ruoyi.schemax.entity.vo.JdbcInfoVo;
import com.ruoyi.schemax.mapper.JdbcInfoMapper;
import com.ruoyi.schemax.service.IJdbcInfoService;
import com.ruoyi.schemax.utils.RdbmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 驱动管理Service业务层处理
 *
 * @author wsitm
 * @date 2025-01-11
 */
@Service
public class JdbcInfoServiceImpl implements IJdbcInfoService {
    @Autowired
    private JdbcInfoMapper jdbcInfoMapper;

    /**
     * 查询驱动管理
     *
     * @param jdbcId 驱动管理主键
     * @return 驱动管理
     */
    @Override
    public JdbcInfo selectJdbcInfoByJdbcId(Long jdbcId) {
        return jdbcInfoMapper.selectJdbcInfoByJdbcId(jdbcId);
    }

    /**
     * 查询驱动管理列表
     *
     * @param jdbcInfo 驱动管理
     * @return 驱动管理
     */
    @Override
    public List<JdbcInfoVo> selectJdbcInfoList(JdbcInfo jdbcInfo) {
        List<JdbcInfoVo> jdbcInfoVoList = jdbcInfoMapper.selectJdbcInfoList(jdbcInfo);
        for (JdbcInfoVo jdbcInfoVo : jdbcInfoVoList) {
            jdbcInfoVo.setIsLoaded(RdbmsUtil.isLoadJdbcJar(jdbcInfoVo.getJdbcId()));
        }
        return jdbcInfoVoList;
    }

    /**
     * 新增驱动管理
     *
     * @param jdbcInfo 驱动管理
     * @return 结果
     */
    @Override
    @Transactional
    public int insertJdbcInfo(JdbcInfo jdbcInfo) {
        jdbcInfo.setCreateBy(SecurityUtils.getUsername());
        jdbcInfo.setCreateTime(DateUtils.getNowDate());
        int insert = jdbcInfoMapper.insertJdbcInfo(jdbcInfo);
        RdbmsUtil.loadJdbcJar(jdbcInfo);
        return insert;
    }

    /**
     * 修改驱动管理
     *
     * @param jdbcInfo 驱动管理
     * @return 结果
     */
    @Override
    @Transactional
    public int updateJdbcInfo(JdbcInfo jdbcInfo) {
        if (jdbcInfo.getJdbcId() == null) {
            throw new ServiceException("驱动ID不能为空");
        }
        RdbmsUtil.loadJdbcJar(jdbcInfo);
        return jdbcInfoMapper.updateJdbcInfo(jdbcInfo);
    }

    /**
     * 批量删除驱动管理
     *
     * @param jdbcIds 需要删除的驱动管理主键
     * @return 结果
     */
    @Override
    public int deleteJdbcInfoByJdbcIds(Long[] jdbcIds) {
        return jdbcInfoMapper.deleteJdbcInfoByJdbcIds(jdbcIds);
    }

    /**
     * 删除驱动管理信息
     *
     * @param jdbcId 驱动管理主键
     * @return 结果
     */
    @Override
    public int deleteJdbcInfoByJdbcId(Long jdbcId) {
        return jdbcInfoMapper.deleteJdbcInfoByJdbcId(jdbcId);
    }


    /**
     * 安装或卸载驱动
     *
     * @param jdbcId 驱动ID
     * @param action load/unload 安装/卸载
     * @return 结果
     */
    @Override
    public int load(Long jdbcId, String action) {
        JdbcInfo jdbcInfo = jdbcInfoMapper.selectJdbcInfoByJdbcId(jdbcId);
        switch (action) {
            case "load":
                RdbmsUtil.loadJdbcJar(jdbcInfo);
                break;
            case "unload":
                RdbmsUtil.unloadJdbcJar(jdbcInfo);
                break;
        }
        return 1;
    }
}
