import React from "react";

const SignUp: React.FC = () => {
  return (
    <div className="min-h-screen flex font-nanum justify-center items-center">
      <div className="max-w-lg w-full p-6 bg-white rounded-lg">
        <h1 className="text-2xl font-bold text-center mb-6">로그인</h1>
        <form>
          {/* 성함 */}
          <div className="mb-4">
            <label htmlFor="name" className="block font-semibold mb-2">
              성함 *
            </label>
            <input
              type="text"
              id="name"
              className="w-full px-3 py-2 border border-gray-300 rounded-lg"
              required
            />
          </div>

          {/* 연락처 */}
          <div className="mb-4">
            <label htmlFor="phone" className="block font-semibold mb-2">
              연락처 *
            </label>
            <div className="flex gap-2">
              <input
                type="text"
                id="phone-1"
                maxLength={3}
                className="w-1/3 px-3 py-2 border border-gray-300 rounded-lg"
                required
              />
              <span className="self-center">-</span>
              <input
                type="text"
                id="phone-2"
                maxLength={4}
                className="w-1/3 px-3 py-2 border border-gray-300 rounded-lg"
                required
              />
              <span className="self-center">-</span>
              <input
                type="text"
                id="phone-3"
                maxLength={4}
                className="w-1/3 px-3 py-2 border border-gray-300 rounded-lg"
                required
              />
            </div>
          </div>

          {/* 이메일 주소 */}
          <div className="mb-4">
            <label htmlFor="email" className="block font-semibold mb-2">
              이메일 주소 *
            </label>
            <input
              type="email"
              id="email"
              className="w-full px-3 py-2 border border-gray-300 rounded-lg"
              required
            />
          </div>

          {/* 제출 버튼 */}
          <button
            type="submit"
            className="w-full bg-blue-500 text-white py-3 rounded-lg hover:bg-blue-600"
          >
            제출
          </button>
        </form>
      </div>
    </div>
  );
};

export default SignUp;
