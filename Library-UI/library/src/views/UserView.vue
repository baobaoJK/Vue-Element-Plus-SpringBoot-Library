<template>
  <div class="user">
    <el-container>
      <el-main>
        <!--        用户操作栏-->
        <el-row class="user-header">
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
          <el-col :span="4" class="add-button-pane">
            <el-button
              type="success"
              :icon="Plus"
              class="add-button"
              @click="addFromButton(addUserFormRef)"
            >
              添加用户
            </el-button>
          </el-col>
        </el-row>
        <!--        用户表格栏-->
        <el-row class="user-table">
          <el-col>
            <el-table :data="users" height="100%" empty-text="没有数据">
              <el-table-column fixed prop="id" label="Id" width="50" />
              <el-table-column prop="groups" label="组名" />
              <el-table-column prop="username" label="用户名" />
              <el-table-column prop="name" label="姓名" />
              <el-table-column prop="gender" label="性别" />
              <el-table-column prop="idCard" label="借书卡号" />
              <el-table-column prop="phone" label="手机号" />
              <el-table-column prop="identity" label="身份" />
              <el-table-column prop="bookCount" label="可借书数量" />
              <el-table-column prop="state" label="用户状态">
                <template #default="users">
                  <span v-if="users.row.state === 1">正常</span>
                  <span v-if="users.row.state === 0">冻结</span>
                </template>
              </el-table-column>
              <el-table-column fixed="right" label="操作">
                <template #default="users">
                  <el-button
                    @click="editFromButton(editUserFormRef, users.row)"
                    v-if="users.row.id !== 1"
                    type="text"
                    >编辑</el-button
                  >
                  <el-button
                    @click="deleteUserDialog(users.row)"
                    v-if="users.row.id !== 1"
                    type="text"
                    >删除</el-button
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

        <!--         添加用户表单 -->
        <el-dialog
          v-model="addUserFormVisible"
          title="添加用户"
          class="add-user-dialog"
          width="500px"
          :close-on-click-modal="false"
        >
          <el-form
            :model="addUserForm"
            :rules="userRules"
            ref="addUserFormRef"
            class="add-user-form"
          >
            <el-form-item
              label="所属组"
              :label-width="formLabelWidth"
              prop="groups"
            >
              <el-select
                v-model="addUserForm.groups"
                placeholder="请选择所属组"
              >
                <el-option
                  v-for="item in UserType"
                  :key="item.typeId"
                  :label="item.label"
                  :value="item.typeName"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item
              label="用户名"
              :label-width="formLabelWidth"
              prop="username"
            >
              <el-input
                v-model="addUserForm.username"
                autocomplete="off"
              ></el-input>
            </el-form-item>
            <el-form-item
              label="密码"
              :label-width="formLabelWidth"
              prop="password"
            >
              <el-input
                v-model="addUserForm.password"
                type="password"
                autocomplete="off"
              ></el-input>
            </el-form-item>
            <el-form-item
              label="姓名"
              :label-width="formLabelWidth"
              prop="name"
            >
              <el-input
                v-model="addUserForm.name"
                autocomplete="off"
              ></el-input>
            </el-form-item>
            <el-form-item
              label="性别"
              :label-width="formLabelWidth"
              prop="gender"
            >
              <el-select v-model="addUserForm.gender" placeholder="请选择性别">
                <el-option
                  v-for="item in GenderType"
                  :key="item.typeId"
                  :label="item.typeName"
                  :value="item.typeName"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item
              label="借书卡号"
              :label-width="formLabelWidth"
              prop="idCard"
            >
              <el-input
                v-model.number="addUserForm.idCard"
                autocomplete="off"
              ></el-input>
            </el-form-item>
            <el-form-item
              label="手机号"
              :label-width="formLabelWidth"
              prop="phone"
            >
              <el-input
                v-model.number="addUserForm.phone"
                autocomplete="off"
                maxlength="11"
              ></el-input>
            </el-form-item>
            <el-form-item
              label="身份"
              :label-width="formLabelWidth"
              prop="identity"
            >
              <el-select
                v-model="addUserForm.identity"
                placeholder="请选择身份"
              >
                <el-option
                  v-for="item in IdentityType"
                  :key="item.typeId"
                  :label="item.typeName"
                  :value="item.typeName"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-form>
          <template #footer>
            <span class="dialog-footer">
              <el-button @click="addUserFormVisible = false">取消</el-button>
              <el-button type="primary" @click="addUserButton(addUserFormRef)">
                添加
              </el-button>
            </span>
          </template>
        </el-dialog>

        <!--        编辑用户表单-->
        <el-dialog
          v-model="editUserFormVisible"
          title="添加用户"
          class="edit-user-dialog"
          width="500px"
          :close-on-click-modal="false"
        >
          <el-form
            :model="editUserForm"
            :rules="userRules"
            ref="editUserFormRef"
            class="edit-user-form"
          >
            <el-form-item
              label="所属组"
              :label-width="formLabelWidth"
              prop="groups"
            >
              <el-select
                v-model="editUserForm.groups"
                placeholder="请选择所属组"
              >
                <el-option
                  v-for="item in UserType"
                  :key="item.typeId"
                  :label="item.label"
                  :value="item.typeName"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item
              label="用户名"
              :label-width="formLabelWidth"
              prop="username"
            >
              <el-input
                v-model="editUserForm.username"
                autocomplete="off"
                disabled
              ></el-input>
            </el-form-item>
            <el-form-item
              label="密码"
              :label-width="formLabelWidth"
              prop="password"
            >
              <el-input
                v-model="editUserForm.password"
                type="password"
                autocomplete="off"
              ></el-input>
            </el-form-item>
            <el-form-item
              label="姓名"
              :label-width="formLabelWidth"
              prop="name"
            >
              <el-input
                v-model="editUserForm.name"
                autocomplete="off"
              ></el-input>
            </el-form-item>
            <el-form-item
              label="性别"
              :label-width="formLabelWidth"
              prop="gender"
            >
              <el-select v-model="editUserForm.gender" placeholder="请选择性别">
                <el-option
                  v-for="item in GenderType"
                  :key="item.typeId"
                  :label="item.label"
                  :value="item.typeName"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item
              label="借书卡号"
              :label-width="formLabelWidth"
              prop="idCard"
            >
              <el-input
                v-model.number="editUserForm.idCard"
                autocomplete="off"
                disabled
              ></el-input>
            </el-form-item>
            <el-form-item
              label="手机号"
              :label-width="formLabelWidth"
              prop="phone"
            >
              <el-input
                v-model.number="editUserForm.phone"
                autocomplete="off"
                maxlength="11"
              ></el-input>
            </el-form-item>
            <el-form-item
              label="身份"
              :label-width="formLabelWidth"
              prop="identity"
            >
              <el-select
                v-model="editUserForm.identity"
                placeholder="请选择身份"
              >
                <el-option
                  v-for="item in IdentityType"
                  :key="item.typeId"
                  :label="item.typeName"
                  :value="item.typeName"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item
              label="借书数量"
              :label-width="formLabelWidth"
              prop="bookCount"
            >
              <el-input
                v-model.number="editUserForm.bookCount"
                autocomplete="off"
              ></el-input>
            </el-form-item>
            <el-form-item
              label="账号状态"
              :label-width="formLabelWidth"
              prop="state"
            >
              <el-select
                v-model="editUserForm.state"
                placeholder="选择账号状态"
              >
                <el-option
                  v-for="item in StateType"
                  :key="item.id"
                  :label="item.label"
                  :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-form>
          <template #footer>
            <span class="dialog-footer">
              <el-button @click="editUserFormVisible = false">取消</el-button>
              <el-button
                type="primary"
                @click="editUserButton(editUserFormRef)"
              >
                编辑
              </el-button>
            </span>
          </template>
        </el-dialog>

        <!--        删除用户对话框-->
        <el-dialog
          v-model="deleteUserDialogVisible"
          title="删除用户"
          width="500px"
        >
          <span>确认删除 {{ deleteName }}</span>
          <template #footer>
            <span class="dialog-footer">
              <el-button @click="deleteUserDialogVisible = false"
                >取消</el-button
              >
              <el-button type="danger" @click="deleteUser"> 确认 </el-button>
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
import { Plus, Search } from "@element-plus/icons-vue";
import axios from "axios";
import { ElMessageBox } from "element-plus";

// 标签长度
let formLabelWidth = 120;

// 获取用户数据
let users = ref();
const getUser = () => {
  axios
    .get("http://localhost:8888/user/" + pageNum.value + "/" + pageSize.value)
    .then((resp) => {
      console.log(resp);
      users.value = resp.data.content;
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
    getUser();
  } else {
    searchUser();
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
    getUser();
  } else {
    searchUser();
  }
};

// 搜索框选项
let searchModel = ref("username");
let searchOptions = [
  {
    value: "username",
    label: "用户名",
  },
  {
    value: "idCard",
    label: "借书卡号",
  },
  {
    value: "phone",
    label: "手机号",
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
  searchUser();
};
// 搜索用户
const searchUser = () => {
  if (searchInput.value != "") {
    axios
      .get(
        "http://localhost:8888/user/search/" +
          searchModel.value +
          "/" +
          searchInput.value +
          "/" +
          pageNum.value +
          "/" +
          pageSize.value
      )
      .then((resp) => {
        users.value = resp.data.content;
        pageTotal.value = resp.data.totalElements;
      });
  } else {
    getUser();
  }
};

// 用户表单判断
const userRules = reactive<FormRules>({
  groups: [{ required: true, message: "请选择组名称", trigger: "change" }],
  username: [{ required: true, message: "请输入用户名称", trigger: "blur" }],
  password: [{ required: true, message: "请输入密码", trigger: "blur" }],
  name: [{ required: true, message: "请输入用户姓名", trigger: "blur" }],
  gender: [{ required: true, message: "请选择性别", trigger: "change" }],
  idCard: [{ required: true, message: "请输入借书卡号", trigger: "blur" }],
  phone: [{ required: true, message: "请输入手机号", trigger: "blur" }],
  identity: [{ required: true, message: "请选择身份", trigger: "change" }],
  bookCount: [
    { required: true, message: "请输入可借阅图书数量", trigger: "blur" },
  ],
  state: [{ required: true, message: "请选择账号状态", trigger: "change" }],
});

// 选项框属性
const UserType = ref([
  {
    typeId: 1,
    typeName: "admin",
    label: "管理员",
  },
  {
    typeId: 2,
    typeName: "user",
    label: "用户",
  },
]);
const GenderType = ref([
  {
    typeId: 1,
    typeName: "男",
  },
  {
    typeId: 2,
    typeName: "女",
  },
]);
const IdentityType = ref([
  {
    typeId: 1,
    typeName: "管理员",
  },
  {
    typeId: 2,
    typeName: "学生",
  },
  {
    typeId: 3,
    typeName: "教师",
  },
]);
const StateType = ref([
  {
    id: 1,
    value: 1,
    label: "正常",
  },
  {
    id: 2,
    value: 0,
    label: "冻结",
  },
]);

// 添加用户对话框显示
let addUserFormVisible = ref(false);
// 添加用户表单按钮
const addFromButton = (formEl: FormInstance | undefined) => {
  addUserFormVisible.value = true;
  if (!formEl) return;
  formEl.resetFields();
};

// 添加用户表单
const addUserFormRef = ref<FormInstance>();
let addUserForm = reactive({
  groups: "",
  username: "",
  password: "",
  name: "",
  gender: "",
  idCard: "",
  phone: "",
  identity: "",
});

// 添加用户按钮
const addUserButton = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.validate((valid) => {
    if (valid) {
      axios
        .post("http://localhost:8888/user/save", addUserForm)
        .then((resp) => {
          const statusCode = resp.data.statusCode;

          // 添加失败
          if (statusCode == 0) {
            ElMessageBox.alert("添加用户失败，请重试", "信息", {
              confirmButtonText: "确认",
            });
          }
          // 添加成功
          if (statusCode == 1) {
            ElMessageBox.alert("添加成功", "信息", {
              confirmButtonText: "确认",
              callback: () => {
                addUserFormVisible.value = false;
              },
            });
          }
          // 用户名存在
          if (statusCode == 2) {
            ElMessageBox.alert("添加失败，此用户名已存在", "信息", {
              confirmButtonText: "确认",
            });
          }
          // 借书卡存在
          if (statusCode == 3) {
            ElMessageBox.alert("添加失败，此借书卡已存在", "信息", {
              confirmButtonText: "确认",
            });
          }
          // 手机号存在
          if (statusCode == 4) {
            ElMessageBox.alert("添加失败，此手机号已存在", "信息", {
              confirmButtonText: "确认",
            });
          }
        });
    } else {
      return false;
    }
  });
};

// 编辑用户对话框
let editUserFormVisible = ref(false);
const editFromButton = (formEl: FormInstance | undefined, row: number) => {
  editUserFormVisible.value = true;
  editUserForm = reactive(JSON.parse(JSON.stringify(row)));
  if (!formEl) return;
  formEl.resetFields();
};

// 编辑用户表单
const editUserFormRef = ref<FormInstance>();
let editUserForm = reactive({
  groups: "",
  username: "",
  password: "",
  name: "",
  gender: "",
  idCard: "",
  phone: "",
  identity: "",
  bookCount: 0,
  state: 1,
});

// 编辑用户按钮
const editUserButton = (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  formEl.validate((valid) => {
    if (valid) {
      axios
        .post("http://localhost:8888/user/update", editUserForm)
        .then((resp) => {
          const statusCode = resp.data.statusCode;

          // 编辑失败
          if (statusCode == 0) {
            ElMessageBox.alert("编辑用户失败，请重试", "信息", {
              confirmButtonText: "确认",
            });
          }
          // 编辑成功
          if (statusCode == 1) {
            ElMessageBox.alert("编辑成功", "信息", {
              confirmButtonText: "确认",
              callback: () => {
                editUserFormVisible.value = false;
              },
            });
          }
          // 手机号
          if (statusCode == 2) {
            ElMessageBox.alert("编辑失败，此手机号已存在", "信息", {
              confirmButtonText: "确认",
            });
          }
        });
    } else {
      return false;
    }
  });
};

// 删除用户
let deleteName = ref("");
let deleteId = ref(0);
let deleteUserDialogVisible = ref(false);
const deleteUserDialog = (row: any) => {
  deleteId.value = row.id;
  deleteName.value = row.username;
  deleteUserDialogVisible.value = true;
};

// 删除用户按钮
const deleteUser = () => {
  if (deleteId.value) {
    axios
      .post("http://localhost:8888/user/delete/" + deleteId.value)
      .then((resp) => {
        const statusCode = resp.data.statusCode;

        // 删除失败
        if (statusCode == 0) {
          ElMessageBox.alert("删除用户失败，请重试", "信息", {
            confirmButtonText: "确认",
          });
        }
        // 删除成功
        if (statusCode == 1) {
          ElMessageBox.alert("删除成功", "信息", {
            confirmButtonText: "确认",
            callback: () => {
              deleteUserDialogVisible.value = false;
            },
          });
        }
        // Id 不存在
        if (statusCode == 2) {
          ElMessageBox.alert("删除失败，此 Id 不存在", "信息", {
            confirmButtonText: "确认",
          });
        }
      });
  }
};

// 初始化
const init = () => {
  getUser();
};
init();
</script>

<style lang="scss">
@import "../assets/css/user";
</style>
