import React, {useState, useRef} from 'react';
import Styles from './page.module.scss';

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
        departmentName: ''
    });
    const [purchaseData, setPurchaseData] = useState({
        medicine: '',
        price: '',
        quantity: '',
        supplier: ''
    })
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
    const [purchaseDataUpdate, setPurchaseDataUpdate] = useState({
        medicine: '',
        price: '',
        quantity: '',
        supplier: ''
    })
    const [consumptionDataUpdate, setConsumptionDataUpdate] = useState({
        medicine: '',
        quantity: '',
        department:''
    })
    //const [error, setError] = useState('');

    const stockNameRef = useRef();
    const stockDescriptionRef = useRef();
    const stockUnitsRef = useRef();
    const stockPricePerUnitRef = useRef();
    const contactNameRef = useRef();
    const contactInfoRef = useRef();
    const quantityRef = useRef();
    const departmentRef = useRef();
    const priceRef = useRef();
    const departmentNameToAddRef = useRef();
    const departmentNameRef = useRef();
    const drugNameRef = useRef();
    const supplierNameRef = useRef();

    const medicinePurchaseUpdate = useRef(null);
    const pricePurchaseUpdate = useRef(null);
    const quantityPurchaseUpdate = useRef(null);
    const supplierPurchaseUpdate = useRef(null);const medicineConsumptionUpdate = useRef();
    const departmentConsumptionUpdate= useRef();
    const quantityConsumptionUpdate = useRef();
    const handlePurchaseUpdate = (event) => {
        const { name, value } = event.target;
        setPurchaseDataUpdate((prevData) => ({
            ...prevData,
            [name]: value
        }));
    };

    const handleConsumptionUpdate = (e) => {
        setConsumptionDataUpdate({
            ...consumptionDataUpdate,
            [e.target.name]: e.target.value
        });
    };

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
            departmentName: departmentRef.current.value
        });
        setPurchaseData({
            medicine: stockNameRef.current.value,
            price: priceRef.current.value,
            quantity: quantityRef.current.value,
            supplier: contactNameRef.current.value
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
            name: e.target.value,
        });
    };

    const handleSupplierNameChange = (e) => {
        setSupplierData({
            ...supplierData,
            name: e.target.value
        });
    };

    const fetchPurchaseData = async (medicine, supplier) => {
        try {
            console.log(medicine,supplier);
            const response = await fetch(`http://localhost:8082/api/hospital_Purchases/purchases/one/?medicine=${encodeURIComponent(medicine)}&supplier=${encodeURIComponent(supplier)}`, {
                method: 'GET'
            });

            if (response.ok) {
                const purchaseData = await response.json();
                console.log('Purchase data fetched successfully:', purchaseData);
                return purchaseData;
            } else {
                const errorText = await response.text();
                console.error('Failed to fetch purchase data:', errorText);
                alert('Failed to fetch purchase data: ' + errorText);
                return null;
            }
        } catch (error) {
            console.error('Error fetching purchase data:', error);
            alert('An error occurred while fetching purchase data');
            return null;
        }
    };

    const updatePurchaseData = async (id, updates) => {
        try {
            const { quantity, price } = updates;
            console.log(id, updates);
            const response = await fetch(`http://localhost:8082/api/hospital_Purchases/purchases/${id}?quantity=${encodeURIComponent(quantity)}&price=${encodeURIComponent(price)}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            });

            if (response.ok) {
                const updatedPurchase = await response.json();
                console.log('Purchase data updated successfully:', updatedPurchase);
                alert('Purchase data updated successfully');
            } else {
                const errorText = await response.text();
                console.error('Failed to update purchase data:', errorText);
                alert('Failed to update purchase data: ' + errorText);
            }
        } catch (error) {
            console.error('Error updating purchase data:', error);
            alert('An error occurred while updating purchase data');
        }
    };

    const handleSubmitUpdate = async () => {
        const { medicine, price, quantity, supplier } = purchaseDataUpdate;
        const purchaseData = await fetchPurchaseData(medicine, supplier);

        console.log(purchaseData);
        if (purchaseData) {
            const updates = {
                quantity: quantity,
                price: price
            };
            await updatePurchaseData(purchaseData, updates);
        } else {
            console.error('Purchase data not found or invalid');
        }
    };

    const fetchConsumptionData = async (medicine, department) => {
        try {
            console.log(medicine, department, "helllooo");
            const response = await fetch(`http://localhost:8082/api/hospital_stocks/consumptions/findIdsByMedicineAndDepartment?medicine=${encodeURIComponent(medicine)}&department=${encodeURIComponent(department)}`, {
                method: 'GET'
            });

            if (response.ok) {
                const purchaseData = await response.json();
                console.log('Purchase data fetched successfully:', purchaseData);
                return purchaseData;
            } else {
                const errorText = await response.text();
                console.error('Failed to fetch consumption data:', errorText);
                alert('Failed to fetch purchase data: ' + errorText);
                return null;
            }
        } catch (error) {
            console.error('Error fetching purchase data:', error);
            alert('An error occurred while fetching purchase data');
            return null;
        }
    };

    const fetchStockData = async (medicine, department) =>{
        try {
            console.log(medicine, department);
            const response = await fetch(`http://localhost:8082/api/hospital_stocks/stocks/findIdsByMedicineAndDepartment?medicine=${encodeURIComponent(medicine)}&department=${encodeURIComponent(department)}`, {
                method: 'GET'
            });

            if (response.ok) {
                const purchaseData = await response.json();
                console.log('Purchase data fetched successfully:', purchaseData);
                return purchaseData;
            } else {
                const errorText = await response.text();
                console.error('Failed to fetch purchase data:', errorText);
                alert('Failed to fetch purchase data: ' + errorText);
                return null;
            }
        } catch (error) {
            console.error('Error fetching purchase data:', error);
            alert('An error occurred while fetching purchase data');
            return null;
        }
    }
    const updateConsumptionData = async (id, updates) => {
        try {
            const { quantity } = updates;
            console.log(id, updates);
            const response = await fetch(`http://localhost:8082/api/hospital_stocks/consumptions/updateQuantity/${id}?quantity=${encodeURIComponent(quantity)}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            });

            if (response.ok) {
                const updatedPurchase = await response.json();
                console.log('Purchase data updated successfully:', updatedPurchase);
                alert('consumption data updated successfully');
            } else {
                const errorText = await response.text();
                console.error('Failed to update consumption data:', errorText);
                alert('Failed to update consumption data: ' + errorText);
            }
        } catch (error) {
            console.error('Error updating purchase data:', error);
            alert('An error occurred while updating consumption data');
        }
    };
    const updateStockData = async (id, updates) => {
        try {
            const { quantity } = updates;
            console.log(id, updates);
            const response = await fetch(`http://localhost:8082/api/hospital_stocks/stocks/updateQuantity/${id}?quantity=${encodeURIComponent(quantity)}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            });

            if (response.ok) {
                const updatedPurchase = await response.json();
                console.log('Purchase data updated successfully:', updatedPurchase);
                alert('stock data updated successfully');
            } else {
                const errorText = await response.text();
                console.error('Failed to update purchase data:', errorText);
                alert('Failed to update stock data: ' + errorText);
            }
        } catch (error) {
            console.error('Error updating purchase data:', error);
            alert('An error occurred while updating purchase data');
        }
    }
    const handleSubmitUpdateCon = async () => {
        const { medicine, department, quantity } = consumptionDataUpdate;
        const purchaseData = await fetchConsumptionData(medicine, department);
        const stockDataToUpdate = await fetchStockData(medicine, department);
        console.log(purchaseData , stockDataToUpdate);
        if (purchaseData) {
            const updates = {
                quantity: quantity
            };
            await updateConsumptionData(purchaseData, updates);
        } else {
            console.error('Consumption data not found or invalid');
        }
        if (stockDataToUpdate) {
            const updates = {
                quantity: quantity
            };
            await updateStockData(stockDataToUpdate, updates);
        } else {
            console.error('Consumption data not found or invalid');
        }
    };

    const handleDelete = async (type, endpoint) => {
        let data;
        switch (type) {
            case "department":
                data = departmentData;
                break;
            case "supplier":
                data = supplierData;
                break;
            case "drug":
                data = drugData;
                break;
            default:
                return;
        }
        try {
            const url = endpoint + data.name;
            console.log(url);
            const response = await fetch(url, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            console.log(url);
            if (response.ok) {
                alert(type + ' deleted successfully');

            } else {
                const errorText = await response.text();
                console.error('Error response:', errorText);
                alert('Failed to delete: ' + errorText);
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
        console.log('Data:', data);
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
        await handleSubmit(stockData, 'http://localhost:8082/api/hospital_stocks/medicines');
        await handleSubmit(contactData, 'http://localhost:8082/api/hospital_stocks/suppliers');
        await handleSubmit(quantityData, 'http://localhost:8082/api/hospital_stocks/stocks', 'application/x-www-form-urlencoded');
        await handleSubmit(purchaseData, 'http://localhost:8082/api/hospital_Purchases/purchases', 'application/x-www-form-urlencoded');

    };

    return (
        <div className={Styles.container}>
            <div className={Styles.page__title}>
                Add, Delete and Update Stocks!
            </div>
            <section className={Styles.add__section}>
                <div className={Styles.page__section__title}>
                    Add Drugs!
                </div>
                <div className={Styles.line}></div>
                <div className={Styles.add__section__forms}>
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
                        <button className={Styles.form__button} type="button" onClick={saveStockData}>Confirm</button>
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
                        <button className={Styles.form__button} type="button" onClick={saveContactData}>Confirm</button>
                    </form>

                    <form className={Styles.page__form}>
                        <label className={Styles.page__form__label} htmlFor="quantity">Enter the quantity of
                            drugs</label>
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
                        <label className={Styles.page__form__label} htmlFor="price">Enter the price</label>
                        <input
                            className={Styles.page__form__input}
                            type="text"
                            name="price"
                            placeholder="Price"
                            ref={priceRef}
                        />
                        <button className={Styles.form__button} type="button" onClick={saveQuantityData}>Confirm
                        </button>
                    </form>
                </div>
                <button className={Styles.form__button__submit} onClick={handleAdd}>Add</button>
            </section>

            <section className={Styles.add__section__department}>
                <div className={Styles.page__section__title}>
                    Add Departments!
                </div>
                <div className={Styles.line}></div>
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
                    <button type="button" className={Styles.form__button__submit} onClick={handleAddDepartment}>Add
                        Department
                    </button>
                </form>
            </section>

            <section className={Styles.delete__section}>
                <div className={Styles.page__section__title}>
                    Delete the Data!
                </div>
                <div className={Styles.line}></div>
                <div className={Styles.page__forms__group}>
                    <form className={Styles.page__form}>
                        <div className={Styles.form__group}>
                            <label className={Styles.page__form__label} htmlFor="departamentNameToDelete">Enter the name of
                                the
                                department</label>
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
                        <button
                            className={Styles.form__button__submit}
                            type="button"
                            onClick={() => handleDelete("department", "http://localhost:8082/api/hospital_stocks/departments/")}>Delete
                            Department
                        </button>

                </form>

                <form className={Styles.page__form}>
                    <div className={Styles.form__group}>
                        <label className={Styles.page__form__label} htmlFor="drugNameToDelete">Enter the name of the
                            drug</label>
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
                    <button
                        className={Styles.form__button__submit}
                        type="button"
                            onClick={() => handleDelete("drug", "http://localhost:8082/api/hospital_stocks/medicines/")}>Delete
                        Drug
                    </button>

                </form>

                    <form className={Styles.page__form}>
                        <div className={Styles.form__group}>
                            <label className={Styles.page__form__label} htmlFor="supplierNameToDelete">Enter the name of the
                                supplier</label>
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
                        <button
                            className={Styles.form__button__submit}
                            type="button"
                            onClick={() => handleDelete("supplier", "http://localhost:8082/api/hospital_stocks/suppliers/")}>Delete
                            Supplier
                        </button>
                    </form>
                </div>
            </section>

            <section className={Styles.update__section}>
                <div className={Styles.page__section__title}>
                    Update the Data!
                </div>
                <div className={Styles.line}></div>
                <div className={Styles.update__section__forms}>
                    <div className={Styles.update__section_form_group}>
                        <div className={Styles.update__section__form__title}>
                            Add New Purchase!
                        </div>
                        <form className={Styles.page__form}>
                            <label className={Styles.page__form__label} htmlFor="medicine">Enter the name of
                                drugs</label>
                            <input
                                className={Styles.page__form__input}
                                type="text"
                                name="medicine"
                                placeholder="Name"
                                ref={medicinePurchaseUpdate}
                                onChange={handlePurchaseUpdate}
                            />
                            <label className={Styles.page__form__label} htmlFor="price">Enter the price of drugs</label>
                            <input
                                className={Styles.page__form__input}
                                type="text"
                                name="price"
                                placeholder="Price"
                                ref={pricePurchaseUpdate}
                                onChange={handlePurchaseUpdate}
                            />
                            <label className={Styles.page__form__label} htmlFor="quantity">Enter the quantity of
                                drugs</label>
                            <input
                                className={Styles.page__form__input}
                                type="number"
                                name="quantity"
                                placeholder="Quantity"
                                ref={quantityPurchaseUpdate}
                                onChange={handlePurchaseUpdate}
                            />
                            <label className={Styles.page__form__label} htmlFor="supplier">Enter the supplier</label>
                            <input
                                className={Styles.page__form__input}
                                type="text"
                                name="supplier"
                                placeholder="Supplier"
                                ref={supplierPurchaseUpdate}
                                onChange={handlePurchaseUpdate}
                            />
                            <button
                                type="button"
                                className={Styles.form__button__submit}
                                onClick={handleSubmitUpdate}
                            >
                                Add Purchase
                            </button>
                        </form>
                    </div>
                    <div className={Styles.update__section_form_group}>
                        <div className={Styles.update__section__form__title}>
                            Add New Consumption!
                        </div>
                        <form className={Styles.page__form}>
                            <label className={Styles.page__form__label} htmlFor="name">Enter the name of drugs</label>
                            <input
                                className={Styles.page__form__input}
                                type="text"
                                name="medicine"
                                placeholder="Name"
                                ref={medicineConsumptionUpdate}
                                onChange={handleConsumptionUpdate}
                            />
                            <label className={Styles.page__form__label} htmlFor="quantity">Enter the quantity of
                                drugs</label>
                            <input
                                className={Styles.page__form__input}
                                type="number"
                                name="quantity"
                                placeholder="Quantity"
                                ref={quantityConsumptionUpdate}
                                onChange={handleConsumptionUpdate}
                            />
                            <label className={Styles.page__form__label} htmlFor="department">Enter the
                                department</label>
                            <input
                                className={Styles.page__form__input}
                                type="text"
                                name="department"
                                placeholder="Department"
                                ref={departmentConsumptionUpdate}
                                onChange={handleConsumptionUpdate}
                            />
                            <button
                                type="button"
                                className={Styles.form__button__submit}
                                onClick={handleSubmitUpdateCon}
                            >
                                Add Consumption
                            </button>
                        </form>
                    </div>
                </div>
            </section>
        </div>
    );
}
