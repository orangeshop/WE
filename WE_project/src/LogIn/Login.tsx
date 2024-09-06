import React, { useState } from "react";
import flower from "../../src/assets/images/flower.png";

const LoginForm: React.FC = () => {
  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });

  const [error, setError] = useState<string | null>(null);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

    if (!emailPattern.test(formData.email)) {
      setError("이메일 형식이 올바르지 않습니다.");
      return;
    }

    // 로그인 처리 로직
    console.log(formData);
    setError(null); // 에러 메시지 초기화
  };

  return (
    <div className="font-nanum bg-image">
      <div>
        <nav className="fixed top-0 left-0 w-full flex items-center py-9 px-3 z-50 bg-white border-b border-gray-300">
          <div className="mr-24 ml-12 text-2xl">
            <a href="/">[ WE : ]</a>
          </div>
          <div className="flex gap-12 justify-center mt-1">
            <a href="/invitation" className="text-lg">
              Mobile Invitation Card
            </a>
            <a href="/account" className="text-lg">
              Account Book
            </a>
            <a href="/signup" className="text-lg">
              Sign Up
            </a>
            <a href="/login" className="text-lg">
              Login
            </a>
          </div>
        </nav>
      </div>

      <div className="flex justify-center mt-40 mb-20 w-[600px]">
        <img src={flower} alt="flower" className="w-40" />
      </div>
      <div className="flex justify-center mb-20">
        <form className="w-full" onSubmit={handleSubmit}>
          <div className="mb-6">
            <label
              className="block text-gray-700 text-lg font-bold mb-2"
              htmlFor="email"
            >
              이메일 주소
            </label>
            <input
              id="email"
              name="email"
              type="email"
              placeholder=""
              value={formData.email}
              onChange={handleChange}
              className="w-full px-4 py-3 border-b text-md focus:outline-none focus:border-gray-700"
              required
              pattern="^[^\s@]+@[^\s@]+\.[^\s@]+$"
              title="유효한 이메일 주소를 입력하세요. (예: ssafy@domain.com)"
            />
          </div>
          <div className="mb-6">
            <label
              className="block text-gray-700 text-lg font-bold mb-2"
              htmlFor="password"
            >
              비밀번호
            </label>
            <input
              id="password"
              name="password"
              type="password"
              placeholder=""
              value={formData.password}
              onChange={handleChange}
              className="w-full px-4 py-3 border-b text-md focus:outline-none focus:border-gray-700"
              required
              pattern="(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,}"
              title="비밀번호는 최소 8자 이상이며 특수 문자를 포함해야 합니다."
            />
          </div>
          {error && (
            <div className="mb-4 text-red-500 text-center">
              {error}
            </div>
          )}
          <button type="submit" className="w-full py-3 px-4 rounded-md text-lg bg-[#f5f0e6]">
            로그인
          </button>
        </form>
      </div>
    </div>
  );
};

export default LoginForm;
