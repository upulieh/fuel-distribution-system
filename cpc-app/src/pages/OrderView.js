import React from 'react';
import { useEffect, useState } from 'react';

function OrderView() {
  const [data, setData] = useState([]);

  const fetchData = () => {
    fetch('http://localhost:8191/services/orders', {
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

  return (
    <div>
      <h2>View All Orders</h2>
      {data.length > 0 && (
        <ul>
          {data.map((d) => (
            <li key={d.id}>
              <p>
                id {d.id} | stationId {d.stationId} | fuelType {d.fuelType} |
                quantity {d.quantity} | reserved{' '}
                {d.reserved === true ? 'true' : 'false'} | reservedTime{' '}
                {d.reservedTime == null ? 'N/A' : d.reservedTime} | allocated{' '}
                {d.allocated === true ? 'true' : 'false'} | allocatedTime{' '}
                {d.allocatedTime == null ? 'N/A' : d.allocatedTime} | scheduled{' '}
                {d.scheduled === true ? 'true' : 'false'} | scheduledTime{' '}
                {d.scheduledTime == null ? 'N/A' : d.scheduledTime} | dispatched{' '}
                {d.dispatched === true ? 'true' : 'false'} | dispatchedTime{' '}
                {d.dispatchedTime == null ? 'N/A' : d.dispatchedTime} |
                delivered {d.delivered === true ? 'true' : 'false'} |
                deliveredTime{' '}
                {d.deliveredTime == null ? 'N/A' : d.deliveredTime}
              </p>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default OrderView;
