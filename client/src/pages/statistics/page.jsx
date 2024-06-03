import { useParams } from 'react-router-dom';
import React, { useEffect, useState } from 'react';
import Styles from './page.module.scss';

export default function Statistics() {
    const { departmentId } = useParams();
    const [stocks, setStocks] = useState([]);
    const [mostConsumedMedicines, setMostConsumedMedicines] = useState([]);
    const [loading, setLoading] = useState(true);
    const [predictions, setPredictions] = useState([]);
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

        const fetchMostConsumedMedicines = async () => {
            try {
                const response = await fetch(`http://localhost:8082/api/hospital_stocks/consumptions/department/${departmentId}/most-consumed`, {
                    method: 'GET'
                });
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                const data = await response.json();
                setMostConsumedMedicines(data);
            } catch (error) {
                setError(error);
            } finally {
                setLoading(false);
            }
        };

        const fetchPredictions = async () => {
            try {
                const response = await fetch(`http://localhost:8082/api/hospital_stocks/consumptions/department/${departmentId}/running-out-soon`, {
                    method: 'GET'
                });
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                const data = await response.json();
                setPredictions(data);
            } catch (error) {
                setError(error);
            }
        };

        fetchStocks();
        fetchMostConsumedMedicines();
        fetchPredictions();
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
                                <span className={Styles.highlight}>{medicine}: </span>{quantity + "units"}
                            </div>
                        ))}
                    </div>
                </div>
            </div>

            <div className={Styles.stocks}>
                <div className={Styles.page__stocks}>
                    <h3 className={Styles.page__stocks__title}>Most Consumed Medicines</h3>
                    <div className={Styles.page__stocks__list}>
                        {Object.entries(mostConsumedMedicines).map(([medicine, quantity], index) => (
                            <div key={index} className={Styles.page__stocks__element}>
                                <span className={Styles.highlight}>{medicine}: </span>{quantity + "units"}
                            </div>
                        ))}
                    </div>
                </div>
            </div>

            <div className={Styles.stocks}>
                <div className={Styles.page__stocks}>
                    <h3 className={Styles.page__stocks__title}>Medicines Predicted to Run Out Soon</h3>
                    <div className={Styles.page__stocks__list}>
                        {Object.entries(predictions).map(([medicine, days], index) => (
                            <div key={index} className={Styles.page__stocks__element}>
                                <span className={Styles.highlight}>{medicine}: </span>will run out in {days} days
                            </div>
                        ))}
                    </div>
                </div>
            </div>
        </div>
    );
}
