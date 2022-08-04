import React from 'react';
import { useEffect, useState } from 'react';

const Dispatches = () => {
  const [data, setData] = useState([]);

  const fetchData = () => {
    fetch('http://localhost:8194/dispatch')
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        setData(data);
      });
  };

  useEffect(() => {
    fetchData();
  }, []);

  return (
    <div>
      <h2>Dispatch List</h2>
      {data.length > 0 && (
        <ul>
          {data.map((d) => (
            <li key={d._id}>
              {d.stationId} - {d.quantity}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default Dispatches;
