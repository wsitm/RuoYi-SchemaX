package com.ruoyi.rdbms.service.impl;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.meta.JdbcType;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.rdbms.entity.domain.ConnectInfo;
import com.ruoyi.rdbms.entity.vo.ColumnVO;
import com.ruoyi.rdbms.entity.vo.ConnectInfoVO;
import com.ruoyi.rdbms.entity.vo.TableVO;
import com.ruoyi.rdbms.mapper.ConnectInfoMapper;
import com.ruoyi.rdbms.mapper.JdbcInfoMapper;
import com.ruoyi.rdbms.metainfo.IMetaInfoHandler;
import com.ruoyi.rdbms.metainfo.MetaInfoFactory;
import com.ruoyi.rdbms.service.IConnectInfoService;
import com.ruoyi.rdbms.utils.CacheUtil;
import com.ruoyi.rdbms.utils.CommonUtil;
import com.ruoyi.rdbms.utils.DDLUtil;
import com.ruoyi.rdbms.utils.PoiUtil;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 连接配置Service业务层处理
 *
 * @author wsitm
 * @date 2025-01-11
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
@Service
public class ConnectInfoServiceImpl implements IConnectInfoService {
    private static final Logger log = LoggerFactory.getLogger(ConnectInfoServiceImpl.class);

    @Autowired
    private JdbcInfoMapper jdbcInfoMapper;
    @Autowired
    private ConnectInfoMapper connectInfoMapper;

    /**
     * 查询连接配置
     *
     * @param connectId 连接配置主键
     * @return 连接配置
     */
    @Override
    public ConnectInfoVO selectConnectInfoByConnectId(Long connectId) {
        ConnectInfoVO connectInfoVO = connectInfoMapper.selectConnectInfoByConnectId(connectId);
        connectInfoVO.setCacheType(CacheUtil.cacheType(connectInfoVO.getConnectId()));
        return connectInfoVO;
    }

    /**
     * 查询连接配置列表
     *
     * @param connectInfo 连接配置
     * @return 连接配置
     */
    @Override
    public List<ConnectInfoVO> selectConnectInfoList(ConnectInfo connectInfo) {
        List<ConnectInfoVO> connectInfoVOList = connectInfoMapper.selectConnectInfoList(connectInfo);
        for (ConnectInfoVO connectInfoVo : connectInfoVOList) {
            connectInfoVo.setCacheType(CacheUtil.cacheType(connectInfoVo.getConnectId()));
        }
        return connectInfoVOList;
    }

    /**
     * 新增连接配置
     *
     * @param connectInfo 连接配置
     * @return 结果
     */
    @Override
    public int insertConnectInfo(ConnectInfo connectInfo) {
        connectInfo.setCreateTime(DateUtils.getNowDate());
        connectInfo.setCreateBy(SecurityUtils.getUsername());
        int insert = connectInfoMapper.insertConnectInfo(connectInfo);

        flushCahce(connectInfo.getConnectId());

        return insert;
    }

    /**
     * 修改连接配置
     *
     * @param connectInfo 连接配置
     * @return 结果
     */
    @Override
    public int updateConnectInfo(ConnectInfo connectInfo) {
        connectInfo.setUpdateTime(DateUtils.getNowDate());
        connectInfo.setUpdateBy(SecurityUtils.getUsername());
        int update = connectInfoMapper.updateConnectInfo(connectInfo);

        flushCahce(connectInfo.getConnectId());

        return update;
    }

    /**
     * 批量删除连接配置
     *
     * @param connectIds 需要删除的连接配置主键
     * @return 结果
     */
    @Override
    public int deleteConnectInfoByConnectIds(Long[] connectIds) {
        return connectInfoMapper.deleteConnectInfoByConnectIds(connectIds);
    }

    /**
     * 删除连接配置信息
     *
     * @param connectId 连接配置主键
     * @return 结果
     */
    @Override
    public int deleteConnectInfoByConnectId(Long connectId) {
        return connectInfoMapper.deleteConnectInfoByConnectId(connectId);
    }

    /**
     * @param connectInfo 连接信息
     * @return 布尔
     */
    @Override
    public boolean checkConnectInfo(ConnectInfo connectInfo) {
//        JdbcInfo jdbcInfo = jdbcInfoMapper.selectJdbcInfoByJdbcId(connectInfo.getJdbcId());
        Connection conn = null;
        try {
            String url = connectInfo.getJdbcUrl();
            String username = connectInfo.getUsername();
            String password = connectInfo.getPassword();

//            Class.forName(jdbcInfo.getDriverClass());
//            DriverManager.setLoginTimeout(1);

            conn = DriverManager.getConnection(url, username, password);
            return true;
        } catch (Exception e) {
            log.error("测试连接异常", e);
            throw new ServiceException(e.getMessage());
        } finally {
            IoUtil.close(conn);
        }
    }

    /**
     * 获取连接所有表格详细信息
     *
     * @param connectId 连接ID
     * @return 表格信息
     */
    @Override
    public List<TableVO> getTableInfo(Long connectId) {
        return CacheUtil.getTableMetaList(connectId);
    }

    /**
     * 刷新缓存
     *
     * @param connectId 连接ID
     * @return 布尔
     */
    @Override
    public boolean flushCahce(Long connectId) {
        ConnectInfoVO connectInfoVO = connectInfoMapper.selectConnectInfoByConnectId(connectId);
        IMetaInfoHandler metaInfoHandler = MetaInfoFactory.getInstance(connectInfoVO.getDriverClass());
        ThreadUtil.execute(() -> metaInfoHandler.loadDataToCache(connectId));
        return true;
    }

    /**
     * 生成表格DDL
     *
     * @param connectId 连接ID
     * @param database  数据库类型
     * @return DDL
     */
    public Map<String, String[]> genTableDDL(Long connectId, String database) {
        List<TableVO> tableVOList = CacheUtil.getTableMetaList(connectId);
        return DDLUtil.genDDL(tableVOList, database);
    }


    public static final String[] ARR_COL = new String[]{"序号", "字段", "类型", "长度", "为空", "自增", "主键", "默认", "注释"};

    public void exportTableInfo(HttpServletResponse response, Long connectId, String[] skipStrArr) throws IOException {

        String fileName = "表格信息-" + connectId + "-" + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_PATTERN) + ".xlsx";
        String path = RuoYiConfig.getDownloadPath() + "connect/";
        File dir = new File(path);
        // 判断路径是否存在
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(path, fileName);
        try (ExcelWriter excelWriter = ExcelUtil.getBigWriter(file)) {

            skipStrArr = ArrayUtil.removeEmpty(skipStrArr);
            if (ArrayUtil.isEmpty(skipStrArr)) {
                skipStrArr = new String[]{"*"};
            }
            Arrays.sort(skipStrArr, (s1, s2) -> {
                boolean b1 = StrUtil.startWith(s1, "!");
                boolean b2 = StrUtil.startWith(s2, "!");
                if (b1 && b2) return 0;
                if (b1) return -1;
                if (b2) return 1;
                return 0;
            });

            int tableNum = 1;

            Font font = excelWriter.getWorkbook().createFont();
            font.setBold(true);
            int currRow = 1;

            List<TableVO> tableVOList = CacheUtil.getTableMetaList(connectId);
            for (TableVO tableVO : tableVOList) {

                String tableName = tableVO.getTableName();
                if (!CommonUtil.matchAnyIgnoreCase(tableName, skipStrArr)) {
                    continue;
                }

                String title = (tableNum++) + ". " + tableName.toLowerCase() + ", " + tableVO.getComment();
                CellStyle cellStyle = PoiUtil.createDefaultCellStyle(excelWriter.getWorkbook());
                cellStyle.setAlignment(HorizontalAlignment.LEFT);
                cellStyle.setFont(font);

                excelWriter.merge(
                        excelWriter.getCurrentRow(),
                        excelWriter.getCurrentRow(),
                        0,
                        ARR_COL.length - 1,
                        title,
                        cellStyle
                );
                excelWriter.passCurrentRow();

                excelWriter.writeHeadRow(Arrays.asList(ARR_COL));
                CellStyle headCellStyle = excelWriter.getHeadCellStyle();
                headCellStyle.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
                headCellStyle.setFont(font);
                headCellStyle.setAlignment(HorizontalAlignment.LEFT);

                List<ColumnVO> columns = tableVO.getColumnList();
                int num = 1;
                for (ColumnVO columnVO : columns) {

                    String size = StrUtil.toString(columnVO.getSize());
//                    if (columnVO.getType() == JdbcType.FLOAT.typeCode
//                            || columnVO.getType() == JdbcType.DOUBLE.typeCode
//                            || columnVO.getType() == JdbcType.NUMERIC.typeCode
//                            || columnVO.getType() == JdbcType.DECIMAL.typeCode) {
//                        size += "," + columnVO.getDigit();
//                    }
                    if (columnVO.getType() == JdbcType.FLOAT.typeCode
                            || columnVO.getType() == JdbcType.CLOB.typeCode
                            || StrUtil.equalsAnyIgnoreCase(columnVO.getTypeName(), "text", "longtext")) {
                        size = "";
                    }

                    String defVal = columnVO.getColumnDef();
                    if (StrUtil.isNotEmpty(defVal) && !StrUtil.contains(defVal, "nextval")) {
                        if (StrUtil.contains(defVal, "::")) {
                            defVal = defVal.split("::")[0];
                        }
                        if (StrUtil.startWith(defVal, "'")) {
                            defVal = StrUtil.subAfter(defVal, "'", false);
                        }
                        if (StrUtil.endWith(defVal, "'")) {
                            defVal = StrUtil.subBefore(defVal, "'", true);
                        }
                    }

                    excelWriter.writeRow(
                            ListUtil.toList(
                                    num++,
                                    columnVO.getName().toLowerCase(),
                                    columnVO.getTypeName().toLowerCase(),
                                    size,
                                    columnVO.isNullable() ? "YES" : "NO",
                                    columnVO.isAutoIncrement() ? "YES" : null,
                                    columnVO.isPk() ? "YES" : null,
                                    defVal,
                                    columnVO.getComment()
                            )
                    );

                }
                excelWriter.passCurrentRow();
            }
            excelWriter.setColumnWidth(1, 25);
            excelWriter.setColumnWidth(8, 50);
            excelWriter.getCellStyle().setAlignment(HorizontalAlignment.LEFT);
            excelWriter.flush();

            CommonUtil.renderFile(response, file);
        }
    }
}
