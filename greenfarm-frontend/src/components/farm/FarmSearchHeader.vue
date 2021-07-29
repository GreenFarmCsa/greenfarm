<template>
  <div class="header">
    <div style="position:fixed;top:1rem;z-index:1888;width:7.5rem; height:1.14rem">
      <van-search shape="round" v-model="keyword" placeholder="Search" style=" padding:  0.3rem;">
        <template #right-icon>
          <van-button @click="onSearch" round type="primary">Search</van-button>
        </template>
      </van-search>
    </div>
  </div>
</template>
<script>
export default {
  name: "FarmSearchHeader",
  data() {
    return {
      keyword: ""
    };
  },
  computed: {
    routeName() {
      return this.$route.name;
    }
  },
  mounted() {
    this.keyword = this.$route.params.keyword;
  },
  methods: {
    onSearch() {
      this.$emit("onSearch", this.keyword);
    }
  },
  watch: {
    "$route.params.keyword"(val) {
      this.keyword = val;
    }
  }
};
</script>
<style lang="less" scoped>
.header {
  /deep/ .van-search {
    padding: 0;
    .van-button {
      height: 0.5rem;
    }
    .van-field__control {
      padding: 0.1rem;
      color: #9a9a9a;
      font-size: 0.24rem;
    }
    .van-field__left-icon {
      margin-right: 0;
      margin-left: 0.08rem;
      .van-icon {
        line-height: 0.68rem;
        color: #9a9a9a;
      }
    }
    .van-icon-clear {
      font-size: 0.28rem;
      margin-right: 0.08rem;
    }
  }
  /deep/ .van-cell.van-cell--borderless.van-field {
    padding: 0 0.14rem 0 0;
  }
  /deep/ .van-button.van-button--primary.van-button--normal.van-button--round {
    height: 0.54rem;
    width: 1.34rem;
  }
  /deep/ .van-field__right-icon {
    padding: 0;
  }
  /deep/ .van-button__text {
    font-family: Montserrat-Light;
  }
}
</style>

