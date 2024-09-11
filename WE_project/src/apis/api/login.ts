import api from "../utils/instance";

export type LoginDto = {
  email: string;
  password: string;
};

// 응답 데이터 타입 정의
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
