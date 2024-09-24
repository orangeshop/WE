import React, { useState } from "react";
import flower from "../../src/assets/images/flower.png";
import Navbar from "../Components/Navbar";
import { login, LoginDto } from "../apis/api/login";
import { useNavigate } from "react-router-dom";

const LoginForm: React.FC = () => {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });

  const [error, setError] = useState<string | null>(null);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError(null);
    try {
      const memberData: LoginDto = {
        email: formData.email,
        password: formData.password,
      };

      const response = await login(memberData); // 로그인 응답을 받음
      console.log("로그인 응답:", response);

      // 응답에서 토큰을 가져옴
      const { accessToken, refreshToken } = response.data.tokens;

      localStorage.setItem("accessToken", accessToken);
      localStorage.setItem("refreshToken", refreshToken);

      navigate("/");
    } catch {
      setError("로그인 중 오류가 발생했습니다. 다시 시도해주세요.");
    }
  };

  return (
    <div className="font-nanum bg-image">
      <Navbar isScrollSensitive={false} />

      <div className="flex justify-center mt-40 mb-20 w-[480px]">
        <img src={flower} alt="flower" className="w-40" />
      </div>
      <div className="flex justify-center mb-20">
        <form className="w-full" onSubmit={handleSubmit}>
          <div className="mb-6">
            <label
              className="block text-gray-700 font-bold mb-2"
              htmlFor="email"
            >
              이메일
            </label>
            <input
              id="email"
              name="email"
              type="email"
              value={formData.email}
              onChange={handleChange}
              className="w-full px-4 py-3 border-b text-md focus:outline-none focus:border-gray-700 bg-[#fcfaf5]"
              required
              pattern="^[^\s@]+@[^\s@]+\.[^\s@]+$"
              title="유효한 이메일 주소를 입력하세요. (예: ssafy@domain.com)"
            />
          </div>
          <div className="mb-6">
            <label
              className="block text-gray-700 font-bold mb-2"
              htmlFor="password"
            >
              비밀번호
            </label>
            <input
              id="password"
              name="password"
              type="password"
              value={formData.password}
              onChange={handleChange}
              className="w-full px-4 py-3 border-b text-md focus:outline-none focus:border-gray-700 bg-[#fcfaf5]"
              required
              title="비밀번호는 최소 8자 이상이며 특수 문자를 포함해야 합니다."
            />
          </div>
          {error && (
            <div className="mb-4 text-red-500 text-center">{error}</div>
          )}
          <button
            type="submit"
            className="w-full py-3 px-4 rounded-md text-lg font-bold bg-[#FFECCA] mt-5"
          >
            로그인
          </button>
        </form>
      </div>
    </div>
  );
};

export default LoginForm;
