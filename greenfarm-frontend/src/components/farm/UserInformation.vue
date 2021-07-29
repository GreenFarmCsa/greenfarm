<template>
  <div class="user-information">
    <div class="information-top">{{title}}</div>
    <div class="information-body">
      <div class="information-body-left">
        <span>Name</span>
      </div>
      <div class="information-body-right">
        <span>{{personInfo.name}}</span>
      </div>
    </div>
    <div class="information-body">
      <div class="information-body-left">
        <span>Phone</span>
      </div>
      <div class="information-body-right">
        <span>{{personInfo.phone}}</span>
      </div>
    </div>
    <div class="information-body">
      <div class="information-body-left">
        <span>Email</span>
      </div>
      <div class="information-body-right">
        <span>{{personInfo.email}}</span>
      </div>
    </div>
    <div class="information-body">
      <div class="information-body-left">
        <span>Home address</span>
      </div>
      <div class="information-body-right">
        <span>{{personInfo.address}}</span>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  name: "UserInformation",
  data() {
    return {
      personInfo: {
        name: this.$store.state.user.username,
        phone: this.$store.state.user.phone,
        email: this.$store.state.user.email,
        address: this.$store.state.user.location
      }
    };
  },
  props: {
    title: {
      type: String,
      default: ""
    }
  },
  methods: {}
};
</script>
<style lang="scss" scoped>
.user-information {
  width: 100%;
  padding: 0.24rem 0;
  .information-top {
    width: 90%;
    margin: 0.1rem auto;
    text-align: left;
    margin-top: 0.02rem;
    font-family: Montserrat-Medium;
    font-weight: 550;
    line-height: 0.5rem;
    font-size: 0.32rem;
    color: #333;
  }
  .information-body {
    width: 90%;
    margin: 0.1rem auto;
    display: flex;
    .information-body-left {
      line-height: 0.5rem;
      flex: 8;
      span {
        font-family: Montserrat-Regular;
        font-size: 0.28rem;
        color: #666;
      }
    }
    .information-body-right {
      line-height: 0.5rem;
      flex: 17;
      text-align: right;
      word-break: break-all;
      span {
        font-family: Montserrat-Regular;
        color: #14ac69;
        font-size: 0.28rem;
      }
    }
  }
}
</style>

