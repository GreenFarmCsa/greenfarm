<template>
  <div class="address">
    <div class="title">
      <div class="icon">
        <span class="def">Default</span>
      </div>
      <div class="block">
        <div class="info">{{address}}</div>
        <div class="tel">{{name}} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {{tel}}</div>
      </div>
    </div>
    <div class="banner">
      <img :src="require('@/assets/images/mall-banner.png')" alt>
    </div>
  </div>
</template>
<script>
import { mapState } from "vuex";
export default {
  name: "AddressHeader",
  data() {
    return {};
  },
  methods: {},
  mounted() {},
  computed: {
    ...mapState({
      name: state => state.user.fullname || "Jack Miller",
      tel: state => state.user.phone || "585-5208167",
      address: state =>
        state.user.location || "130 Wenquan Town, Haidian District, Beijing"
    })
  }
};
</script>
<style lang="scss" scoped>
.address {
  background-color: #fff;
  .title {
    display: flex;
    padding: 0.3rem;
    .block {
      margin-left: 0.2rem;
    }
    .info {
      font-family: Montserrat-Medium;
      font-size: 0.3rem;
      color: #333333;
    }
    .tel {
      font-family: Montserrat-Light;
      font-size: 0.28rem;
      color: #666666;
      padding-top: 0.3rem;
    }
    .def {
      color: #fefefe;
      font-size: 0.2rem;
      font-family: Montserrat-Light;
      border-radius: 10px;
      background-color: #00b459;
      display: inline-block;
      width: 1rem;
      text-align: center;
      line-height: 0.4rem;
    }
  }
}
.banner {
  height: 0.2rem;
  img {
    width: 100%;
  }
}
</style>
