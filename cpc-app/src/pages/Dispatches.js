import React from 'react';
import { useEffect, useState } from 'react';

function Dispatches() {
  const [data, setData] = useState([]);

  const fetchData = () => {
    fetch('http://localhost:8194/dispatch', {
      method: 'get',
    })
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

  const postDispatch = (d) => {
    console.log(d);
    fetch('http://localhost:8194/dispatch', {
      method: 'POST',
      mode: 'cors',
      cache: 'no-cache',
      credentials: 'same-origin',

      headers: {
        'content-type': 'application/json',
      },
      body: JSON.stringify(d),
    })
      .then(() => {
        console.log('refreshing page');
        fetchData();
      })
      .catch(function(erro) {
        console.log(erro);
      });
  };

  return (
    <div>
      <h2>Dispatch List</h2>
      {data.length > 0 && (
        <ul>
          {data.map((d) => (
            <li key={d._id}>
              {d.stationId} | {d.quantity} |{' '}
              <button onClick={() => postDispatch(d)}>Dispatch Order</button>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default Dispatches;
