<template>
  <div>
    <div style="margin-left: 200px;margin-top: 15px">
      <span>
      <el-card class="box-card" style="height: 180px">
        <div slot="header" class="clearfix" align="center" style="height: 5px">
          <span>天气预报</span>
        </div>
        <div class="text item" style="height: 100px">
          <iframe width="565" height="98"
                  src="//tianqi.2345.com/plugin/widget/index.htm?s=2&z=3&t=1&v=0&d=3&bd=0&k=&f=&ltf=009944&htf=cc0000&q=1&e=1&a=1&c=54511&w=565&h=98&align=center"></iframe>
        </div>
      </el-card>
      </span>
    </div>

    <div style="margin-left: 810px;margin-top: -250px">
      <span style="margin-left: 500px">
      <el-card class="box-card" style="height: 180px">
        <div slot="header" class="clearfix" align="center" style="height: 5px">
          <span>音乐放松一下</span>
        </div>
        <div class="text item" style="height: 100px" align="center">
          <iframe width=330 height=86
                  src="//music.163.com/outchain/player?type=2&id=2050684657&auto=0&height=66"></iframe>
        </div>
      </el-card>
        </span>
    </div>

    <div style="margin-left: 196px;margin-top: 5px">
      <table>
        <tr>
          <td>
            <el-card class="box-card" style="height: 100px;width: 200px" align="center">
              {{ msg.userSum }}人
              <hr>
              <div style="font-size:12px">注册会员数</div>

            </el-card>
          </td>
          <td>
            <el-card class="box-card" style="height: 100px;width: 220px;margin-left:28px" align="center">
              {{ msg.proSum }}个
              <hr>
              <div style="font-size:12px">平台商品数量</div>

            </el-card>
          </td>
          <td>
            <el-card class="box-card" style="height: 100px;width: 220px;margin-left:28px" align="center">
              101241个
              <hr>
              <div style="font-size:12px">商品销售总量</div>

            </el-card>
          </td>
          <td>
            <el-card class="box-card" style="height: 100px;width: 220px;margin-left:28px" align="center">
              806642W
              <hr>
              <div style="font-size:12px">平台销售总额(W)</div>

            </el-card>
          </td>
          <td>
            <el-card class="box-card" style="height: 100px;width: 220px;margin-left:28px" align="center">
              1523人
              <hr>
              <div style="font-size:12px">实时在线会员数</div>

            </el-card>
          </td>
        </tr>
      </table>

    </div>


    <div style="margin-left: 200px;margin-top: 15px">
      <span>
      <el-card class="box-card" style="height: 300px">
        <div slot="header" class="clearfix" align="center" style="height: 5px">
          <span>商品访问占比分析</span>
        </div>
        <div class="text item" style="height: 200px">
          <!--第一个图-->

<el-col :span="12">
  <div id="pie" style="width: 300px;height: 300px;margin-left: 50px"></div>
</el-col>


        </div>
      </el-card>
      </span>
    </div>

    <div style="margin-left: 810px;margin-top: -322px">
      <span style="margin-left: 500px">
      <el-card class="box-card" style="height: 300px">
        <div slot="header" class="clearfix" align="center" style="height: 5px">
          <span>热卖商品分析</span>
        </div>
        <div class="text item" style="height: 200px" align="center">
          <!--第二个图-->


<el-col :span="12">
  <div id="bar" style="width: 450px;height: 280px;margin-left:50px "></div>
</el-col>


        </div>
      </el-card>
        </span>
    </div>


    <div style="margin-left: 200px;margin-top: 15px">
      <span>
      <el-card class="box-card" style="height: 320px">
        <div slot="header" class="clearfix" align="center" style="height: 5px">
          <span>热卖商品实施排行榜</span>
        </div>
        <div class="text item" style="height: 200px">
            <div class="table">
      <el-table align="center" :data="saleData" strip>

        <el-table-column prop="level" label="排名" width="70" align="center"></el-table-column>
        <el-table-column prop="name" label="商品" show-overflow-tooltip></el-table-column>
        <el-table-column prop="price" label="价格" show-overflow-tooltip></el-table-column>
        <el-table-column prop="visitCount" label="销售量" show-overflow-tooltip></el-table-column>
        <el-table-column label="详情" width="100px">
          <template v-slot="scope">
            <el-button type="primary" size="mini" @click.stop="addsee(scope.row)">查看</el-button>
          </template>
        </el-table-column>

      </el-table>

      <div class="pagination" style="margin-left: 80px">
        <el-pagination
            background
            @current-change="salehandleCurrentChange"
            :current-page="salepageNum"
            :page-sizes="[5, 10, 20]"
            :page-size="salepageSize"
            layout="total, prev, pager, next"
            :total="saletotal">
        </el-pagination>
      </div>

            </div>

        </div>
      </el-card>
      </span>
    </div>




    <div style="margin-left: 810px;margin-top: -342px">
      <span style="margin-left: 500px">
      <el-card class="box-card" style="height: 320px">
        <div slot="header" class="clearfix" align="center" style="height: 5px">
          <span>待补货商品</span>
        </div>
        <div class="text item" style="height: 800px" align="center">



          <div class="table">
      <el-table align="center" :data="addData" strip>

        <el-table-column prop="level" label="排名" width="70" align="center"></el-table-column>
        <el-table-column prop="name" label="商品" show-overflow-tooltip></el-table-column>
        <el-table-column prop="price" label="价格" show-overflow-tooltip></el-table-column>
        <el-table-column prop="sum" label="库存" show-overflow-tooltip></el-table-column>
        <el-table-column label="详情" width="100px">
          <template v-slot="scope">
            <el-button type="primary" size="mini" @click.stop="addsee(scope.row)">查看</el-button>
          </template>
        </el-table-column>

      </el-table>

      <div class="pagination" style="margin-left: 80px">
        <el-pagination
            background
            @current-change="addhandleCurrentChange"
            :current-page="addpageNum"
            :page-sizes="[5, 10, 20]"
            :page-size="addpageSize"
            layout="total, prev, pager, next"
            :total="addtotal">
        </el-pagination>
      </div>
    </div>






        </div>
      </el-card>
        </span>
    </div>


  </div>
</template>

<script>
import Qs from "qs";
import * as echarts from "echarts";
export default {
  name: "head",
  data() {
    return {
      msg: {},
      addData: [],
      saleData: [],
      addpageNum: 1,
      addpageSize: 3,
      addtotal: 0,
      salepageNum: 1,
      salepageSize: 3,
      saletotal: 0
    }
  },
  created() {
    this.getmsg()
    this.addload()
    this.saleload()
  },
  mounted() {
// 柱状折线图
    var option = {
      title: {
        text: '热点商品Top5',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      xAxis: {
        type: 'category',
        data: []
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          name: "访问量",
          data: [],
          type: 'bar'
        },
        {
          name: "库存",
          data: [],
          type: 'bar'
        }
      ]
    };
    var chartDom = document.getElementById('bar');
    var myChart = echarts.init(chartDom);
    this.$axios.get("/api/product/top5").then(res => {
      // 填空
      const products=res.data.data
      products.forEach(pro =>{
        option.xAxis.data.push(pro.name);
        option.series[0].data.push(pro.visitCount)
        option.series[1].data.push(pro.sum)
      })
      console.log(option.series[1].data)
      // 在数据准备完毕之后再set
      myChart.setOption(option);
    })

//饼图
    var pieOption = {
      title: {
        text: '商品访问量占比',
        left: 'right'
      },
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: {
        name: 'MoreMall',
        type: 'pie',
        radius: '35%',
        center: ['75%', '50%'],
        label: {            //饼图图形上的文本标签
          normal: {
            show: true,
            position: 'inner', //标签的位置
            textStyle: {
              fontWeight: 300,
              fontSize: 14,    //文字的字体大小
              color: "#fff"
            },
            formatter: '{d}%'
          }
        },
        data: [],  // 填空
      }
    };
    var pieDom = document.getElementById('pie');
    var pieChart = echarts.init(pieDom);
    this.$axios.get("/api/product/visit").then(res => {
      console.log(res.data.data)
      pieOption.series.data = [
        {name: "100以下", value: res.data.data[0]},
        {name: "100~1000", value: res.data.data[1]},
        {name: "1000~5000", value: res.data.data[2]},
        {name: "5000以上", value: res.data.data[3]},
      ]
      pieChart.setOption(pieOption)
    })
  },
  methods: {
    addsee(row){
      this.$router.push({
        path: "/admin/ProductDetail",
        query: {
          "row":row,
          "name":this.$route.name
        }
      })
    },
    saleload() {
      let salesend = {
        pageSize: this.salepageSize,
        pageNum: this.salepageNum
      }
      this.$axios.post("/api/product/saleload", Qs.stringify(salesend)).then(res => res.data).then(res => {
        if (res.code == 200) {
          this.saleData = res.data?.list
          for (let i = 0; i < this.saleData.length; i++) {
            this.saleData[i].level=i+this.salepageSize*(this.salepageNum-1)+1
          }
          this.saletotal = res.data?.total
          console.log(res.data)

        }

      })
    },
    addload() {
      let addsend = {
        pageSize: this.addpageSize,
        pageNum: this.addpageNum
      }
      this.$axios.post("/api/product/addload", Qs.stringify(addsend)).then(res => res.data).then(res => {
        if (res.code == 200) {
          this.addData = res.data?.list
          for (let i = 0; i < this.addData.length; i++) {
            this.addData[i].level=i+this.addpageSize*(this.addpageNum-1)+1
          }
          this.addtotal = res.data?.total
          console.log(res.data)

        }

      })
    },
    getmsg() {
      this.$axios.get("/api/product/getmsg").then(res => res.data).then(res => {
        if (res.code == 200) {
          this.msg = res.data
        }
      })
    },
    addhandleCurrentChange(pageNum) {
      console.log(pageNum)
      this.addpageNum = pageNum
      this.addload()
    },
    salehandleCurrentChange(pageNum) {
      console.log(pageNum)
      this.salepageNum = pageNum
      this.saleload()
    },
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
  width: 600px;
  height: 600px;
}

.box-card2 {
  width: 600px;
  height: 600px;
}
</style>
