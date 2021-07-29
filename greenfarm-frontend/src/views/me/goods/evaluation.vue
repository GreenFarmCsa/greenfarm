<template>
  <div>
    <van-form validate-first @failed="onFailed" @submit="submit">
      <div class="product">
        <div class="product-item" v-for="(item,$index) in productList" :key="$index">
          <div class="top">
            <div class="img">
              <img :src="changeImg(item.imageUrl)">
            </div>
            <div class="detail">
              <div class="name">{{item.productName}}</div>
              <div class="description">${{parseFloat(item.price).toFixed(2)}}</div>
            </div>
          </div>
          <div class="bottom">
            <van-field
              v-model="item.commentContent"
              @change="changeVal(item,$index)"
              type="textarea"
              placeholder="Please fill in your experience with this product…"
              :rules="[{ required: true, message: 'please input' }]"
            />
          </div>
        </div>
        <div class="evaluation-button"  native-type="submit">
          <div class="button" @click="submit" native-type="submit">Save</div>
        </div>
      </div>
    </van-form>
  </div>
</template>

<script>
import { addComment } from "@/api/order";
import { queryProductComment } from "@/api/mall";
export default {
  data() {
    return {
      productList: [],
      disabledButton: true
    };
  },
  mounted() {
    this.getProductComment();
  },
  methods: {
    onFailed() {},
    changeVal(item, index) {},
    async getProductComment() {
      try {
        let tmp = JSON.parse(decodeURIComponent(this.$route.query.param));
        for (let i = 0, len = tmp.length; i < len; i++) {
          const res = await queryProductComment({ productId: tmp[i].productId });
          for (let j = 0, len = res.data.length; j < len; j++) {
            if (res.data[j].username == this.$store.state.user.username) {
              if ( res.data[j].commentContent != ""){
                tmp[i].commentContent = res.data[j].commentContent;
              }
              tmp[i].commentContentEdit = true;
              this.disabledButton = false;
            }
          }
        }
        this.productList = tmp;
      } catch (err) {
        console.log(err);
      }
    },
    async submit() {
      let param = [];
      try {
        for (let i = 0, len = this.productList.length; i < len; i++) {
          if(this.productList[i].commentContent == undefined || this.productList[i].commentContent == ""){
            this.$toast({ message: "Comment is not allowed null"});
            return
          }
          param[i] = {
            commentContent: this.productList[i].commentContent,
            productId: this.productList[i].productId,
            username: this.$store.state.user.username
          };
          const res = await addComment(param[i]);
        }
        this.$router.go(-1);
      } catch (err) {
        console.log(err);
      } finally {
      }
    },
    changeImg(val) {
      return process.env.VUE_APP_BASE_API + "/file/download?url=" + val;
    }
  }
};
</script>

<style lang="scss" scoped>
.product {
  padding: 0 0.3rem;
  .product-item {
    height: 6rem;
    margin: 0.3rem 0;
    background: #ffffff;
    box-shadow: 0 0.08rem 0.32rem 0 rgba(104, 104, 104, 0.19);
    border-radius: 0.16rem;
    border: 0.02rem solid #eeeff1;
    .top {
      padding: 0.3rem;
      display: flex;
      justify-content: flex-start;
      align-items: center;
      border-bottom: 0.02rem solid #f2efef;
      .img {
        img {
          object-fit: cover;
          border-radius: 0.1rem;
          height: 1.2rem;
          width: 1.2rem;
        }
      }
      .detail {
        height: 0.8rem;
        padding: 0.2rem 0.22rem;
        width: 2.66rem;
        .name {
          height: 0.32rem;
          font-size: 0.28rem;
          line-height: 0.28rem;
          font-family: Montserrat-SemiBold;
          color: #333333;
          letter-spacing: 0;
        }
        .description {
          margin-top: 0.16rem;
          font-family: Montserrat-SemiBold;
          font-size: 0.28rem;
          color: #00b459;
          line-height: 0.36rem;
          letter-spacing: 0;
        }
      }
    }
    .bottom {
      height: 4rem;
      ::v-deep .van-field__body {
        textarea {
          height: 3.6rem;
          font-family: Montserrat-Light;
          font-size: 0.28rem;
          color: #333333;
          letter-spacing: 0;
          text-align: left;
          line-height: 0.36rem;
        }
        textarea::-webkit-input-placeholder {
          font-family: Montserrat-Light;
          font-size: 0.28rem;
          color: #AAAAAA;
          letter-spacing: 0;
          text-align: left;
          line-height: 0.36rem;
        }
      }
    }
  }
  .evaluation-button {
    height: 0.8rem;
    margin: 0.32rem 0;
    .button {
      margin: 0 auto;
      width: 3.12rem;
      height: 0.8rem;
      line-height: 0.8rem;
      font-family: Montserrat-Regular;
      font-size: 0.28rem;
      color: #ffffff;
      background: #00b459;
      border-radius: 0.4rem;
      letter-spacing: 0;
      text-align: center;
      vertical-align: middle;
    }
  }
}
</style>
