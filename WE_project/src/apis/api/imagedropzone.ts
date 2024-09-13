import api from "../utils/instance";

export type ImageDto = {
  url: File;
};

export const inputImage = async (dto: ImageDto): Promise<void> => {
  try {
    const response = await api.post(`/invitation/formal`, dto, {});
    console.log(response);
    return response.data;
  } catch (error) {
    console.error("Error during member registration:", error);
    throw error;
  }
};
