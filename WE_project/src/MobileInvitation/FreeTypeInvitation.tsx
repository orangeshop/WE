import React, { useState } from "react";
import { SketchPicker, ColorResult } from "react-color";
import Draggable from "react-draggable";

interface ImageProps {
  src: string;
  width: number;
  height: number;
}

interface TextProps {
  text: string;
  color: string;
  fontSize: number;
}

const FreeTypeInvitation: React.FC = () => {
  const [selectedImage, setSelectedImage] = useState<ImageProps | null>(null);
  const [textProps, setTextProps] = useState<TextProps>({
    text: "Edit me!",
    color: "#000",
    fontSize: 20,
  });
  const [isTextEditing, setIsTextEditing] = useState<boolean>(false);
  const [currentColor, setCurrentColor] = useState<string>("#000");
  const [imagePosition, setImagePosition] = useState<{ x: number; y: number }>({
    x: 0,
    y: 0,
  });
  const [textPosition, setTextPosition] = useState<{ x: number; y: number }>({
    x: 100,
    y: 100,
  });

  const handleImageUpload = (event: React.ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = () => {
        setSelectedImage({
          src: reader.result as string,
          width: 300,
          height: 300,
        });
      };
      reader.readAsDataURL(file);
    }
  };

  const handleTextChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setTextProps({ ...textProps, text: event.target.value });
  };

  const handleColorChange = (color: ColorResult) => {
    setCurrentColor(color.hex);
    setTextProps({ ...textProps, color: color.hex });
  };

  const handleFontSizeChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setTextProps({ ...textProps, fontSize: parseInt(event.target.value, 10) });
  };

  return (
    <div style={{ height: "100vh", display: "flex", flexDirection: "column" }}>
      <div
        style={{
          height: "50px",
          backgroundColor: "#333",
          color: "#fff",
          display: "flex",
          alignItems: "center",
          padding: "0 20px",
        }}
      >
        <div style={{ marginRight: "20px", fontSize: "18px" }}>
          <strong>DesignTool</strong>
        </div>
        <div style={{ display: "flex", gap: "20px" }}>
          <span>File</span>
          <span>Edit</span>
          <span>View</span>
          <span>Help</span>
        </div>
      </div>

      <div style={{ display: "flex", flexGrow: 1 }}>
        <div
          style={{
            width: "250px",
            backgroundColor: "#f4f4f4",
            padding: "20px",
          }}
        >
          <div>
            <h3>Tools</h3>
            <div>
              <input
                type="file"
                accept="image/*"
                onChange={handleImageUpload}
              />
            </div>
            <div style={{ marginTop: "20px" }}>
              <SketchPicker
                color={currentColor}
                onChangeComplete={handleColorChange}
              />
            </div>
            <div style={{ marginTop: "20px" }}>
              <input
                type="text"
                value={textProps.text}
                onChange={handleTextChange}
                placeholder="Enter text here"
                style={{ padding: "10px", fontSize: "16px", width: "100%" }}
              />
              <div style={{ marginTop: "10px" }}>
                <label>Font Size:</label>
                <input
                  type="number"
                  value={textProps.fontSize}
                  onChange={handleFontSizeChange}
                  style={{ marginLeft: "10px", width: "60px" }}
                />
              </div>
            </div>
          </div>
        </div>

        <div
          style={{
            flexGrow: 1,
            display: "flex",
            justifyContent: "center",
            alignItems: "center",
          }}
        >
          <div
            style={{
              width: "800px",
              height: "600px",
              backgroundColor: "#f0f0f0",
              position: "relative",
              border: "2px solid black",
            }}
          >
            {selectedImage && (
              <Draggable
                bounds="parent"
                position={imagePosition}
                onStop={(e, data) => setImagePosition({ x: data.x, y: data.y })}
              >
                <img
                  src={selectedImage.src}
                  alt="Uploaded"
                  style={{
                    width: selectedImage.width,
                    height: selectedImage.height,
                    position: "absolute",
                  }}
                />
              </Draggable>
            )}

            <Draggable
              bounds="parent"
              position={textPosition}
              onStop={(e, data) => setTextPosition({ x: data.x, y: data.y })}
            >
              <div
                style={{
                  color: textProps.color,
                  fontSize: textProps.fontSize,
                  cursor: "pointer",
                  position: "absolute",
                  top: textPosition.y,
                  left: textPosition.x,
                }}
                onClick={() => setIsTextEditing(true)}
              >
                {isTextEditing ? (
                  <input
                    type="text"
                    value={textProps.text}
                    onChange={handleTextChange}
                    style={{
                      fontSize: textProps.fontSize,
                      color: textProps.color,
                      padding: "5px",
                      border: "none",
                      outline: "none",
                    }}
                    onBlur={() => setIsTextEditing(false)}
                  />
                ) : (
                  textProps.text
                )}
              </div>
            </Draggable>
          </div>
        </div>
      </div>
    </div>
  );
};

export default FreeTypeInvitation;
