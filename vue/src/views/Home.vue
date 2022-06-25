<template>
  <div style="padding: 10px">
  <!--  功能区域  -->
    <div style="margin: 10px 0px">
    <el-button type="primary" @click="add">新增</el-button>
    <el-button type="primary">导入</el-button>
    <el-button type="primary">导出</el-button>
    </div>

    <!--  搜索区域  -->
    <div style="margin: 10px 0px">
      <!-- clearable属性给输入框添加清空输入的功能-->
      <el-input v-model="search" placeholder="请输入关键字" style="width: 25%" clearable />
      <el-button type="primary" style="margin-left: 5px" @click="load" >查询</el-button>
    </div>

    <!--  数据展示区域  -->
    <el-table :data="tableData" border style="width: 100%" stripe >
      <el-table-column prop="id" label="id"  sortable />
      <el-table-column prop="userName" label="用户名"  />
      <el-table-column prop="password" label="密码"  />
      <el-table-column prop="nickName" label="昵称"  />
      <el-table-column prop="age" label="年龄"  />
      <el-table-column prop="sex" label="性别"  />
      <el-table-column prop="address" label="地址" />
      <el-table-column fixed="right" label="操作" >
        <template #default="scope">
          <!--  scope.row 是取得当前这一行的所有数据，实际上获取了当前这个行的所有内容-->
          <el-button  type="primary" @click="handleEdit(scope.row)">
            编辑
          </el-button>
          <el-popconfirm title="确定要删除吗?" @confirm="handleDelete(scope.row.id)">
            <template #reference >
          <el-button  type="danger" >删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <!--  分页区域  -->
    <div style="margin: 10px 0px">
      <el-pagination
          v-model:currentPage="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[5, 10, 20]"
          :small="small"
          :disabled="disabled"
          :background="background"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />

      <el-dialog v-model="dialogVisible" title="添加用户" width="30%" >
        <el-form :model="form" label-width="120px">
          <el-form-item label="用户名" >
            <el-input v-model="form.userName" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="密码" >
            <el-input v-model="form.password" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="昵称" >
            <el-input v-model="form.nickName" style="width: 80%"/>
          </el-form-item>
          <el-form-item label="年龄" >
            <el-input v-model="form.age" style="width: 80%" />
          </el-form-item>
          <el-form-item label="性别" >
            <el-radio v-model="form.sex" label="男">男</el-radio>
            <el-radio v-model="form.sex" label="女">女</el-radio>
          </el-form-item>
          <el-form-item label="地址" >
            <el-input type="textarea" v-model="form.address" style="width: 80%" />
          </el-form-item>
          </el-form>
        <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="ensure">确定</el-button>
      </span>
        </template>
      </el-dialog>

    </div>
  </div>
</template>

<script lang="js" >
import request from "../utils/request";
import axios from "axios";

export default {
  //定义组件名字
  name: 'Home',
  //局部注册其他地方的组件
  components: {
  },
  //定义该组件的私有数据
  data(){
    return{
      form: {},
      dialogVisible: false,
      search: '',
      currentPage: 1,
      pageSize: 5,
      total: 0,
      tableData: []
    }
  },
  created() {
    this.load();
  },
  //定义该组件的业务处理方法
  methods:{
    // load:function(){
    //   axios({
    //     method: "get",
    //     url: "/api/user/query",
    //     contentType: "application/json",
    //     param:{
    //       searchParams: JSON.stringify(this.searchData)
    //     }
    //     // param:{
    //     //   pageNum: this.currentPage,
    //     //   pageSize: this.pageSize,
    //     //   search: this.search
    //     // }
    //   }).then(function (res) {
    //     console.log(res)
    //   }).catch(function (reason) {
    //     console.log(reason)
    //       }
    //   )
    // },

    load(){
      request.get("/user/query", {
      params:{
        pageNum: this.currentPage,
        pageSize: this.pageSize,
        search: this.search
      }}).then(res => {
        this.tableData = res.extend.data.records //将查询的数据在table中展示
        this.total = res.extend.data.total//显示数据总条数
      }).catch(reason => {
        console.log(reason)
      })
    },
    handleEdit(row){
      this.dialogVisible = true;
      this.form = JSON.parse(JSON.stringify(row))
    },
    handleDelete(id){
      // request.delete("/user/delete/"+id).then(res => {
      request.delete("/user/delete/",{
        params:{
          id:id
        }}).then(res => {
        if (res.code === 100){
          this.$message({
            type:"success",
            message:"删除成功"
          })
          this.load();
        }else {
          this.$message({
            type:"error",
            message:"删除失败"
          })
        }
      }).catch(reason => {
        console.log(reason)
      })
    },
    handleSizeChange(){
        this.load();
    },
    handleCurrentChange(){
        this.load();
    },
    add:function () {
      this.dialogVisible = true;
      this.form = {};
    },
    ensure(){
      if (this.form.id != null){//更新用户
        request.put("/user/update",this.form).then(res => {
          if (res.code === 100){
            this.$message({
              type:"success",
              message:"更新成功"
            })
            this.load();
            this.dialogVisible = false;
          }else {
            this.$message({
              type:"error",
              message:"更新失败"
            })
            this.dialogVisible = true;
          }
        }).catch(reason => {
          console.log(reason)
        })
      }else {//新增用户
        request.post("/user/save",this.form).then(res => {
          if (res.code === 100){
            this.$message({
              type:"success",
              message:"新增成功"
            })
            this.load();
            this.dialogVisible = false;
          }else {
            this.$message({
              type:"error",
              message:"更新失败"
            })
            this.dialogVisible = true;
          }
        }).catch(reason => {
          console.log(reason)
        })
      }
    }
  },
}
</script>

