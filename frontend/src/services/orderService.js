import axios from "axios";

export const addOrder = (args) => {
    return axios.post("/v1/api/orders", args).catch(e => e.response);
}

export const getOrders = (args) => {
    return axios.get("/v1/api/orders", args).catch(e => e.response);
}

export const getOrder = (id) => {
    return axios.get(`/v1/api/orders/${id}`).catch(e => e.response);
}