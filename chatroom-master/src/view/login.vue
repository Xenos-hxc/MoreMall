<template>
  <div style="background-color: #f3f7f9;width: 210.5vh;height: 100vh">
    <div style="position: absolute;width:100%;height:70px;background-color: #ffffff;box-shadow: 0 0 25px #cac6c6;">
  <span style=";float: left">
    <img src="../assets/gongsi/gongsi.png" style="width: 50px;margin-left: 50px;margin-top: 10px">
  </span>
      <span
          style="font-size: 30px;font-family: 宋体;float: left;margin-left: 20px;margin-top: 20px">MoreMall</span>
      <span style=";float: right">
    <img src="../assets/gongsi/经营更简单.png" style="width: 200px;margin-right: 100px;margin-top: 10px">
  </span>
      <div
          style="position: absolute;background-color:#FFFFFF;width: 1000px;height: 600px;top: 100px;left: 300px;box-shadow: 0 0 25px #cac6c6;">
        <div style="width: 422px;float: left">
          <img src="../assets/gongsi/让经营更简单2.png" style="width: 422px">
        </div>
        <div style="float: left" v-show="win">
          <div style="margin-left: 130px;margin-top: 100px">
            <el-input v-model="loginsend.id" style="width: 300px;border-radius:100%;" placeholder="账号"
                      icon="el-icon-search">
            </el-input>
            <br><br><br>
            <el-input v-model="loginsend.password" style="width: 300px" placeholder="密码" show-password>
            </el-input>
          </div>
          <br><br>
<!--          <div class="block" style="margin-left: 130px;width: 300px">-->
<!--            <el-cascader-->
<!--                clearable-->
<!--                placeholder="您的身份"-->
<!--                style="width: 300px"-->
<!--                v-model="value"-->
<!--                :options="options"-->
<!--                :props="{ expandTrigger: 'hover' }"-->
<!--                @change="handleChange"></el-cascader>-->
<!--          </div>-->
<!--          <br>-->
          <div style="display: flex">
            <el-input style="flex: 1;margin-left: 130px" size="medium" v-model="code"></el-input>
            <Identify :identifyCode="identifyCode" @click.native="refreshCode"/>
          </div>
          <br>
          <div style="margin-left: 365px;color: rgba(158,161,177,1);cursor: pointer">
            忘记密码?
          </div>
          <br><br>
          <div>
            <el-button type="primary" style="margin-left: 130px;width: 130px" @click="login()">
              登录
            </el-button>
            <el-button type="info" style="margin-left:40px;width: 130px" @click="goregister()">
              注册
            </el-button>
          </div>
        </div>

        <!--注册-->

        <div style="float: left" v-show="!win">
          <div style="margin-left: 130px;margin-top: 100px">
            <el-input v-model="registersend.userid" style="width: 300px;border-radius:100%;" placeholder="账号"
                      icon="el-icon-search">
            </el-input>
            <br><br><br>
            <el-input v-model="registersend.userid" style="width: 300px;border-radius:100%;" placeholder="昵称"
                      icon="el-icon-search">
            </el-input>
            <br><br><br>
            <el-input v-model="registersend.password" style="width: 300px" placeholder="密码" show-password>
            </el-input>
            <br><br><br>
            <el-input v-model="registersend.password" style="width: 300px" placeholder="确认密码" show-password>
            </el-input>
          </div>
          <br><br><br>
          <div>
            <el-button type="primary" style="margin-left: 130px;width: 303px" @click="goregister">
              去登录
            </el-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Qs from "qs";
import Identify from "@/components/Identify";

export default {
  components: {
    Identify
  },
  name: "login",
  data() {
    return {
      win: true,
      loginsend: {
        id: '',
        password: ''
      },
      code: '',   // 表单绑定的验证码
      identifyCode: '',
      // 验证码规则
      identifyCodes: '123456789ABCDEFGHGKMNPQRSTUVWXY',
      registersend: {
        userid: '',
        password: '',
        checkpassword: '',
        username: '',
        identity: ''
      },
      options: [
        , {
          value: '管理员',
          label: '管理员'
        }, {
          value: '会员',
          label: '会员'
        }],
      value: []
    }
  },
  created() {
  },
  mounted() {
    this.refreshCode()
  },
  methods: {
    // 切换验证码
    refreshCode() {
      this.identifyCode = ''
      this.makeCode(this.identifyCodes, 4)
    },
    // 生成随机验证码
    makeCode(o, l) {
      for (let i = 0; i < l; i++) {
        this.identifyCode += this.identifyCodes[Math.floor(Math.random() * (this.identifyCodes.length))]
      }
    },
    login() {
      this.$axios.post("/api/staff/login", Qs.stringify(this.loginsend)).then(res => res.data).then(res => {
        if (!this.code) {
          this.$message.warning('请输入验证码')
          this.refreshCode()
          return
        }
        if (this.code.toLowerCase() !== this.identifyCode.toLowerCase()) {
          this.$message.warning('验证码错误')
          this.refreshCode()
          return
        }
        if (res.code==200) {
          this.$message.success("登录成功")
          this.$router.push({path: "/admin/head"})
          console.log(res);
          localStorage.setItem('id',res.data.id);
        }else {
          this.$message.error("账号或者密码错误")
        }

      })

    },
    handleChange(value) {
      console.log(value);
    },
    goregister() {
      this.test()
      this.win = !this.win
    }
  }
}
</script>

<style scoped>

</style>
