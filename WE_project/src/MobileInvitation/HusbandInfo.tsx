// HusbandInfo.tsx
import React from "react";

interface HusbandInfoProps {
  husbandOrder: string;
  handleHusbandOrderChange: (
    event: React.ChangeEvent<HTMLSelectElement>
  ) => void;
}

const HusbandInfo: React.FC<HusbandInfoProps> = ({
  husbandOrder,
  handleHusbandOrderChange,
}) => {
  return (
    <div>
      <p className="mt-20 mb-5 text-md text-center">
        신랑 측 정보를 작성해 주세요.
      </p>
      <div className="flex justify-between gap-5 mb-3">
        <input
          id="husband-last-name"
          name="husband-last-name"
          type="text"
          placeholder="신랑 성"
          className="px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white"
          required
        />
        <input
          id="husband-name"
          name="husband-name"
          type="text"
          placeholder="신랑 이름"
          className="px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white"
          required
        />
      </div>

      <div className="flex mb-3">
        <select
          id="husband-order"
          name="husband-order"
          value={husbandOrder}
          onChange={handleHusbandOrderChange}
          className={`w-full px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white ${
            husbandOrder === "" ? "text-gray-400" : "text-black"
          }`}
        >
          <option value="" disabled hidden>
            신랑 서열 (장남 / 차남 / 아들)
          </option>
          <option value="first">장남</option>
          <option value="second">차남</option>
          <option value="son">아들</option>
        </select>
      </div>

      <div className="flex justify-between gap-5 mb-3">
        <input
          id="husband-father-last-name"
          name="husband-father-last-name"
          type="text"
          placeholder="신랑 아버님 성"
          className="px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white"
          required
        />
        <input
          id="husband-father-name"
          name="husband-father-name"
          type="text"
          placeholder="신랑 아버님 이름"
          className="px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white"
          required
        />
      </div>
      <div className="flex justify-between gap-5 mb-3">
        <input
          id="husband-mother-last-name"
          name="husband-mother-last-name"
          type="text"
          placeholder="신랑 어머님 성"
          className="px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white"
          required
        />
        <input
          id="husband-mother-name"
          name="husband-mother-name"
          type="text"
          placeholder="신랑 어머님 이름"
          className="px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white"
          required
        />
      </div>
    </div>
  );
};

export default HusbandInfo;
