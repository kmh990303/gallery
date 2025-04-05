import httpRequestor from "@/libs/httpRequestor.js";

export const getItems = () => {
    return httpRequestor.get("/v1/api/cart/items").catch(e => e.response);
}

export const addItem = (itemId) => {
    return httpRequestor.post("/v1/api/carts", {itemId}).catch(e => e.response);
}

export const removeItem = (itemId) => {
    return httpRequestor.delete(`/v1/api/cart/items/${itemId}`).catch(e => e.response);
}