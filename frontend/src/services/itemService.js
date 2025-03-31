import axios from "axios";

export const getItems = () => {
    return axios.get("/v1/api/items").catch(e => e.response);
}