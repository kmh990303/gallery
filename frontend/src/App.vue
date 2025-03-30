<script setup lang="ts">
import Header from "@/components/Header.vue";
import Footer from "@/components/Footer.vue";
import {useAccountStore} from "@/stores/account";
import {watch} from "vue";
import {useRoute} from "vue-router";

const accountStore = useAccountStore();

const route = useRoute();

const checkAccount = async () => {

}

(async function onCreated() {
  await checkAccount();
})();

watch(() => route.path, () => {
  checkAccount();
})
</script>

<template>
  <Header />
  <main>
    <router-view></router-view>
  </main>
  <Footer />
</template>