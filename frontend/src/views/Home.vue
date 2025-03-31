<script setup>
import {getItems} from "@/services/itemService.js";
import {reactive, onMounted} from "vue";
import Card from "@/components/Card.vue";

const state = reactive({
  items: []
});

onMounted(async () => {
  try {
    const res = await getItems();
    if (res.status === 200) {
      state.items = [...res.data];
    }
  } catch (error) {
    console.error("데이터 로드 실패:", error);
  }
});
</script>

<template>
  <div class="home">
    <div class="album py-5 bg-light">
      <div class="container">
        <div class="row row-cols-1 row-cols-lg-2 row-cols-xl-3 g-3">
          <div class="col" v-for="item in state.items" :key="item.id">
            <Card :item="item"/>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
