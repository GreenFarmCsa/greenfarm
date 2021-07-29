<template>
  <div class="body">
    <div class="img">
      <img src="@/assets/images/order-success-smile.png" alt>
    </div>
    <div class="msg">The product was successfully</div>
    <div class="msg">placed on the shelves!</div>
    <div class="button">
      <button @click="$router.back()">Complete</button>
    </div>
  </div>
</template>
<script>
export default {
  data() {
    return {};
  },
  methods: {}
};
</script>
<style lang="scss" scoped>
.body {
  .img {
    width: 1.84rem;
    height: 1.84rem;
    margin: 1.34rem auto 0.76rem auto;
    img {
      width: 100%;
      height: 100%;
    }
  }
  .msg {
    font-family: Montserrat-Medium;
    font-size: 0.32rem;
    color: #333333;
    letter-spacing: 0;
    line-height: 0.32rem;
    text-align: center;
    margin-bottom: 0.14rem;
  }
  p {
    width: 70%;
    margin: 0.34rem auto 0.34rem auto;
    font-family: Montserrat-Regular;
    font-size: 0.28rem;
    color: #666666;
    letter-spacing: 0;
    text-align: center;
    line-height: 0.4rem;
  }
  .button {
    margin: 1rem auto;
    width: 100%;
    position: relative;
    text-align: center;
  }
  button {
    border: 0;
    font-family: Montserrat-Regular;
    font-size: 0.28;
    color: #fcfcfc;
    letter-spacing: 0;
    line-height: 0.28rem;
    background: #00b459;
    border-radius: 0.44rem;
    padding: 0.25rem 1rem;
  }
}
</style>


