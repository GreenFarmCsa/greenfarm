<template>
  <div class="lease-farmers">
    <div class="nodata" v-if="ifListNull">
      <img :src="require('@/assets/images/add-one-placeholder.jpg')" alt>
      <div class="text">
        <p>Farms that have not been leased,</p>
        <p>Please create a farm!</p>
      </div>
    </div>
    <RecommendFarm
      v-for="(item,index) in dataList"
      :key="index"
      :item="item"
      @click.native="$router.push({name:'leaseEdit',query:{id:item.farmId}})"
    ></RecommendFarm>
    <div class="op-container">
      <div class="btn" @click="$router.push({name:'leaseAdd'})">Add a Farm</div>
    </div>
  </div>
</template>

<script>
import { queryFarmByUserName } from "@/api/farm";
import RecommendFarm from "@/components/recommendFarm";
export default {
  components: { RecommendFarm },
  data() {
    return {
      dataList: [],
      ifListNull: false
    };
  },
  mounted() {
    this.getData();
  },
  methods: {
    getData() {
      queryFarmByUserName({ username: this.$store.state.user.username }).then(res => {
        this.dataList = res.data;
        if(res.data.length == 0){
          this.ifListNull = true
        }else{
          this.ifListNull = false
        }
      }).catch(e => {
        console.log(e);
      })
    }
  }
};
</script>

<style lang="scss" scoped>
.lease-farmers {
  border-top: 0.26rem #f6f6f6 solid;
  background: #fff;
  padding: 0 0.3rem 1.1rem;
  .nodata {
    padding: 1rem 0.8rem;
    text-align: center;
    img {
      width: 100%;
    }
    .text {
      margin: 0.44rem -0.4rem;
      p {
        font-family: Montserrat-Regular;
        font-size: 0.32rem;
        color: #333333;
        letter-spacing: 0;
        text-align: center;
        line-height: 0.44rem;
      }
    }
  }
  .op-container {
    position: fixed;
    bottom: 1.45rem;
    left: 0;
    right: 0;
    padding: 0.14rem 0.6rem;
    background: #ffffff;
    box-shadow: 0 0 0.2rem 0 rgba(0, 0, 0, 0.08);
    border-top: 1 #f2efef solid;
    border-bottom: 1 #f2efef solid;
    .btn {
      height: 0.8rem;
      line-height: 0.8rem;
      background: #00b459;
      border-radius: 0.4rem;
      font-family: Montserrat-Regular;
      font-size: 0.28rem;
      color: #fcfcfc;
      letter-spacing: 0;
      text-align: center;
    }
  }
}
</style>
