import React from 'react';
import { useEffect, useState } from 'react';

function OrderStatus() {
  const [data, setData] = useState(); //response
  const [id, setId] = useState(''); //id

  const checkStatus = (event) => {
    event.preventDefault();
    fetch(`http://localhost:8191/services/orders/${id}`, {
      method: 'get',
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
      <h2>View All Orders</h2>
      <form onSubmit={checkStatus}>
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
        <input type="submit" value="Check Status" />
      </form>
      <div>{data}</div>
    </>
  );
}

export default OrderStatus;
