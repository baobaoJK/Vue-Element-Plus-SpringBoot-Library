<template>
  <div class="home">
    <el-main>
      <!--      仪表盘-->
      <el-row class="dashboard">
        <el-col :xs="24" :sm="12" :lg="6" class="book-num pane"
          ><div>
            <el-icon><Reading /></el-icon>
            <div class="message">
              <p class="title">图书馆书本数量</p>
              <p class="sub-title">{{ bookCount }}</p>
            </div>
          </div></el-col
        >
        <el-col :xs="24" :sm="12" :lg="6" class="overtime-num pane"
          ><div>
            <el-icon><Failed /></el-icon>
            <div class="message">
              <p class="title">超时归还数量</p>
              <p class="sub-title">{{ overtimeCount }}</p>
            </div>
          </div></el-col
        >
        <el-col :xs="24" :sm="12" :lg="6" class="borrow-num pane"
          ><div>
            <el-icon><List /></el-icon>
            <div class="message">
              <p class="title">借出书本数量</p>
              <p class="sub-title">{{ borrowCount }}</p>
            </div>
          </div></el-col
        >
        <el-col :xs="24" :sm="12" :lg="6" class="system-time pane"
          ><div>
            <el-icon><Timer /></el-icon>
            <div class="message">
              <p class="title">系统时间</p>
              <p class="time">{{ times }}</p>
            </div>
          </div></el-col
        >
      </el-row>
      <!--      介绍卡片-->
      <el-card class="introduce">
        <template #header> 开源信息 </template>
        <p>作者信息：KSaMar</p>
        <p>该项目模拟学校图书馆的图书管理系统</p>
        <p>
          码云下载地址：<a
            href="https://github.com/baobaoJK/Vue-Element-Plus-SpringBoot-Library"
            >Gitee</a
          >
        </p>
        <p>
          GitHub下载地址：<a
            href="https://gitee.com/baobao_JK/Vue-Element-Plus-SpringBoot-Library"
            >GitHub</a
          >
        </p>
        <p>文档地址：<a href="#">文档.md</a></p>
      </el-card>
      <!--      日志卡片-->
      <el-card class="log">
        <template #header>
          <span>操作日志</span>
        </template>
        <el-table
          style="width: 100%"
          height="600"
          :data="logs"
          :default-sort="{ prop: 'date', order: 'descending' }"
        >
          <el-table-column
            prop="time"
            label="日期/时间"
            sortable
            width="220px"
          />
          <el-table-column prop="name" label="名字" width="120px" />
          <el-table-column prop="info" label="操作" />
        </el-table>
      </el-card>
    </el-main>
  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import { Reading, Failed, List, Timer } from "@element-plus/icons-vue";
import axios from "axios";

// 仪表盘数据
let bookCount = ref(0);
let overtimeCount = ref(0);
let borrowCount = ref(0);
let times = ref("");

//系统时间
setInterval(function () {
  let date = new Date();
  let year = date.getFullYear(); //获取当前年份
  let mon = date.getMonth() + 1; //获取当前月份
  let da = date.getDate(); //获取当前日
  let day = date.getDay(); //获取当前星期几
  let h: any = date.getHours(); //获取小时
  let m: any = date.getMinutes(); //获取分钟
  let s: any = date.getSeconds(); //获取秒
  let weeks = ["日", "一", "二", "三", "四", "五", "六"]; //星期
  let d = document.getElementById("Date");
  if (h < 10) {
    h = "0" + h;
  }
  if (m < 10) {
    m = "0" + m;
  }
  if (s < 10) {
    s = "0" + s;
  }
  times.value =
    year +
    "-" +
    mon +
    "-" +
    da +
    " " +
    "星期" +
    weeks[day] +
    " " +
    h +
    ":" +
    m +
    ":" +
    s;
}, 1);

// 获取仪表盘数据
const getDashboard = () => {
  axios.get("http://localhost:8888/book/dashboard/").then((resp) => {
    bookCount.value = resp.data.bookCount;
    overtimeCount.value = resp.data.overtimeCount;
    borrowCount.value = resp.data.borrowCount;
  });
};
getDashboard();

// 获取日志数据
let logs = ref();
const getLogs = () => {
  axios.get("http://localhost:8888/nav/logs/").then((resp) => {
    logs.value = resp.data.content;
  });
};
getLogs();
</script>

<style lang="scss">
@import "../assets/css/home";
</style>
