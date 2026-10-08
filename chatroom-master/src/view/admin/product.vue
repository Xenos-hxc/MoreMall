<template>
  <div class="home">
    <el-card shadow="hover" style="margin-bottom: 20px">
      <div style="width: 100%">
        <el-row>
          <el-col :offset="8" :span=4>
            <div>
              <label class="el-form-item__label">商品名称: </label>
              <el-input
                  v-model="params.name"
                  placeholder="请输入商品名称"
                  style="width: 160px"
              ></el-input>
            </div>
          </el-col>
          <el-col :span=5>
            <el-button style="margin-left: 5px" type="primary" @click="load">
              <i class="el-icon-search"></i>搜索
            </el-button>
            <el-button style="margin-left: 8px" @click="reset">
              <i class="el-icon-refresh-right"></i>重置
            </el-button>
          </el-col>
        </el-row>
      </div>
    </el-card>
    <el-card class="box-card" shadow="hover">
      <div style="display: flex; margin-bottom: 10px;width: 100%">
        <el-row style="width: 100%">

          <el-col :span=18>
            <span style="font-weight: bold">商品详情</span>
          </el-col>
          <el-col :span=6>

            <el-button
                type="primary"
                @click="exportExcel"
            >
              <i class="el-icon-upload2"></i>
              导出
            </el-button>
            <el-button
                type="primary"
                @click="add"
            >
              <i class="el-icon-circle-plus-outline"></i>
              添加商品
            </el-button>
            <el-button
                type="danger"
                @click="deleteSelectItem"
            >
              <i class="el-icon-delete-solid"></i>
              批量删除
            </el-button>
          </el-col>

        </el-row>
      </div>

      <el-table
          id="table"
          :data="tableData"
          :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
          border
          stripe

          @export="exportExcel"
          @selection-change="handleSelectionChange"
      >
        <el-table-column
            align="center"
            type="selection"
            width="55"
        >
        </el-table-column>
        <el-table-column
            align="center"
            label="商品名称"
            prop="name"
        ></el-table-column>
        <el-table-column
            align="center"
            label="商品价格"
            prop="price"
        ></el-table-column>
        <el-table-column
            align="center"
            label="商品库存"
            prop="sum"
        ></el-table-column>
        <el-table-column
            align="center"
            label="商品介绍"
            prop="info"
        ></el-table-column>
        <el-table-column
            align="center"
            label="入库时间"
            prop="createdTime"
        >
          <template slot-scope="scope">
            {{ scope.row.addDate | formatTime }}
          </template>
        </el-table-column>
        <el-table-column
            align="center"
            label="状态"
            prop="status"
        >
          <template slot-scope="scope">
            <el-tag :type="scope.row.status === 1 ? 'success' : 'info'">{{ scope.row.status === 1 ? "上架" : "下架" }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column align="center" label="操作" width="300">
          <template v-slot="scope">
            <el-button type="text" @click="changeStatus(scope.row.id)">
              <i v-if="scope.row.status===0" class="el-icon-top"></i>
              <i v-else class="el-icon-bottom"></i>
              {{ scope.row.status === 1 ? "下架" : "上架" }}
            </el-button>
            <el-button style="margin-right: 5px" type="text" @click="edit(scope.row)">
              <i class="el-icon-edit"></i>
              编辑
            </el-button>
            <el-popconfirm
                style="margin-left: 4px"
                title="您确定删除这行数据吗？"
                @confirm="del(scope.row.id)"
            >
              <el-button
                  slot="reference"
                  style="margin-right: 5px"
                  type="text"
              >
                <i class="el-icon-delete"></i>删除
              </el-button
              >
            </el-popconfirm>
            <el-button type="text" @click="lookMessage(scope.row)">
              <i class="el-icon-grape"></i>
              查看
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--  分页-->
      <div style="margin-top: 20px; display: flex">
        <el-pagination
            :current-page="params.pageIndex"
            :page-size="params.pageSize"
            :total="total"
            background
            layout="prev, pager, next"
            @current-change="handleCurrentChange"
        >
        </el-pagination>
      </div>

      <el-dialog
          :visible.sync="IsAdd"
          title="新增商品"
          width="400px"
      >
        <el-form ref="addform" :model="addForm" label-width="80px" :rules="rules">
          <el-form-item label="商品名称" prop="name">
            <el-input v-model="addForm.name"></el-input>
          </el-form-item>

          <el-form-item label="商品价格" prop="price">
            <el-input v-model="addForm.price"></el-input>
          </el-form-item>
          <el-form-item label="商品库存" prop="sum">
            <el-input v-model="addForm.sum"></el-input>
          </el-form-item>
          <el-form-item label="商品介绍" prop="info">
            <el-input v-model="addForm.info"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="IsAdd = false">取 消</el-button>
          <el-button type="primary" @click="addMsg">确 定</el-button>
        </div>
      </el-dialog>

      <el-dialog :visible.sync="IsEdit" title="修改商品" width="400px">
        <el-form ref="editform" :model="editForm" label-width="80px" :rules="rules">
          <el-form-item label="商品名称" prop="name">
            <el-input v-model="editForm.name"></el-input>
          </el-form-item>

          <el-form-item label="商品价格" prop="price">
            <el-input v-model="editForm.price"></el-input>
          </el-form-item>
          <el-form-item label="商品库存" prop="sum">
            <el-input v-model="editForm.sum"></el-input>
          </el-form-item>
          <el-form-item label="商品介绍" prop="info">
            <el-input v-model="editForm.info"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="IsEdit = false">取 消</el-button>
          <el-button type="primary" @click="editMsg">确 定</el-button>
        </div>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
import FileSaver from 'file-saver'
import XLSX from 'xlsx'

export default {
  name: "head",
  data() {
    return {
      allData: [],
      tableData: [],
      addForm: {
        name: '',
        price: '',
        sum: '',
        info: '',
      },
      editForm: {
        name: '',
        price: '',
        sum: '',
        info: '',
      },
      total: 0,
      params: {
        pageSize: 10,
        pageIndex: 1,
        name: ""
      },
      IsAdd: false,
      IsEdit: false,
      multipleSelection: [],
      ids: [],
      rules:{
        name:[
          { required: true, message: '请输入商品名称', trigger: 'blur' },
          { min:1,max: 10, message: '长度在 1 到 10 个字符', trigger: 'blur' }
        ],
        price: [
          { required: true, message: '请输入商品价格', trigger: 'blur' },
          // 确保price是数字，并且格式正确（例如，最多包含两位小数）
          { validator: this.validatePrice, trigger: 'blur' }
        ],
        sum: [
          { required: true, message: '请输入商品总价', trigger: 'blur' },
          // 确保sum是数字，并且非负
          { validator: this.validateSum, trigger: 'blur' }
        ],
        // 假设info字段需要是非空的，并且长度不超过100个字符
        info: [
          { required: true, message: '请输入商品信息', trigger: 'blur' },
          { max: 100, message: '商品信息长度不能超过100个字符', trigger: 'blur' }
        ]
      },
    }
  },
  created() {
    this.load();
  },
  mounted() {

  },
  filters: {
    formatTime: function (value) {
      if (!value) return '';
      const date = new Date(value);
      return date.toLocaleString(); // 或者使用其他合适的时间格式化方法
    }
  },
  methods: {
    validatePrice(rule, value, callback) {
      if (value === '') {
        callback(new Error('请输入商品价格'));
      } else if (!/^\d+(\.\d{1,2})?$/.test(value)) {
        callback(new Error('商品价格格式不正确，应为数字，最多两位小数'));
      } else {
        callback();
      }
    },
    validateSum(rule, value, callback) {
      if (value === '') {
        callback(new Error('请输入商品库存'));
      } else if (!/^\d+(\.\d+)?$/.test(value)) {
        callback(new Error('商品库存格式不正确，应为数字'));
      } else if (parseFloat(value) < 0) {
        callback(new Error('总价不能为负数'));
      } else {
        callback();
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;//存储选中的数据
    },
    deleteSelectItem() {
      this.$confirm("此操作将永久删除, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
          .then(() => {
            //遍历获得多选选中的index值
            this.multipleSelection.forEach((value, index) => {
              // console.log(value.id);
              this.ids.push(value.id)
            });
            this.$axios.post("/api/product/deleteByIds" ,this.ids)
                .then(res => res.data)
                .then(res => {
                  if (res.code===200){
                    this.$message({
                      type: "success",
                      message: "删除成功!",
                    });
                    this.load();
                  }
                })
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消删除",
            });
          });
    },
    reset() {
      this.params = {
        pageSize: 10,
        pageIndex: 1,
      };
      this.load();
    },
    exportExcel() {
      let tableName = [];
      this.$axios.post("/api/product/listAllData").then(res => res.data)
          .then((res) => {
            if (res.code === 200) {
              console.log(res.data);
              this.allData = res.data;
              tableName = [
                ['编号', '商品名称', '商品价格', '库存', '访问量', "状态", "入库时间", "商品介绍"]//导出表头
              ]
              this.allData.forEach((item) => {
                let rowData = [];
                //导出内容的字段
                rowData = [
                  item.id,
                  item.name,
                  item.price,
                  item.sum,
                  item.visitCount,
                  item.status === 1 ? "上架" : "下架",
                  item.addDate,
                  item.info
                ]
                tableName.push(rowData)
              })
              let workSheet = XLSX.utils.aoa_to_sheet(tableName);
              let bookNew = XLSX.utils.book_new();
              XLSX.utils.book_append_sheet(bookNew, workSheet, 'sheet1') // 工作簿名称
              let name = 'result.xlsx'
              XLSX.writeFile(bookNew, name) // 保存的文件名
            }
          });


    },
    load() {
      this.$axios.post("/api/product/listAll", this.params).then(res => res.data)
          .then((res) => {
            if (res.code === 200) {
              console.log(res.data);
              this.tableData = res.data.data;
              this.total = res.data.count;
            }
          });
    },
    changeStatus(id) {
      console.log(id);
      this.$axios.post("/api/product/changeStatus?id=" + id).then(res => res.data)
          .then((res) => {
            if (res.code === 200) {
              this.$message.success("修改成功");
              this.load();
            }

          })
    },
    add() {
      this.IsAdd = true;
      this.$refs["addform"].resetFields();
    },
    addMsg() {
      this.$refs["addform"].validate((valid) => {
        if (valid) {
          this.$axios.post("/api/product/add", this.addForm).then(res => res.data)
              .then((res) => {
                if (res.code === 200) {
                  this.$notify.success("新增成功");
                  this.IsAdd = false;
                  this.load();
                } else {
                  this.$notify.error(res.msg);
                }
              });
        }
      });
    },
    handleCurrentChange(pageNum) {
      this.params.pageIndex = pageNum;
      this.load();
    },
    del(productId) {
      this.$axios.delete("/api/product/delete?productId=" + productId).then(res => res.data)
          .then((res) => {
            if (res.code === 200) {
              this.$notify.success("删除成功");
              this.load();
            } else {
              this.$notify.error(res.msg);
            }
          });
    },
    edit(row) {
      this.IsEdit = true;
      this.$axios.get("/api/product/getById/" + row.id).then(res => res.data)
          .then((res) => {
            console.log(res.data);
            this.editForm = res.data;
          });
    },
    editMsg() {
      this.$refs["editform"].validate((valid) => {
        if (valid) {
          this.$axios.post("/api/product/edit", this.editForm).then(res => res.data)
              .then((res) => {
                if (res.code === 200) {
                  this.$notify.success("更新成功");
                  this.$refs["editform"].resetFields();
                  this.IsEdit = false;
                  this.load();
                } else {
                  this.$notify.error(res.msg);
                }
              });
        }
      });
    },
    lookMessage(row) {

      this.$router.push({
        path: "/admin/ProductDetail",
        query: {
          "row":row,
          "name":this.$route.name
        }
      })
    }
  }
}
</script>

<style scoped>
.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}

.box-card {
  width: 100%;
}

.box-card2 {
  width: 600px;
  height: 600px;
}
</style>
