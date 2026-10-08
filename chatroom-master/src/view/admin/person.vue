<template>
  <div style="margin-left: 10%; margin-top: 20px">
    <el-card style="height: 600px; width: 1200px;border-radius: 15px" class="box-card">
      <div slot="header" class="clearfix" >
        <span><i class="el-icon-user-solid"></i> 个人中心</span>
        <el-button style="float: right; padding: 3px 0" type="text" @click="update"><i class="el-icon-edit-outline"></i>修改</el-button>
      </div>
      <div class="container">
        <el-row :gutter="20">
          <!-- 侧边栏 -->
          <el-col :span="16">
<!--            <p>用户名: {{admin.name}}</p>-->
            <div>
              <span style="font-weight: bold;width: 80px;display: inline-block">用户名:</span>
              <span style="margin-left: 20px">{{admin.name}}</span>
            </div>
<!--            <hr style='background-color:	#DCDCDC; height:1px; border:none;'/>-->
            <el-divider></el-divider>
<!--            <p>手机号: {{admin.mobile}}</p>-->
            <div>
              <span style="font-weight: bold;width: 80px;display: inline-block">手机号:</span>
              <span style="margin-left: 20px">{{admin.mobile}}</span>
            </div>
<!--            <hr style='background-color:	#DCDCDC; height:1px; border:none;'/>-->
            <el-divider></el-divider>
<!--            <p>个人邮箱: {{admin.email}}</p>-->
            <div>
              <span style="font-weight: bold;width: 80px;display: inline-block">个人邮箱:</span>
              <span style="margin-left: 20px">{{admin.email}}</span>
            </div>
<!--            <hr style='background-color:	#DCDCDC; height:1px; border:none;'/>-->
            <el-divider></el-divider>
          </el-col>
          <!-- 主内容区域 -->
          <el-col :span="8">
            <el-image style="width: 370px; height: 370px;border-radius: 50%" :src="admin.img" fit="fill" :preview-src-list="srcList">

            </el-image>
            <el-upload style="margin-left: 145px"
                       class="avatar-uploader"
                       :show-file-list="false"
                       :on-success="handleCoverSuccess"
                       :action="'/api/staff/upload'">
              <span style="font-size: 12px;"><i class="el-icon-edit"></i>点击更换头像</span>
            </el-upload>
          </el-col>
        </el-row>
      </div>
    </el-card>

    <!--    弹出框-->
    <el-dialog title="修改信息"
               :visible.sync="dialogVisible"
               width="30%">
      <el-form label-position="right" label-width="80px" :model="adminData" :rules="rules" ref="adminData">
        <el-form-item label="名称" prop="name">
          <el-input v-model="adminData.name" placeholder="请输入名称"></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="mobile">
          <el-input v-model="adminData.mobile" placeholder="请输入手机号"></el-input>
        </el-form-item>
        <el-form-item label="个人邮箱" prop="email">
          <el-input v-model="adminData.email" placeholder="请输入个人邮箱"></el-input>
        </el-form-item>
      </el-form>
  <span slot="footer" class="dialog-footer">
    <el-button @click="cancel('adminData')">取 消</el-button>
    <el-button type="primary" @click="submitForm('adminData')">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
import Qs from "qs";
export default {
  name: "person",
  data() {
    return {
      dialogVisible: false,
      admin:{
        id:localStorage.getItem('id'),
        name:'xxxx',
        mobile: '1xxxxxxxxxx',
        email: 'xxxxx@xx.com',
        img:'https://tse4-mm.cn.bing.net/th/id/OIP-C.ykqJ-9-lJOLIcxlY1n5utgHaFV?rs=1&pid=ImgDetMain'
      },
      adminData:{
        id:localStorage.getItem('id'),
        name:'',
        mobile:'',
        email:''
      },
      rules:{
        name:[
          { required: true, message: '请输入名称', trigger: 'blur' },
          { min:1,max: 10, message: '长度在 1 到 10 个字符', trigger: 'blur' }
        ],
        mobile:[
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { pattern:/^1(3\d|4[5-9]|5[0-3,5-9]|7[0-9]|8[1-2,4-9])\d{8}$/,message: '请输入正确的手机号',trigger: 'blur'}
        ],
        email:[
          { required: true, message: '请输入个人邮箱', trigger: 'blur' },
          { pattern:/^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/,message: '请输入正确的个人邮箱',trigger: 'blur'}
        ]
      },
      srcList:[
      ],
      avatarSend:{
        id:localStorage.getItem('id'),
        avatar:'',
      }
    }
  },
  created() {
    this.getAdminById();
  },
  methods: {
    getAdminById(){
      this.$axios.post("/api/staff/getAdminById", Qs.stringify(this.admin)).then((res)=>{
        this.admin.name=res.data.data.name;
        this.adminData.name = res.data.data.name;
        this.admin.img = res.data.data.avatar;
        this.srcList=[];
        this.srcList.push(res.data.data.avatar);
        if(res.data.data.email!==null) {
          this.admin.email = res.data.data.email;
          this.adminData.email = res.data.data.email;

        }
        else this.admin.email = '无';
        if(res.data.data.mobile!==null) {
          this.admin.mobile = res.data.data.mobile;
          this.adminData.mobile = res.data.data.mobile;
        }
        else this.admin.mobile = '无';
      })
    },
    handleCoverSuccess(res, file){
      console.log(res);
      this.admin.img=res.data;
      this.srcList=[];
      this.srcList.push(res.data);
      this.avatarSend.avatar = res.data;
      this.$axios.post("/api/staff/updateAvatar", Qs.stringify(this.avatarSend)).then((result)=>{
        console.log(result);
      })
    },
    update() {
      // this.adminData.name=this.admin.name;
      // this.adminData.mobile=this.admin.mobile;
      // this.adminData.email=this.admin.email;
      this.dialogVisible = true
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$axios.post("/api/staff/updateAdmin", this.adminData).then((res)=>{
            console.log(res);
            this.getAdminById();
            this.$message({
              message: '修改成功',
              type: 'success'
            });
          })
          this.dialogVisible = false;
          this.$refs[formName].resetFields();

        } else {
          console.log('error submit!!');
          return false;
        }

      });
    },
    cancel(formName) {
      this.$refs[formName].resetFields();
      this.dialogVisible = false;
    },
    handleClose(formName){
      this.$refs[formName].resetFields();
    }
  }
}
</script>

<style scoped>
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}

.box-card {
  width: 480px;
}

.el-card ::v-deep .el-card__header {
  background-color: #F6F6F6;
}
</style>
