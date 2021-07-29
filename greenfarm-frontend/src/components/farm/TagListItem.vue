<template>
  <div class="tag-list-item">{{dataObj.name}}</div>
</template>
<script>
export default {
  name: "TagListItem",
  data() {
    return {};
  },
  props: {
    dataObj: {
      default: () => {},
      type: Object
    }
  },
  methods: {
    goDetail() {}
  }
};
</script>
<style lang="scss">
.tag-list {
  &::-webkit-scrollbar {
    height: 0;
    display: none;
  }
  overflow-x: scroll;
  scrollbar-width: none;
  -ms-overflow-style: none;
  display: flex;
  margin: 0 0 0.3rem 0;
  width: 6.9rem;
  .tag-list-item {
    min-width: 1.25rem;
    height: 0.56rem;
    padding: 0 0.14rem;
    line-height: 0.56rem;
    margin-right: 0.12rem;
    background: #f1f0f5;
    border-radius: 0.26rem;
    text-align: center;
    font-family: "Montserrat-Light";
    font-size: 0.24rem;
    color: #666666;
    letter-spacing: 0;
    text-align: center;
    &.active {
      background: rgba(0, 180, 89, 0.1);
      color: #00b459;
    }
  }
}
</style>

