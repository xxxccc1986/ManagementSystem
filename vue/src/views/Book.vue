<template>
  <div style="padding: 10px">
  <!--  功能区域  -->
    <div style="margin: 10px 0px">
    <el-button type="primary" size="normal" @click="add">新增</el-button>
      <el-popconfirm title="确定要删除吗?" @confirm="deleteBatch">
        <template #reference >
          <el-button size="normal" type="danger" >批量删除</el-button>
        </template>
      </el-popconfirm>
<!--    <el-button type="primary">导出</el-button>-->
    </div>

    <!--  搜索区域  -->
    <div style="margin: 10px 0px">
      <!-- clearable属性给输入框添加清空输入的功能-->
      <el-input v-model="search" placeholder="请输入关键字" style="width: 25%" clearable />
      <el-button type="primary" style="margin-left: 5px" @click="load" >查询</el-button>
    </div>

    <!--  数据展示区域  -->
    <el-table :data="tableData"
              border
              style="width: 100%"
              @selection-change="handleSelectionChange"
              stripe >
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-checkbox-button ></el-checkbox-button>
      <el-table-column prop="id" label="id"  sortable />
      <el-table-column prop="name" label="书籍名字"  />
      <el-table-column prop="price" label="价格"  />
      <el-table-column prop="author" label="作者"  />
      <el-table-column prop="createdTime" label="出版日期"  />
      <el-table-column  label="书籍封面" >
        <template #default="scope">
          <el-image
              style="width: 50%;height: 50%"
              :src= "scope.row.cover"
              :preview-src-list="[scope.row.cover]"
              preview-teleported="true"
          ></el-image>
        </template>
      </el-table-column>
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
          :page-sizes="[3, 5, 10]"
          :small="small"
          :disabled="disabled"
          :background="background"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />

      <!--   添加书籍的表单   -->
      <el-dialog v-model="addBook" title="添加书籍" width="30%" >
        <el-form :model="form" label-width="120px">
          <el-form-item label="书籍名字" >
            <el-input v-model="form.name" style="width: 50%" clearable/>
          </el-form-item>
          <el-form-item label="价格" >
            <el-input v-model="form.price" style="width: 50%" clearable/>
          </el-form-item>
          <el-form-item label="作者" >
            <el-input v-model="form.author" style="width: 50%" clearable/>
          </el-form-item>
          <el-form-item label="出版日期">
            <el-col :span="11">
              <el-date-picker type="date" clearable placeholder="选择日期" v-model="form.createdTime" style="width: 110%;"></el-date-picker>
            </el-col>
          </el-form-item>
          <el-form-item label="封面">
            <el-upload ref="upload"
                       :action="fileUploadUrl" :on-success="handleSuccess">
              <el-button type="primary">点击上传</el-button>
            </el-upload>
            <span>上传的jpg文件不能超过500kb！！</span>
          </el-form-item>
          <el-form-item >
            <el-button type="primary" @click="ensure">确定</el-button>
            <el-button @click="addBook = false">取消</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>

      <!--   修改书籍的表单   -->
      <el-dialog v-model="dialogVisible" title="修改书籍" width="30%" >
        <el-form :model="form" label-width="120px">
          <el-form-item label="书籍名字" >
            <el-input v-model="form.name" style="width: 50%" clearable/>
          </el-form-item>
          <el-form-item label="价格" >
            <el-input v-model="form.price" style="width: 50%" clearable/>
          </el-form-item>
          <el-form-item label="作者" >
            <el-input v-model="form.author" style="width: 50%" clearable/>
          </el-form-item>
          <el-form-item label="出版日期">
            <el-col :span="11">
              <el-date-picker type="date" clearable placeholder="选择日期" v-model="form.createdTime" style="width: 110%;"></el-date-picker>
            </el-col>
          </el-form-item>
          <el-form-item label="封面">
            <el-upload ref="upload"
                :action="fileUploadUrl" :on-success="handleSuccess">
              <el-button type="primary">点击上传</el-button>
            </el-upload>
            <span>上传的jpg文件不能超过500kb！！</span>
          </el-form-item>
          <el-form-item >
          <el-button type="primary" @click="ensure">确定</el-button>
          <el-button @click="dialogVisible = false">取消</el-button>
          </el-form-item>
          </el-form>
      </el-dialog>

    </div>
  </div>
</template>

<script lang="js" >
import request from "../utils/request";
import {UploadUrl} from "../../public/static/config"

export default {
  //定义组件名字
  name: 'Book',
  //局部注册其他地方的组件
  components: {
  },
  //定义该组件的私有数据
  data(){
    return{
      checked: false,
      form: {},
      addBook: false,
      dialogVisible: false,
      search: '',
      currentPage: 1,
      pageSize: 3,
      total: 0,
      tableData: [],
      ids: [], //需要统一删除的id
      fileUploadUrl: "http://" + UploadUrl + ":9090/file/upload"
    }
  },
  created() {
    this.load();
  },
  //定义该组件的业务处理方法
  methods:{
    //批量删除的功能代码
    deleteBatch(){
      if (this.ids.length === 0){
        this.$message.warning("请先选择需要删除的数据！")
        return false;
      }else {
        request.delete("/book/deleteBatch/" + this.ids).then(res =>{
          if (res.code === 100){
            this.$message.success("批量删除成功")
            this.load()
          }else {
            this.$message.error("批量删除失败")
          }
        })
      }
    },
    //当用户的选择项发生变化时会触发该事件
    //selection参数为用户当前选择的行数据
    handleSelectionChange(selection){
      this.ids = selection.map(selection => selection.id)  //map方法可以将多个行数据对象中的id抽取出来形成一个新的数组
    },
    handleSuccess(res){
      this.form.cover = res.extend.url;
    },
    load(){
      request.get("/book/query", {
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
      console.log(row.id)
      this.dialogVisible = true;
      this.form = JSON.parse(JSON.stringify(row))
      this.$nextTick(() => {  //对尚未加载的元素进行处理
        if (this.$refs['upload']){  //判断文件是否存在
          this.$refs['upload'].clearFiles() ; //清楚历史文件列表
        }
      })
    },
    handleDelete(id){
      // request.delete("//delete/"+id).then(res => {
      request.delete("/book/delete/",{
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
      this.addBook = true;
      this.form = {};
      if (this.$refs['upload']){  //判断文件是否存在
        this.$refs['upload'].clearFiles() ; //清楚历史文件列表
      }
    },
    ensure(){
      if (this.form.id != null){//更新用户
        request.put("/book/update",this.form).then(res => {
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
        request.post("/book/save",this.form).then(res => {
          if (res.code === 100){
            this.$message({
              type:"success",
              message:"新增成功"
            })
            this.load();
            this.addBook = false;
          }else {
            this.$message({
              type:"error",
              message:"新增失败"
            })
            this.addBook = true;
          }
        }).catch(reason => {
          console.log(reason)
        })
      }
    }
  },
}
</script>

