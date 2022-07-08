<template>
  <div style="padding: 10px">
  <!--  功能区域  -->
    <div style="margin: 10px 0px">
    <el-button type="primary" @click="add">新增</el-button>
      <el-popconfirm title="确定要删除吗?" @confirm="deleteBatch">
        <template #reference >
          <el-button  type="danger" >批量删除</el-button>
        </template>
      </el-popconfirm>
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
              stripe
              @selection-change="handleSelectionChange">
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column prop="id" label="id"  sortable />
      <el-table-column prop="title" label="新闻标题"  />
      <el-table-column prop="author" label="作者"  />
      <el-table-column prop="createTime" label="发布时间"  />
      <el-table-column fixed="right" label="操作" >
        <template #default="scope">
          <!--  scope.row 是取得当前这一行的所有数据，实际上获取了当前这个行的所有内容-->
          <el-button  type="success" @click="showDetail(scope.row)">
            详情
          </el-button>
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

      <!--  显示富文本详情的文本     -->
      <el-dialog v-model="editorDetail" title="内容详情" width="50%" >
        <el-card>
          <div v-html="detail.context" style="width: 200px;height: 200px;"></div>  <!-- 显示每行数据表单中的context的内容 -->
        </el-card>
      </el-dialog>

      <!--   添加新闻的表单   -->
      <el-dialog v-model="addNews" title="添加新闻" width="50%" >
        <el-form :model="form" label-width="120px">
          <el-form-item label="新闻标题" >
            <el-input v-model="form.title" style="width: 80%" clearable/>
          </el-form-item>

          <div id="div2"></div>

        </el-form>

        <template #footer>
          <span class="dialog-footer">
            <el-button type="primary" @click="ensure" >确定</el-button>
            <el-button @click="addNews = false">取消</el-button>
          </span>
        </template>

      </el-dialog>

      <!--   修改新闻的表单   -->
      <el-dialog v-model="dialogVisible" title="修改新闻" width="50%" >
        <el-form :model="form" label-width="120px">
          <el-form-item label="新闻标题" >
            <el-input v-model="form.title" style="width: 80%" clearable/>
          </el-form-item>

          <div id="div1"></div>

        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button type="primary" @click="ensure" >确定</el-button>
            <el-button @click="dialogVisible = false">取消</el-button>
          </span>
        </template>
      </el-dialog>

    </div>
  </div>
</template>

<script lang="js" >
import request from "../utils/request";
import {UploadUrl} from "../../public/static/config"
import E from 'wangeditor'

let editor;


export default {
  //定义组件名字
  name: 'News',
  //局部注册其他地方的组件
  components: {
  },
  //定义该组件的私有数据
  data(){
    return{
      user: JSON.parse(sessionStorage.getItem("user")),
      form: {},
      addNews: false,
      dialogVisible: false,
      search: '',
      currentPage: 1,
      pageSize: 5,
      total: 0,
      tableData: [],
      detail :{},
      editorDetail : false,
      ids : [],
      fileUpload : UploadUrl
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
        request.delete("/news/deleteBatch/" + this.ids).then(res =>{
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
    showDetail(row){
      this.editorDetail = true
      this.detail = row;
    },
    handleSuccess(res){
      this.form.cover = res.extend.url
    },
    load(){
      request.get("/news/query", {
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
      // this.form = JSON.parse(JSON.stringify(row))
      this.dialogVisible = true;
      this.form = row

      this.$nextTick(() => {
        if (editor != null){
          editor.destroy();
        }
        //关联弹窗div，创建一个富文本editor对象
        editor = new E('#div1')
        //配置图片上传的地址
        editor.config.uploadImgServer = 'http://' + this.fileUpload + ':9090/file/editorUpload';
        editor.config.uploadFileName = "file";

        editor.create();
        editor.txt.html(row.context)

      })

    },
    handleDelete(id){
      // request.delete("//delete/"+id).then(res => {
      request.delete("/news/delete/",{
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
      this.addNews = true;
      this.form = {}; //清空数据

      //对尚未完成的元素进行缓冲
      this.$nextTick(() => {

        //关联弹窗div，创建一个富文本editor对象
        editor = new E('#div2')
        //配置图片上传的地址
        editor.config.uploadImgServer = 'http://' + this.fileUpload + ':9090/file/editorUpload';
        editor.config.uploadFileName = "file";  //设置文件上传的名称
        editor.create();

      })

    },
    ensure(){
      //获取富文本编辑器内容的文本值放入form对象中
      this.form.context = editor.txt.html();

      if (this.form.id != null){//更新用户
        request.put("/news/update",this.form).then(res => {
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

        //将表单的作者名字保存为当前登录用户名
        this.form.author = this.user.nickName;

        request.post("/news/save",this.form).then(res => {
          if (res.code === 100){
            this.$message({
              type:"success",
              message:"新增成功"
            })
            this.load();
            this.addNews = false;
          }else {
            this.$message({
              type:"error",
              message:"新增失败"
            })
            this.addNews = true;
          }
        }).catch(reason => {
          console.log(reason)
        })
      }
    }
  },
}
</script>

