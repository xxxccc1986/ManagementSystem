<template>
  <!--    头部-->
  <Header />
  <!--    主体-->
  <div style="display:flex">
    <!--    侧边栏  -->
    <Aside />
    <!--   内容区域   -->
    <router-view style="flex: 1"/>
  </div>
</template>

<script lang="js">
//import需要使用的组件，并且在下面进行局部注册，才能在当前文件使用组件标签调用该组件
import Header from "../components/Header";
import Aside from "../components/Aside";

export default {
  name: "layout",
  components :{
    Aside,
    Header,
  }
}
</script>
