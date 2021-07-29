<template>
  <div class="div">
    <div class="label">
      <span :class="must">*</span>
      <span class="title">{{title}}</span>
    </div>
  </div>
</template>
<script>
export default {
  name: "FormInput",
  props: {
    title: {
      type: String,
      default: ""
    },
    must: {
      type: String,
      default: ""
    }
  },

  data() {
    return {};
  },
  methods: {},
  created() {},
  mounted() {}
};
</script>
<style lang="less" scoped>
.div {
  width: 100%;
  background-color: #fff;
  .hidden {
    display: none;
  }
  .label {
    width: 100%;
    .must {
      color: red;
    }
    .title {
      font-family: Montserrat-Regular;
      font-size: 0.28rem;
      color: #151515;
      letter-spacing: 0;
      line-height: 0.28rem;
    }
  }
}
</style>

