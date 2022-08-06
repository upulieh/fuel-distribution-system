import { useEffect, useState } from 'react';
import React from 'react';

function OrderReceival() {
  const [data, setData] = useState(); //response
  const [id, setId] = useState(''); //id

  const confirmReceival = (event) => {
    event.preventDefault();
    fetch(`http://localhost:8191/services/orders/${id}`, {
      method: 'post',
    })
      .then((response) => {
        return response.text();
      })
      .then((data) => {
        setData(data);
      });
  };

  useEffect(() => {
    // checkStatus();
  }, []);

  return (
    <>
      <h2>Confirm Order Receival</h2>
      <form onSubmit={confirmReceival}>
        <label>
          Order Id:
          <input
            type="text"
            value={id}
            onChange={(e) => {
              setId(e.target.value);
            }}
          />
        </label>
        <input type="submit" value="Received" />
      </form>
      <div>{data}</div>
    </>
  );
}

export default OrderReceival;
