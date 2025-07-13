
import React, { useState, useEffect } from 'react';
import axios from 'axios';

function FileUpload() {
  const [file, setFile] = useState(null);

  const [fileList, setFileList] = useState([]);

 
  const fetchFiles = async () => {
    try {

      const response = await axios.get('/api/files'); 
      setFileList(response.data);

    } catch (error) {
      console.error("Error fetching files:", error);
    }
  };

  useEffect(() => {
    fetchFiles();

  }, []);

  const handleUpload = async () => {
    if (!file) return alert("Please select a file!");

    const formData = new FormData();
    formData.append("file", file);
    formData.append("userId", 3); 

    try {
      await axios.post("/api/files/upload", formData);
      alert("File uploaded successfully!");
      fetchFiles(); 
    } catch (error) {
      console.error("Upload failed:", error);
      alert("Upload failed.");
    }
  };

  const handleDownload = async (fileId, fileName) => {
    try {
      const response = await axios.get(`/api/files/download/${fileId}`, {
        responseType: 'blob',
      });

      // Create a download link and click it
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement('a');
      link.href = url;
      link.setAttribute('download', fileName);
      document.body.appendChild(link);
      link.click();
      link.remove();
    } catch (error) {
      console.error("Download failed:", error);
      alert("Download failed.");
    }
  };

  const handleDelete = async (fileId) => {
    try {
      await axios.delete(`/api/files/delete/${fileId}`);
      alert("File deleted successfully!");
      fetchFiles(); 
    } catch (error) {
      console.error("Delete failed:", error);
      alert("Delete failed.");
    }
  };

  return (
    <div style={{ padding: "20px" }}>
      <h2>File Upload</h2>
      <input type="file" onChange={e => setFile(e.target.files[0])} />
      <button onClick={handleUpload}>Upload</button>

      <hr />
      <h3>Uploaded Files</h3>
      <ul>
        {fileList.map(file => (
          <li key={file.id}>
            {file.name} ({file.size} bytes)
            <button onClick={() => handleDownload(file.id, file.name)} style={{ marginLeft: "10px" }}>Download</button>
            <button onClick={() => handleDelete(file.id)} style={{ marginLeft: "10px", color: 'red' }}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default FileUpload;
