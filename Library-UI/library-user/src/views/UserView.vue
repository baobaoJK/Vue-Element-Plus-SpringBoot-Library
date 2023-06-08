<template>
  <div class="user">
    <el-container>
      <el-main>
        <!--        用户信息面板-->
        <el-row class="user-card">
          <el-col :span="6" class="user-info">
            <el-image :src="imageUrl" class="user-img"></el-image>
            <p class="name">用户名称：{{ user.name }}</p>
            <p class="idCard">借书卡号：{{ user.idCard }}</p>
            <p class="phone">手机号：{{ user.phone }}</p>
            <p class="identity">身份：{{ user.identity }}</p>
            <p class="bookCount">可借阅图书数量：{{ user.bookCount }}</p>
            <el-button
              type="primary"
              @click="updatePasswordDialog(updatePasswordFormRef)"
              >修改密码</el-button
            >
          </el-col>
          <el-col :span="18" class="user-book">
            <p class="title">当前借阅图书列表</p>
            <el-table :data="books" empty-text="当前无借阅" class="book-table">
              <el-table-column prop="bookName" label="书名" />
              <el-table-column prop="isbn" label="ISBN号码" />
              <el-table-column prop="borrowTime" label="借阅时间" />
              <el-table-column prop="returnTime" label="应归还时间" />
            </el-table>
          </el-col>
        </el-row>
        <!--        更改密码对话框-->
        <el-dialog
          v-model="updatePasswordDialogVisible"
          title="更改密码"
          width="500px"
          :close-on-click-modal="false"
        >
          <el-form
            :model="updatePasswordForm"
            :rules="updatePasswordRules"
            ref="updatePasswordFormRef"
            class="update-password-form"
          >
            <el-form-item label="原密码" label-width="100px" prop="oldPassword">
              <el-input
                v-model="updatePasswordForm.oldPassword"
                type="password"
                placeholder="原密码"
              ></el-input>
            </el-form-item>
            <el-form-item label="新密码" label-width="100px" prop="newPassword">
              <el-input
                v-model="updatePasswordForm.newPassword"
                type="password"
                placeholder="新密码"
              ></el-input>
            </el-form-item>
          </el-form>
          <template #footer>
            <span class="dialog-footer">
              <el-button @click="updatePasswordDialogVisible = false"
                >取消</el-button
              >
              <el-button
                type="primary"
                @click="updatePassword(updatePasswordFormRef)"
              >
                修改
              </el-button>
            </span>
          </template>
        </el-dialog>
      </el-main>
    </el-container>
  </div>
</template>

<script lang="ts" setup>
import { reactive, ref } from "vue";
import type { FormInstance, FormRules } from "element-plus";
import { ElMessageBox } from "element-plus";
import axios from "axios";
import jsCookie from "js-cookie";

// 用户头像
let imageUrl;
imageUrl = require("../assets/images/user.png");

// 用户名
let username = jsCookie.get("username");
let user = ref({
  name: "",
  idCard: "",
  phone: "",
  identity: "",
  bookCount: "",
});
// 获取用户信息
const getUserInfo = () => {
  axios.get("http://localhost:8888/user/get/" + username).then((resp) => {
    user.value = resp.data;
    console.log(resp.data);
  });
};

// 借阅图书信息
let books = ref();
const getBooks = () => {
  axios.get("http://localhost:8888/borrow/get/" + username).then((resp) => {
    books.value = resp.data;
  });
};

// 更改密码对话框
let updatePasswordDialogVisible = ref(false);
const updatePasswordDialog = (formEl: FormInstance | undefined) => {
  updatePasswordDialogVisible.value = true;
  if (!formEl) return;
  formEl.resetFields();
};

// 更改密码表单规则
const updatePasswordRules = ref<FormRules>({
  oldPassword: [{ required: true, message: "请输入旧密码", trigger: "blur" }],
  newPassword: [{ required: true, message: "请输入新密码", trigger: "blur" }],
});

// 更改密码表单
const updatePasswordFormRef = ref<FormInstance>();
let updatePasswordForm = reactive({
  oldPassword: "",
  newPassword: "",
});

// 修改密码
const updatePassword = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.validate((valid) => {
    if (valid) {
      axios
        .post(
          "http://localhost:8888/user/update/user/" + username,
          updatePasswordForm
        )
        .then((resp) => {
          const statusCode = resp.data.statusCode;
          // 修改失败
          if (statusCode == 0) {
            ElMessageBox.alert("修改密码失败，请重试", "信息", {
              confirmButtonText: "确认",
            });
          }
          // 修改成功
          if (statusCode == 1) {
            ElMessageBox.alert("修改成功", "信息", {
              confirmButtonText: "确认",
              callback: () => {
                updatePasswordDialogVisible.value = false;
              },
            });
          }
          // 密码不一致
          if (statusCode == 2) {
            ElMessageBox.alert("原密码不一致，修改失败", "信息", {
              confirmButtonText: "确认",
            });
          }
        });
    }
  });
};

// 初始化
const init = () => {
  // 获取用户信息
  getUserInfo();
  // 获取借阅图书信息
  getBooks();
};
init();
</script>

<style lang="scss">
@import "../assets/css/user";
</style>
