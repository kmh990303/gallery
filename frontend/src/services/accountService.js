import httpRequestor from "@/libs/httpRequestor.js";

export const join = (args) => {
    return httpRequestor.post("/v1/api/account/join", args).catch(e => e.response);
}

export const login = (args) => {
    return httpRequestor.post("/v1/api/account/login", args).catch(e => e.response);
}

export const check = () => {
    return httpRequestor.get("/v1/api/account/check").catch(e => e.response);
}

export const logout = () => {
    return httpRequestor.post("/v1/api/account/logout").catch(e => e.response);
}