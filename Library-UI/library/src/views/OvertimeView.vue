<template>
  <div class="overtime">
    <el-container>
      <el-main>
        <!--        超时查询操作栏-->
        <el-row class="overtime-header">
          <el-col :span="4" class="search-page-pane">
            <el-select
              v-model="pageSize"
              placeholder="数据显示"
              @change="changeSize"
              class="search-size"
            >
              <el-option
                v-for="item in sizeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              >
              </el-option>
            </el-select>
          </el-col>
          <el-col :span="16" class="search-input-pane">
            <el-row>
              <el-col :span="18">
                <el-input
                  placeholder="请输入搜索内容"
                  v-model="searchInput"
                  :suffix-icon="Search"
                  class="search-input"
                >
                </el-input>
              </el-col>
              <el-col :span="6">
                <el-button
                  type="primary"
                  class="search-button"
                  @click="searchButton"
                >
                  搜索
                </el-button>
              </el-col>
            </el-row>
          </el-col>
        </el-row>
        <!--        超时查询表格栏-->
        <el-row class="overtime-table">
          <el-col>
            <el-table :data="overtimes" height="100%" empty-text="没有数据">
              <el-table-column fixed prop="id" label="Id" width="50" />
              <el-table-column prop="bookName" label="书名" />
              <el-table-column prop="isbn" label="ISBN号码" />
              <el-table-column prop="username" label="借阅人" />
              <el-table-column prop="idCard" label="借阅卡号" />
              <el-table-column prop="borrowTime" label="借阅时间" />
              <el-table-column prop="overtimeTime" label="应归还时间" />
              <el-table-column fixed="right" label="操作">
                <template #default="overtimes">
                  <el-button
                    @click="overtimeBookDialog(overtimes.row)"
                    type="text"
                    >归还</el-button
                  >
                </template>
              </el-table-column>
            </el-table>
            <el-pagination
              background
              v-model:current-page="pageNum"
              v-model:page-size="pageSize"
              layout="prev, pager, next"
              :total="pageTotal"
              @current-change="page"
            >
            </el-pagination>
          </el-col>
        </el-row>
        <!--        超时查询对话框-->
        <el-dialog
          v-model="overtimeBookDialogVisible"
          title="归还图书"
          width="500px"
        >
          <span>确认归还 {{ overtimeName }}</span>
          <template #footer>
            <span class="dialog-footer">
              <el-button @click="overtimeBookDialogVisible = false"
                >取消</el-button
              >
              <el-button type="primary" @click="overtimeBook"> 确认 </el-button>
            </span>
          </template>
        </el-dialog>
      </el-main>
    </el-container>
  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import { Search } from "@element-plus/icons-vue";
import axios from "axios";
import { ElMessageBox } from "element-plus";

// 超时未归还图书信息
let overtimes = ref();
const getOvertime = () => {
  axios
    .get(
      "http://localhost:8888/borrow/overtime/" +
        pageNum.value +
        "/" +
        pageSize.value
    )
    .then((resp) => {
      overtimes.value = resp.data.content;
      pageTotal.value = resp.data.totalElements;
    });
};

// 显示数据数量选项
let pageNum = ref(1);
let pageSize = ref(10);
let pageTotal = ref(0);
const page = (val: number) => {
  pageNum.value = val;
  if (searchInput.value == undefined) {
    getOvertime();
  } else {
    searchOvertime();
  }
};

// 数据显示框
let sizeOptions = [
  {
    value: 10,
    label: "10条数据/页",
  },
  {
    value: 50,
    label: "50条数据/页",
  },
  {
    value: 100,
    label: "100条数据/页",
  },
];

// 修改显示数据量
const changeSize = (value: number) => {
  pageSize.value = value;
  if (searchInput.value == undefined) {
    getOvertime();
  } else {
    searchOvertime();
  }
};

// 搜索框数据
const searchInput = ref();
// 搜索框按钮
const searchButton = () => {
  pageNum.value = 1;
  searchOvertime();
};
// 搜索超时未归还图书信息
const searchOvertime = () => {
  if (searchInput.value != "" && searchInput.value != undefined) {
    axios
      .get(
        "http://localhost:8888/borrow/overtime/search/" +
          searchInput.value +
          "/" +
          pageNum.value +
          "/" +
          pageSize.value
      )
      .then((resp) => {
        overtimes.value = resp.data.content;
        pageTotal.value = resp.data.totalElements;
      });
  } else {
    getOvertime();
  }
};
// 超时查询
let overtimeName = ref("");
let overtimeId = ref(0);
let overtimeBookDialogVisible = ref(false);
const overtimeBookDialog = (row: any) => {
  overtimeId.value = row.id;
  overtimeName.value = row.bookName;
  console.log(overtimeId);
  console.log(overtimeName);
  overtimeBookDialogVisible.value = true;
};
// 归还图书
const overtimeBook = () => {
  if (overtimeId.value) {
    axios
      .post("http://localhost:8888/borrow/return/" + overtimeId.value)
      .then((resp) => {
        const statusCode = resp.data.statusCode;

        // 归还失败
        if (statusCode == 0) {
          ElMessageBox.alert("归还图书失败，请重试", "信息", {
            confirmButtonText: "确认",
          });
        }
        // 归还成功
        if (statusCode == 1) {
          ElMessageBox.alert("归还图书成功", "信息", {
            confirmButtonText: "确认",
            callback: () => {
              overtimeBookDialogVisible.value = false;
            },
          });
        }
        // 借阅记录不存在
        if (statusCode == 2) {
          ElMessageBox.alert("归还失败，此借阅记录不存在", "信息", {
            confirmButtonText: "确认",
          });
        }
      });
  }
};

// 初始化
const init = () => {
  getOvertime();
};
init();
</script>

<style lang="scss">
@import "../assets/css/overtime";
</style>
