<template>
  <!--  窗体代码  -->
  <div class="login">
    <el-container>
      <el-main>
        <!--  登录表单窗体  -->
        <el-row class="login-form">
          <!--  登陆表单左面板 -->
          <el-col :span="12" class="login-form-left">
            <p>图书管理系统</p>
            <p>管理员页面</p>
          </el-col>
          <!--  登陆表单右面板 -->
          <el-col :span="12" class="login-form-right">
            <!--  登录表单  -->
            <el-form
              ref="loginFormRef"
              :model="loginForm"
              :rules="loginRules"
              @keyup.enter="login(loginFormRef)"
            >
              <el-form-item>
                <p>图书管理系统-登录</p>
              </el-form-item>
              <el-form-item prop="username">
                <el-input
                  v-model="loginForm.username"
                  placeholder="用户名"
                ></el-input>
              </el-form-item>
              <el-form-item prop="password">
                <el-input
                  type="password"
                  v-model="loginForm.password"
                  placeholder="密码"
                ></el-input>
              </el-form-item>
              <el-form-item>
                <el-button
                  type="primary"
                  @click="login(loginFormRef)"
                  ref="loginButtonRef"
                  :disabled="loginState"
                  >登录</el-button
                >
              </el-form-item>
            </el-form>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
  </div>
</template>

<script lang="ts" setup>
import { reactive, ref } from "vue";
import type { FormInstance, FormRules } from "element-plus";
import { ElMessageBox, ElButton } from "element-plus";

import axios from "axios";
import jsCookie from "js-cookie";
import router from "@/router";

// 判断用户登录状态
const userLoginState = jsCookie.get("username");
if (userLoginState != undefined) {
  router.push("/home");
}

// 登录表单
const loginButtonRef = ref();
let loginState = ref(false);

// 登陆表单
const loginFormRef = ref<FormInstance>();
const loginForm = reactive({
  username: "",
  password: "",
});

// 表单校验
const loginRules = reactive<FormRules>({
  username: [{ required: true, message: "请输入用户名", trigger: "blur" }],
  password: [{ required: true, message: "请输入密码", trigger: "blur" }],
});

// 登录操作
const login = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.validate((valid) => {
    if (valid && !loginState.value) {
      loginState.value = true;
      // 发送客户端请求
      axios
        .post("http://localhost:8888/login/admin", loginForm)
        .then((resp) => {
          const statusCode = resp.data.statusCode;

          // 登录失败
          if (statusCode == 0) {
            ElMessageBox.alert("登录失败，账号或密码错误", "信息", {
              confirmButtonText: "重试",
            });
            loginState.value = false;
          }

          // 登录成功
          if (statusCode == 1) {
            ElMessageBox.alert("登录成功", "信息", {
              confirmButtonText: "确认",
              callback: () => {
                // 设置Cookie
                jsCookie.set("username", resp.data.username, {
                  expires: 1,
                  path: "/",
                });
                // 页面跳转
                router.push("/home");
              },
            });
          }

          // 登录失败
          if (statusCode == 2) {
            ElMessageBox.alert("登录失败，账号已被冻结", "信息", {
              confirmButtonText: "确定",
            });
            loginState.value = false;
          }
        });
    } else {
      return false;
    }
  });
};
</script>

<style lang="scss">
@import "../assets/css/login";
</style>
