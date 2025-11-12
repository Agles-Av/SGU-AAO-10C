import axios from "axios";
import { AlertHelper } from "../utilities/AlertHelper";

const ENV = import.meta.env;


const DEFAULT_HOST = "localhost";
const DEFAULT_PORT = "8081";
const DEFAULT_BASE = "";

const HOST = ENV.VITE_API_HOST || DEFAULT_HOST;
const PORT = ENV.VITE_API_PORT || DEFAULT_PORT;
const BASE = ENV.VITE_API_BASE || DEFAULT_BASE;

const SERVER_URL = `http://${HOST}:${PORT}${BASE}`;

export const AxiosClient = axios.create({
  baseURL: SERVER_URL,
  withCredentials: false,
});


AxiosClient.interceptors.request.use(
  (request) => {
    if (request.data instanceof FormData) {
      // Axios manejará el Content-Type para FormData
    } else {
      request.headers["Content-Type"] = "application/json";
    }
    
    request.headers["Accept"] = "application/json";
    request.headers["Access-Control-Allow-Origin"] = "*";

    return request;
  },
  (error) => {
    AlertHelper.showAlert("Error en la petición: " + error.message, "error");
    return Promise.reject(error);
  },
);


AxiosClient.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    console.log("log en axios", error);
    const message =
      error.response?.data?.message ||
      error.response?.data ||
      "Ocurrió un error inesperado.";
      
    AlertHelper.showAlert(message, "error");
    return Promise.reject(error);
  },
);

export default AxiosClient;