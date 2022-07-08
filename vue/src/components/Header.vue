<template>
  <div style="height: 50px; line-height: 50px; border-bottom: 1px solid #ccc; display: flex" >
    <div style="width: 200px;padding-left: 30px;font-weight: bold;color: dodgerblue">后台管理</div>
    <div style="flex: 1"></div>
    <div style="width: 40px;padding: 5px" class="block"><el-avatar :size="40" :src="photo"></el-avatar></div>
    <div style="width: 100px; padding: 15px" >
      <el-dropdown>
        <span class="el-dropdown-link" >
          {{user?.nickName}}<el-icon class="el-icon--right"><ArrowDownBold /></el-icon>
        </span>
        <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="info">个人信息</el-dropdown-item>
              <el-dropdown-item @click="exit">退出系统</el-dropdown-item>
            </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
export default {
  name: "Header",
  data() {
    return {
      user: JSON.parse(sessionStorage.getItem("user")),
      photo: "http://m.imeitou.com/uploads/allimg/2016060815/g1qemno1c4t.jpg",
    }
  },
  methods:{
    info(){
      this.$router.push("/information")
    },
    exit(){
      this.$router.push("/login")
      sessionStorage.removeItem("user")
    }
  }
}
</script>

<style>

</style>