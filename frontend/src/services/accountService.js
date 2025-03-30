import axios from "axios";

export const join = (args) => {
    return axios.post("/v1/api/account/join", args).catch(e => e.response);
}

export const login = (args) => {
    return axios.post("/v1/api/account/login", args).catch(e => e.response);
}

export const check = () => {
    return axios.get("/v1/api/account/check").catch(e => e.response);
}

export const logout = () => {
    return axios.post("/v1/api/account/logout").catch(e => e.response);
}