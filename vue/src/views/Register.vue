<template>
<div style="width: 100%;height:100vh;
background-image:url(https://w.wallhaven.cc/full/g7/wallhaven-g7evdl.png);overflow: hidden;">
  <div style="width: 400px;margin: 150px auto">
    <div style="color:whitesmoke; font-weight: bold;font-size: 30px; text-align: center;padding: 30px">
      欢迎注册
    </div>
      <el-form ref="form" :model="form" size="normal" :rules="rules">
        <el-form-item prop="username">
          <el-input prefix-icon="UserFilled" v-model="form.username" placeholder="用户名" ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input prefix-icon="Lock" v-model="form.password" placeholder="用户密码" show-password="false"></el-input>
        </el-form-item>
        <el-form-item prop="confirm">
          <el-input prefix-icon="Lock" v-model="form.confirm" placeholder="请再次输入密码"
                    show-password="false" @blur="toConfirm">
          </el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onRegister" style="width: 100%;">注册</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="$router.push('/login')" style="width: 100%;">返回</el-button>
        </el-form-item>
      </el-form>
  </div>
</div>

</template>

<script>
import request from "../utils/request";

export default {
  name: "Register",
  data(){
    return{
      rules:{
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          // { min: 3, max: 5, message: '用户名长度至少要3位', trigger: 'blur' },
        ],
        password:[
          {required: true, message: '请输入密码', trigger: 'blur'}
        ],
        confirm:[
          {required: true, message: '请确认密码', trigger: 'blur'}
        ]
      },
      form:{}
    }
  },
  methods:{
    toConfirm(){
      if (this.form.password != this.form.confirm){
        //弹出警告
        this.$message({
          type: "error",
          message: "前后两次密码不一致！"
        })
      }
    },
    onRegister(){
      this.$refs['form'].validate((valid) => {
        //合法
        if (valid) {
          request.post("/admin/register",this.form).then(res =>{
            if (res.code === 200){
              this.$message({
                type: "error",
                message: res.extend.result
              })
            }else if (res.code === 100){
              this.$router.push("/login");
              this.$message({
                type: "success",
                message: res.extend.result
              })
            }
          })
        } else {//不合法
          return false;
        }
      });
    },
  }


}
</script>

<style scoped>

</style>