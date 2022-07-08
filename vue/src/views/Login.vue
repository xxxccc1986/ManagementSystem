<template>
<div style="width: 100%;height:100vh;
background-image:url(https://w.wallhaven.cc/full/g7/wallhaven-g7evdl.png);overflow: hidden;">
  <div style="width: 400px;margin: 150px auto">
    <div style="color:whitesmoke; font-weight: bold;font-size: 30px; text-align: center;padding: 30px">
      欢迎登录管理系统
    </div>
      <el-form ref="form" :model="form" size="normal" :rules="rules">
        <el-form-item prop="username">
          <el-input prefix-icon="UserFilled" v-model="form.username" placeholder="用户名"  ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input prefix-icon="Lock" v-model="form.password" placeholder="用户密码" show-password="false"></el-input>
        </el-form-item>
        <el-form-item >
          <div style="display:flex;">
            <el-input prefix-icon="key" v-model="form.validCode" style="width: 58%;height: 58%"  placeholder="请输入验证码" ></el-input>
            <ValidCode @input="createValidCode" style="width: 150px"/>
          </div>
        </el-form-item>
        <el-form-item>
            <el-button type="primary" @click="toLogin" style="width:190px;" >登录</el-button>
            <el-button @click="reset" style="width:195px;">忘记密码？</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="Register" style="width: 100%;">没有账户？点击注册</el-button>
        </el-form-item>
      </el-form>
  </div>
</div>

</template>

<script>
import request from "../utils/request";
import ValidCode from "../components/ValidCode";

export default {
  name: "Login",
  components : {
    ValidCode
  },
  data(){
    return{
      form:{},
      rules:{
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          // { min: 3, max: 5, message: '用户名长度至少要3位', trigger: 'blur' },
        ],
        password:[
          {required: true, message: '请输入密码', trigger: 'blur'}
        ]
      },
      validCode: '',
    }
  },
  methods:{
    //接收验证码组件提交的四位验证码数字
    createValidCode(data){
      this.validCode = data;
    },
    toLogin(){
      this.$refs['form'].validate((valid) => {
        //合法
        if (valid) {
          //验证是否已经填写验证码
          if (!this.form.validCode){
            this.$message.error("请填写验证码")
            return false
          }
          //判断表单验证码是否和生成的验证码一致
          if (this.form.validCode.toLowerCase() !== this.validCode.toLowerCase()){
            this.$message.error("验证码不正确，请重试")
            return false
          }
          request.get("/admin/login",{
                params:{
                  username:this.form.username,
                  password:this.form.password,
                }
              }
          ).then(res => {
            if (res.extend.user == null){
              this.$message({
                type:"error",
                message:"登录失败，用户名或密码错误"
              })
            }else{
              this.$message({
                type:"success",
                message:"登录成功"
              })
              //将登录的用户信息放入sessionStorage
              sessionStorage.setItem("user",JSON.stringify(res.extend.user))
              //登录成功之后，vue实现页面跳转
              this.$router.push("/")
              console.log(sessionStorage.getItem("user"))
            }
          });
        } else {//不合法
          return false;//阻止表单的默认提交行为
        }
      });

    },
    Register(){
      this.$router.push("/register")
    },
    reset(){
      this.$router.push("/reSetPassword")
    }
  }


}
</script>

<style scoped>

</style>