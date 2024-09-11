import api from "../utils/instance";

export type LoginDto = {
  email: string;
  password: string;
};

export const login = async (dto: LoginDto): Promise<void> => {
  try {
    const response = await api.post(`/auth/login`, dto, {
      headers: {
        "Content-Type": "application/json",
      },
    });
    console.log(response);
    return response.data;
  } catch (error) {
    console.error("Error during member registration:", error);
    throw error;
  }
};
