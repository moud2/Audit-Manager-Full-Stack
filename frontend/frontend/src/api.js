import axios from "axios";

const api = axios.create({
    baseURL: import.meta.env.VITE_BACKEND_URL || "/api",
    headers: {
        'accept': 'application/json'
    }
})

export default api