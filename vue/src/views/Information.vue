<template>
<!--  <div >-->

    <div style="padding: 20px">
      <el-card style="width: 40%">
        <div style="padding: 20px;" >
          <span>用户个人信息:</span>
        </div>
        <el-form :model="form" label-width="120px">
          <el-form-item label="id" v-show="false" > <!-- 相当于隐藏域的作用 -->
            <el-input v-model="form.id"/>
          </el-form-item>
          <el-form-item label="用户名" >
            <el-input v-model="form.username" disabled style="width: 50%"/>
          </el-form-item>
          <el-form-item label="密码" >
            <el-input v-model="form.password" style="width: 50%"/>
          </el-form-item>
          <el-form-item label="昵称" >
            <el-input v-model="form.nickName" style="width: 50%"/>
          </el-form-item>
          <el-form-item label="年龄" >
            <el-input v-model="form.age" style="width: 50%"/>
          </el-form-item>
          <el-form-item label="性别" >
            <el-radio v-model="form.sex" label="男">男</el-radio>
            <el-radio v-model="form.sex" label="女">女</el-radio>
          </el-form-item>
          <el-form-item label="地址" >
            <el-input type="textarea" v-model="form.address" style="width: 50%" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary"  @click="changeUser" >修改</el-button>
            <el-button @click="this.$router.push('/user')">返回</el-button>
          </el-form-item>
        </el-form>
      </el-card>

<!--    </div>-->
  </div>


</template>

<script>
import request from "../utils/request";
export default {
  name: "Information",
  data(){
    return{
      form: {},
      user: JSON.parse(sessionStorage.getItem("user"))
    }
  },
  created(){
    this.form = this.user
  },
  methods:{
    changeUser(){
      request.put("/user/update",this.form).then(res => {
        if (res.code === 100){
          this.$message({
            type: "success",
            message : "修改成功"
          })
          this.$router.push("/information")
        }else {
          this.$message({
            type: "error",
            message : "修改失败"
          })
          return false;
        }
      })
    }
  }
}
</script>

<style scoped>

</style>