<template>
  <div>
    <div class="community-list-wrapper">
      <div
        class="community-list-item"
        v-for="(item,index) in dataList"
        :key="index"
        @click="goDetail(item)" >
        <div class="img-wrapper">
          <!-- <img v-lazy="item.topicImageUrl" alt> -->
          <van-image :src="item.topicImageUrl" lazy-load>
            <template v-slot:loading>
              <van-loading type="spinner" size="20" />
            </template>
          </van-image>
          <div class="like-wrapper">
            <svg-icon
              iconClass="heart-enabled"
              style="font-size: 0.38rem;vertical-align: middle;"
              v-if="item.hasLiked"
              @click.stop="onLike(item)"
            ></svg-icon>
            <svg-icon
              iconClass="heart-disabled"
              style="font-size: 0.38rem;vertical-align: middle;"
              v-else
              @click.stop="onLike(item)"
            ></svg-icon>
            <div class="like-num">{{item.likeSum}}</div>
          </div>
        </div>
      </div>
    </div>
    <div class="addIcon" @click="goAdd">
      <img :src="require('@/assets/images/add-green-cross.png')" alt>
    </div>
  </div>
</template>

<script>
import { queryTopicByUserName, topicLike } from "@/api/topic";
import Vue from 'vue';
import { Lazyload } from 'vant';
Vue.use(Lazyload);
export default {
  data() {
    return {
      dataList: []
    };
  },
  computed: {},
  mounted() {
    // window.scrollTo(0, 1);
    // window.scrollTo(0, 0);
    this.getData();
  },
  methods: {
    getData() {
      let params = {
        username: this.$store.state.user.username || "jack"
      };
      queryTopicByUserName(params).then(res => {
        let arr = res.data;
        for (let i = 0; i < arr.length; i++) {
          arr[i].topicImageUrl =
            process.env.VUE_APP_BASE_API +
            "/file/download?url=" +
            arr[i].topicImageUrl;
        }
        this.dataList = arr;
      });
    },
    goDetail(item) {
      this.$router.push({
        path: "/community/post-detail/" + item.topicId
      });
    },
    onLike(item) {
      let params = {
        topicId: item.topicId,
        username: this.$store.state.user.username || "jack",
        isLike: !item.hasLiked
      };
      topicLike(params).then(res => {
          if (item.hasLiked == false) {
            item.likeSum = Number(item.likeSum) + 1;
          } else {
            item.likeSum = Number(item.likeSum) - 1;
          }
          item.hasLiked = !item.hasLiked;
      })
      .catch(e => {
        console.log(error,e)
      });
    },
    goAdd() {
      this.$router.push({
        path: "/community/addTopic"
      });
    }
  }
};
</script>

<style lang="scss" scoped>
.community-list-wrapper {
  overflow: hidden;
  padding-bottom: 0.2rem;
  .community-list-item {
    float: left;
    width: 32.3%;
    margin-right: 1.5%;
    margin-bottom: 1.3%;
    .img-wrapper {
      position: relative;
      overflow: hidden;
      width: 100%;
      height: 2.5rem;
      border: 0.02rem solid #f3f4f5;
      box-sizing: border-box;
      ::v-deep .van-image {
        width: 100%;
        height: 100%;
      }
      ::v-deep .van-image__img, .van-image__error, .van-image__loading {
        width: 100%;
        height: 100%;
        object-fit: cover;
      }
      .like-wrapper {
        position: absolute;
        bottom: 0.2rem;
        right: 0.1rem;
        .like {
          display: inline-block;
          margin-right: 0.04rem;
          vertical-align: middle;
          width: 0.36rem;
          height: 0.36rem;
          background: green;
          &.active {
            background: red;
          }
        }
        .like-num {
          display: inline-block;
          margin-left: 0.08rem;
          vertical-align: middle;
          font-family: "Montserrat-Light";
          font-size: 0.28rem;
          color: #ffffff;
          letter-spacing: 0;
          text-align: center;
          line-height: 0.56rem;
        }
      }
    }
    &:nth-child(1) {
      width: 66.1%;
      .img-wrapper {
        width: 100%;
        height: 5.1rem;
        border: 0.02rem solid #f3f4f5;
        box-sizing: border-box;
      }
    }
    &:nth-child(3n),
    &:nth-child(2),
    &:nth-child(3) {
      margin-right: 0;
    }
  }
}
.addIcon {
  position: fixed;
  bottom: 1.6rem;
  right: 0.8rem;
  width: 1.24rem;
  height: 1.24rem;
  img {
    width: 1.24rem;
    height: 1.24rem;
  }
}
</style>
