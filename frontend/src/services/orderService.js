import httpRequestor from "@/libs/httpRequestor.js";

export const addOrder = (args) => {
    return httpRequestor.post("/v1/api/orders", args).catch(e => e.response);
}

export const getOrders = (args) => {
    return httpRequestor.get("/v1/api/orders", args).catch(e => e.response);
}

export const getOrder = (id) => {
    return httpRequestor.get(`/v1/api/orders/${id}`).catch(e => e.response);
}