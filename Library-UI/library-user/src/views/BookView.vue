<template>
  <div class="book">
    <el-container>
      <el-main>
        <!--        图书操作栏-->
        <el-row class="book-header">
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
              <el-col :span="4">
                <el-select
                  v-model="searchModel"
                  placeholder="搜索类型"
                  @change="changeSearch"
                  class="search-size"
                >
                  <el-option
                    v-for="item in searchOptions"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </el-col>
              <el-col :span="14">
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
        <!--        图书表格栏-->
        <el-row class="book-table">
          <el-col>
            <el-table :data="books" height="100%" empty-text="没有数据">
              <el-table-column fixed prop="id" label="Id" width="50" />
              <el-table-column prop="groups" label="组名" />
              <el-table-column prop="name" label="书名" />
              <el-table-column prop="author" label="作者" />
              <el-table-column prop="press" label="出版社" />
              <el-table-column prop="price" label="价格(人民币)" />
              <el-table-column prop="quantity" label="数量(本)" />
              <el-table-column prop="isbn" label="ISBN号码" />
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
      </el-main>
    </el-container>
  </div>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import { Search } from "@element-plus/icons-vue";
import axios from "axios";

// 获取图书数据
let books = ref();
const getBook = () => {
  axios
    .get("http://localhost:8888/book/" + pageNum.value + "/" + pageSize.value)
    .then((resp) => {
      books.value = resp.data.content;
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
    getBook();
  } else {
    searchBook();
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
    getBook();
  } else {
    searchBook();
  }
};

// 搜索框选项
let searchModel = ref("name");
let searchOptions = [
  {
    value: "name",
    label: "图书名称",
  },
  {
    value: "author",
    label: "作者名称",
  },
  {
    value: "press",
    label: "出版社名称",
  },
  {
    value: "isbn",
    label: "ISBN号码",
  },
];
const changeSearch = (value: string) => {
  searchModel.value = value;
};

// 搜索框数据
const searchInput = ref();
// 搜索框按钮
const searchButton = () => {
  pageNum.value = 1;
  searchBook();
};
// 搜索图书
const searchBook = () => {
  if (searchInput.value != "") {
    axios
      .get(
        "http://localhost:8888/book/search/" +
          searchModel.value +
          "/" +
          searchInput.value +
          "/" +
          pageNum.value +
          "/" +
          pageSize.value
      )
      .then((resp) => {
        books.value = resp.data.content;
        pageTotal.value = resp.data.totalElements;
      });
  } else {
    getBook();
  }
};

// 初始化
const init = () => {
  // 获取图书
  getBook();
};
init();
</script>

<style lang="scss">
@import "../assets/css/book";
</style>
