// BrideInfo.tsx
import React from "react";

interface BrideInfoProps {
  brideOrder: string;
  handleBrideOrderChange: (event: React.ChangeEvent<HTMLSelectElement>) => void;
}

const BrideInfo: React.FC<BrideInfoProps> = ({
  brideOrder,
  handleBrideOrderChange,
}) => {
  return (
    <div>
      <p className="mt-20 mb-5 text-md text-center font-semibold">
        신부 측 정보를 작성해 주세요.
      </p>
      <div className="flex justify-between gap-5 mb-3">
        <input
          id="bride-last-name"
          name="bride-last-name"
          type="text"
          placeholder="신부 성"
          className="px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white"
          required
        />
        <input
          id="bride-name"
          name="bride-name"
          type="text"
          placeholder="신부 이름"
          className="px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white"
          required
        />
      </div>

      <div className="flex mb-3">
        <select
          id="bride-order"
          name="bride-order"
          value={brideOrder}
          onChange={handleBrideOrderChange}
          className={`w-full px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white ${
            brideOrder === "" ? "text-gray-400" : "text-black"
          }`}
        >
          <option value="" disabled hidden>
            신부 서열 (장녀 / 차녀 / 딸)
          </option>
          <option value="first">장녀</option>
          <option value="second">차녀</option>
          <option value="son">딸</option>
        </select>
      </div>

      <div className="flex justify-between gap-5 mb-3">
        <input
          id="bride-father-last-name"
          name="bride-father-last-name"
          type="text"
          placeholder="신부 아버님 성"
          className="px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white"
          required
        />
        <input
          id="bride-father-name"
          name="bride-father-name"
          type="text"
          placeholder="신부 아버님 이름"
          className="px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white"
          required
        />
      </div>
      <div className="flex justify-between gap-5 mb-3">
        <input
          id="bride-mother-last-name"
          name="bride-mother-last-name"
          type="text"
          placeholder="신부 어머님 성"
          className="px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white"
          required
        />
        <input
          id="bride-mother-name"
          name="bride-mother-name"
          type="text"
          placeholder="신부 어머님 이름"
          className="px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white"
          required
        />
      </div>
    </div>
  );
};

export default BrideInfo;
