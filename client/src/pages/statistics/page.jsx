import { useParams } from 'react-router-dom';
import React, { useEffect, useState } from 'react';
import Styles from './page.module.scss';

export default function Statistics() {
    const { departmentId } = useParams();
    const [stocks, setStocks] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchStocks = async () => {
            try {
                const response = await fetch(`http://localhost:8082/api/hospital_stocks/stocks/department/${departmentId}/medicines`, {
                    method: 'GET'
                });
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                const data = await response.json();
                setStocks(data);
                setLoading(false);
            } catch (error) {
                setError(error);
                setLoading(false);
            }
        };

        fetchStocks();
    }, [departmentId]);

    if (loading) {
        return <div className={Styles.container}>Loading...</div>;
    }

    if (error) {
        return <div className={Styles.container}>Error: {error.message}</div>;
    }

    return (
        <div className={Styles.container}>
            <div className={Styles.page__title}>Statistics for Department {departmentId}</div>
            <div className={Styles.stocks}>
                <div className={Styles.page__stocks}>
                    <h3 className={Styles.page__stocks__title}>Stocks</h3>
                    <div className={Styles.page__stocks__list}>
                        {Object.entries(stocks).map(([medicine, quantity], index) => (
                            <div key={index} className={Styles.page__stocks__element}>
                                {medicine}: {quantity}
                            </div>
                        ))}
                    </div>
                </div>
            </div>

            <div className={Styles.stocks}>
                <div className={Styles.page__stocks}>
                    <h3 className={Styles.page__stocks__title}>Most Consumed Medicines</h3>
                    <div className={Styles.page__stocks__list}>

                    </div>
                </div>
            </div>
        </div>
    );
}
