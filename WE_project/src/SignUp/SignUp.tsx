import React, { useState } from "react";
import flower from "../../src/assets/images/flower.png";

const SignupForm: React.FC = () => {
  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    confirmPassword: "",
  });

  const [agreeTerms, setAgreeTerms] = useState(false);
  const [error, setError] = useState("");

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });

    if (name === "confirmPassword") {
      if (value !== formData.password) {
        setError("비밀번호가 일치하지 않습니다.");
      } else {
        setError("");
      }
    }
  };

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (formData.password !== formData.confirmPassword) {
      setError("비밀번호가 일치하지 않습니다.");
      return;
    }

    if (!agreeTerms) {
      setError("개인정보 수집 및 이용 동의에 체크해주세요.");
      return;
    }

    console.log(formData);
    setError("");
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
          <div className="mb-6">
            <label className="flex items-center">
              <input
                type="checkbox"
                checked={agreeTerms}
                onChange={() => setAgreeTerms(!agreeTerms)}
                className="mr-2 w-4 h-4"
              />
              <span className="text-gray-700 text-md">
                개인정보 수집 및 이용에 동의합니다. (필수)
              </span>
              <span className="ml-4 text-[#535bf2] underline">
                <a
                  href="https://plip.kr/pcc/0d19f2d4-2de5-47ff-bd03-839c7e3ffaf8/privacy/1.html"
                  target="_blank"
                  rel="noopener noreferrer"
                >
                  자세히 보기
                </a>
              </span>
            </label>
          </div>
          {error && (
            <div className="mb-4 text-red-500 text-center">{error}</div>
          )}
          <button
            type="submit"
            className="w-full py-3 px-4 rounded-md text-lg bg-[#f5f0e6] mt-5"
          >
            회원가입
          </button>
        </form>
      </div>
    </div>
  );
};

export default SignupForm;
