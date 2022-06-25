<template>
  <div >
<!--    头部-->
    <Header/>
<!--    主体-->
    <div style="display:flex">
<!--    侧边栏  -->
      <AsideMenu/>
<!--   内容区域   -->
      <router-view style="flex: 1"/>
    </div>

  </div>

</template>

<style>

</style>

<script>
//import需要使用的组件，并且在下面进行局部注册，才能在当前文件使用组件标签调用该组件
import Header from "./components/Header";
import AsideMenu from "./components/AsideMenu";

export default{
  //局部注册以下组件
  //这里的AsideMenu是缩写，完整写法是 AsideMenu：AsideMenu
  components:{
    AsideMenu,
    Header,
  }
}
</script>