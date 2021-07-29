<template>
  <div class="block">
    <div class="card goods-card" v-for="(item,index) in recommendGoodsList" :key="index">
      <div class="img">
        <img :src="item.url" alt>
      </div>
      <div class="name">{{item.productName}}*{{item.productParam}}</div>
      <div class="foot">
        <div class="price">${{item.price}}</div>
        <div class="like">
          <svg-icon iconClass="like" style="font-size: 0.2rem;"/>
          {{item.like}}
        </div>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "GoodsRecommend",
  data() {
    return {
      recommendGoodsList: []
    };
  },
  mounted() {
    this.recommendGoodsList = [];
  }
};
</script>
<style lang="scss" scoped>
.card {
  padding: 0.3rem;
  box-shadow: 0 4px 16px 0 rgba(104, 104, 104, 0.19);
  border-radius: 0.16rem;
}
.goods-card {
  margin: 0.1rem 0;
  .img {
    margin: -0.3rem -0.3rem 0.3rem;
    img {
      width: 100%;
      height: 3rem;
      border-radius: 0.15rem 0.15rem 0 0;
    }
  }
  .name {
    font-size: 0.3rem;
    margin: 0.1rem 0;
    font-family: Montserrat-Regular;
    font-weight: bold;
  }
  .foot {
    display: flex;
    justify-content: space-between;
    align-items: center;
    .price {
      color: #08c25e;
      font-size: 0.2rem;
      font-weight: bold;
      font-family: Montserrat-Regular;
    }
    .like {
      color: #909090;
      font-size: 0.2rem;
      font-family: Montserrat-Regular;
    }
  }
}
</style>

