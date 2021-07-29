<template>
  <div class="overview">
    <div class="img">
      <img
        :src="require('@/assets/images/seed-'+($route.query.seedId % 5 == '0' ? 5 : $route.query.seedId % 5)+'.png')"
        alt
      >
    </div>
    <div class="title">Crop Production Data Provided by FAO</div>
    <div class="box" v-html="dataObj.seedIntroduction"></div>
    <div class="null"></div>
  </div>
</template>

<script>
import { querySeedById } from "@/api/farm";
export default {
  data() {
    return {
      title: "green plant",
      context: "context",
      dataObj: {}
    };
  },
  mounted() {
    this.getData();
  },
  methods: {
    getData() {
      let params = {
        id: this.$route.query.seedId
      };
      querySeedById(params).then(res => {
        let tmp = res.data;
        if(tmp.seedIntroduction){
          tmp.seedIntroduction = tmp.seedIntroduction.replace(/\n/g, "<br/>");
        }
        this.dataObj = tmp;
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.img {
  width: 100%;
  height: 4rem;
  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
}
.title {
  margin: 0.36rem 0.44rem 0.3rem;
  font-family: Montserrat-Medium;
  font-size: 0.3rem;
  color: #333333;
  letter-spacing: 0;
  line-height: 0.5rem;
}
.box {
  margin: 0 0.44rem;
  text-indent: 2em;
  margin-top: 0.2rem;
  font-family: Montserrat-Light;
  font-size: 0.28rem;
  color: #666666;
  letter-spacing: 0;
  text-align: justify;
  line-height: 0.4rem;
  word-break: break-all;
}
.null {
  width: 100%;
  height: 1.4rem;
}
</style>
