<template>
  <div class="div">
    <div class="input">
      <input
        v-if="disabled"
        readonly
        type="text"
        :class="input"
        :placeholder="placeholder"
        @blur="onblur"
        :value="value"
        @input="$emit('input',$event.target.value)"
        maxlength="30"
      >
      <input
        v-else
        type="text"
        :class="input"
        :placeholder="placeholder"
        @blur="onblur"
        :value="value"
        @input="$emit('input',$event.target.value)"
        maxlength="30"
      >
      <div :class="errors">{{error}}</div>
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
    error: {
      type: String,
      default: ""
    },
    placeholder: {
      type: String,
      default: ""
    },
    inputval: {
      type: String,
      default: ""
    },
    disabled: {
      type: Boolean,
      default: false
    },
    value: {
      type: String,
      default: ""
    }
  },

  data() {
    return {
      input: "inputstyle",
      errors: "hidden",
      val: this.inputval
    };
  },
  methods: {
    onblur() {
      if (this.error && !this.inputval) {
        this.errors = "error";
      } else {
        this.errors = "hidden";
      }
    }
  },
  watch: {
    newValue() {
      this.$emit("input", this.val);
    }
  },
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
  .inputstyle {
    padding-left: 0.28rem;
    box-sizing: border-box;
    margin-top: 0.25rem;
    height: 0.7rem;
    width: 100%;
    border: 0.02rem solid #dcdee0;
    border-radius: 0.08rem;
    font-size: 0.28rem;
    line-height: 0.28rem;
  }
  .error {
    color: red;
    font-size: 0.26rem;
  }
}
</style>

