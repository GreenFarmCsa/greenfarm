<template>
  <div class="detail">
    <span>Produce Detail</span>
    <div class="detail-img">
      <div class="tag-list">
        <div v-for="(image, index) in imageList" :key="index">
          <img :src="imgurl((image) ? image: '')" alt>
        </div>
      </div>
    </div>
    <div class="detail-cell">
      <van-cell-group :border="false">
        <van-cell>
          <template #title>
            <span class="custom-title">Price</span>
          </template>
          <template>
            <span class="custom-value">${{parseFloat(amount).toFixed(2)}}</span>
          </template>
        </van-cell>
        <van-cell>
          <template #title>
            <div class="cell">
              <van-checkbox @click="chooseCredit" v-model="checked" checked-color="#08C25E"/>
              <span class="custom-title">Carbon credit</span>
            </div>
          </template>
          <template>
            <span class="custom-value">{{credit}} Credit</span>
          </template>
        </van-cell>
      </van-cell-group>
    </div>
    <div v-if="this.checked" class="detail-total">
      Total: ${{parseFloat(amount).toFixed(2)}} - ${{parseFloat(amount-unpaid).toFixed(2)}} 
    </div>
    <div v-else class="detail-total">
      Total: ${{parseFloat(unpaid).toFixed(2)}} 
    </div>
  </div>
</template>
<script>
import { mapState } from "vuex";
import { queryUserInfo } from "@/api/user";
import { queryCart } from "@/api/mall";
export default {
  name: "CheckoutDetail",
  data() {
    return {
      checked: false,
      credit: 0,
      amount: 0,
      creditLimit: 0,
      imageList: [],
      donate: 0,
      unpaid: 0
    };
  },
  methods: {
    imgurl(url, defaultImg) {
      return process.env.VUE_APP_BASE_API + "/file/download?url=" + url;
    },
    //choose
    chooseCredit() {
      if (this.checked) {
        let tmpunpaid = this.amount  - (this.credit / 100)
        this.unpaid = tmpunpaid
        if (this.unpaid < 0) {
          this.unpaid = 0;
        }
        this.$store.dispatch("cartGoods/setCarbon", this.credit);
        this.$store.dispatch("cartGoods/setTotalPrice", this.unpaid);
      } else {
        this.unpaid = this.amount
        this.$store.dispatch("cartGoods/clearCarbon");
        this.$store.dispatch("cartGoods/setTotalPrice", this.unpaid);
      }
    },
    //quickGoods
    quickGoods(quickObj) {
      this.amount += quickObj.price * quickObj.quickNum;
      this.unpaid = this.amount
      //limit
      this.creditLimit = quickObj.carbonCredit;
      this.imageList.push(quickObj.imageUrl);
      //donate
      this.donate = quickObj.donate
      this.$store.dispatch("cartGoods/setTotalPrice", this.amount);
      this.$store.dispatch("cartGoods/setTotalDonate", this.donate);
      this.queryCarbon();
    },
    //queryGoods
    queryGoodsList() {
      let checkList = [];
      queryCart({ username: this.username })
        .then(res => {
          checkList = res.data;
          checkList.forEach(item => {
            if (this.productIdList.indexOf(item.productId) > -1) {
              //donate
              this.donate += item.number * item.donateAmount;
              //check
              this.amount += item.price * item.number;
              // limit
              this.creditLimit += item.carbonCredit * item.number;
              //image
              this.imageList.push(item.imageUrl);
            }
          });
          this.amount = this.amount;
          this.unpaid = this.amount;
          this.donate = this.donate;
          this.$store.dispatch("cartGoods/setTotalDonate", this.donate);
          this.$store.dispatch("cartGoods/setTotalPrice", this.amount);
          this.queryCarbon();
        })
        .catch(e => {
          this.queryCarbon();
          console.log(e);
        });
    },
    //carbon
    queryCarbon() {
      queryUserInfo({ username: this.username })
        .then(res => {
          let userCarbonCredit = res.data.carbonCredit;
          this.credit = Math.min(userCarbonCredit, this.creditLimit);
        })
        .catch(e => {
          console.log(e);
        });
    }
  },
  mounted() {
    this.imageList = [];
    // console.log("list",this.productIdList)
    if (this.productIdList.length > 0) {
      this.queryGoodsList();
    } else {
      this.quickGoods(this.quickObj);
    }
    // this.queryCarbon();
  },
  computed: {
    ...mapState({
      username: state => state.user.username || "jack",
      productIdList: state => state.cartGoods.goodsList,
      quickObj: state => state.cartGoods.quickGoods
    })
  }
};
</script>
<style lang="less" scoped>
/deep/ .van-checkbox {
  width: 0.3rem;
  height: 0.3rem;
  margin: 0.05rem 0 0 0.05rem;
}
.tag-list {
  &::-webkit-scrollbar {
    height: 0;
    display: none;
  }
  overflow-y: hidden;
  overflow-x: scroll;
  scrollbar-width: none;
  -ms-overflow-style: none;
  display: flex;
  margin: 0.3rem 0;
  width: 6.9rem;
  img {
    width: 1.2rem;
    height: 1.2rem;
    border-radius: 10%;
    margin-left: 0.4rem;
    object-fit: cover;
  }
}
.detail {
  width: 100%;
  margin-top: 0.2rem;
  background: white;
  padding-top: 0.2rem;
  span {
    margin-left: 0.4rem;
    font-size: 0.3rem;
    font-weight: 550;
    font-family: Montserrat-Medium;
    color: #333333;
  }
  .cell {
    display: flex;
  }
  .custom-title {
    color: #666666;
    font-family: Montserrat-Light;
    font-size: 0.28rem;
    margin-left: 0.1rem;
    display: inline-block;
  }
  .custom-value {
    color: #14ac69;
    font-family: Montserrat-Regular;
    display: block;
    font-size: 0.28rem;
  }
  .detail-total {
    border-top: 0.02rem solid #EEEFF1;
    padding: 0.4rem 0.3rem;
    font-family: Montserrat-Regular;
    font-size: 0.28rem;
    color: #333333;
    letter-spacing: 0;
    text-align: right;
    line-height: 0.28rem;
  }
}
</style>
