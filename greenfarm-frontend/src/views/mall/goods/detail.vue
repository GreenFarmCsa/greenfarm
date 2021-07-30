<template>
  <div class="detail">
    <!--image-->
    <div class="title">
      <van-swipe :autoplay="3000" indicator-color="white">
        <van-swipe-item v-for="(image, index) in imageList" :key="index">
          <img :src="imgurl((image) ? image: '')" alt>
        </van-swipe-item>
      </van-swipe>
      <div class="card">
        <div class="header">
          <div class="name">
            {{productItem.productName}}*{{productItem.weight}}KG
            <span class="like">
              <svg-icon iconClass="like"/>
              {{productItem.likeNumber}}
            </span>
          </div>
          <span class="remain">
            Remaining
            <span>{{productItem.number}}</span>
          </span>
        </div>

        <div class="foot">
          <div class="price">
            <span class="dolar">$</span>
            {{parseFloat(productItem.price).toFixed(2)}}
            <span class="sales">{{productItem.saleNumber}} Sales</span>
          </div>
          <div>
            <van-stepper v-model="selectNum" theme="round" button-size="20" disable-input/>
          </div>
        </div>
      </div>
    </div>
    <!--identifications-->
    <div class="identifications">
      <div class="identifications-wrapper">
        <div class="identifications-item" v-for="(item,index) in identificationsList" :key="index">
          <img class="pic" :src="item.url">
          <div class="picInfo">{{item.info}}</div>
        </div>
      </div>
    </div>
    <!--info-->
    <div class="card info">
      <span class="block-title">Produce Detail</span>
      <van-cell-group :border="false">
        <van-cell>
          <template #title>
            <span class="custom-title">Category</span>
          </template>
          <template>
            <span class="custom-value">{{productItem.category}}</span>
          </template>
        </van-cell>
        <van-cell>
          <template #title>
            <span class="custom-title">Green Donation</span>
          </template>
          <template>
            <span class="custom-value">${{parseFloat(productItem.donateAmount).toFixed(2)}}</span>
          </template>
        </van-cell>
        <van-cell>
          <template #title>
            <span class="custom-title">Weight</span>
          </template>
          <template>
            <span class="custom-value">{{productItem.weight}}KG</span>
          </template>
        </van-cell>
        <van-cell>
          <template #title>
            <span class="custom-title">Farm</span>
          </template>
          <template>
            <span
              class="custom-value"
              style="border-bottom: 0.02rem solid #00B459;padding:0.02rem;"
              @click="toFarm"
            >{{farmName}}</span>
          </template>
        </van-cell>
      </van-cell-group>
    </div>
    <!--activity-->
    <div class="card eval">
      <span class="block-title">Introduction</span>
      <div class="introduction">
        <!-- {{productItem.introduction}} -->
        <span>{{introduction}}</span>
        <span v-if="moreShow" class="more" @click="clickMore">MORE...</span>
        <span v-if="!moreShow">{{introductionExtra}}</span>
      </div>
    </div>
    <!--activity-->
    <div class="card eval">
      <span class="block-title2">
        Planting Activities
        <span class="go">
          <svg-icon
            @click="goTaskList"
            iconClass="PLANTING ACTIVITIES"
            style="font-size: 0.56rem;"
          />
        </span>
      </span>
    </div>
    <!-- IBM BLOCK CHAIN -->
    <div class="card eval">
      <span class="block-title2">
        IBM Blockchain Info
        <span class="go">
          <svg-icon @click="goIBM" iconClass="IBM BLOCK CHAIN INFO" style="font-size: 0.56rem;"/>
        </span>
      </span>
    </div>
    <!--eval-->
    <div class="card eval">
      <span class="block-title">User Reviews</span>
      <UserEval v-for="(item,index) in commentList" :key="index" :userInfo="item"></UserEval>
    </div>
    <van-divider class="footer"/>
    <!--Tabbar-->
    <Tabbar :goodsCount="goodsNumInCart" @add="addIntoCart()" @buy="buyNow()"></Tabbar>
    <!-- block-->
    <van-popup v-model="block" class="popup-block" closeable>
      <div class="popup-title">IBM Block chain info</div>
      <div class="popup-body">
        <div class="popup-body-item" v-for="(item,index) in blockList" :key="index">
          <div class="line">
            <div class="key">Plant Step Name</div>
            <div class="value">{{item.plantStepName}}</div>
          </div>
          <div class="line">
            <div class="key">Cream Time</div>
            <div class="value">{{item.createTime}}</div>
          </div>
          <div class="line">
            <div class="key">Video Hash</div>
            <div class="value">{{item.videoHash}}</div>
          </div>
          <div class="line">
            <div class="key">Image Hash</div>
            <div class="value">{{item.imageHash}}</div>
          </div>
        </div>
      </div>
    </van-popup>
  </div>
</template>
<script>
import Tabbar from "@/components/mall/GoodsTabbar";
import UserEval from "@/components/mall/UserEval";
import { mapState } from "vuex";
import { Toast } from "vant";
import {
  queryProducts,
  queryProductComment,
  queryProductsById,
  queryCart,
  addCart,
  updateCart,
  queryPlantTask
} from "@/api/mall";
import { queryBlockById } from "@/api/block";
import { queryFarmById } from "@/api/farm";
export default {
  name: "GoodsDetail",
  components: { Tabbar, UserEval },
  data() {
    return {
      farmName: "",
      identificationsList: [],
      block: false,
      blockList: [],
      taskInfo: {},
      goodsNumInCart: 0,
      productId: "",
      productItem: {},
      commentList: [],
      selectNum: "",
      oldCount: 0,
      isInCart: false,
      imageList: [],
      introduction: "",
      introductionExtra: "",
      moreShow: false
    };
  },
  mounted() {
    this.$store.dispatch("cartGoods/clearGoodsList");
    this.productId = this.$route.params.goodsId;
    this.getProductComment();
    this.getProductDetails();
    this.loadCart();
    this.queryPlantActivity();
  },
  computed: {
    ...mapState({
      username: state => state.user.username || "jack"
    })
  },
  methods: {
    toFarm() {
      this.$router.push({
        path: "/farm/detail/" + this.productItem.farmId
      });
    },
    async goIBM() {
      const res = await queryBlockById({ productId: this.productId });
      this.blockList = res;
      if( res.length == 0 ){
        this.$toast({message: 'No blockchain information of this product was found'});
        this.block = false;
      } else {
        this.block = true;
      }
      
    },
    imgurl(url, defaultImg) {
      return process.env.VUE_APP_BASE_API + "/file/download?url=" + url;
    },
    async queryPlantActivity() {
      try {
        let res = await queryPlantTask({ productId: this.productId });
        if (res.data !== null) {
          this.taskInfo = res.data;
        }
      } catch (err) {
        console.log(err);
      } finally {
      }
    },
    buyNow() {
      if (this.selectNum > this.productItem.number) {
        this.$toast({message: 'Out of stock!', position: 'bottom'});
        return;
      }
      let obj = {
        productId: this.productId,
        quickNum: this.selectNum,
        price: this.productItem.price,
        imageUrl: this.productItem.imageUrl[0],
        productName: this.productItem.productName,
        //limit
        carbonCredit: this.productItem.carbonCredit * this.selectNum,
        //donate
        donate: this.productItem.donateAmount * this.selectNum
      };
      this.$store.dispatch("cartGoods/setQuickGoods", obj);
      this.$router.push({ name: "orderEdit" });
    },
    //adding to cart
    async addIntoCart() {
      try {
        if (this.selectNum + this.oldCount > this.productItem.number) {
          this.$toast({message: 'Out of stock!', position: 'bottom'});
          return;
        }
        let addObj = {
          username: this.username,
          productId: this.productId,
          count: this.selectNum + this.oldCount
        };
        let res = {};
        if (this.isInCart) {
          res = await updateCart(addObj);
        } else {
          res = await addCart(addObj);
        }
        this.loadCart();
        this.$toast({ message: "Add Successfully" });
      } catch (err) {
        console.log(err);
      }
    },
    //loading cart
    loadCart() {
      let resultList = [];
      this.oldCount = 0;
      queryCart({ username: this.username })
        .then(res => {
          resultList = res.data;
          resultList.forEach(item => {
            if (item.productId == this.productId) {
              this.isInCart = true;
              this.oldCount = item.number;
            }
          });
          this.goodsNumInCart = resultList.length;
        })
        .catch(e => {
          this.goodsNumInCart = 0;
          console.log(e);
        });
    },
    //goTaskList
    goTaskList() {
      if(this.taskInfo.taskId==undefined || this.taskInfo.taskId==null || this.taskInfo.taskId==""){
        this.$toast({message: 'No task of this product was found'});
      } else {
        this.$router.push({
          path: "/me/plant/task-list/",
          query: {
            taskId: this.taskInfo.taskId,
            seedName: this.taskInfo.seedName,
            farmId: this.taskInfo.farmId,
            landId: this.taskInfo.landId,
            seedId: this.taskInfo.seedId
          }
        });
      }
    },
    //details
    getProductDetails() {
      queryProductsById({ productId: this.productId })
        .then(res => {
            this.productItem = res.data[0];
            this.imageList = this.productItem.imageUrl;
            this.getIdentificationsList(res.data[0].identifications);
            this.getIntroduction();
            this.queryFarmById(res.data[0].farmId);
        })
        .catch(e => {
          this.productItem = {};
          console.log(e);
        });
    },
    getIdentificationsList(val) {
      let tmp = val.split(",");
      let tmpList = [];
      let url = "";
      let info = "";
      for (let i = 0, len = tmp.length; i < len; i++) {
        switch (tmp[i]) {
          case "1":
            url = require("@/assets/images/identifications/1-carbon-conscious.png");
            info = "Carbon Conscious";
            break;
          case "2":
            url = require("@/assets/images/identifications/2-sustainably-sourced.png");
            info = "Sustainably Sourced";
            break;
          case "3":
            url = require("@/assets/images/identifications/3-trusted-planting.png");
            info = "Trusted Planting";
            break;
          case "4":
            url = require("@/assets/images/identifications/4-vegan.png");
            info = "Vegan";
            break;
          default:
            url = "";
            info = "";
            break;
        }
        tmpList.push({
          name: tmp[i],
          url: url,
          info: info
        });
      }
      this.identificationsList = tmpList;
    },
    async queryFarmById(farmId) {
      try {
        let res = await queryFarmById({ id: farmId });
        if (res.data !== null) {
          this.farmName = res.data.farmName;
        }
      } catch (err) {
        console.log(err);
      } finally {
      }
    },
    //introduction
    getIntroduction() {
      if (this.productItem.introduction) {
        if (this.productItem.introduction.length <= 200) {
          this.introduction = this.productItem.introduction;
          this.introductionExtra = "";
          this.moreShow = false;
        } else {
          this.introduction = this.productItem.introduction.substr(0, 200);
          this.introductionExtra = this.productItem.introduction.slice(200);
          this.moreShow = true;
        }
      }
    },
    clickMore() {
      this.moreShow = false;
    },

    //comments
    getProductComment() {
      queryProductComment({ productId: this.productId })
        .then(res => {
          this.commentList = res.data;
        })
        .catch(e => {
          this.commentList = [];
          console.log(e);
        });
    }
  }
};
</script>
<style lang="less" scoped>
/deep/ .van-stepper__plus {
  background-color: #00b459;
  border-color: #00b459;
  color: #fff;
}
/deep/ .van-stepper__minus {
  background-color: #00b459;
  border-color: #00b459;
  color: #fff;
}

.footer {
  margin: 0.4rem 0;
}
.custom-title {
  color: #666666;
  font-family: Montserrat-Regular;
}
.custom-value {
  color: #00b459;
  font-family: Montserrat-Regular;
}
.detail {
  background-color: #f6f6f6;
}
.rank {
  margin: 0.2rem 0;
  .ranklist {
    color: #333333;
    font-family: Montserrat-Regular;
    margin-left: 0.1rem;
  }
}
.introduction {
  margin: 0.2rem 0;
  color: #909090;
  font-size: 0.26rem;
  font-family: Montserrat-Light;
  line-height: 0.4rem;
  .more {
    font-size: 0.26rem;
    color: #ff9f00;
    letter-spacing: 0;
    line-height: 0.4rem;
    cursor: pointer;
  }
}
.info {
  margin: 0.2rem 0;
  padding: 0.2rem 0;
  /deep/ .van-cell {
    padding: 0.16rem 0;
  }
}
.block-title {
  display: inline-block;
  padding: 0.12rem 0;
  font-family: Montserrat-Medium;
  font-size: 0.32rem;
  line-height: 0.32rem;
  color: #333333;
  text-align: left;
}
.eval {
  margin: 0.2rem 0;
}
.block-title2 {
  display: inline-block;
  font-family: Montserrat-Medium;
  font-size: 0.32rem;
  line-height: 0.56rem;
  color: #333333;
  text-align: left;
  display: flex;
  justify-content: space-between;
  .go {
    height: 0.56rem;
    margin-right: 0.04rem;
  }
}
img {
  width: 100%;
  height: 4.2rem;
  object-fit: cover;
}
.card {
  padding: 0.3rem;
  background-color: #fff;
  .header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin: 0 0 0.3rem 0;
    .name {
      font-size: 0.32rem;

      font-family: Montserrat-Regular;
      font-weight: bold;
      .like {
        color: #909090;
        font-size: 0.24rem;
        font-family: Montserrat-Regular;
        border-radius: 90px;
        background-color: rgba(247, 181, 0, 0.2);
        display: inline-block;
        width: 1rem;
        text-align: center;
        line-height: 0.4rem;
      }
    }
    .remain {
      color: #909090;
      font-size: 0.28rem;
      font-family: Montserrat-Light;
      span {
        color: rgba(247, 181, 0);
      }
    }
  }

  .foot {
    display: flex;
    justify-content: space-between;
    align-items: center;
    .price {
      color: #08c25e;
      font-size: 0.26rem;
      font-weight: bold;
      font-family: Montserrat-Regular;
      .dolar {
        font-family: Montserrat-Regular;
        font-size: 0.32rem;
      }
      .sales {
        margin-left: 0.1rem;
        color: #909090;
        font-size: 0.24rem;
        font-family: Montserrat-Light;
      }
    }
  }
}
.popup-block {
  width: 6.9rem;
  border-radius: 0.2rem;
  min-height: 6.5rem;
  .popup-title {
    text-align: center;
    height: 1.16rem;
    line-height: 1.16rem;
    font-size: 0.32rem;
    color: #333;
    font-family: Montserrat-Medium;
    font-weight: 550;
    border-bottom: 1px solid #eeeff1;
  }
  .popup-body {
    max-height: 13rem;
    width: 6.8rem;
    overflow-y: scroll;
    margin: auto;
    background: #f6f6f6;
    .popup-body-item {
      &:not(:last-child) {
        margin-bottom: 0.26rem;
      }
      padding: 0.15rem 0;
      background: #ffffff;
      .line {
        padding: 0 0.3rem;
        display: flex;
        .key {
          width: 45%;
          padding: 0.21rem 0;
          font-family: Montserrat-Regular;
          font-size: 0.28rem;
          color: #333333;
          letter-spacing: 0;
          line-height: 0.36rem;
        }
        .value {
          text-align: right;
          padding: 0.21rem 0;
          width: 57%;
          font-family: Montserrat-Regular;
          font-size: 0.28rem;
          color: #666666;
          letter-spacing: 0;
          line-height: 0.32rem;
          word-break: break-all;
        }
      }
    }
  }
}
.identifications {
  padding: 0.3rem 0.24rem;
  background-color: #fff;
  margin: 0.2rem 0;
  .identifications-wrapper {
    &::-webkit-scrollbar {
      height: 0;
      display: none;
    }
    overflow-x: scroll;
    scrollbar-width: none;
    -ms-overflow-style: none;
    display: flex;
    .identifications-item {
      width: 1.3rem;
      &:not(:last-child) {
        margin-right: 0.13rem;
      }
      .pic {
        margin: 0.1rem 0.15rem;
        width: 1rem;
        height: 1rem;
      }
      .picInfo {
        font-family: Montserrat-Light;
        font-size: 0.22rem;
        color: #666666;
        letter-spacing: 0;
        text-align: center;
        line-height: 0.26rem;
      }
    }
  }
}
</style>
