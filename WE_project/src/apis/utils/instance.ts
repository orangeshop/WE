import axios from "axios";

// const BASE_URL = "http://192.168.100.149:8080/v1"; // 경모
const BASE_URL = "http://192.168.100.95:8080/v1"; // 인수

const api = axios.create({
  baseURL: BASE_URL,
  withCredentials: true,
});

export default api;
