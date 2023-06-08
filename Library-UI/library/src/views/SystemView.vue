<template>
  <div class="system">
    <el-container>
      <el-main>
        <!--    管理员信息设置-->
        <el-card>
          <template #header>
            <div class="card-header">
              <span>管理员信息设置</span>
            </div>
          </template>
          <el-button
            type="primary"
            @click="updatePasswordDialog(updatePasswordFormRef)"
            >修改管理员密码</el-button
          >
        </el-card>

        <!--    图书种类信息设置-->
        <el-card>
          <template #header>
            <div class="card-header">
              <span>图书种类设置</span>
            </div>
          </template>
          <el-button
            type="primary"
            @click="addBookTypeDialog(addBookTypeFormRef)"
            >添加图书种类</el-button
          >
          <el-button
            type="primary"
            @click="deleteBookTypeDialog(deleteBookTypeFormRef)"
            >删除图书种类</el-button
          >
        </el-card>

        <!--        修改密码对话框-->
        <el-dialog
          v-model="updatePasswordDialogVisible"
          title="修改密码"
          width="500px"
          :close-on-click-modal="false"
        >
          <el-form
            :model="updatePasswordForm"
            :rules="updatePasswordRules"
            ref="updatePasswordFormRef"
          >
            <el-form-item label="新密码" prop="password">
              <el-input
                v-model="updatePasswordForm.password"
                type="password"
                autocomplete="off"
              />
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

        <!--        添加图书种类对话框-->
        <el-dialog
          v-model="addBookTypeDialogVisible"
          title="添加图书种类"
          width="500px"
          :close-on-click-modal="false"
        >
          <el-form
            :model="addBookTypeForm"
            :rules="BookTypeRules"
            ref="addBookTypeFormRef"
          >
            <el-form-item label="种类名称" prop="typeName">
              <el-input v-model="addBookTypeForm.typeName" autocomplete="off" />
            </el-form-item>
          </el-form>
          <template #footer>
            <span class="dialog-footer">
              <el-button @click="addBookTypeDialogVisible = false"
                >取消</el-button
              >
              <el-button
                type="primary"
                @click="addBookType(addBookTypeFormRef)"
              >
                添加
              </el-button>
            </span>
          </template>
        </el-dialog>

        <!--        删除图书种类对话框-->
        <el-dialog
          v-model="deleteBookTypeDialogVisible"
          title="删除图书种类"
          width="500px"
          :close-on-click-modal="false"
        >
          <el-form
            :model="deleteBookTypeForm"
            :rules="BookTypeRules"
            ref="deleteBookTypeFormRef"
          >
            <el-form-item label="种类名称" prop="typeName">
              <el-input
                v-model="deleteBookTypeForm.typeName"
                autocomplete="off"
              />
            </el-form-item>
          </el-form>
          <template #footer>
            <span class="dialog-footer">
              <el-button @click="deleteBookTypeDialogVisible = false"
                >取消</el-button
              >
              <el-button
                type="danger"
                @click="deleteBookType(deleteBookTypeFormRef)"
              >
                删除
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
import axios from "axios";
import { ElMessageBox } from "element-plus";

// 修改密码对话框
let updatePasswordDialogVisible = ref(false);
const updatePasswordDialog = (formEl: FormInstance | undefined) => {
  updatePasswordDialogVisible.value = true;
  if (!formEl) return;
  formEl.resetFields();
};

// 修改密码表单
const updatePasswordFormRef = ref<FormInstance>();
const updatePasswordForm = reactive({
  password: "",
});

// 修改密码表单规则
const updatePasswordRules = reactive<FormRules>({
  password: [{ required: true, message: "请输入新密码", trigger: "blur" }],
});

// 修改密码
const updatePassword = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.validate((valid) => {
    if (valid) {
      axios
        .post("http://localhost:8888/user/update/admin", updatePasswordForm)
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
        });
    }
  });
};

// 图书种类表单规则
const BookTypeRules = reactive<FormRules>({
  typeName: [
    { required: true, message: "请输入图书种类名称", trigger: "blur" },
  ],
});

// 添加图书种类
let addBookTypeDialogVisible = ref(false);
const addBookTypeDialog = (formEl: FormInstance | undefined) => {
  addBookTypeDialogVisible.value = true;
  if (!formEl) return;
  formEl.resetFields();
};

// 添加图书种类表单
const addBookTypeFormRef = ref<FormInstance>();
const addBookTypeForm = reactive({
  typeName: "",
});

// 添加图书种类
const addBookType = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.validate((valid) => {
    if (valid) {
      axios
        .post("http://localhost:8888/type/add", addBookTypeForm)
        .then((resp) => {
          const statusCode = resp.data.statusCode;

          // 添加失败
          if (statusCode == 0) {
            ElMessageBox.alert("添加图书种类失败，请重试", "信息", {
              confirmButtonText: "确认",
            });
          }
          // 添加成功
          if (statusCode == 1) {
            ElMessageBox.alert("添加图书种类成功", "信息", {
              confirmButtonText: "确认",
              callback: () => {
                addBookTypeDialogVisible.value = false;
              },
            });
          }
          // 图书种类已存在
          if (statusCode == 2) {
            ElMessageBox.alert("该种类名称已存在", "信息", {
              confirmButtonText: "确认",
            });
          }
        });
    }
  });
};

// 删除图书种类
let deleteBookTypeDialogVisible = ref(false);
const deleteBookTypeDialog = (formEl: FormInstance | undefined) => {
  deleteBookTypeDialogVisible.value = true;
  if (!formEl) return;
  formEl.resetFields();
};

// 删除图书种类表单
const deleteBookTypeFormRef = ref<FormInstance>();
const deleteBookTypeForm = reactive({
  typeName: "",
});

// 删除图书种类
const deleteBookType = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.validate((valid) => {
    if (valid) {
      axios
        .post("http://localhost:8888/type/delete", deleteBookTypeForm)
        .then((resp) => {
          const statusCode = resp.data.statusCode;

          // 删除失败
          if (statusCode == 0) {
            ElMessageBox.alert("删除图书种类失败，请重试", "信息", {
              confirmButtonText: "确认",
            });
          }
          // 删除成功
          if (statusCode == 1) {
            ElMessageBox.alert("删除图书种类成功", "信息", {
              confirmButtonText: "确认",
              callback: () => {
                deleteBookTypeDialogVisible.value = false;
              },
            });
          }
          // 图书种类不存在
          if (statusCode == 2) {
            ElMessageBox.alert("该种类名称不存在", "信息", {
              confirmButtonText: "确认",
            });
          }
        });
    }
  });
};
</script>

<style lang="scss">
@import "../assets/css/system";
</style>
