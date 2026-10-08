<template>
  <div>
    <el-menu
        :router="true"
        :default-active="activeIndex"
        class="el-menu-demo"
        mode="horizontal"
        @select="handleSelect">
       <span style=";float: left">
    <img src="../assets/gongsi/gongsi.png" style="width: 50px;margin-left: 50px;margin-top: 10px">
  </span>
      <el-menu-item index="/admin/head" style="margin-left: 200px">数据看板</el-menu-item>
      <el-menu-item index="/admin/product" style="margin-left: 100px">商品维护</el-menu-item>
      <el-menu-item index="/admin/user" style="margin-left: 100px">会员管理</el-menu-item>
      <el-menu-item index="/admin/person" style="margin-left: 100px">个人中心</el-menu-item>
      <el-menu-item index="/login" style="margin-left: 300px;color: black"  @click="back">退出</el-menu-item>

    </el-menu>

    <transition name="el-zoom-in-center">
      <router-view></router-view>
    </transition>
  </div>
</template>

<script>
export default {
  name: "headView",
  data(){
    return{
      activeIndex: '1',
      show2: true

    }

  },
  created() {

  },
  methods:{
    back(){
      // 清除状态保持
      window.localStorage.clear()

    },
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    }
  }
}
</script>

<style scoped>
.transition-box {
  margin-bottom: 10px;
  width: 200px;
  height: 100px;
  border-radius: 4px;
  background-color: #409EFF;
  text-align: center;
  color: #fff;
  padding: 40px 20px;
  box-sizing: border-box;
  margin-right: 20px;
}
</style>
