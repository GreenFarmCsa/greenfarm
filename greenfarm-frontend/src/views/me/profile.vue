<template>
  <div class="profile">
    <div class="profile-item" v-for="(item,index) in myProfile" :key="index">
      <div class="key">{{item.key}}</div>
      <div class="value">{{item.value}}</div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      myProfile: [
        {
          key: "User Name",
          value: this.$store.state.user.username
        },
        {
          key: "Full Name",
          value: this.$store.state.user.fullname
        },
        {
          key: "Sex",
          value: this.$store.state.user.sex
        },
        {
          key: "Phone",
          value: this.$store.state.user.phone
        },
        {
          key: "Email",
          value: this.$store.state.user.email
        },
        {
          key: "Home address",
          value: this.$store.state.user.location
        }
      ]
    };
  },
  mounted() {},
  methods: {}
};
</script>

<style lang="scss" scoped>
.profile {
  min-height: calc(100vh - 2.6rem);
  background: #f6f6f6;
  .profile-item {
    padding: 0 0.3rem;
    background: #ffffff;
    display: flex;
    .key {
      padding: 0.3rem 0;
      width: 2.6rem;
      font-family: Montserrat-Regular;
      font-size: 0.28rem;
      color: #333333;
      letter-spacing: 0;
      line-height: 0.36rem;
      border-bottom: 1px solid #e9e9e9;
    }
    .value {
      text-align: right;
      padding: 0.3rem 0;
      width: 4.3rem;
      font-family: Montserrat-Regular;
      font-size: 0.28rem;
      color: #999999;
      letter-spacing: 0;
      line-height: 0.36rem;
      word-break: break-all;
      border-bottom: 1px solid #e9e9e9;
    }
  }
}
</style>
