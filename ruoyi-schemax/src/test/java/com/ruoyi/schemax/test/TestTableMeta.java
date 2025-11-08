package com.ruoyi.schemax.test;

import com.ruoyi.schemax.utils.CommonUtil;
import org.junit.Test;

public class TestTableMeta {

    @Test
    public void test1() {
        String str = "a ${dd} ;sdfa;'df;a${bc}d'fadf ' s;d'";
//        String res = StrUtil.replace(str, "'([^']*)'", match -> {
//            // 获取捕获组的内容（单引号内的部分）
//            String content = match.group(1);
//            // 将分号替换为中文分号
//            content = content.replace(';', '；');
//            // 返回替换后的内容，并重新加上单引号
//            return "'" + content + "'";
//        });
        System.out.println(CommonUtil.replaceInQuotes(str, ";", "；"));
    }

    @Test
    public void test2() {
        String str = "UNIQUE INDEX `UNIQUE_SHARE_CODE`(`share_code`) USING BTREE";
        String res = str.replaceAll("(?<=(?i)UNIQUE).*?(?=\\()", " ");
        System.out.println(res);
    }

    @Test
    public void test3() {
        String ddl = " geo public.GEOMETRY not null ";
        String marker = "geometry|geography|_geometry|st_geomfromtext|st_setsrid|st_makepoint";
        String ddl2 = ddl.replaceAll("(\\s+)([^\\s]*\\.)(?=(?i)(" + marker + "))", " ");
        System.out.println(ddl2);
    }

}
