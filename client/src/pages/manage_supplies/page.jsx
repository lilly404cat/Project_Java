import React, { useState, useRef } from 'react';
import Styles from '../departments/page.module.scss';

export default function Manager() {
    const [stockData, setStockData] = useState({
        name: '',
        description: '',
        unit: '',
        pricePerUnit: ''
    });

    const [contactData, setContactData] = useState({
        name: '',
        contactInfo: ''
    });

    const [quantityData, setQuantityData] = useState({
        quantity: '',
        departmentName: '' // Make sure this matches the backend parameter name
    });

    const [departmentData, setDepartmentData] = useState({
        name: ''
    });

    const [departmentDataToAdd, setDepartmentDataToAdd] = useState({
        name: ''
    });

    const [drugData, setDrugData] = useState({
        name: ''
    });

    const [supplierData, setSupplierData] = useState({
        name: ''
    });

    const [error, setError] = useState('');

    const stockNameRef = useRef();
    const stockDescriptionRef = useRef();
    const stockUnitsRef = useRef();
    const stockPricePerUnitRef = useRef();

    const contactNameRef = useRef();
    const contactInfoRef = useRef();

    const quantityRef = useRef();
    const departmentRef = useRef();

    const departmentNameToAddRef = useRef();
    const departmentNameRef = useRef();
    const drugNameRef = useRef();
    const supplierNameRef = useRef();

    const saveStockData = () => {
        setStockData({
            name: stockNameRef.current.value,
            description: stockDescriptionRef.current.value,
            unit: stockUnitsRef.current.value,
            pricePerUnit: stockPricePerUnitRef.current.value
        });
    };

    const saveContactData = () => {
        setContactData({
            name: contactNameRef.current.value,
            contactInfo: contactInfoRef.current.value
        });
    };

    const saveQuantityData = () => {
        setQuantityData({
            quantity: quantityRef.current.value,
            departmentName: departmentRef.current.value // Make sure this matches the backend parameter name
        });
    };

    const handleDepartmentNameChange = (e) => {
        setDepartmentData({
            ...departmentData,
            name: e.target.value
        });
    };

    const handleDepartmentNameChangeToAdd = (e) => {
        setDepartmentDataToAdd({
            ...departmentDataToAdd,
            name: e.target.value
        });
    };

    const handleDrugNameChange = (e) => {
        setDrugData({
            ...drugData,
            name: e.target.value
        });
    };

    const handleSupplierNameChange = (e) => {
        setSupplierData({
            ...supplierData,
            name: e.target.value
        });
    };

    const handleDelete = async(type, endpoint) => {
        let data;
        switch (type) {
            case "department":
                data = departmentData;
                break;
            case "supplier":
                data = drugData;
                break;
            case "drug":
                data = supplierData;
                break;
            default:
                return;
        }
        try {
            const url = endpoint + data.name;
            const response = await fetch(url, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            console.log(url);
            if (response.ok) {
                alert('Department deleted successfully');

            } else {
                const errorText = await response.text();
                console.error('Error response:', errorText);
                alert('Failed to add department: ' + errorText);
            }
        } catch (error) {
            console.error('Error:', error);
            alert('An error occurred');
        }
    }

    const handleAddDepartment = async () => {
        try {
            const response = await fetch('http://localhost:8082/api/hospital_stocks/departments', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(departmentDataToAdd)
            });
            if (response.ok) {
                alert('Department added successfully');
                // Clear the department name input after successful submission
                setDepartmentDataToAdd({
                    name: ''
                });
            } else {
                const errorText = await response.text();
                console.error('Error response:', errorText);
                alert('Failed to add department: ' + errorText);
            }
        } catch (error) {
            console.error('Error:', error);
            alert('An error occurred');
        }
    };

    const handleSubmit = async (data, endpoint, contentType = 'application/json') => {
        const options = {
            method: 'POST',
            headers: {
                'Content-Type': contentType,
            },
        };

        if (contentType === 'application/x-www-form-urlencoded') {
            let encodedData = [];
            for (let property in data) {
                let encodedKey = encodeURIComponent(property);
                let encodedValue = encodeURIComponent(data[property]);
                encodedData.push(encodedKey + "=" + encodedValue);
            }
            encodedData = encodedData.join("&");
            options.body = encodedData;
        } else {
            options.body = JSON.stringify(data);
        }

        try {
            console.log('Submitting data to:', endpoint);
            console.log('Data:', data);
            const response = await fetch(endpoint, options);
            if (response.ok) {
                alert('Data submitted successfully');
            } else {
                const errorText = await response.text();
                console.error('Error response:', errorText);
                alert('Failed to submit data: ' + errorText);
            }
        } catch (error) {
            console.error('Error:', error);
            alert('An error occurred');
        }
    };

    const handleAdd = async () => {
        // saveStockData();
        // saveContactData();
        // saveQuantityData();

        await handleSubmit(stockData, 'http://localhost:8082/api/hospital_stocks/medicines');
        await handleSubmit(contactData, 'http://localhost:8082/api/hospital_stocks/suppliers');
        await handleSubmit(quantityData, 'http://localhost:8082/api/hospital_stocks/stocks', 'application/x-www-form-urlencoded');
    };

    return (
        <div className={Styles.container}>
            <div className={Styles.page__title}>
                Add, Delete and Update Stocks!
            </div>
            <section className={Styles.add_section}>
                <div className={Styles.page__section__title}>
                    Add Drugs!
                </div>
                <form className={Styles.page__form}>
                    <label className={Styles.page__form__label} htmlFor="name">Enter the name of drugs</label>
                    <input
                        className={Styles.page__form__input}
                        type="text"
                        name="name"
                        placeholder="Name"
                        ref={stockNameRef}
                    />
                    <label className={Styles.page__form__label} htmlFor="description">Enter the description of
                        drugs</label>
                    <input
                        className={Styles.page__form__input}
                        type="text"
                        name="description"
                        placeholder="Description"
                        ref={stockDescriptionRef}
                    />
                    <label className={Styles.page__form__label} htmlFor="units">Enter the units of drugs</label>
                    <input
                        className={Styles.page__form__input}
                        type="text"
                        name="units"
                        placeholder="Units"
                        ref={stockUnitsRef}
                    />
                    <label className={Styles.page__form__label} htmlFor="pricePerUnit">Enter the price per unit of
                        drugs</label>
                    <input
                        className={Styles.page__form__input}
                        type="number"
                        name="pricePerUnit"
                        placeholder="Price per Unit"
                        ref={stockPricePerUnitRef}
                    />
                    <button type="button" onClick={saveStockData}>Confirm</button>
                </form>

                <form className={Styles.page__form}>
                    <label className={Styles.page__form__label} htmlFor="name">Enter the name of supplier</label>
                    <input
                        className={Styles.page__form__input}
                        type="text"
                        name="name"
                        placeholder="Name"
                        ref={contactNameRef}
                    />
                    <label className={Styles.page__form__label} htmlFor="contact">Enter the contact info of
                        supplier</label>
                    <input
                        className={Styles.page__form__input}
                        type="text"
                        name="contact"
                        placeholder="Contact Info"
                        ref={contactInfoRef}
                    />
                    <button type="button" onClick={saveContactData}>Confirm</button>
                </form>

                <form className={Styles.page__form}>
                    <label className={Styles.page__form__label} htmlFor="quantity">Enter the quantity of drugs</label>
                    <input
                        className={Styles.page__form__input}
                        type="number"
                        name="quantity"
                        placeholder="Quantity"
                        ref={quantityRef}
                    />
                    <label className={Styles.page__form__label} htmlFor="department">Enter the department</label>
                    <input
                        className={Styles.page__form__input}
                        type="text"
                        name="department"
                        placeholder="Department"
                        ref={departmentRef}
                    />
                    <button type="button" onClick={saveQuantityData}>Confirm</button>
                </form>

                <button onClick={handleAdd}>Add</button>
            </section>

            <section className={Styles.add__section__department}>
                <div className={Styles.page__section__title}>
                    Add Departments!
                </div>
                <form className={Styles.page__form}>
                    <div className={Styles.form__group}>
                        <label className={Styles.page__form__label} htmlFor="departmentName">Enter the name of the
                            department</label>
                        <input
                            className={Styles.page__form__input}
                            type="text"
                            name="departmentName"
                            placeholder="Department Name"
                            value={departmentDataToAdd.name}
                            onChange={handleDepartmentNameChangeToAdd}
                            ref={departmentNameToAddRef}
                        />
                    </div>
                    <button type="button" onClick={handleAddDepartment}>Add Department</button>
                </form>
            </section>

            <section className={Styles.delete__section}>
                <div className={Styles.page__section__title}>
                    Delete the Data!
                </div>
                <form className={Styles.page__form}>
                    <div className={Styles.form__group}>
                        <label className={Styles.page__form__label} htmlFor="departamentNameToDelete">Enter the name of
                            the
                            department you want to delete</label>
                        <input
                            className={Styles.page__form__input}
                            type="text"
                            name="departamentNameToDelete"
                            placeholder="Department Name"
                            value={departmentData.name}
                            onChange={handleDepartmentNameChange}
                            ref={departmentNameRef}
                        />
                    </div>
                    <button type="button"
                            onClick={() => handleDelete("department", "http://localhost:8082/api/hospital_stocks/departments/")}>Delete
                        Department
                    </button>

                </form>

                <form className={Styles.page__form}>
                    <div className={Styles.form__group}>
                    <label className={Styles.page__form__label} htmlFor="drugNameToDelete">Enter the name of the
                            drug to delete from the database</label>
                        <input
                            className={Styles.page__form__input}
                            type="text"
                            name="drugNameToDelete"
                            placeholder="Drug Name"
                            value={drugData.name}
                            onChange={handleDrugNameChange}
                            ref={drugNameRef}
                        />
                    </div>
                    <button type="button"
                            onClick={() => handleDelete("drug", "http://localhost:8082/api/hospital_stocks/medicines/")}>Delete
                        Drug
                    </button>

                </form>

                <form className={Styles.page__form}>
                    <div className={Styles.form__group}>
                        <label className={Styles.page__form__label} htmlFor="supplierNameToDelete">Enter the name of the
                            supplier to delete</label>
                        <input
                            className={Styles.page__form__input}
                            type="text"
                            name="supplierNameToDelete"
                            placeholder="Supplier Name"
                            value={supplierData.name}
                            onChange={handleSupplierNameChange}
                            ref={supplierNameRef}
                        />
                    </div>
                    <button type="button"
                            onClick={() => handleDelete("supplier", "http://localhost:8082/api/hospital_stocks/suppliers/")}>Delete
                        Supplier
                    </button>
                </form>
            </section>

            <section className={Styles.update__section}>
                <div className={Styles.page__section__title}>
                    Update the Data!
                </div>
            </section>
        </div>
    );
}
