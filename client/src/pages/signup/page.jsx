import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import Styles from './page.module.scss';

export default function Signup() {
    const [formData, setFormData] = useState({
        email: '',
        username: '',
        password: '',
        password_confirmation: '',
    });

    const [errors, setErrors] = useState([]);
    const navigate = useNavigate();

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setErrors([]);
        let encodedData = [];
        for (let property in formData) {
            let encodedKey = encodeURIComponent(property);
            let encodedValue = encodeURIComponent(formData[property]);
            encodedData.push(encodedKey + "=" + encodedValue);
        }
        encodedData = encodedData.join("&");
        try {
            const response = await fetch('http://localhost:8082/api/hospital_stocks/users/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: encodedData,
            });

            const result = await response.json();
           if(result === 200){
                navigate('/');
            } else {
                setErrors(['An unexpected error occurred.']);
            }

        } catch (error) {
            console.log(error)
            setErrors(['An unexpected error occurred.']);
        }

        setInterval(() => {
            setErrors([])
        }, 3000);
    };

    return (
        <div className={Styles.container}>
            <div className={Styles.welcome__Section}>
                <h1 className={Styles.welcome__text}>Welcome to Medical Stocks!</h1>
                <p className={Styles.welcome__subtext}>Glad you want our help!</p>
            </div>
            <div className={Styles.signup__section}>
                <h1 className={Styles.signup__title}>Sign Up</h1>
                <form className={Styles.form__signup} onSubmit={handleSubmit}>
                    <label className={Styles.form__label} htmlFor="email">Your Email:</label><br />
                    {errors.map((error, index) => (
                        <div key={index} className={`${Styles.alert} ${Styles.alertDanger}`}>{error}</div>
                    ))}
                    <input
                        className={Styles.form__input}
                        type="email"
                        id="email"
                        name="email"
                        placeholder="Enter the email"
                        value={formData.email}
                        onChange={handleChange}
                    /><br />
                    <label className={Styles.form__label} htmlFor="username">Your Username:</label><br />
                    <input
                        className={Styles.form__input}
                        type="text"
                        id="username"
                        name="username"
                        placeholder="Enter the username"
                        value={formData.username}
                        onChange={handleChange}
                    /><br />
                    <label className={Styles.form__label} htmlFor="password">Your Password:</label><br />
                    <input
                        className={Styles.form__input}
                        type="password"
                        id="password"
                        name="password"
                        placeholder="Enter the password"
                        value={formData.password}
                        onChange={handleChange}
                    /><br />
                    <label className={Styles.form__label} htmlFor="password_confirmation">Confirm Password:</label><br />
                    <input
                        className={Styles.form__input}
                        type="password"
                        id="password_confirmation"
                        name="password_confirmation"
                        placeholder="Enter the password"
                        value={formData.password_confirmation}
                        onChange={handleChange}
                    />
                    <div className={Styles.submit__button}>
                        <input className={Styles.button__input} type="submit" value="Submit" />
                    </div>
                </form>
                <div className={Styles.signup__line}></div>
                <div className={Styles.is__signedup}>
                    <div className={Styles.is__signupQuestion}>Already have an account?&nbsp;&nbsp;</div>
                    <Link className={Styles.signUpInSignInForm} to="/signin">Sign In!</Link>
                </div>
            </div>
        </div>
    );
}
