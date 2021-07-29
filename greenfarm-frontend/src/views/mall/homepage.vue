<template>
  <div>
    <Header></Header>
    <div class="homepage">
      <!--kind-->
      <div class="card kind-card">
        <div class="tab" @click="goRouter('vegetables')">
          <div class="imageborder">
            <svg-icon iconClass="vegetable" style="font-size: 0.6rem;"/>
          </div>
          <p>Vegetable</p>
        </div>
        <div class="tab" @click="goRouter('fruit')">
          <div class="imageborder">
            <svg-icon iconClass="strawberry" style="font-size: 0.6rem;"/>
          </div>
          <p>Fruit</p>
        </div>
        <div class="tab">
          <div class="imageborder" @click="goRouter('seeds')">
            <svg-icon iconClass="seeds" style="font-size: 0.6rem;"/>
          </div>
          <p>Seed</p>
        </div>
        <div class="tab">
          <div class="imageborder" @click="goRouter('fertilizer')">
            <svg-icon iconClass="ferterlizer-1" style="font-size: 0.6rem;"/>
          </div>
          <p>Fertilizer</p>
        </div>
      </div>

      <!--hot-->
      <div class="block">
        <div class="block-title">
          <svg-icon iconClass="selling good" style="font-size: 0.44rem;"/>
          <span>Selling Produce</span>
        </div>
        <div class="hot">
          <!-- <GoodsHot  v-for="(item,index) in hotProductsList" :key="index" v-if="index<6" :item="item"></GoodsHot> -->
          <GoodsHot v-for="(item,index) in hotProductsList.slice(0,6)" :key="index" :item="item"></GoodsHot>
        </div>
      </div>

      <!--favorite-->
      <div class="block">
        <div class="block-title">
          <svg-icon iconClass="recommend goods" style="font-size: 0.44rem;"/>
          <span>Recommended Produce</span>
        </div>
        <!-- <RecommendGoods @click.native="$router.push('/mall/goods/detail/' + item.productId)"  v-for="(item,index) in recommendProductsList" :key="index" v-if="index<3" :item="item"></RecommendGoods> -->
        <RecommendGoods
          @click.native="$router.push('/mall/goods/detail/' + item.productId)"
          v-for="(item,index) in recommendProductsList.slice(0,3)"
          :key="index"
          :item="item"
        ></RecommendGoods>
      </div>
      <van-divider>End</van-divider>
    </div>
  </div>
</template>
<script>
import Header from "@/components/mall/GoodsHeader";
import GoodsHot from "@/components/mall/GoodsHot";
import RecommendGoods from "@/components/recommendGoods";
import { recommendProducts } from "@/api/dashboard";
import { queryHotProducts } from "@/api/mall";
import { mapState } from "vuex";
export default {
  name: "HomePage",
  components: { Header, GoodsHot, RecommendGoods },
  data() {
    return {
      hotProductsList: [],
      recommendProductsList: []
    };
  },
  computed: {
    ...mapState({
      username: state => state.user.username || "Jack"
    })
  },
  mounted() {
    this.getRecommendProducts();
    this.getHotProducts();
  },
  methods: {
    //router
    goRouter(val) {
      this.$router.push({ name: "goodsList", params: { id: val } });
    },
    //recommendgoods
    getRecommendProducts() {
      recommendProducts({ username: this.username })
        .then(res => {
          this.recommendProductsList = res.data;
        })
        .catch(e => {
          console.log(e);
        });
    },
    //hotgoods
    getHotProducts() {
      queryHotProducts()
        .then(res => {
          this.hotProductsList = res.data;
        })
        .catch(e => {
          console.log(e);
        });
    }
  }
};
</script>

<style lang="scss" scoped>
.homepage {
  background: #fdfdfd;
  padding: 1.14rem 0.3rem 0 0.3rem;
  .card {
    padding: 0.3rem;
    box-shadow: 0 4px 16px 0 rgba(104, 104, 104, 0.19);
    border-radius: 0.16rem;
  }
  .kind-card {
    // margin: 0 0;
    display: flex;
    flex-wrap: wrap;
    justify-content: space-between;
    .tab {
      display: flex;
      justify-content: center;
      align-items: center;
      flex-direction: column;
      width: 20%;
      .imageborder {
        width: 1rem;
        height: 0.8rem;
        border: 1px solid #dedede;
        display: table-cell;
        vertical-align: middle;
        border-radius: 0.16rem;
        text-align: center;
        padding: 0.2rem 0rem 0rem 0rem;
      }
      p {
        font-size: 0.28rem;
        margin-top: 0.24rem;
        font-family: Montserrat-Medium;
        color: #323233;
      }
    }
  }
  .block {
    .block-title {
      margin: 0.5rem 0;
      span {
        margin-left: 0.16rem;
        font-size: 0.32rem;
        font-weight: bold;
        font-family: Montserrat-Medium;
      }
    }
    .hot {
      display: flex;
      flex-wrap: wrap;
      justify-content: space-between;
    }
  }
  .footer {
    margin: 1rem 0;
  }
}
</style>


