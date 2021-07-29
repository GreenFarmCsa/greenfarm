<template>
  <div>
    <transition name="fade" mode="out-in">
      <router-view></router-view>
    </transition>
  </div>
</template>

<script>
export default {
  name: "MeIndex",
  data() {
    return {};
  },
  mounted() {},
  methods: {}
};
</script>

<style lang="scss" scoped>
</style>
