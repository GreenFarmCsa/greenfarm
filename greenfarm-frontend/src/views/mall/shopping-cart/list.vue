<template>
  <div class="shopping">
    <div class="cartlist">
      <div class="emp" v-if="goodsList.length==0">
        <svg-icon iconClass="shopping-cart" style="font-size: 0.44rem;"/>
        <span>Empty</span>
      </div>
      <div class="list" v-for="item in goodsList" :key="item.cartId">
        <van-swipe-cell>
          <div class="cart">
            <van-checkbox checked-color="#08C25E" @click="checkSingle(item)" v-model="item.select"/>
            <CarList @refresh="loadCart()" @calculate="calculateTotal()" :goodsInfo="item"></CarList>
          </div>
          <template #right>
            <van-button @click="removeItem(item)" square text="Remove" type="danger" class="delete-button"/>
          </template>
        </van-swipe-cell>
      </div>
    </div>
    <!--favorite-->
    <div class="block">
      <div class="block-title">
        <svg-icon iconClass="recommend goods" style="font-size: 0.44rem;"/>
        <span>Recommended Produce</span>
      </div>
      <RecommendGoods
        @click.native="$router.push('/mall/goods/detail/' + item.productId)"
        v-for="(item,index) in recommendProductsList"
        :key="index"
        :item="item"
      ></RecommendGoods>
    </div>
    <van-divider class="footer"/>
    <div class="submit-box">
      <van-checkbox checked-color="#08C25E" @click="setAll" v-model="checkAll">
        <span class="text">ALL</span>
      </van-checkbox>
      <div class="price">
        <span class="label">Total:</span>
        <span class="num">${{parseFloat(totalPrice).toFixed(2)}}</span>
      </div>
      <div class="btn" @click="onSubmit">Account</div>
    </div>
  </div>
</template>
<script>
import CarList from "@/components/mall/CartList";
import RecommendGoods from "@/components/recommendGoods";
import { recommendProducts } from "@/api/dashboard";
import { mapState } from "vuex";
import {
  queryCart,
  addCart,
  removeCart,
  updateCart,
  queryProductsById
} from "@/api/mall";
export default {
  name: "ShoppingCart",
  components: { CarList, RecommendGoods },
  data() {
    return {
      goodsList: [],
      checkAll: false,
      recommendProductsList: [],
      totalPrice: 0,
      currentPrice: 3,
      currentNumber: 0
    };
  },
  computed: {
    ...mapState({
      username: state => state.user.username || "jack"
    })
  },
  methods: {
    //query current
    async getCurrent(productId) {
      try {
        let tempList = [];
        let number = 0;
        let price = 0;
        let result = 0;
        const res = await queryCart({ username: this.username });
        tempList = res.data;
        tempList.forEach(item => {
          if (item.productId == productId) {
            number = item.number;
            price = item.price;
            result = price * number;
          }
        });
        return result;
      } catch (err) {
        console.log(err);
      }
    },
    //total price
    async calculateTotal() {
      this.$store.dispatch("cartGoods/clearGoodsList");
      let tmpPrice = 0;
      for (let i = 0, len = this.goodsList.length; i < len; i++) {
        if (this.goodsList[i].select) {
          let res = await this.getCurrent(this.goodsList[i].productId);
          let tmp = tmpPrice + res;
          tmpPrice = tmp;
          //use vuex productid
          this.$store.dispatch(
            "cartGoods/setGoodsList",
            this.goodsList[i].productId
          );
        } else {
          tmpPrice = tmpPrice;
        }
      }
      this.totalPrice = tmpPrice;
    },
    stepperActive(item) {
      item.select = true;
      this.checkSingle(item);
    },
    //commit
    onSubmit() {
      if (this.totalPrice > 0) {
        this.$router.push({ name: "orderEdit" });
      } else {
        this.$toast({ message: "unselected!" });
      }
    },
    //checksingle
    checkSingle(item) {
      let selectAll = true;
      this.goodsList.forEach(item => {
        if (!item.select) {
          selectAll = false;
        }
      });
      this.checkAll = selectAll;
      this.calculateTotal();
    },
    //appendobj
    appendObject() {
      this.goodsList.forEach(item => {
        this.$set(item, "select", false);
      });
    },
    //loading cart
    loadCart() {
      this.checkAll = false;
      this.totalPrice = 0;
      queryCart({ username: this.username })
        .then(res => {
          this.goodsList = res.data;
          this.appendObject();
        })
        .catch(e => {
          console.log(e);
        });
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
    //selectALL
    setAll() {
      if (this.checkAll) {
        this.goodsList.forEach(item => {
          item.select = true;
        });
      } else {
        this.goodsList.forEach(item => {
          item.select = false;
        });
      }
      this.calculateTotal();
    },
    //remove
    removeItem(item) {
      let params = {
        username: item.username,
        productId: item.productId
      };
      removeCart(params)
        .then(res => {
          this.loadCart()
        })
        .catch(e => {
          this.loadCart()
          console.log(e);
        });
    }
  },
  mounted() {
    this.$store.dispatch("cartGoods/clearGoodsList");
    this.getRecommendProducts();
    this.loadCart();
  }
};
</script>
<style lang="less" scoped>
.emp {
  text-align: center;
  padding-top: 0.3rem;
  span {
    font-family: Montserrat-Regular;
    font-size: 0.26rem;
    margin-left: 0.1rem;
  }
}
.submit-box {
  box-shadow: 0 0 0.2rem 0 rgba(0, 0, 0, 0.08);
  background-color: #fff;
  height: 1.15rem;
  padding-left: 0.3rem;
  padding-right: 0.3rem;
  position: fixed;
  left: 0;
  right: 0;
  bottom: 1.46rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
  /deep/ .van-checkbox__label .text {
    font-family: Montserrat-Regular;
    font-size: 0.32rem;
    color: #333333;
    letter-spacing: 0;
    line-height: 0.32rem;
  }
  .price {
    .label {
      font-family: Montserrat-Light;
      font-size: 0.28rem;
      color: #666666;
      letter-spacing: 0;
      line-height: 0.28rem;
    }
    .num {
      margin-left: 0.1rem;
      font-family: Montserrat-Regular;
      font-size: 0.32rem;
      color: #08c25e;
      letter-spacing: 0;
      line-height: 0.32rem;
    }
  }
  .btn {
    background: #00b459;
    border-radius: 0.44rem;
    height: 0.7rem;
    width: 2.28rem;
    text-align: center;
    font-family: Montserrat-Regular;
    font-size: 0.28rem;
    color: #fcfcfc;
    letter-spacing: 0;
    line-height: 0.7rem;
  }
}
.text {
  font-family: Montserrat-Regular;
  font-size: 0.2rem;
  color: #050505;
}
.footer {
  margin: 0.3rem 0;
}
.shopping {
  background-color: #f6f6f6;
  .cart {
    width: 100%;
    display: flex;
    padding: 0.3rem;
  }
}
.list {
  background-color: #fff;
  display: flex;
  margin-top: 0.05rem;
  /deep/ .van-checkbox {
    margin-top: 0.4rem;
    width: 0.5rem;
    height: 0.5rem;
  }
  /deep/ .van-swipe-cell {
    width:100%;
  }
}
.block {
  margin-top: 0.3rem;
  background-color: #fff;
  padding: 0.3rem 0.3rem;
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
.delete-button {
  height: 100%;
}
</style>