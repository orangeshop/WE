import { useState, forwardRef, useImperativeHandle, useEffect } from "react";
import {
  BrideInfoDto,
  BirthOrder,
  inputBrideInfo,
} from "../../apis/api/brideinfo";
import { useParams } from "react-router-dom";

interface BrideInfoProps {
  initialBrideInfo: {
    lastName: string;
    firstName: string;
    birthOrder: "" | BirthOrder;
    fatherLastName: string;
    fatherFirstName: string;
    motherLastName: string;
    motherFirstName: string;
  };
}

const BrideInfo = forwardRef((props: BrideInfoProps, ref) => {
  const [lastName, setBrideLastName] = useState(
    props.initialBrideInfo.lastName
  );
  const [firstName, setBrideFirstName] = useState(
    props.initialBrideInfo.firstName
  );
  const [birthOrder, setBrideBirthOrder] = useState<BirthOrder | "">(
    props.initialBrideInfo.birthOrder
  );
  const [fatherLastName, setBrideFatherLastName] = useState(
    props.initialBrideInfo.fatherLastName
  );
  const [fatherFirstName, setBrideFatherFirstName] = useState(
    props.initialBrideInfo.fatherFirstName
  );
  const [motherLastName, setBrideMotherLastName] = useState(
    props.initialBrideInfo.motherLastName
  );
  const [motherFirstName, setBrideMotherFirstName] = useState(
    props.initialBrideInfo.motherFirstName
  );
  const { invitationId } = useParams();

  useEffect(() => {
    setBrideLastName(props.initialBrideInfo.lastName);
    setBrideFirstName(props.initialBrideInfo.firstName);
    setBrideBirthOrder(props.initialBrideInfo.birthOrder);
    setBrideFatherLastName(props.initialBrideInfo.fatherLastName);
    setBrideFatherFirstName(props.initialBrideInfo.fatherFirstName);
    setBrideMotherLastName(props.initialBrideInfo.motherLastName);
    setBrideMotherFirstName(props.initialBrideInfo.motherFirstName);
  }, [props.initialBrideInfo]);
  const handleSubmit = async () => {
    if (!birthOrder) {
      alert("신부 서열을 선택해 주세요.");
      return;
    }

    const dto: BrideInfoDto = {
      lastName,
      firstName,
      birthOrder: birthOrder as BirthOrder,
      fatherLastName,
      fatherFirstName,
      motherLastName,
      motherFirstName,
    };

    try {
      if (dto && invitationId) {
        await inputBrideInfo(invitationId, dto);
      }
    } catch (error) {
      console.error("신부 정보 업로드 중 오류 발생:", error);
      alert("신부 정보 업로드 중 오류가 발생했습니다.");
    }
  };

  useImperativeHandle(ref, () => ({
    submit: handleSubmit,
  }));
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
          value={lastName}
          onChange={(e) => setBrideLastName(e.target.value)}
          className="px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white"
          required
        />
        <input
          id="bride-name"
          name="bride-name"
          type="text"
          placeholder="신부 이름"
          value={firstName}
          onChange={(e) => setBrideFirstName(e.target.value)}
          className="px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white"
          required
        />
      </div>

      <div className="flex mb-3">
        <select
          id="bride-order"
          name="bride-order"
          value={birthOrder}
          onChange={(e) => setBrideBirthOrder(e.target.value as BirthOrder)}
          className={`w-full px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white ${
            birthOrder === "" ? "text-gray-400" : "text-black"
          }`}
        >
          <option value="" disabled hidden>
            신부 서열 (장녀 / 차녀 / 딸)
          </option>
          <option value="FIRST">장녀</option>
          <option value="SECOND">차녀</option>
          <option value="OTHER">딸</option>
        </select>
      </div>

      <div className="flex justify-between gap-5 mb-3">
        <input
          id="bride-father-last-name"
          name="bride-father-last-name"
          type="text"
          placeholder="신부 아버님 성"
          value={fatherLastName}
          onChange={(e) => setBrideFatherLastName(e.target.value)}
          className="px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white"
          required
        />
        <input
          id="bride-father-name"
          name="bride-father-name"
          type="text"
          placeholder="신부 아버님 이름"
          value={fatherFirstName}
          onChange={(e) => setBrideFatherFirstName(e.target.value)}
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
          value={motherLastName}
          onChange={(e) => setBrideMotherLastName(e.target.value)}
          className="px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white"
          required
        />
        <input
          id="bride-mother-name"
          name="bride-mother-name"
          type="text"
          placeholder="신부 어머님 이름"
          value={motherFirstName}
          onChange={(e) => setBrideMotherFirstName(e.target.value)}
          className="px-2 py-2 border text-md border-gray-400 focus:border-gray-400 text-center bg-white"
          required
        />
      </div>
    </div>
  );
});

export default BrideInfo;
