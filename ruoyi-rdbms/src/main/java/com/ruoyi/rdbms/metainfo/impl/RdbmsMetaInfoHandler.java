package com.ruoyi.rdbms.metainfo.impl;

import cn.hutool.db.meta.MetaUtil;
import com.ruoyi.rdbms.constant.RdbmsConstants;
import com.ruoyi.rdbms.entity.vo.TableVO;
import com.ruoyi.rdbms.metainfo.AbsMetaInfoHandler;
import com.ruoyi.rdbms.metainfo.anno.JdbcType;
import com.ruoyi.rdbms.utils.RdbmsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Consumer;

/**
 * 通用元数据信息处理器
 */
@Component
@JdbcType(RdbmsConstants.JDBC_RDBMS)
public class RdbmsMetaInfoHandler extends AbsMetaInfoHandler {
    private static final Logger log = LoggerFactory.getLogger(RdbmsMetaInfoHandler.class);

    /**
     * 刷新数据
     *
     * @param connectId 连接ID
     * @param consumer  消费者
     */
    @Override
    public void flushData(Long connectId, Consumer<TableVO> consumer) {
        try (RdbmsUtil.ShimDataSource dataSource = RdbmsUtil.getDataSource(connectId)) {
            List<String> tableNames = MetaUtil.getTables(dataSource);
            for (String tableName : tableNames) {
                try {
                    TableVO tableVO = new TableVO(MetaUtil.getTableMeta(dataSource, tableName));
                    consumer.accept(tableVO);
                } catch (Exception e) {
                    log.error("获取表信息失败", e);
                }
            }
        }
    }

}
