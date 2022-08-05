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

  const f = (d) => {
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
    }).catch(function(erro) {
      console.log(erro);
    });
  };

  // function postDispatch(id) {
  //   //post message to dispatch service to, be sent to order service (to change state)
  //   console.log('Inside postDispatch ' + id);

  //   const res = fetch('http://localhost:8194/dispatch', {
  //     method: 'POST',
  //     headers: {
  //       'Content-Type': 'application/json',
  //     },
  //     body: id,
  //   })
  //     .then((response) => response.json())
  //     .then((responseJson) => {
  //       return responseJson.movies;
  //     })
  //     .catch((error) => {
  //       console.error(error);
  //     });
  // }

  return (
    <div>
      <h2>Dispatch List</h2>
      {data.length > 0 && (
        <ul>
          {data.map((d) => (
            <li key={d._id}>
              {d.stationId} | {d.quantity} |{' '}
              <button onClick={() => f(d)}>Dispatch Order</button>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default Dispatches;
