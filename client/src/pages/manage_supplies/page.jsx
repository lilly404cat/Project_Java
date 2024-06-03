import React, { useState } from 'react';
import Styles from '../departments/page.module.scss';

export default function Manager() {
    const [stockData, setStockData] = useState({
        name: '',
        description: '',
        units: '',
        pricePerUnit: ''
    });

    const [contactData, setContactData] = useState({
        name: '',
        contactInfo: ''
    });

    const [quantityData, setQuantityData] = useState({
        quantity: ''
    });

    const handleInputChange = (e, setData) => {
        const { name, value } = e.target;
        setData(prevState => ({
            ...prevState,
            [name]: value
        }));
    };

    const handleSubmit = async (e, data, endpoint) => {
        e.preventDefault();
        try {
            const response = await fetch(endpoint, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            });
            if (response.ok) {
                alert('Data submitted successfully');
            } else {
                alert('Failed to submit data');
            }
        } catch (error) {
            console.error('Error:', error);
            alert('An error occurred');
        }
    };

    const handleAdd = async () => {
        await handleSubmit(null, stockData, 'http://localhost:8082/api/hospital_stocks/medicines');
        await handleSubmit(null, contactData, 'http://localhost:8082/api/hospital_stocks/suppliers');
        await handleSubmit(null, quantityData, 'http://localhost:8082/api/hospital_stocks/stocks');
    };

    return (
        <div className={Styles.container}>
            <div className={Styles.page__title}>
                Add, Delete and Update Stocks!
            </div>
            <section className={Styles.add_section}>
                <form onSubmit={(e) => handleSubmit(e, stockData, '/api/addStock')}>

                    <input
                        type="text"
                        name="name"
                        placeholder="Name"
                        value={stockData.name}
                        onChange={(e) => handleInputChange(e, setStockData)}
                    />
                    <input
                        type="text"
                        name="description"
                        placeholder="Description"
                        value={stockData.description}
                        onChange={(e) => handleInputChange(e, setStockData)}
                    />
                    <input
                        type="number"
                        name="units"
                        placeholder="Units"
                        value={stockData.units}
                        onChange={(e) => handleInputChange(e, setStockData)}
                    />
                    <input
                        type="number"
                        name="pricePerUnit"
                        placeholder="Price per Unit"
                        value={stockData.pricePerUnit}
                        onChange={(e) => handleInputChange(e, setStockData)}
                    />
                    <button type="submit">Confirm</button>
                </form>

                <form onSubmit={(e) => handleSubmit(e, contactData, '/api/addContact')}>
                    <input
                        type="text"
                        name="name"
                        placeholder="Name"
                        value={contactData.name}
                        onChange={(e) => handleInputChange(e, setContactData)}
                    />
                    <input
                        type="text"
                        name="contactInfo"
                        placeholder="Contact Info"
                        value={contactData.contactInfo}
                        onChange={(e) => handleInputChange(e, setContactData)}
                    />
                    <button type="submit">Confirm</button>
                </form>

                <form onSubmit={(e) => handleSubmit(e, quantityData, '/api/addQuantity')}>
                    <input
                        type="number"
                        name="quantity"
                        placeholder="Quantity"
                        value={quantityData.quantity}
                        onChange={(e) => handleInputChange(e, setQuantityData)}
                    />
                    <button type="submit">Confirm</button>
                </form>

                <button onClick={handleAdd}>Add</button>
            </section>
        </div>
    );
}
