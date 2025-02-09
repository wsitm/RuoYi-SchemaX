package com.ruoyi.rdbms.config;

import cn.hutool.core.collection.CollUtil;
import com.ruoyi.rdbms.entity.domain.JdbcInfo;
import com.ruoyi.rdbms.entity.vo.JdbcInfoVo;
import com.ruoyi.rdbms.mapper.JdbcInfoMapper;
import com.ruoyi.rdbms.utils.RdbmsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class LoadJdbcConfig implements ApplicationListener<ApplicationStartedEvent> {
    private static final Logger log = LoggerFactory.getLogger(LoadJdbcConfig.class);


    @Autowired
    private JdbcInfoMapper jdbcInfoMapper;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        // 获取jdbc信息并初始化，加载 jdbc 到类加载器中
        List<JdbcInfoVo> jdbcInfoList = jdbcInfoMapper.selectJdbcInfoList(new JdbcInfo());
        if (CollUtil.isNotEmpty(jdbcInfoList)) {
            for (JdbcInfo jdbcInfo : jdbcInfoList) {
                RdbmsUtil.loadJdbcJar(jdbcInfo);
            }
        }
    }

}
