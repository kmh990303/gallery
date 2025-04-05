import axios from "axios";
import {useAccountStore} from "@/stores/account.js";

const instance = axios.create();

instance.interceptors.response.use((res) => {
    return res;
}, async (err) => {
    switch (err.response.status) {
        case 400:
            window.alert("잘못된 요청입니다.");
            break;

        case 401:
            const config = err.config;

            if (config.retried) {
                window.alert("권한이 없습니다.");
                window.location.replace("/");
                return;
            }

            const res = await axios.get("/v1/api/account/token");

            const accessToken = res.data;

            const accountStore = useAccountStore();


            accountStore.setAccessToken(accessToken);

            config.headers.authorization = `Bearer ${accountStore.accessToken}`;

            config.retried = true;

            return instance(config);

        case 500:
            window.alert("오류가 있습니다. 관리지에게 문의해주세요.");
            break;
    }

    return Promise.reject(err);
})

const generateConfig = () => {
    const accountStore = useAccountStore();

    const config = {};

    if (accountStore.accessToken) {
        config.headers = {
            authorization: `Bearer ${accountStore.accessToken}`
        };
    }

    return config;
};

export default {
    get(url, params) {
        const config = generateConfig();
        config.params = params;
        return instance.get(url, config);
    },
    post(url, params) {
        return instance.post(url, params, generateConfig());
    },
    put(url, params) {
        return instance.put(url, params, generateConfig());
    },
    delete(url) {
        return instance.delete(url, generateConfig());
    }
};