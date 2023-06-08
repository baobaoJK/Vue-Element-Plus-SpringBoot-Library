import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import AboutView from "@/views/AboutView.vue";
import TestPageView from "@/views/TestPageView.vue";

import LoginView from "@/views/LoginView.vue";
import NavView from "@/views/NavView.vue";
import HomeView from "@/views/HomeView.vue";
import BookView from "@/views/BookView.vue";
import BorrowView from "@/views/BorrowView.vue";
import ReturnView from "@/views/ReturnView.vue";
import OvertimeView from "@/views/OvertimeView.vue";
import UserView from "@/views/UserView.vue";
import SystemView from "@/views/SystemView.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "登录",
    component: LoginView,
    meta: { show: false },
  },
  {
    path: "/login",
    name: "管理员登录",
    component: LoginView,
    meta: { show: false },
  },
  {
    path: "/nav",
    name: "导航",
    component: NavView,
    redirect: "/home",
    meta: { show: false },
    children: [
      {
        path: "/home",
        name: "主页",
        meta: { show: true },
        components: { home: HomeView },
      },
    ],
  },
  {
    path: "/nav",
    name: "图书",
    component: NavView,
    meta: { show: true },
    children: [
      {
        path: "/book",
        name: "图书管理",
        meta: { show: true },
        components: { book: BookView },
      },
      {
        path: "/borrow",
        name: "借阅图书",
        meta: { show: true },
        components: { borrow: BorrowView },
      },
      {
        path: "/return",
        name: "归还图书",
        meta: { show: true },
        components: { return: ReturnView },
      },
      {
        path: "/overtime",
        name: "超时查询",
        meta: { show: true },
        components: { overtime: OvertimeView },
      },
    ],
  },
  {
    path: "/nav",
    name: "用户",
    component: NavView,
    meta: { show: true },
    children: [
      {
        path: "/user",
        name: "用户管理",
        meta: { show: true },
        components: { user: UserView },
      },
    ],
  },
  {
    path: "/nav",
    name: "其他",
    component: NavView,
    meta: { show: true },
    children: [
      {
        path: "/system",
        name: "系统设置",
        meta: { show: true },
        components: { system: SystemView },
      },
      {
        path: "/about",
        name: "关于",
        meta: { show: true },
        components: { about: AboutView },
      },
      {
        path: "/test",
        name: "测试",
        meta: { show: true },
        components: { test: TestPageView },
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
