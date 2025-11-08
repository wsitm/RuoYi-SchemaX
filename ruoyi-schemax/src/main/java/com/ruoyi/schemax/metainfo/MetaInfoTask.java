package com.ruoyi.schemax.metainfo;

import com.ruoyi.schemax.entity.vo.ConnectInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MetaInfoTask implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(MetaInfoTask.class);

    private final ConnectInfoVO connectInfoVO;

    public MetaInfoTask(ConnectInfoVO connectInfoVO) {
        this.connectInfoVO = connectInfoVO;
    }

    @Override
    public void run() {
        log.info("连接ID: {}， 正在线程 {} 上运行.", connectInfoVO.getConnectId(), Thread.currentThread().getName());
        // 获取数据库连接信息
        IMetaInfoHandler metaInfoHandler = MetaInfoFactory.getInstance(connectInfoVO.getDriverClass());
        metaInfoHandler.loadDataToCache(connectInfoVO);
        log.info("连接ID: {} 处理完成。", connectInfoVO.getConnectId());
    }

}
