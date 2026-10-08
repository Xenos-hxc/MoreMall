<template>
  <el-card>
    <template #header>
      <div class="card-header">
        <span>会员管理</span>
      </div>
    </template>
    <!-- 按钮组 -->
    <el-row style="margin-top: 20px;">
      <el-col>
        <el-button type="danger" @click="batchDeleteDialogVisible = true">批量删除</el-button>
        <el-button type="success" @click="showAddUserDialog">添加</el-button>
        <el-button type="primary" @click="exportCheckedUsers">批量导出</el-button>
        <el-button type="primary" @click="exportUsers">全部导出</el-button>
      </el-col>
    </el-row>
    <el-table :data="users" style="width: 100%" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50"></el-table-column>
      <el-table-column prop="username" label="用户名" width="200"></el-table-column>
      <el-table-column prop="age" label="年龄" width="150"></el-table-column>
      <el-table-column prop="mobile" label="手机" width="200"></el-table-column>
      <el-table-column prop="email" label="邮箱" width="200"></el-table-column>
      <el-table-column prop="address" label="地址" width="200"></el-table-column>
      <el-table-column label="状态" width="180">
        <template #default="scope">
          <el-tag :type="scope.row.status === 0 ? 'danger' : 'success'">
            {{ scope.row.status === 0 ? '冻结' : '激活' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="260">
        <template #default="scope">
          <el-button type="danger" size="small" @click="confirmDeleteUser(scope.row.id)">
            <i class="el-icon-delete"></i> 删除
          </el-button>
          <el-button :type="scope.row.status === 0 ? 'success' : 'warning'" size="small" @click="changeStatus(scope.row.id)">
            <i class="el-icon-edit"></i> {{ scope.row.status === 0 ? '激活' : '冻结' }}
          </el-button>
          <el-button type="primary" size="small" @click="viewUser(scope.row)">
            <i class="el-icon-view"></i> 查看
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-row>
      <el-col style="text-align: center; margin-top: 20px">
        <el-button :disabled="page <= 1" @click="prevPage">上一页</el-button>
        <el-button :disabled="isLastPage" @click="nextPage">下一页</el-button>
      </el-col>
    </el-row>

    <!-- 添加用户弹窗 -->
    <el-dialog title="添加用户" :visible.sync="addUserDialogVisible">
      <el-form :model="newUser">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="用户名">
              <el-input v-model="newUser.username"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="密码">
              <el-input v-model="newUser.password"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="手机">
              <el-input v-model="newUser.mobile"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮箱">
              <el-input v-model="newUser.email"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="年龄">
              <el-input v-model="newUser.age"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="地址">
              <el-input v-model="newUser.address"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <template #footer>
        <el-button @click="addUserDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="addUser">添加</el-button>
      </template>
    </el-dialog>

    <!-- 查看用户弹窗 -->
    <el-dialog title="查看用户" :visible.sync="viewUserDialogVisible">
      <el-form :model="viewUserDetail" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="viewUserDetail.username" disabled></el-input>
        </el-form-item>
        <el-form-item label="年龄">
          <el-input v-model="viewUserDetail.age" disabled></el-input>
        </el-form-item>
        <el-form-item label="手机">
          <el-input v-model="viewUserDetail.mobile" disabled></el-input>
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="viewUserDetail.email" disabled></el-input>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="viewUserDetail.address" disabled></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="viewUserDialogVisible = false">关闭</el-button>
      </template>
    </el-dialog>

    <!-- 删除确认弹窗 -->
    <el-dialog title="确认删除" :visible.sync="deleteUserDialogVisible">
      <span>确定要删除这个用户吗？</span>
      <template #footer>
        <el-button @click="deleteUserDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="deleteUser">确定</el-button>
      </template>
    </el-dialog>

    <!-- 批量删除确认弹窗 -->
    <el-dialog
        title="确认批量删除"
        :visible.sync="batchDeleteDialogVisible"
        width="30%"
        :close-on-click-modal="false"
    >
      <span>确定要删除选中的用户吗？</span>
      <template #footer>
        <el-button @click="batchDeleteDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="deleteChecked">确定</el-button>
      </template>
    </el-dialog>

  </el-card>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import { Message } from 'element-ui';
const batchDeleteDialogVisible = ref(false)
const users = ref([])
const page = ref(1)
const size = ref(10)
const isLastPage = ref(false)
const addUserDialogVisible = ref(false)
const viewUserDialogVisible = ref(false)
const deleteUserDialogVisible = ref(false)
const newUser = ref({
  username: '',
  password:'',
  age: '',
  mobile: '',
  email: '',
  address: '',
  status: 1
})
const viewUserDetail = ref({
  username: '',
  age: '',
  mobile: '',
  email: '',
  address: '',
})
const userIdToDelete = ref(null)

const loadUsers = async () => {
  try {
    const response = await axios.get('/api/users/page', { params: { page: page.value, size: size.value } })
    users.value = response.data.map(user => ({ ...user, _checked: false }))
    isLastPage.value = users.value.length < size.value
  } catch (error) {
    Message.error('加载用户失败')
  }
}


const selectedUsers = ref([])


const handleSelectionChange = (selectedRows) => {
  selectedUsers.value = selectedRows
}

const deleteChecked = async () => {
  if (selectedUsers.value.length === 0) {
    Message.warning('请选择要删除的用户')
    return
  }
  try {
    const ids = selectedUsers.value.map(user => user.id)
    // 发送批量删除请求
    await axios.delete('/api/users/deleteBatch', {
      data: ids
    })
    Message.success('批量删除成功')
    await loadUsers()
    batchDeleteDialogVisible.value = false
  } catch (error) {
    Message.error('批量删除失败')
  }
}


const confirmDeleteUser = (id) => {
  userIdToDelete.value = id
  deleteUserDialogVisible.value = true
}

const deleteUser = async () => {
  try {
    await axios.delete(`/api/users/delete/${userIdToDelete.value}`)
    Message.success('删除成功')
    await loadUsers()
    deleteUserDialogVisible.value = false
  } catch (error) {
    Message.error('删除失败')
  }
}

const changeStatus = async (id) => {
  try {
    // 获取当前用户的状态
    const user = users.value.find(u => u.id === id);
    const newStatus = user.status === 0 ? 1 : 0; // 切换状态

    await axios.put(`/api/users/changeStatus`, null, {
      params: {
        id: id,
        status: newStatus
      }
    });

    Message.success('状态更新成功');
    await loadUsers(); // 重新加载用户数据
  } catch (error) {
    Message.error('状态更新失败');
  }
}


const viewUser = (user) => {
  viewUserDetail.value = {...user}
  viewUserDialogVisible.value = true
}

const exportUsers = async () => {
  try {
    await axios.get(`/api/users/export`);
    Message.success('全部导出成功');
  } catch (error) {
    Message.error('全部导出失败');
  }
}
const exportCheckedUsers = async () => {
  if (selectedUsers.value.length === 0) {
    Message.warning('请选择要导出的用户')
    return
  }
  try {
    const ids = selectedUsers.value.map(user => user.id)
    // 发送批量导出请求
    await axios.post('/api/users/exportChecked', ids)
    Message.success('批量导出成功')
  } catch (error) {
    Message.error('批量导出失败')
  }
}
const prevPage = () => {
  if (page.value > 1) {
    page.value -= 1
    loadUsers()
  }
}

const nextPage = async () => {
  if (!isLastPage.value) {
    page.value += 1
    await loadUsers()
  } else {
    Message.error('没有更多数据了')
  }
}

const showAddUserDialog = () => {
  newUser.value = {
    username: '',
    password:'',
    age: '',
    mobile: '',
    email: '',
    address: '',
    status:1
  }
  addUserDialogVisible.value = true
}

const addUser = async () => {
  try {
    if (newUser.value.username === '')Message.error('请输入用户名')
    else if (newUser.value.password === '')Message.error('请输入密码')
    else if (newUser.value.age === '')Message.error('请输入年龄')
    else if (newUser.value.mobile === '')Message.error('请输入手机号')
    else if (newUser.value.email === '')Message.error('请输入邮箱')
    else if (newUser.value.address === '')Message.error('请输入地址')
    else {
      await axios.post('/api/users/add', newUser.value)
      Message.success('添加成功')
      addUserDialogVisible.value = false
      await loadUsers()
    }
  } catch (error) {
    Message.error('添加失败')
  }
}

onMounted(loadUsers)
</script>
