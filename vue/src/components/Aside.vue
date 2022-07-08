<template>
  <div>
    <!--    router属性可以利用嵌套路由进行菜单栏中的目录跳转     -->
    <el-menu
        style="width: 200px;min-height: calc(100vh - 50px)"
        :default-active= "path"
        router
        class="el-menu-vertical-demo">
      <el-sub-menu index="1" v-if="this.user.role === 1">
        <template #title><span>系统管理</span></template>
        <el-menu-item index="/user">用户管理</el-menu-item>
      </el-sub-menu>
      <el-menu-item index="/book">
        <span>书籍管理</span>
      </el-menu-item>
      <el-menu-item index="/news">
        <span>新闻管理</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script>
import request from "../utils/request";

export default {
    name: "Aside",
    data(){
      return{
        user :  {},
        path : this.$route.path, //设置默认高亮的菜单项
      }
    },
    created() {
      this.user = JSON.parse(sessionStorage.getItem("user") || "{}")
      request.get("/user/" + this.user.id).then(res => {
        if(res.code === 100 )
        this.user = res.extend.user
      })
    }
}


</script>
