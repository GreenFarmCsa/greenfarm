<template>
  <div>
    <van-submit-bar
      label="Total: "
      text-align="center"
      currency="$"
      :price="price"
      button-color="#00B459"
      button-text="Account"
      @submit="onSubmit"
    >
      <van-checkbox checked-color="#08C25E" @click="setCheckedAll" v-model="checkAll">
        <span class="text">ALL</span>
      </van-checkbox>
    </van-submit-bar>
  </div>
</template>
<script>
export default {
  name: "SubmitBar",
  data() {
    return {
      checkAll: false
    };
  },
  props: {
    checked: {
      type: Boolean,
      default: false
    },
    price: {
      type: Number,
      default: 0
    }
  },
  methods: {
    onSubmit() {
      if (this.price !== 0) {
        this.$router.push({ name: "orderEdit" });
      }
    },
    setCheckedAll() {
      this.$emit("selectAll");
    }
  }
};
</script>
<style lang="less" scoped>
/deep/ .van-submit-bar {
  bottom: 1rem;
  height: 1.2rem;

  .van-submit-bar__text {
    font-family: Montserrat-Regular;
    font-size: 0.2rem;
    color: #666666;
    span {
      font-family: Montserrat-Regular;
    }
    .van-submit-bar__price {
      color: #08c25e;
    }
  }
  .van-submit-bar__button {
    padding: 0 0.5rem;
  }
}
.text {
  font-family: Montserrat-Regular;
  font-size: 0.2rem;
  color: #050505;
}
</style>
