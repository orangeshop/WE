import api from "../utils/instance";

export type LoginDto = {
  email: string;
  password: string;
};

export type LoginResponse = {
  message: string;
  data: {
    memberInfo: {
      id: number;
      email: string;
      nickname: string;
      imageUrl: string | null;
      regDate: string;
      leavedDate: string | null;
      leaved: boolean;
    };
    coupleInfo: {
      id: number;
      accountNumber: string;
    };

    tokens: {
      accessToken: string;
      refreshToken: string;
    };
  };
};

export const login = async (dto: LoginDto): Promise<LoginResponse> => {
  try {
    const response = await api.post<LoginResponse>(`/auth/login`, dto, {
      headers: {
        "Content-Type": "application/json",
      },
    });

    return response.data;
  } catch (error) {
    console.error("Error during login:", error);
    throw error;
  }
};
