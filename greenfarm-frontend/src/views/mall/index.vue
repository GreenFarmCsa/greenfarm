<template>
  <div>
    <transition name="fade" mode="out-in">
      <router-view :key="$route.name+($route.query.productName||'')"/>
    </transition>
  </div>
</template>
<script>
export default {};
</script>