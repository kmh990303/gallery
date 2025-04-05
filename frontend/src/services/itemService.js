import httpRequestor from "@/libs/httpRequestor.js";

export const getItems = () => {
    return httpRequestor.get("/v1/api/items").catch(e => e.response);
}