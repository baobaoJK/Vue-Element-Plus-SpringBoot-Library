import { createRouter, createWebHistory, RouteRecordRaw } from "vue-router";
import LoginView from "@/views/LoginView.vue";
import NavView from "@/views/NavView.vue";
import BookView from "@/views/BookView.vue";
import UserView from "@/views/UserView.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "登录",
    component: LoginView,
    meta: { show: false },
  },
  {
    path: "/login",
    name: "用户登录",
    component: LoginView,
    meta: { show: false },
  },
  {
    path: "/nav",
    name: "导航",
    component: NavView,
    redirect: "/book",
    meta: { show: true },
    children: [
      {
        path: "/book",
        name: "图书信息",
        component: BookView,
        meta: { show: true },
      },
      {
        path: "/user",
        name: "用户信息",
        component: UserView,
        meta: { show: true },
      },
    ],
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
