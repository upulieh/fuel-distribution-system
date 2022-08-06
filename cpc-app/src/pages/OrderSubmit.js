import React from 'react';
import { useEffect, useState } from 'react';

const OrderSubmit = () => {
  const [data, setData] = useState({}); //response
  const [stationId, setStationId] = useState(''); //id
  const [fuelType, setFuelType] = useState('OCTANE92'); //fuelType
  const [quantity, setQuantity] = useState('L3_300'); //quantity

  const checkStatus = (event) => {
    const jsonData = {
      stationId: stationId,
      fuelType: fuelType,
      quantity: quantity,
    };
    event.preventDefault();
    fetch(`http://localhost:8191/services/orders`, {
      method: 'POST',
      mode: 'cors',
      cache: 'no-cache',
      credentials: 'same-origin',
      headers: {
        'Content-Type': 'application/json;charset=UTF-8',
      },
      body: JSON.stringify(jsonData),
    })
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        setData(data);
      });
  };

  useEffect(() => {
    // checkStatus();
  }, []);

  const refreshPage = () => {
    window.location.reload();
  };

  return (
    <>
      <h2>Create a new Order</h2>
      <form onSubmit={checkStatus}>
        <label>
          Station Id:
          <input
            type="text"
            value={stationId}
            onChange={(e) => {
              setStationId(e.target.value);
            }}
          />
          Fuel Type:
          <select
            value={fuelType}
            onChange={(e) => {
              setFuelType(e.target.value);
            }}
          >
            <option value="OCTANE92">OCTANE92</option>
            <option value="OCTANE95">OCTANE95</option>
            <option value="REGULAR_DIESEL">REGULAR_DIESEL</option>
            <option value="SUPER_DIESEL">SUPER_DIESEL</option>
          </select>
          Quantity:
          <select
            value={quantity}
            onChange={(e) => {
              setQuantity(e.target.value);
            }}
          >
            <option value="L3_300">L3_300</option>
            <option value="L6_600">L6_600</option>
            <option value="L13_200">L13_200</option>
          </select>
        </label>
        <input type="submit" value="Make Order" />
      </form>
      {Object.keys(data).length !== 0 && (
        <div>
          <p>
            Order submission was succesful. Please remember the Order Id for
            future reference.
          </p>
          <p>OrderId : {data.id}</p>
          <p>StationId : {data.stationId}</p>
          <p>FuelType : {data.fuelType}</p>
          <p>Quantity : {data.quantity}</p>

          <button onClick={refreshPage}>Create More Orders</button>
        </div>
      )}
    </>
  );
};

export default OrderSubmit;
