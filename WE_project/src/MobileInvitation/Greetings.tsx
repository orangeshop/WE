import React from "react";

interface Greetings {
  value: string;
  onChange: (event: React.ChangeEvent<HTMLTextAreaElement>) => void;
}

const GreetingsSection: React.FC<Greetings> = ({ value, onChange }) => {
  return (
    <div className="w-full mt-20 text-center ">
      <p className="font-semibold">인사말을 작성해 주세요.</p>
      <textarea
        id="greetings"
        name="greetings"
        placeholder="인사말 작성"
        value={value}
        onChange={onChange}
        className="mt-5 custom-textarea px-4 py-2 border border-gray-400 focus:border-gray-400 bg-white text-gray-700"
        required
      />
      <div className="mt-20 border border-gray-200"></div>
    </div>
  );
};

export default GreetingsSection;
