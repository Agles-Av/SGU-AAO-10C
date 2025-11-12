import AxiosClient from "../interceptor/AxiosClient";
import { AlertHelper } from "../utilities/AlertHelper";

// NOTE: Backend API base path: /api/usuarios

export const fetchAllUsers = async () => {
    try {
        const response = await AxiosClient({
            method: "GET",
        });

        // Returns the whole ApiResponse object from the backend
        return response.data;
    } catch (e) {
        AlertHelper.showAlert(e.message, "error");
    }
};

export const fetchUserById = async (id) => {
    try {
        const response = await AxiosClient({
            method: "GET",
            url: `/${id}`,
        });

        return response.data;
    } catch (e) {
        AlertHelper.showAlert(e.message, "error");
    }
};

export const createUser = async (userPayload) => {
    try {
        const response = await AxiosClient({
            method: "POST",
            data: userPayload,
        });

        return response.data;
    } catch (e) {
        AlertHelper.showAlert(e.message, "error");
    }
};

export const updateUser = async (id, userPayload) => {
    try {
        const response = await AxiosClient({
            method: "PUT",
            url: `/${id}`,
            data: userPayload,
        });

        return response.data;
    } catch (e) {
        AlertHelper.showAlert(e.message, "error");
    }
};

export const deleteUser = async (id) => {
    try {
        const response = await AxiosClient({
            method: "DELETE",
            url: `/${id}`,
        });

        return response.data;
    } catch (e) {
        AlertHelper.showAlert(e.message, "error");
    }
};
