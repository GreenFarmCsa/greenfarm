<template>
  <div class="creditCard">
    <div class="gift">
      <img :src="require('@/assets/images/gifts.png')" alt>
    </div>
    <div class="card">GREEN CREDIT CARD</div>
    <div class="frist">
      Get
      <span>25%</span> off for your first payment .
    </div>
    <div class="done">
      <van-button @click="goResult" :disabled="!checked" round color="#00B459">DONE</van-button>
    </div>
    <div class="footer">
      <van-checkbox v-model="checked" checked-color="#08C25E"/>
      <span class="policies">Accept green credit card policies</span>
    </div>
  </div>
</template>
<script>
export default {
  name: "CreditCard",
  data() {
    return {
      checked: false
    };
  },
  methods: {
    goResult() {
      this.$emit("confirm");
    }
  }
};
</script>
<style lang="less" scoped>
/deep/ .van-button {
  height: 0.8rem;
  width: 4rem;
  padding: 0 0.5rem;
  font-family: Montserrat-Regular;
  font-size: 0.28rem;
}
.creditCard{
  padding: 0 0.68rem;
  .gift {
    text-align: center;
    img {
      width: 2rem;
      height: 2rem;
      margin-top: 1rem;
    }
  }
  .card {
    font-family: Montserrat-Regular;
    font-size: 0.3rem;
    color: #37353d;
    margin-top: 0.3rem;
    text-align: center;
  }
  .frist {
    font-family: Montserrat-Light;
    font-size: 0.28rem;
    color: #383f45;
    text-align: center;
    margin-top: 0.3rem;
    span {
      color: red;
    }
  }
  .done {
    text-align: center;
    margin-top: 3rem;
  }
  .footer {
    display: flex;
    padding: 0.3rem 0;
    .policies {
      font-family: Montserrat-Light;
      font-size: 0.2rem;
      color: #666666;
      margin-left: 0.1rem;
      margin-top: 0.03rem;
    }
  }
}
</style>
