<template>
  <div class="fieldList">
    <PlantTask></PlantTask>
  </div>
</template>

<script>
import PlantTask from "@/components/plantTask";
export default {
  name: "FieldList",
  components: { PlantTask },
  data() {
    return {};
  },
  mounted() {},
  methods: {}
};
</script>

<style lang="scss" scoped>
.fieldList {
  background: #fdfdfd;
  padding: 0rem 0.3rem;
  margin-bottom: 0.7rem;
  color: #333;
  font-family: Montserrat-Regular;
}
</style>