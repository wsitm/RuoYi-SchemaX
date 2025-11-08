<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <el-col :sm="24" :lg="12" style="padding-left: 20px">
        <h2>RuoYi-SchemaX</h2>
        <!--        <h3>若依后台管理框架</h3>-->
        <p>
          <strong>基于RuoYi框架开发的数据库结构转换工具</strong>
          <!--          <br/>-->
          <!--          <br/>-->
          <!--          <strong>ruoyi-schemax工具能做什么</strong>-->
          <br/>
          <br/>
          <strong>一、DDL转换</strong>
          <br/>
          工具的另一大亮点在于DDL转换功能。我们深知DDL语句在不同数据库之间的差异性，因此特别设计了这一功能来兼容输入各种类型的DDL语句。
          <br/>
          用户只需输入原始的DDL语句，并指定目标数据库的方言，工具即可智能地将其转换为相应的DDL语句。
          <br/>
          <br/>
          <strong>二、驱动管理</strong>
          <br/>
          工具提供了的驱动管理功能，让用户能够上传并配置各类数据库驱动包。无论是常见的MySQL、Oracle、PostgreSQL、SQL
          Server，还是更多小众的数据库类型，只需上传相应的驱动包，即可迅速完成配置。
          <br/>
          这一功能不仅简化了驱动安装与更新的繁琐过程，还确保了工具能够与各类数据库无缝对接，为用户提供更为广泛的选择和便利。
          <br/>
          <br/>
          <strong>三、连接管理</strong>
          <br/>
          在连接管理方面，工具支持用户配置JDBC URL，通过简单的设置即可连接到目标数据库。
          <br/>
          > 表结构导出：一键导出当前数据库中的所有表结构信息，便于用户进行查看和分析。
          <br/>
          > 表信息查看：清晰展示数据库中的所有表，方便用户快速定位所需表格。
          <br/>
          > 表结构查看：清晰展示当前数据库中的所有表结构信息，方便用户快速定位所需表结构。
          <br/>
          > DDL查看与切换：支持查看每个表的DDL（数据定义语言）语句，并且用户可以根据需要轻松切换DDL的数据库方言。
          <br/>
          这一功能不仅提高了DDL语句的可读性和可移植性，还为用户在不同数据库之间的迁移和转换提供了极大的便利。
          <br/>
        </p>
        <p>
          <b>当前版本:</b> <span>v{{ version }}</span>，沿用 RuoYi 的版本，不定期同步
        </p>
        <!--        <p>-->
        <!--          <el-tag type="danger">&yen;免费开源</el-tag>-->
        <!--        </p>-->
        <p>
          <el-button
              type="primary"
              size="mini"
              icon="el-icon-cloudy"
              plain
              @click="goTarget('https://gitee.com/wsitm/RuoYi-RDBMShttps://gitee.com/wsitm/RuoYi-SchemaX.git')"
          >访问码云
          </el-button>
          <!--          <el-button-->
          <!--            size="mini"-->
          <!--            icon="el-icon-s-home"-->
          <!--            plain-->
          <!--            @click="goTarget('https://gitee.com/wsitm/RuoYi-SchemaX.git')"-->
          <!--            >访问主页
          </el-button>-->
        </p>
      </el-col>

      <el-col :sm="24" :lg="12" style="padding-left: 50px">
        <el-row>
          <el-col :span="12">
            <h2>技术选型</h2>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <h4>后端技术</h4>
            <ul>
              <li>SpringBoot</li>
              <li>Spring Security</li>
              <li>JWT</li>
              <li>MyBatis</li>
              <li>Druid</li>
              <li>Fastjson</li>
              <li><strong>Hutool</strong></li>
              <li><strong>jdialects</strong></li>
              <li><strong>jsqlparser</strong></li>
              <li>...</li>
            </ul>
          </el-col>
          <el-col :span="6">
            <h4>前端技术</h4>
            <ul>
              <li>Vue</li>
              <li>Vuex</li>
              <li>Element-ui</li>
              <li>Axios</li>
              <li>Sass</li>
              <li>Quill</li>
              <li><strong>Univer</strong></li>
              <li><strong>codemirror</strong></li>
              <li><strong>sqltools</strong></li>
              <li><strong>xe-utils</strong></li>
              <li>...</li>
            </ul>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
    <el-divider/>
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>联系信息</span>
          </div>
          <div class="body">
            <p>
              <i class="el-icon-s-promotion"></i> 官网：
              <el-link
                  href="https://gitee.com/wsitm/RuoYi-SchemaX.git"
                  target="_blank">
                https://gitee.com/wsitm/RuoYi-SchemaX.git
              </el-link>
            </p>
            <p>
            </p>
<!--            <p>-->
<!--              <i class="el-icon-chat-dot-round"></i> 微信：<a-->
<!--                href="javascript:;"-->
<!--            >/ *若依</a>-->
<!--            </p>-->
<!--            <p>-->
<!--              <i class="el-icon-money"></i> 支付宝：<a-->
<!--                href="javascript:;"-->
<!--                class="支付宝信息"-->
<!--            >/ *若依</a>-->
<!--            </p>-->
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>更新日志</span>
          </div>
          <el-collapse accordion>
            <el-collapse-item title="v3.9.0 - 2025-11-08">
              <ol>
                <li>同步RuoYi 3.9.0最新代码</li>
                <li>修改项目名称</li>
                <li>其他细节优化</li>
              </ol>
            </el-collapse-item>
            <el-collapse-item title="v3.8.9 - 2024-12-30">
              <ol>
                <li>添加驱动管理模块</li>
                <li>添加连接配置模块</li>
                <li>添加DDL转换模块</li>
                <li>其他细节优化</li>
              </ol>
            </el-collapse-item>
          </el-collapse>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>捐赠支持</span>
          </div>
          <div class="body">
            <img
                src="@/assets/images/pay.png"
                alt="donate"
                width="100%"
            />
            <span style="display: inline-block; height: 30px; line-height: 30px"
            >你可以请作者喝杯咖啡表示鼓励</span>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "Index",
  data() {
    return {
      // 版本号
      version: "3.9.0"
    };
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    }
  }
};
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }

  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }

  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;

    li {
      strong {
        color: #409EFF;
      }
    }
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>

