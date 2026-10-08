<template>
  <div class="product-list">
    <el-card :key="product.id" class="product-card">
      <div class="product-info">
        <h3>商品名称：{{ product.name }}</h3>
        <p>价格: {{ product.price }}</p>
        <p>库存: {{ product.sum }}</p>
        <p>入库时间:
          <template>
            {{ product.addDate | formatTime }}
          </template>
        </p>
        <p>状态:
          <el-tag :type="product.status == 1 ? 'success' : 'warning'">{{ product.status == 1 ? "上架" : "下架" }}
          </el-tag>
        </p>
        <p>{{ product.info }}</p>
      </div>
      <div style=" margin-top: 15px">
        <el-button type="text" style="color: #5c6675;margin-left: 200px" @click="back"><i class="el-icon-back"></i>返回</el-button>
      </div>
    </el-card>
  </div>
</template>
<script>
export default {
  name: "ProductDetail",
  data() {
    return {
      product: {},
      name:""
    }
  },
  created() {
    const { row, name } = this.$route.query;
    this.product =row;
    this.name=name;
    console.log(this.name)
  },
  filters: {
    formatTime: function (value) {
      if (!value) return '';
      const date = new Date(value);
      return date.toLocaleString(); // 或者使用其他合适的时间格式化方法
    }
  },
  mounted() {

  },
  methods: {
    back() {
      if(this.name=="product"){
        this.$router.push({path: '/admin/product'});
      }else {
        this.$router.push({path: '/admin/head'});
      }

    }
  }
}
</script>
<style lang="scss" scoped>
.product-list {
  margin-top: 50px;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
}

.product-card {
  width: 300px;
  margin-bottom: 20px;
}

.product-info h3 {
  margin: 0 0 10px;
}

.product-info p {
  margin: 5px 0;
}
</style>
