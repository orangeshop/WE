import axios from "axios";

const BASE_URL = "http://192.168.100.149:8080/v1";

const api = axios.create({
  baseURL: BASE_URL,
  withCredentials: true,
});

// export const clearTokenInterceptors = (interceptorId: number) => {
//   api.interceptors.request.eject(interceptorId);
// };

export default api;
