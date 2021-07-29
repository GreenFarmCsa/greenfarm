<template>
  <div class="farmaddbody">
    <div class="body">
      <div class="body-content">
        <div class="form-title">
          <span>Registration Of Land</span>
        </div>
        <div class="form-body">
          <div class="farmLogo">
            <Label :title="farmCrops.farmName" :must="farmCrops.must"/>
            <div class="farmCrops">
              <FormInput
                :error="farmCrops.error"
                :placeholder="farmCrops.placeholder"
                v-model="inputval"
              />
              <div class="add-button">
                <button @click="addCrop">Add</button>
              </div>
            </div>
            <div class="tab-button" v-for="(item,index) in cropsList" :key="index">
              <button>{{item}}</button>
              <div class="close" @click="close(index)">
                <svg-icon iconClass="close" style="font-size:.24rem"></svg-icon>
              </div>
            </div>
          </div>
          <div class="farmLogo">
            <Label :title="farmArea.farmName" :must="farmArea.must"/>
            <div class="farmCrops-parent">
              <div class="farmCrops-button">
                <button @click="uploadar">AR Ruler</button>
              </div>
              <div class="farmCrops">
                <FormInput
                  :error="farmArea.error"
                  :placeholder="farmArea.placeholder"
                  :inputval="formData.area"
                  v-model="formData.area"
                  :disabled="disabled"
                />
                <span>m²</span>
              </div>
            </div>
          </div>
          <div class="farmLogo">
            <Label :title="farmPirce.farmName" :must="farmPirce.must"/>
            <div class="farmprice">
              <span>$</span>
              <FormInput
                :error="farmPirce.error"
                :placeholder="farmPirce.placeholder"
                :inputval="formData.price"
                v-model="formData.price"
              />
            </div>
          </div>
          <div class="bottom-button">
            <div class="cancel">
              <button @click="cancel">Cancel</button>
            </div>
            <div class="confirm">
              <button @click="confirm">Confirm</button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="divs"></div>
  </div>
</template>

<script>
import FormInput from "@/components/FormInput";
import Label from "@/components/Label";
import { addLand } from "@/api/land";
export default {
  components: { FormInput, Label },
  data() {
    return {
      disabled: true,
      formData: {
        farmId: this.$route.query.id,
        isRent: "",
        suitedCrops: "",
        area: "30"
      },
      farmArea: {
        farmName: "Area of Farm",
        error: "",
        placeholder: "Please enter the…",
        must: "must"
      },
      farmPirce: {
        farmName: "Lease Price (per year)",
        error: "Lease Price of the farm is not allowed null",
        placeholder: "Please enter the lease price",
        must: "must"
      },
      farmCrops: {
        farmName: "Suited crops",
        placeholder: "Please enter the…",
        must: "must"
      },
      cropsList: [],
      inputval: "",
      addressList: [
        {
          Country: "China",
          city: [
            {
              name: "Beijing"
            },
            {
              name: "Tianjin"
            },
            {
              name: "Shanghai"
            },
            {
              name: "Shenzhen"
            }
          ]
        },
        {
          Country: "USA",
          city: [
            {
              name: "New York"
            }
          ]
        }
      ],
      cityList: []
    };
  },
  methods: {
    uploadar() {
      window.UMJSBridge.callHandler("jumpRuler", {}, response => {
        this.formData.area = parseFloat(response);
      });
    },
    cancel() {
      this.$router.back();
    },
    addCrop() {
      if (this.inputval) {
        this.cropsList = this.inputval.split(";");
      }
    },
    close(val) {
      this.cropsList.splice(val, 1);
      this.inputval = "";
      if (this.cropsList) {
        for (let i = 0; i < this.cropsList.length; i++) {
          if (this.inputval) this.inputval += " ; " + this.cropsList[i];
          else this.inputval += this.cropsList[i];
        }
      }
    },

    confirm() {
      let cropsList = this.cropsList;
      this.formData.suitedCrops;
      if (cropsList) {
        for (let i = 0; i < cropsList.length; i++) {
          if (this.formData.suitedCrops)
            this.formData.suitedCrops += "," + cropsList[i];
          else this.formData.suitedCrops += cropsList[i];
        }
      }
      if (!this.formData.price) {
        this.$toast({
          message: "price of the land is not allowed null",
          position: "bottom"
        });
        return;
      }

      if (!this.formData.suitedCrops) {
        this.$toast({
          message: "Crops of the land is not allowed null",
          position: "bottom"
        });
        return;
      }
      addLand({ ...this.formData }).then(res => {
        this.$router.back();
      }).catch(e => {
        console.log(e);
      })
    }
  }
};
</script>

<style lang="scss" scoped>
.farmaddbody {
  width: 100%;
  background: #f6f6f6;
  padding-top: 0.02rem;
  .body {
    width: 100%;
    margin-top: 0.2rem;
    background-color: #fff;
    .body-content {
      width: 90%;
      margin: 0.2rem auto 0 auto;
      padding-top: 0.02rem;
      .form-title {
        margin-top: 0.1rem;
        span {
          font-family: Montserrat-Medium;
          font-size: 0.32rem;
          color: #333333;
          text-align: left;
          line-height: 0.9rem;
        }
      }
      .form-body {
        .farmLogo {
          .farmprice {
            display: flex;
            span {
              margin-top: 0.25rem;
              flex: 1;
              line-height: 0.7rem;
              margin-right: 0.2rem;
              font-family: Montserrat-Regular;
              font-size: 0.28rem;
              color: #333333;
            }
          }
          .location {
            select {
              height: 0.7rem;
              margin-top: 0.25rem;
              width: 100%;
              background: #ffffff;
              border-radius: 0.08rem;
              border: 0.02rem solid #dcdee0;
              padding-left: 0.2rem;
              box-sizing: border-box;
              option {
                span {
                  font-family: Montserrat-Regular;
                  font-size: 0.28rem;
                  color: #969696;
                  letter-spacing: 0;
                  line-height: 0.28rem;
                }
              }
            }
          }
          overflow: hidden;
          margin-top: 0.3rem;
          .tab-button {
            float: left;
            position: relative;
            button {
              background: rgba(0, 180, 89, 0.14);
              padding: 0.1rem 0.2rem;
              border-radius: 0.26rem;
              border: 0;
              font-size: 0.24rem;
              color: #00b459;
              margin-right: 0.2rem;
              margin-top: 0.3rem;
            }
            .close {
              position: absolute;
              right: 0.15rem;
              top: 0.2rem;
            }
          }
          .farmCrops-parent {
            display: flex;
            .farmCrops-button {
              margin-top: 0.25rem;
              flex: 1;
              button {
                padding: 0.2rem 0.4rem;
                border: 0;
                background: #00b459;
                border-radius: 0.4rem;
                font-family: Montserrat-Regular;
                font-size: 0.28rem;
                color: #fcfcfc;
                letter-spacing: 0;
                text-align: center;
                line-height: 0.28rem;
              }
            }
            .farmCrops {
              flex: 2;
              display: flex;
              span {
                flex: 1;
                margin-left: 0.1rem;
                margin-top: 0.25rem;
                font-family: Montserrat-Regular;
                font-size: 0.28rem;
                color: #887d7d;
                letter-spacing: 0;
                line-height: 0.7rem;
              }
              .add-button {
                flex: 2;
                margin-top: 0.25rem;
                button {
                  padding: 0.2rem 0.4rem;
                  background: #00b459;
                  border: 0.02rem solid #dcdee0;
                  border-radius: 0 0.08rem 0.08rem 0;
                  font-family: Montserrat-Regular;
                  font-size: 0.28rem;
                  color: #ffffff;
                  letter-spacing: 0;
                  line-height: 0.28rem;
                }
              }
            }
          }
          .farmCrops {
            display: flex;
            align-items: center;
            span {
              flex: 1;
              margin-top: 0.25rem;
              font-family: Montserrat-Regular;
              font-size: 0.28rem;
              color: #887d7d;
              letter-spacing: 0;
              line-height: 0.7rem;
            }
            .add-button {
              flex: 2;
              margin-top: 0.25rem;
              button {
                padding: 0.2rem 0.4rem;
                background: #00b459;
                border: 0.02rem solid #dcdee0;
                border-radius: 0 0.08rem 0.08rem 0;
                font-family: Montserrat-Regular;
                font-size: 0.28rem;
                color: #ffffff;
                letter-spacing: 0;
                line-height: 0.28rem;
              }
            }
          }

          .farmLogos {
            textarea {
              margin-top: 0.25rem;
              width: 100%;
              border: 0.02rem solid #dcdee0;
              border-radius: 0.08rem;
              padding: 0.28rem;
              box-sizing: border-box;
              font-size: 0.28rem;
            }
          }
          .logo {
            width: 100%;
            margin-top: 0.25rem;
            display: flex;
            .logo-left {
              flex: 2;
              height: 1.4rem;
              width: 1.4rem;
            }
            .logo-right {
              flex: 7;
              height: 1.4rem;
              width: 1.4rem;
              margin-top: 0.4rem;
              span {
                font-family: Montserrat-Light;
                font-size: 0.28rem;
                color: #999999;
                letter-spacing: 0;
                line-height: 0.36rem;
              }
            }
          }
        }
      }
      .bottom-button {
        height: 2rem;
        display: flex;
        .cancel {
          flex: 1;
          height: 0.8rem;
          button {
            margin-top: 0.5rem;
            border: 0.02rem solid #00b459;
            border-radius: 0.4rem;
            color: #00b459;
            background-color: #fff;
            height: 0.8rem;
            width: 95%;
          }
        }
        .confirm {
          flex: 1;
          height: 0.8rem;
          text-align: right;
          button {
            margin-top: 0.5rem;
            border: 0.02rem solid #00b459;
            border-radius: 0.4rem;
            color: #fff;
            background-color: #00b459;
            height: 0.8rem;
            width: 95%;
          }
        }
      }
    }
  }
}
</style>
