<template>
  <div class="return">
    <el-container>
      <el-main>
        <!--        归还图书操作栏-->
        <el-row class="return-header">
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
        <!--        归还图书表格栏-->
        <el-row class="return-table">
          <el-col>
            <el-table :data="borrows" height="100%" empty-text="没有数据">
              <el-table-column fixed prop="id" label="Id" width="50" />
              <el-table-column prop="bookName" label="书名" />
              <el-table-column prop="isbn" label="ISBN号码" />
              <el-table-column prop="username" label="借阅人" />
              <el-table-column prop="idCard" label="借阅卡号" />
              <el-table-column prop="borrowTime" label="借阅时间" />
              <el-table-column prop="returnTime" label="应归还时间" />
              <el-table-column fixed="right" label="操作">
                <template #default="borrows">
                  <el-button @click="returnBookDialog(borrows.row)" type="text"
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
        <!--        归还图书对话框-->
        <el-dialog
          v-model="returnBookDialogVisible"
          title="归还图书"
          width="500px"
        >
          <span>确认归还 {{ returnName }}</span>
          <template #footer>
            <span class="dialog-footer">
              <el-button @click="returnBookDialogVisible = false"
                >取消</el-button
              >
              <el-button type="primary" @click="returnBook"> 确认 </el-button>
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

// 借阅图书信息
let borrows = ref();
const getBorrow = () => {
  axios
    .get("http://localhost:8888/borrow/" + pageNum.value + "/" + pageSize.value)
    .then((resp) => {
      borrows.value = resp.data.content;
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
    getBorrow();
  } else {
    searchBorrow();
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
    getBorrow();
  } else {
    searchBorrow();
  }
};

// 搜索框数据
const searchInput = ref();
// 搜索框按钮
const searchButton = () => {
  pageNum.value = 1;
  searchBorrow();
};
// 搜索借阅信息
const searchBorrow = () => {
  if (searchInput.value != "") {
    axios
      .get(
        "http://localhost:8888/borrow/search/" +
          searchInput.value +
          "/" +
          pageNum.value +
          "/" +
          pageSize.value
      )
      .then((resp) => {
        borrows.value = resp.data.content;
        pageTotal.value = resp.data.totalElements;
      });
  } else {
    getBorrow();
  }
};

// 归还图书
let returnName = ref("");
let returnId = ref(0);
let returnBookDialogVisible = ref(false);
const returnBookDialog = (row: any) => {
  returnId.value = row.id;
  returnName.value = row.bookName;
  returnBookDialogVisible.value = true;
};
const returnBook = () => {
  if (returnId.value) {
    axios
      .post("http://localhost:8888/borrow/return/" + returnId.value)
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
              returnBookDialogVisible.value = false;
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
  getBorrow();
};
init();
</script>

<style lang="scss">
@import "../assets/css/return";
</style>
