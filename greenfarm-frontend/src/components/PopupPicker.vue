<template>
  <div class="picker">
    <van-field
      readonly
      clickable
      :value="showValue"
      right-icon="arrow-down"
      @click="showPicker = true"
    />
    <van-popup v-model="showPicker" round position="bottom" class="popupPicker">
      <van-picker
        show-toolbar
        :columns="columns"
        :value-key="valueKey"
        @cancel="showPicker = false"
        @confirm="onConfirm"
        cancel-button-text="cancel"
        confirm-button-text="confirm"
      />
    </van-popup>
  </div>
</template>
<script>
export default {
  name: "PopupPicker",
  props: {
    columns: {
      type: Array,
      default: () => {
        return []
      }
    },
    valueKey: {
      type: String,
      default: ""
    },
    realValueKey: {
      type: String,
      default: ""
    },
    showValueKey: {
      type: String,
      default: ""
    },
    backVal: {
      type: String,
      default: "value"
    },
  },

  data() {
    return {
      showPicker: false,
      showValue: "",
      realValue: ""
    };
  },
  methods: {
    onConfirm(value,index) {
      this.showValue = value[this.showValueKey]
      this.showPicker = false;
      if( this.backVal == "value"){
        this.$emit("onConfirm",value[this.realValueKey])        
      } else if( this.backVal == "index"){
        this.$emit("onConfirm",{
          "value": value,
          "index": index
        })        
      }
    }

  },
  watch: {
  },
  mounted() {}
};
</script>
<style lang="less" scoped>
.picker {
  width: 100%;
  ::v-deep .van-cell__value {
    height: 0.7rem;
  }
  ::v-deep .van-cell {
    border: 0.02rem solid #dcdee0;
    border-radius: 0.08rem;
    padding: 0 0.1rem 0 0.28rem;
    margin-top: 0.25rem;
    box-sizing: border-box;
    width: 100%;
    font-size: 0.28rem;
    font-family: Montserrat-Regular;
  }
  ::v-deep .van-field__body {
    height: 0.7rem;
    input {
      height: 0.7rem;
    }
  }
  .popupPicker {
    ::v-deep .van-ellipsis {
      font-family: Montserrat-Light;
    }
    ::v-deep .van-picker__confirm,
    ::v-deep .van-picker__cancel {
      font-family: Montserrat-Medium;
      padding: 0 0.4rem;
    }
  }
}
</style>

