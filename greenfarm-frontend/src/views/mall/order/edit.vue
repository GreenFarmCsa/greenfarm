<template>
  <div class="orderBody">
    <!--address-->
    <div class="address">
      <Address></Address>
    </div>
    <!--detail-->
    <div class="detail">
      <Details></Details>
    </div>
    <!--payment-->
    <div class="payment">
      <Payment></Payment>
    </div>
    <!--chechout-->
    <Checkout></Checkout>
  </div>
</template>
<script>
import Address from "@/components/mall/AddressHeader";
import Checkout from "@/components/mall/CheckoutBar";
import Payment from "@/components/mall/PaymentList";
import Details from "@/components/mall/CheckDetail";
export default {
  name: "OrderEdit",
  components: { Address, Checkout, Payment, Details },
  data() {
    return {};
  }
};
</script>
<style lang="scss" scoped>
.orderBody {
  background-color: #f6f6f6;
  margin-bottom: 1.2rem;
  .address {
    margin-top: 0.2rem;
  }
}
</style>