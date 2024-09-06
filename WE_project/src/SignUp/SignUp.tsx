import React, { useState } from "react";
import flower from "../../src/assets/images/flower.png";

const SignupForm: React.FC = () => {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    confirmPassword: "",
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    // 회원가입 처리 로직
    console.log(formData);
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
              htmlFor="name"
            >
              성함
            </label>
            <input
              id="name"
              name="name"
              type="text"
              placeholder=""
              value={formData.name}
              onChange={handleChange}
              className="w-full px-4 py-3 border-b text-md focus:outline-none focus:border-gray-700"
              required
            />
          </div>
          <div className="mb-6">
            <label
              className="block text-gray-700 text-lg font-bold mb-2"
              htmlFor="email"
            >
              이메일
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
            />
          </div>
          <div className="mb-8">
            <label
              className="block text-gray-700 text-lg font-bold mb-2"
              htmlFor="confirmPassword"
            >
              비밀번호 확인
            </label>
            <input
              id="confirmPassword"
              name="confirmPassword"
              type="password"
              placeholder=""
              value={formData.confirmPassword}
              onChange={handleChange}
              className="w-full px-4 py-3 border-b text-md focus:outline-none focus:border-gray-700"
              required
            />
          </div>
          <button type="submit" className="w-full py-3 px-4 rounded-md text-lg">
            회원가입
          </button>
        </form>
      </div>
    </div>
  );
};

export default SignupForm;
